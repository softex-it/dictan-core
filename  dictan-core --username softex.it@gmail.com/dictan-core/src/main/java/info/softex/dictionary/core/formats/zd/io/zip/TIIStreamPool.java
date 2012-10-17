/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2012  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
 *	
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License (LGPL) as 
 *  published by the Free Software Foundation, either version 3 of the License, 
 *  or any later version.
 *	
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *	
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package info.softex.dictionary.core.formats.zd.io.zip;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.1, 04/11/2010
 * 
 * @modified version 3.4, 06/27/2012
 * 
 * @author Dmitry Viktorov
 *
 */
public class TIIStreamPool {
	
	private static final int LEVEL_UNDEFINED = -1; 
	//private static final int LEVEL_LOW = -2;
	private static final int LEVEL_HIGH = -3;
	
	private static final int minLevelGradeDiff = 16384;
	
	private static final Logger log = LoggerFactory.getLogger(TIIStreamPool.class);
	
	private final TIIStreamFactory tiisFactory;
	
	private final AtomicReferenceArray<TIIStream> activeStreams;
	
	private final LinkedList<TIIStream> returnedStreams;
	
	private final long[] levelGrades;
	
	private final int streamsNumber; 
	
	private final AtomicInteger activeThreadsNumber;
	
	public TIIStreamPool(TIIStreamFactory tiisFactory, long maxUncompressedLength, int streamsNumber, int readThreadNumber, int returnThreadNumber) {
		
		this.activeThreadsNumber = new AtomicInteger(0);
		
		this.tiisFactory = tiisFactory;
		
		long maxStreamsNumber = Math.max(0, maxUncompressedLength / minLevelGradeDiff - 1);

		if (streamsNumber > maxStreamsNumber) {
			streamsNumber = (int)maxStreamsNumber;
		}
		this.streamsNumber = streamsNumber;

		// Calculate levels. The last one is the upper limit.
		this.levelGrades = new long[this.streamsNumber];
		
		long curLevelGradeDiff = maxUncompressedLength / (this.levelGrades.length + 1);
		for (int i = 0; i < this.levelGrades.length; i++) {
			this.levelGrades[i] = (i + 1) * curLevelGradeDiff;
		}
		
		this.activeStreams = new AtomicReferenceArray<TIIStream>(streamsNumber);
		
		// Create reading threads
		if (readThreadNumber > this.streamsNumber) {
			readThreadNumber = this.streamsNumber;
		}
		
		if (readThreadNumber > 0) {
			ExecutorService executorService = Executors.newFixedThreadPool(readThreadNumber, new ReadingThreadFactory());
			for (int i = 0; i < readThreadNumber; i++) {
				executorService.execute(new ReadingTask());
			}
		}
		
		// Create returning threads
		this.returnedStreams = new LinkedList<TIIStream>();
		
		if (returnThreadNumber > this.streamsNumber) {
			returnThreadNumber = this.streamsNumber;
		}
		
		if (returnThreadNumber > 0) {
			ExecutorService executorService = Executors.newFixedThreadPool(returnThreadNumber, new ReturningThreadFactory());
			for (int i = 0; i < returnThreadNumber; i++) {
				executorService.execute(new ReturningTask());
			}
		}
		
		log.info("Streams: {}; Reading Threads: {}; Returning Threads: {}", new Object[] {streamsNumber, readThreadNumber, returnThreadNumber});
	}
	
	public TIIStream getIfCloser(long position, long curPosition) {
		
		TIIStream resIs = getPrevAvailableStream(position);
		
		if (resIs == null) {
			return null;
		}
		
		if (resIs.getTotalBytesPassed() < curPosition) {
			put(resIs);
			resIs = null;
		} 
		
		return resIs;
	}
	
	public TIIStream get(long position) {
				
		TIIStream is = getPrevAvailableStream(position);
		
		if (is != null) {
			long skipLength = position - is.getTotalBytesPassed();
			
			if (skipLength != 0) {
				boolean skipped = skipStream(is, skipLength);
				if (!skipped) {
					is = null;
				}
			}
			
			synchronized (this.activeStreams) { 
				this.activeStreams.notify();
			}
		} else {
			is = this.tiisFactory.createTIIStream(position);
		}

		// is must never be null here
		return is;
	}
	
	public void put(TIIStream is) {
		
		if (this.streamsNumber == 0) {
			return;
		}
		
		synchronized (this.returnedStreams) {
			if (this.returnedStreams.size() > streamsNumber / 2) {
				return;
			}
			this.returnedStreams.add(is);
			this.returnedStreams.notify();
		}
	}
	
	private void putDirectly(TIIStream is) {
				
		boolean saved = false;

		int streamLevel = LEVEL_UNDEFINED;
		
		while (!saved) {
			
			streamLevel = getNextAvailableLevel(is.getTotalBytesPassed());
			
			if (streamLevel == LEVEL_HIGH) {
				break;
			}
			
			long skipLength = (this.levelGrades[streamLevel] - is.getTotalBytesPassed());
			if (skipLength != 0) {
				boolean skipped = skipStream(is, skipLength);
				if (!skipped) {
					return;
				}
			}
			
			saved = activeStreams.compareAndSet(streamLevel, null, is);
		}
		
		if (!saved) {
			closeStream(is);
		} 

	}

	private TIIStream getPrevAvailableStream(long position) {
		
		int startStreamLevel = getStreamLevel(position);
		
		if (startStreamLevel == LEVEL_HIGH) {
			startStreamLevel = this.levelGrades.length - 1;
		} else {
			startStreamLevel--;
		}
		
		TIIStream is = null;
		
		for (; is == null && startStreamLevel >= 0; startStreamLevel--) {
			is = this.activeStreams.getAndSet(startStreamLevel, null);
		}
	
		return is;
	}
	
	private int getNextAvailableLevel(long position) {
		
		int streamLevel = getStreamLevel(position);
		
		if (streamLevel == LEVEL_HIGH) {
			return LEVEL_HIGH;
		} 
		
		for (int i = streamLevel; i < this.levelGrades.length; i++) {
			TIIStream stream = this.activeStreams.get(i);
			if (stream == null) {
				return i;
			}
		}
		
		return LEVEL_HIGH;	
	}
	
	private int getNextAvailableLevelsNumber(long position) {
		
		int streamLevel = getStreamLevel(position);
		
		if (streamLevel < 0) {
			return 0;
		} 
		
		int count = 0;
		for (int i = streamLevel; i < this.levelGrades.length; i++) {
			TIIStream stream = this.activeStreams.get(i);
			if (stream == null) {
				count++;
			}
		}
		return count;
	}
	
	private int getStreamLevel(long position) {
		
		if (this.levelGrades.length ==0 || position > this.levelGrades[this.levelGrades.length - 1]) {
			return LEVEL_HIGH;
		}
		
		int level = 0;
		
		for (; level < this.levelGrades.length; level++) {
			if (position <= this.levelGrades[level]) {
				break;
			}
		}
				
		return level;
	}
	
	private static boolean skipStream(TIIStream is, long length) {
		
		if (length == 0) {
			return true;
		}
		
		try {
			is.skip(length);
		} catch (IOException e) {
			log.info("Couldn't skip stream: " + e.getMessage());
			closeStream(is);
			return false;
		}
		return true;
	}
	
	private static void closeStream(TIIStream is) {
		try {
			is.close();
		} catch (IOException e) {
			log.info("Couldn't close stream: " + e.getMessage());
		}
		return;
	}
	
	private class ReadingTask implements Runnable {

		private void waitOnStreams() {
			synchronized (activeStreams) {
				try {
					activeStreams.wait();
				} catch (InterruptedException e) {
					log.error("Error in Reading Task", e);
				}
			}
		}
		
		@Override
		public void run() {
			while (true) {

				int availableLevels = getNextAvailableLevelsNumber(0);
				int activeThreads = activeThreadsNumber.get();
				if (activeThreads < availableLevels) {
					activeThreadsNumber.incrementAndGet();
				} else {
					waitOnStreams();
					continue;
				}
				
				TIIStream iis = tiisFactory.createTIIStream();
				
				if (iis != null) {
					int level = getNextAvailableLevel(0);
					if (level != LEVEL_HIGH) {
						putDirectly(iis);
					} else {
						closeStream(iis);
					}
				}
				
				activeThreadsNumber.decrementAndGet();
				
			}

		}
		
	}
	
	private class ReturningTask implements Runnable {
		
		private TIIStream waitAndGetReturnedStream() {
			TIIStream iis = null;
			synchronized (returnedStreams) {
				while (iis == null) {
					if (returnedStreams.size() == 0) {
						try {
							returnedStreams.wait();
						} catch (InterruptedException e) {
							log.error("Error in Returning Task", e);
						}
					}
					
					// May lose element after wait if it doesn't get monitor immediately
					if (returnedStreams.size() > 0) {
						iis = returnedStreams.removeFirst();
					}
				}


			}
			return iis;
		}
		
		@Override
		public void run() {
			while (true) {
				
				TIIStream iis = waitAndGetReturnedStream();

				int availableLevels = getNextAvailableLevelsNumber(iis.getTotalBytesPassed());
				int activeThreads = activeThreadsNumber.get();
				
				if (activeThreads < availableLevels) {
					activeThreadsNumber.incrementAndGet();
					putDirectly(iis);
					activeThreadsNumber.decrementAndGet();
				} else {
					closeStream(iis);
				}
			}
		}
		
	}
	
	private class ReadingThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable task) {
			Thread thread = new Thread(task);
			thread.setDaemon(true);
			thread.setPriority(Thread.MIN_PRIORITY);
			return thread;
		}
	}
	
	private class ReturningThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable task) {
			Thread thread = new Thread(task);
			thread.setDaemon(true);
			thread.setPriority(Thread.MIN_PRIORITY);
			return thread;
		}
	}
	
}

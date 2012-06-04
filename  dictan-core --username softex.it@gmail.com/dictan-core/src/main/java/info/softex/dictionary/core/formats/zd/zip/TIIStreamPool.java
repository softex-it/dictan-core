/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2011 - 2012  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.formats.zd.zip;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.logging.Logger;

/**
 * 
 * @since version 2.1, 04/11/2010
 * 
 * @author Dmitry Viktorov
 *
 */
public class TIIStreamPool {
	
	private static final int LEVEL_UNDEFINED = -1; 
	//private static final int LEVEL_LOW = -2;
	private static final int LEVEL_HIGH = -3;
	
	private static final int minLevelGradeDiff = 16384;
	//private static final int minLevelGradeDiff = 4096;
	
	private static final Logger LOG = Logger.getLogger(TIIStreamPool.class.toString());
	
	//private ExecutorService executorService;
	
	private final TIIStreamProducer tiisCreator;
	
	private final AtomicReferenceArray<TIIStream> activeStreams;
	
	private final LinkedList<TIIStream> returnedStreams;
	
	private final long[] levelGrades;
	
	private final int streamsNumber; 
	
	private final AtomicInteger activeThreadsNumber;
	//private final int readThreadNumber;
	
	public TIIStreamPool(TIIStreamProducer tiisCreator, long maxUncompressedLength, int streamsNumber, int readThreadNumber, int returnThreadNumber) {
		
		this.activeThreadsNumber = new AtomicInteger(0);
		
		this.tiisCreator = tiisCreator;
		
		long maxStreamsNumber = Math.max(0, maxUncompressedLength / minLevelGradeDiff - 1);

		if (streamsNumber > maxStreamsNumber) {
			streamsNumber = (int)maxStreamsNumber;
		}
		this.streamsNumber = streamsNumber;
		LOG.info("Pool | Streams Number: " + streamsNumber);

		// Calculate levels. The last one is the upper limit.
		this.levelGrades = new long[this.streamsNumber];
		
		long curLevelGradeDiff = maxUncompressedLength / (this.levelGrades.length + 1);
		for (int i = 0; i < this.levelGrades.length; i++) {
			this.levelGrades[i] = (i + 1) * curLevelGradeDiff;
			//LOG.info("Pool | " + i + ": " + this.levelGrades[i]); 
		}
		//LOG.info("Pool | Max: " + maxUncompressedLength);
		
		this.activeStreams = new AtomicReferenceArray<TIIStream>(streamsNumber);
		
		// Create reading threads
		if (readThreadNumber > this.streamsNumber) {
			readThreadNumber = this.streamsNumber;
		}

		LOG.info("Pool | Read Threads Number: " + readThreadNumber);
		
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
		LOG.info("Pool | Return Threads Number: " + returnThreadNumber);
	}
	
	public TIIStream getIfCloser(long position, long curPosition) {
		
		TIIStream resIs = getPrevAvailableStream(position);
		
		if (resIs == null) {
			return null;
		}
		
		//System.out.println("TTT " + resIs.getTotalBytesPassed());
		if (resIs.getTotalBytesPassed() < curPosition) {
			put(resIs);
			resIs = null;
		} 
		
		//LOG.info("Pool | Get If Closer | Returned stream: " + resIs + ", Cur Pos: " + curPosition + ", Req Pos: " + position);

		return resIs;
	}
	
	public TIIStream get(long position) {
				
		TIIStream is = getPrevAvailableStream(position);
		
//		if (is == null) {
//			LOG.info("Pool | Get Stream | NOT Found Stream for Req Level: " + getStreamLevel(position) + ", Req Pos: " + position);
//		} else  {
//			LOG.info("Pool | Get Stream | Found Stream for Req Level: " + getStreamLevel(position) + ", Req Pos: " + position + ", " + is + ", " + getStreamLevel(is.getTotalBytesPassed()));
//		}
		
		if (is != null) {
			long skipLength = position - is.getTotalBytesPassed();
			
			if (skipLength != 0) {
				//LOG.info("Pool | Get Stream | Skip: " + skipLength);
				boolean skipped = skipStream(is, skipLength);
				if (!skipped) {
					is = null;
				}
			}
			
			synchronized (this.activeStreams) { 
				this.activeStreams.notify();
			}
			
			//LOG.info("Pool | Get Stream | Skipped Stream for Req Level: " + getStreamLevel(position) + ", " + is + ", Skipped Length: " + skipLength);
		} else {
			is = this.tiisCreator.createTIIStream(position);
			//LOG.info("Pool | Get Stream | Created a new stream: " + is + ", Req Pos: " + position);
		}

		// is must never be null here
		return is;
	}
	
	public void put(TIIStream is) {
		
		//LOG.info("Pool | Put | Got stream: " + is);
		
		if (this.streamsNumber == 0) {
			//LOG.info("Pool | Put | Stream's number is 0, returning");
			return;
		}
		
		synchronized (this.returnedStreams) {
			if (this.returnedStreams.size() > streamsNumber / 2) {
				//LOG.info("Pool | Put | Too many returned streams, returning");
				return;
			}
			this.returnedStreams.add(is);
			this.returnedStreams.notify();
		}
	}
	
	private void putDirectly(TIIStream is) {
				
		//LOG.info("Pool | Put Stream | Pos: " + is.getTotalBytesPassed());
		
		boolean saved = false;

		int streamLevel = LEVEL_UNDEFINED;
		
		while (!saved) {
			
			streamLevel = getNextAvailableLevel(is.getTotalBytesPassed());
			
			if (streamLevel == LEVEL_HIGH) {
				break;
			}
			
			long skipLength = (this.levelGrades[streamLevel] - is.getTotalBytesPassed());
			if (skipLength != 0) {
				//LOG.info("Pool | Put Directly | Skip: " + skipLength);
				boolean skipped = skipStream(is, skipLength);
				if (!skipped) {
					return;
				}
			}
			
			saved = activeStreams.compareAndSet(streamLevel, null, is);
		
			//nextLevel++; // didn't coincide with the boundary, so next level is a start point
		}
		
		if (!saved) {
			closeStream(is);
			//LOG.info("Pool | Stream is closed because of no space: " + is);
		} 
//		else {
//			LOG.info("Pool | Stream is saved to " + streamLevel + ", " + is);
//		}
		
		//LOG.info("Pool | " + this.activeStreams);
	}

	private TIIStream getPrevAvailableStream(long position) {
		
		int startStreamLevel = getStreamLevel(position);
		
		if (startStreamLevel == LEVEL_HIGH) {
			//LOG.info("Get Stream | High Level requested: " + startStreamLevel + ", making it: " + (this.levelGrades.length - 2));
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
		
		//LOG.info("Pool | Next Available Level | Stream Level: " + streamLevel + ", Pos: " + position);
		
		if (streamLevel == LEVEL_HIGH) {
			//LOG.info("Pool | Next Available Level | High Level is got, Pos: " + position + ", closing");
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
			LOG.info("Couldn't skip stream: " + e.getMessage());
			closeStream(is);
			return false;
		}
		return true;
	}
	
	private static void closeStream(TIIStream is) {
		try {
			is.close();
		} catch (IOException e) {
			LOG.info("Couldn't close stream: " + e.getMessage());
		}
		return;
	}
	
	private class ReadingTask implements Runnable {

		private void waitOnStreams() {
			synchronized (activeStreams) {
				try {
					//LOG.info("READING TASK | " + Thread.currentThread().getName() + " WAITING");
					activeStreams.wait();
				} catch (InterruptedException e) {
					LOG.severe("Error in Reading Task :" + e.getMessage());
				}
			}
		}
		
		@Override
		public void run() {
			while (true) {

				int availableLevels = getNextAvailableLevelsNumber(0);
				int activeThreads = activeThreadsNumber.get();
				//LOG.info("READING TASK | " + Thread.currentThread().getName() + " | Levels: " + availableLevels + ", Active Thtreads: " + activeThreads);
				if (activeThreads < availableLevels) {
					activeThreadsNumber.incrementAndGet();
				} else {
					waitOnStreams();
					continue;
				}
				
				//LOG.info("READING TASK | " + Thread.currentThread().getName());
				TIIStream iis = tiisCreator.createTIIStream();
				
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
							//LOG.info("RETURNING TASK | " + Thread.currentThread().getName() + " WAITING");
							returnedStreams.wait();
						} catch (InterruptedException e) {
							LOG.severe("Error in Returning Task: " + e.getMessage());
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
				//LOG.info("RETURNING TASK | " + Thread.currentThread().getName() + " | Levels: " + availableLevels + ", Active Threads: " + activeThreads);
				
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

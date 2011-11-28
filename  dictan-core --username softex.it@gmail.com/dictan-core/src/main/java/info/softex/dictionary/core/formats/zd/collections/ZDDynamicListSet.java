/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2011  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.formats.zd.collections;

import info.softex.dictionary.core.attributes.ProgressInfo;
import info.softex.dictionary.core.collections.AbstractDynamicListSet;
import info.softex.dictionary.core.collections.BasicCacheBlock;
import info.softex.dictionary.core.collections.CacheBlockReferenceTypes;
import info.softex.dictionary.core.collections.RestoreBlockException;
import info.softex.dictionary.core.formats.zd.ZDConstants;
import info.softex.dictionary.core.formats.zd.ZDReadUtils;
import info.softex.dictionary.core.formats.zd.zip.TIIStream;
import info.softex.dictionary.core.formats.zd.zip.TIIStreamPool;
import info.softex.dictionary.core.formats.zd.zip.TIIStreamProducer;
import info.softex.dictionary.core.io.BaseFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 1.3, 11/13/2010
 * 
 * @modified version 1.7, 02/05/2011
 * @modified version 1.8, 02/10/2011
 * @modified version 1.9, 02/16/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class ZDDynamicListSet extends AbstractDynamicListSet<String> {
	
	private final Logger log = LoggerFactory.getLogger(ZDDynamicListSet.class.getSimpleName());

	private final int bufferSize;
	private final File dictFile;

	private final int wordsNumber;
	private final int wordsStartPosition;
	private final int wordsSize;
	private final String listCodepageName;
	
	private TIIStream tiis;
	private TIIStreamPool tiisPool;
	
	public ZDDynamicListSet(
				File file, int bufferSize, int wordListBlockSize,
				int wordsNumber, int wordsStartPosition, 
				int wordsSize, String listCodepageName,
				ProgressInfo progressInfo
			) throws IOException, BaseFormatException {
		
		super(wordsNumber, wordListBlockSize, CacheBlockReferenceTypes.SOFT);

		this.dictFile = file; 
		this.bufferSize = bufferSize;
		
		this.wordsNumber = wordsNumber;
		this.wordsStartPosition = wordsStartPosition;
		this.wordsSize = wordsSize;
		this.listCodepageName = listCodepageName;

		
		TIIStreamProducer tiisCreator = new TIIStreamProducer(
				this.dictFile, 
				this.wordsStartPosition, 
				this.bufferSize
			);
		
		this.tiis = tiisCreator.createTIIStream();

		int blockIndices[];
		try {
			blockIndices = ZDReadUtils.buildWordBlockIndices(
					this.tiis, this.wordsNumber, this.wordsSize, 
					progressInfo, blockSize
				);
		} catch (DataFormatException e) {
			log.error("Error", e);
			throw new BaseFormatException(e.getMessage());
		}
		
		// Set other blocks
		for (int i = 0; i < weakBlocksList.length; i++) {
			weakBlocksList[i] = new ZDCacheBlock<String>(i, blockIndices[i], this.referenceType);
			//log.info("Block " + i + ": " + weakBlocksList[i]);
		}
		
		// Save 0 block content as it is called too often from Android ListView
		final TIIStream iisTemp = this.tiis;
		this.tiis = tiisCreator.createTIIStream();

		this.tiisPool = new TIIStreamPool(
				tiisCreator, this.wordsSize, 
				ZDConstants.POOL_READ_STREAMS_NUMBER, 
				ZDConstants.POOL_READ_THREADS_NUMBER, 
				ZDConstants.POOL_RETURN_THREADS_NUMBER
			);
		
		super.strongBlockList.put(0, getBlockContent(0));
		this.tiis = iisTemp;
		super.lastRestoredBlockNumber = -1;
		//super.lastRequestedBlockNumber = -1;
		//super.lastRestoredBlockContent = null;

		log.info("DDL Constructor | Blocks Size: " + weakBlocksList.length + ", LengStart Point: " + this.wordsStartPosition);
		
	}

	/**
	 * Attempts to restore the child stored on the given index.
	 * 
	 * @param index - the index.
	 * @return null if the child could not be restored or the restored child.
	 * @throws IOException 
	 */
	protected List<String> restoreBlockContent(final BasicCacheBlock<String> abstractBlock) throws RestoreBlockException {
		
		// This helps to see the probability of restoring the same block
		if (abstractBlock.getBlockNumber() == lastRestoredBlockNumber) {
			//throw new RestoreBlockException(abstractBlock.blockNumber, "Received the call to restore the block that was peviously restored");
			log.warn("Received the call to restore the block that was peviously restored");
		}
		
		//long startTime = System.currentTimeMillis();
		
		ZDCacheBlock<String> block = (ZDCacheBlock<String>)abstractBlock;
		
		final int blockStartPosition = findBlockStartPosition(block.getBlockNumber());
		int skipLength = blockStartPosition;
		final int bufferSize = block.endPosition - blockStartPosition;
		
		List<String> strongElements = null;
		
		try {
		
			// = is required as the selected block could be restored from the soft references
			if (block.getBlockNumber() <= lastRestoredBlockNumber || lastRestoredBlockNumber < 0) {
				renewTIIStream(skipLength);
			} else {
				ZDCacheBlock<String> lastSelectedBlock = (ZDCacheBlock<String>)weakBlocksList[lastRestoredBlockNumber];
				skipLength = blockStartPosition - lastSelectedBlock.endPosition;
				renewTIIStreamIfCloser(this.tiis.getTotalBytesPassed() + skipLength);
			}
			
			//LOG.info("Skip Length: " + skipLength + " | Cur BN: " + block.blockNumber + ", Last BN: " + lastRestoredBlockNumber);
			
			//if (this.tiis.getTotalBytesPassed() != blockStartPosition) {
			//	throw new RestoreBlockException(block.blockNumber, "Unexpected IS: " + this.tiis.getTotalBytesPassed() + ", Expected: " + blockStartPosition);
			//}
			
			byte[] dataBuffer = new byte[bufferSize];
			ZDReadUtils.readBuffer(this.tiis, dataBuffer, dataBuffer.length);
			strongElements = new ArrayList<String>(blockSize);
			
			int bc = 0;
			for (int j = 0; j < dataBuffer.length; j++) {
				if (dataBuffer[j] == 0) {
					int wlen = j - bc; 
					
					String word = new String(dataBuffer, bc, wlen, this.listCodepageName);
					strongElements.add(word);
					//log.info("! " + strongElements.size() + ", " + block + ", " + word);
					bc = j + 1;
				}		
			}
	
			// Read last element
			String word = new String(dataBuffer, bc, dataBuffer.length - bc, this.listCodepageName);
			strongElements.add(word);
			
			block.setElementsReference(strongElements, this.referenceType);
			
//			switch (refType) {
//				case SOFT_REF: block.dynamicElementsRef = new SoftReference<List<String>>(strongElements); break;
//				case WEAK_REF: block.dynamicElementsRef = new WeakReference<List<String>>(strongElements); break;
//			}
	
			//long time = System.currentTimeMillis() - startTime;
			//log.info("DDL | RestoreBlock | " + block.blockNumber + ", Block Start Position: " + blockStartPosition + ", Buffer Size: " + bufferSize);
			//log.info("DDL | RestoreBlock | Size: " + strongElements.size() + ", Time: " + time + ", First Word: " + strongElements.get(0));
			
		} catch (Exception e) {
			throw new RestoreBlockException(block.getBlockNumber(), "Unknown error {last restored block: " + 
				lastRestoredBlockNumber + ", skip length: " + skipLength + ", size: " + bufferSize + "}", e);
		}
		
		return strongElements;
		
	}
	
	private void renewTIIStream(long position) throws IOException {
		this.tiisPool.put(this.tiis);
		this.tiis = this.tiisPool.get(position);
		//System.out.println("got: " + tiis);
	}
	
	private void renewTIIStreamIfCloser(long position) throws IOException {
		TIIStream stream = this.tiisPool.getIfCloser(position, this.tiis.getTotalBytesPassed());
		if (stream != null) {
			this.tiisPool.put(this.tiis);
			this.tiis = stream;
			//LOG.info("Stream is OBTAINED from the pool 2: " + stream);
		} 
//		else {
//			LOG.info("Stream is not obtained from the pool 2");
//		}
		this.tiis.skip(position - this.tiis.getTotalBytesPassed());
		//LOG.info("Skipped stream: " + this.tiis);
	}
	
	private int findBlockStartPosition(int curBlockNumber) {
		int blockStartPosition = 0;
		if (curBlockNumber != 0) {
			ZDCacheBlock<String> prevBlock = (ZDCacheBlock<String>)weakBlocksList[curBlockNumber - 1];
			blockStartPosition = prevBlock.endPosition + 1;
		}
		return blockStartPosition;
	}
	
	public TIIStream getTIIStream() throws IOException {
		return tiis;
	}
	
}

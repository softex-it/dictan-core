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

package info.softex.dictionary.core.collections;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.RandomAccess;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 1.3, 11/12/2010
 * 
 * @modified version 1.4, 12/19/2010
 * @modified version 1.7, 02/05/2011
 * @modified version 1.8, 02/10/2011
 * @modified version 1.9, 02/16/2011
 * @modified version 2.6, 09/18/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public abstract class AbstractDynamicListSet<T> extends AbstractList<T> implements RandomAccess, Set<T> {

	private final Logger log = LoggerFactory.getLogger(AbstractDynamicListSet.class.getSimpleName());

	protected final int maxSize;
	protected final int blockSize;
	
	protected final CacheBlockReferenceTypes referenceType;

	// Stores the content of strong blocks which could be added at deferable classes
	protected final HashMap<Integer, List<T>> strongBlockList;
	
	protected final BasicCacheBlock<T>[] weakBlocksList;
	
	// Necessary for quick returns
	protected int lastRequestedBlockNumber = -1;
	
	// Necessary for file tracking
	protected int lastRestoredBlockNumber = -1;

	// Necessary to avoid reloads of the same elements
	protected List<T> lastSelectedBlockContent;

	/**
	 * blocksList must be filled out in the superclass!
	 * 
	 * @param maxSize
	 * @param blockSize
	 * @param refType
	 */
	@SuppressWarnings("unchecked")
	public AbstractDynamicListSet(final int maxSize, final int blockSize, CacheBlockReferenceTypes refType) {

		this.referenceType = refType;
		
		this.maxSize = maxSize;
		this.blockSize = blockSize;

		int blockNumber = (int)Math.ceil((double) maxSize / blockSize);
		this.weakBlocksList = new BasicCacheBlock[blockNumber];

		// Storage for the content of strong blocks
		this.strongBlockList = new HashMap<Integer, List<T>>();
		
		log.info("ADL Constructor | Number of Blocks: " + blockNumber + " | (" + maxSize + "/" + blockSize + ")");
		
	}

	/**
	 * 
	 * Synchronized because the data is being changed
	 * 
	 */
	@Override
	public synchronized T get(final int index) {
		
		int blockNumber = index / blockSize;
		int elementNumber = index % blockSize;
		
		// Use a quick response if the block number was selected last time
		if (this.lastRequestedBlockNumber == blockNumber) {
			//log.debug("AWL | Cached in last selected " + index + " | Block Number: " + blockNumber);
			return this.lastSelectedBlockContent.get(elementNumber);
		}

		// Find among the content of strong blocks
		if (this.strongBlockList.containsKey(blockNumber)) {
			List<T> blockContent = this.strongBlockList.get(blockNumber);
			if (blockContent != null) {
				//log.info("AWL | Cached in strong storage " + index + " | Block Number: " + blockNumber);
				return blockContent.get(elementNumber);
			}
		}
		
		List<T> blockContent = getBlockContent(blockNumber);
		T elem = blockContent.get(elementNumber);

		//log.debug("AWL | Get " + index + " | BN: " + blockNumber + " | " + toStringCounters() + ", Elem: " + elem);
		
		return elem;
	}
	
	public String toStringCounters() {
		return "Last Restored BN: " + this.lastRestoredBlockNumber + 
		" | Last Requested BN: " + this.lastRequestedBlockNumber + 
		", Block Size: " + blockSize;
	}
	
	@Override
	public int size() {
		return maxSize;
	}
	
	// Protected methods ------------------------

	/**
	 * 
	 * Attempts to restore the child stored at the given index.
	 * 
	 * @param index
	 * @return null if the child could not be restored or the restored child.
	 * 
	 */
	protected abstract List<T> restoreBlockContent(BasicCacheBlock<T> block) throws Exception;
	
	protected List<T> getBlockContent(final int blockNumber) {
		
		BasicCacheBlock<T> block = this.weakBlocksList[blockNumber];

		//log.debug("AWL | {}", + block);

		List<T> curDynamicElements = block.getElementsReference().get();

		if (curDynamicElements == null) {
			try {
				curDynamicElements = restoreBlockContent(block);
				this.lastRestoredBlockNumber = block.getBlockNumber();
			} catch (Exception e) {
				
				log.error("Error occured while restoring the block {}: ", block.getBlockNumber(), e);
				
				if (e instanceof RestoreBlockException) {
					throw (RestoreBlockException)e;
				} else {
					throw new RestoreBlockException(block.getBlockNumber(), "Read error occured", e);
				}
			}
			
			// Reject the case when the block is null
			if (curDynamicElements == null) {
				log.error("Block {} of size {} couldn't be restored", block.getBlockNumber(), blockSize); 
				throw new RestoreBlockException(block.getBlockNumber(), "Null returned");
			}
		}
		
		this.lastRequestedBlockNumber = block.getBlockNumber();
		this.lastSelectedBlockContent = curDynamicElements;
		
		//log.debug("Returning Block Content: " + block.blockNumber + ", Size:" + curDynamicElements.size());
		
		return curDynamicElements;
	}
	
}


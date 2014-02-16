/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2014  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

import info.softex.dictionary.core.collections.BasicCacheBlock;
import info.softex.dictionary.core.collections.CacheBlockReferenceTypes;

/**
 * 
 * @since version 1.3, 11/13/2010
 * 
 * @author Dmitry Viktorov
 *
 */
public class ZDCacheBlock<E> extends BasicCacheBlock<E> {
	
	protected final int endPosition;
	
	public ZDCacheBlock(int blockNumber, int endPosition, CacheBlockReferenceTypes refType) {	
		super(blockNumber, refType);
		this.endPosition = endPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}
		
	@Override
	public String toString() {
		return super.toString() + ", end position: " + endPosition;
	}
	
}

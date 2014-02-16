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

package info.softex.dictionary.core.collections;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 
 * @since version 1.3, 11/12/2010
 * 
 * @modified version 2.6, 09/10/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class BasicCacheBlock<E> {
	
	private final int blockNumber;
	
	private Reference<List<E>> elementsReference;

	public BasicCacheBlock(final int blockNumber, CacheBlockReferenceTypes refType) {
		this.blockNumber = blockNumber;
		setElementsReference(null, refType);
	}
	
	public int getBlockNumber() {
		return this.blockNumber;
	}
	
	public Reference<List<E>> getElementsReference() {
		return this.elementsReference;
	}
	
	public void setElementsReference(List<E> elements, CacheBlockReferenceTypes refType) {
		switch (refType) {
			case SOFT: this.elementsReference = new SoftReference<List<E>>(elements); break;
			case WEAK: this.elementsReference = new WeakReference<List<E>>(elements); break;
		}
	}
	
	@Override
	public String toString() {
		return "Block: number " + this.blockNumber;
	}
	
}
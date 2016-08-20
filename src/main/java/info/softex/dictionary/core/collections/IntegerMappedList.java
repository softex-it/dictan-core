/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2015  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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
import java.util.Map;
import java.util.RandomAccess;
import java.util.TreeMap;

/**
 * Adapter which represents a Map with Integer keys as List. 
 * The original Map may have it's own unordered sequence of elements.
 * 
 * @since version 4.7, 03/29/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class IntegerMappedList<E> extends AbstractList<E> implements RandomAccess {

	private final Map<Integer, E> map;
	
	private final TreeMap<Integer, Integer> indexMap = new TreeMap<>();
	
	public IntegerMappedList(Map<Integer, E> inMap) {
		if (inMap == null) {
			throw new NullPointerException();
		}
		this.map = inMap;

		int count = 0;
		for (Integer key : inMap.keySet()) {
			indexMap.put(count++, key);
		}
		
	}
	
	@Override
	public E get(int index) {
		Integer origIndex = indexMap.get(index);
		return origIndex != null ? map.get(origIndex) : null;
	}

    public int getOriginalIndex(int index) {
        Integer origIndex = indexMap.get(index);
        return origIndex != null ? origIndex : -1;
    }
    	
	@Override
	public int size() {
		return map.size();
	}

	/**
	 * Removes the last element only if it exists.
	 */
    public E removeLast() {
    	int size = size();
    	if (size > 0) {
    		Integer removedOrigInd = indexMap.remove(size - 1);
    		return map.remove(removedOrigInd);
    	}
    	return null;
    }

}

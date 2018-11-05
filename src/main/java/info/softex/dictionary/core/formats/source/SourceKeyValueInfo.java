/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2018  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.formats.source;

import info.softex.dictionary.core.attributes.KeyValueInfo;

/**
 * The storage for key-value pairs. Key is expected to be String, 
 * and value is parameterized.
 * 
 * @since version 4.2, 03/07/2014
 * 
 * @modified version 4.6, 01/27/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class SourceKeyValueInfo<V> implements KeyValueInfo<String, V> {

	private String key;
	
	private V value;
	
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void setKey(String inKey) {
		this.key = inKey;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public void setValue(V inValue) {
		this.value = inValue;
	}
	
}

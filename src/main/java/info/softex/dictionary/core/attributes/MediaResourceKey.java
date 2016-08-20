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

package info.softex.dictionary.core.attributes;

/**
 * 
 * @since version 4.6,		02/21/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class MediaResourceKey {
	
	private int id = -1;
	private String resourceKey;
	
	public MediaResourceKey(String word) {
		setResourceKey(word);
	}
	
	public MediaResourceKey(int id) {
		setId(id);
	}
	
	public MediaResourceKey(int id, String resourceKey) {
		setId(id);
		setResourceKey(resourceKey);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getResourceKey() {
		return resourceKey;
	}
	
	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}
	
	public boolean hasIndex() {
		return this.id >= 0;
	}
	
	@Override
	protected MediaResourceKey clone() {
		try {
			return (MediaResourceKey)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}

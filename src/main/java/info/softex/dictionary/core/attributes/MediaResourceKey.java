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

package info.softex.dictionary.core.attributes;

/**
 * 
 * @since       4.6, 02/21/2015
 *
 * @modified    5.2, 10/23/2018
 *
 * @author Dmitry Viktorov
 * 
 */
public class MediaResourceKey {

	// Base ID of the base that issued the resource.
	private String baseId = null;
	private int id = -1;
	private String resourceKey;
	
	public MediaResourceKey(String baseId, String key) {
	    setBaseId(baseId);
		setResourceKey(key);
	}
	
	public MediaResourceKey(String baseId, int id) {
	    setBaseId(baseId);
		setId(id);
	}
	
	public MediaResourceKey(String baseId, int id, String resourceKey) {
		setId(id);
		setResourceKey(resourceKey);
	}

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getBaseId() {
        return baseId;
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
			return (MediaResourceKey) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}

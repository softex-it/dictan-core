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

package info.softex.dictionary.core.attributes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 
 * @since version 2.6, 09/17/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class MediaResourceInfo {
	
	private int resourceId;
	private String resourceKey;
	private byte[] byteArray;
	
	public MediaResourceInfo(int resourceId, String resourceKey, byte[] byteArray) {
		this(resourceKey, byteArray);
		this.resourceId = resourceId;
	}
	
	public MediaResourceInfo(String resourceKey, byte[] byteArray) {
		setResourceKey(resourceKey);
		this.byteArray = byteArray;
	}
	
	public byte[] getByteArray() {
		return byteArray;
	}
	
	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}
	
	public int getResourceId() {
		return resourceId;
	}
	
	public String getResourceKey() {
		return resourceKey;
	}
	
	public InputStream getInputStream() {
		return new ByteArrayInputStream(byteArray);
	}
	
	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}
	
}

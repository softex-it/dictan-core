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
 * @modified version 3.4, 07/08/2012
 * 
 * @author Dmitry Viktorov
 * 
 */
public class MediaResourceInfo {
	
	private Key key;
	private byte[] byteArray;
	
	public MediaResourceInfo(Key key, byte[] byteArray) {
		this.key = key;
		this.byteArray = byteArray;
	}
	
	public byte[] getByteArray() {
		return byteArray;
	}
	
	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}
	
	public InputStream getInputStream() {
		return new ByteArrayInputStream(byteArray);
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	
	public static class Key implements Cloneable {
		
		private int id = -1;
		private String resourceKey;
		
		public Key(String word) {
			setResourceKey(word);
		}
		
		public Key(int id) {
			setId(id);
		}
		
		public Key(int id, String resourceKey) {
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
		protected Key clone() {
			try {
				return (Key)super.clone();
			} catch (CloneNotSupportedException e) {
				return null;
			}
		}
		
	}
	
}

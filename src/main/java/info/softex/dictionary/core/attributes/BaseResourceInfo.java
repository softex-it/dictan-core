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

import info.softex.dictionary.core.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @since version 2.6,		10/16/2011
 * 
 * @modified version 4.6,	02/16/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class BaseResourceInfo {

	private final static String UTF8 = "UTF-8";
	
	private int resourceId;
	private String resourceKey;
	private byte[] byteArray;
	private String info1;
	private String info2;
	
	public BaseResourceInfo(int resourceId, String resourceKey, byte[] byteData) {
		this(resourceKey, byteData);
		this.resourceId = resourceId;
	}
	
	public BaseResourceInfo(BaseResourceKey resourceKey, String stringData) throws UnsupportedEncodingException {
		setResourceKey(resourceKey);
		setByteArray(stringData.getBytes(UTF8));
	}
	
	public BaseResourceInfo(String resourceKey, String stringData) throws UnsupportedEncodingException {
		setResourceKey(resourceKey);
		setByteArray(stringData.getBytes(UTF8));
	}

	public BaseResourceInfo(BaseResourceKey resourceKey, byte[] byteArray) {
		setResourceKey(resourceKey);
		setByteArray(byteArray);
	}
	
	public BaseResourceInfo(String resourceKey, byte[] byteArray) {
		setResourceKey(resourceKey);
		setByteArray(byteArray);
	}
	
	public byte[] getByteArray() {
		return byteArray != null ? byteArray : new byte[0];
	}
	
	public void setByteArray(byte[] inByteArray) {
		this.byteArray = inByteArray;
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
	
	public void setResourceKey(String inKey) {
		this.resourceKey = inKey;
	}
	
	public void setResourceKey(BaseResourceKey inResourceKey) {
		if (inResourceKey != null) {
			setResourceKey(inResourceKey.getKey());
		}
	}
	
	public void setInfo1(String info) {
		this.info1 = info;
	}
	
	public void setInfo2(String info) {
		this.info2 = info;
	}
	
	public String getInfo1() {
		return StringUtils.defaultString(info1);
	}
	
	public String getInfo2() {
		return StringUtils.defaultString(info2);
	}
	
}

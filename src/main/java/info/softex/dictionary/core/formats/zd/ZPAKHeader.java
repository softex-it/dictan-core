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

package info.softex.dictionary.core.formats.zd;

/**
 * 
 * @since version 2.0, 03/08/2011
 *  
 * @author Dmitry Viktorov
 * 
 */
public class ZPAKHeader {

	private int mediaResourcesNumber;
	private int mediaResourcesBlockSize;
	private short mediaFormatVersion;
	
	private long mediaFileSize;

	private byte[] mark = null;
	private int flags;
	
	public int getMediaResourcesNumber() {
		return mediaResourcesNumber;
	}

	public void setMediaResourcesNumber(int mediaResourcesNumber) {
		this.mediaResourcesNumber = mediaResourcesNumber;
	}

	public int getMediaResourcesBlockSize() {
		return mediaResourcesBlockSize;
	}

	public void setMediaResourcesBlockSize(int mediaResourcesBlockSize) {
		this.mediaResourcesBlockSize = mediaResourcesBlockSize;
	}

	public short getMediaFormatVersion() {
		return mediaFormatVersion;
	}

	public void setMediaFormatVersion(short mediaFormatVersion) {
		this.mediaFormatVersion = mediaFormatVersion;
	}
	
	public long getMediaFileSize() {
		return mediaFileSize;
	}

	public void setMediaFileSize(long mediaFileSize) {
		this.mediaFileSize = mediaFileSize;
	}
	
	public byte[] getMark() {
		return mark;
	}

	public void setMark(byte[] mark) {
		this.mark = mark;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}
	
}
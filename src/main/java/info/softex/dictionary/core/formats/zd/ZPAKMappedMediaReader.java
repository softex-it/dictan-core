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

import info.softex.dictionary.core.formats.api.BaseFormatException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.InflaterInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.0, 03/06/2011
 * 
 * @modified version 2.6, 09/17/2011
 * @modified version 3.4, 07/07/2012
 * 
 * @author Dmitry Viktorov
 *
 */
public class ZPAKMappedMediaReader {
	
	private final Logger log = LoggerFactory.getLogger(ZPAKMappedMediaReader.class);
	
	protected File mediaFile = null;
	protected RandomAccessFile raf = null;
	protected MappedByteBuffer fileBuffer = null;
	protected ZPAKHeader zpakHeader = null;
	protected Map<String, ZPAKResourceMetaInfo> resources = null;

	public ZPAKMappedMediaReader(File file) throws IOException {
		this.mediaFile = file;
		this.raf = new RandomAccessFile(file, "r");
		FileChannel fileChannel = raf.getChannel();
		this.fileBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, raf.length());
		this.fileBuffer.order(ByteOrder.LITTLE_ENDIAN);
	}

	public void close() throws IOException {
		raf.close();
	}

	public void load() throws BaseFormatException, UnsupportedEncodingException {
	    if (this.zpakHeader == null) {
	        loadZPAKHeader();
	    }
		loadMediaResources();
	}

	public ZPAKHeader loadZPAKHeader() throws BaseFormatException {
		
        log.debug("Loading Header");
        
		this.zpakHeader = new ZPAKHeader();
		
		byte[] prefix = new byte[4];
		prefix[0] = fileBuffer.get();
		prefix[1] = fileBuffer.get();
		prefix[2] = fileBuffer.get();
		prefix[3] = fileBuffer.get();
		
		if (prefix[0] != 90 || prefix[1] != 112 || prefix[2] != 97 || prefix[3] != 107) {
			throw new BaseFormatException("ZPAK File Header is not recognized");
		} else {
			zpakHeader.setMark(prefix);
			zpakHeader.setMediaFormatVersion(fileBuffer.getShort());
			zpakHeader.setMediaResourcesNumber(fileBuffer.getInt());
			zpakHeader.setFlags(fileBuffer.getInt());
			zpakHeader.setMediaResourcesBlockSize(fileBuffer.getInt());
			zpakHeader.setMediaFileSize(this.mediaFile.length());
			return this.zpakHeader;
		}
	}

	private void loadMediaResources() throws BaseFormatException, UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		this.resources = new HashMap<String, ZPAKResourceMetaInfo>();
		
		long startTime = System.currentTimeMillis();
		
		int currNumber = 0;
		mainLoop: for (; currNumber < zpakHeader.getMediaResourcesNumber(); currNumber++)
			do {
				char curChar = fileBuffer.getChar();
				if (curChar == 0) {
					String name = sb.toString().toUpperCase();
					sb.setLength(0);
					
					ZPAKResourceMetaInfo item = new ZPAKResourceMetaInfo();
					
					item.offset = fileBuffer.getInt();
					item.itemSize = fileBuffer.getInt();
					item.itemZSize = fileBuffer.getInt();

					boolean exists = resources.put(name, item) != null;
					if (exists) {
						log.warn("Duplicate found for '{}' in {}", name, mediaFile.getAbsolutePath());
					}
					continue mainLoop;
				}
				sb.append(curChar);
			} while (true);
		
		long totalTime = System.currentTimeMillis() - startTime;
		log.debug("Total Resources Load Time: {} ms", totalTime);
		
	}

	public byte[] loadMediaResource(String name) {
		name = name.toUpperCase();
		ZPAKResourceMetaInfo offset = resources.get(name);
		if (offset == null) {
			log.info("Requested item '{}' is not found", name);
			return null;
		}
		byte compData[] = new byte[offset.itemZSize];
		fileBuffer.position(offset.offset);
		fileBuffer.get(compData);
		if (offset.itemSize == offset.itemZSize){
			log.trace("The resource is not zipped");
			return compData;
		}
		
		byte[] decompData = null;
		
		try {
			decompData = readBytesFromStream(new InflaterInputStream(new ByteArrayInputStream(compData)), offset.itemSize);
		} catch (IOException e) {
			log.error("Error", e);
		}
			
		return decompData;
	}

	public boolean isResourceAvailble(String itemName) {
		return this.resources.get(itemName) == null ? false : true;
	}
	
	public String getFilePath() {
		return mediaFile.getAbsolutePath();
	}
	
	private byte[] readBytesFromStream(InputStream is, int bytesNumber) throws IOException {
		byte[] readData = new byte[bytesNumber];
		int readBytesNumber = 0;
		while (readBytesNumber < bytesNumber) {
			readBytesNumber += is.read(readData, readBytesNumber, readData.length - readBytesNumber);
		}
		is.close();
		return readData;
	}

	public Set<String> getResourceKeys() {
		return this.resources.keySet();
	}

	
	private class ZPAKResourceMetaInfo {
		int offset;
		int itemSize;
		int itemZSize;
	}
	
}

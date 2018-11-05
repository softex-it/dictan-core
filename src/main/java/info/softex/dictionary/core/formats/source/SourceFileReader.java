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
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.source.utils.SourceReaderUtils;
import info.softex.dictionary.core.io.BufferedRandomAccessFile;
import info.softex.dictionary.core.utils.FileConversionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.2,		03/07/2014
 *
 * @modified version 4.6,	01/27/2015
 * @modified version 4.7,	03/23/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class SourceFileReader {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	protected final static int INIT_LIST_SIZE = 10000;
	protected byte[] contentBuffer = null; 
	
	protected final File file;
	protected final BufferedRandomAccessFile raf;
	protected final FileChannel fileChannel;
	protected final long fileSize;
	
	protected final List<String> lineKeys;
	protected final List<Long> linePointers;
	protected Map<String, Integer> lineMapper;
	
	public SourceFileReader(File inFile, int inBufferSize) throws IOException {
		
		// Check BOM to see the encoding is UTF-8 or undefined	
		FileConversionUtils.verifyUnicodeEncodingUndefinedOrUTF8(inFile);
		
		this.file = inFile;
		this.raf = new BufferedRandomAccessFile(file, "r");
		this.fileChannel = raf.getChannel();
		this.fileSize = file.length();
		
		this.contentBuffer = new byte[inBufferSize];
		
		this.lineKeys = new ArrayList<String>(INIT_LIST_SIZE);
		this.linePointers = new ArrayList<Long>(INIT_LIST_SIZE);
		
	}
	
	public void load(boolean isMapperActive) throws IOException {
		
		SourceReaderUtils.loadSourceKeys(raf, lineKeys, linePointers);
		
		if (isMapperActive) {
			lineMapper = new LinkedHashMap<String, Integer>();
			int entriesRemoved = 0;
			for (int i = 0; i < lineKeys.size(); i++) {
				String key = lineKeys.get(i);
				// Duplicates will be removed when working with the index mapper
				// Though give the priority to the entries that were read first
				if (!lineMapper.containsKey(key)) {
					lineMapper.put(key, i);
				} else {
					log.info("The entry {} (index {}) is already in the line map, ignoring it.", key, i);
					entriesRemoved++;
				}
			}
			
			log.info("Total number of removed duplicate entries: {}", entriesRemoved);
			
		}
		
	}

	public boolean readLine(KeyValueInfo<String, String> inKeyValueInfo, String key) throws BaseFormatException {
		Integer pointer = getLineMapper().get(key);
		if (pointer == null) {
			return false;
		}
		return readLine(inKeyValueInfo, pointer);
	}
	
	public boolean readLine(KeyValueInfo<String, String> inKeyValueInfo, int inLineNum) throws BaseFormatException {
		
		int nextLineNum = inLineNum + 1;
		int lineByteLength = 0; 
		
		if (nextLineNum < linePointers.size()) {
			lineByteLength = (int) (linePointers.get(nextLineNum) - linePointers.get(inLineNum));
		} else {
			lineByteLength = (int) (fileSize - linePointers.get(inLineNum));
		}
		
		if (lineByteLength > contentBuffer.length) {
			contentBuffer = new byte[lineByteLength];
			log.warn("Read buffer is extended to: {}", lineByteLength);
		}

		String line = null;
		
		try {
			line = SourceReaderUtils.readLineUTF(fileChannel, linePointers.get(inLineNum), lineByteLength, contentBuffer);
		} catch (IOException e) {
			log.error("Error", e);
			throw new BaseFormatException(e.getMessage());
		}
		
		if (line == null) {
			throw new BaseFormatException("Line " + inLineNum + " could not be read");
		}
		
		return SourceReaderUtils.parseSourceLine(inKeyValueInfo, line);
		
	}

	public File getFile() {
		return file;
	}

	public long getFileSize() {
		return fileSize;
	}

	public List<Long> getLinePointers() {
		return linePointers;
	}
	
	public List<String> getLineKeys() {
		return lineKeys;
	}
	
	public Map<String, Integer> getLineMapper() {
		return lineMapper;
	}
	
	public void close() throws IOException {
		if (raf != null) {
			raf.close();
		}
	}
	
}

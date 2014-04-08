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

package info.softex.dictionary.core.formats.source;

import info.softex.dictionary.core.attributes.KeyValueInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.source.utils.SourceReaderUtils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.2, 03/07/2014
 * 
 * @author Dmitry Viktorov
 *
 */
public class SourceFileReader {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final int initialListSize = 10000;
	private byte[] contentBuffer = null; 
	
	private final File file;
	private final BufferedRandomAccessFile raf;
	private final FileChannel fileChannel;
	private final long fileSize;
	
	private final List<String> lineKeys;
	private final List<Long> linePointers;
	private Map<String, Integer> lineMapper;
	
	public SourceFileReader(File inFile, int inBufferSize) throws IOException {
		
		this.file = inFile;
		this.raf = new BufferedRandomAccessFile(file, "r");
		this.fileChannel = raf.getChannel();
		this.fileSize = file.length();
		
		this.contentBuffer = new byte[inBufferSize];
		
		this.lineKeys = new ArrayList<String>(initialListSize);
		this.linePointers = new ArrayList<Long>(initialListSize);
		
	}
	
	public void load(boolean isMapperActive) throws IOException {
		
		SourceReaderUtils.loadSourceKeys(raf, lineKeys, linePointers);
		
		if (isMapperActive) {
			lineMapper = new HashMap<String, Integer>();
			for (int i = 0; i < lineKeys.size(); i++) {
				lineMapper.put(lineKeys.get(i), i);
			}
		}
		
	}

	public boolean readLine(KeyValueInfo<String> inKeyValueInfo, String key) throws BaseFormatException {
		Integer pointer = getLineMapper().get(key);
		if (pointer == null) {
			return false;
		}
		return readLine(inKeyValueInfo, pointer);
	}
	
	public boolean readLine(KeyValueInfo<String> inKeyValueInfo, int inLineNum) throws BaseFormatException {
		
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

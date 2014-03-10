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

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.2, 03/07/2014
 * 
 * @author Dmitry Viktorov
 *
 */
public class SourceReaderUtils {
	
	public static final String UTF8 = "UTF-8";
	public static final String SOURCE_DELIMITER = "  ";
	
	private static final Logger log = LoggerFactory.getLogger(SourceReaderUtils.class);
	
	/**
	 * Parses a line from SOURCE
	 * 
	 * @param inLine
	 * @param inKeyValueInfo
	 * @return 
	 *			true - if the KeyValueInfo has been populated
	 *			false - if the string couldn't be parsed, so the KeyValueInfo is untouched
	 */
	public static <T extends KeyValueInfo<String>> boolean parseSourceLine(T inKeyValueInfo, String inLine) {
		
		if (inLine != null) {
			
			inLine = inLine.trim();
			
			if (inLine.length() != 0) {
				
				int delimPosition = inLine.indexOf(SOURCE_DELIMITER);
				
				if (delimPosition > 0) {
					
					// Substring called directly doesn't cut the value char array
					String key = new String(inLine.substring(0, delimPosition).trim().toCharArray());
					if (key.length() > 0) {
						
						// Substring called directly doesn't cut the value char array
						String value = new String(inLine.substring(delimPosition).trim().toCharArray());
						inKeyValueInfo.setKey(key);
						inKeyValueInfo.setValue(value);
						return true;
						
					}
					
				}
				
			}
			
		}
		
		return false;
		
		//log.info("Source | Get raw article time: {}", System.currentTimeMillis() - t1);
		
	}
	
	public static void loadSourceKeys(BufferedRandomAccessFile raf, List<String> keys, List<Long> pointerData) throws IOException {
		
		int i = 0;
		int m = 0;
		
		pointerData.add(raf.getFilePointer());
		
		// Line container
		SourceKeyValueInfo kvInfo = new SourceKeyValueInfo();
		
		String line = null;
		while ((line = readLineUTF(raf)) != null) {
			
//			if (line.length() > 500000) {
//				log.info("Long line found! Key: {}, Length: {}", key, line.length());
//			}
			
			// Clear the key
			kvInfo.setKey(null);
			
			if (parseSourceLine(kvInfo, line)) {
				
				i++;
				m += kvInfo.getKey().length();

				if (i % 10000 == 0) {
					log.info("I " + i + ", M " + m + ", P " + raf.getFilePointer());
				}
				
				keys.add(kvInfo.getKey());
				pointerData.add(raf.getFilePointer());
				
			} else {
				log.warn("Article line is empty or inconsistent #{}, skipping: {}", i, line);
			}

		}
		
	}
	
	public static String readLineUTF(FileChannel fc, long start, int length, byte[] contentBuffer) throws IOException {
		
		MappedByteBuffer articleBuffer = fc.map(MapMode.READ_ONLY, start, length);
		
		//System.out.println(start + " | " + length);
		
		articleBuffer.load().get(contentBuffer, 0, length);

		return new String(contentBuffer, 0, length, UTF8);
		
	}
	
	/**
	 * It's important to return string buffer because the String transferred 
	 * to StringBuffer by the compiler may cause OOM in the loop.
	 * 
	 * @param raf
	 * @return
	 * @throws IOException
	 */
	private static String readLineUTF(BufferedRandomAccessFile raf) throws IOException {

		byte[] lineBuffer = raf.readLineBuffer();
		if (lineBuffer == null) {
			return null;
		}

		return new String(lineBuffer, UTF8);
		
	}

}

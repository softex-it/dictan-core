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

package info.softex.dictionary.core.utils;

import info.softex.dictionary.core.io.UnicodeInputStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @since version 4.5, 03/30/2014
 * 
 * @modified version 4.6, 02/01/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FileUtils {
	
	protected static final String UTF8 = "UTF-8";
	
	public final static String EXT_HTML = ".html";
	public final static String EXT_HTM = ".htm";
	
	@SuppressWarnings("serial")
	private final static Map<String, String> FILE_NAME_REPLACEMENTS = new HashMap<String, String>() {{
		put("\"", "&#34;");
		put("\\*", "&#42;");
		put("/", "&#47;");
		put(":", "&#58;");
		put("<", "&#60;");
		put(">", "&#62;");
		put("\\?", "&#63;");
		put("\\\\", "&#92;");
		put("\\|", "&#124;");
	}};
	
	public static File string2File(String filePath, String content) throws IOException {
		File outFile = new File(filePath);
		Writer writer = createWriter(outFile);
		writer.write(content);
		writer.flush();
		writer.close();
		return outFile;
	}
	
	public static String file2String(File file) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public static BufferedWriter createWriter(File file) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF8));
	}
	
	public static String title2FileName(String title) {
		for (Map.Entry<String, String> repEntry : FILE_NAME_REPLACEMENTS.entrySet()) {
			title = title.replaceAll(repEntry.getKey(), repEntry.getValue());
		}
		return title.trim() + EXT_HTML;
	}
	
	public static String fileName2Title(String fileName) {
		for (Map.Entry<String, String> repEntry : FILE_NAME_REPLACEMENTS.entrySet()) {
			fileName = fileName.replaceAll(repEntry.getValue(), repEntry.getKey());
		}
		String lcFileName = fileName.toLowerCase();
		if (lcFileName.endsWith(EXT_HTML)) {
			return fileName.substring(0, fileName.length() - EXT_HTML.length()).trim();
		} else if (lcFileName.endsWith(EXT_HTM)) {
			return fileName.substring(0, fileName.length() - EXT_HTM.length()).trim();
		} else {
			return fileName.trim();
		}
	}
	
	/**
	 * Verifies the Unicode encoding of a file is UTF8 or undefined. Otherwise throws exception.
	 * 
	 * @param file - File to verify
	 * @throws IOException - If Unicode encoding is defined and it's not UTF8
	 */
	public static void verifyUnicodeEncodingUndefinedOrUTF8(File file) throws IOException {
		
		// Check BOM to see the encoding is UTF-8 or undefined (supposed to be UTF-8)
		UnicodeInputStream uis = new UnicodeInputStream(new FileInputStream(file));
		uis.close();
		if (!uis.isUnicodeEncodingUndefinedOrUTF8()) {
			throw new IOException("Only UTF-8 encoding is supported. The detected encoding is " + 
				uis.getUnicodeEncoding() + ". Please consider the iconv utility to convert it to UTF-8.");
		}
		
	}

}

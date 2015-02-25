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

package info.softex.dictionary.core.formats.dsl;

import info.softex.dictionary.core.attributes.KeyValueInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.dsl.utils.DSLReaderUtils;
import info.softex.dictionary.core.formats.source.SourceFileNames;
import info.softex.dictionary.core.io.TextLineReader;
import info.softex.dictionary.core.utils.FileConversionUtils;
import info.softex.dictionary.core.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * File reader for the DSL format developed by ABBYY Lingvo. It's used
 * separately for the articles and abbreviations to open a DSL base.
 * 
 * @since version 4.6, 01/26/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseReadUnit {
	
	private final static int INIT_LIST_SIZE = 10000;
	private final static String UTF8 = "UTF-8";
	
	private final static Logger log = LoggerFactory.getLogger(DSLBaseReadUnit.class.getName());

	protected List<String> headers;

	protected final TextLineReader tlr;

	protected List<String> words;
	protected final List<Long> linePointers;
	protected Map<String, Integer> indexMapper;

	protected final TreeMap<Integer, String> wordsMappings = new TreeMap<Integer, String>();
	protected final TreeMap<Integer, Integer> wordsRedirects = new TreeMap<Integer, Integer>();
	
	protected final String sourceDir;
	protected final File descFile;
	protected final File iconFile;
	
	public DSLBaseReadUnit(String sourceDirPath, String nameBasis) throws IOException {
		
		this.sourceDir = sourceDirPath;
		
		// Source file: Check BOM to see the encoding is UTF-8 or undefined
		File sourceFile = new File(sourceDirPath + File.separator + nameBasis + SourceFileNames.FILE_DSL_EXT_MAIN);
		FileConversionUtils.verifyUnicodeEncodingUndefinedOrUTF8(sourceFile);
		
		// Description file: Check it exists and if yes, check it's UTF-8
		File tempDescFile = new File(sourceDirPath + File.separator + nameBasis + SourceFileNames.FILE_DSL_EXT_DESCRIPTION);
		if (tempDescFile.exists() && tempDescFile.isFile()) {
			FileConversionUtils.verifyUnicodeEncodingUndefinedOrUTF8(tempDescFile);
			descFile = tempDescFile;
		} else {
			descFile = null;
			log.info("DSL description file is not found: {}", tempDescFile);
		}
		
		// Icon file: Check if it exists
		File tempIconFile = new File(sourceDirPath + File.separator + nameBasis + SourceFileNames.FILE_DSL_EXT_ICON);
		if (tempIconFile.exists() && tempIconFile.isFile()) {
			iconFile = tempIconFile;
		} else {
			iconFile = null;
			log.info("DSL icon file is not found: {}", tempIconFile);
		}
		
		this.tlr = new TextLineReader(sourceFile, new TextLineReader.InitialBomRemover());
		this.linePointers = new ArrayList<Long>(INIT_LIST_SIZE);

	}

	public void load(boolean isMapperActive) throws IOException, BaseFormatException {

		// Iterate via any empty lines
		tlr.readEmptyLines("Epmty line found at the beggining of file. Please consider removing it.");

		headers = DSLReaderUtils.loadDSLHeaders(tlr);

		// Headers have passed, so load words
		readWords(isMapperActive);

		// Load is completed, set load read mode to false
		tlr.setLoadReadMode(false);

	}

	protected void readWords(boolean isMapperActive) throws IOException, BaseFormatException {

		words = DSLReaderUtils.readDSLWords(tlr, linePointers, wordsRedirects, wordsMappings);

		if (isMapperActive) {
			indexMapper = new HashMap<String, Integer>();
			for (int i = 0; i < words.size(); i++) {
				indexMapper.put(words.get(i), i);
			}
		}

	}

	public List<String> getDSLHeaders() {
		return headers;
	}
	
	public String getDSLHeadersAsString() {
		String result = null;
		List<String> headers = getDSLHeaders();
		if (headers != null && !headers.isEmpty()) {
			String strHeaders = "";
			for (String head : headers) {
				strHeaders += head.trim() + "\r\n";
			}
			result = strHeaders.trim();
		}
		return result;
	}

	public boolean readEntry(KeyValueInfo<String, String> inKeyValueInfo, int entryId) throws BaseFormatException {

		if (entryId < 0) {
			throw new IllegalArgumentException("EntryId must be greate or equal to 0");
		}
		
		String entry = null;
		long pointer = linePointers.get(entryId);

		try {

			tlr.readLine(pointer);
			entry = DSLReaderUtils.readDSLArticle(tlr, entryId, linePointers, words, wordsRedirects, wordsMappings);
			
			if (entry == null) {
				throw new BaseFormatException("Entry " + entryId + " could not be read");
			}
			
			inKeyValueInfo.setKey(words.get(entryId));
			inKeyValueInfo.setValue(entry);

		} catch (IOException e) {
			throw new BaseFormatException(
				"Couldn't read the entry for word ID " + entryId + 
				". Article: " + entry + ". Error: " + e.getMessage());
		}

		return true;

	}
	
	public boolean readEntry(KeyValueInfo<String, String> inKeyValueInfo, String key) throws BaseFormatException {
		Integer pointer = getLineMapper().get(key);
		if (pointer == null) {
			return false;
		}
		return readEntry(inKeyValueInfo, pointer);
	}

	public List<String> getWords() {
		return words;
	}
	
	public TreeMap<Integer, String> getWordsMappings() {
		return wordsMappings;
	}
	
	public TreeMap<Integer, Integer> getWordRedirects() {
		return wordsRedirects;
	}
	
	public List<Long> getLinePointers() {
		return linePointers;
	}
	
	public long getLinesRead() {
		return tlr.getLinesRead();
	}

	public void close() throws IOException {
		if (tlr != null) {
			tlr.close();
		}
	}
	
	public Map<String, Integer> getLineMapper() {
		return indexMapper;
	}
	
	public byte[] getDSLIcon() {
		byte[] result = null;
		if (iconFile != null) {
			try {
				// Android doesn't support java.nio.file.Files, so use the common approach
				result = FileUtils.toByteArray(iconFile);
			} catch (IOException e) {
				log.error("Error", e);
			}
		}
		return result;
	}
	
	public String getDSLDescription() {
		String result = null;
		if (descFile != null) {
			try {
				// Android doesn't support java.nio.file.Files, so use the common approach
				result = new String(FileUtils.toByteArray(descFile), UTF8);
			} catch (IOException e) {
				log.error("Error", e);
			}
		}
		return result;
	}

}

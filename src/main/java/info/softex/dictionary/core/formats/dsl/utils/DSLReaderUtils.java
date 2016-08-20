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

package info.softex.dictionary.core.formats.dsl.utils;

import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.io.TextLineReader;
import info.softex.dictionary.core.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Headwords: Allowed characters: letters of alphabets, numbers, spaces, hyphens, commas and braces {} - to mark the unsorted part of the headword.
 * 
 * http://lingvo.helpmax.net/en/troubleshooting/dsl-compiler/inserting-pictures-and-sounds/
 * 
 * Supported image formats: BMP, Bitmaps (*.bmp), PCX (*.pcx), DCX (*.dcx), JPEG (*.jpg), TIFF (*.tif)
 * Supported sound formats: Wave Sound (*.wav)
 * To insert a video in a DSL dictionary entry: [s]movie.avi[/s].
 * 
 * @since version 4.6, 02/01/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLReaderUtils {
	
	protected final static String CHAR_HASH = "#";
	
	private final static Logger log = LoggerFactory.getLogger(DSLReaderUtils.class);
	
	/**
	 * Reads DSL Words. It's supposed to be used at load read mode.
	 */
	public static List<String> readDSLWords(TextLineReader tlr, List<Long> linePointers, 
			TreeMap<Integer, Integer> wordRedirects, TreeMap<Integer, String> wordsExtended) throws IOException, BaseFormatException {
		
		List<String> words = new ArrayList<String>();
		
		// Check if last line could be read
		if (!tlr.isLoadReadMode()) {
			throw new IOException("The reader is supposed to be in load read mode");
		}
		
		// Check if last line could be read
		if (tlr.getLastLine() == null) {
			throw new BaseFormatException("File contains headers but data is not found: " + tlr.getFilePath());
		}
		
		// Last line is never expected empty
		if (tlr.getLastLineTrimmed().isEmpty()) {
			throw new BaseFormatException("After header is read, the last line is never expected empty. There must be an issue in the code.");
		}
		
		do {
			
			linePointers.add(tlr.getLastPointer());
			String curKey = tlr.getLastLineTrimmed();	
			
			// Store the headword
			storeDSLWord(curKey, words, wordsExtended);
			
			int curKeyId = words.size() - 1;
			
			// Read article
			String article = DSLReaderUtils.readDSLArticle(tlr, curKeyId, linePointers, words, wordRedirects, wordsExtended);
			
			// Check if the next word already started
			if (tlr.isLastLineBlank()) {
				tlr.readEmptyLines("Skipping empty lines after the article of the word: " + curKey);	
			}
			
			if (article == null || article.isEmpty()) {
				throw new BaseFormatException("DSL article is not found for the word " + curKey +
					". Line num: " + tlr.getLinesRead() + ", Line content: " + tlr.getLastLine());
			}
				
			if ((words.size() - 1) % 10000 == 0) {
				log.info("DSL Key No: {}, Pointer: {}, Line: {}", words.size() - 1, tlr.getLastPointer(), tlr.getLinesRead());
			}
			
			//linePointers.add(lastPointer);

		} while (tlr.getLastLine() != null);
		
		return words;
		
	}
	
	/**
	 * Reads DSL article. The method differs the initial load and post 
	 * load to populate words and redirects.
	 */
	public static String readDSLArticle(TextLineReader tlr, int wordId, List<Long> linePointers,
			List<String> words, TreeMap<Integer, Integer> wordRedirects, TreeMap<Integer, String> wordsMappings) throws IOException, BaseFormatException {
		
		//System.out.println("Current word: " + tlr.getLastLineTrimmed() + " " + wordId);
		
		// Read word redirects
		DSLReaderUtils.readDSLRedirects(tlr, linePointers, words, wordRedirects, wordsMappings);
		
		String article = tlr.getLastLineLTrimmed() + "\r\n";
		
		while (tlr.readLine() != null && tlr.getLastLine().length() > 0 && Character.isWhitespace(tlr.getLastLine().charAt(0))) {
			article += tlr.getLastLineLTrimmed() + "\r\n";
		}

		article = article.trim();
		if (article.endsWith("\\")) {
			article = article + " ";
		}
		//System.out.println("Article: " + article);
		return article;
		
	}
	
	/**
	 * Reads DSL redirects which go right after the key word.
	 */
	protected static void readDSLRedirects(TextLineReader tlr, List<Long> linePointers,
			List<String> words, TreeMap<Integer, Integer> wordRedirects, TreeMap<Integer, String> wordsMappings) throws IOException {
		
		List<String> redirects = new ArrayList<String>();
		while (tlr.readLine() != null && tlr.isLastLineNotBlank()) {
			if (Character.isWhitespace(tlr.getLastLine().charAt(0))) {
				break;
			} else {
				redirects.add(tlr.getLastLineTrimmed());
				linePointers.add(tlr.getLastPointer());
			}
		}
		
		// Populate words and redirects only if non-load read mode
		if (tlr.isLoadReadMode()) {
			
			int wordId = words.size() - 1;
			
			for (String redirect : redirects) {
				// Store redirected word
				storeDSLWord(redirect, words, wordsMappings);
				int curRedirId = words.size() - 1;
				wordRedirects.put(curRedirId, wordId);
			}
		}

	}
	
	public static List<String> loadDSLHeaders(TextLineReader tlr) throws IOException, BaseFormatException {
		
		List<String> headers = null;
		
		// Check if line could be read
		if (tlr.getLastLine() == null) {
			throw new BaseFormatException("File doesn't contain any data: " + tlr.getFilePath());
		}
		
		// Check if it's a header comment
		if (tlr.getLastLineTrimmed().startsWith(CHAR_HASH)) {
			headers = new ArrayList<String>();
			headers.add(tlr.getLastLine());
			while (tlr.readLine() != null && tlr.getLastLineTrimmed().startsWith(CHAR_HASH)) {
				headers.add(tlr.getLastLine());
			}
			if (tlr.getLastLineTrimmed().isEmpty()) {
				tlr.readEmptyLines("Skipping empty lines after the header");
			}
		}
		
		return headers;
		
	}
	
	protected static void storeDSLWord(String word, List<String> words, TreeMap<Integer, String> wordsMappings) {
		
		if (StringUtils.isBlank(word)) {
			throw new IllegalArgumentException("Word can't be blank");
		}
		
		word = word.trim();
		
		String indexedWord = DSLReadFormatUtils.convertDSLWordToIndexedWord(word);
		words.add(indexedWord);
		
		// If non-indexed parts are found, add it to the words mappings
		if (!word.equals(indexedWord)) {
			wordsMappings.put(words.size() - 1, word);
		} 
	}
	

}

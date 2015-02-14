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

import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.dsl.utils.DSLReaderUtils;
import info.softex.dictionary.core.io.TextLineReader;
import info.softex.dictionary.core.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
public class DSLFileReader {

	protected final static int INIT_LIST_SIZE = 10000;

	protected List<String> headers;

	protected final TextLineReader tlr;

	protected List<String> words;
	protected final List<Long> linePointers;
	protected Map<String, Integer> lineMapper;

	protected final TreeMap<Integer, String> wordsMappings = new TreeMap<Integer, String>();
	protected final TreeMap<Integer, Integer> wordsRedirects = new TreeMap<Integer, Integer>();

	public DSLFileReader(File inFile, int inBufferSize) throws IOException {

		// Check BOM to see the encoding is UTF-8 or undefined
		FileUtils.verifyUnicodeEncodingUndefinedOrUTF8(inFile);

		this.tlr = new TextLineReader(inFile, new TextLineReader.InitialBomRemover());
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
			lineMapper = new HashMap<String, Integer>();
			for (int i = 0; i < words.size(); i++) {
				lineMapper.put(words.get(i), i);
			}
		}

	}

	public List<String> getHeaders() {
		return headers;
	}

	public boolean readArticleInfo(ArticleInfo articleInfo) throws BaseFormatException {

		WordInfo wordInfo = articleInfo.getWordInfo();

		int wordId = wordInfo.getId();

		// Populate word
		wordInfo.setWord(words.get(wordId));

		// Check if the word is redirected
		Integer redirectId = wordsRedirects.get(wordInfo.getId());
		if (redirectId != null) {
			wordId = redirectId;
			wordInfo.setRedirectToId(redirectId);
		}

		//System.out.println(wordInfo.getId() + " | " + wordId);

		long pointer = linePointers.get(wordId);

		try {

			tlr.readLine(pointer);

			// System.out.println("ART: " + article + " W " + tlr.getLastLine());

			String article = DSLReaderUtils.readDSLArticle(tlr, wordId, linePointers, words, wordsRedirects, wordsMappings);
			articleInfo.setArticle(article);

		} catch (IOException e) {
			throw new BaseFormatException(
				"Couldn't read the article for word ID " + wordId
					+ ". Article: " + articleInfo + ". Error: " + e.getMessage());
		}

		//System.out.println("Pointer: " + pointer);

		return true;

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

}

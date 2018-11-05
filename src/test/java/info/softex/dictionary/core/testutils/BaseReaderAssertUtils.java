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

package info.softex.dictionary.core.testutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.MediaResourceKey;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.api.BaseReader;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Main test utilities for assertions.
 * 
 * @since		4.6, 03/01/2015
 * 
 * @modified		5.2, 10/26/2018
 * 
 * @author Dmitry Viktorov
 * 
 */
public class BaseReaderAssertUtils {
	
	protected final static String FDB = "FDB";
	
	/**
	 * The most basic method to check the base integrity.
	 */
	@SuppressWarnings("deprecation")
	public static void assertBaseReaderIntegrity(BaseReader reader) throws BaseFormatException {
		
		assertTrue(reader.isLoaded());
		
		FormatInfo formatInfo = reader.getFormatInfo();
		assertNotNull(formatInfo);
		String formatName = formatInfo.getName();
		assertNotNull(formatName);
		
		BasePropertiesInfo props = reader.getBaseInfo();
		assertNotNull(props);
		
		// Words
		List<String> words = reader.getWords();
		assertNotNull(words);
		assertTrue(words.size() > 0);
		assertEquals(words.size(), props.getWordsNumber());
		
		// Redirects
		Map<Integer, Integer> redirects = reader.getWordsRedirects();
		if (redirects != null) {
			assertEquals(redirects.size(), props.getWordsRelationsNumber());
		} else {
			assertEquals(0, props.getWordsRelationsNumber());
		}
		
		// Articles
		assertEquals(words.size() - redirects.size(), props.getArticlesActualNumber());
		if (formatName.equals(FDB)) {
			assertEquals(words.size(), props.getArticlesDeprecatedNumber());
		}
		
		// Mappings
		Map<Integer, String> mappings = reader.getWordsMappings();
		if (mappings != null) {
			assertEquals(mappings.size(), props.getWordsMappingsNumber());
		} else {
			assertEquals(0, props.getWordsMappingsNumber());
		}
		
		// Abbreviations
		Set<String> abbrevs = reader.getAbbreviationKeys();
		if (abbrevs != null) {
			assertEquals(abbrevs.size(), props.getAbbreviationsNumber());
		} else {
			assertEquals(0, props.getAbbreviationsNumber());
		}
		
		// Media resources
		Set<String> resKeys = reader.getMediaResourceKeys();
		if (resKeys != null) {
			assertEquals(resKeys.size(), props.getMediaResourcesNumber());
		} else {
			assertEquals(0, props.getMediaResourcesNumber());
		}
		
		// Formatting
		assertNotNull(props.getArticlesFormattingModeResolved());
		assertNotNull(props.getAbbreviationsFormattingModeResolved());
		assertNotNull(props.getArticlesFormattingInjectWordModeResolved());

	}
	
	public static void assertMainBasePropertiesEqual(BasePropertiesInfo expected, BasePropertiesInfo actual) throws BaseFormatException {
		
		// Words and articles
		assertEquals("Words", expected.getWordsNumber(), actual.getWordsNumber());		
		assertEquals("Words Mappings", expected.getWordsMappingsNumber(), actual.getWordsMappingsNumber());
		assertEquals("Words Relations", expected.getWordsRelationsNumber(), actual.getWordsRelationsNumber());
		assertEquals("Actual Articles", expected.getArticlesActualNumber(), actual.getArticlesActualNumber());
		
		// Abbreviations
		assertEquals("Abbreviations", expected.getAbbreviationsNumber(), actual.getAbbreviationsNumber());
		
		// Media resources
		assertEquals("Media Resources", expected.getMediaResourcesNumber(), actual.getMediaResourcesNumber());
		
		// Check the numbers of all entries are equal
		assertEquals("All Entries", expected.getWmraNumber(), actual.getWmraNumber());
		
	}
	
	public static void assertBaseWordsEqual(List<String> expected, List<String> actual) throws BaseFormatException {
		assertEquals("Words", expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), actual.get(i));
		}
	}
	
	public static void assertBaseAbbrevsDefsEqual(BaseReader expected, BaseReader actual) throws BaseFormatException {
		Set<String> abbrevsExp = expected.getAbbreviationKeys();
		Set<String> abbrevsAct = actual.getAbbreviationKeys();
		assertEquals("Abbreviations", abbrevsExp.size(), abbrevsAct.size());
		for (String abbrKeyExp: abbrevsExp) {
			assertTrue(abbrevsAct.contains(abbrKeyExp));
			AbbreviationInfo abbrInfoExp = expected.getAbbreviationInfo(abbrKeyExp);
			AbbreviationInfo abbrInfoAct = actual.getAbbreviationInfo(abbrKeyExp);
			assertEquals(abbrInfoExp.getDefinition(), abbrInfoAct.getDefinition());
		}
	}
	
	public static void assertBaseRawArticlesEqual(BaseReader expected, BaseReader actual) throws BaseFormatException {
		List<String> wordsExp = expected.getWords();		
		for (int i = 0; i < wordsExp.size(); i++) {
			ArticleInfo artExp = expected.getRawArticleInfo(new WordInfo(null, i));
			ArticleInfo artAct = actual.getRawArticleInfo(new WordInfo(null, i));
			assertEquals(artExp.getArticle(), artAct.getArticle());
		}
	}
	
	public static void assertWordsMappingsEqual(Map<Integer, String> expected, Map<Integer, String> actual) throws BaseFormatException {
		assertEquals("Words Mappings", expected.size(), actual.size());
		int count = 0;
		for (Integer wordId : expected.keySet()) {
			String mappingExp = expected.get(wordId);
			String mappingAct = actual.get(wordId);
			assertNotNull(mappingExp);
			assertEquals("Words mapping " + count + " for word id " + wordId, mappingExp, mappingAct);
			count++;
		}
	}
	
	public static void assertWordsRedirectsEqual(Map<Integer, Integer> expected, Map<Integer, Integer> actual) throws BaseFormatException {
		assertEquals("Words Redirects", expected.size(), actual.size());
		int count = 0;
		for (Integer wordId : expected.keySet()) {
			Integer redirectExp = expected.get(wordId);
			Integer redirectAct = actual.get(wordId);
			assertNotNull(redirectExp);
			assertNotEquals(wordId, redirectAct); // Word should never redirect to itself
			assertEquals("Words redirect " + count + " for word id " + wordId, redirectExp, redirectAct);
			count++;
		}
	}
	
	public static void assertBaseMediaResourceEqual(BaseReader expected, BaseReader actual) throws BaseFormatException {
		Set<String> resExp = expected.getMediaResourceKeys();
		Set<String> resAct = actual.getMediaResourceKeys();
		assertEquals("Abbreviations", resExp.size(), resAct.size());
		for (String resKeyExp: resExp) {
			assertTrue(resAct.contains(resKeyExp));
			MediaResourceInfo resInfoExp = expected.getMediaResourceInfo(new MediaResourceKey(null, resKeyExp));
			MediaResourceInfo resInfoAct = actual.getMediaResourceInfo(new MediaResourceKey(null, resKeyExp));
			assertEquals(resInfoExp.getByteArray().length, resInfoAct.getByteArray().length);
		}
	}
	
	public static void assertMainBaseReaderParametersEqualByProperties(BasePropertiesInfo propsExp, BaseReader readerAct) throws BaseFormatException {
		
		assertBaseReaderIntegrity(readerAct);
		
		// Main Properties
		BasePropertiesInfo propsAct = readerAct.getBaseInfo();
		assertMainBasePropertiesEqual(propsExp, propsAct);

		// Words
		List<String> words = readerAct.getWords();
		assertNotNull(words);
		assertEquals(propsExp.getWordsNumber(), words.size());
		
		// Mappings
		Map<Integer, String> mappings = readerAct.getAdaptedWordsMappings();
		assertNotNull(mappings);
		assertEquals(propsExp.getWordsMappingsNumber(), mappings.size());
		
		// Redirects
		Map<Integer, Integer> redirects = readerAct.getWordsRedirects();
		assertNotNull(redirects);
		assertEquals(propsExp.getWordsRelationsNumber(), redirects.size());

		// Abbreviations
		Set<String> abbrevs = readerAct.getAbbreviationKeys();
		assertEquals(propsExp.getAbbreviationsNumber(), abbrevs.size());

		// Media Resources
		Set<String> mediaRes = readerAct.getMediaResourceKeys();
		assertEquals(propsExp.getMediaResourcesNumber(), mediaRes.size());
		
		// Check all words are defined
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			assertNotNull(word);
			assertFalse(word.isEmpty());
		}
		
		// Check all abbreviations are defined
		for (String abbr : abbrevs) {
			assertNotNull(abbr);
			assertFalse(abbr.isEmpty());
		}

		// Check all media resources are defined
		for (String res : mediaRes) {
			assertNotNull(res);
			assertFalse(res.isEmpty());
		}

	}
	
	/**
	 * Checks if all main bases parameters are equal.
	 * It checks the integrity of the bases, equality of the base properties 
	 * and their consistency with the reader.
	 */
	public static void assertMainBaseReaderParametersEqual(BaseReader expected, BaseReader actual) throws BaseFormatException {
		
		assertMainBaseReaderParametersEqualByProperties(expected.getBaseInfo(), actual);
		
		// Words
		assertEquals("Number of words", expected.getWords().size(), actual.getWords().size());
		assertEquals("Number of words mappings", expected.getWordsMappings().size(), actual.getWordsMappings().size());
		assertEquals("Number of words redirects", expected.getWordsRedirects().size(), actual.getWordsRedirects().size());
		
		// Mappings
		Map<Integer, String> mappings = expected.getAdaptedWordsMappings();
		if (mappings != null) {
			assertEquals(mappings.size(), actual.getAdaptedWordsMappings());
		} else {
			assertEquals(0, actual.getAdaptedWordsMappings());
		}
		
		// Redirects
		Map<Integer, Integer> redirects = expected.getWordsRedirects();
		if (redirects != null) {
			assertEquals(redirects.size(), actual.getWordsRedirects().size());
		} else {
			assertEquals(0, actual.getWordsRedirects().size());
		}
		
		// Abbreviations
		Set<String> abbrKeysExp = expected.getAbbreviationKeys();
		if (abbrKeysExp != null) {
			assertEquals(abbrKeysExp.size(), actual.getAbbreviationKeys().size());
		} else {
			assertEquals(0, actual.getAbbreviationKeys().size());
		}
		
		// Media Resources
		Set<String> mediaRes = expected.getMediaResourceKeys();
		if (mediaRes != null) {
			assertEquals(mediaRes.size(), actual.getMediaResourceKeys().size());
		} else {
			assertEquals(0, actual.getMediaResourceKeys().size());
		}
		
	}
	
	public static void assertFullBaseReaderParametersEqual(BaseReader expected, BaseReader actual) throws BaseFormatException {

		assertMainBaseReaderParametersEqual(expected, actual);
		
		List<String> wordsExp = expected.getWords();
		List<String> wordsAct = actual.getWords();
		
		// Assert words are equal
		assertBaseWordsEqual(wordsExp, wordsAct);
		
		// Assert articles are equal
		assertBaseRawArticlesEqual(expected, actual);

		// Assert mappings are equal
		assertWordsMappingsEqual(expected.getWordsMappings(), actual.getWordsMappings());
		
		// Assert redirects are equal
		assertWordsRedirectsEqual(expected.getWordsRedirects(), actual.getWordsRedirects());
		
		// Assert words are equal
		assertBaseAbbrevsDefsEqual(expected, actual);
		
		// Assert media resources equal
		assertBaseMediaResourceEqual(expected, actual);
		
	}
	
}

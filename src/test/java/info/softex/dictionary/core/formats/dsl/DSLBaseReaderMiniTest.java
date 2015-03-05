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

import static info.softex.dictionary.core.formats.dsl.testutils.content.DSLBaseMiniContent.PROPS_MINI_ORIG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.BaseResourceKey;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseAssertUtils;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.formats.dsl.testutils.content.DSLBaseMiniContent;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Test for main functionality of DSL base reader.
 * 
 * @since version 4.6, 01/27/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseReaderMiniTest {
	
	@Test
	public void testDSLBaseReader() throws Exception {
	
		DSLBaseReaderWrapper reader = DSLBaseMiniContent.createAndAssertMiniDSLBaseReader();
		
		DSLBaseAssertUtils.assertMainDSLBaseReaderParametersEqualByProperties(PROPS_MINI_ORIG, reader, false, false);
		
		List<String> words = reader.getWords();
		List<Long> pointers = reader.getLinePointers();
		
		// Check the number of words and pointers is the same
		assertEquals(PROPS_MINI_ORIG.getWordsNumber(), pointers.size());
		
		// Check all lines are read
		assertEquals(DSLBaseMiniContent.LINES_NUMBER, reader.getLinesRead());
		
		// Check redirects
		Map<Integer, Integer> redirectsExp  = DSLBaseMiniContent.REDIRECTS;
		Map<Integer, Integer> redirectsAct = reader.getWordsRedirects();
		assertEquals(PROPS_MINI_ORIG.getWordsRelationsNumber(), redirectsExp.size());
		assertEquals(PROPS_MINI_ORIG.getWordsRelationsNumber(), redirectsAct.size());
		
		for (Integer wordId : redirectsAct.keySet()) {
			assertEquals(redirectsExp.get(wordId), redirectsAct.get(wordId));
		}
		
		// Check abbreviations
		Set<String> abbrevs = reader.getAbbreviationKeys();
		assertEquals(PROPS_MINI_ORIG.getAbbreviationsNumber(), abbrevs.size());
		
		// Check words match the expected words
		int countWords = 0;
		for (String word : DSLBaseMiniContent.WORDS_ARTICLES.keySet()) {
			assertEquals(word, words.get(countWords));			
			countWords++;
		}
		
		// Check articles match the expected articles
		int wordsCount = 0;
		for (String wordExpected : DSLBaseMiniContent.WORDS_ARTICLES.keySet()) {
			ArticleInfo articleInfoRead = reader.getRawArticleInfo(new WordInfo(wordsCount));
			String articleExpected = DSLBaseMiniContent.WORDS_ARTICLES.get(wordExpected);
			
			assertNotNull(articleInfoRead.getWordInfo().getWord());
			assertNotNull(articleInfoRead.getArticle());
			
			assertEquals("Word for word '" + words.get(wordsCount) + "' with id " + wordsCount + " doesn't match.", 
				wordExpected, articleInfoRead.getWordInfo().getWord());
			
			assertEquals("Article for word '" + words.get(wordsCount) + "' with id " + wordsCount + " doesn't match.", 
				articleExpected, articleInfoRead.getArticle());	
			
			wordsCount++;
		}
		
		// Check Base Resources
		BaseResourceInfo articleHeaders = reader.getBaseResourceInfo(BaseResourceKey.BASE_ARTICLES_META_DSL.getKey());
		assertEquals(DSLBaseMiniContent.DSL_ARTICLE_BASE_HEADER, new String(articleHeaders.getInfo1()));
		
	}
		
}

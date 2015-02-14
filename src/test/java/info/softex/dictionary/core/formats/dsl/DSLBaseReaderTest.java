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

import static org.junit.Assert.assertEquals;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderFactory;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseSyntaxContent;

import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Test for main functionality of DSL base reader.
 * 
 * @since version 4.6, 01/27/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseReaderTest {
	
	@Test
	public void testDSLBaseReader() throws Exception {
	
		DSLBaseReaderWrapper reader = DSLBaseReaderFactory.createAndAssertSyntaxDSLBaseReader();
		
		List<String> words = reader.getWords();
		List<Long> pointers = reader.getLinePointers();
		Map<Integer, Integer> redirects = reader.getWordsRedirects();
		
		//System.out.println(words);
		//System.out.println("Redirects: " + redirects);
		
		BasePropertiesInfo baseInfo = reader.getBasePropertiesInfo();
		assertEquals(DSLBaseSyntaxContent.DSL_FULL_BASE_HEADER, baseInfo.getHeaderComments());
		
		// Check the number of words and pointers is the same
		assertEquals(DSLBaseSyntaxContent.WORDS_NUMBER, DSLBaseSyntaxContent.WORDS_NUMBER);
		assertEquals(DSLBaseSyntaxContent.WORDS_NUMBER, words.size());
		assertEquals(DSLBaseSyntaxContent.WORDS_NUMBER, pointers.size());
		
		// Check number of articles
		assertEquals(DSLBaseSyntaxContent.WORDS_NUMBER, baseInfo.getWordsNumber());
		assertEquals(DSLBaseSyntaxContent.ARTICLES_NUMBER, baseInfo.getArticlesActualNumber());
		
		// Check all lines are read
		assertEquals(DSLBaseSyntaxContent.LINES_NUMBER, reader.getLinesRead());
		
		// Check redirects
		Map<Integer, Integer> expectedRedirects = DSLBaseSyntaxContent.REDIRECTS;
		assertEquals(DSLBaseSyntaxContent.REDIRECTS_NUMBER, expectedRedirects.size());
		assertEquals(DSLBaseSyntaxContent.REDIRECTS_NUMBER, redirects.size());
		
		for (Integer wordId : redirects.keySet()) {
			assertEquals(expectedRedirects.get(wordId), redirects.get(wordId));
		}
		
		// Check words match the expected words
		int countWords = 0;
		for (String word : DSLBaseSyntaxContent.WORDS_ARTICLES.keySet()) {
			assertEquals(word , words.get(countWords));			
			countWords++;
		}
		
		// Check articles match the expected articles
		int countArt = 0;
		for (String article : DSLBaseSyntaxContent.WORDS_ARTICLES.values()) {
			//System.out.println("Checking " + words.get(countArt) + " " + countArt);
			assertEquals("Article for word " + words.get(countArt) + " with id " + countArt + " doesn't match.", article , reader.getRawArticleInfo(new WordInfo(countArt)).getArticle());			
			countArt++;
		}
		
	}
	
	@Test
	public void testDSLToFDBConversion() throws Exception {
		
	}
		
}

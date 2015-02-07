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
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseFullContent;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderTestFactory;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;

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
	
	private final static String PATH_BASE_DSL_FULL = "/info/softex/dictionary/core/formats/dsl/bases/syntax";
	
	@Test
	public void testDSLBaseReader() throws Exception {
	
		DSLBaseReaderWrapper reader = DSLBaseReaderTestFactory.createAndAssertDSLBaseReader(PATH_BASE_DSL_FULL);
		
		List<String> words = reader.getWords();
		List<Long> pointers = reader.getLinePointers();
		Map<Integer, Integer> redirects = reader.getWordRedirects();
		
		System.out.println(words);
		System.out.println("Redirects: " + redirects);
		
		BasePropertiesInfo baseInfo = reader.getBasePropertiesInfo();
		assertEquals(DSLBaseFullContent.DSL_FULL_BASE_HEADER, baseInfo.getHeaderComments());
		
		// Check the number of words and pointers is the same
		assertEquals(DSLBaseFullContent.DSL_FULL_BASE_WORDS_NUMBER, words.size());
		assertEquals(DSLBaseFullContent.DSL_FULL_BASE_WORDS_NUMBER, pointers.size());

		// Check all lines are read
		assertEquals(DSLBaseFullContent.DSL_FULL_BASE_LINES_NUMBER, reader.getLinesRead());
		
		// Check redirects
		Map<Integer, Integer> expectedRedirects = DSLBaseFullContent.DSL_FULL_BASE_REDIRECTS;
		assertEquals(expectedRedirects.size(), redirects.size());
		
		for (Integer wordId : redirects.keySet()) {
			assertEquals(expectedRedirects.get(wordId), redirects.get(wordId));
		}
		
		// Check words match the expected words
		int countWords = 0;
		for (String word : DSLBaseFullContent.DSL_FULL_BASE_WORDS.keySet()) {
			assertEquals(word , words.get(countWords));			
			countWords++;
		}
		
		// Check articles match the expected articles
		int countArt = 0;
		for (String article : DSLBaseFullContent.DSL_FULL_BASE_WORDS.values()) {
			System.out.println("Checking " + words.get(countArt) + " " + countArt);
			assertEquals("Article for word " + words.get(countArt) + " with id " + countArt + " doesn't match.", article , reader.getRawArticleInfo(new WordInfo(countArt)).getArticle());			
			countArt++;
		}
		
	}
		
}

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseLayoutsContent;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderFactory;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.testutils.TestUtils;
import info.softex.dictionary.core.utils.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.6, 02/03/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLFormatUtilsTest {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Test
	public void testDSLFormatting() throws Exception {
		
		DSLBaseReaderWrapper readerOrig = DSLBaseReaderFactory.createAndAssertLayoutsDSLBaseReader();
		DSLBaseReaderWrapper readerAdapted = DSLBaseReaderFactory.createAndAssertLayoutsAdaptedDSLBaseReader();
		
		List<String> wordsOrig = readerOrig.getWords();
		List<String> wordsAdapted = readerAdapted.getWords();
		
		log.info("Number of words. Original: {}. Adapted: {}", wordsOrig.size(), wordsAdapted.size());
		
		// Verify readers have the same words
		assertEquals(DSLBaseLayoutsContent.WORDS_NUMBER, wordsOrig.size());
		assertEquals(DSLBaseLayoutsContent.WORDS_NUMBER, wordsAdapted.size());
		assertEquals(wordsOrig, wordsAdapted);
		
		// Verify readers have the same redirects
		Map<Integer, Integer> redirectsOrig = readerOrig.getWordsRedirects();
		Map<Integer, Integer> redirectsAdapted = readerAdapted.getWordsRedirects();		
		assertEquals(DSLBaseLayoutsContent.REDIRECTS_NUMBER, redirectsOrig.size());
		assertEquals(DSLBaseLayoutsContent.REDIRECTS_NUMBER, redirectsAdapted.size());
		assertEquals(redirectsOrig, redirectsAdapted);
		
		for (int i = 0; i < wordsOrig.size(); i++) {
			ArticleInfo artInfoOrig = readerOrig.getRawArticleInfo(new WordInfo(i));
			ArticleInfo artInfoAdapted = readerAdapted.getRawArticleInfo(new WordInfo(i));
			processTestArticle(
				artInfoOrig.getWordInfo().getWord(), artInfoOrig.getArticle(),
				artInfoAdapted.getWordInfo().getWord(), artInfoAdapted.getArticle()
			);
		}
		
	}
	
	private void processTestArticle(final String word, final String article,
			final String wordAdapted, final String articleAdaptedExpected) throws IOException {
		
		assertEquals(word, wordAdapted);
		
		String articleAdaptedActual = DSLReadFormatUtils.convertDSLToAdaptedHtml(article);
		assertFalse(StringUtils.isBlank(articleAdaptedActual));
		
		String dslArticle = DSLWriteFormatUtils.convertAdaptedHtmlToDSL(articleAdaptedActual);
		
		String htmlArticle = DSLReadFormatUtils.convertDSLAdaptedHtmlToHtml(articleAdaptedActual);
		
		//String outArticle = articleAdaptedActual;
		String outArticle = htmlArticle;
		
		Files.write(TestUtils.getMavenTestPath(word + ".html"), outArticle.getBytes());
		
		// Check the original article and the one after adaptation-deadaptation are equal
		assertEquals(article, dslArticle);
		
		// Check the expected and actual adapted articles are the same
		assertEquals(articleAdaptedExpected, articleAdaptedActual);
		
	}

}

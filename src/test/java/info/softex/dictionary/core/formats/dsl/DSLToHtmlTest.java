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

import static info.softex.dictionary.core.formats.dsl.testutils.content.DSLBaseLayoutsContent.PROPS_LAYOUTS_ORIG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.FontInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.formats.dsl.testutils.content.DSLBaseLayoutsContent;
import info.softex.dictionary.core.formats.dsl.utils.DSLWriteFormatUtils;
import info.softex.dictionary.core.testutils.MavenUtils;
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
public class DSLToHtmlTest {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Test
	public void testDSLFormatting() throws Exception {
		
		DSLBaseReaderWrapper readerOrig = DSLBaseLayoutsContent.createAndAssertLayoutsDSLBaseReader();
		DSLBaseReaderWrapper readerAdapted = DSLBaseLayoutsContent.createAndAssertLayoutsAdaptedDSLBaseReader();
		
		List<String> wordsOrig = readerOrig.getWords();
		List<String> wordsAdapted = readerAdapted.getWords();
		
		log.info("Number of words. Original: {}. Adapted: {}", wordsOrig.size(), wordsAdapted.size());
		
		// Verify readers have the same words
		assertEquals(PROPS_LAYOUTS_ORIG.getWordsNumber(), wordsOrig.size());
		assertEquals(PROPS_LAYOUTS_ORIG.getWordsNumber(), wordsAdapted.size());
		assertEquals(wordsOrig, wordsAdapted);
		
		Map<Integer, String> mappingsOrig = readerOrig.getAdaptedWordsMappings();
		Map<Integer, String> mappingsAdapted = readerAdapted.getWordsMappings();
		
		// Check words mappings
		assertEquals(PROPS_LAYOUTS_ORIG.getWordsMappingsNumber(), mappingsOrig.size());
		assertEquals(PROPS_LAYOUTS_ORIG.getWordsMappingsNumber(), mappingsAdapted.size());
		
		for (int i = 0; i < wordsOrig.size(); i++) {
			assertNotNull(wordsOrig.get(i));
			assertEquals(wordsOrig.get(i), wordsAdapted.get(i));
			assertEquals(mappingsOrig.get(i), mappingsAdapted.get(i));	
		}
		
		// Verify readers have the same redirects
		Map<Integer, Integer> redirectsOrig = readerOrig.getWordsRedirects();
		Map<Integer, Integer> redirectsAdapted = readerAdapted.getWordsRedirects();		
		assertEquals(PROPS_LAYOUTS_ORIG.getWordsRelationsNumber(), redirectsOrig.size());
		assertEquals(PROPS_LAYOUTS_ORIG.getWordsRelationsNumber(), redirectsAdapted.size());
		assertEquals(redirectsOrig, redirectsAdapted);
		for (int i = 0; i < wordsOrig.size(); i++) {
			processTestArticle(
				readerOrig.getRawArticleInfo(new WordInfo(null, i)),
				readerOrig.getAdaptedArticleInfo(new WordInfo(null, i)),
				readerAdapted.getRawArticleInfo(new WordInfo(null, i))
			);
		}
		
	}
	
	private void processTestArticle(ArticleInfo origRaw, ArticleInfo origAdapted, ArticleInfo adaptedRaw) throws IOException {
		String origWordRaw = origRaw.getWordInfo().getWord();
		assertNotNull(origWordRaw);
		
		String adaptedWord = adaptedRaw.getWordInfo().getWord();
		assertNotNull(adaptedWord);
		
		assertEquals(origWordRaw, adaptedWord);
		assertEquals(origWordRaw, origAdapted.getWordInfo().getWord());
		
		String origArticleAdapted = origAdapted.getArticle();
		assertTrue(StringUtils.isNotBlank(origArticleAdapted));
		
		// Output the full HTML article (for manual verification)
		String htmlArticle = origAdapted.getFullArticle(new FontInfo(), null, DSLStyles.getDSLStylesForBrowser());
		Files.write(MavenUtils.getMavenTestPath(origWordRaw + ".html"), htmlArticle.getBytes());
		
		// Check the expected and actual adapted articles are the same
		assertEquals(origArticleAdapted, adaptedRaw.getArticle());

		// Check the original article and the one after adaptation-deadaptation are equal
		String dslArticle = DSLWriteFormatUtils.convertAdaptedHtmlToDSL(origArticleAdapted);
		assertEquals(origRaw.getArticle(), dslArticle);
		
		// Check word mapping is the same
		String origRawWord = origRaw.getWordInfo().getWordMapping();
		String origAdaptedWord = origAdapted.getWordInfo().getWordMapping();
		String adaptedMappedWord = adaptedRaw.getWordInfo().getWordMapping();
		
		assertEquals(origAdaptedWord, adaptedMappedWord);
		assertEquals(origRawWord, DSLWriteFormatUtils.convertAdaptedHtmlToDSL(origAdaptedWord));
	
	}

}

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

package info.softex.dictionary.core.formats.fdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.AbbreviationsFormattingMode;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.ArticlesFormattingInjectWordMode;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.ArticlesFormattingMode;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.BasicCollatorFactory;
import info.softex.dictionary.core.database.BasicSQLiteConnectionFactory;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseLayoutsContent;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderFactory;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseIOFactory;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseReaderWrapper;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseWriterWrapper;
import info.softex.dictionary.core.testutils.TestUtils;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.6, 		02/09/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBBaseConversionTest {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected final static String BASE_FILE = "test_base_from_dsl.fdb";
	
	protected File file = null;

	@Before
	public void doBefore() throws Exception {
		file = TestUtils.getMavenTestDictFile(BASE_FILE);
	}

	@Test
	public void testDSLToFDBConversion() throws Exception {
		
		if (file.exists()) {
			file.delete();
		}
		
		// The reader is expected to be tested in another test
		DSLBaseReaderWrapper reader = DSLBaseReaderFactory.createAndAssertLayoutsDSLBaseReader();	
		List<String> words = reader.getWords();
		Map<Integer, String> wordsMappings = reader.getWordsMappings();
		Map<Integer, Integer> wordsRedirects = reader.getWordsRedirects();
		
		assertNotNull(wordsMappings);
		assertEquals(DSLBaseLayoutsContent.WORDS_MAPPINGS_NUMBER, wordsMappings.size());
		
		assertNotNull(wordsRedirects);
		assertEquals(DSLBaseLayoutsContent.REDIRECTS_NUMBER, wordsRedirects.size());

		BasePropertiesInfo baseInfo = reader.getBasePropertiesInfo();
		
		// Actual number of articles
		assertEquals(DSLBaseLayoutsContent.ARTICLES_ACTAUL_NUMBER, baseInfo.getArticlesActualNumber());
		
		
		// Create writer
		BasePropertiesInfo writerBPI = new BasePropertiesInfo();
		writerBPI.setArticlesFormattingMode(ArticlesFormattingMode.DSL);
		writerBPI.setAbbreviationsFormattingMode(AbbreviationsFormattingMode.DSL);
		writerBPI.setArticlesFormattingInjectWordMode(ArticlesFormattingInjectWordMode.AUTO);
		
		FDBBaseWriterWrapper writer = FDBBaseIOFactory.createFDBBaseWriter(file, writerBPI);
		
		// Set for tracking unique words
		Set<String> uniqueWords = new LinkedHashSet<String>();
		
		// Convert bases
		for (int count = 0; count < words.size(); count++) {
			
			String word = words.get(count);
			
			if (!uniqueWords.contains(word)) {
				uniqueWords.add(word);
				
				WordInfo wordInfo = new WordInfo(count, word);
				wordInfo.setWordMapping(wordsMappings.get(count));
				if (wordsRedirects.containsKey(count)) {
					wordInfo.setRedirectToId(wordsRedirects.get(count));
				}
				
				ArticleInfo articleInfo = new ArticleInfo(wordInfo, "");
				writer.saveArticleInfo(articleInfo);

			} else {
				log.warn("The word is ignored because it's a duplicate: {}", word);
			}
		}
		
		reader.close();
		writer.close();
		
	}
	
	@Test
	public void testDSLToFDBConvertedBase() throws Exception {
		
		FDBBaseReaderWrapper reader = FDBBaseIOFactory.createFDBBaseReader(file);
		
		BasePropertiesInfo baseInfo = reader.getBasePropertiesInfo();
		List<String> words = reader.getWords();

		assertEquals(DSLBaseLayoutsContent.WORDS_UNIQUE_NUMBER, baseInfo.getWordsNumber());
		assertEquals(DSLBaseLayoutsContent.WORDS_MAPPINGS_UNIQUE_NUMBER, baseInfo.getWordsMappingsNumber());
		assertEquals(DSLBaseLayoutsContent.REDIRECTS_UNIQUE_NUMBER, baseInfo.getWordsRelationsNumber());
		
		// Actual number of articles
		assertEquals(DSLBaseLayoutsContent.ARTICLES_ACTAUL_NUMBER, baseInfo.getArticlesActualNumber());
		
		assertEquals(DSLBaseLayoutsContent.WORD_NONINDEXED_AE3PE, words.get(7));
		
	}
	
}

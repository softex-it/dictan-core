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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.AbbreviationsFormattingMode;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.ArticlesFormattingInjectWordMode;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.ArticlesFormattingMode;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.BaseResourceKey;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.MediaResourceKey;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseLayoutsContent;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderFactory;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseIOFactory;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseReaderWrapper;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseWriterWrapper;
import info.softex.dictionary.core.testutils.TestUtils;
import info.softex.dictionary.core.utils.StringUtils;

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
public class DSLToFDBConversionTest {
	
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
		
		Map<Integer, String> wordsMappings = reader.getWordsMappings();
		assertNotNull(wordsMappings);
		assertEquals(DSLBaseLayoutsContent.WORDS_MAPPINGS_NUMBER, wordsMappings.size());
		
		Map<Integer, Integer> wordsRedirects = reader.getWordsRedirects();
		assertNotNull(wordsRedirects);
		assertEquals(DSLBaseLayoutsContent.REDIRECTS_NUMBER, wordsRedirects.size());
		
		Set<String> abbrKeys = reader.getAbbreviationKeys();
		assertNotNull(abbrKeys);
		assertFalse(abbrKeys.isEmpty());
		
		BasePropertiesInfo baseInfo = reader.getBasePropertiesInfo();
		
		// Actual number of articles
		assertEquals(DSLBaseLayoutsContent.ARTICLES_ACTUAL_NUMBER, baseInfo.getArticlesActualNumber());

		// Create writer
		BasePropertiesInfo writerBPI = new BasePropertiesInfo();
		writerBPI.setArticlesFormattingMode(ArticlesFormattingMode.DSL);
		writerBPI.setAbbreviationsFormattingMode(AbbreviationsFormattingMode.DSL);
		writerBPI.setArticlesFormattingInjectWordMode(ArticlesFormattingInjectWordMode.AUTO);
		
		FDBBaseWriterWrapper writer = FDBBaseIOFactory.createFDBBaseWriter(file, writerBPI);
		
		// Base resources
		BaseResourceInfo artResource = reader.getBaseResourceInfo(BaseResourceKey.BASE_ARTICLES_META_DSL.getKey());
		BaseResourceInfo abbrResource = reader.getBaseResourceInfo(BaseResourceKey.BASE_ABBREVIATIONS_META_DSL.getKey());

		assertTrue(artResource.getByteArray().length > 0);
		assertTrue(StringUtils.isNotBlank(artResource.getInfo1()));
		assertTrue(StringUtils.isNotBlank(artResource.getInfo2()));

		assertTrue(abbrResource.getByteArray().length > 0);
		assertTrue(StringUtils.isNotBlank(abbrResource.getInfo1()));
		assertTrue(StringUtils.isNotBlank(abbrResource.getInfo2()));
		
		for (BaseResourceKey brk : BaseResourceKey.values()) {
			if (brk.isAutomatic()) {
				BaseResourceInfo resInfo = reader.getBaseResourceInfo(brk.getKey());
				if (resInfo != null) {
					writer.saveBaseResourceInfo(resInfo);
				}
			}
		}
		
		// Words
		List<String> words = reader.getWords();
		assertNotNull(words);
		
		// Set for tracking unique words
		Set<String> uniqueWords = new LinkedHashSet<String>();
		
		// Convert bases
		for (int count = 0; count < words.size(); count++) {
			
			String word = words.get(count);
			
			if (!uniqueWords.contains(word)) {
				
				uniqueWords.add(word);
				
				WordInfo wordInfo = new WordInfo(count);
				ArticleInfo sourceArticleInfo = reader.getAdaptedArticleInfo(wordInfo);
				assertTrue(StringUtils.isNotBlank(sourceArticleInfo.getArticle()));
				assertTrue(StringUtils.isNotBlank(wordInfo.getWord()));
				assertTrue(StringUtils.isNotBlank(wordInfo.getArticleWord()));
				
				// Check toWordId is always available if redirectId is available
				if (wordInfo.hasRedirect()) {
					assertTrue(StringUtils.isNotBlank(wordInfo.getRedirectToWord()));
				}
				
				ArticleInfo articleInfo = new ArticleInfo(sourceArticleInfo.getWordInfo(), sourceArticleInfo.getArticle());
				writer.saveArticleInfo(articleInfo);

			} else {
				log.warn("The word is ignored because it's a duplicate: {}", word);
			}
		}
		
		// Abbreviations
		Set<String> abbs = reader.getAbbreviationKeys();
		assertNotNull(abbs);
		assertTrue(abbs.size() > 0);
		for (String key : abbs) {
			AbbreviationInfo abbInfo = reader.getAbbreviationInfo(key);
			assertNotNull(abbInfo);
			assertNotNull(abbInfo.getKey());
			assertNotNull(abbInfo.getAbbreviation());
			writer.saveAbbreviationInfo(abbInfo);
		}
		
		// Media resources
		Set<String> mediaKeys = reader.getMediaResourceKeys();
		assertNotNull(mediaKeys);
		assertTrue(mediaKeys.size() > 0);
		for (String key : mediaKeys) {
			MediaResourceInfo resourceInfo = reader.getMediaResourceInfo(new MediaResourceKey(key));
			assertNotNull(resourceInfo);
			writer.saveMediaResourceInfo(resourceInfo);
		}
		
		reader.close();
		writer.close();
		
	}
	
	@Test
	public void testDSLToFDBConvertedBase() throws Exception {
		
		FDBBaseReaderWrapper reader = FDBBaseIOFactory.createAndAssertFDBBaseReader(file);
		
		BasePropertiesInfo baseInfo = reader.getBasePropertiesInfo();
		List<String> words = reader.getWords();

		assertEquals(DSLBaseLayoutsContent.WORDS_UNIQUE_NUMBER, baseInfo.getWordsNumber());
		assertEquals(DSLBaseLayoutsContent.WORDS_MAPPINGS_UNIQUE_NUMBER, baseInfo.getWordsMappingsNumber());
		
		assertEquals(DSLBaseLayoutsContent.ABBREVS_NUMBER, baseInfo.getAbbreviationsNumber());
		
		assertEquals(DSLBaseLayoutsContent.REDIRECTS_UNIQUE_NUMBER, baseInfo.getWordsRelationsNumber());
		
		// Actual number of articles
		assertEquals(DSLBaseLayoutsContent.ARTICLES_ACTUAL_NUMBER, baseInfo.getArticlesActualNumber());
		
		assertEquals(DSLBaseLayoutsContent.WORD_NONINDEXED_AE2_SPEC, words.get(8));
		assertEquals(DSLBaseLayoutsContent.WORD_NONINDEXED_AE3_PE, words.get(14));
		
	}
	
}

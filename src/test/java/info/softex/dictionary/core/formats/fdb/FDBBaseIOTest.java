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
import static org.junit.Assert.assertTrue;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.ArticleInfo.RT;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.BasicCollatorFactory;
import info.softex.dictionary.core.database.BasicSQLiteConnectionFactory;
import info.softex.dictionary.core.formats.api.BaseWriter;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseSampleContent;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseIOFactory;
import info.softex.dictionary.core.testutils.TestUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.6, 		08/27/2011
 * 
 * @modified version 3.2,	04/20/2013
 * @modified version 4.5,	03/26/2014
 * @modified version 4.6,	01/28/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBBaseIOTest {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected final static String BASE_FILE = "test_base_simple.fdb";
	
	protected final static String SW_1002 = FDBBaseSampleContent.createWord(1002);
	
	protected final static int REDIRECT_TO_ID_123 = 123;
	protected final static int REDIRECT_TO_ID_237 = 237;

	protected final static String SW_2000_REDIRECT_TO_123 = FDBBaseSampleContent.createRedirectedWord(2000, REDIRECT_TO_ID_123);
	protected final static String SW_3001_REDIRECT_TO_237 = FDBBaseSampleContent.createRedirectedWord(3001, REDIRECT_TO_ID_237);
	
	protected File file = null;

	@Before
	public void doBefore() throws Exception {
		file = TestUtils.getMavenTestDictFile(BASE_FILE);
	}
	
	@Test
	public void testFDBBaseWrite() throws Exception {
		
		log.info("FDB write test started");
		
		FDBBaseWriter writer = FDBBaseIOFactory.createFDBBaseWriter(file, new BasePropertiesInfo());

		pushSampleToWriter(writer, FDBBaseSampleContent.createWordsArticles(), FDBBaseSampleContent.REDIRECTS);
		
		writer.close();
		
		log.info("FDB write is completed!");
		
	}
	
	@Test
	public void testFDBBaseRead() throws Exception {
		
		log.info("FDB read test started");
		
		FDBBaseReader reader = new FDBBaseReader(file, new BasicSQLiteConnectionFactory(), null, new BasicCollatorFactory());
		reader.load();
		
        assertTrue(reader.isLoaded());

		BasePropertiesInfo baseInfo = reader.getBasePropertiesInfo();
		assertEquals("FDB", baseInfo.getFormatName());
		assertEquals(FDBConstants.CURRENT_FDB_VERSION, baseInfo.getFormatVersion());
		assertEquals(FDBBaseSampleContent.SHORT_NAME, baseInfo.getBaseShortName());
		assertEquals(FDBBaseSampleContent.FULL_NAME, baseInfo.getBaseFullName());
		
		List<String> words = reader.getWords();
		
		assertNotNull(words);
		assertTrue("Number of words must be > 0", words.size() > 0);
		assertEquals(FDBBaseSampleContent.WORDS_NUMBER, words.size());		
		assertEquals(FDBBaseSampleContent.WORDS_NUMBER, baseInfo.getWordsNumber());
		assertEquals(FDBBaseSampleContent.WORDS_NUMBER, baseInfo.getArticlesDeprecatedNumber());
		assertEquals(FDBBaseSampleContent.ARTICLES_ACTUAL_NUMBER, baseInfo.getArticlesActualNumber());
		
		String word0 = words.get(0);
		assertEquals(FDBBaseSampleContent.createWord(0), word0);

		String word15 = words.get(15);
		assertEquals(FDBBaseSampleContent.createWord(15), word15);
		
		String word1002 = words.get(1002);
		assertEquals(SW_1002, word1002);
		
		for (int i = 0; i < words.size(); i++) {
			ArticleInfo article2 = reader.getArticleInfo(new WordInfo(i));
			assertEquals(RT.STRONG, article2.getReferenceType());
			assertNotNull(article2.getArticle());
		}
		
		// Test Redirects
		Map<Integer, Integer> redirects = reader.getWordsRedirects();
		assertEquals(FDBBaseSampleContent.RELATIONS_NUMBER, redirects.size());
		assertEquals(FDBBaseSampleContent.RELATIONS_NUMBER, baseInfo.getWordsRelationsNumber());
		assertEquals(REDIRECT_TO_ID_123, (int) redirects.get(2000));
		assertEquals(REDIRECT_TO_ID_237, (int) redirects.get(3001));

		ArticleInfo article123 = reader.getRawArticleInfo(new WordInfo(123));
		ArticleInfo article2000 = reader.getRawArticleInfo(new WordInfo(2000));
		assertEquals(article123.getArticle(), article2000.getArticle());
		assertEquals(article123.getWordInfo().getWord(), article2000.getWordInfo().getArticleWord());
		assertEquals(SW_2000_REDIRECT_TO_123, article2000.getWordInfo().getWord());
		
		ArticleInfo article237 = reader.getRawArticleInfo(new WordInfo(237));
		ArticleInfo article3001 = reader.getRawArticleInfo(new WordInfo(3001));
		assertEquals(article237.getArticle(), article3001.getArticle());		
		
		// Test redirect
		int redirWID = reader.searchWordIndex(SW_2000_REDIRECT_TO_123, false);
		log.info("ID Redir: {}", redirWID);
		assertTrue("Word indext must be positive", redirWID >= 0);
		assertEquals(SW_2000_REDIRECT_TO_123, words.get(redirWID));
		
		// Test word search
		int engWID = reader.searchWordIndex(FDBBaseSampleContent.createWord(148), true);
		log.info("ID Eng: {}", engWID);
		assertEquals(148, engWID);

		int rusWID = reader.searchWordIndex("текст 50", false);
		log.info("ID Rus: {}", rusWID);
		assertEquals(-3003, rusWID);
		
		reader.close();
		
	}
	
	protected static int pushSampleToWriter(BaseWriter writer, Map<String, String> sample, Map<Integer, Integer> redirects) throws Exception {
		int count = 0;
		for (String word : sample.keySet()) {
			WordInfo wordInfo = new WordInfo(count, word);
			Integer redirectId = redirects.get(count);
			if (redirectId != null) {
				wordInfo = new WordInfo(count, word, redirectId);
			}
			writer.saveArticleInfo(new ArticleInfo(wordInfo, sample.get(word)));				
			count++;
		}
		return count;
	}
	
}

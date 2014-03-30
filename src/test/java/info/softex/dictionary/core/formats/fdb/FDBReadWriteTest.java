/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2014  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.BasicCollatorFactory;
import info.softex.dictionary.core.collation.CollationRulesFactory;
import info.softex.dictionary.core.collation.CollationRulesFactory.SimpleCollationProperties;
import info.softex.dictionary.core.database.BasicSQLiteConnectionFactory;
import info.softex.dictionary.core.formats.api.BaseWriter;

import java.io.File;
import java.text.Collator;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.6, 08/27/2011
 * 
 * @modified version 3.2, 04/20/2013
 * @modified version 4.5, 03/26/2014
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBReadWriteTest {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public static final String BASE_NAME = "Best Base";
	public static final String BASE_PATH = "../dicts";
	public static final String BASE_FILE = "new_dictionary.fdb";
	
	@Test
	public void testFDBBase() throws ClassNotFoundException {
		
		// Connection connection = null;
		try {
			
			new File(BASE_PATH).mkdirs();
			
			File file = new File(BASE_PATH, BASE_FILE);
			
			if (file.exists()) {
				file.delete();
			}
			
			BaseWriter writer = new FDBBaseWriter(file.getAbsolutePath(), new BasicSQLiteConnectionFactory(), null);
			writer.createBase();
			
			BasePropertiesInfo info = new BasePropertiesInfo();
			info.setBaseFullName(BASE_NAME);
			info.setBaseVersion(10,11);
			//info.setBaseLocale(new Locale("RU"));
			//info.setBaseDescription("Very good dictionary");
			
			//info.setArticleFormattingEnabled(false);
			
			info.setFormatVersion(1);
			info.setFormatName("FDB");
			
			writer.saveBasePropertiesInfo(info);
			
			
			LanguageDirectionsInfo dirs = new LanguageDirectionsInfo();
			dirs.setDefaultCollationProperties(CollationRulesFactory.createDefaultCollationProperties().getCollationRules(), null, 10);
			int ver = 0;
			SimpleCollationProperties props = CollationRulesFactory.createLocaleAppendixCollationProperties(new Locale("ru"));
			dirs.addDirection("ru", "en", props.getCollationRules(), "", Collator.PRIMARY, Collator.CANONICAL_DECOMPOSITION, ver, props.isCollationIndependent());
			SimpleCollationProperties props2 = CollationRulesFactory.createLocaleAppendixCollationProperties(new Locale("en"));
			dirs.addDirection("en", "ru", props2.getCollationRules(), "", Collator.PRIMARY, Collator.CANONICAL_DECOMPOSITION, ver, props2.isCollationIndependent());	
			
			writer.saveLanguageDirectionsInfo(dirs);
			

			
			//-------------------------------
			
			pushSampleToWriter(writer, 10, 400);
			
			//-------------------------------
			
			writer.flush();
			
			
			log.info("Base is populated!");
			
			FDBBaseReader r = new FDBBaseReader(file, new BasicSQLiteConnectionFactory(), null, new BasicCollatorFactory());
			
			r.load();
			BasePropertiesInfo dictInfo = r.getBasePropertiesInfo();
			
			assertEquals(BASE_NAME, dictInfo.getBaseFullName());
			
			int id = r.searchWordIndex("sample word 148", true);
			log.info("ID: {}", id);

			int rusId = r.searchWordIndex("текст 50", true);
			log.info("ID Rus: {}", rusId);
			
			
			List<String> words = r.getWords();
			
			assertNotNull(words);
			
			String word = words.get(0);
			log.info("Word 0: {}", word);

			String word2 = words.get(15);
			log.info("Word 1: {}", word2);
			
			String word3 = words.get(155);
			log.info("Word 2: {}", word3);

//			ArticleInfo article = r.getArticleInfo(new WordInfo(null, 0));
//			log.info("{}, {}", article, article.getArticle());
//
//			article = r.getArticleInfo(new WordInfo(null, 1));
//			log.info("{}, {}", article, article.getArticle());
//			
//			article = r.getArticleInfo(new WordInfo(null, 1000));
//			log.info("{}, {}", article, article.getArticle());
//
//			article = r.getArticleInfo(new WordInfo(null, 2052));
//			log.info("{}, {}", article, article.getArticle());
//			
//			article = r.getArticleInfo(new WordInfo(null, 2053));
//			log.info("{}, {}", article, article.getArticle());
//
//			ArticleInfo article3 = r.getArticleInfo(new WordInfo(null, 2054));
//			log.info("{}, {}", article3, article3.getArticle());
//
//			ArticleInfo article4 = r.getArticleInfo(new WordInfo(null, 2055));
//			log.info("{}, {}", article4, article4.getArticle());
			
			for (int i = 0; i < words.size(); i++) {
				ArticleInfo article2 = r.getArticleInfo(new WordInfo(i));
				log.info(i + " {}, {}", article2, article2.getArticle());
			}
			
		} catch (Exception e) {
			log.error("Error", e);
		}
		
	}
	
	private void pushSampleToWriter(BaseWriter writer, int mlp, int packSize) throws Exception {

		for (int i = 0; i < mlp; i++) {
			
			//log.info("Starting MLP {}", i);
			int offset = i * packSize;
			
			for (int j = 0; j < packSize; j++) {
				int num = j + offset;
				writer.saveArticleInfo(new ArticleInfo(new WordInfo(num, "sample word " + num), "simple text string is used as an article " + num));
			}
			
			log.info("Pushing MLP: {}", i);
			
		}
		
	}
	
}

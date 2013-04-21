package info.softex.dictionary.core.formats.fdb;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.BasicCollatorFactory;
import info.softex.dictionary.core.collation.CollationRulesFactory;
import info.softex.dictionary.core.collation.CollationRulesFactory.SimpleCollationProperties;
import info.softex.dictionary.core.database.BasicSQLiteConnectionFactory;
import info.softex.dictionary.core.formats.commons.BaseWriter;

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
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBGeneralTest {
	
	private final static Logger log = LoggerFactory.getLogger(FDBGeneralTest.class.getSimpleName());
	
	public static final String BASE_NAME = "Best Base";

	public static final String BASE_PATH = "../dicts";
	
	public static final String BASE_FILE = "new_dictionary.fdb";
	
	@Test
	public void testFDBBase() throws ClassNotFoundException {
		
		// Connection connection = null;
		try {
			
			new File(BASE_PATH).mkdirs();
			
			File file = new File(BASE_PATH + File.pathSeparator + BASE_FILE);
			
			if (file.exists()) {
				file.delete();
			}
			
			// create a database connection
			//connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
			//connection = new BasicSQLiteConnectionFactory().createConnection(file.getAbsolutePath());
			//connection.createStatement().execute("PRAGMA synchronous=OFF");
			
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
			dirs.setDefaultCollationProperties(CollationRulesFactory.createDefaultCollationProperties(10).getCollationRules(), null, 10);
			int ver = 0;
			SimpleCollationProperties props = CollationRulesFactory.createLocaleCollationProperties(new Locale("ru"), ver, false);
			dirs.addDirection("ru", "en", props.getCollationRules(), "", Collator.PRIMARY, Collator.CANONICAL_DECOMPOSITION, ver, props.isCollationIndependent());
			SimpleCollationProperties props2 = CollationRulesFactory.createLocaleCollationProperties(new Locale("en"), ver, false);
			dirs.addDirection("en", "ru", props2.getCollationRules(), "", Collator.PRIMARY, Collator.CANONICAL_DECOMPOSITION, ver, props2.isCollationIndependent());	
			
			writer.saveLanguageDirectionsInfo(dirs);
			

			
			//-------------------------------
			
			pushSampleToWriter(writer, 10, 400);
			
			//-------------------------------
			
			writer.flush();
			
			
			log.info("Base is populated!");
			
			FDBBaseReader r = new FDBBaseReader(file, 
				new BasicSQLiteConnectionFactory(), null, new BasicCollatorFactory());
			
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
	
	private static void pushSampleToWriter(BaseWriter writer, int mlp, int packSize) throws Exception {

		for (int i = 0; i < mlp; i++) {
			
			//List<ArticleInfo> trs = new ArrayList<ArticleInfo>(packSize);

			log.info("Starting MLP {}", i);
			int offset = i * packSize;
			
			for (int j = 0; j < packSize; j++) {
				int num = j + offset;
				writer.saveArticleInfo(new ArticleInfo(new WordInfo(num, "sample word " + num), "simple text string is used as an article " + num));
			}
			
			log.info("Pushing MLP: {}", i);
			
			//writer.saveArticleInfos(trs);
			
		}
		
	}
	
}

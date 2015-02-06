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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderTestFactory;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.testutils.TestUtils;
import info.softex.dictionary.core.utils.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @since version 4.6, 02/03/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLFormatUtilsTest {

	private final static String PATH_BASE_DSL_LAYOUTS = "/info/softex/dictionary/core/formats/dsl/basevariouslayouts";
	private final static String PATH_BASE_DSL_ARTICLE = "/info/softex/dictionary/core/formats/dsl/articles/dsl.article.alltags.txt";
	private final static String DSL_ADAPTED_ARTICLE_FILE_NAME = "dsl.article.alltags.adapted.html";

	@Test
	public void testDSLFormatting() throws Exception {
		
		DSLBaseReaderWrapper reader = DSLBaseReaderTestFactory.createAndAssertDSLBaseReader(PATH_BASE_DSL_LAYOUTS);
		
		List<String> words = reader.getWords();

		for (int i = 0; i < words.size(); i++) {
			ArticleInfo artInfo = reader.getRawArticleInfo(new WordInfo(i));
			processTestArticle(artInfo.getWordInfo().getWord(), artInfo.getArticle());
		}
		
	}
	
	private void processTestArticle(String word, String article) throws IOException {
		String adaptedArticle = DSLFormatUtils.convertDSLToAdaptedHtml(article);
		assertFalse(StringUtils.isBlank(adaptedArticle));
		
		String htmlArticle = DSLFormatUtils.convertDSLAdaptedHtmlToHtml(adaptedArticle);
		Files.write(TestUtils.getMavenTestPath(word + ".html"), htmlArticle.getBytes());
		
	}
	
	@Test
	public void testValidFDBBaseSizes() throws IOException, URISyntaxException {
		
		URL url = getClass().getResource(PATH_BASE_DSL_ARTICLE);
		
		List<String> lines = Files.readAllLines(Paths.get(url.toURI()), Charset.forName("UTF-8"));
		assertNotNull(lines);
		
		String article = StringUtils.join(lines, "\r\n");
		assertFalse(StringUtils.isBlank(article));
		
		String adaptedArticle = DSLFormatUtils.convertDSLToAdaptedHtml(article);
		assertFalse(StringUtils.isBlank(adaptedArticle));
		
		//System.out.println(adaptedArticle);
		
		String htmlArticle = DSLFormatUtils.convertDSLAdaptedHtmlToHtml(adaptedArticle);
		
		Files.write(TestUtils.getMavenTestPath(DSL_ADAPTED_ARTICLE_FILE_NAME), htmlArticle.getBytes());
		
	}

}

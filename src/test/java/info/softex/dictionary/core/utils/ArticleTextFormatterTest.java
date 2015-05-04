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

package info.softex.dictionary.core.utils;

import static org.junit.Assert.assertEquals;
import info.softex.dictionary.core.testutils.MavenUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @since version 4.8,		05/02/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class ArticleTextFormatterTest {
	
	protected final static String UTF8 = "UTF-8";
	protected final static String FULL_ARTICLE_HTML = "/info/softex/dictionary/core/utils/article_full_html.txt";
	protected final static String FULL_ARTICLE_NO_HTML = "/info/softex/dictionary/core/utils/article_full_no_html.txt";
	protected final static String FULL_ARTICLE_NO_HTML_CUT = "/info/softex/dictionary/core/utils/article_full_no_html_cut.txt";
	
	protected final static int TEST_LENGTH = 7;
	
	@SuppressWarnings("serial")
	public static final Map<String, String> ARTICLES = new LinkedHashMap<String, String>() {{
		put(null, null);
		put("", "");
		put(" ", " ");
		put("simple text", "simple text");
		put("simple text <markup> test</markup>", "simple text  test");
		put("<div>simple \r\ntext <markup> test</markup><div>", "simple \r\ntext  test");
		put("simple \r\ntext <script> test</script>", "simple \r\ntext ");
		put("simple \r\ntext <style> test</style>", "simple \r\ntext ");
		put("<!-- simple \r\ntext <style> test -->", "");
		put("text <style> error1 \r\n<style> error2 </style> text", "text  text");
		put("<script> error1 </div>\r\n<style> error2 </style> text 4", "");
		put("<script> error1 </div>\r\n<style> error2 </script> text 4", " text 4");
		put("special &#40; character", "special ( character");
		put("special \r\n&#109 character", "special \r\nm character");
		put("sp &amp ecial character &copy;", "sp & ecial character \u00A9");
		put("<div class=\"header\">butter</div><div class=\"m1\"><o>&#91;</o> <t>'bʌtə</t> <o>&#93;</o>", "butter[ 'bʌtə ]");
	}};

	@Test
	public void testRemoveXmlHtml() {
		for (Map.Entry<String, String> entry : ARTICLES.entrySet()) {
			String value = ArticleTextFormatter.removeXmlHtml(entry.getKey());
			assertEquals(entry.getValue(), value);
		}
	}
	
	@Test
	public void testRemoveXmlHtmlMaxLength() {
		int count = 0;
		for (Map.Entry<String, String> entry : ARTICLES.entrySet()) {
			String expValue = entry.getValue();
			if (expValue != null && expValue.length() >= TEST_LENGTH) {
				String value = ArticleTextFormatter.removeXmlHtml(entry.getKey(), TEST_LENGTH);
				assertEquals("Lines at #" + count + " don't match", expValue.substring(0, TEST_LENGTH), value);
			}
			count++;
		}
	}
	
	@Test
	public void testRemoveXmlHtmlFromFullArticle() throws UnsupportedEncodingException, IOException {		
		String articleOrig = new String(FileUtils.toByteArray(MavenUtils.getCodeSourceRelevantFile(FULL_ARTICLE_HTML)), UTF8);
		String articleNoXml = new String(FileUtils.toByteArray(MavenUtils.getCodeSourceRelevantFile(FULL_ARTICLE_NO_HTML)), UTF8);
		assertEquals(articleNoXml, ArticleTextFormatter.removeXmlHtml(articleOrig));
	}
	
	@Test
	public void testRemoveXmlHtmlMaxLengthFromFullArticle() throws UnsupportedEncodingException, IOException {		
		String articleOrig = new String(FileUtils.toByteArray(MavenUtils.getCodeSourceRelevantFile(FULL_ARTICLE_HTML)), UTF8);
		String articleNoXmlCut = new String(FileUtils.toByteArray(MavenUtils.getCodeSourceRelevantFile(FULL_ARTICLE_NO_HTML_CUT)), UTF8);
		assertEquals(articleNoXmlCut, ArticleTextFormatter.removeXmlHtml(articleOrig, 400));
	}
	
}

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

package info.softex.dictionary.core.formats.dsl.testutils.content;

import static org.junit.Assert.assertEquals;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.api.BaseReader;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseAssertUtils;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.testutils.BaseReaderAssertUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Test data for the layouts DSL base.
 * 
 * @since version 4.6, 02/09/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseLayoutsContent {
	
	protected final static String PATH_BASE_DSL_LAYOUTS_ORIG = "/info/softex/dictionary/core/formats/dsl/bases/layouts";
	protected final static String PATH_BASE_DSL_LAYOUTS_ADAPTED = "/info/softex/dictionary/core/formats/dsl/bases/layoutsadapted";
	
	protected final static int WORDS_NUMBER = 29;
	protected final static int WORDS_NUMBER_UNIQUE = 27;
	
	protected final static int WORDS_MAPPINGS_NUMBER = 16;
	protected final static int WORDS_MAPPINGS_NUMBER_UNIQUE = 15;
	
	protected final static int REDIRECTS_NUMBER = 17;
	protected final static int REDIRECTS_NUMBER_UNIQUE = 16;
	
	protected final static int ARTICLES_ACTUAL_NUMBER = 12;
	protected final static int ARTICLES_ACTUAL_NUMBER_UNIQUE = 11;
	
	protected final static int ABBREVS_NUMBER = 16;
	protected final static int MEDIA_RES_NUMBER = 1;
	
	public final static String WORD_NONINDEXED_AE2_SPEC = "-ae2 with {braces} and";
	public final static String WORD_NONINDEXED_AE3_PE = "-ae3 partially excluded";
	
	@SuppressWarnings("serial")
	public static final TreeMap<Integer, Integer> REDIRECTS_ORIG = new TreeMap<Integer, Integer>() {{
		
		// specialchars
		put(3, 2);
		
		// -ae1
		put(6, 5); put(7, 5); put(8, 5); put(9, 5); put(10, 5); put(11, 5); put(12, 5); put(13, 5); put(14, 5); put(15, 5);
		
		// sample entry
		put(20, 19); put(21, 19); put(22, 19); put(23, 19); put(24, 19); put(25, 19);
		
	}};
	
	@SuppressWarnings("serial")
	public static final TreeMap<Integer, Integer> REDIRECTS_UNIQUE_DSL_2_FDB = new TreeMap<Integer, Integer>() {{
		
		// -ae1
		put(1, 0); put(2, 0); put(3, 0); put(4, 0); put(5, 0); put(6, 0); put(7, 0); put(8, 0); put(9, 0);
		
		// sample entry
		put(10, 17); put(13, 17); put(14, 17); put(15, 17); put(16, 17); put(18, 17);
		
		// specialchars
		put(20, 19);
		
	}};
	
	@SuppressWarnings("serial")
	public static final TreeMap<Integer, Integer> REDIRECTS_UNIQUE_FDB_2_DSL = new TreeMap<Integer, Integer>() {{
		
		// -ae1
		put(1, 0); put(2, 0); put(3, 0); put(4, 0); put(5, 0); put(6, 0); put(7, 0); put(8, 0); put(9, 0);
		
		// sample entry
		put(13, 12); put(14, 12); put(15, 12); put(16, 12); put(17, 12); put(18, 12);
		
		// specialchars
		put(20, 19);
		
	}};
	
	public final static BasePropertiesInfo PROPS_LAYOUTS_ORIG = new BasePropertiesInfo();
	static {
		PROPS_LAYOUTS_ORIG.setWordsNumber(WORDS_NUMBER);
		PROPS_LAYOUTS_ORIG.setWordsMappingsNumber(WORDS_MAPPINGS_NUMBER);
		PROPS_LAYOUTS_ORIG.setWordsRelationsNumber(REDIRECTS_NUMBER);
		PROPS_LAYOUTS_ORIG.setArticlesActualNumber(ARTICLES_ACTUAL_NUMBER);
		PROPS_LAYOUTS_ORIG.setAbbreviationsNumber(ABBREVS_NUMBER);
		PROPS_LAYOUTS_ORIG.setMediaResourcesNumber(MEDIA_RES_NUMBER);
	}
	
	public final static BasePropertiesInfo PROPS_LAYOUTS_UNIQUE = new BasePropertiesInfo();
	static {
		PROPS_LAYOUTS_UNIQUE.setWordsNumber(WORDS_NUMBER_UNIQUE);
		PROPS_LAYOUTS_UNIQUE.setWordsMappingsNumber(WORDS_MAPPINGS_NUMBER_UNIQUE);
		PROPS_LAYOUTS_UNIQUE.setWordsRelationsNumber(REDIRECTS_NUMBER_UNIQUE);
		PROPS_LAYOUTS_UNIQUE.setArticlesActualNumber(ARTICLES_ACTUAL_NUMBER_UNIQUE);
		PROPS_LAYOUTS_UNIQUE.setAbbreviationsNumber(ABBREVS_NUMBER);
		PROPS_LAYOUTS_UNIQUE.setMediaResourcesNumber(MEDIA_RES_NUMBER);
	}
	
	@SuppressWarnings("serial")
	public static final Map<String, String> WORDS_ARTICLES_ORIG_SELECTIVE = new LinkedHashMap<String, String>() {{
		put("trivial card with markup", 
			"Trivially simple card. [m3]Very simple.[/m]\r\n" + 
			"[m2]Two left margin tags[/m][m2] in one line.[/m] and line with no tags.\r\n" + 
			"[m2]Math expressions: 5 < 3 < 2, 2 <=> 2[/m]\r\n" + 
			"This is [p]abbr.[/p]\r\n" + 
			"[c]Text with default highlighted color[/c]\r\n" + 
			"[c blue]Text with blue highlighted color[/c]\r\n" + 
			"[t]This is transcription[/t]\r\n" + 
			"Stress[']e[/']d word\r\n" + 
			"[com]This text is a comment[/com]\r\n" + 
			"[trn][*]Translation zone line[/*][/trn]\r\n" + 
			"[!trs]Don't index this line[/!trs]\r\n" + 
			"[ex]Example line[/ex]\r\n" + 
			"[ex testatr]Example line with test attribute[/ex]\r\n" + 
			"[lang id=1033]Language 1033 line[/lang]\r\n" + 
			"[lang]Language line with no id[/lang]\r\n" + 
			"Line with braces \\(\\) \\{ \\}\r\n" + 
			"One more line.\r\n" + 
			"\\ ");
	}};
	
	@SuppressWarnings("serial")
	public static final Map<String, String> WORDS_ARTICLES_ADAPTED_SELECTIVE = new LinkedHashMap<String, String>() {{
		put("trivial card with markup", 
			"Trivially simple card. <div class=\"m3\">Very simple.</div>\r\n" + 
			"<div class=\"m2\">Two left margin tags</div><div class=\"m2\"> in one line.</div> and line with no tags.\r\n" + 
			"<br/><div class=\"m2\">Math expressions: 5 &#60; 3 &#60; 2, 2 &#60;=&#62; 2</div>\r\n" + 
			"This is <w>abbr.</w>\r\n" + 
			"<br/><span class=\"cd\">Text with default highlighted color</span>\r\n" + 
			"<br/><span class=\"ca\" style=\"color:blue\">Text with blue highlighted color</span>\r\n" + 
			"<br/><t>This is transcription</t>\r\n" + 
			"<br/>Stress<v>e</v>d word\r\n" + 
			"<br/><c>This text is a comment</c>\r\n" + 
			"<br/><n><f>Translation zone line</f></n>\r\n" + 
			"<br/><m>Don't index this line</m>\r\n" + 
			"<br/><e>Example line</e>\r\n" + 
			"<br/><e testatr>Example line with test attribute</e>\r\n" + 
			"<br/><l id=1033>Language 1033 line</l>\r\n" + 
			"<br/><l>Language line with no id</l>\r\n" + 
			"<br/>Line with braces &#40;&#41; &#123; &#125;\r\n" + 
			"<br/>One more line.\r\n" + 
			"<br/>&#160;<br/><br/>");
	}};
	
	
	public static void assertReaderHasOrigLayoutsContent(BaseReader reader) throws BaseFormatException {
		DSLBaseAssertUtils.assertMainDSLBaseReaderParametersEqualByProperties(PROPS_LAYOUTS_ORIG, reader, true, true);
		BaseReaderAssertUtils.assertWordsRedirectsEqual(REDIRECTS_ORIG, reader.getWordsRedirects());
		
		ArticleInfo artInfo = reader.getRawArticleInfo(new WordInfo(1));
		assertEquals(WORDS_ARTICLES_ORIG_SELECTIVE.values().iterator().next(), artInfo.getArticle());
	}
	
	public static void assertReaderHasUniqueLayoutsContentDslToFdb(BaseReader reader) throws BaseFormatException {
		DSLBaseAssertUtils.assertMainDSLBaseReaderParametersEqualByProperties(PROPS_LAYOUTS_UNIQUE, reader, true, true);
		BaseReaderAssertUtils.assertWordsRedirectsEqual(REDIRECTS_UNIQUE_DSL_2_FDB, reader.getWordsRedirects());

		WordInfo wordInfo = new WordInfo(WORDS_ARTICLES_ADAPTED_SELECTIVE.keySet().iterator().next());
		ArticleInfo artInfo = reader.getRawArticleInfo(wordInfo);
		
		//System.out.println("AAAART " + wordInfo + " R " + reader);
		assertEquals(WORDS_ARTICLES_ADAPTED_SELECTIVE.values().iterator().next(), artInfo.getArticle());
	}
	
	public static void assertReaderHasUniqueLayoutsContentFdb2Dsl(BaseReader reader) throws BaseFormatException {
		DSLBaseAssertUtils.assertMainDSLBaseReaderParametersEqualByProperties(PROPS_LAYOUTS_UNIQUE, reader, true, true);
		BaseReaderAssertUtils.assertWordsRedirectsEqual(REDIRECTS_UNIQUE_FDB_2_DSL, reader.getWordsRedirects());
	}
	
	// Creation of sample layout bases ---------------------------------------------------------------
	
	public static DSLBaseReaderWrapper createAndAssertLayoutsAdaptedDSLBaseReader() throws Exception {
		return DSLBaseAssertUtils.createAndAssertDSLBaseReader(PATH_BASE_DSL_LAYOUTS_ADAPTED);
	}
	
	public static DSLBaseReaderWrapper createAndAssertLayoutsDSLBaseReader() throws Exception {
		
		DSLBaseReaderWrapper reader = DSLBaseAssertUtils.createAndAssertDSLBaseReader(PATH_BASE_DSL_LAYOUTS_ORIG);
		
		// Assert base is read correctly
		DSLBaseLayoutsContent.assertReaderHasOrigLayoutsContent(reader);
		
		return reader;
		
	}
	
}

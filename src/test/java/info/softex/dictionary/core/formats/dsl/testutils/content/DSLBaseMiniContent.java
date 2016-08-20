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

import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseAssertUtils;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Test data for the DSL base with minimum layout.
 * 
 * @since version 4.6, 01/27/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseMiniContent {
	
	protected final static String PATH_BASE_DSL_SYNTAX = "/info/softex/dictionary/core/formats/dsl/bases/mini";

	protected static final int WORDS_NUMBER = 19;
	protected static final int WORDS_MAPPINGS_NUMBER = 5;
	protected static final int WORDS_REDIRECTS_NUMBER = 10;
	protected static final int ARTICLES_ACTUAL_NUMBER = 9;
	protected static final int ABBREVS_NUMBER = 0;
	protected static final int MEDIA_RES_NUMBER = 0;
	
	public static final int LINES_NUMBER = 48;
	
	@SuppressWarnings("serial")
	public static final Map<String, String> WORDS_ARTICLES = new LinkedHashMap<String, String>() {{
		put("''", "[m1][i][c][trn]symbol[/c][/i] ditto[/trn][/m]");
		put("cause", "\\[[t]kəz[/t]\\]\r\n[m1][p][trn]conj.[/p] [i][c]informal[/c][/i] short for [ref]because[/ref][/trn][/m]");
		put("cept", "\\[[t]sep(t)[/t]\\] \r\n[m1][p][trn]prep., conj., & v.[/p] nonstandard contraction of [ref]except[/ref][/trn][/m]");
		
		put("sample entry",  "Sample entry is the main headword with many redirects.");
		put("example",  get("sample entry"));
		put("sample card 1",  get("sample entry"));
		put("sample card 2",  get("sample entry"));
		put("sample card 3",  get("sample entry"));
		put("sample card 4",  get("sample entry"));
		put("sample headword",  get("sample entry"));
		
		put("d", "[m1][i][c][trn]contraction[/c][/i] had[/trn][/m]\r\n[m1][*][trn][ex]they'd already gone[/ex][/trn][/*][/m]\r\n[m1][c gray][trn]■[/c] would[/trn][/m]");
		put("lean to", "[m1][trn]incline or be partial to (a view or position)[/trn][/m]  \r\n[m1][*][trn][ex]I now lean toward accident as the cause of the crash[/ex][/trn][/*][/m]");
		put("myTestKey5",  "simple text to verify");
		put("myTestKey6",  "This is an example of multi-key article");
		put("myTestKey7",  get("myTestKey6"));
		put("myTestKey8",  "This is another example of multi-key article\r\nSecond line of the test");
		put("myTestKey9",  get("myTestKey8"));
		put("myTestKey10", get("myTestKey8"));
		put("myTestKey11", get("myTestKey8"));
	}};
	
	@SuppressWarnings("serial")
	public static final TreeMap<Integer, Integer> REDIRECTS = new TreeMap<Integer, Integer>() {{
		put(4, 3);
		put(5, 3);
		put(6, 3);
		put(7, 3);
		put(8, 3);
		put(9, 3);
		put(14, 13);
		put(16, 15);
		put(17, 15);
		put(18, 15);
	}};
	
	public final static BasePropertiesInfo PROPS_MINI_ORIG = new BasePropertiesInfo();
	static {
		PROPS_MINI_ORIG.setWordsNumber(WORDS_NUMBER);
		PROPS_MINI_ORIG.setWordsMappingsNumber(WORDS_MAPPINGS_NUMBER);
		PROPS_MINI_ORIG.setWordsRelationsNumber(WORDS_REDIRECTS_NUMBER);
		PROPS_MINI_ORIG.setArticlesActualNumber(ARTICLES_ACTUAL_NUMBER);
		PROPS_MINI_ORIG.setAbbreviationsNumber(ABBREVS_NUMBER);
		PROPS_MINI_ORIG.setMediaResourcesNumber(MEDIA_RES_NUMBER);
	}
	
	public static final String DSL_ARTICLE_BASE_HEADER = "#NAME	\"TestDictionary (En-En)\"\r\n#INDEX_LANGUAGE	\"English\"\r\n#CONTENTS_LANGUAGE	\"English\"\r\n#ICON_FILE	\"\\test\\testbase.bmp\"";

	
	// Creation of sample layout bases -----------------------------------------------------
	
	public static DSLBaseReaderWrapper createAndAssertMiniDSLBaseReader() throws Exception {
		return DSLBaseAssertUtils.createAndAssertDSLBaseReader(PATH_BASE_DSL_SYNTAX);
	}
	
}

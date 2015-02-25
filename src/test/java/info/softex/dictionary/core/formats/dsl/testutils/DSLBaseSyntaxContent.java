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

package info.softex.dictionary.core.formats.dsl.testutils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Test data for the syntax DSL base.
 * 
 * @since version 4.6, 01/27/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseSyntaxContent {

	public static final int WORDS_NUMBER = 12;
	public static final int ARTICLES_NUMBER = 8;
	public static final int REDIRECTS_NUMBER = 4;
	public static final int ABBR_NUMBER = 2;
	
	public static final int LINES_NUMBER = 39;
	
	@SuppressWarnings("serial")
	public static final Map<String, String> WORDS_ARTICLES = new LinkedHashMap<String, String>() {{
		put("''", "[m1][i][c][trn]symbol[/c][/i] ditto[/trn][/m]");
		put("cause", "\\[[t]kəz[/t]\\]\r\n[m1][p][trn]conj.[/p] [i][c]informal[/c][/i] short for [ref]because[/ref][/trn][/m]");
		put("cept", "\\[[t]sep(t)[/t]\\] \r\n[m1][p][trn]prep., conj., & v.[/p] nonstandard contraction of [ref]except[/ref][/trn][/m]");
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
	public static final Map<String, String> ABBREVIATIONS = new LinkedHashMap<String, String>() {{
		put("abbr.", "Abbreviation");
		put("adj.", "Adjective");
	}};
	
	@SuppressWarnings("serial")
	public static final TreeMap<Integer, Integer> REDIRECTS = new TreeMap<Integer, Integer>() {{
		put(7, 6);
		put(9, 8);
		put(10, 8);
		put(11, 8);
	}};
	
	public static final String DSL_ARTICLE_BASE_HEADER = "#NAME	\"TestDictionary (En-En)\"\r\n#INDEX_LANGUAGE	\"English\"\r\n#CONTENTS_LANGUAGE	\"English\"\r\n#ICON_FILE	\"\\test\\testbase.bmp\"";

	
}

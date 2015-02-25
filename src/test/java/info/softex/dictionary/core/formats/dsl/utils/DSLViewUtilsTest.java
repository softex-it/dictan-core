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

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @since version 4.6, 02/16/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLViewUtilsTest {

	@SuppressWarnings("serial")
	public static final Map<String, String> DSL_TAGS_TO_HTML_TAGS = new LinkedHashMap<String, String>() {{
		put(null, null);
		put("", "");
		put(" ", " ");
		put("\n", "\n");
		put("<", "<");
		put(">", ">");
		put("<t", "<t");
		put("<>", "<>");
		put("< >", "< >");
		put("<  >", "<  >");
		put("<   >", "<   >");
		put("<t ", "<span class=\"t\" "); // Line 10
		put("<t  ", "<span class=\"t\"  ");
		put("<t  <", "<span class=\"t\"  <");
		put("<t  >", "<span class=\"t\"  >");
		put(" >", " >");
		put(" t>", " t>");
		put("/t>", "/span>");
		put(" /t>", " /span>");
		put("  /t>", "  /span>");
		put("<t>my transcription</t><br/><hr/>Stress<v>e</v>d<a href=\"test\">test</a>", 
			"<span class=\"t\">my transcription</span><br/><hr/>Stress<span class=\"v\">e</span>d<a href=\"test\">test</a>");
		put("<n>translation zone</n><f>full translation zone</f><m>no index zone</m>", 
			"<n>translation zone</n><f>full translation zone</f><m>no index zone</m>");
	}};
	
	@Test
	public void testConvertDSLTagsToHtmlTags() {

		int count = 0;
		for (String article : DSL_TAGS_TO_HTML_TAGS.keySet()) {
			
			String processed = DSLViewUtils.convertDSLHtmlToHtml4(article);
			assertEquals("Line " + count + " don't match", DSL_TAGS_TO_HTML_TAGS.get(article), processed);
			
			count++;
		}
	}
	
}

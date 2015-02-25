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
 * @since version 4.6, 02/14/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLFormatUtilsTest {
	
	@SuppressWarnings("serial")
	public static final Map<String, String> LINE_BREAK_MAPING = new LinkedHashMap<String, String>() {{
		put(null, null);
		put("", "<br/><br/>");
		put(" ", " <br/><br/>");
		put("\n", "\n<br/><br/>");
		put("\r", "\r<br/><br/>");
		put("\r\n", "\r\n<br/><br/>");
		put("\n\n", "\n<br/>\n<br/><br/>");
		put(" \n ", " \n<br/> <br/>");
		put("some text\nsometext 2\n", "some text\n<br/>sometext 2\n<br/><br/>");
		put("text 1\nsome text\nsometext 2\n", "text 1\n<br/>some text\n<br/>sometext 2\n<br/><br/>");
		put("\ntext 1\nsome text\nsometext 2\n", "\n<br/>text 1\n<br/>some text\n<br/>sometext 2\n<br/><br/>");
		put("\ntext 2\r\nsome text\nsometext 2\n", "\n<br/>text 2\r\n<br/>some text\n<br/>sometext 2\n<br/><br/>");
		put("\ntext 2\r\nsome text\nsometext 2\nmore text", "\n<br/>text 2\r\n<br/>some text\n<br/>sometext 2\n<br/>more text<br/><br/>");
		put("[m1]some test text[/m]", "[m1]some test text[/m]<br/>");
		put("[m1]some test text[/m] plus other text  ", "[m1]some test text[/m] plus other text  <br/><br/>");
	}};
	
	@Test
	public void testAddDSLLineBreaks() throws Exception {
		
		int count = 0;
		for (String article : LINE_BREAK_MAPING.keySet()) {
			String processed = DSLReadFormatUtils.addDSLLineBreaks(article);
			assertEquals("Lines " + count + " don't match", LINE_BREAK_MAPING.get(article), processed);
			
			// Check converted line with removed breaks equals to the original line
			String origConverted = processed != null ? processed.replaceAll("<br/>", "") : null;
			assertEquals("Lines " + count + " don't match", article, origConverted);
			
			count++;
		}
		
	}

}

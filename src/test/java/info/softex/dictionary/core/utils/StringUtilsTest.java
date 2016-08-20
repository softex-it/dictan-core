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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests functionality of StringUtils.
 * 
 * @since version 4.6, 02/07/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class StringUtilsTest {
	
	@SuppressWarnings("serial")
	private final static Map<String, String > STRINGS_LTRIMMED = new HashMap<String, String>() {{
		put(null, null);
		put("", "");
		put(" ", "");
		put(" \t ", "");
		put(" some string ", "some string ");
		put("\t some string \t ", "some string \t ");
		put("string no spaces", "string no spaces");
	}};
	
	@SuppressWarnings("serial")
	protected final static Map<String, String[]> STRINGS_WITH_LINE_BREAKS = new HashMap<String, String[]>() {{
		put(null, null);
		put("", new String[] {""});
		put(" ", new String[] {" "});
		put("text no lines", new String[] {"text no lines"});
		put("text \nwith 2 lines n", new String[] {"text ", "with 2 lines n"});
		put("text \rwith 1 line r", new String[] {"text \rwith 1 line r"});
		put("text \nwith 2 \rlines", new String[] {"text ", "with 2 \rlines"});
		put("text \r\nwith 2 lines rn", new String[] {"text ", "with 2 lines rn"});
	}};
	
	@Test
	public void testLTrim() throws Exception {
		for (String input : STRINGS_LTRIMMED.keySet()) {
			assertEquals(STRINGS_LTRIMMED.get(input), StringUtils.ltrim(input));
		}
	}
	
	@Test
	public void testDefaultString() throws Exception {
		assertEquals("", StringUtils.defaultString(null));
		assertEquals("", StringUtils.defaultString(""));
		assertEquals("mytest", StringUtils.defaultString("mytest"));
	}
	
	@Test
	public void testSplitByLineBreaks() throws UnsupportedEncodingException, IOException {
		for (String str : STRINGS_WITH_LINE_BREAKS.keySet()) {
			
			String[] expectedLines = STRINGS_WITH_LINE_BREAKS.get(str);			
			String[] lines = StringUtils.splitByLineBreaks(str);
			
			if (lines == null && expectedLines == null) {
				continue;
			}
			
			assertEquals("Lengths are not equeal: " + Arrays.toString(expectedLines) + " | " + 
				Arrays.toString(lines), expectedLines.length, lines.length);
			
			for (int i = 0; i < expectedLines.length; i++) {
				assertEquals(expectedLines[i], lines[i]);
			}

		}
	}

}

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

}

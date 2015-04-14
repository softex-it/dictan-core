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
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

/**
 * 
 * @since version 4.7, 03/30/2015
 *
 * @author Dmitry Viktorov
 *
 */
public class SearchUtilsTest {
	
	protected final static String WORD_UPPERCASE_TEST = "test and after Test";
	protected final static String WORD_UPPERCASE_TEST_FLR = "Test and after Test";
	
	protected final static int SIZE_DEF = 1000;
	
	@SuppressWarnings("serial")
	protected final static Map<String, String> SEARCH_FLR_MAPPING = new LinkedHashMap<String, String>() {{
		put("some test word", "Some test word");
		put("another test word", "Another test word");
		put("3rd word", "3Rd word");
		put("test", "Test");
		put("line ending with test", "Line ending with test");
		put(WORD_UPPERCASE_TEST, WORD_UPPERCASE_TEST_FLR);
		put("%", null);
		put("before test", "Before test");
		put("%%", null);
		put("some expression", "Some expression");
		put("%%%", null);
		put(" 123 text after numbers", " 123 Text after numbers");
		put("m", "M");
		put("N", "n");
		put(" 2+2=4", null);
	}};
	
	protected final static Set<String> SEARCH_ENTRIES = SEARCH_FLR_MAPPING.keySet();
	
	@SuppressWarnings("serial")
	protected final static Map<String, String> SEARCH_ESCAPE_MAPPING = new LinkedHashMap<String, String>() {{
		put("%", "!%");    // % is not considered a valid expression, so escape it
		put("%%", "!%!%"); // %% is not considered a valid expression, so escape it
		put("%%%", "%!%%");
		put("%%%%", "%!%!%%");
		put("!", "!!");
		put("!!", "!!!!");
		put("%a%", "%a%");
		put("%a%%", "%a!%%");
		put("%a!%%", "%a!!!%%");
		put("abc", "abc");
		put("", "");
		put(null, null);
	}};
	
	
	@Test
    public void searchSQLLikeBorderTest() {
		TreeMap<Integer, String> result = SearchUtils.searchSQLLike(SEARCH_ENTRIES, "%test%", 0);
		assertEquals(0, result.size());
		
		result = SearchUtils.searchSQLLike(null, null, SIZE_DEF);
		assertEquals(0, result.size());
		
		result = SearchUtils.searchSQLLike(SEARCH_ENTRIES, "%", SIZE_DEF);
		assertEquals(3, result.size());
		
		result = SearchUtils.searchSQLLike(SEARCH_ENTRIES, "%%", SIZE_DEF);
		assertEquals(2, result.size());
		
		result = SearchUtils.searchSQLLike(SEARCH_ENTRIES, "%%%", SIZE_DEF);
		assertEquals(3, result.size());
		
	}
	
	@Test
    public void searchSQLLikeBeforeAfterTest() {
    	
		// Test general
		TreeMap<Integer, String> result = SearchUtils.searchSQLLike(SEARCH_ENTRIES, "%test%", SIZE_DEF);
    	assertEquals(6, result.size());
    	
    	// Test limit
    	result = SearchUtils.searchSQLLike(SEARCH_ENTRIES, "%test%", 2);
    	assertEquals(2, result.size());
    }
	
	@Test
    public void searchSQLLikeAfterTest() {
    	TreeMap<Integer, String> result = SearchUtils.searchSQLLike(SEARCH_ENTRIES, "test%", SIZE_DEF);
    	assertEquals(2, result.size());
    }

	@Test
    public void searchSQLLikeBeforeTest() {
    	TreeMap<Integer, String> result = SearchUtils.searchSQLLike(SEARCH_ENTRIES, "%test", SIZE_DEF);
    	assertEquals(4, result.size());
    	assertTrue(result.containsValue(WORD_UPPERCASE_TEST));
    }
	
	@Test
	public void escapeSQLLikeTest() {
		int count = 0;
		for (String key : SEARCH_ESCAPE_MAPPING.keySet()) {
			assertEquals("Item " + count + " failed", SEARCH_ESCAPE_MAPPING.get(key), SearchUtils.escapeSQLLike(key, '!', '%'));
			count++;
		}
	}
	
	@Test
	public void revertFirstLetterRegisterTest() {
		int count = 0;
		for (String key : SEARCH_FLR_MAPPING.keySet()) {
			assertEquals("Item " + count + " failed", SEARCH_FLR_MAPPING.get(key), SearchUtils.revertFirstLetterRegister(key));
			count++;
		}
	}

}

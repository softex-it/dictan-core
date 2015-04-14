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

package info.softex.dictionary.core.collections;

import static org.junit.Assert.assertEquals;

import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @since version 4.7, 04/01/2015
 *
 * @author Dmitry Viktorov
 *
 */
public class IntegerMappedListTest {
	
	@SuppressWarnings("serial")
	protected final static TreeMap<Integer, String> TEST_INT_MAP = new TreeMap<Integer, String>() {{
		put(123, "Test 1");
		put(456, "Test 2");
		put(789, "Test 3");
		put(888, "Test 4");
	}};
	
	protected IntegerMappedList<String> testList = null;
	
	@SuppressWarnings("unchecked")
	@Before
	public void doBefore() {
		testList = new IntegerMappedList<>((TreeMap<Integer, String>)TEST_INT_MAP.clone());
	}
	
	@Test
    public void mappedListBasicTest() {
		assertEquals(4, testList.size());
		assertEquals(testList.get(0), "Test 1");
		assertEquals(testList.get(1), "Test 2");
		assertEquals(testList.get(2), "Test 3");
		assertEquals(testList.get(3), "Test 4");
	}
	
	@Test
    public void mappedListRemovalTest() {
		testList.removeLast();
		assertEquals(3, testList.size());
		assertEquals(testList.get(0), "Test 1");
		assertEquals(testList.get(1), "Test 2");
		assertEquals(testList.get(2), "Test 3");
		
		testList.removeLast();
		assertEquals(2, testList.size());

		testList.removeLast();
		assertEquals(1, testList.size());
		
		testList.removeLast();
		assertEquals(0, testList.size());

		testList.removeLast();
		assertEquals(0, testList.size());
		
	}

}

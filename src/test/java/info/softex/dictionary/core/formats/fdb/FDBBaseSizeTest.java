/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2014  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.formats.fdb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @since version 4.5, 03/29/2014
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBBaseSizeTest {
	
	private final static long TEST_SEC_PART_SIZE = 1073741824L;
	
	@Test
	public void testValidFDBBaseSizes() throws Exception {
		long totalKeysNumber = 1000000;
		long wordsPerMegabyte = 16000;
		long mainPartSize = TEST_SEC_PART_SIZE - Math.round(totalKeysNumber / wordsPerMegabyte) * 1024 * 1024;
		FDBBaseWriterWrapper writer = FDBBaseWriterWrapper.create(TEST_SEC_PART_SIZE, mainPartSize);
		assertEquals(mainPartSize, writer.getMinMainBaseSize());
		assertEquals(TEST_SEC_PART_SIZE, writer.getMinSecondaryBaseSize());
	}

	@Test
	public void testInvalidUnlimitedSecFDBBaseSizes() throws Exception {
		FDBBaseWriterWrapper writer = FDBBaseWriterWrapper.create(0, 10000);
		assertEquals(0, writer.getMinMainBaseSize());
		assertEquals(0, writer.getMinSecondaryBaseSize());
	}
	
	@Test
	public void testInvalidMainNegativeFDBBaseSizes() throws Exception {
		FDBBaseWriterWrapper writer = FDBBaseWriterWrapper.create(TEST_SEC_PART_SIZE, -1000);
		assertEquals(TEST_SEC_PART_SIZE, writer.getMinMainBaseSize());
		assertEquals(TEST_SEC_PART_SIZE, writer.getMinSecondaryBaseSize());
	}
	
	@Test
	public void testInvalidMainUnlimitedFDBBaseSizes() throws Exception {
		FDBBaseWriterWrapper writer = FDBBaseWriterWrapper.create(TEST_SEC_PART_SIZE, 0);
		assertEquals(TEST_SEC_PART_SIZE, writer.getMinMainBaseSize());
		assertEquals(TEST_SEC_PART_SIZE, writer.getMinSecondaryBaseSize());
	}
	
}

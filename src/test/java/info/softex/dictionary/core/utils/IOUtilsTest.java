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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.junit.Test;

/**
 * 
 * @since version 4.8, 05/13/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class IOUtilsTest {
	
	protected final static String UTF8 = "UTF-8";
	
	protected final static String ST_SAMPLE = "unicode smiley \u263A and umbrella \u2602";

	@Test
	public void testCopy() throws UnsupportedEncodingException, IOException {
		
		byte[] stSampleBytes = ST_SAMPLE.getBytes(UTF8);
		
		InputStream is = new ByteArrayInputStream(stSampleBytes);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		long length = IOUtils.copy(is, os);
		
		assertEquals(stSampleBytes.length, length);
		assertEquals(stSampleBytes.length, os.toByteArray().length);
		
		assertEquals(Arrays.toString(stSampleBytes), Arrays.toString(os.toByteArray()));
		
		is.close();
		os.close();
		
	}
	
}

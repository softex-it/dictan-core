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
import info.softex.dictionary.core.testutils.MavenUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.junit.Test;

/**
 * 
 * @since version 4.5,		04/02/2014
 * 
 * @modified version 4.8,	05/04/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FileUtilsTest {
	
	protected final static String UTF8 = "UTF-8";
	
	protected final static String ST_SAMPLE = "unicode smiley \u263A and umbrella \u2602";
	protected final static byte[] ST_SAMPLE_BT = new byte[] { 
		117, 110, 105, 99, 111, 100, 101, 32, 115, 109, 105, 108, 101, 121, 32, -30, -104, 
		-70, 32, 97, 110, 100, 32, 117, 109, 98, 114, 101, 108, 108, 97, 32, -30, -104, -126
	}; 

	protected final static String ST_SAMPLE_FILE = "/info/softex/dictionary/core/utils/unicode_samples.txt";

	protected final static byte[] ST_SAMPLE_FILE_BT = new byte[] { 
		115, 111, 109, 101, 32, 105, 110, 112, 117, 116, 32, 119, 105, 116, 104, 32, 85, 110, 
		105, 99, 111, 100, 101, 32, 115, 121, 109, 112, 111, 108, 115, 10, 10, 73, 116, 32, 109, 
		97, 121, 32, 98, 101, 32, 117, 115, 101, 102, 117, 108, 32, 116, 111, 32, 102, 111, 114, 
		32, 102, 105, 108, 101, 32, 117, 116, 105, 108, 115, 32, 116, 101, 115, 116, 115, 10, 10, 
		83, 111, 109, 101, 32, 117, 110, 105, 99, 111, 100, 101, 32, 99, 104, 97, 114, 97, 99, 
		116, 101, 114, 115, 58, 10, 10, -30, -104, -70, 10, -30, -104, -126, 10
	}; 
	
	@Test
	public void testImageExtensions() {
		assertEquals("", FileUtils.getFileExtension(null));
		assertEquals("", FileUtils.getFileExtension(""));
		assertEquals("", FileUtils.getFileExtension("myfile."));
		assertEquals("", FileUtils.getFileExtension("myfile"));
		assertEquals("pNg", FileUtils.getFileExtension("myfile.pNg"));
		assertEquals("bmp", FileUtils.getFileExtension("myfile.ext.bmp"));
	}
	
	@Test
	public void testToByteArray() throws UnsupportedEncodingException, IOException {
		
		assertEquals(
			Arrays.toString(ST_SAMPLE_BT), 
			Arrays.toString(FileUtils.toByteArray(new ByteArrayInputStream(ST_SAMPLE.getBytes(UTF8))))
		);

		assertEquals(
			new String(ST_SAMPLE_FILE_BT, UTF8), 
			new String(FileUtils.toByteArray(MavenUtils.getCodeSourceRelevantFile(ST_SAMPLE_FILE)), UTF8)
		);
		
	}
	
}

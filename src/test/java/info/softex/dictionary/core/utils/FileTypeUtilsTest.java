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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @since version 4.6.0,	02/17/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FileTypeUtilsTest {

	@Test
	public void testImageExtensions() {
		assertTrue(FileTypeUtils.isImageExtension("jpg"));
		assertTrue(FileTypeUtils.isImageExtension("jpEg"));	
		assertTrue(FileTypeUtils.isImageExtension("Tif"));	
		assertTrue(FileTypeUtils.isImageExtension("Png"));	
		assertTrue(FileTypeUtils.isImageExtension("bmp"));	
	}
	
	@Test
	public void testAudioExtensions() {
		assertTrue(FileTypeUtils.isAudioExtension("waV"));	
		assertTrue(FileTypeUtils.isAudioExtension("mp3"));
		assertTrue(FileTypeUtils.isAudioExtension("oGG"));		
	}
	
	@Test
	public void testVideoExtensions() {
		assertTrue(FileTypeUtils.isVideoExtension("avi"));
	}
	
}

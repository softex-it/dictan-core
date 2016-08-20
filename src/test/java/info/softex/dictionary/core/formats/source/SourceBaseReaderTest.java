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

package info.softex.dictionary.core.formats.source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.formats.api.BaseReader;

import java.io.File;
import java.net.URL;

import org.junit.Test;

/**
 * 
 * @since version 4.5, 04/05/2014
 * 
 * @modified version 4.6, 03/05/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class SourceBaseReaderTest {
	
	private final static String PATH_BASE_SOURCE_FULL = "/info/softex/dictionary/core/formats/source/testbasefull";
	
	@Test
	public void testSourceBaseFull() throws Exception {
		URL url = getClass().getResource(PATH_BASE_SOURCE_FULL);
		BaseReader srcReader = new SourceBaseReader(new File(url.getPath()));
		srcReader.load();
		
		assertNotNull(srcReader.getWords());
		assertEquals(4, srcReader.getWords().size());
		
		assertNotNull(srcReader.getAbbreviationKeys());
		assertEquals(2, srcReader.getAbbreviationKeys().size());
		
		assertNotNull(srcReader.getMediaResourceKeys());
		assertEquals(1, srcReader.getMediaResourceKeys().size());
	}
	
	@Test
	public void testSourceReaderFormatInfo() throws Exception {
		FormatInfo sbrFormatInfo = SourceBaseReader.FORMAT_INFO;
		assertFalse(sbrFormatInfo.hasKeysOnly());
	}

}

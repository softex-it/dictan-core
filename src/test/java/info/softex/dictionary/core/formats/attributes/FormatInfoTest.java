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

package info.softex.dictionary.core.formats.attributes;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.formats.fdb.FDBBaseReader;
import info.softex.dictionary.core.formats.zd.ZDBaseReader;

import org.junit.Test;

/**
 * 
 * @since version 2.6, 09/03/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class FormatInfoTest {

	@Test
	public void testZDFormatInfo() throws ClassNotFoundException {
		
		FormatInfo format = FormatInfo.buildFormatInfoFromAnnotation(ZDBaseReader.class);
		
		assertNotNull(format); 
		assertEquals(format.getName(), "ZD");
		assertEquals(format.getPrimaryExtension(), ".zd");
		
		assertArrayEquals(format.getExtensions().toArray(), new String [] {".zd"});
		
	}
	
	@Test
	public void testFDBFormatInfo() throws ClassNotFoundException {
		
		FormatInfo format = FormatInfo.buildFormatInfoFromAnnotation(FDBBaseReader.class);
		
		assertNotNull(format);
		assertEquals(format.getName(), "FDB");
		assertEquals(format.getPrimaryExtension(), ".fdb");
		
		assertArrayEquals(format.getExtensions().toArray(), new String [] {".fdb"});
		
	}
	
}

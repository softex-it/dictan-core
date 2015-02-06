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

package info.softex.dictionary.core.formats.dsl.testutils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * 
 * @since version 4.6, 02/04/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseReaderTestFactory {

	public static DSLBaseReaderWrapper createAndAssertDSLBaseReader(String resourcePath) throws Exception {
		
		assertNotNull(resourcePath);
		URL resource = DSLBaseReaderTestFactory.class.getResource(resourcePath);
		assertNotNull(resource);
			
		DSLBaseReaderWrapper reader = new DSLBaseReaderWrapper(new File(resource.getPath()));
		reader.load();
		
		assertTrue(reader.isLoaded());
		
		BasePropertiesInfo baseInfo = reader.getBasePropertiesInfo();
		
		assertNotNull(baseInfo);
		
		List<String> words = reader.getWords();
		List<Long> pointers = reader.getLinePointers();
		
		assertNotNull(words);
		assertNotNull(pointers);
		
		return reader;
		
	}
	
}

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
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.formats.api.BaseReader;
import info.softex.dictionary.core.formats.dsl.DSLBaseReader;

import java.io.File;
import java.util.List;

/**
 * Test for main functionality of DSL base reader.
 * 
 * @since version 4.6, 02/01/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLConverter {
	
	public static void main(String[] args) throws Exception {
		
		//URL url = getClass().getResource(PATH_BASE_DSL_FULL);
		BaseReader reader = new DSLBaseReader(new File("/Volumes/Media/ln/convoxford"));
		reader.load();
		
		BasePropertiesInfo baseInfo = reader.getBasePropertiesInfo();
		
		System.out.println("Header: " + baseInfo.getHeaderComments());
		
		List<String> words = reader.getWords();
		
		assertNotNull(words);
		//assertEquals(2200000, words.size());
		
		System.out.println("Redirects: " + reader.getWordRedirects());
		
		
		System.out.println("Words Number: " + words.size());
		
		
	}

}

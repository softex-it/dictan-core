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
import info.softex.dictionary.core.testutils.TestUtils;

import java.io.File;
import java.util.List;

/**
 * 
 * @since version 4.6, 02/04/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseReaderFactory {
	
	protected final static String PATH_BASE_DSL_SYNTAX = "/info/softex/dictionary/core/formats/dsl/bases/syntax";
	protected final static String PATH_BASE_DSL_LAYOUTS_ORIG = "/info/softex/dictionary/core/formats/dsl/bases/layouts";
	protected final static String PATH_BASE_DSL_LAYOUTS_ADAPTED = "/info/softex/dictionary/core/formats/dsl/bases/layoutsadapted";
	
	public static DSLBaseReaderWrapper createAndAssertDSLBaseReader(String resourcePath) throws Exception {
		
		File srcFile = TestUtils.getCodeSourceRelevantFile(resourcePath);
			
		DSLBaseReaderWrapper reader = new DSLBaseReaderWrapper(srcFile);
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
	
	public static DSLBaseReaderWrapper createAndAssertSyntaxDSLBaseReader() throws Exception {
		return DSLBaseReaderFactory.createAndAssertDSLBaseReader(PATH_BASE_DSL_SYNTAX);
	}
	
	public static DSLBaseReaderWrapper createAndAssertLayoutsDSLBaseReader() throws Exception {
		return DSLBaseReaderFactory.createAndAssertDSLBaseReader(PATH_BASE_DSL_LAYOUTS_ORIG);
	}
	
	public static DSLBaseReaderWrapper createAndAssertLayoutsAdaptedDSLBaseReader() throws Exception {
		return DSLBaseReaderFactory.createAndAssertDSLBaseReader(PATH_BASE_DSL_LAYOUTS_ADAPTED);
	}
	
}

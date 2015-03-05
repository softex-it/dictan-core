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
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.BaseResourceKey;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.api.BaseReader;
import info.softex.dictionary.core.testutils.BaseReaderAssertUtils;
import info.softex.dictionary.core.testutils.MavenUtils;
import info.softex.dictionary.core.utils.StringUtils;

import java.io.File;
import java.util.List;

/**
 * 
 * @since version 4.6, 02/04/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseAssertUtils {

	/**
	 * Creates and asserts the DSLBaseReader with regards to all specifics of DSL format.
	 */
	public static DSLBaseReaderWrapper createAndAssertDSLBaseReader(File dslPath) throws Exception {
			
		DSLBaseReaderWrapper reader = new DSLBaseReaderWrapper(dslPath);
		reader.load();
		
		BaseReaderAssertUtils.assertBaseReaderIntegrity(reader);
		
		List<Long> pointers = reader.getLinePointers();
		assertNotNull(pointers);
		assertTrue(pointers.size() > 0);
		
		return reader;
		
	}
	
	public static DSLBaseReaderWrapper createAndAssertDSLBaseReader(String codeSourcePath) throws Exception {
		return createAndAssertDSLBaseReader(MavenUtils.getCodeSourceRelevantFile(codeSourcePath));
	}
	
	public static void assertMainDSLBaseReaderParametersEqualByProperties(
			BasePropertiesInfo propsExp, BaseReader readerAct, boolean artResAvailable, boolean abbrResAvailable) throws BaseFormatException {
		
		BaseReaderAssertUtils.assertMainBaseReaderParametersEqualByProperties(propsExp, readerAct);
		
		// Base resources
		BaseResourceInfo artResource = readerAct.getBaseResourceInfo(BaseResourceKey.BASE_ARTICLES_META_DSL.getKey());
		BaseResourceInfo abbrResource = readerAct.getBaseResourceInfo(BaseResourceKey.BASE_ABBREVIATIONS_META_DSL.getKey());

		if (artResAvailable) {
			assertTrue("DSL Article Resource", artResource.getByteArray().length > 0);
			assertTrue(StringUtils.isNotBlank(artResource.getInfo1()));
			assertTrue(StringUtils.isNotBlank(artResource.getInfo2()));
		}

		if (abbrResAvailable) {
			assertTrue("DSL Abbreviation Resource", abbrResource.getByteArray().length > 0);
			assertTrue(StringUtils.isNotBlank(abbrResource.getInfo1()));
			assertTrue(StringUtils.isNotBlank(abbrResource.getInfo2()));
		}
		
	}
	
	public static DSLBaseWriterWrapper createAndAssertDSLBaseWriter(File resourcePath) throws Exception {
		DSLBaseWriterWrapper writer = new DSLBaseWriterWrapper(resourcePath);
		assertNotNull(writer.getFormatInfo());
		return writer;
	}
	
}

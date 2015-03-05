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

package info.softex.dictionary.core.conversions;

import info.softex.dictionary.core.conversions.testutils.DSLToFDBConverter;
import info.softex.dictionary.core.conversions.testutils.FDBToDSLConverter;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseAssertUtils;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.formats.dsl.testutils.content.DSLBaseLayoutsContent;
import info.softex.dictionary.core.testutils.MavenUtils;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests DSL to FDB and FDB to DSL conversions.
 * 
 * @since version 4.6, 		02/09/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class DSLToFDBBidiConversionTest {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected final static String FDB_FILE_1 = "layouts_base_from_dsl.fdl";
	protected final static String FDB_FILE_2 = "layouts_base_from_dsl_preconverted.fdl";
	protected final static String DSL_PATH = "layouts_base_from_fdl.dsl";
	
	protected File outFdbFile1 = null;
	protected File outFdbFile2 = null;
	protected File outDslPathFile = null;
	
	@Before
	public void doBefore() throws Exception {
		outFdbFile1 = MavenUtils.getMavenTestDictFile(FDB_FILE_1);
		outFdbFile2 = MavenUtils.getMavenTestDictFile(FDB_FILE_2);
		outDslPathFile = MavenUtils.getMavenTestDictFile(DSL_PATH);
		outDslPathFile.mkdirs();
	}

	@Test
	public void testCompositeDslToBdbConversion() throws Exception {

		testDSLToFDBConversion();
		testDSLToFDBConvertedBase();
		
		testFDBToDSLConversion();
		testFDBToDSLConvertedBase();
		
		testDSLToFDBConversionDSLPreconverted();
		testDSLToFDBConvertedBaseDSLPreconverted();
		
	}
	
	public void testDSLToFDBConversion() throws Exception {
		DSLBaseReaderWrapper reader = DSLBaseLayoutsContent.createAndAssertLayoutsDSLBaseReader();
		DSLToFDBConverter.testDSLToFDBConversion(reader, outFdbFile1);
	}
	
	public void testDSLToFDBConvertedBase() throws Exception {
		DSLBaseReaderWrapper readerExp = DSLBaseLayoutsContent.createAndAssertLayoutsDSLBaseReader();
		DSLToFDBConverter.testDSLToFDBConvertedBase(readerExp, outFdbFile1);
		log.info("DSL base is converted to FDB and verified");
	}
	
	public void testFDBToDSLConversion() throws Exception {
		FDBToDSLConverter.testFDBToDSLConversion(outFdbFile1, outDslPathFile);
	}
	
	public void testFDBToDSLConvertedBase() throws Exception {
		DSLBaseReaderWrapper readerExp = DSLBaseLayoutsContent.createAndAssertLayoutsDSLBaseReader();
		DSLBaseReaderWrapper readerAct = DSLBaseAssertUtils.createAndAssertDSLBaseReader(outDslPathFile);
		FDBToDSLConverter.testFDBToDSLConvertedBase(readerExp, readerAct);
		log.info("FDB base is converted to DSL and verified");
	}
	
	public void testDSLToFDBConversionDSLPreconverted() throws Exception {
		DSLBaseReaderWrapper reader = DSLBaseAssertUtils.createAndAssertDSLBaseReader(outDslPathFile);
		DSLToFDBConverter.testDSLToFDBConversion(reader, outFdbFile2);
	}
	
	public void testDSLToFDBConvertedBaseDSLPreconverted() throws Exception {
		DSLBaseReaderWrapper readerExp = DSLBaseLayoutsContent.createAndAssertLayoutsDSLBaseReader();
		DSLToFDBConverter.testDSLToFDBConvertedBase(readerExp, outFdbFile2);
		log.info("DSL base is converted to FDB and verified");
	}
	
}

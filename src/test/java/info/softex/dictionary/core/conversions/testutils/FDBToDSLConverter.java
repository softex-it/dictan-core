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

package info.softex.dictionary.core.conversions.testutils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.BaseResourceKey;
import info.softex.dictionary.core.conversions.ConversionUtils;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseAssertUtils;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseWriterWrapper;
import info.softex.dictionary.core.formats.dsl.testutils.content.DSLBaseLayoutsContent;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseIOFactory;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseReaderWrapper;
import info.softex.dictionary.core.testutils.BaseReaderAssertUtils;

import java.io.File;

/**
 * 
 * @since version 4.6, 		02/27/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBToDSLConverter {
	
	public static void testFDBToDSLConversion(File fdbFile, File dslPath) throws Exception {
		
		FDBBaseReaderWrapper reader = FDBBaseIOFactory.createAndAssertFDBBaseReader(fdbFile);
		
		DSLBaseWriterWrapper writer = DSLBaseAssertUtils.createAndAssertDSLBaseWriter(dslPath);
		writer.createBase();
		
		ConversionUtils.convert(reader, writer, new TestConversionObserver());
		
		reader.close();
		writer.close();
		
	}
	
	public static void testFDBToDSLConvertedBase(DSLBaseReaderWrapper readerExp, DSLBaseReaderWrapper readerAct) throws Exception {
		
		readerExp.load();
		readerAct.load();
		
		BaseReaderAssertUtils.assertBaseReaderIntegrity(readerExp);
		BaseReaderAssertUtils.assertBaseReaderIntegrity(readerAct);
		
		BaseReaderAssertUtils.assertBaseAbbrevsDefsEqual(readerExp, readerAct);
		BaseReaderAssertUtils.assertBaseMediaResourceEqual(readerExp, readerAct);
		
		DSLBaseLayoutsContent.assertReaderHasUniqueLayoutsContentFdb2Dsl(readerAct);
		
		// Base resources
		BaseResourceInfo artMeta = readerAct.getBaseResourceInfo(BaseResourceKey.BASE_ARTICLES_META_DSL.getKey());
		BaseResourceInfo abbrMeta = readerAct.getBaseResourceInfo(BaseResourceKey.BASE_ABBREVIATIONS_META_DSL.getKey());
		
		assertNotNull(artMeta);
		assertTrue(artMeta.getByteArray().length > 0);
		assertFalse(artMeta.getInfo1().isEmpty());
		assertFalse(artMeta.getInfo2().isEmpty());
		
		assertNotNull(abbrMeta);
		assertTrue(abbrMeta.getByteArray().length > 0);
		assertFalse(abbrMeta.getInfo1().isEmpty());
		assertFalse(abbrMeta.getInfo2().isEmpty());
		
	}

}

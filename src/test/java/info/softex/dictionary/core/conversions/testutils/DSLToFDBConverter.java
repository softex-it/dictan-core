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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.AbbreviationsFormattingMode;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.ArticlesFormattingInjectWordMode;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.ArticlesFormattingMode;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.BaseResourceKey;
import info.softex.dictionary.core.conversions.ConversionUtils;
import info.softex.dictionary.core.formats.api.BaseReader;
import info.softex.dictionary.core.formats.dsl.testutils.DSLBaseReaderWrapper;
import info.softex.dictionary.core.formats.dsl.testutils.content.DSLBaseLayoutsContent;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseIOFactory;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseReaderWrapper;
import info.softex.dictionary.core.formats.fdb.testutils.FDBBaseWriterWrapper;
import info.softex.dictionary.core.testutils.BaseReaderAssertUtils;

import java.io.File;
import java.util.List;

/**
 * 
 * @since version 4.6, 		02/27/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class DSLToFDBConverter {

	public static void testDSLToFDBConversion(DSLBaseReaderWrapper dslReader, File fdbOutFile) throws Exception {
		
		// Writer
		BasePropertiesInfo writerBPI = new BasePropertiesInfo();
		writerBPI.setArticlesFormattingMode(ArticlesFormattingMode.DSL);
		writerBPI.setAbbreviationsFormattingMode(AbbreviationsFormattingMode.DSL);
		writerBPI.setArticlesFormattingInjectWordMode(ArticlesFormattingInjectWordMode.AUTO);

		fdbOutFile.delete();
		FDBBaseWriterWrapper writer = FDBBaseIOFactory.createAndAssertFDBBaseWriter(fdbOutFile, writerBPI);
		
		ConversionUtils.convert(dslReader, writer, new TestConversionObserver());
		
		dslReader.close();
		writer.close();
		
	}
	
	public static void testDSLToFDBConvertedBase(BaseReader readerExp, File fdbFile) throws Exception {
		
		FDBBaseReaderWrapper readerAct = FDBBaseIOFactory.createAndAssertFDBBaseReader(fdbFile);

		List<String> words = readerAct.getWords();
		
		// Assert main parameters
		DSLBaseLayoutsContent.assertReaderHasUniqueLayoutsContentDslToFdb(readerAct);
		
		BaseReaderAssertUtils.assertBaseAbbrevsDefsEqual(readerExp, readerAct);
		BaseReaderAssertUtils.assertBaseMediaResourceEqual(readerExp, readerAct);
		
		assertEquals(DSLBaseLayoutsContent.WORD_NONINDEXED_AE2_SPEC, words.get(5));
		assertEquals(DSLBaseLayoutsContent.WORD_NONINDEXED_AE3_PE, words.get(9));
		
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
		
		BaseReaderAssertUtils.assertBaseReaderIntegrity(readerExp);
		BaseReaderAssertUtils.assertBaseReaderIntegrity(readerAct);
		//TestBaseAssertUtils.assertMainBaseReaderParametersEqual(readerExp, readerAct);
		
		readerExp.close();
		readerAct.close();
		
	}
	
}

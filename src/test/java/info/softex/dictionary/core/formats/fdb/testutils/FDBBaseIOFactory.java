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

package info.softex.dictionary.core.formats.fdb.testutils;

import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.collation.BasicCollatorFactory;
import info.softex.dictionary.core.collation.CollationRulesFactory;
import info.softex.dictionary.core.collation.CollationRulesFactory.SimpleCollationProperties;
import info.softex.dictionary.core.database.BasicSQLiteConnectionFactory;
import info.softex.dictionary.core.formats.api.BaseFormatException;

import java.io.File;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Locale;

/**
 * 
 * @since version 4.6,	02/09/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBBaseIOFactory {
	
	public static FDBBaseWriterWrapper createFDBBaseWriter(File file, BasePropertiesInfo baseInfo) throws Exception {
		
		if (file.exists()) {
			file.delete();
		}
		
		FDBBaseWriterWrapper writer = new FDBBaseWriterWrapper(file.getAbsolutePath(), new BasicSQLiteConnectionFactory(), null);
		writer.createBase();
		
		// Populate base properties
		baseInfo.setBaseVersion(6,3);
		baseInfo.setBaseShortName(FDBBaseSampleContent.SHORT_NAME);
		baseInfo.setBaseFullName(FDBBaseSampleContent.FULL_NAME);
		
		writer.saveBasePropertiesInfo(baseInfo);
		
		// Populate language directions
		LanguageDirectionsInfo dirs = new LanguageDirectionsInfo();
		dirs.setDefaultCollationProperties(CollationRulesFactory.createDefaultCollationProperties().getCollationRules(), null, 10);
		int ver = 0;
		SimpleCollationProperties props = CollationRulesFactory.createLocaleAppendixCollationProperties(new Locale("ru"));
		dirs.addDirection("ru", "en", props.getCollationRules(), "", Collator.PRIMARY, Collator.CANONICAL_DECOMPOSITION, ver, props.isCollationIndependent());
		SimpleCollationProperties props2 = CollationRulesFactory.createLocaleAppendixCollationProperties(new Locale("en"));
		dirs.addDirection("en", "ru", props2.getCollationRules(), "", Collator.PRIMARY, Collator.CANONICAL_DECOMPOSITION, ver, props2.isCollationIndependent());	
		
		writer.saveLanguageDirectionsInfo(dirs);
		
		return writer;
		
	}
	
	public static FDBBaseReaderWrapper createAndAssertFDBBaseReader(File file) throws SQLException, BaseFormatException {
		
		FDBBaseReaderWrapper reader = new FDBBaseReaderWrapper(file, new BasicSQLiteConnectionFactory(), null, new BasicCollatorFactory());
		reader.load();
		
		reader.verifyBaseIntegrity();
		
		return reader;
		
	}

}

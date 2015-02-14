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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.collation.AbstractCollatorFactory;
import info.softex.dictionary.core.database.DatabaseConnectionFactory;
import info.softex.dictionary.core.formats.fdb.FDBBaseReader;
import info.softex.dictionary.core.formats.fdb.FDBTables;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * 
 * @since version 4.6, 02/12/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBBaseReaderWrapper extends FDBBaseReader {
	
	public FDBBaseReaderWrapper(File fdbFile, DatabaseConnectionFactory conFactory, Map<String, ?> inParams, AbstractCollatorFactory collatorFactory) throws SQLException {
		super(fdbFile, conFactory, inParams, collatorFactory);
	}

	public void verifyBaseIntegrity() throws SQLException {
		
		BasePropertiesInfo baseInfo = getBasePropertiesInfo();
		assertNotNull(baseInfo);
		assertTrue(baseInfo.getWordsNumber() > 0);
		assertTrue(baseInfo.getWordsRelationsNumber() > 0);
		assertTrue(baseInfo.getWordsMappingsNumber() > 0);
		
		Statement st = mainBase.createStatement();
		
		// Words number
		ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM " + FDBTables.words);
		rs1.next();
		assertEquals(baseInfo.getWordsNumber(), rs1.getInt(1));
		
		// Relations number
		ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM " + FDBTables.words_relations);
		rs2.next();
		assertEquals(baseInfo.getWordsRelationsNumber(), rs2.getInt(1));
		
		// Mappings number
		ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM " + FDBTables.words_mappings);
		rs3.next();
		assertEquals(baseInfo.getWordsMappingsNumber(), rs3.getInt(1));
		
	}

}

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

package info.softex.dictionary.core.formats.fdb;

import info.softex.dictionary.core.database.BasicSQLiteConnectionFactory;
import info.softex.dictionary.core.database.DatabaseConnectionFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @since version 4.5, 03/29/2014
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBBaseWriterWrapper extends FDBBaseWriter {
	
	public FDBBaseWriterWrapper(String baseFilePath, DatabaseConnectionFactory conFactory, Map<String, String> params) throws SQLException, IOException {
		super(baseFilePath, conFactory, params);
	}
	
	public long getMinMainBaseSize() {
		return minMainBaseSize;
	}
	
	public long getMinSecondaryBaseSize() {
		return minSecondaryBaseSize;
	}
	
	public static FDBBaseWriterWrapper create(long secPartsSize, long mainPartSize) throws SQLException, IOException {
		Map<String, String> wparams = new HashMap<String, String>();
		wparams.put(FDBConstants.PARAM_KEY_BASE_MAIN_SIZE_LIMIT, String.valueOf(mainPartSize));
		wparams.put(FDBConstants.PARAM_KEY_BASE_SECONDARY_SIZE_LIMIT, String.valueOf(secPartsSize));
		return new FDBBaseWriterWrapper("", new BasicSQLiteConnectionFactory(), wparams);
	}
	
}

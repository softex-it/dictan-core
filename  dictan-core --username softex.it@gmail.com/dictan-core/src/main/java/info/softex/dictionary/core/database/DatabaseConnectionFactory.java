/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2012  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * 
 * @since version 2.6, 09/07/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public interface DatabaseConnectionFactory {
	
	public static final String DB_NO_LOCALIZED_COLLATORS = "NO_LOCALIZED_COLLATORS";
	public static final String DB_CREATE_IF_NECESSARY = "CREATE_IF_NECESSARY";
	public static final String DB_OPEN_READ_ONLY = "OPEN_READONLY";
	
	public static final String PROP_JDBC_DRIVER = "PROP_JDBC_DRIVER";

	public Connection createConnection(String url, Map<String, String> params) throws SQLException;
	
}

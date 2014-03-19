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

package info.softex.dictionary.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.6, 09/07/2011
 * 
 * @modified version 3.9, 12/03/2013
 * @modified version 4.4, 03/19/2014
 * 
 * @author Dmitry Viktorov
 * 
 */
public class BasicSQLiteConnectionFactory implements DatabaseConnectionFactory {
	
	private static final Logger log = LoggerFactory.getLogger(BasicSQLiteConnectionFactory.class);
	
	protected static final String DEFAULT_JDBC_DRIVER = "org.sqlite.JDBC";
	
	protected static final String SYSPROP_SQLITE_LIB_NAME_KEY = "org.sqlite.lib.name";
	protected static final String SYSPROP_SQLITE_LIB_NAME_VALUE = "sqlitejdbc";
	protected static final String SYSPROP_SQLITE_LIB_NAME_EXT = ".jnilib";
	
	/**
	 * 
	 * PROP_DATABASE_PATH - DB Path - required
	 * PROP_JDBC_DRIVER - Driver class name - optional
	 * 
	 */
	@Override
	public Connection createConnection(String url, Map<String, String> params) throws SQLException {
		
		if (url == null) {
			throw new IllegalArgumentException("The DB URL is required!");
		}
		
		String driverClassName = DEFAULT_JDBC_DRIVER;
		if (params != null && params.get(PROP_JDBC_DRIVER) != null && params.get(PROP_JDBC_DRIVER).length() != 0) {
			driverClassName = params.get(PROP_JDBC_DRIVER);
		}
		
		// Use pure Java library
		//System.setProperty("sqlite.purejava", "true");

		// Load the sqlite-JDBC driver using the current class loader
		try {
			
			Class.forName(driverClassName);
			
			// Fix the change at Java 7 at OS X where mapping to .jnilib was changed to .dylib
			String sqliteFullLibName = System.mapLibraryName(SYSPROP_SQLITE_LIB_NAME_VALUE);
			log.debug("SQLite native library name: {}", sqliteFullLibName);
			
			if (sqliteFullLibName != null && !sqliteFullLibName.endsWith(SYSPROP_SQLITE_LIB_NAME_EXT)) {
				int dotInd = sqliteFullLibName.lastIndexOf(".");
				if (dotInd > 0) {
					sqliteFullLibName = sqliteFullLibName.substring(0, dotInd) + SYSPROP_SQLITE_LIB_NAME_EXT;
					log.info("SQLite native library name changed to: {}", sqliteFullLibName);
					System.setProperty(SYSPROP_SQLITE_LIB_NAME_KEY, sqliteFullLibName);
				}
			}
			
		} catch (ClassNotFoundException e) {
			log.error("Error", e);
			throw new SQLException("Couldn't find the JDBC Driver: " + driverClassName + "; Reason: " + e.getMessage());
		}

		// http://www.xerial.org/trac/Xerial/wiki/SQLiteJDBC#RuninPure-Javamode
		
		//SQLiteConfig config = new SQLiteConfig();
		//config.setSynchronous(SynchronousMode.OFF);
		//config.enableRecursiveTriggers(false);
		//config.setReadUncommited(true);
		//config.setSharedCache(false);
		//config.setTempStore(TempStore.DEFAULT);
		//config.setReadOnly(true);  
		//config.recursiveTriggers(true);
		
		Properties props = new Properties();
		props.put("synchronous", "OFF");
		
		// Create a database connection
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + url, props);
//		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + url);
//		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath, config.toProperties());
		
		//conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		
		return conn;
	}

}

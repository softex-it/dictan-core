package info.softex.dictionary.core.formats.fdb;

import info.softex.dictionary.core.formats.commons.BaseFormatException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZDBRead {
	
	private final static Logger log = LoggerFactory.getLogger(ZDBRead.class.getSimpleName());
	
	public static void main(String[] args) throws BaseFormatException, Exception {
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {

			//String path = "../dicts/Wiki_RU_2011.08.01_1.0.zdb";
			String path = "../dicts/new_dictionary_50.fdb";
			
			// words 743330
			connection = DriverManager.getConnection("jdbc:sqlite:" + path);
			Statement statement = connection.createStatement();
			
//			FDBBaseReader reader = new FDBBaseReader(connection);
//			reader.load();
			
			//ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name");
			//ResultSet rs = statement.executeQuery("SELECT * FROM dict");
			//ResultSet rs = statement.executeQuery("SELECT * FROM info");
			
			//ResultSet rs = statement.executeQuery("pragma collation_list");
			
			//ResultSet rs = statement.executeQuery("PRAGMA table_info(android_metainfo)");
			
			ResultSet rs = statement.executeQuery("select * from android_metainfo");
			
			//ResultSet rs = statement.executeQuery("PRAGMA INDEX_LIST(dict)");
			
			//version INTEGER, description TEXT, icon BLOB, lang_word TEXT, lang_trans TEXT, css TEXT, dont_autoformat BOOL)"
			
			while (rs.next()) {
				System.out.print(rs.getString(1));
				System.out.print(" | " + rs.getString(2));
//				System.out.print(" | " + rs.getString(3));
//				System.out.print(" | " + rs.getString(4));
//				System.out.print(" | " + rs.getString(5));
//				System.out.print(" | " + rs.getString(6));
//				System.out.println(" | " + rs.getString(7));
			}
//			
		} catch (SQLException e) {
			// If the error message is "out of memory", it probably means no database file is found
			log.error("Error", e);
		} 
//		finally {
//			try {
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				log.error("Error", e);
//			}
//		}
		
	}
	
//	public static void main(String[] args) throws ClassNotFoundException {
//		
//		// load the sqlite-JDBC driver using the current class loader
//		Class.forName("org.sqlite.JDBC");
//
//		Connection connection = null;
//		try {
//			// create a database connection
//			connection = DriverManager.getConnection("jdbc:sqlite:../dicts/ruwiki-01082011.zdb");
//			Statement statement = connection.createStatement();
//			statement.setQueryTimeout(30); // set timeout to 30 sec.
//
//			//statement.executeUpdate("drop table if exists words");
//			//statement.executeUpdate("create table person (id integer, name string)");
//			//statement.executeUpdate("create table words (id integer, word string)");
//			//statement.executeUpdate("insert into person values(1, 'кто то')");
//			//statement.executeUpdate("insert into person values(2, 'yui')");
//			
//			ResultSet rs = statement.executeQuery("select * from person");
//			while (rs.next()) {
//				// read the result set
//				System.out.println("name = " + rs.getString("name"));
//				System.out.println("id = " + rs.getInt("id"));
//			}
//		} catch (SQLException e) {
//			// if the error message is "out of memory", it probably means no database file is found
//			System.err.println(e.getMessage());
//		} finally {
//			try {
//				if (connection != null)
//					connection.close();
//			} catch (SQLException e) {
//				// connection close failed.
//				System.err.println(e);
//			}
//		}
//		
//	}

}

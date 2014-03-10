package info.softex.dictionary.core.formats.fdb;

import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.database.BasicSQLiteConnectionFactory;
import info.softex.dictionary.core.formats.api.BaseWriter;


public class FDBase {
	
	//ruwiki-01082011.zdb
	//	abr
	//	dict
	//	0 | word | TEXT | 0 | null | 0
	//	1 | trans | BLOB | 0 | null | 0
	//	2 | trans_uncompessed_size | INTEGER | 0 | null | 0
	//	pack
	
	// langs
	//   in, out
	
	// info 
	//   built_by - TEXT
	//   created_by - TEXT
	//   creation_date - TEXT
	//   base_version - TEXT | INTEGER
	//   version -> format_version - INTEGER

	
	public static void main(String[] args) throws Exception {
		
		// Load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		//Connection connection = null;
		try {
			
			String path = "../dicts/new_dictionary.fdb";
			// create a database connection
			//connection = DriverManager.getConnection("jdbc:sqlite:" + path);
			
			BaseWriter writer = new FDBBaseWriter(path, new BasicSQLiteConnectionFactory(), null);
			
			writer.createBase();
			
			BasePropertiesInfo info = new BasePropertiesInfo();
			//info.setBaseName("New Britannica");
			//info.setBaseVersion("12");
			//info.setBaseLocale(new Locale("RU"));
			//info.setBaseDescription("Test dictionary");
			
			//info.setArticleFormattingEnabled(false);
			
			info.setFormatVersion(1);
			info.setFormatName("FDB");
			
			
			writer.saveBasePropertiesInfo(info);
			
			//-------------------------------
			
			ArticleInfo trInfo = new ArticleInfo(new WordInfo(0, "actual word"), "simple text string");
			writer.saveArticleInfo(trInfo);
			
			//-------------------------------
			
			//FDBBaseReader dr = new FDBBaseReader(connection);
			//dr.load();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
//	public static void main(String[] args) throws ClassNotFoundException, IOException, BaseFormatException {
//		
//		BaseReader dict = new ZDBaseReader(new BasicBaseRegionalResolver(), new File("../dicts/Dict_EN-RU-EN_1.0.zd"));
//		dict.load();
//		List<String> words = dict.getWords();
//
//
//
//		
//		
//		// load the sqlite-JDBC driver using the current class loader
//		Class.forName("org.sqlite.JDBC");
//
//		Connection connection = null;
//		try {
//			// create a database connection
//			connection = DriverManager.getConnection("jdbc:sqlite:../dicts/dictionary.fdb");
//			Statement statement = connection.createStatement();
//			statement.setQueryTimeout(30); // set timeout to 30 sec.
//
//			statement.executeUpdate("drop table if exists words");
//			statement.executeUpdate("create table words (id integer, word string)");
//
//			statement.executeUpdate("drop table if exists translations");
//			statement.executeUpdate("create table translations (id integer, translation string)");
//			
//			String updateWords = "insert into words values(?, ?)";
//			PreparedStatement st = connection.prepareStatement(updateWords);
//			
//			String updateTrans = "insert into translations values(?, ?)";
//			PreparedStatement st2 = connection.prepareStatement(updateTrans);
//
//			for (int i = 0; i < dict.getBaseInfo().getWordsNumber(); i++) {
//
//				st.setInt(1, i);
//				st.setString(2, words.get(i));
//				st.executeUpdate();
//				
//				st2.setInt(1, i);
//				st2.setString(2, dict.getRawTranslation(new WordInfo(null, i)).getTranslation());
//				st2.executeUpdate();
//			}
//			
////			ResultSet rs = statement.executeQuery("select * from person");
////			while (rs.next()) {
////				// read the result set
////				System.out.println("name = " + rs.getString("name"));
////				System.out.println("id = " + rs.getInt("id"));
////			}
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
//	}

	
	
}

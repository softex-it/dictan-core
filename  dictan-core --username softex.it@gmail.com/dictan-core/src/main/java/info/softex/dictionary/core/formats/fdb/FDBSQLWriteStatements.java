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

package info.softex.dictionary.core.formats.fdb;

/**
 * 
 * @since version 2.6, 08/26/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FDBSQLWriteStatements {

	public static final String CREATE_TABLE_WORDS = 
		"CREATE TABLE " + FDBTables.words + 
		" (word_id INTEGER PRIMARY KEY, word TEXT UNIQUE NOT NULL)";
	
	public static final String CREATE_TABLE_ARTICLE_BLOCKS = 
		"CREATE TABLE " + FDBTables.article_blocks + 
		" (article_block_id INTEGER PRIMARY KEY, article_block BLOB NOT NULL)";
	
	public static final String CREATE_TABLE_LANGUAGE_DIRECTIONS = 
		"CREATE TABLE " + FDBTables.language_directions + 
		" (language_direction_id INTEGER PRIMARY KEY, from_locale TEXT NOT NULL, to_locale TEXT NOT NULL)";

	public static final String CREATE_TABLE_ABBREVIATIONS =
		"CREATE TABLE " + FDBTables.abbreviations + 
		" (abbreviation_id INTEGER PRIMARY KEY, abbreviation TEXT UNIQUE NOT NULL, definition BLOB NOT NULL)";
	
	public static final String CREATE_TABLE_MEDIA_RESOURCE_KEYS =
		"CREATE TABLE " + FDBTables.media_resource_keys + 
		" (media_resource_id INTEGER PRIMARY KEY, media_resource_key TEXT UNIQUE NOT NULL)";
	
	public static final String CREATE_TABLE_MEDIA_RESOURCE_BLOCKS =
		"CREATE TABLE " + FDBTables.media_resource_blocks + 
		" (media_resource_block_id INTEGER PRIMARY KEY, media_resource_block BLOB NOT NULL)";

	public static final String CREATE_TABLE_BASE_PROPERTIES = 
		"CREATE TABLE " + FDBTables.base_properties + 
		" (base_property_id INTEGER PRIMARY KEY, base_property_key TEXT UNIQUE NOT NULL, base_property TEXT)";

	public static final String CREATE_TABLE_BASE_RESOURCES = 
		"CREATE TABLE " + FDBTables.base_resources + 
		" (base_resource_id INTEGER PRIMARY KEY, base_resource_key TEXT UNIQUE NOT NULL, base_resource BLOB NOT NULL, data_1 BLOB NOT NULL, data_2 BLOB NOT NULL, data_3 BLOB NOT NULL, info_1 TEXT NOT NULL, info_2 TEXT NOT NULL, info_3 TEXT NOT NULL, info_4 TEXT NOT NULL, info_5 TEXT NOT NULL, info_6 TEXT NOT NULL)";
	
	
	// INSERT --------------------------------------------------------------------------------------------
	public static final String INSERT_BASE_PROPERTY =
		"INSERT INTO " + FDBTables.base_properties + " (base_property_id, base_property_key, base_property) VALUES(?, ?, ?)";
	
	public static final String INSERT_WORD =
		"INSERT INTO " + FDBTables.words + " (word_id, word) VALUES(?, ?)";

	public static final String INSERT_ARTICLE =
		"INSERT INTO " + FDBTables.article_blocks + 
		" (article_block_id, article_block) VALUES(?, ?)";
	
	public static final String INSERT_MEDIA_RESOURCE_KEY =
		"INSERT INTO " + FDBTables.media_resource_keys + " (media_resource_id, media_resource_key) VALUES(?, ?)";

	public static final String INSERT_MEDIA_RESOURCE =
		"INSERT INTO " + FDBTables.media_resource_blocks + " (media_resource_block_id, media_resource_block) VALUES(?, ?)";
	
	public static final String INSERT_ABBREVIATION =
		"INSERT INTO " + FDBTables.abbreviations + "(abbreviation_id, abbreviation, definition) VALUES(?, ?, ?)";
	
	public static final String INSERT_LANGUAGE_DIRECTION =
		"INSERT INTO " + FDBTables.language_directions + " (language_direction_id, from_locale, to_locale) VALUES(?, ?, ?)";
		
	public static final String INSERT_BASE_RESOURCE =
		"INSERT INTO " + FDBTables.base_resources + 
		" (base_resource_id,base_resource_key,base_resource,data_1,data_2,data_3,info_1,info_2,info_3,info_4,info_5,info_6) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";		

	
	// UPDATE ---------------------------------------------------------
	public static final String UDATE_BASE_PROPERTY =
		"UPDATE " + FDBTables.base_properties + " SET base_property=? WHERE base_property_key=?";
	
	// DROP -----------------------------------------------------------
	public static final String DROP_TABLE_UNI = "DROP TABLE IF EXISTS";

}

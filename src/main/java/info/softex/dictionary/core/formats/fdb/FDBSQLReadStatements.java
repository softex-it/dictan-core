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
 * @since version 2.6, 09/15/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FDBSQLReadStatements {

	public static final String SELECT_ALL_BASE_PROPERTIES =
		"SELECT * FROM " + FDBTables.base_properties;
	
	public static final String SELECT_WORDS_NUMER =
		"SELECT MAX(word_id)+1 FROM " + FDBTables.words;

	public static final String SELECT_ABBREVIATIONS =
		"SELECT abbreviation_id, abbreviation, definition FROM " + FDBTables.abbreviations;
	
	public static final String SELECT_ABBREVIATIONS_NUMER =
		"SELECT MAX(abbreviation_id)+1 FROM " + FDBTables.abbreviations;

	public static final String SELECT_MEDIA_RESOURCES_NUMER =
		"SELECT MAX(media_resource_id)+1 FROM " + FDBTables.media_resource_keys;
	
	public static final String SELECT_WORDS_IN_RANGE =	
		"SELECT word FROM " + FDBTables.words + " WHERE word_id BETWEEN (?) AND (?)";
	
	public static final String SELECT_WORD_ID_BY_WORD =	
		"SELECT word_id FROM " + FDBTables.words + " WHERE word=(?)";
	
	public static final String SELECT_MEDIA_RESOURCE_ID_BY_MEDIA_RESOURCE_KEY =	
		"SELECT media_resource_id from " + FDBTables.media_resource_keys + " WHERE media_resource_key=?"; 
	
	public static final String SELECT_MEDIA_RESOURCE_BY_MEDIA_RESOURCE_ID =	
		"SELECT media_resource_block_id, media_resource_block FROM " + FDBTables.media_resource_blocks + 
		" WHERE media_resource_block_id=(SELECT MAX(media_resource_block_id) FROM " + 
		FDBTables.media_resource_blocks + " WHERE media_resource_block_id<=(?))";
	
	public static final String SELECT_ALL_MEDIA_RESOURCE_KEYS =
		"SELECT media_resource_key FROM " + FDBTables.media_resource_keys;
	
	public static final String SELECT_BASE_RESOURCE_DEFAULT_COLLATION_RULES =	
		"SELECT base_resource_id,base_resource_key,base_resource,data_1,data_2,data_3,info_1,info_2,info_3,info_4,info_5,info_6 FROM " + 
		FDBTables.base_resources + " WHERE base_resource_key=\"collation.rules.default\"";
	
	public static final String SELECT_BASE_RESOURCE_LOCALE_COLLATION_RULES =
		"SELECT from_locale,to_locale,base_resource,data_1,data_2,data_3,info_1,info_2,info_3,info_4,info_5,info_6 FROM " + 
		FDBTables.language_directions + "," + FDBTables.base_resources + 
		" WHERE base_resource_key=\"collation.rules.\"||from_locale";
	
	
}

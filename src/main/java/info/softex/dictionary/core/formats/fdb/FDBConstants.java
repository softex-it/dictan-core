/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2018  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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
 * @since version 2.6,		08/28/2011
 * 
 * @modified version 4.0,	02/02/2014
 * @modified version 4.5,	03/29/2014
 * @modified version 4.6,	01/28/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FDBConstants {
	
	public static final int FDB_VERSION_1 = 1;
	public static final int FDB_VERSION_2 = 2;
	public static final int FDB_VERSION_3 = 3;
	
	public static final int CURRENT_FDB_VERSION = FDB_VERSION_3;

	public static final String PARAM_KEY_WORD_LIST_BLOCK_SIZE = "wordListBlockSize";
	
	public static final String PARAM_KEY_BASE_MAIN_SIZE_LIMIT = "mainBaseSizeLimit";
	public static final String PARAM_KEY_BASE_SECONDARY_SIZE_LIMIT = "secondaryBaseSizeLimit";
	
	public static final String PARAM_VALUE_DEFAULT = "default";
	
	public static final int VALUE_WORD_LIST_BLOCK_SIZE_DEFAULT = 256;

	// Relations
	public static final int RELATION_REDIRECT_NORMAL = 1;
	public static final int RELATION_REDIRECT_SUBENTRY = 2;
	
}

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
 * @since version 2.6, 08/28/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FDBConstants {
	
	public static final int FDB_VERSION_1 = 1;

	public static final int CURRENT_FDB_VERSION = FDB_VERSION_1;
	
	
	public static final String PARAM_KEY_WORD_LIST_BLOCK_SIZE = "wordListBlockSize";
	public static final String PARAM_KEY_BASE_SIZE_LIMIT = "baseSizeLimit";
	
	public static final int VALUE_WORD_LIST_BLOCK_SIZE_DEFAULT = 256;
	
}

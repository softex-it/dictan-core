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

package info.softex.dictionary.core.formats.zd;


/**
 * 
 * @since version 2.1, 04/10/2011
 * 
 * @modified version 2.2, 05/08/2011
 * @modified version 2.6, 09/02/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class ZDConstants {
	
	public static final int COMPRESSED_BUFFER_SIZE = 8 * 1024;
	
	public static final int UNCOMPRESSED_BUFFER_SIZE = 3 * 128 * 1024; 

	
	public static final int WORD_LIST_BLOCK_SIZE = 256;
	
	public static final int POOL_READ_STREAMS_NUMBER = 32;
	
	public static final int POOL_READ_THREADS_NUMBER = 4;

	public static final int POOL_RETURN_THREADS_NUMBER = 2;
	
}

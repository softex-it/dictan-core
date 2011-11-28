/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2011  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.stat;

/**
 * 
 * @since version 2.5, 08/06/2011
 * 
 * @modified version 3.0, 11/27/2011
 *  
 * @author Dmitry Viktorov
 * 
 */
@Deprecated
public class BaseConstants {
	
	public static final String URL_PARAM_CHOP = "chop";
	
	public static final String FORMAT_ZPAK_NAME = "ZPAK";
	public static final String FORMAT_ZPAK_EXT = ".zpak";
	
	public static final String HTTP_SCHEME = "http";
	public static final String HTTPS_SCHEME = "https";

	public static final String URLSEG_ABBREVS = "abbrevs";
	public static final String URLSEG_IMAGES = "images";
	public static final String URLSEG_EXTERNAL = "external";
	public static final String URLSEG_INTERNAL = "internal";
	public static final String URLSEG_TRANS_WORD = "trans-word";
	public static final String URLSEG_TRANS_INDEX = "trans-index";
	public static final String URLSEG_SOUNDS = "sounds";

	public static final String DICT_URL_BASE = "content://info.softex.dictionary";
	public static final String DICT_URL_ASSETS = DICT_URL_BASE + "/assets";
	
	public static final String RESOURCE_INT_IMG_SOUND = "sound_32";
}

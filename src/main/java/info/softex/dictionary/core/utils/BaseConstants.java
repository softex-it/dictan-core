/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2015  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.utils;

/**
 * 
 * @since version 2.5, 08/06/2011
 * 
 * @modified version 3.0, 11/27/2011
 * @modified version 3.1, 05/22/2012
 * @modified version 4.1, 02/05/2014
 * @modified version 4.6, 02/07/2015
 * @modified version 5.4, 02/17/2017
 *
 * @author Dmitry Viktorov
 * 
 */
public class BaseConstants {

	public final static String PATH_SEPARATOR = "/";

	public final static String PATH_PREFIX_INTERNAL = "Jt6iFt4-";
	
	// The segment for images, sounds, videos and other resources
	public final static String URLSEG_ABBREVS = PATH_PREFIX_INTERNAL + "abbr";
	public final static String URLSEG_ARTICLE_INDEX = PATH_PREFIX_INTERNAL + "word-id";
	
	public final static String URLSEG_IMAGES = PATH_PREFIX_INTERNAL + "img";
	public final static String URLSEG_IMAGES_EXTERNAL = PATH_PREFIX_INTERNAL + "img-ext";
	public final static String URLSEG_AUDIO = PATH_PREFIX_INTERNAL + "aud";
	public final static String URLSEG_VIDEO = PATH_PREFIX_INTERNAL + "vid";
	
	public final static String RESOURCE_INT_IMG_SOUND = "sound_32";
	
	public final static String MODE_DISABLED = "DISABLED";
	public final static String MODE_BASIC = "BASIC";
	public final static String MODE_FULL = "FULL";
	public final static String MODE_DSL = "DSL";
	public final static String MODE_AUTO = "AUTO";
	public final static String MODE_ALWAYS = "ALWAYS";
	
}

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

package info.softex.dictionary.core.utils;

/**
 * 
 * @since       version 2.5, 08/06/2011
 * 
 * @modified    version 3.0, 11/27/2011
 * @modified    version 3.1, 05/22/2012
 * @modified    version 4.1, 02/05/2014
 * @modified    version 4.6, 02/07/2015
 * @modified    version 5.1, 02/17/2017
 * @modified    version 5.2, 10/21/2018
 *
 * @author Dmitry Viktorov
 * 
 */
public class BaseConstants {

	public final static String EMPTY = "";
	public final static String PATH_SEPARATOR = "/";

	//public final static String URLSEG_INTERNAL = "Jt6iFt4-";
	public final static String URLSEG_INTERNAL = "internal";

    public final static String URLSEG_ABBREV = "abbr";
    public final static String URLSEG_WORD_INDEX = "word-id";
    public final static String URLSEG_IMAGE = "image";
    public final static String URLSEG_IMAGE_APP = "image-app";
    public final static String URLSEG_AUDIO = "audio";
    public final static String URLSEG_VIDEO = "video";
	
	// The segment for images, sounds, videos and other resources
	public final static String PATH_INTERNAL_ABBREV = URLSEG_INTERNAL + PATH_SEPARATOR + URLSEG_ABBREV;
	public final static String PATH_INTERNAL_WORD_INDEX = URLSEG_INTERNAL + PATH_SEPARATOR + URLSEG_WORD_INDEX;
	public final static String PATH_INTERNAL_IMAGE = URLSEG_INTERNAL + PATH_SEPARATOR + URLSEG_IMAGE;
	public final static String PATH_INTERNAL_IMAGE_APP = URLSEG_INTERNAL + PATH_SEPARATOR + URLSEG_IMAGE_APP;
	public final static String PATH_INTERNAL_AUDIO = URLSEG_INTERNAL + PATH_SEPARATOR + URLSEG_AUDIO;
	public final static String PATH_INTERNAL_VIDEO = URLSEG_INTERNAL + PATH_SEPARATOR + URLSEG_VIDEO;

	public final static String RESOURCE_INT_IMG_SOUND = "sound_32";
	
	public final static String MODE_DISABLED = "DISABLED";
	public final static String MODE_BASIC = "BASIC";
	public final static String MODE_FULL = "FULL";
	public final static String MODE_DSL = "DSL";
	public final static String MODE_AUTO = "AUTO";
	public final static String MODE_ALWAYS = "ALWAYS";
	
}

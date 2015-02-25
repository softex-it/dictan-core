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
 *  
 * @author Dmitry Viktorov
 * 
 */
public class BaseConstants {
	
	public final static String PATH_SEPARATOR = "/";
	
	// The segment for images, sounds, videos and other resources
	public final static String URLSEG_ABBREVS = "abbr";
	public final static String URLSEG_ARTICLE_INDEX = "word-id";
	
	public final static String URLSEG_IMAGES = "img";
	public final static String URLSEG_IMAGES_EXTERNAL = "img-ext";
	public final static String URLSEG_AUDIO = "aud";
	public final static String URLSEG_VIDEO = "vid";
	
	public final static String RESOURCE_INT_IMG_SOUND = "sound_32";
	
	protected final static String MODE_DISABLED = "DISABLED";
	protected final static String MODE_BASIC = "BASIC";
	protected final static String MODE_FULL = "FULL";
	protected final static String MODE_DSL = "DSL";
	protected final static String MODE_AUTO = "AUTO";
	protected final static String MODE_ALWAYS = "ALWAYS";
	
}

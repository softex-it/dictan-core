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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @since version 4.6,		02/17/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FileTypeUtils {
	
	public static enum MediaType {
		IMAGE,
		AUDIO,
		VIDEO,
		UNKNOWN
	}
	
	protected final static Set<String> IMAGE_EXTENSIONS = new HashSet<String>(Arrays.asList(new String[] {
		"jpg", "jpeg", "png", "bmp", "pcx", "dcx", "tif", "tiff", "gif"
	}));
	
	protected final static Set<String> AUDIO_EXTENSIONS = new HashSet<String>(Arrays.asList(new String[] {
		"wav", "ogg", "mp3", "wma", "vox", "oga", "m4a", "m4p", "flac", 
		"gsm", "aac", "aiff", "ra", "tta"
	}));
	
	protected final static Set<String> VIDEO_EXTENSIONS = new HashSet<String>(Arrays.asList(new String[] {
		"avi", "mpg", "mpe", "mpeg", "mpv", "mp2", "m2v", "mp4", "m4v", 
		"mkv", "mov", "qt", "wmv", "rm", "rmvb", "asf", "flv", "webm", 
		"ogv", "3gp", "3g2"
	}));
	
	protected static boolean isExtensionInSet(Set<String> set, String ext) {
		if (ext != null) {
			return set.contains(ext.toLowerCase());
		} else {
			return false;
		}
	}
	
	public static boolean isImageExtension(String ext) {
		return isExtensionInSet(IMAGE_EXTENSIONS, ext);
	}
	
	public static boolean isAudioExtension(String ext) {
		return isExtensionInSet(AUDIO_EXTENSIONS, ext);
	}
	
	public static boolean isVideoExtension(String ext) {
		return isExtensionInSet(VIDEO_EXTENSIONS, ext);
	}
	
	public static MediaType getMediaTypeByExtension(String ext) {
		if (isImageExtension(ext)) {
			return MediaType.IMAGE;
		} else if (isAudioExtension(ext)) {
			return MediaType.AUDIO;
		} else if (isVideoExtension(ext)) {
			return MediaType.VIDEO;
		}
		return MediaType.UNKNOWN;
	}

}

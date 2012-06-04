/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2011 - 2012  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.attributes;

import info.softex.dictionary.core.annotations.DictionaryFormat;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * @since version 2.6, 09/02/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FormatInfo {
	
	private final String name;
	private final String primaryExtension;
	private final Set<String> extensions;
	
	public FormatInfo(String name, String primaryExtension, String[] extensions) {
		this.name = name.toUpperCase();
		this.primaryExtension = primaryExtension;
		this.extensions = new LinkedHashSet<String>();
		for (int i = 0; i < extensions.length; i++) {
			this.extensions.add(extensions[i].toLowerCase());
		}
	}
	
	// The primary name of a dictionary
	public String getName() {
		return name;
	}
	
	// The primary extension of a dictionary 
	public String getPrimaryExtension() {
		return primaryExtension;
	}

	// A set of format extensions that a dictionary may have 
	public Set<String> getExtensions() {
		return extensions;
	}
	
	public String getSupportedExtension(String fileName) {
		for (String curExt : this.extensions) {
			if (fileName.toLowerCase().endsWith(curExt)) {
				return curExt;
			}
		}
		return null;
	}
	
	public static FormatInfo buildFormatInfoFromAnnotation(Class<?> clazz) {
		Annotation[] anns = clazz.getAnnotations();
		for (int i = 0; i < anns.length; i++) {
			if (anns[i] instanceof DictionaryFormat) {
				DictionaryFormat df = (DictionaryFormat)anns[i];
				return new FormatInfo(df.name(), df.primaryExtension(), df.extensions());
			}
		}
		return null;
	}

}

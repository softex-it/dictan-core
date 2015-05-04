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

package info.softex.dictionary.core.attributes;

import info.softex.dictionary.core.annotations.BaseFormat;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * 
 * @since version 2.6, 09/02/2011
 * 
 * @modified version 3.7, 06/12/2013
 * @modified version 4.6, 02/26/2015
 * @modified version 4.7, 03/29/2015
 *
 * @author Dmitry Viktorov
 * 
 */
public class FormatInfo {
	
	private static final Locale LOCALE_DEFAULT = Locale.ENGLISH;
	
	private final String name;
	private final String primaryExtension;
	private final Set<String> extensions;
	
	// States if the format is expected to be sorted
	private final boolean sortingExpected;
	
	// States if the format contains keys only
	private final boolean hasKeysOnly;

    // States if the format supports the DB like search, e.g. %test%
    private final boolean likeSearchSupported;
	
	public FormatInfo(String name, String inPrimaryExtension, String[] inExtensions,
            boolean inSortingExpected, boolean inHasKeysOnly, boolean inLikeSearchSupported) {
		this.name = name.toUpperCase(LOCALE_DEFAULT);
		this.primaryExtension = inPrimaryExtension;
		this.sortingExpected = inSortingExpected;
		this.hasKeysOnly = inHasKeysOnly;
        this.likeSearchSupported = inLikeSearchSupported;
		this.extensions = new LinkedHashSet<String>();
		for (int i = 0; i < inExtensions.length; i++) {
			this.extensions.add(inExtensions[i].toLowerCase(LOCALE_DEFAULT));
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
	
	public String getSupportedExtension(final String fileName) {
        String fileNameLC = fileName.toLowerCase(LOCALE_DEFAULT);
		for (String curExt : this.extensions) {
			if (fileNameLC.endsWith(curExt)) {
				return curExt;
			}
		}
		return null;
	}
	
	public boolean isSortingExpected() {
		return sortingExpected;
	}
	
	public boolean hasKeysOnly() {
		return hasKeysOnly;
	}

    public boolean isLikeSearchSupported() {
        return likeSearchSupported;
    }

    public static FormatInfo buildFormatInfoFromAnnotation(Class<?> clazz) {
		Annotation[] anns = clazz.getAnnotations();
		for (int i = 0; i < anns.length; i++) {
			if (anns[i] instanceof BaseFormat) {
				BaseFormat df = (BaseFormat)anns[i];
				return new FormatInfo(df.name(), df.primaryExtension(), df.extensions(),
                    df.sortingExpected(), df.hasKeysOnly(), df.likeSearchSupported()
                );
			}
		}
		return null;
	}

}

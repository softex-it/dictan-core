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

import java.util.Iterator;
import java.util.Objects;

/**
 * 
 * @since version 4.6, 02/03/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class StringUtils {
	
	public static final String EMPTY = "";

	/**
	 * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
	 *
	 * @param str - the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(cs.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
	 * 
	 * @param cs  the CharSequence to check, may be null
	 * @return <code>true</code> if the CharSequence is not empty and not null and not whitespace
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}

	/**
	 * <p>Joins the elements of the provided {@code Iterable} into a single String
	 * containing the provided elements.</p>
	 *
	 * <p>No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String ("").</p>
	 */
	public static String join(final Iterable<?> iterable, final String separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), separator);
	}

	/**
	 * <p>Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.</p>
	 *
	 * <p>No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String ("").</p>
	 *
	 * <p>See the examples here: {@link #join(Object[],String)}.</p>
	 *
	 * @param iterator
	 *            the {@code Iterator} of values to join together, may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null iterator input
	 */
	public static String join(final Iterator<?> iterator, final String separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		final Object first = iterator.next();
		if (!iterator.hasNext()) {
			return Objects.toString(first, "");
		}

		// Two or more elements
		// Java default is 16, probably too small
		final StringBuilder buf = new StringBuilder(256);
		
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			final Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}
	
	/**
	 * Join the strings with line breaks.
	 */
	public static String joinWithLineBreaks(final Iterable<String> iterable) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), "\r\n");
	}
	
	/**
	 * Splits input string into an array of string by line breaks.
	 */
	public static String[] splitByLineBreaks(final String str) {
		if (str != null) {
			return str.split("\\r?\\n");
		}
		return null;
	}
	
	/**
	 * Removes the leading white spaces. It repeats the implementation of
	 * String.trim() but only for the leading white spaces.
	 * 
	 * The method also checks for nulls and doesn't process the empty strings.
	 */
	public static String ltrim(String s) {
		int len;
		if (s == null || (len = s.length()) == 0) {
			return s;
		}
        int st = 0;
        while ((st < len) && (s.charAt(st) <= ' ')) {
            st++;
        }
        return (st > 0) ? s.substring(st, len) : s;
    }
	
	/**
	 * <p>Returns either the passed in String, or if the String 
	 * is {@code null}, an empty String ("").</p>
	 */
	public static String defaultString(final String str) {
		return str == null ? EMPTY : str;
	}

}

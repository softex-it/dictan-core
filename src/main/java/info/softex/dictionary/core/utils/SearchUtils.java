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

import java.util.Collection;
import java.util.TreeMap;

/**
 *
 * @since       4.7, 03/30/2015
 *
 * @author Dmitry Viktorov
 *
 */
public class SearchUtils {

	private final static String PERCENT = "%";

    /**
     * Searches for a String in the list based on the SQL 'like' expression
     * @param list
     * @param likeExp
     * @param limit - the maximum number of items to be returned
     * @return
     */
    public static TreeMap<Integer, String> searchSQLLike(final Collection<String> list, final String likeExp, final int limit) {
        TreeMap<Integer, String> result = new TreeMap<>();

        // Don's search for null or zero values
        if (list == null || likeExp == null || limit <= 0) {
            return result;
        }

        // Prepare info about like expression
        String likeExpLC = likeExp.toLowerCase();
        boolean anyBefore = false;
        boolean anyAfter = false;

        // Check for any before and after only if like expression length is > 2
        if (likeExp.length() > 2) {
            if (likeExpLC.startsWith(PERCENT)) {
                anyBefore = true;
                likeExpLC = likeExpLC.substring(1);
            }
            if (likeExpLC.endsWith(PERCENT)) {
                anyAfter = true;
                likeExpLC = likeExpLC.substring(0, likeExpLC.length() - 1);
            }
        }

        // Go over the list of strings to check the condition
        int count = 0;
        for (String str : list) {
            String strLC = str.toLowerCase();

            // Check if the String satisfies start from or before statement
            int index = -1;
            if (anyBefore) {
            	index = strLC.indexOf(likeExpLC);
            } else if (strLC.startsWith(likeExpLC)) {
            	index = 0;
            }

            if (index >= 0) {
            	// If any character is allowed after the prefix, add it to the result
                if (anyAfter || strLC.length() == index + strLC.length() || strLC.endsWith(likeExpLC)) {
                	result.put(count, str);
                    // Check if the limit is reached
                    if (result.size() >= limit) {
                        break;
                    }
                }
            }
            count++;
        }
        return result;
    }

    /**
     * Escapes SQL like expressions using the provided parameters.
     * The like expressions are supposed to have % only the head and tail.
     */
    public static String escapeSQLLike(final String likeExp, final char escaper, final char escaped) {
    	if (likeExp == null) {
    		return null;
    	}
        String escapedString = Character.toString(escaped);
        String escaperString = Character.toString(escaper);
        String replacement = escaperString + escapedString;

        String escLike = likeExp.replaceAll(escaperString, escaperString + escaperString);
        escLike = escLike.replaceAll(escapedString, replacement);

        if (likeExp.length() > 2) {
            if (escLike.startsWith(replacement)) {
                escLike = escapedString + escLike.substring(2);
            }
            if (escLike.endsWith(replacement)) {
                escLike = escLike.substring(0, escLike.length() - 2) + escapedString;
            }
        }
        return escLike;
    }

    /**
     * Reverts the register of the first letter of the input string.
     * The method is useful for SQL like expressions for DBs which
     * don't support case insensitive search.
     *
     * If a letter couldn't be found or the register can't be reverted, null is returned.
     *
     * Examples:
     *
     * %some text% -> %Some text%
     * %Some text% -> %some text%
     * % some text% -> % Some text%
     * 1-2=3 -> null
     *
     */
    public static String revertFirstLetterRegister(final String likeExp) {
        // Don't process null or empty strings
        if (likeExp == null || likeExp.length() == 0) {
            return null;
        }
        final char[] buffer = likeExp.toCharArray();
        Character revertedChar = null;

        int charIndex = 0;
        for (; charIndex < buffer.length; charIndex++) {
            char curChar = buffer[charIndex];
            if (Character.isLetter(curChar)) {
                if (Character.isLowerCase(curChar)) {
                    revertedChar = Character.toUpperCase(curChar);
                } else if (Character.isUpperCase(curChar)) {
                    revertedChar = Character.toLowerCase(curChar);
                }
                break;
            }
        }

        if (revertedChar != null && revertedChar != buffer[charIndex]) {
            buffer[charIndex] = revertedChar;
            return new String(buffer);
        }
        return null;
    }

}

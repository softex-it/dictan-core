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

package info.softex.dictionary.core.formats.dsl.utils;

import info.softex.dictionary.core.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @since version 4.6, 02/07/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLReadValidator {
	
	protected final static Pattern EMPTY_BRACKETS = Pattern.compile("[(.+?)]");
	
	public static boolean isValidDSL(String s) {
		
		// Check for empty brackets [ ]
		Matcher matcher = EMPTY_BRACKETS.matcher(s);
		while (matcher.find()) {
			String body = matcher.group(1);
			if (body != null && StringUtils.isBlank(body)) {
				return false;
			}
		}

		return true;
	        
	}

}

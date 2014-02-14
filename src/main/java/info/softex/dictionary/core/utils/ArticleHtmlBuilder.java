/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2012  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since version 1.0, 10/26/2010 
 * 
 * @modified version 1.4, 12/19/2010 
 * @modified version 1.9, 02/18/2011
 * @modified version 2.5, 07/10/2011 
 * @modified version 4.1, 02/03/2014 
 * 
 * @author Dmitry Viktorov
 *
 */
public class ArticleHtmlBuilder {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleHtmlBuilder.class.getSimpleName());

	public static String buildHtmlArticle(String article, String inGlobalCSSPath, String fontName, String fontPath, int fontSize) {

		String globalCSSLink = "";
		if (inGlobalCSSPath != null && inGlobalCSSPath.length() > 0) {
			globalCSSLink = "<link rel='stylesheet' type='text/css' href='" + inGlobalCSSPath + "' />";
		}
		
		String style = "<style>body {text-align:left;padding:1pt;}</style>";
		if (fontName != null && fontPath != null && fontSize > 0) {
			
			// src: url('file:///android_asset/transcr.ttf');
			style = 
				"<style>" +
					"@font-face {font-family:'" + fontName + "';src:url('" + fontPath + "');" +
					"} body {font-family:'" + fontName+ "';font-size:" + fontSize + "px;text-align:left;padding:1pt;}" +
				"</style>";
		}
		
    	String articleHtml =
    		"<html><head>" +
    		"<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>" +
    		globalCSSLink +
			style +
	    	"</head><body>" + article + "</body></html>";
    	
    	return articleHtml;
		
	}
	
}

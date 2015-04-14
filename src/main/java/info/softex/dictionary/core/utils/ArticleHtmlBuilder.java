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

import info.softex.dictionary.core.attributes.FontInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since version 1.0,      10/26/2010
 * 
 * @modified version 1.4,   12/19/2010
 * @modified version 1.9,   02/18/2011
 * @modified version 2.5,   07/10/2011
 * @modified version 4.0,   02/03/2014
 * @modified version 4.1,   02/17/2014
 * @modified version 4.6,   02/15/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class ArticleHtmlBuilder {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleHtmlBuilder.class.getSimpleName());

	/**
	 * Makes final formatting of the article to be viewed.
	 * 
	 * @param article - Body of the article
	 * @param inFontInfo - Information about the used global font which is used to construct the proper styles
	 * @param inGlobalCSSPath - Path to global CSS which can be used e.g. to change the theme
	 * @param inSpecificCSS - Optional CSS which could be dependent on the formatting mode
	 * @return - Formatted article ready for viewing
	 */
	public static String buildHtmlArticle(String article, FontInfo inFontInfo, String inGlobalCSSPath, String inSpecificCSS) {

		String globalCSSLink = "";
		if (StringUtils.isNotBlank(inGlobalCSSPath)) {
			globalCSSLink = "<link rel='stylesheet' type='text/css' href='" + inGlobalCSSPath + "' />";
		}
		
		String specificStyles = "";
		if (StringUtils.isNotBlank(inSpecificCSS)) {
			specificStyles = "<style>" + inSpecificCSS + "</style>";
		}
		
		String fontStyles = buildFontStyles(inFontInfo);
		
    	String articleHtml =
    		"<html><head>" +
    		"<meta http-equiv='content-type' content='text/html'/>" +
    		"<meta charset='UTF-8'>" +
    		globalCSSLink +
			fontStyles +
			specificStyles +
	    	"</head><body>" + article + "</body></html>";
    	
    	return articleHtml;
		
	}
	
	protected static String buildFontStyles(FontInfo fontInfo) {
		String fontSizeString = fontInfo.getSize() > 0 ? fontInfo.getSize() + "px" : "100%";
		String fontName = fontInfo.getName();
		String fontPath = fontInfo.getFilePath();
		
		String fontAttr = "font-size:" + fontSizeString + 
			";font-weight:" + fontInfo.getWeight() + ";font-style:" + fontInfo.getStyle();
		
		String style = "<style>";
		if (fontPath != null) {
			if (fontName == null) {
				fontName = "Default Application Font";
			}
			style += "@font-face {font-family:'" + fontName + "';src:url('" + fontPath + "');}";
			style += " body {font-family:'" + fontName + "';" + fontAttr + ";text-align:left;padding:1pt;}";
		} else if (fontName != null && fontName.length() > 0) {
//			String fontFamily = resolveFontFamily(fontName);
//			if (fontFamily == null) {
//				fontFamily = fontName;
//			}
			style += "body {font-family:'" + fontName + "';" + fontAttr + ";text-align:left;padding:1pt;}";
		} else {
			style += "body {" + fontAttr + ";text-align:left;padding:1pt;}";
			log.debug("Article font is not set");
		}

		style += "</style>";
		
		return style;
	}
	
	protected static String resolveFontFamily(String fontName) {
		String lcFontName = fontName.toLowerCase();
		String fontFamily = null;
		if (lcFontName.contains("sans mono")) {
			fontFamily = "monospace";
		} else if (lcFontName.contains("sans")) {
			fontFamily = "sans-serif";
		} else if (lcFontName.contains("serif")) {
			fontFamily = "serif";
		}
		return fontFamily;
	}
	
}

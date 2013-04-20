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

import info.softex.dictionary.core.utils.ArticleHtmlBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since version 1.0, 10/26/2010 
 * 
 * @modified version 1.4, 12/19/2010 
 * @modified version 1.9, 02/18/2011
 * @modified version 2.5, 07/10/2011 
 * 
 * @author Dmitry Viktorov
 *
 */
public class ArticleHtmlBuilder {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleHtmlBuilder.class.getSimpleName());

	public static String buildHtmlArticle(String article, String commonCSSFN) {
		
    	String transHtml = "<html><head>" + 
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/" + commonCSSFN + "\" />" +
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/article.css\" />" +
	    	"</head><body>" + article + "</body></html>";
    	
    	return transHtml;
		
	}
	
	/**
	 * 
	 * @param word - should never be null or empty
	 * @param article
	 * @return
	 */
	public static String postArticle(String word, String article) {
		
		if (article == null || article.trim().length() == 0) {
			log.info("Article is null, returning word");
			return "<b>" + word + "</b>";
		}
		
		//boolean stickWordToArticle = true;
		
		article = article.trim();
		
		// If (Article.length() > 0) is not pointed, charAt causes the StringIndexOutOfBoundsException.
		// It happens because String.indexOf() returns 0 of length=0 - Android bug
		
		// Start with the converted to HTML as the probability to find it is 
		// higher rather than the raw word. If there are no HTML symbols, than the
		// word is same.
		int subIndex = article.indexOf(toHTMLString(word));
		
		if (subIndex < 0) {
			subIndex = article.indexOf(word);
		}
		
		if (subIndex == 0) {

			article = article.substring(word.length()).trim();
            
			if (article.length() > 0) {
			
				char c = article.charAt(0);
				// u2010 hyphen
				// u2011 non-breaking hyphen
				// u201D hyphen-minus
				// u2013 en dash
				// u2014 em dash
	            if(c == '\u2013' || c == '\u2014' || c == '\u2010' || c == '\u2011' || c == '\u201D') {
	            	article = article.substring(1).trim();
	            }
			} else { // Return the word back if there are no any words
				log.info("No text is found in the Article except the word, returning word");
				article = word;
			}
		
		} 		
//		else if (subIndex > 0) {
//			String transStart = t.substring(0, subIndex);
//			//log.info("HTML Article Start: {}", transStart);
//			transStart = transStart.replaceAll("\\<.*?\\>", "").trim();
//			if (transStart.length() == 0) {
//				log.info("Word is found in the Article HTML");
//				stickWordToArticle = false;
//			}
//		}

		article = removeExcessiveBRFromBeginning(article);
		
		//if (stickWordToArticle) {
		if (!article.startsWith("<p>") && !article.startsWith("<P>")) {			
			article = "<b>" + word + "</b><br/><br/>" + article;
		} else {
			article = "<b>" + word + "</b><br/>" + article;
		}
		//}
		
		return article;

	}
	
	private static String removeExcessiveBRFromBeginning(String article) {
		// Cut out excessive <br/> and <br> tags.
		// Android 2.0 could leave only 1 <br> but 2.2 leaves more
		while (true) {
			String compArticle = article.toLowerCase();
			if (compArticle.startsWith("<br/>")) {
				article = article.substring(5);
			} else if (compArticle.startsWith("<br>")) {
				article = article.substring(4);
			} else {
				break;
			}
		}
		return article;
	}
	
	public static String toHTMLString(String string) {
	    StringBuffer sb = new StringBuffer(string.length());
	    // true if last char was blank
	    //boolean lastWasBlankChar = false;
	    int len = string.length();
	    char c;

	    for (int i = 0; i < len; i++) {
	        c = string.charAt(i);

            //lastWasBlankChar = false;
            //
            // HTML Special Chars
            if (c == '"')
                sb.append("&quot;");
            else if (c == '&')
                sb.append("&amp;");
            else if (c == '<')
                sb.append("&lt;");
            else if (c == '>')
                sb.append("&gt;");
            else if (c == '\n')
                // Handle Newline
                sb.append("&lt;br/&gt;");
            else {
                //int ci = 0xffff & c;
                //if (ci < 160 )
                    // nothing special only 7 Bit
                    sb.append(c);
                //else {
                //    // Not 7 Bit use the unicode system
                //    sb.append("&#");
                //    sb.append(new Integer(ci).toString());
                //    sb.append(';');
                //   }
                }
            }

	    return sb.toString();
	}
	
}

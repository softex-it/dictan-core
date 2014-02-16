/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2014  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.6, 08/18/2011 
 * 
 * @modified version 3.3, 06/15/2012
 * @modified version 3.5, 08/01/2012
 * @modified version 4.0, 02/06/2014 
 * 
 * @author Dmitry Viktorov
 *
 */
public class ArticleHtmlFormatter {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleHtmlFormatter.class.getSimpleName());

	public static String prepareArticle(
				String word, String article, Set<String> abbreviations, 
				String articleFormatMode, String articleFormatIWM, 
				String abbrFormatMode, boolean isMediaAvailable
			) {
		
		long t1 = System.currentTimeMillis();
		article = applyArticleFormat(article, articleFormatMode, isMediaAvailable);
		article = applyAbbreviationsFormat(article, abbreviations, abbrFormatMode);
		article = injectWord(word, article, articleFormatIWM);
		
		log.debug("Prepare Article | Total time: {} ms", System.nanoTime() - t1);
		log.debug("Prepare Article | Output: {}", article);
		
		return article;
		
	}
	
    // \\W - Non-word character
	// \\w - A word character: [a-zA-Z_0-9]
	// \\S - A non-whitespace character: [^\s]
    // \\s - A whitespace character: [ \t\n\x0B\f\r]
	private static String applyArticleFormat(String a, String articlesFormatMode, boolean isMediaAvailable) {
		
		// Always process essential replacements
		a = applyEssentialReplacements(a, isMediaAvailable);
		
		if (BaseConstants.MODE_DISABLED.equalsIgnoreCase(articlesFormatMode)) {
			log.debug("Prepare Article | Article formatting is disabled");
			return a;
		}
		
		//log.debug("Prepare Article | Input: {}", t);
		
		long t1 = System.currentTimeMillis();
		
		// If formatting is not disabled try to remove unnecessary html tags
		if (icMatches(a, ".*?\\<html.*?\\>.*?")) {
			
			log.debug("Prepare Article | Raw HTML is found, removing");
			
			a = icReplaceAll(a, "<.?!doctype.*?\\>", "");
			a = icReplaceAll(a, "<.?html.*?\\>", "");
			a = icReplaceAll(a, "<.?head.*?\\>", "");
			a = icReplaceAll(a, "<.?title.*?\\>", "");
			a = icReplaceAll(a, "<.?meta.*?\\>", "");
			a = icReplaceAll(a, "<.?body.*?\\>", "");
			a = icReplaceAll(a, "\\Q<style>\\E(.+)\\Q</style>\\E", "");
			
		}
			
		a = applyBasicReplacements(a, isMediaAvailable);
		
		if (BaseConstants.MODE_FULL.equalsIgnoreCase(articlesFormatMode)) {
			log.debug("Applying advanced replacements");
			a = applyAdvancedReplacements(a, isMediaAvailable);
		}

		log.debug("Apply Article | Time for replacements: {} ms", System.currentTimeMillis() - t1);
		
		return a;
	}
	
	private static String applyAbbreviationsFormat(String a, Set<String> abbreviations, String abbrFormatMode) {
		
		if (abbreviations == null || abbreviations.isEmpty()) {
			log.debug("Apply Abbreviations | Abbreviations don't exist");
			return a;
		}
		
		long t1 = System.currentTimeMillis();
		
		// Always process the <abbr> tag
		a = icReplaceAll(a, "\\Q<abbr>\\E([^<]++)\\Q</abbr>\\E", "<a href=\"" + BaseConstants.URLSEG_ABBREVS + "/$1\">$1</a>");
		
		if (BaseConstants.MODE_DISABLED.equalsIgnoreCase(abbrFormatMode)) {
			log.debug("Apply Abbreviations | Abbreviations formatting is disabled, only essential rules are applied");
			return a;
		} 
		
		if (BaseConstants.MODE_FULL.equalsIgnoreCase(abbrFormatMode)) {
			for (Iterator<String> iterator = abbreviations.iterator(); iterator.hasNext();) {
	
	            String key = iterator.next();
	            String modKey = key;
	            
	            if (key.startsWith("_")) {
	                modKey = modKey.substring(1);
	                String searchKey = "(^|\\s)" + key + "($|\\s|\\<)";
	                a = a.replaceAll(searchKey, "$1<a href=\"" + BaseConstants.URLSEG_ABBREVS + "/" + key + "\">" + modKey + "</a>$2");
	            }
	
			}
		}
		
		// This replacements applies to Basic and Full modes
		a = icReplaceAll(a, "\\Q<c>\\E([^<]++)\\Q</c>\\E", "<a href=\"" + BaseConstants.URLSEG_ABBREVS + "/$1\">$1</a>");

		int abbrSize = abbreviations == null ? 0 : abbreviations.size();
		log.debug("Prepare Abbreviations | Time for replacements: {} ms | Size: {}", System.currentTimeMillis() - t1, abbrSize);
		
		return a;
	}

	private static String applyEssentialReplacements(String a, boolean isMediaAvailable) {
		
		// Rewrite paths to images and audio if media is available
		if (isMediaAvailable) {
			a = icReplaceAll(a, "\\<img(.+?)src\\s*=\\s*[\"'](.+?)[\"'](.*?)/{0,1}\\>", "<img$1src=\"" + BaseConstants.URLSEG_INTERNAL + "/" + BaseConstants.URLSEG_IMAGES + "/$2\"$3/>");
			a = icReplaceAll(a, "\\<audio(.+?)src\\s*=\\s*[\"'](.+?)[\"'](.*?)/{0,1}\\>", "<audio$1src=\"" + BaseConstants.URLSEG_SOUNDS + "/$2\"$3/>"); // audio tag added in HTML5
		}
		return a;
		
	}
	
	private static String applyBasicReplacements(String t, boolean isMediaAvailable) {
		
        t = icReplaceAll(t, "\\Q<r>\\E([^<]++)\\Q</r>\\E", "<a href=\"$1\">$1</a>");
        t = icReplaceAll(t, "\\Q<t>\\E([^<]+)\\Q</t>\\E", "<b>[ $1 ]</b>");
        
		if (isMediaAvailable) {
			t = icReplaceAll(t, "\\Q<wav>\\E(.+)\\Q</wav>\\E", "<br/><a href=\"" + BaseConstants.URLSEG_SOUNDS + "/$1\"><img src=\"" + BaseConstants.URLSEG_EXTERNAL + "/" + BaseConstants.URLSEG_IMAGES + "/" + BaseConstants.RESOURCE_INT_IMG_SOUND + "\" border=\"0\"/></a><br/>");
		} else {
			t = icReplaceAll(t, "\\<img.*?\\>", ""); // Cut out IMG tags
	        t = icReplaceAll(t, "\\Q<wav>\\E(.+)\\Q</wav>\\E", ""); // Cut out sounds
		}
		return t;
		
	}
	
	private static String applyAdvancedReplacements(String t, boolean isMediaAvailable) {
	
		// Needed only when <br/> are at input
		t = icReplaceAll(t, "\\<br\\>", "<br/>"); // replace all <br> tags with proper ones <br/>
		
        t = t.replaceAll("(^| |(\\<br/\\>){1,2})\\_([IV]{1,3})\\b", "<br/><br/><font color=\"red\"><b>$3</b></font>");
        //t = t.replaceAll("(^| )([IV]{1,3})\\. ", "<br/><br/><font color=\"red\"><b>$2.</b></font>&nbsp;"); // improper viewing
        //t = t.replaceAll("\\b([IV]{1,3})\\. ", "<br/><br/><font color=\"red\"><b>$1.</b></font>&nbsp;"); // Meriam Webster, impairs other dicts
        //t = t.replaceAll("<br>(\\d+)\\.<br>", "<br/><font color=\"maroon\"><b>$1.</b></font><br/>"); // Collins Cobuild 

        t = t.replaceAll("\\{(.+?)\\}", "<font color=\"green\">$1</font>"); // For English Idioms
        t = t.replaceAll("\\* /(.*?)/", "<br/><br/><i>$1</i>"); // For English Idioms
        t = t.replaceAll(" -- \\((.*?)\\) ", "<br/><br/>($1)&nbsp;"); // For English Synonyms
        
        t = t.replaceAll("\\<=", "="); // For Ojegov (equals sign)

        //t = t.replaceAll("\\<br/\\>\\s*?(\\d{1,2})\\. ", "<br/><br/><font color=\"maroon\"><b>$1.</b></font> "); // added to remove <br/> (For English Synonyms 2.0)
        t = t.replaceAll("(^| |\\<br/\\>)(\\s*?)(\\d{1,2})\\. ", "<br/><br/><font color=\"maroon\"><b>$3.</b></font> "); // is original
        
        t = t.replaceAll("(^| |\\<br/\\>)(\\s*?)(\\d{1,2})\\>([ &])", "<br/><font color=\"green\"><b>$3)</b></font>&nbsp;$4");
        t = t.replaceAll("(^| |\\<br/\\>)(\\s*?)(\\w)\\>([ &])", "<br/><font color=\"#B35617\"><b>$3)</b></font>&nbsp;$4"); // English-Russian(-English)
        t = t.replaceAll("\\<br/\\>(\\s*?)(\\d{1,2})\\)", "<br/><font color=\"green\"><b>$2)</b></font>"); // For Efremova, Mueller 24
        t = t.replaceAll("\\<br/\\>(\\s*?)(\\w)\\)", "<br/><font color=\"#B35617\"><b>$2)</b></font>"); // For Efremova, Mueller 24
        
        //t = t.replaceAll("(^| )(See: )(.*)\\.", "<br/>$2<a href=\"" + URLSEG_REFS + "/$3\">$3</a>"); // English Idioms
       
        return t;
	
	}
	
	/**
	 * The method will check if the article is blank, and if it should be prepended with a word.
	 * It will also remove all <br/> tags from the beginning.
	 * 
	 * @param word - should never be null or empty
	 * @param article
	 * @return
	 */
	private static String injectWord(String word, String article, String articleInjectWordMode) {
		
		if (word == null || article.trim().length() == 0) {
			log.info("Word is blank, returning article");
			return article;
		}
		
		if (article == null || article.trim().length() == 0) {
			log.info("Article is blank, returning word");
			return "<b>" + word + "</b>";
		}
		
		article = article.trim();
		
		if (BaseConstants.MODE_DISABLED.equalsIgnoreCase(articleInjectWordMode)) {
			log.info("Word injection is disabled, returning article");
			return article;
		} else if (BaseConstants.MODE_ALWAYS.equalsIgnoreCase(articleInjectWordMode)) {
			article = "<b>" + word + "</b><br/><br/>" + article;
			return article;
		} else { // Auto mode
			
			log.info("Word injection is auto, searching for the word in the article");

			// If (Article.length() > 0) is not pointed, charAt causes the StringIndexOutOfBoundsException.
			// It happens because String.indexOf() returns 0 of length=0 - Android bug
			
			// Start with the word converted to HTML because the probability to find it is 
			// higher than the raw word. If there are no HTML symbols, then the word is the same.
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
					log.info("No text is found in the article except the word, returning word");
					article = word;
				}
			
			}
	
			article = removeExcessiveBRFromBeginning(article);
			
			if (!article.startsWith("<p>") && !article.startsWith("<P>")) {	
				article = "<br/>" + article;
			}
			
			article = "<b>" + word + "</b><br/>" + article;
		
			return article;
			
		}

	}
	
	private static String removeExcessiveBRFromBeginning(String article) {
		// Cut out excessive <br/> and <br> tags.
		// Android 2.1 could leave only 1 <br> but 2.2 leaves more
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
	
	private static String toHTMLString(String string) {
	    StringBuffer sb = new StringBuffer(string.length());

	    int len = string.length();
	    char c;

	    for (int i = 0; i < len; i++) {
	        c = string.charAt(i);

            // HTML special chars
            if (c == '"') {
                sb.append("&quot;");
            } else if (c == '&') {
                sb.append("&amp;");
            } else if (c == '<') {
                sb.append("&lt;");
            } else if (c == '>') {
                sb.append("&gt;");
            } else if (c == '\n') {
                // Handle Newline
            	sb.append("&lt;br/&gt;");
            } else {
            	sb.append(c);
            }
	    }

	    return sb.toString();
	}
	
	
	
	/**
	 * Replace All Ignore Case
	 */
	private static String icReplaceAll(String src, String expr, String change) {
		return Pattern.compile(expr, Pattern.CASE_INSENSITIVE).matcher(src).replaceAll(change);
	}
	
	/**
	 * Matches Ignore Case
	 */
	private static boolean icMatches(String src, String expr) {
		return Pattern.compile(expr, Pattern.CASE_INSENSITIVE).matcher(src).matches();
	}

}

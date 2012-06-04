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

import info.softex.dictionary.core.utils.ArticleHtmlFormatter;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.6, 08/18/2011 
 * 
 * @author Dmitry Viktorov
 *
 */
public class ArticleHtmlFormatter {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleHtmlFormatter.class.getSimpleName());
	
	public static String prepareArticle(String t, Set<String> abbreviations, String articlesFormatMode, String abbrFormatMode, boolean isMediaAvailable) {
		
		if (articlesFormatMode.equalsIgnoreCase("DISABLED")) {
			log.debug("Prepare Article | Article formatting is disabled");
			return applyAbbreviations(t, abbreviations, abbrFormatMode);
		}
		
        // \\W - Non-word character
		// \\w - A word character: [a-zA-Z_0-9]
		// \\S - A non-whitespace character: [^\s]
        // \\s - A whitespace character: [ \t\n\x0B\f\r]
		
		long l1 = System.nanoTime();
		
		log.debug("Prepare Article | Input: {}", t);
		
		if (icMatches(t, ".*?\\<html.*?\\>.*?")) {
			
			log.debug("Prepare Article | Raw HTML is found, removing");
			
			t = icReplaceAll(t, "<.?!doctype.*?\\>", "");
			t = icReplaceAll(t, "<.?html.*?\\>", "");
			t = icReplaceAll(t, "<.?head.*?\\>", "");
			t = icReplaceAll(t, "<.?title.*?\\>", "");
			t = icReplaceAll(t, "<.?meta.*?\\>", "");
			t = icReplaceAll(t, "<.?body.*?\\>", "");
			t = icReplaceAll(t, "\\Q<style>\\E(.+)\\Q</style>\\E", "");
			
		} else {
			
			t = applyBasicReplacements(t, isMediaAvailable);
			
			if (articlesFormatMode.equalsIgnoreCase("MEDIA")) {
				log.debug("Applying media replacements");
				t = applyMediaReplacements(t, isMediaAvailable);
			} else if (articlesFormatMode.equalsIgnoreCase("FULL")) {
				log.debug("Applying media & advanced replacements");
				t = applyMediaReplacements(t, isMediaAvailable);
				t = applyAdvancedReplacements(t, isMediaAvailable);
			}
			
		}
		
		long l2 = System.nanoTime();
		t = applyAbbreviations(t, abbreviations, abbrFormatMode);
		long l3 = System.nanoTime();
		
		long time1 = (l2 - l1) / 0xf4240L;
		long time2 = (l3 - l2) / 0xf4240L;
		long time3 = (l3 - l1) / 0xf4240L;
				
		//log.info("Prepare Article | Replacements: " + time1 + " ms, Abbreviations (" + zdLoader.getAbbreviations().size() + "): " + time2 + " ms, Total: " + time3 + " ms");
		
		int abbrSize = abbreviations == null ? 0 : abbreviations.size();
		log.debug("Prepare Article | Replacements: {} ms, Abbreviations ({}): {} ms, Total: {} ms", new Object[] {time1, abbrSize, time2, time3});
		log.debug("Prepare Article | Output: {}", t);
		
		return t;
	}
	
	private static String applyAbbreviations(String t, Set<String> abbreviations, String abbrFormatMode) {
		
		if (abbreviations == null || abbrFormatMode.equalsIgnoreCase("DISABLED")) {
			log.debug("Prepare Article | Abbreviations don't exist or formatting is disabled");
			return t;
		}
		
		if (!abbrFormatMode.equalsIgnoreCase("BASIC")) {
			for (Iterator<String> iterator = abbreviations.iterator(); iterator.hasNext();) {
	
	            String key = iterator.next();
	            String modKey = key;
	            
	            if (key.startsWith("_")) {
	                modKey = modKey.substring(1);
	                String searchKey = "(^|\\s)" + key + "($|\\s|\\<)";
	                t = t.replaceAll(searchKey, "$1<a href=\"" + BaseConstants.URLSEG_ABBREVS + "/" + key + "\">" + modKey + "</a>$2");
	            }
	
			}
		}
		
		t = icReplaceAll(t, "\\Q<abbr>\\E([^<]++)\\Q</abbr>\\E", "<a href=\"" + BaseConstants.URLSEG_ABBREVS + "/$1\">$1</a>");
		t = icReplaceAll(t, "\\Q<c>\\E([^<]++)\\Q</c>\\E", "<a href=\"" + BaseConstants.URLSEG_ABBREVS + "/$1\">$1</a>");
		
		return t;
	}
	

	private static String applyBasicReplacements(String t, boolean isMediaAvailable) {
        t = icReplaceAll(t, "\\Q<r>\\E([^<]++)\\Q</r>\\E", "<a href=\"" + BaseConstants.URLSEG_TRANS_WORD + "/$1\">$1</a>");
        t = icReplaceAll(t, "\\Q<t>\\E([^<]+)\\Q</t>\\E", "<b>[ $1 ]</b>");
        return t;
	}
	
	private static String applyMediaReplacements(String t, boolean isMediaAvailable) {
		if (isMediaAvailable) {
			t = icReplaceAll(t, "\\<img(.+?)src\\s*=\\s*[\"'](.+?)[\"'](.*?)/{0,1}\\>", "<img$1src=\"" + BaseConstants.URLSEG_INTERNAL + "/" + BaseConstants.URLSEG_IMAGES + "/$2\"$3/>");
			t = icReplaceAll(t, "\\Q<wav>\\E(.+)\\Q</wav>\\E", "<br/><a href=\"" + BaseConstants.URLSEG_SOUNDS + "/$1\"><img src=\"" + BaseConstants.URLSEG_EXTERNAL + "/" + BaseConstants.URLSEG_IMAGES + "/" + BaseConstants.RESOURCE_INT_IMG_SOUND + "\" /></a><br/>");
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
        //t = t.replaceAll("<br>(\\d+)\\.<br>", "<br/><font color=\"maroon\"><b>$1.</b></font><br/>"); // For Collins Cobuild 

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

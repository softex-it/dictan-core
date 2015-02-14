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

/**
 * 
 * @since version 4.6, 02/01/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLReadFormatUtils {

	/**
	 * Converts DSL markup into adapted HTML. 
	 * See http://lingvo.helpmax.net/en/troubleshooting/dsl-compiler/dsl-tags/
	 */
	public static String convertDSLToAdaptedHtml(String s) {
		
		// Don't process blank string
		if (StringUtils.isBlank(s)) {
			return s;
		}
		
		// Italic
		s = s.replaceAll("\\[i\\]", "<i>");
		s = s.replaceAll("\\[/i\\]", "</i>");
		
		// Bold
		s = s.replaceAll("\\[b\\]", "<b>");
		s = s.replaceAll("\\[/b\\]", "</b>");

		// Underlined
		s = s.replaceAll("\\[u\\]", "<u>");
		s = s.replaceAll("\\[/u\\]", "</u>");
		
		// Superscript
		s = s.replaceAll("\\[sup\\]", "<sup>");
		s = s.replaceAll("\\[/sup\\]", "</sup>");

		// Subcript
		s = s.replaceAll("\\[sub\\]", "<sub>");
		s = s.replaceAll("\\[/sub\\]", "</sub>");
		
		// Invisible comments
		s = s.replaceAll("\\{\\{(.*?)\\}\\}", "<!--$1-->");

		// Colored text. The supported colors are the ones defined at MS Internet Explorer 4.0.
		s = s.replaceAll("\\[c (.+?)\\]", "<c $1>");
		s = s.replaceAll("\\[c\\]", "<c>");
		s = s.replaceAll("\\[/c\\]", "</c>");

		// Stressed vowels in a word. They are usually highlighted.
		s = s.replaceAll("\\['\\]", "<v>");
		s = s.replaceAll("\\[/'\\]", "</v>");

		// The language of the word or phrase. These tags are used in cards to mark 
		// words that are written in a language other than the target language.
		s = s.replaceAll("\\[lang(.*?)\\]", "<ln$1>");
		s = s.replaceAll("\\[/lang\\]", "</ln>");
		
		// Transcription zone
		s = s.replaceAll("\\[t\\]", "<t>");
		s = s.replaceAll("\\[/t\\]", "</t>");
		
		// Abbreviations (They can be replaced by the viewer directly at the article or linked as pop up window)
		s = s.replaceAll("\\[p\\]", "<d>");
		s = s.replaceAll("\\[/p\\]", "</d>");

		// References
		s = s.replaceAll("\\[ref(.*?)\\](.*?)\\[/ref\\]", "<a href=\"$2\"$1>$2</a>");
		s = s.replaceAll("<<(.+?)>>", "<a class=\"v2\" href=\"$1\">$1</a>");
		
		// Media resources. Can't reuse s because it's strike in html.
		s = s.replaceAll("\\[s\\]", "<ss>");
		s = s.replaceAll("\\[/s\\]", "</ss>");
		
		// Examples
		s = s.replaceAll("\\[ex\\]", "<ex>");
		s = s.replaceAll("\\[/ex\\]", "</ex>");
		
		// Comments zone. This zone provides additional information about the translation.
		s = s.replaceAll("\\[com\\]", "<cm>");
		s = s.replaceAll("\\[/com\\]", "</cm>");
		
		// No text index zone.
		s = s.replaceAll("\\[!trs\\]", "<ntrs>");
		s = s.replaceAll("\\[/!trs\\]", "</ntrs>");
		
		// Translation zone. It contains translation of the head word.
		s = s.replaceAll("\\[trn\\]", "<tn>");
		s = s.replaceAll("\\[/trn\\]", "</tn>");
		
		// Full translation zone mode tags. These ones are wrapped into <trf> 
		// and can be later processed by the viewer if needed. 
		s = s.replaceAll("\\[\\*\\]", "<ft>");
		s = s.replaceAll("\\[/\\*\\]", "</ft>");
		
		// Left paragraph margins mN where N defines the number of spaces from  left side. 
		// May vary from [m0] to [m9]. The range used in practice is usually from [m0] to [m4].
		s = s.replaceAll("\\[m(.*?)\\]", "<div class=\"m$1\">");
		s = s.replaceAll("\\[/m\\]", "</div>");
		
		// Special characters
		s = s.replaceAll("\\\\ ",    "&nbsp;"); // "\ "
		s = s.replaceAll("\\\\\\\\", "&#47;");  // "\\"
		s = s.replaceAll("\\\\\\[",  "&#91;");  // "\["
		s = s.replaceAll("\\\\\\]",  "&#93;");  // "\]"
		s = s.replaceAll("\\\\\\{",  "&#123;"); // "\{"
		s = s.replaceAll("\\\\\\}",  "&#125;"); // "\}"
		s = s.replaceAll("\\\\\\@",  "&#64;");  // "\@"
		
		return s;
		
	}
	
	public static String convertDSLAdaptedHtmlToHtml(String s) {
		
		// Don't process blank string
		if (StringUtils.isBlank(s)) {
			return s;
		}
		
		// Colors
		s = s.replaceAll("<c(.*?)>(.*?)</c>", "<span style=\"color:$1\">$2</span>");
		
		String outTag = "<div";
		
		//s = s.replaceAll("\\[/m\\]", "[/m]<br/>"); // Append break to list items
		s = s.replaceAll("<div class=\"m0\">", outTag + ">");
		s = s.replaceAll("<div class=\"m1\">", outTag + " style=\"margin-left:15px\">");
		s = s.replaceAll("<div class=\"m2\">", outTag + " style=\"margin-left:30px\">");
		s = s.replaceAll("<div class=\"m3\">", outTag + " style=\"margin-left:45px\">");
		s = s.replaceAll("<div class=\"m4\">", outTag + " style=\"margin-left:60px\">");
		
		s = s.trim();
		
		return s;
	}
	
	public static String injectDSLWord(String word, String article) {
		String fullArticle = "<div class=\"header\">" + word + "</div><br/>" + article;
		return fullArticle;
	}

	public static String convertDSLWordToIndexedWord(String word) {
		String result = word;
		if (!StringUtils.isBlank(result)) {
			// First replace the non-indexed parts with spaces at both sides
			result = result.replaceAll(" \\{(.*?)\\} ", " ");
			
			// Second replace the ones w/o spaces
			result = result.replaceAll("\\{(.*?)\\}", "");
		}
		if (result != null) {
			result = result.trim();
		}
		return result;
	}
	
}

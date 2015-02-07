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
public class DSLFormatUtils {

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

		// Colored text. The supported colors are the ones defined at MS Internet Explorer 4.0.
		s = s.replaceAll("\\[c\\]", "<c>");
		s = s.replaceAll("\\[/c\\]", "</c>");

		// Stressed vowels in a word. They are usually highlighted.
		s = s.replaceAll("\\['\\]", "<v>");
		s = s.replaceAll("\\[/'\\]", "</v>");

		// The language of the word or phrase. These tags are used in cards to mark 
		// words that are written in a language other than the target language.
		s = s.replaceAll("\\[lang\\]", "<lng>");
		s = s.replaceAll("\\[/lang\\]", "</lng>");
		
		// Transcription zone
		s = s.replaceAll("\\[t\\]", "<t>");
		s = s.replaceAll("\\[/t\\]", "</t>");
		
		// Abbreviations (They can be replaced by the viewer directly at the article or linked as pop up window)
		s = s.replaceAll("\\[p\\]", "<abbr>");
		s = s.replaceAll("\\[/p\\]", "</abbr>");

		// References
		s = s.replaceAll("\\[ref\\]", "<r>");
		s = s.replaceAll("\\[/ref\\]", "</r>");

		// Media resources. Can't reuse s because it's strike in html.
		s = s.replaceAll("\\[s\\]", "<ss>");
		s = s.replaceAll("\\[/s\\]", "</ss>");
		
		// Examples
		s = s.replaceAll("\\[ex\\]", "<ex>");
		s = s.replaceAll("\\[/ex\\]", "</ex>");
		
		// Comments zone. This zone provides additional information about the translation.
		s = s.replaceAll("\\[com\\]", "<comm>");
		s = s.replaceAll("\\[/com\\]", "</comm>");
		
		// No text index zone.
		s = s.replaceAll("\\[!trs\\]", "<ntrs>");
		s = s.replaceAll("\\[/!trs\\]", "</ntrs>");
		
		// Translation zone. It contains translation of the head word.
		s = s.replaceAll("\\[trn\\]", "<trn>");
		s = s.replaceAll("\\[/trn\\]", "</trn>");
		
		// Full translation zone mode tags. These ones are wrapped into <trf> 
		// and can be later processed by the viewer if needed. 
		s = s.replaceAll("\\[\\*\\]", "<trf>");
		s = s.replaceAll("\\[/\\*\\]", "</trf>");
		
		// Left paragraph margins mN where N defines the number of spaces from  left side. 
		// May vary from m0 to m9. The range used in practice is usually from m0 to m4.
		s = s.replaceAll("\\[m0\\]", "<m class=\"m0\">");
		s = s.replaceAll("\\[m1\\]", "<m class=\"m1\">");
		s = s.replaceAll("\\[m2\\]", "<m class=\"m2\">");
		s = s.replaceAll("\\[m3\\]", "<m class=\"m3\">");
		s = s.replaceAll("\\[m4\\]", "<m class=\"m4\">");
		s = s.replaceAll("\\[m5\\]", "<m class=\"m5\">");
		s = s.replaceAll("\\[m6\\]", "<m class=\"m6\">");
		s = s.replaceAll("\\[m7\\]", "<m class=\"m7\">");
		s = s.replaceAll("\\[m8\\]", "<m class=\"m8\">");
		s = s.replaceAll("\\[m9\\]", "<m class=\"m9\">");
		s = s.replaceAll("\\[/m\\]", "</m>");
		
		// Special characters
		s = s.replaceAll("\\\\ ", "&nbsp;");   // "\ "
		s = s.replaceAll("\\\\\\[", "&#91;");  // "\["
		s = s.replaceAll("\\\\\\]", "&#93;");  // "\]"
		s = s.replaceAll("\\\\\\{", "&#123;"); // "\{"
		s = s.replaceAll("\\\\\\}", "&#125;"); // "\}"
		s = s.replaceAll("\\\\\\@", "&#64;");  // "\@"	At-sign: \@ [b]Note:[/b] Sub-entries
		
		
		
		return s;
		
	}
	
	public static String convertDSLAdaptedHtmlToHtml(String s) {
		
		// Don't process blank string
		if (StringUtils.isBlank(s)) {
			return s;
		}

		// Colors (http://www.w3schools.com/htmL/html_colorvalues.asp)
//		s = s.replaceAll("\\[c\\]", "<span id=\"cund\" style=\"color:" + HtmlConstants.BROWSER_CL_GREEN + "\">");
//		s = s.replaceAll("\\[c green\\]", "<span id=\"cgreen\" style=\"color:" + HtmlConstants.BROWSER_CL_GREEN + "\">");
//		s = s.replaceAll("\\[c red\\]", "<span id=\"cred\" style=\"color:" + HtmlConstants.BROWSER_CL_RED + "\">");
//		s = s.replaceAll("\\[c black\\]", "<span id=\"cblack\">");
//		s = s.replaceAll("\\[c brown\\]", "<span id=\"cbrown\" style=\"color:" + HtmlConstants.BROWSER_CL_BROWN + "\">");
//		s = s.replaceAll("\\[/c\\]", "</span>");
		
		// Punctuation
//		s = s.replaceAll("\\[p\\]", "<span id=\"punct\" style=\"color:" + HtmlConstants.BROWSER_CL_GREEN + "\">");
//		s = s.replaceAll("\\[/p\\]", "</span>");

//		s = s.replaceAll("\\[ex\\]", "<span id=\"example\" style=\"color:" + HtmlConstants.BROWSER_CL_DIM_GRAY + "\">");
//		s = s.replaceAll("\\[/ex\\]", "</span>");

//		// Unescape []
//		s = s.replaceAll("\\\\\\[", "[");
//		s = s.replaceAll("\\\\\\]", "]");

		// Lists

//		// Put spaces before all m1 except 1st one
//		if (s.indexOf("[m1]") != s.lastIndexOf("[m1]")) {
//			// Prepend m1 with break
//			s = s.replaceAll("\\[m1\\]", "<br/>[m1]");
//			// Remove break before the first m1
//			int firstM1 = s.indexOf("[m1]");
//			s = s.substring(0, firstM1 - 5) + s.substring(firstM1);
//		}

//		s = s.replaceAll("\\[m2\\]", "<br/>[m2]");
//
//		s = s.replaceAll("\\[/m\\]", "[/m]<br/>"); // Append break to list items
//		s = s.replaceAll("\\[m1\\]", "<span id=\"m1\">");
//		s = s.replaceAll("\\[m2\\]", "<span id=\"m2\" style=\"margin-left:15px\">");
//		s = s.replaceAll("\\[m3\\]", "<span id=\"m3\" style=\"margin-left:35px\">");
//		s = s.replaceAll("\\[m4\\]", "<span id=\"m4\" style=\"margin-left:35px\">");
//		s = s.replaceAll("\\[/m\\]", "</span>");
		
		String outTag = "<div";
		
		s = s.replaceAll("\\[/m\\]", "[/m]<br/>"); // Append break to list items
		s = s.replaceAll("<m class=\"m0\">", outTag + ">");
		s = s.replaceAll("<m class=\"m1\">", outTag + " style=\"margin-left:15px\">");
		s = s.replaceAll("<m class=\"m2\">", outTag + " style=\"margin-left:30px\">");
		s = s.replaceAll("<m class=\"m3\">", outTag + " style=\"margin-left:45px\">");
		s = s.replaceAll("<m class=\"m4\">", outTag + " style=\"margin-left:60px\">");
		s = s.replaceAll("</m>", "</div>");
		
		s = s.trim();
		
		return s;
	}
			
			
	
}

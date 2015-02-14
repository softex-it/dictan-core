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
 * @since version 4.6, 02/06/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLWriteFormatUtils {
	
	/**
	 * The method does the opposite to <code>DSLReadFormatUtils.convertDSLToAdaptedHtml</code>
	 */
	public static String convertAdaptedHtmlToDSL(String s) {
		
		// Don't process blank string
		if (StringUtils.isBlank(s)) {
			return s;
		}
		
		// Italic
		s = s.replaceAll("<i>", "[i]");
		s = s.replaceAll("</i>", "[/i]");
		
		// Bold
		s = s.replaceAll("<b>", "[b]");
		s = s.replaceAll("</b>", "[/b]");

		// Underlined
		s = s.replaceAll("<u>", "[u]");
		s = s.replaceAll("</u>", "[/u]");
		
		// Superscript
		s = s.replaceAll("<sup>", "[sup]");
		s = s.replaceAll("</sup>", "[/sup]");

		// Subcript
		s = s.replaceAll("<sub>", "[sub]");
		s = s.replaceAll("</sub>", "[/sub]");
		
		// Invisible comments
		s = s.replaceAll("<!--(.*?)-->", "{{$1}}");
		
		// Colored text
		s = s.replaceAll("<c (.+?)>", "[c $1]");
		s = s.replaceAll("<c>", "[c]");
		s = s.replaceAll("</c>", "[/c]");
		
		// Stressed vowels in a word. They are usually highlighted.
		s = s.replaceAll("<v>", "[']");
		s = s.replaceAll("</v>", "[/']");
		
		// The language of the word or phrase.
		s = s.replaceAll("<ln(.*?)>", "[lang$1]");
		s = s.replaceAll("</ln>", "[/lang]");
		
		// Transcription zone
		s = s.replaceAll("<t>", "[t]");
		s = s.replaceAll("</t>", "[/t]");
		
		// Abbreviations 
		s = s.replaceAll("<d>", "[p]");
		s = s.replaceAll("</d>", "[/p]");
		
		// References
		s = s.replaceAll("<a href=\"(.*?)\"(.*?)>(.*?)</a>", "\\[ref$2\\]$1\\[/ref\\]");
		s = s.replaceAll("<a class=\"v2\" href=\"(.*?)\"(.*?)>(.*?)</a>", "<<$1>>");
		
		// Media resources. Can't reuse s because it's strike in html.
		s = s.replaceAll("<ss>", "[s]");
		s = s.replaceAll("</ss>", "[/s]");
		
		// Examples
		s = s.replaceAll("<ex>", "[ex]");
		s = s.replaceAll("</ex>", "[/ex]");
		
		// Comments zone
		s = s.replaceAll("<cm>", "[com]");
		s = s.replaceAll("</cm>", "[/com]");
		
		// Translation zone
		s = s.replaceAll("<tn>", "[trn]");
		s = s.replaceAll("</tn>", "[/trn]");
		
		// Full translation zone
		s = s.replaceAll("<ft>", "[*]");
		s = s.replaceAll("</ft>", "[/*]");
		
		// Left paragraph margins
		s = s.replaceAll("<div class=\"m(.*?)\">", "[m$1]");
		s = s.replaceAll("</div>", "[/m]");
		
		// Special characters
		s = s.replaceAll("&nbsp;", "\\\\ ");
		s = s.replaceAll("&#47;",  "\\\\\\\\");  // "\\"
		s = s.replaceAll("&#91;",  "\\\\["); // "\["
		s = s.replaceAll("&#93;",  "\\\\]"); // "\]"
		s = s.replaceAll("&#123;", "\\\\{"); // "\{"
		s = s.replaceAll("&#125;", "\\\\}"); // "\}"		
		s = s.replaceAll("&#64;",  "\\\\@"); // "\}"	
		
		return s;
	}

}

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
	
	protected final static String TEMP_REPLACEMENT_1 = "79e1f3c7-6cdd-4e3c-8ebf-13a6658d126f";
	protected final static String TEMP_REPLACEMENT_2 = "95566f0c-7c64-48d7-8d6d-457b3d6a9d25";
	protected final static String TEMP_REPLACEMENT_3 = "39ba0938-69b1-4fd5-99b9-359bb6f161ce";
	protected final static String TEMP_REPLACEMENT_4 = "024ddfc8-dd76-4298-ba19-3fefc9e35063";
	
	protected final static String CHAR_LT = "&#60;"; // <
	protected final static String CHAR_GT = "&#62;"; // >
	
	protected final static String CHAR_LCBRACE = "&#123;"; // {
	protected final static String CHAR_RCBRACE = "&#125;"; // }
	
	protected final static String HTML_BR = "<br/>";
	
	/**
	 * Converts DSL markup into adapted HTML. 
	 * See http://lingvo.helpmax.net/en/troubleshooting/dsl-compiler/dsl-tags/
	 */
	public static String convertDSLToAdaptedHtml(String s) {
		
		// Don't process blank string
		if (StringUtils.isBlank(s)) {
			return s;
		}
		
		// Special characters, unescaped, first order
		s = s.replaceAll("&",        "&#38;");  // "&", always first
		s = s.replaceAll("<",        CHAR_LT);  // "<"
		s = s.replaceAll(">",        CHAR_GT);  // ">"
		
		// Special characters, escaped, last order
		s = s.replaceAll("\\\\ ",    "&#160;"); // "\ ", non-breaking space, aka &nbsp;
		s = s.replaceAll("\\\\\\\\", "&#47;");  // "\\"
		s = s.replaceAll("\\\\\\[",  "&#91;");  // "\["
		s = s.replaceAll("\\\\\\]",  "&#93;");  // "\]"
		s = s.replaceAll("\\\\\\{",  CHAR_LCBRACE); // "\{"
		s = s.replaceAll("\\\\\\}",  CHAR_RCBRACE); // "\}"
		s = s.replaceAll("\\\\\\(",  "&#40;"); // "\("
		s = s.replaceAll("\\\\\\)",  "&#41;"); // "\)"
		s = s.replaceAll("\\\\\\@",  "&#64;");  // "\@"
		s = s.replaceAll("\\\\\\~",  "&#126;"); // "\~"
		
		// References
		s = s.replaceAll("\\[ref(.*?)\\](.*?)\\[/ref\\]", "<a href=\"$2\"$1>$2</a>");
		s = s.replaceAll("&#60;&#60;(.+?)&#62;&#62;", "<a class=\"v2\" href=\"$1\">$1</a>"); // <<link>>,
		s = s.replaceAll("\\[url(.*?)\\](.*?)\\[/url\\]", "<a class=\"v3\" href=\"$2\"$1>$2</a>"); // external links
		
		// Abbreviations (They can be replaced by the viewer directly at the article or linked as pop up window)
		s = s.replaceAll("\\[p\\]", "<w>");
		s = s.replaceAll("\\[/p\\]", "</w>");
		
		// Media resources. Can't reuse s because it's strike in HTML.
		s = s.replaceAll("\\[s\\]", "<r>");
		s = s.replaceAll("\\[/s\\]", "</r>");
		
		// Replace all basic tags
		s = convertDSLDesignTagsToAdaptedHtml(s);
		
		// Specific conversion for Dictan. It helps wrap and style braces (\[ and \]) around transcriptions
		// Must be done before the conversion of other comments
		s = s.replaceAll("\\{\\{t\\}\\}(.*?)\\{\\{/t\\}\\}", "<o>$1</o>");
		
		// Invisible comments
		s = s.replaceAll("\\{\\{(.*?)\\}\\}", "<!--$1-->");
		
		// Transcription zone
		s = s.replaceAll("\\[t (.*?)\\]", "<t $1>"); // Space is needed to differ it from [trn]
		s = s.replaceAll("\\[t\\]", "<t>");
		s = s.replaceAll("\\[/t\\]", "</t>");
		
		// The language of the word or phrase. These tags are used in cards to mark 
		// words that are written in a language other than the target language.
		s = s.replaceAll("\\[lang(.*?)\\]", "<l$1>");
		s = s.replaceAll("\\[/lang\\]", "</l>");
		
		// Examples
		s = s.replaceAll("\\[ex(.*?)\\]", "<e$1>");
		s = s.replaceAll("\\[/ex\\]", "</e>");
		
		// Comments zone. This zone provides additional information about the translation.
		s = s.replaceAll("\\[com(.*?)\\]", "<c$1>");
		s = s.replaceAll("\\[/com\\]", "</c>");
		
		// Translation zone. It contains translation of the head word.
		s = s.replaceAll("\\[trn\\]", "<n>");
		s = s.replaceAll("\\[/trn\\]", "</n>");
		
		// Full translation zone mode tags. They can be later processed by the viewer if needed. 
		s = s.replaceAll("\\[\\*\\]", "<f>");
		s = s.replaceAll("\\[/\\*\\]", "</f>");
		
		// No text index zone.
		s = s.replaceAll("\\[!trs\\]", "<m>");
		s = s.replaceAll("\\[/!trs\\]", "</m>");
		
		// Before [/m] is formatted, append artificial breaks to all lines which don't end with [/m]
		s = addDSLLineBreaks(s);
		
		// Left paragraph margins mN where N defines the number of spaces from  left side. 
		// May vary from [m0] to [m9]. The range used in practice is usually from [m0] to [m4].
		s = s.replaceAll("\\[m(.*?)\\]", "<div class=\"m$1\">");
		s = s.replaceAll("\\[/m\\]", "</div>");
		
		return s;
		
	}
	
	public static String convertDSLDesignTagsToAdaptedHtml(String s) {
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
		
		// Colored text. The supported colors are the ones defined at MS IE 4.0.
		s = s.replaceAll("\\[c (.*?)\\]", "<span class=\"ca\" style=\"color:$1\">"); // color in attribute
		s = s.replaceAll("\\[c\\]", "<span class=\"cd\">"); // color by default
		s = s.replaceAll("\\[/c\\]", "</span>");

		// Stressed vowels in a word. They are usually highlighted.
		s = s.replaceAll("\\['\\]", "<v>");
		s = s.replaceAll("\\[/'\\]", "</v>");
		
		return s;
	}

	/**
	 * Adds HTML line breaks to the DSL markup. It should be called before [/m] is replaced.
	 */
	protected static String addDSLLineBreaks(String s) {
		
		// Don't process null values
		if (s == null) {
			return s;
		}
		
		// Before [/m] is formatted, append artificial breaks to all lines which don't end with [/m]
		String[] splitLines = s.split("\n", -1); // array will contain empty string as well
		StringBuffer linesBuffer = new StringBuffer();
		if (splitLines.length == 1) {
			String line = splitLines[0];
			linesBuffer.append(line);
			if (!line.trim().endsWith("[/m]")) {
				linesBuffer.append(HTML_BR);
			}
		} else {
			for (int i = 0; i < splitLines.length; i++) {
				
				String line = splitLines[i];
				
				linesBuffer.append(line);
				
				if (i != splitLines.length - 1 || line.trim().length() != 0) {
					if (i < splitLines.length - 1) {
						linesBuffer.append("\n");
					}
					if (!line.trim().endsWith("[/m]")) {
						linesBuffer.append(HTML_BR);
					}
				}

			}
		}
		
		if (linesBuffer.length() > 0) { // If buffer is not empty return it
			// Append one more break to always have at least 1 empty line in the bottom
			linesBuffer.append(HTML_BR);
			return linesBuffer.toString();		
		} else { // Otherwise return the original the original string
			return s;
		}
		
	}

	public static String convertDSLWordToIndexedWord(String word) {
		String result = word;
		
		if (!StringUtils.isBlank(result)) {
			
			// Temporary replace \{ and \} --------------------------------
			result = result.replaceAll("\\\\\\{",  TEMP_REPLACEMENT_1);
			result = result.replaceAll("\\\\\\}",  TEMP_REPLACEMENT_2);
			
			// Replace the non-indexed parts with spaces at both sides
			result = result.replaceAll(" \\{(.*?)\\} ", " ");
			
			// Second replace the ones w/o spaces
			result = result.replaceAll("\\{(.*?)\\}", "");
			
			// Return \{ and \} back
			result = result.replaceAll(TEMP_REPLACEMENT_1, "\\{");
			result = result.replaceAll(TEMP_REPLACEMENT_2, "\\}");
			
			// Temporary replace \( and \) -------------------------------
			result = result.replaceAll("\\\\\\(",  TEMP_REPLACEMENT_3);
			result = result.replaceAll("\\\\\\)",  TEMP_REPLACEMENT_4);
			
			// Remove all ( and )
			result = result.replaceAll("\\(", "");
			result = result.replaceAll("\\)", "");
			
			// Replace all temporary replacements with ( and )
			result = result.replaceAll(TEMP_REPLACEMENT_3, "\\(");
			result = result.replaceAll(TEMP_REPLACEMENT_4, "\\)");
			
		}
		
		if (result != null) {
			result = result.trim();
		}
		
		return result;
	}
	
}

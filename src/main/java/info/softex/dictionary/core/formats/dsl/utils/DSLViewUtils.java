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

import info.softex.dictionary.core.utils.BaseConstants;
import info.softex.dictionary.core.utils.FileTypeUtils;
import info.softex.dictionary.core.utils.FileUtils;
import info.softex.dictionary.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @since version 4.6, 02/16/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLViewUtils {
	
	protected final static String CHAR_LCBRACE = "&#123;"; // {
	protected final static String CHAR_RCBRACE = "&#125;"; // }
	
	protected final static String CHAR_LPAREN = "&#40;"; // (
	protected final static String CHAR_RPAREN = "&#41;"; // )
	
	protected final static Pattern PT_DSL_RESOURCE = Pattern.compile("<r>(.*?)</r>");
	protected final static Pattern PT_DSL_ABBREV = Pattern.compile("<w>(.*?)</w>");
	protected final static Pattern PT_DSL_XML = Pattern.compile("<[^>]*>");
	
	protected final static String EMPTY = "";
	
	@SuppressWarnings("serial")
	protected final static Map<String, String> DSL_TO_HTML_TAGS = new HashMap<String, String>() {{
		put("<t ", "<span class=\"t\" ");
		put("<t>", "<span class=\"t\">");
		put("<v ", "<span class=\"v\" ");
		put("<v>", "<span class=\"v\">");
		put("<e ", "<span class=\"e\" ");
		put("<e>", "<span class=\"e\">");
		put("/t>", "/span>");
		put("/v>", "/span>");
		put("/e>", "/span>");
	}};
	
	/**
	 * The method replaces all custom tags with span tags.
	 * It may be necessary at the viewers that don't support custom tags,
	 * e.g. Java <code>HTMLFactory</code>.
	 * 
	 * @param s
	 * @return
	 */
	public static String convertDSLHtmlToHtml4(String s) {
		
		// Don't process blank strings and the ones with length < 3
		if (StringUtils.isBlank(s) || s.length() < 3) {
			return s;
		}
		
		StringBuffer result = new StringBuffer();	
		
		char buffer[] = new char[3];
		
		for (int i = 0; i < s.length(); i++) {
			
			char curChar = s.charAt(i);
			
			if (curChar == '<' && s.length() > i + 1) {
				// Read 2 next chars
				buffer[0] = curChar;
				buffer[1] = s.charAt(++i);
				buffer[2] = s.charAt(++i);
				String orig = new String(buffer);
				String repl = DSL_TO_HTML_TAGS.get(orig);
				if (repl != null) {
					result.append(repl);
				} else {
					result.append(orig);
				}
			} else if (curChar == '>' && i > 1) {
				// Read 2 previous chars
				buffer[0] = s.charAt(i - 2);
				buffer[1] = s.charAt(i - 1);
				buffer[2] = curChar;
				String orig = new String(buffer);
				String repl = DSL_TO_HTML_TAGS.get(orig);
				if (repl != null) {
					result.delete(result.length() - 2, result.length());
					result.append(repl);
				} else {
					result.append(curChar);	
				}
			} else {
				result.append(curChar);	
			}
			
		}

		return result.toString();
	}
	
	public static String injectDSLWord(String word, String article) {
		StringBuffer result = new StringBuffer();
		result.append("<div class=\"header\">");
		
		if (StringUtils.isNotBlank(word)) {
			word = word.replaceAll("\\\\\\{", CHAR_LCBRACE); // "\{"
			word = word.replaceAll("\\\\\\}", CHAR_RCBRACE); // "\}"
			word = word.replaceAll("\\\\\\(", CHAR_LPAREN);  // "\("
			word = word.replaceAll("\\\\\\)", CHAR_RPAREN);  // "\)"
			
			word = word.replaceAll("\\{(.*?)\\}", "$1");
		}
		
		result.append(word);
		result.append("</div>");
		
		// Some bases have the empty paragraph included
		if (!article.startsWith("<div class=\"m1\">&#160;</div>")) {
			result.append("<br/>");
		}
		
		result.append(article);

		return result.toString();
	}
	
	public static String convertDSLResourcesToHtml(String s) {
		
		StringBuffer sb = new StringBuffer(s.length());
		
		Matcher m = PT_DSL_RESOURCE.matcher(s);
		while (m.find()) {
			
		    String resName = cleanDSLResource(m.group(1));
		    
		    FileTypeUtils.MediaType type = FileTypeUtils.getMediaTypeByExtension(FileUtils.getFileExtension(resName));
		    switch (type) {
		    	case IMAGE:
				    m.appendReplacement(sb, Matcher.quoteReplacement("<img class=\"int\" src=\"" +
				    	BaseConstants.URLSEG_IMAGES + BaseConstants.PATH_SEPARATOR + resName + "\" />"));
			    		//BaseConstants.URLSEG_IMAGES + BaseConstants.PATH_SEPARATOR + resName + "\" width=\"139\" />"));
				    	//BaseConstants.URLSEG_IMAGES + BaseConstants.PATH_SEPARATOR + resName + "\" width=\"139\" height=\"262\" />"));	
		    	break;
		    	case AUDIO:
		    		String replAud = "<a href=\"" + BaseConstants.URLSEG_AUDIO + BaseConstants.PATH_SEPARATOR + 
		    			resName + "\"><img src=\"" + BaseConstants.URLSEG_IMAGES_EXTERNAL + BaseConstants.PATH_SEPARATOR + 
		    			BaseConstants.RESOURCE_INT_IMG_SOUND + "\" border=\"0\" style=\"vertical-align:middle\"/></a>";
		    		
				    m.appendReplacement(sb, Matcher.quoteReplacement(replAud));
		    	break;
		    	case VIDEO:
				    m.appendReplacement(sb, Matcher.quoteReplacement("<video src=\"" +
					    BaseConstants.URLSEG_VIDEO + BaseConstants.PATH_SEPARATOR + resName + "\" controls/>"));
		    	break;
		    	default:
				    m.appendReplacement(sb, Matcher.quoteReplacement("<res src=\"" + resName + "\"/>"));	
		    }

		}
		
		m.appendTail(sb);
		
		return sb.toString();
	}
	
	public static String convertDSLAbbreviationsToHtml(String s) {
		StringBuffer sb = new StringBuffer(s.length());
		Matcher m = PT_DSL_ABBREV.matcher(s);
		while (m.find()) {
			String resName = cleanDSLResource(m.group(1));
			m.appendReplacement(sb, Matcher.quoteReplacement("<a href=\"abbr/" + resName + "\">" + resName + "</a>"));	
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	protected static String cleanDSLResource(String res) {
		if (res != null) {
			res = PT_DSL_XML.matcher(res).replaceAll(EMPTY);
			res = res.replaceAll("&#160;", " "); // Replace non-breaking spaces that are encountered in the resource
		}
		return res;
	}
	
}

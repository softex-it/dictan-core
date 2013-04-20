/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2011  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;

import java.text.DecimalFormat;

/**
 * 
 * @since version 2.0, 03/13/2011
 * 
 * @modified version 2.6, 09/17/2011
 *  
 * @author Dmitry Viktorov
 * 
 */
public class BaseInfoHtmlBuilder {
	
	public static String buildDictionaryInfoTable(BasePropertiesInfo baseInfo, LanguageDirectionsInfo directionsInfo) {
			
		String compDate = wrapRow("Compilation Date", getShortDate(baseInfo.getCompilationDate()), true);
		
		String baseVersion = wrapRow("Base Version", baseInfo.getBaseVersion(), true);
		String baseDate = wrapRow("Base Date", getShortDate(baseInfo.getBaseDate()), true);
		
		String baseSize = "Base Size: <dvl>" + getConvertedSize(baseInfo.getBaseFileSize()) + "</dvl>";
		String basePartsNumber = wrapRow("Base Parts Number", baseInfo.getBasePartsTotalNumber(), baseInfo.getBasePartsTotalNumber() > 1);
		
		String wordsNumber = "Number of Words: <dvl>" + baseInfo.getArticlesNumber() + "</dvl>";
		String formatVer = "Format Version: <dvl>" + baseInfo.getFormatVersion() + "</dvl>";
		
		String artFormat = "Articles Formatting: <dvl>" + baseInfo.getArticlesFormattingMode() + "</dvl>";
		String abbrFormat = wrapRow("Abbrev. Formatting", baseInfo.getAbbreviationsFormattingMode(), baseInfo.getAbbreviationsNumber() > 0);
		
		String codePage = "";
		if (baseInfo.getWordsCodepageName() != null) {
			if (baseInfo.getWordsCodepageName().equalsIgnoreCase(baseInfo.getArticleCodepageName())) {
				codePage = "<tr><td>Codepage: <dvl>" + formatCPString(baseInfo.getWordsCodepageName()) + 
					"</dvl></td></tr>";
			} else {
				codePage = "<tr><td>Codepages: <dvl>" + formatCPString(baseInfo.getWordsCodepageName()) + 
					" / " + formatCPString(baseInfo.getArticleCodepageName()) + "</dvl></td></tr>";
			}
		}

		//String flags = "Flags: <dvl>" + dictInfo.getDictionaryFlags().getStringFlagsAsString() + "</dvl>";
		//String tbSize = "Translation Block Size: <dvl>" + dictInfo.getTransBlockSize() + "</dvl>";
		
		String media = "";
		if (baseInfo.isMediaBaseSeparate()) {
			if (baseInfo.getMediaResourcesNumber() != 0) {
				String resNumber = "Number of Resources: <dvl>" + baseInfo.getMediaResourcesNumber() + "</dvl>";
				String mediaFormat = "Format Version: <dvl>" + baseInfo.getMediaFormatVersion() + "</dvl>";
				String mediaFileSize = "File Size: <dvl>" + getConvertedSize(baseInfo.getBaseFileSize()) + "</dvl>";
				
				media = "<tr><th class=\"subHeader1\">Media Base: " + baseInfo.getMediaFormatName() + "</th></tr>";
				media += "<tr><td>" + resNumber + "</td></tr>";
				media += "<tr><td>" + mediaFormat + "</td></tr>";
				media += "<tr><td>" + mediaFileSize + "</td></tr>";
			} else {
				media = "<tr><th class=\"subHeader1\">Media Base: Not Available</th></tr>";
			}
		}
		
		//"&nbsp;&nbsp;<a href=\"content://info.softex.dictan/show/dialog\">(Flags)</a>"
		String html = "<table>" + 
			"<tr><th class=\"subHeader1\">Dictionary Base: " + baseInfo.getFormatName() + "</th></tr>" +
			"<tr><td>" + wordsNumber + "</td></tr>";
		
			if (baseInfo.getAbbreviationsNumber() > 0) {
				String abbrevNumber = "Number of Abbreviations: <dvl>" + baseInfo.getAbbreviationsNumber() + "</dvl>";
				html += "<tr><td>" + abbrevNumber + "</td></tr>";
			}
			
			if (!baseInfo.isMediaBaseSeparate() && baseInfo.getMediaResourcesNumber() > 0) {
				String resNumber = "Number of Resources: <dvl>" + baseInfo.getMediaResourcesNumber() + "</dvl>";
				html += "<tr><td>" + resNumber + "</td></tr>";
			}
			
			html += "<tr><td>" + formatVer + "</td></tr>" +
			//"<tr><td>" + tbSize + "</td></tr>" +
			//"<tr><td>" + langDirections + "</td></tr>" +
			codePage +
			"<tr><td>" + artFormat + "</td></tr>" +
			abbrFormat +
			//"<tr><td>" + abbrFormat + "</td></tr>" +
			baseVersion +
			baseDate +
			compDate + 
			basePartsNumber +
			"<tr><td>" + baseSize + "<br/><br/></td></tr>" +
			media + "</table>";
		
		return html;
	}
	
	private static String getShortDate(String longDate) {
		String res = null;
		if (longDate != null) {
			String[] dateParts = longDate.split("T");
			if (dateParts.length > 1) {
				res = dateParts[0];
			}
		}
		return res;
	}
	
	private static String wrapRow(String title, Object row, boolean condition) {
		String result = "";
		if (row != null && condition) {
			String strRow = row.toString();
			if (strRow.trim().length() > 0) {
				result= "<tr><td>" + title + ": <dvl>" + strRow + "</dvl></td></tr>";
			}
			
		}
		return result;
	}
	
	private static String getConvertedSize(long l) {
		if (l > 1073741824) {
			return (new DecimalFormat("#.##")).format((double) l / 1073741824D) + " GB (" +l + " B)";
		} else {
			return (new DecimalFormat("#.##")).format((double) l / 1048576D) + " MB (" + l + " B)";
		}
	}
	
	private static String formatCPString(String cp) {
		if (cp == null || cp.length() < 1) {
			return cp;
		}
		
		if (cp.toLowerCase().startsWith("utf")) {
			return cp.substring(0,3).toUpperCase() + cp.substring(3);
		} else {
	        return cp.substring(0,1).toUpperCase() + cp.substring(1).toLowerCase();
		}
	}

}

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
import java.util.HashMap;

/**
 * 
 * @since version 2.0, 03/13/2011
 * 
 * @modified version 2.6, 09/17/2011
 * @modified version 3.7, 06/14/2013
 * @modified version 3.9, 12/05/2013
 *  
 * @author Dmitry Viktorov
 * 
 */
public class BaseInfoHtmlBuilder {
	
	protected enum StringKey {
		DICTIONARY_BASE,
		COMP_DATE,
		COMPILED_BY,
		BASE_VERSION,
		BASE_DATE,
		BASE_SIZE,
		BASE_PARTS_NUMBER,
		NUMBER_OF_WORDS,
		NUMBER_OF_ABBREVIATIONS,
		NUMBER_OF_RESOURCES,
		FORMAT_VERSION,
		ARTICLES_FORMATTING,
		ABBREV_FORMATTING,		
		FILE_SIZE,
		MEDIA_BASE,
		CODEPAGE,
		LANGUAGES
		;
		
		@SuppressWarnings("serial")
		protected static HashMap<StringKey, String> enStrings = new HashMap<StringKey, String>() {{
			put(DICTIONARY_BASE, "Dictionary Base");
			put(MEDIA_BASE, "Media Base");
			put(COMP_DATE, "Compilation Date");
			put(COMPILED_BY, "Compiled By");
			put(BASE_VERSION, "Base Version");
			put(BASE_DATE, "Base Date");
			put(BASE_SIZE, "Base Size");
			put(BASE_PARTS_NUMBER, "Number of Base Parts");
			put(NUMBER_OF_WORDS, "Number of Words");
			put(NUMBER_OF_ABBREVIATIONS, "Number of Abbreviations");
			put(NUMBER_OF_RESOURCES, "Number of Resources");
			put(FORMAT_VERSION, "Format Version");
			put(ARTICLES_FORMATTING, "Articles Formatting");
			put(ABBREV_FORMATTING, "Abbrev. Formatting");	
			put(CODEPAGE, "Codepage");
			put(LANGUAGES, "Languages");
		}};
		
		@SuppressWarnings("serial")
		protected static HashMap<StringKey, String> ruStrings = new HashMap<StringKey, String>() {{
			put(DICTIONARY_BASE, "Словарная База");
			put(MEDIA_BASE, "Медиа База");
			put(COMP_DATE, "Дата Сборки");
			put(COMPILED_BY, "Автор Сборки");
			put(BASE_VERSION, "Версия Базы");
			put(BASE_DATE, "Дата Базы");
			put(BASE_SIZE, "Размер");
			put(BASE_PARTS_NUMBER, "Количество Частей Базы");
			put(NUMBER_OF_ABBREVIATIONS, "Количество Сокращений");
			put(NUMBER_OF_WORDS, "Количество Слов");
			put(NUMBER_OF_RESOURCES, "Количество Ресурсов");
			put(FORMAT_VERSION, "Версия Формата");
			put(ARTICLES_FORMATTING, "Формат Статей");
			put(ABBREV_FORMATTING, "Формат Сокращений");
			put(CODEPAGE, "Кодовая Страница");
			put(LANGUAGES, "Языки");
		}};
	}
	
	protected static String getString(StringKey key, String lang) {
		String res = null;
		if (lang != null && lang.equalsIgnoreCase("ru")) {
			res = StringKey.ruStrings.get(key);
		}	
		if (res == null) {
			res = StringKey.enStrings.get(key);
		}
		return res;
	}
	
	public static String buildDictionaryInfoTable(BasePropertiesInfo baseInfo, LanguageDirectionsInfo langsInfo, String lang) {
		
		String compDate = wrapRow(getString(StringKey.COMP_DATE, lang), getShortDate(baseInfo.getCompilationDate()), true);
		
		String baseVersion = wrapRow(getString(StringKey.BASE_VERSION, lang), baseInfo.getBaseVersion(), true);
		String baseDate = wrapRow(getString(StringKey.BASE_DATE, lang), getShortDate(baseInfo.getBaseDate()), true);
		
		String baseSize = getString(StringKey.BASE_SIZE, lang) + ": <dvl>" + getConvertedSize(baseInfo.getBaseFileSize()) + "</dvl>";
		String basePartsNumber = wrapRow(getString(StringKey.BASE_PARTS_NUMBER, lang), baseInfo.getBasePartsTotalNumber(), baseInfo.getBasePartsTotalNumber() > 1);
		
		String wordsNumber = getString(StringKey.NUMBER_OF_WORDS, lang) + ": <dvl>" + baseInfo.getArticlesNumber() + "</dvl>";
		String formatVer = getString(StringKey.FORMAT_VERSION, lang) + ": <dvl>" + baseInfo.getFormatVersion() + "</dvl>";
		
		String artFormat = getString(StringKey.ARTICLES_FORMATTING, lang) + ": <dvl>" + baseInfo.getArticlesFormattingMode() + "</dvl>";
		String abbrFormat = wrapRow(getString(StringKey.ABBREV_FORMATTING, lang), baseInfo.getAbbreviationsFormattingMode(), baseInfo.getAbbreviationsNumber() > 0);
		
		String createdBy = wrapRow(getString(StringKey.COMPILED_BY, lang), baseInfo.getCompilationCreatorName(), true);

		String langDirections = "";
		if (langsInfo != null) {
			String langs = langsInfo.toLanguagePairsString(true);
			if (langs.length() != 0) {
				langDirections = "<tr><td>" + getString(StringKey.LANGUAGES, lang) + ": <dvl>" + langs + "</dvl></td></tr>";
			}
		}
		
		String codePage = "";
		if (baseInfo.getWordsCodepageName() != null) {
			if (baseInfo.getWordsCodepageName().equalsIgnoreCase(baseInfo.getArticleCodepageName())) {
				codePage = "<tr><td>" + getString(StringKey.CODEPAGE, lang) + ": <dvl>" + 
					formatCPString(baseInfo.getWordsCodepageName()) + "</dvl></td></tr>";
			} else {
				codePage = "<tr><td>" + getString(StringKey.CODEPAGE, lang) + ": <dvl>" + 
					formatCPString(baseInfo.getWordsCodepageName()) + 
					" / " + formatCPString(baseInfo.getArticleCodepageName()) + "</dvl></td></tr>";
			}
		}

		//String flags = "Flags: <dvl>" + dictInfo.getDictionaryFlags().getStringFlagsAsString() + "</dvl>";
		//String tbSize = "Translation Block Size: <dvl>" + dictInfo.getTransBlockSize() + "</dvl>";
		
		String media = "";
		if (baseInfo.isMediaBaseSeparate()) {
			if (baseInfo.getMediaResourcesNumber() != 0) {
				String resNumber = getString(StringKey.NUMBER_OF_RESOURCES, lang) + ": <dvl>" + baseInfo.getMediaResourcesNumber() + "</dvl>";
				String mediaFormat = getString(StringKey.FORMAT_VERSION, lang) + ": <dvl>" + baseInfo.getMediaFormatVersion() + "</dvl>";
				String mediaFileSize = getString(StringKey.BASE_SIZE, lang) + ": <dvl>" + getConvertedSize(baseInfo.getBaseFileSize()) + "</dvl>";
				
				media = "<tr><th class=\"subHeader1\">" + getString(StringKey.MEDIA_BASE, lang) + ": " + baseInfo.getMediaFormatName() + "</th></tr>";
				media += "<tr><td>" + resNumber + "</td></tr>";
				media += "<tr><td>" + mediaFormat + "</td></tr>";
				media += "<tr><td>" + mediaFileSize + "</td></tr>";
			} else {
				media = "<tr><th class=\"subHeader1\">" + getString(StringKey.MEDIA_BASE, lang) + ": N/A</th></tr>";
			}
		}
		
		//"&nbsp;&nbsp;<a href=\"content://info.softex.dictan/show/dialog\">(Flags)</a>"
		String html = "<table>" + 
			"<tr><th class=\"subHeader1\">" + getString(StringKey.DICTIONARY_BASE, lang) + ": " + baseInfo.getFormatName() + "</th></tr>" +
			"<tr><td>" + wordsNumber + "</td></tr>";
		
			if (baseInfo.getAbbreviationsNumber() > 0) {
				String abbrevNumber = getString(StringKey.NUMBER_OF_ABBREVIATIONS, lang) + ": <dvl>" + baseInfo.getAbbreviationsNumber() + "</dvl>";
				html += "<tr><td>" + abbrevNumber + "</td></tr>";
			}
			
			if (!baseInfo.isMediaBaseSeparate() && baseInfo.getMediaResourcesNumber() > 0) {
				String resNumber = getString(StringKey.NUMBER_OF_RESOURCES, lang) + ": <dvl>" + baseInfo.getMediaResourcesNumber() + "</dvl>";
				html += "<tr><td>" + resNumber + "</td></tr>";
			}
			
			html += langDirections +
				"<tr><td>" + artFormat + "</td></tr>" +
				//"<tr><td>" + tbSize + "</td></tr>" +
				abbrFormat +
				baseDate +
				baseVersion +
				codePage +
				createdBy +
				compDate + 
				basePartsNumber +
				"<tr><td>" + formatVer + "</td></tr>" +
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

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

package info.softex.dictionary.core.utils;

import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * 
 * @since version 2.0,		03/13/2011
 * 
 * @modified version 2.6,	09/17/2011
 * @modified version 3.7,	06/14/2013
 * @modified version 3.9,	12/05/2013
 * @modified version 4.0,	02/09/2014
 * @modified version 4.6,	02/08/2015
 *  
 * @author Dmitry Viktorov
 * 
 */
public class BaseInfoHtmlBuilder {
	
	protected final static DecimalFormat FORMATTER_SIZE = new DecimalFormat("#.##");
	protected final static DecimalFormat FORMATTER_NUMBER = new DecimalFormat("#,###");
	
	protected enum StringKey {
		FORMAT_VERSION,
		DICTIONARY_BASE,
		COMP_DATE,
		COMPILED_BY,
		BASE_VERSION,
		BASE_SIZE,
		BASE_PARTS_NUMBER,
		NUMBER_OF_WORDS_ARTICLES,
		NUMBER_OF_MAPPINGS_REDIRECTS,
		NUMBER_OF_ABBREVIATIONS,
		NUMBER_OF_RESOURCES,
		ARTICLES_FORMATTING,
		ARTICLES_FORMATTING_INJECT_WORDS,
		ABBREV_FORMATTING,		
		FILE_SIZE,
		MEDIA_BASE,
		CODEPAGE,
		LANGUAGES
		;
		
		@SuppressWarnings("serial")
		protected static HashMap<StringKey, String> enStrings = new HashMap<StringKey, String>() {{
			put(FORMAT_VERSION, "Ver.");
			put(DICTIONARY_BASE, "Dictionary Base");
			put(MEDIA_BASE, "Media Base");
			put(COMP_DATE, "Compilation Date");
			put(COMPILED_BY, "Compiled By");
			put(BASE_VERSION, "Version");
			put(BASE_SIZE, "Base Size");
			put(BASE_PARTS_NUMBER, "Number of Base Parts");
			put(NUMBER_OF_WORDS_ARTICLES, "Words, Articles");
			put(NUMBER_OF_MAPPINGS_REDIRECTS, "Mappings, Redirects");
			put(NUMBER_OF_ABBREVIATIONS, "Abbreviations");
			put(NUMBER_OF_RESOURCES, "Media Resources");
			put(ARTICLES_FORMATTING, "Articles Formatting");
			put(ARTICLES_FORMATTING_INJECT_WORDS, "Prefix Articles with Words");
			put(ABBREV_FORMATTING, "Abbrev. Formatting");
			put(CODEPAGE, "Codepage");
			put(LANGUAGES, "Languages");
		}};
		
		@SuppressWarnings("serial")
		protected static HashMap<StringKey, String> ruStrings = new HashMap<StringKey, String>() {{
			put(FORMAT_VERSION, "Ver.");
			put(DICTIONARY_BASE, "Словарная База");
			put(MEDIA_BASE, "Медиа База");
			put(COMP_DATE, "Дата Сборки");
			put(COMPILED_BY, "Автор Сборки");
			put(BASE_VERSION, "Версия");
			put(BASE_SIZE, "Размер");
			put(BASE_PARTS_NUMBER, "Количество Частей Базы");
			put(NUMBER_OF_ABBREVIATIONS, "Сокращения");
			put(NUMBER_OF_WORDS_ARTICLES, "Слова, Статьи");
			put(NUMBER_OF_MAPPINGS_REDIRECTS, "Маппинги, Редиректы");
			put(NUMBER_OF_RESOURCES, "Медиа Ресурсы");
			put(ARTICLES_FORMATTING, "Формат Статей");
			put(ARTICLES_FORMATTING_INJECT_WORDS, "Префикс Статей Словами");
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
		
		String combinedVersion = getShortDate(baseInfo.getBaseDate());
		if (combinedVersion == null) {
			combinedVersion = baseInfo.getBaseVersion();
		} else if (baseInfo.getBaseVersion() != null && baseInfo.getBaseVersion().length() != 0) {
			combinedVersion += " (" + baseInfo.getBaseVersion() + ")";
		}
		
		String baseVersion = wrapRow(getString(StringKey.BASE_VERSION, lang), combinedVersion, true);

		String baseSize = getString(StringKey.BASE_SIZE, lang) + ": <span class=\"dvl\">" + formatSize(baseInfo.getBaseFileSize()) + "</span>";
		String basePartsNumber = wrapRow(getString(StringKey.BASE_PARTS_NUMBER, lang), baseInfo.getBasePartsTotalNumber(), baseInfo.getBasePartsTotalNumber() > 1);
		
		String artFormat = wrapRow(getString(StringKey.ARTICLES_FORMATTING, lang), baseInfo.getArticlesFormattingMode(), true);
		String artFormatIWM = wrapRow(getString(StringKey.ARTICLES_FORMATTING_INJECT_WORDS, lang), baseInfo.getArticlesFormattingInjectWordMode(), true);
		String abbrFormat = wrapRow(getString(StringKey.ABBREV_FORMATTING, lang), baseInfo.getAbbreviationsFormattingMode(), baseInfo.getAbbreviationsNumber() > 0);
		
		String createdBy = wrapRow(getString(StringKey.COMPILED_BY, lang), baseInfo.getCompilationCreatorName(), true);

		String langDirections = "";
		if (langsInfo != null) {
			String langs = langsInfo.toLanguagePairsString(true);
			if (langs.length() != 0) {
				langDirections = "<tr><td>" + getString(StringKey.LANGUAGES, lang) + ": <span class=\"dvl\">" + langs + "</span></td></tr>";
			}
		}
		
		String codePage = "";
		if (baseInfo.getWordsCodepageName() != null) {
			if (baseInfo.getWordsCodepageName().equalsIgnoreCase(baseInfo.getArticleCodepageName())) {
				codePage = "<tr><td>" + getString(StringKey.CODEPAGE, lang) + ": <span class=\"dvl\">" + 
					formatCPString(baseInfo.getWordsCodepageName()) + "</span></td></tr>";
			} else {
				codePage = "<tr><td>" + getString(StringKey.CODEPAGE, lang) + ": <span class=\"dvl\">" + 
					formatCPString(baseInfo.getWordsCodepageName()) + 
					" / " + formatCPString(baseInfo.getArticleCodepageName()) + "</span></td></tr>";
			}
		}
		
		String media = "";
		if (baseInfo.isMediaBaseSeparate()) {
			if (baseInfo.getMediaResourcesNumber() != 0) {
				String resNumber = getString(StringKey.NUMBER_OF_RESOURCES, lang) + ": <span class=\"dvl\">" + formatNumber(baseInfo.getMediaResourcesNumber()) + "</span>";
				String mediaFileSize = getString(StringKey.BASE_SIZE, lang) + ": <span class=\"dvl\">" + formatSize(baseInfo.getBaseFileSize()) + "</span>";
				media = "<tr><th class=\"header-sub\">" + getString(StringKey.MEDIA_BASE, lang) + ": " + baseInfo.getMediaFormatName() + " (" + getString(StringKey.FORMAT_VERSION, lang) + " " + baseInfo.getMediaFormatVersion() + ")</th></tr>";
				media += "<tr><td>" + resNumber + "</td></tr>";
				media += "<tr><td>" + mediaFileSize + "</td></tr>";
			} else {
				media = "<tr><th class=\"header-sub\">" + getString(StringKey.MEDIA_BASE, lang) + ": N/A</th></tr>";
			}
		}
		
		String html = "<table>" + 
			"<tr><th class=\"header-sub\">" + getString(StringKey.DICTIONARY_BASE, lang) + ": " + baseInfo.getFormatName() + " (" + getString(StringKey.FORMAT_VERSION, lang) + " " + baseInfo.getFormatVersion() + ")</th></tr>";
		
		String wordsArticlesHeader = getString(StringKey.NUMBER_OF_WORDS_ARTICLES, lang);
		String wordsArticlesNumber = ": <span class=\"dvl\">" + formatNumber(baseInfo.getWordsNumber()) + "</span>";
		if (baseInfo.getArticlesActualNumber() > 0 && baseInfo.getArticlesActualNumber() != baseInfo.getWordsNumber()) {
			wordsArticlesNumber = ": <span class=\"dvl\">" + formatNumber(baseInfo.getWordsNumber()) + " / " + formatNumber(baseInfo.getArticlesActualNumber()) + "</span>";
		}
		
		html += "<tr><td>" + wordsArticlesHeader + wordsArticlesNumber + "</td></tr>";

		if (baseInfo.getAbbreviationsNumber() > 0) {
			String abbrevNumber = getString(StringKey.NUMBER_OF_ABBREVIATIONS, lang) + ": <span class=\"dvl\">" + baseInfo.getAbbreviationsNumber() + "</span>";
			html += "<tr><td>" + abbrevNumber + "</td></tr>";
		}
		
		if (!baseInfo.isMediaBaseSeparate() && baseInfo.getMediaResourcesNumber() > 0) {
			String resNumber = getString(StringKey.NUMBER_OF_RESOURCES, lang) + ": <span class=\"dvl\">" + formatNumber(baseInfo.getMediaResourcesNumber()) + "</span>";
			html += "<tr><td>" + resNumber + "</td></tr>";
		}

		if (baseInfo.getWordsRelationsNumber() > 0 || baseInfo.getWordsMappingsNumber() > 0) {
			String redirectsMappingsNumber = getString(StringKey.NUMBER_OF_MAPPINGS_REDIRECTS, lang) + 
				": <span class=\"dvl\">" + formatNumber(baseInfo.getWordsMappingsNumber()) + " / " + 
				formatNumber(baseInfo.getWordsRelationsNumber()) + "</span>";
			html += "<tr><td>" + redirectsMappingsNumber + "</td></tr>";
		}
		
		html += langDirections +
			artFormat +
			abbrFormat +
			artFormatIWM +
			baseVersion +
			codePage +
			createdBy +
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
				result= "<tr><td>" + title + ": <span class=\"dvl\">" + strRow + "</span></td></tr>";
			}
			
		}
		return result;
	}
	
	private static String formatSize(long size) {
		if (size > 1073741824) {
			return (FORMATTER_SIZE).format((double) size / 1073741824D) + " GB (" +size + " B)";
		} else {
			return (FORMATTER_SIZE).format((double) size / 1048576D) + " MB (" + size + " B)";
		}
	}
	
	private static String formatNumber(long num) {
		return (FORMATTER_NUMBER).format(num);
	}
	
	private static String formatCPString(String cp) {
		if (cp == null || cp.length() < 1) {
			return cp;
		}
		
		if (cp.toLowerCase().startsWith("utf")) {
			return cp.substring(0, 3).toUpperCase() + cp.substring(3);
		} else {
	        return cp.substring(0, 1).toUpperCase() + cp.substring(1).toLowerCase();
		}
	}

}

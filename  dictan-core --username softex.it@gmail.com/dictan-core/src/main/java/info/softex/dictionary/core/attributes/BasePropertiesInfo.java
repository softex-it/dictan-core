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

package info.softex.dictionary.core.attributes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 
 * @since version 2.0, 03/12/2011
 * 
 * @modified version 2.6, 08/27/2011
 * @modified version 2.9, 11/13/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class BasePropertiesInfo implements Cloneable {
	
	public enum PrimaryKeys {
		
		FORMAT_NAME("format.name"),
		FORMAT_VERSION("format.version"),
		
		BASE_VERSION("base.version"),
		BASE_TYPE("base.type"),
		BASE_DATE("base.date"),
		BASE_NAME_SHORT("base.name.short"),
		BASE_NAME_FULL("base.name.full"),
		BASE_PARTS_CURRENT_NUMBER("base.parts.current.number"),
		BASE_PARTS_TOTAL_NUMBER("base.parts.total.number"),
		BASE_PARTS_SIZE_MIN("base.parts.size.min"),
		BASE_SECURITY_PROPERTIES_MD5("base.security.properties.md5"),
		
		ARTICLES_NUMBER("articles.number"),
		ARTICLES_FORMATTING_MODE("articles.formatting.mode"),
		ARTICLES_BLOCKS_SIZE_UNCOMPRESSED_MIN("articles.blocks.size.uncompressed.min"),
		
		ABBREVIATIONS_NUMBER("abbreviations.number"),
		ABBREVIATIONS_FORMATTING_MODE("abbreviations.formatting.mode"),

		MEDIA_RESOURCES_NUMBER("media.resources.number"),
		MEDIA_RESOURCES_BLOCKS_SIZE_UNCOMPRESSED_MIN("media.resources.blocks.size.uncompressed.min"),
		
		INFO_COMPILATION_CREATOR_NAME("info.compilation.creator.name"),
		INFO_COMPILATION_PROGRAM_NAME("info.compilation.program.name"),
		INFO_COMPILATION_PROGRAM_VERSION("info.compilation.program.version"),
		INFO_COMPILATION_DATE("info.compilation.date");
		
		private String key = null;
		
		private PrimaryKeys(String key) {
			this.key = key;
		}
		
		public String getKey() {
			return this.key;
		}
		
		public static PrimaryKeys valueOfKey(String key) {
			PrimaryKeys[] values = PrimaryKeys.values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].getKey().equalsIgnoreCase(key)) {
					return values[i];
				}
			}
			return null;
		}
		
		public static String getBasePartsArticlesBlockIdStartKey(int num) {
			return "base.parts." + num + ".articles.blocks.id.start";
		}
		
		public static String getBasePartsMediaResourcesBlockIdStartKey(int num) {
			return "base.parts." + num + ".media.resources.blocks.id.start";
		}

	}
	
	public enum ArticlesFormattingModes {
		DISABLED, 
		FULL,
		BASIC,
		MEDIA
	}

	public enum AbbreviationsFormattingModes {
		DISABLED,
		FULL,
		BASIC
	}
	
	public enum BaseTypes {
		UNDEFINED,
		DICTIONARY,
		ENCYCLOPEDIA
	}
	
	protected LinkedHashMap<String, Object> primaryParams = new LinkedHashMap<String, Object>();
	
	//private int wordsNumber = 0;
	//private int abbreviationsNumber = 0;
	
	// Media
	private boolean mediaBaseSeparate = false;
	//private int mediaResourcesNumber = 0;
	private String mediaBaseVersion = null;
	private String mediaFormatVersion = null;
	private String mediaFormatName = null;
	
	private long mediaFileSize = 0; // Only if the media base is separate 
	
	private String articlesCodepageName = null;
	private String wordsCodepageName = null;
	
	// Meta base file parameters
	private String baseFilePath;
	private long baseFileSize;	
	
	public String getBaseFilePath() {
		return this.baseFilePath;
	}
	
	public void setBaseFilePath(String dictionaryFilePath) {
		this.baseFilePath = dictionaryFilePath;
	}

	public String getBaseFileName() {
		return getBaseFilePath() == null ? null : new File(getBaseFilePath()).getName();
	}
	
	public String getBaseVersion() {
		return (String)this.primaryParams.get(PrimaryKeys.BASE_VERSION.getKey());
	}

	public void setBaseVersion(int major, int minor) {
		String version = major + "." + minor;
		this.primaryParams.put(PrimaryKeys.BASE_VERSION.getKey(), version);
	}

	public void setBaseDate(Date date) {
		this.primaryParams.put(PrimaryKeys.BASE_DATE.getKey(), dateToString(date));
	}
	
	public void setCompilationDate(Date date) {
		this.primaryParams.put(PrimaryKeys.INFO_COMPILATION_DATE.getKey(), dateToString(date));
	}
	
	public String getCompilationDate() {
		return (String)this.primaryParams.get(PrimaryKeys.INFO_COMPILATION_DATE.getKey());
	}
	
	public String getBaseDate() {
		return (String)this.primaryParams.get(PrimaryKeys.BASE_DATE.getKey());
	}
	
	public int getFormatVersion() {
		return getIntValue(PrimaryKeys.FORMAT_VERSION.getKey());
	}
	
	public void setFormatVersion(int formatVersion) {
		this.primaryParams.put(PrimaryKeys.FORMAT_VERSION.getKey(), formatVersion);
	}

	public String getFormatName() {
		return (String)this.primaryParams.get(PrimaryKeys.FORMAT_NAME.getKey());
	}

	public void setFormatName(String formatName) {
		this.primaryParams.put(PrimaryKeys.FORMAT_NAME.getKey(), formatName);
	}
	
	public String getBaseFullName() {
		return (String)this.primaryParams.get(PrimaryKeys.BASE_NAME_FULL.getKey());
	}
	
	public String getBaseShortName() {
		return (String)this.primaryParams.get(PrimaryKeys.BASE_NAME_SHORT.getKey());
	}

	public String getBaseType() {
		Object baseType = primaryParams.get(PrimaryKeys.BASE_TYPE.getKey());
		if (baseType instanceof String) {
			return BaseTypes.valueOf(baseType.toString()).name();
		}
		return BaseTypes.UNDEFINED.name();
	}

	public void setBaseFullName(String fullName) {
		this.primaryParams.put(PrimaryKeys.BASE_NAME_FULL.getKey(), fullName);
	}
	
	public void setBaseShortName(String shortName) {
		this.primaryParams.put(PrimaryKeys.BASE_NAME_SHORT.getKey(), shortName);
	}

	public void setBaseType(BaseTypes type) {
		this.primaryParams.put(PrimaryKeys.BASE_TYPE.getKey(), type.name());
	}
	
	public void setArticlesFormattingMode(ArticlesFormattingModes mode) {
		this.primaryParams.put(PrimaryKeys.ARTICLES_FORMATTING_MODE.getKey(), mode.name());
	}

	public String getArticlesFormattingMode() {
		Object mode = primaryParams.get(PrimaryKeys.ARTICLES_FORMATTING_MODE.getKey());
		if (mode instanceof String) {
			return ArticlesFormattingModes.valueOf(mode.toString()).name();
		}
		return ArticlesFormattingModes.FULL.name();
	}

	public String getAbbreviationsFormattingMode() {
		Object mode = primaryParams.get(PrimaryKeys.ABBREVIATIONS_FORMATTING_MODE.getKey());
		if (mode instanceof String) {
			return AbbreviationsFormattingModes.valueOf(mode.toString()).name();
		}
		return AbbreviationsFormattingModes.FULL.name();
	}
	
	public void setAbbreviationsFormattingMode(AbbreviationsFormattingModes mode) {
		this.primaryParams.put(PrimaryKeys.ABBREVIATIONS_FORMATTING_MODE.getKey(), mode.name());
	}
	
	public Map<String, Object> getPrimaryParameters() {
		return primaryParams;
	}
	
	public void setBaseFileSize(long size) {
		baseFileSize = size;
	}
	
	public long getBaseFileSize() {
		return baseFileSize;
	}
	
	public int getBasePartsTotalNumber() {
		return getIntValue(PrimaryKeys.BASE_PARTS_TOTAL_NUMBER.getKey());
	}
	
	
	public int getBasePartsArticlesBlockIdStart(int num) {
		return getIntValue(PrimaryKeys.getBasePartsArticlesBlockIdStartKey(num));
	}
	
	public int getBasePartsMediaResourcesBlockIdStart(int num) {
		return getIntValue(PrimaryKeys.getBasePartsMediaResourcesBlockIdStartKey(num));
	}

	
	public void setBasePartsTotalNumber(int number) {
		primaryParams.put(PrimaryKeys.BASE_PARTS_TOTAL_NUMBER.getKey(), number);
	}
	
	public void setArticlesCodepageName(String transCodepageName) {
		this.articlesCodepageName = transCodepageName;
	}
	
	public String getArticleCodepageName() {
		return articlesCodepageName;
	}
	
	public String getWordsCodepageName() {
		return wordsCodepageName;
	}
	
	public void setWordsCodepageName(String wordsCodepageName) {
		this.wordsCodepageName = wordsCodepageName;
	}
	
	public int getArticlesNumber() {
		return getIntValue(PrimaryKeys.ARTICLES_NUMBER.getKey());
	}

	public void setArticlesNumber(int articlesNumber) {
		this.primaryParams.put(PrimaryKeys.ARTICLES_NUMBER.getKey(), articlesNumber);
	}

	public int getAbbreviationsNumber() {
		return getIntValue(PrimaryKeys.ABBREVIATIONS_NUMBER.getKey());
	}

	public void setAbbreviationsNumber(int abbreviationsNumber) {
		this.primaryParams.put(PrimaryKeys.ABBREVIATIONS_NUMBER.getKey(), abbreviationsNumber);
	}
	
//	public Locale getBaseLocale() {
//		Object obj = this.primaryParams.get(PrimaryKeys.BASE_LOCALE);
//		if (obj instanceof Locale) {
//			return (Locale)obj;
//		} else if (obj instanceof String) {
//			return new Locale((String)obj);
//		}
//		return null;
//	}
//
//	public void setBaseLocale(Locale locale) {
//		this.primaryParams.put(PrimaryKeys.BASE_LOCALE, locale);
//	}
	
	public void setCompilationCreatorName(String name) {
		this.primaryParams.put(PrimaryKeys.INFO_COMPILATION_CREATOR_NAME.getKey(), name);
	}
	
	public void setCompilationProgramsName(String name) {
		this.primaryParams.put(PrimaryKeys.INFO_COMPILATION_PROGRAM_NAME.getKey(), name);
	}
	
	public void setCompilationProgramsVersion(String version) {
		this.primaryParams.put(PrimaryKeys.INFO_COMPILATION_PROGRAM_VERSION.getKey(), version);
	}
	
//	public String getBaseDescription() {
//		return (String)this.primaryParams.get(PrimaryKeys.BASE_DESCRIPTION);
//	}
//
//	public void setBaseDescription(String description) {
//		this.primaryParams.put(PrimaryKeys.BASE_DESCRIPTION, description);
//	}
	
	@Override
	public String toString() {
		String prim = this.primaryParams != null ? this.primaryParams.toString() : "";
		if (getArticleCodepageName() != null) {
			return "articleCodePage: " + getArticleCodepageName() + " " + prim;
		}
		return prim;
	}
	
	public int getMediaResourcesNumber() {
		return getIntValue(PrimaryKeys.MEDIA_RESOURCES_NUMBER.getKey());
	}
	
	public void setMediaResourcesNumber(int mediaResourcesNumber) {
		this.primaryParams.put(PrimaryKeys.MEDIA_RESOURCES_NUMBER.getKey(), mediaResourcesNumber);
	}
	
	public String getMediaBaseVersion() {
		return mediaBaseVersion;
	}
	
	public void setMediaBaseVersion(String mediaBaseVersion) {
		this.mediaBaseVersion = mediaBaseVersion;
	}
	
	public String getMediaFormatVersion() {
		return mediaFormatVersion;
	}
	
	public void setMediaFormatVersion(String mediaFormatVersion) {
		this.mediaFormatVersion = mediaFormatVersion;
	}
	
	public String getMediaFormatName() {
		return mediaFormatName;
	}
	
	public void setMediaFormatName(String mediaFormatName) {
		this.mediaFormatName = mediaFormatName;
	}
	
	public long getMediaFileSize() {
		return mediaFileSize;
	}
	
	public void setMediaFileSize(long mediaFileSize) {
		this.mediaFileSize = mediaFileSize;
	}
	
	public boolean isMediaBaseSeparate() {
		return mediaBaseSeparate;
	}
	
	public void setMediaBaseSeparate(boolean mediaBaseSeparate) {
		this.mediaBaseSeparate = mediaBaseSeparate;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BasePropertiesInfo clone() {
		try {
			BasePropertiesInfo info = (BasePropertiesInfo)super.clone();
			info.primaryParams = (LinkedHashMap)this.primaryParams.clone();
			return info;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
//	
//	public void skipNumbers() {
//		setArticlesNumber(0);
//		setMediaResourcesNumber(0);
//		setAbbreviationsNumber(0);
//	}
	
	// Private methods ------------------------------------------------------------

	private int getIntValue(String keyString) {
		Object obj = this.primaryParams.get(keyString);
		if (obj instanceof Integer) {
			return (Integer)obj;
		} else if (obj instanceof String) {
			return Integer.parseInt((String)obj);
		} else if (obj != null) {
			throw new IllegalArgumentException("The key " + keyString + " cannot be cast to int");
		}
		return 0;
	}
	
//	private int getIntValue(PrimaryKeys key) {
//		Object obj = this.primaryParams.get(key);
//		if (obj instanceof Integer) {
//			return (Integer)obj;
//		} else if (obj instanceof String) {
//			return Integer.parseInt((String)obj);
//		} else if (obj != null) {
//			throw new IllegalArgumentException("The key " + key + " cannot be cast to int");
//		}
//		return 0;
//	}
	
//	private boolean getBooleanValue(PrimaryKeys key) {
//		Object obj = this.primaryParams.get(key);
//		if (obj instanceof Boolean) {
//			return (Boolean)obj;
//		} else if (obj instanceof String) {
//			return Boolean.parseBoolean((String)obj);
//		} else if (obj != null) {
//			throw new IllegalArgumentException("The key " + key + " cannot be cast to boolean");
//		}
//		return false;
//	}
	
	private String dateToString(Date date) {
		//2012-03-06T08:00:00,000Z
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS'Z'");
		format.setCalendar(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
		//Date date = format.parse("2003-01-25 00:15:30");
		//System.out.println(date);
		return format.format(date);
	}
 	
}

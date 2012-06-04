/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2011 - 2012  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.formats.zd;

import info.softex.dictionary.core.annotations.DictionaryFormat;
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.AbbreviationsFormattingModes;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.ArticlesFormattingModes;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.LocalizedStringComparator;
import info.softex.dictionary.core.io.BaseFormatException;
import info.softex.dictionary.core.io.BaseReader;
import info.softex.dictionary.core.regional.RegionalResolver;
import info.softex.dictionary.core.utils.ArticleHtmlFormatter;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 1.0, 09/23/2010
 * 
 * @modified version 2.0, 03/06/2011
 * @modified version 2.4, 06/10/2011
 * @modified version 2.5, 08/02/2011
 * @modified version 2.6, 09/02/2011
 * @modified version 2.7, 10/23/2011
 *  
 * @author Dmitry Viktorov
 * 
 */
@DictionaryFormat(name = "ZD", primaryExtension = ".zd", extensions = {".zd"})
public class ZDBaseReader implements BaseReader {
	
	public static final String FORMAT_ZPAK_NAME = "ZPAK";
	public static final String FORMAT_ZPAK_EXT = ".zpak";
	
	private final Logger log = LoggerFactory.getLogger(ZDBaseReader.class.getSimpleName());

	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(ZDBaseReader.class);

	protected BasePropertiesInfo baseInfo = null;
	protected LanguageDirectionsInfo languageDirections = null;
	
	protected RegionalResolver regionalResolver = null;
	
	protected Comparator<String> stringComparator = null;	

	protected ZDDynamicArticlesReader zdReader = null;
	protected ZPAKMappedMediaReader zpakReader = null;

	protected Locale locale = null;
	
	public ZDBaseReader(File zdFile, RegionalResolver regionalResolver) throws IOException {
		
		this.regionalResolver = regionalResolver;
		this.zdReader = new ZDDynamicArticlesReader(regionalResolver, zdFile);
		
		String zdFilePath = stripExtension(zdFile.getPath());
		
		File zpakFile = new File(zdFilePath + FORMAT_ZPAK_EXT);
		if (zpakFile.exists()) {
			log.info("ZPAK file is found: {}", zpakFile);
			this.zpakReader = new ZPAKMappedMediaReader(zpakFile);
		} else {
			this.zpakReader = null;
		}
		
	}

	@Override
	public List<String> getWords() {
		return zdReader.getWords();
	}

	public ArticleInfo getArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		return getArticleInfo(wordInfo, false);
	}
	
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		return getArticleInfo(wordInfo, true);
	}
	
	protected ArticleInfo getArticleInfo(WordInfo wordInfo, boolean isRaw) throws BaseFormatException {
		
		ArticleInfo articleInfo = null;
		
		try {
			if (!wordInfo.hasIndex()) {
				//int index = getWordIndex(wordInfo.getWord());
				int index = searchWordIndex(wordInfo.getWord(), false);
				if (index >= 0) {
					wordInfo.setId(index);
				} else {
					return null;
				}
			}

			articleInfo = new ArticleInfo(wordInfo, getArticle(wordInfo.getId(), isRaw));

		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't retrieve the article: " + e.getMessage());
		}
		
		return articleInfo;
		
	}
	
	protected String getArticle(int index, boolean isRaw) throws Exception {
		String article = zdReader.getArticle(index);
		if (!isRaw) {
			article = ArticleHtmlFormatter.prepareArticle(
					zdReader.getArticle(index), getAbbreviationKeys(), 
					baseInfo.getArticlesFormattingMode(), 
					baseInfo.getAbbreviationsFormattingMode(),
					baseInfo.getMediaResourcesNumber() != 0
				);
		}
		return article;
	}

	@Override
	public void close() throws IOException {
		boolean zdCloseError = false;
		boolean zpakCloseError = false;
		try {
			this.zdReader.close();
		} catch (IOException e) {
			log.error("ZD Close Error", e);
			zdCloseError = true;
		}
		
		if (this.zpakReader != null) { 
			try {
				zpakReader.close();
			} catch (IOException e) {
				zpakCloseError = true;
				log.error("ZPAK Close Error", e);
			}
		}

		if (zdCloseError || zpakCloseError) {
			throw new IOException("Close Error: zd=" + zdCloseError +", zpak=" + zpakCloseError);
		}
	}

	@Override
    public void load() throws BaseFormatException, IOException, ParseException {
    	
	    if (this.baseInfo == null) {
	        log.debug("Loading DictionaryInfo");
	        this.loadBasePropertiesInfo();
	    }
    	
	    zdReader.load();
	    
	    if(zpakReader != null) {
	        zpakReader.load();
	    }
	    
	    if (this.languageDirections == null) {
	    	this.loadLanguageDirectionsInfo();
	    }
		    
	    log.debug("Dictionary loaded: {}", this);
    }
    
    @Override
    public BasePropertiesInfo loadBasePropertiesInfo() throws IOException, BaseFormatException {
		baseInfo = new BasePropertiesInfo();
		
		baseInfo.setBaseFilePath(zdReader.getFilePath());
		baseInfo.setBaseFileSize(new File(zdReader.getFilePath()).length());
		
		baseInfo.setMediaBaseSeparate(true);
		
		ZDHeader zdHeader = zdReader.loadHeader();
		populateBasePropertiesInfoFromZDHeader(this.baseInfo, zdHeader);
		
		// Dictionary name
		String nameNoZD = stripExtension(zdReader.getFileName());
		String dictName = nameNoZD.replaceAll("_", " ");
		this.baseInfo.setBaseShortName(dictName);
		this.baseInfo.setBaseFullName(dictName);
		
		this.locale = this.regionalResolver.getLanguageLocale(zdHeader.getCollateLocaleId());
		this.stringComparator = new LocalizedStringComparator(this.locale);
		
	    if (this.zpakReader != null) {
	        ZPAKHeader zpakHeader = this.zpakReader.loadZPAKHeader();
	        if (zpakHeader != null) {
	        	populateBasePropertiesInfoFromZPAKHeader(this.baseInfo, zpakHeader);
	        } 
	    } 
	    
		return this.baseInfo;
	}

    @Override
	public MediaResourceInfo getMediaResourceInfo(String resourceId) {
		if (zpakReader == null) {
			log.info(".zpak file is not found. Cannot load resource: {}", resourceId);
			return null;
		}
		return new MediaResourceInfo(resourceId, zpakReader.loadResource(resourceId));
	}
	
	@Override
	public Set<String> getMediaResourceKeys() {
		if (this.zpakReader == null) {
			return null;
		}
		return this.zpakReader.getResourceKeys();
	}
	
//	@Override
//	public int getWordIndex(String word) throws BaseFormatException {
//		int index = searchWordIndex(word, false);
//		return index >= 0 ? index : -1;
//	}

	@Override
    public int searchWordIndex(String word, boolean positive) {
        int index = searchWordIndexPE(word);
        if(index < 0 && positive) {
            index = Math.abs(index) - 1;
        } 
        return index;
    }
	
    @Override
	public Set<String> getAbbreviationKeys() {
		return zdReader.getAbbreviationKeys();
	}

	public AbbreviationInfo getAbbreviationInfo(String abbreviation) {
		
		AbbreviationInfo abbreviationInfo = null;

		String definition = zdReader.getAbbreviations().get(abbreviation);
		
		if (definition != null) {
			definition = definition.trim();
			if (definition.length() > 0) {
				abbreviationInfo = new AbbreviationInfo(abbreviation, definition);
			}
		}
		
		return abbreviationInfo;
	}
	
	// Punctuation Equality
	private int searchWordIndexPE(String word) {
		int index = searchWordIndexSE(word);
		if (index >= 0) {
			return index;
		}
		
		if (word.contains(" ") || word.contains("-")) {
			int start = -1 - index;
			int max = Math.min(start + 150, getWords().size());
			int min = Math.max(start - 150, 0);
			
			for (int i = min; i < max; i++) {
				if ((getWords().get(i)).startsWith(word)) {
					if ((getWords().get(i)).equals(word)) {
						return i;
					} else {
						return -1 - i;
					}
				}
			}
		}
		return index;
	}
	
	// Strong Equality
	private int searchWordIndexSE(String word) {
		int index = Collections.binarySearch(getWords(), word, this.stringComparator);
		if (index > 0) {
			int min = index;
			int max = index;
			
			for (; this.stringComparator.compare(word, getWords().get(min)) == 0; min--);
			for (; this.stringComparator.compare(word, getWords().get(max)) == 0; max++);
			min++;
			max--;
			
			if (min == max) {
				return index;
			}
			for (int i = min; i <= max; i++) {
				if (word.equals(getWords().get(i))) {
					return i;
				}
			}
			return min;

		}
		return index;
	}
	
	public boolean isLoaded() {
		return zdReader.isLoaded();
	}
	
	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}
	
	@Override
	public BasePropertiesInfo getBasePropertiesInfo() {
    	return this.baseInfo;
    }
	
	/**
	 * Language directions are not supported. Instead, the collation rules are based on locale. 
	 * toLocale is always null in ZD base, so the direction cannot be resolved.   
	 * 
	 * @throws ParseException 
	 */
	@Override
	public LanguageDirectionsInfo loadLanguageDirectionsInfo() throws BaseFormatException, ParseException {
		this.languageDirections = new LanguageDirectionsInfo();
		
		this.languageDirections.addDirection(
				this.locale.toString(), LanguageDirectionsInfo.UNDEFINED, 
				"", "", Collator.PRIMARY, Collator.CANONICAL_DECOMPOSITION, 0, true
			);
		
		return this.languageDirections;
	}
	
	/**
	 * Language directions are not supported.
	 */
	@Override
	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return this.languageDirections;
	}
	
	private String stripExtension(final String name) {
		for (String ext: FORMAT_INFO.getExtensions()) {
			if (name.toLowerCase().endsWith(ext)) {
				return name.substring(0, name.length() - ext.length());
			}
		}
		return name;
	}
	
    private static void populateBasePropertiesInfoFromZDHeader(BasePropertiesInfo dictInfo, ZDHeader zdHeader) {
    	dictInfo.setArticlesNumber(zdHeader.getWordsNumber());
    	dictInfo.setAbbreviationsNumber(zdHeader.getAbbreviationsNumber());
    	dictInfo.setFormatVersion(zdHeader.getFormatVersion());
    	dictInfo.setBaseFileSize(zdHeader.getDictionaryFileSize());
    	
    	dictInfo.setArticlesCodepageName(zdHeader.getTransCodepageName());
    	dictInfo.setWordsCodepageName(zdHeader.getWordsCodepageName());

    	//dictInfo.setBaseLocale(resolver.getLanguageLocale(zdHeader.getCollateLocaleId()));
		
    	ArticlesFormattingModes artMode = zdHeader.getFlags().isArticleFormatEnabled() ? ArticlesFormattingModes.FULL : ArticlesFormattingModes.DISABLED;
		dictInfo.setArticlesFormattingMode(artMode);
		
		AbbreviationsFormattingModes abbrMode = zdHeader.getAbbreviationsNumber() == 0 ? AbbreviationsFormattingModes.DISABLED : AbbreviationsFormattingModes.FULL;  
    	dictInfo.setAbbreviationsFormattingMode(abbrMode);
		
    	dictInfo.setFormatName(FORMAT_INFO.getName());
    }
    
    private static void populateBasePropertiesInfoFromZPAKHeader(BasePropertiesInfo dictInfo, ZPAKHeader zpakHeader) {
    	dictInfo.setMediaResourcesNumber(zpakHeader.getMediaResourcesNumber());
    	dictInfo.setMediaFormatVersion(String.valueOf(zpakHeader.getMediaFormatVersion()));
    	dictInfo.setMediaFileSize(zpakHeader.getMediaFileSize());
    	dictInfo.setMediaFormatName(FORMAT_ZPAK_NAME);
    }

}

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

package info.softex.dictionary.core.formats.fdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import info.softex.dictionary.core.annotations.BaseFormat;
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.MediaResourceKey;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.AbstractCollatorFactory;
import info.softex.dictionary.core.database.DatabaseConnectionFactory;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.api.BaseReader;
import info.softex.dictionary.core.utils.ArticleHtmlFormatter;

/**
 * FDB (Free Dictionary Base) base reader.
 * 
 * @since version 2.6,      08/21/2011
 * 
 * @modified version 2.9,   11/11/2011
 * @modified version 3.4,   07/04/2012
 * @modified version 3.9,   01/25/2014
 * @modified version 4.0,   02/08/2014
 * @modified version 4.6,   01/28/2015
 * @modified version 4.7,   03/26/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
@BaseFormat(name = "FDB", primaryExtension = ".fdb", extensions = {".fdb", ".fdl"}, sortingExpected = true, likeSearchSupported = true)
public class FDBBaseReader implements BaseReader {
	
	private final static Logger log = LoggerFactory.getLogger(FDBBaseReader.class);

	public final static FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(FDBBaseReader.class);
		
	protected final String mainBaseFilePath;

	protected final FDBBaseReadUnit mainBase;
	protected final TreeMap<Integer, FDBBaseReadUnit> dbs = new TreeMap<Integer, FDBBaseReadUnit>();
	protected ArrayList<BaseRange> dbArticlesRanges;
	protected ArrayList<BaseRange> dbMediaResourcesRanges;
	
	protected final DatabaseConnectionFactory conFactory;
	
	protected final Map<String, String> dbParams;
	
	protected final int wordListBlockSize;
	
	protected boolean hasWordsRelations = false;
	protected boolean hasWordsMappings = false;
	
	public FDBBaseReader(File fdbFile, DatabaseConnectionFactory conFactory, Map<String, ?> inParams, AbstractCollatorFactory collatorFactory) throws SQLException {
		dbParams = new HashMap<String, String>();
		dbParams.put(DatabaseConnectionFactory.DB_NO_LOCALIZED_COLLATORS, "true");
		dbParams.put(DatabaseConnectionFactory.DB_OPEN_READ_ONLY, "true");
		
		int wordListBlockSize = FDBConstants.VALUE_WORD_LIST_BLOCK_SIZE_DEFAULT;
		if (inParams != null) {
			Object wlbSize = inParams.get(FDBConstants.PARAM_KEY_WORD_LIST_BLOCK_SIZE);
			if (wlbSize instanceof Integer) {
				wordListBlockSize = (Integer) wlbSize;
			}
		}

		log.debug("WordList Block Size: {}", wordListBlockSize);
		this.wordListBlockSize = wordListBlockSize;
		this.mainBaseFilePath = fdbFile.getAbsolutePath();
		this.conFactory = conFactory;
		this.mainBase = new FDBBaseReadUnit(true, mainBaseFilePath, conFactory.createConnection(mainBaseFilePath, dbParams), wordListBlockSize, collatorFactory);
		this.dbs.put(1, mainBase);
	}
	
	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}

	@Override
	public ArticleInfo getArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		ArticleInfo articleInfo = getRawArticleInfo(wordInfo);
		if (articleInfo != null) {
			BasePropertiesInfo baseInfo = mainBase.getBasePropertiesInfo();
			String article = ArticleHtmlFormatter.prepareArticle(
				wordInfo.getArticleWord(),
				articleInfo.getArticle(), getAbbreviationKeys(), 
				baseInfo.getArticlesFormattingMode(),
				baseInfo.getArticlesFormattingInjectWordMode(),
				baseInfo.getAbbreviationsFormattingMode(),
				baseInfo.getMediaResourcesNumber() != 0
			);
			articleInfo.setArticle(article);
		}
		return articleInfo;
	}

	@Override
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		if (!wordInfo.hasIndex()) {
			int wordId = searchWordIndex(wordInfo.getWord(), false);
			if (wordId < 0) {
				return null;
			}
			wordInfo.setId(wordId);
		} else if (wordInfo.getWord() == null) {
			wordInfo.setWord(getWords().get(wordInfo.getId()));
		}
		
		if (hasWordsRelations) {
			mainBase.getWordRedirect(wordInfo);
		}
		
		if (hasWordsMappings) {
			mainBase.getWordMapping(wordInfo);
		}
		
		ArticleInfo articleInfo = getBaseForArticle(wordInfo.getId()).getRawArticleInfo(wordInfo);
		return articleInfo;
	}
	
	@Override
	public ArticleInfo getAdaptedArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		return getRawArticleInfo(wordInfo);
	}
		
	@Override
	public void load() throws BaseFormatException {
		
		loadBasePropertiesInfo();
		
		mainBase.load();
		
		BasePropertiesInfo baseProps = mainBase.getBasePropertiesInfo();
		int depPartsNumber = baseProps.getBasePartsTotalNumber() <= 0 ? 0 : baseProps.getBasePartsTotalNumber() - 1;
		
		log.debug("Total dependent parts number: {}", depPartsNumber);
		
		dbArticlesRanges = new ArrayList<BaseRange>(depPartsNumber);
		dbMediaResourcesRanges = new ArrayList<BaseRange>(depPartsNumber);
		for (int i = 2; i < depPartsNumber + 2; i++) {
			
			int startArtId = baseProps.getBasePartsArticlesBlockIdStart(i);
			if (startArtId >= 0) {
				dbArticlesRanges.add(new BaseRange(startArtId, i));
				log.trace("Added articles block id start: {}", startArtId);
			}
			
			int startResId = baseProps.getBasePartsMediaResourcesBlockIdStart(i);
			if (startResId >= 0) {
				dbMediaResourcesRanges.add(new BaseRange(startResId, i));
				log.debug("Added media resources block id start: {}", startResId);				
			}
			
		}
		
	}
	
	@Override
	public BasePropertiesInfo loadBasePropertiesInfo() throws BaseFormatException {
		
		BasePropertiesInfo props = mainBase.loadBasePropertiesInfo();

		long size = props.getBaseFileSize();
		
		Set<Integer> missingParts = new TreeSet<Integer>();
		
		for (int i = 2; i <= props.getBasePartsTotalNumber(); i++) {
			File partFile = new File(mainBaseFilePath + i);
			if (partFile.exists()) {
				log.debug("Part {} is found: {}", i, partFile);
				size += partFile.length();
			} else {
				log.warn("Part {} is not found: {}", i, partFile);
				missingParts.add(i);
			}
		}
		
		if (!missingParts.isEmpty()) {
			String missingPartsString = "";
			String fileName = new File(mainBaseFilePath).getName();
			for (Iterator<Integer> iterator = missingParts.iterator(); iterator.hasNext();) {
				Integer partNum = iterator.next();
				missingPartsString += fileName + partNum;
				if (iterator.hasNext()) {
					missingPartsString += ", ";
				}
			}
			throw new BaseFormatException("Couldn't find the following parts of the base: " + missingPartsString, BaseFormatException.ERROR_CANT_FIND_BASE_PARTS);
		}
		
		// Define the flags of words relations and mappings to speed up requests
		hasWordsRelations = props.getWordsRelationsNumber() > 0 ? true : false;
		hasWordsMappings = props.getWordsMappingsNumber() > 0 ? true : false;
		
		props.setBaseFileSize(size);

		return props;
	}
	
	@Override
	public LanguageDirectionsInfo loadLanguageDirectionsInfo() throws BaseFormatException {
		return mainBase.loadLanguageDirectionsInfo();
	}
	
	@Override
	public List<String> getWords() {
		return mainBase.getWords();
	}
	
	@Override
	public TreeMap<Integer, String> getWordsLike(String rootWord, int limit) throws BaseFormatException {
		return mainBase.getWordsLike(rootWord, limit);
	}
	
	@Override
	public Map<Integer, Integer> getWordsRedirects() throws BaseFormatException {
		return mainBase.getWordsRedirects();
	}
	
	@Override
	public Map<Integer, String> getWordsMappings() throws BaseFormatException {
		return mainBase.getWordsMappings();
	}
	
	@Override
	public Map<Integer, String> getAdaptedWordsMappings() throws BaseFormatException {
		return getWordsMappings();
	}
	
	@Override
	public int searchWordIndex(String word, boolean positive) throws BaseFormatException {
		return mainBase.searchWordIndex(word, positive);
	}
	
	@Override
	public void close() throws Exception {
		for (Integer dbn : dbs.keySet()) {
			dbs.get(dbn).close();
		}
	}
	
	@Override
	public BasePropertiesInfo getBasePropertiesInfo() {
		return mainBase.getBasePropertiesInfo();		
	}
	
	@Override
	public BaseResourceInfo getBaseResourceInfo(String resourceKey) throws BaseFormatException {
		try {
			return mainBase.getBaseResourceInfo(resourceKey);
		} catch (SQLException e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't read BaseResourceInfo: " + e.getMessage());
		}
	}
	
	@Override
	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return mainBase.getLanguageDirectionsInfo();
	}	
	
	@Override
	public AbbreviationInfo getAbbreviationInfo(String abbreviation) {
		return mainBase.getAbbreviationInfo(abbreviation);
	}
	
	@Override
	public Set<String> getAbbreviationKeys() {
		return mainBase.getAbbreviationKeys();
	}
	
	@Override
	public Set<String> getMediaResourceKeys() throws BaseFormatException {
		try {
			return mainBase.getMediaResourceKeys();
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't read media resource keys: " + e.getMessage(), BaseFormatException.ERROR_CANT_LOAD_MEDIA_KEYS);
		}
	}
	
	@Override
	public MediaResourceInfo getMediaResourceInfo(MediaResourceKey mediaKey) throws BaseFormatException {
		if (!mediaKey.hasIndex()) {
			int resourceId = mainBase.searchMediaResourceKeyIndex(mediaKey.getResourceKey());
			if (resourceId < 0) {
				return null;
			}
			mediaKey.setId(resourceId);
		}
		byte[] resourceData = getBaseForMediaResource(mediaKey.getId()).getMediaResource(mediaKey.getId());			
		return new MediaResourceInfo(mediaKey, resourceData);
	}
	
	@Override
	public boolean isLoaded() {
		return mainBase.isLoaded();
	}
	
	// Protected / Private -----------------------------------------
	protected FDBBaseReadUnit getBaseForArticle(int articleId) throws BaseFormatException {
		return getBaseForId(dbArticlesRanges, articleId);
	}
	
	protected FDBBaseReadUnit getBaseForMediaResource(int resId) throws BaseFormatException {
		return getBaseForId(dbMediaResourcesRanges, resId);
	}
	
	protected FDBBaseReadUnit getBaseForId(ArrayList<BaseRange> rangeList, int id) throws BaseFormatException {
		BaseRange range = null;
		for (Iterator<BaseRange> iterator = rangeList.iterator(); iterator.hasNext();) {
			range = iterator.next();
			if (id < range.startId) {
				return getBase(range.baseNum - 1);
			}
		}
		if (range != null) {
			return getBase(range.baseNum);
		}
		return mainBase;
	}
	
	
	protected FDBBaseReadUnit getBase(int baseNumber) throws BaseFormatException {
		log.debug("Base Part requesed: {}", baseNumber);
		FDBBaseReadUnit base = dbs.get(baseNumber);
		if (base == null) {
			try {
				base = new FDBBaseReadUnit(false, mainBaseFilePath + baseNumber, conFactory.createConnection(mainBaseFilePath + baseNumber, dbParams), wordListBlockSize, null);
			} catch (Exception e) {
				throw new BaseFormatException("Couldn't open base " + mainBaseFilePath + baseNumber + ": " + e.getMessage(), BaseFormatException.ERROR_CANT_OPEN_BASE);
			}
			dbs.put(baseNumber, base);
		}
		return base;
	}
	
	protected class BaseRange {
		final int startId;
		final int baseNum;
		BaseRange(int startId, int baseNum) {
			this.startId = startId;
			this.baseNum = baseNum;
		}
	}
	
}

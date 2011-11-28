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

package info.softex.dictionary.core.formats.fdb;

import info.softex.dictionary.core.annotations.DictionaryFormat;
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.CollatorFactory;
import info.softex.dictionary.core.database.DatabaseConnectionFactory;
import info.softex.dictionary.core.io.BaseFormatException;
import info.softex.dictionary.core.io.BaseReader;
import info.softex.dictionary.core.utils.ArticleHtmlFormatter;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.6, 08/21/2011
 * 
 * @modified version 2.9, 11/11/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
@DictionaryFormat(name = "FDB", primaryExtension = ".fdb", extensions = {".fdb"})
public class FDBBaseReader implements BaseReader {
	
	private static final Logger log = LoggerFactory.getLogger(FDBBaseReader.class.getSimpleName());

	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(FDBBaseReader.class);
		
	protected final String mainBaseFilePath;

	protected final FDBBaseReadUnit mainBase;
	protected final TreeMap<Integer, FDBBaseReadUnit> dbs = new TreeMap<Integer, FDBBaseReadUnit>();
	protected ArrayList<BaseRange> dbArticlesRanges;
	protected ArrayList<BaseRange> dbMediaResourcesRanges;
	
	protected final DatabaseConnectionFactory conFactory;
	
	protected final Map<String, String> dbParams;
	
	protected final int wordListBlockSize;
			
	public FDBBaseReader(String filePath, DatabaseConnectionFactory conFactory, Map<String, Object> params, CollatorFactory collatorFactory) throws SQLException {
		this.dbParams = new HashMap<String, String>();
		dbParams.put(DatabaseConnectionFactory.DB_NO_LOCALIZED_COLLATORS, "true");
		dbParams.put(DatabaseConnectionFactory.DB_OPEN_READ_ONLY, "true");
		
		int wordListBlockSize = FDBConstants.VALUE_WORD_LIST_BLOCK_SIZE_DEFAULT;
		if (params != null) {
			Object wlbSize = params.get(FDBConstants.PARAM_KEY_WORD_LIST_BLOCK_SIZE);
			if (wlbSize instanceof Integer) {
				wordListBlockSize = (Integer)wlbSize;
			}
		}

		log.debug("WordList Block Size: {}", wordListBlockSize);
		this.wordListBlockSize = wordListBlockSize;
		this.mainBaseFilePath = filePath;
		this.conFactory = conFactory;
		this.mainBase = new FDBBaseReadUnit(true, filePath, conFactory.createConnection(filePath, dbParams), wordListBlockSize, collatorFactory);
		this.dbs.put(1, mainBase);
	}
	
	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}

	@Override
	public ArticleInfo getArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		return getArticleInfo(wordInfo, false);
	}

	@Override
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		return getArticleInfo(wordInfo, true);
	}
	
	protected ArticleInfo getArticleInfo(WordInfo wordInfo, boolean isRaw) throws BaseFormatException {
		
		if (!wordInfo.hasIndex()) {
			int wordId = searchWordIndex(wordInfo.getWord(), false);
			if (wordId >= 0) {
				wordInfo.setId(wordId);
			} else {
				return null;
			}
		}
		
		ArticleInfo articleInfo = getBaseForArticle(wordInfo.getId()).getRawArticleInfo(wordInfo);
		BasePropertiesInfo baseInfo = mainBase.getBasePropertiesInfo();
		
		if (!isRaw && articleInfo != null) {
			String article = ArticleHtmlFormatter.prepareArticle(
					articleInfo.getArticle(), getAbbreviationKeys(), 
					baseInfo.getArticlesFormattingMode(), 
					baseInfo.getAbbreviationsFormattingMode(),
					baseInfo.getMediaResourcesNumber() != 0
				);
			articleInfo.setArticle(article);
		}
		
		return articleInfo;
		
	}
	
	@Override
	public void load() throws BaseFormatException {
		
		loadBasePropertiesInfo();
		
		mainBase.load();
		
		BasePropertiesInfo baseProps = mainBase.getBasePropertiesInfo();
		int depPartsNumber = baseProps.getBasePartsTotalNumber() == 0 ? 0 : baseProps.getBasePartsTotalNumber() - 1;
		
		log.debug("Total dependent parts number: {}", depPartsNumber);
		
		dbArticlesRanges = new ArrayList<BaseRange>(depPartsNumber);
		dbMediaResourcesRanges = new ArrayList<BaseRange>(depPartsNumber);
		for (int i = 2; i < depPartsNumber + 2; i++) {
			
			int startArtId = baseProps.getBasePartsArticlesBlockIdStart(i);
			if (startArtId > 0) {
				dbArticlesRanges.add(new BaseRange(startArtId, i));
				log.debug("Added articles block id start: {}", startArtId);				
			}
			
			int startResId = baseProps.getBasePartsMediaResourcesBlockIdStart(i);
			if (startResId > 0) {
				dbMediaResourcesRanges.add(new BaseRange(startResId, i));
				log.debug("Added media resources block id start: {}", startResId);				
			}
			
		}
		
	}
	
	@Override
	public BasePropertiesInfo loadBasePropertiesInfo() throws BaseFormatException {
		BasePropertiesInfo props = mainBase.loadBasePropertiesInfo();
		try {
			long size = props.getBaseFileSize();
			for (int i = 0; i <= props.getBasePartsTotalNumber(); i++) {
				size += new File(mainBaseFilePath + i).length();
			}
			props.setBaseFileSize(size);
			System.out.println("rrrrrrrrrrrrrrrrrrr " + size);
		} catch (Exception e) {
			log.error("Error", e);
		}
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
	public int searchWordIndex(String word, boolean positive) throws BaseFormatException {
		return mainBase.searchWordIndex(word, positive);
	}
	
	@Override
	public void close() throws Exception {
		for (int i = 0; i < dbs.size(); i++) {
			dbs.get(i).close();
		}
	}
	
	@Override
	public BasePropertiesInfo getBasePropertiesInfo() {
		return mainBase.getBasePropertiesInfo();		
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
	public Set<String> getMediaResourceKeys() {
		return mainBase.getMediaResourceKeys();
	}
	
	@Override
	public MediaResourceInfo getMediaResourceInfo(String resourceKey) throws BaseFormatException {
		int resourceId = mainBase.searchMediaResourceKeyIndex(resourceKey);
		if (resourceId < 0) {
			return null;
		}
		byte[] resourceData = getBaseForMediaResource(resourceId).getMediaResource(resourceId);
		
		return new MediaResourceInfo(resourceKey, resourceData);
	}
	
	@Override
	public boolean isLoaded() {
		return mainBase.isLoaded();
	}
	
	// Protected / Private -----------------------------------------
	protected FDBBaseReadUnit getBaseForArticle(int articleId) throws BaseFormatException {
		return getBaseForId(dbArticlesRanges, articleId);
//		BaseRange range = null;
//		for (Iterator<BaseRange> iterator = dbArticlesRanges.iterator(); iterator.hasNext();) {
//			range = iterator.next();
//			if (articleId < range.startId) {
//				return getBase(range.baseNum - 1);
//			}
//		}
//		if (range != null) {
//			return getBase(range.baseNum);
//		}
//		return mainBase;
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
				throw new BaseFormatException("Couldn't open base  " + mainBaseFilePath + baseNumber + ": " + e.getMessage());
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

/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2018  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observer;

import info.softex.dictionary.core.annotations.BaseFormat;
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.PrimaryKey;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.ProgressInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.database.DatabaseConnectionFactory;
import info.softex.dictionary.core.formats.api.BaseWriter;

/**
 * 
 * @since       version 2.6, 08/21/2011
 * 
 * @modified    version 2.9, 11/19/2011
 * @modified    version 3.4, 07/02/2012
 * @modified    version 4.5, 03/29/2014
 * @modified    version 4.6, 02/01/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
@BaseFormat(name = "FDB", primaryExtension = ".fdb", extensions = {".fdb", ".fdl"}, sortingExpected = true, likeSearchSupported = true)
public class FDBBaseWriter implements BaseWriter {

    public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(FDBBaseWriter.class);

    private static final Logger log = LoggerFactory.getLogger(FDBBaseWriter.class);

	protected final ProgressInfo progressInfo = new ProgressInfo();
	
	protected final ArrayList<FDBBaseWriteUnit> dbs = new ArrayList<>(1);
	protected final FDBBaseWriteUnit mainBase;
	protected FDBBaseWriteUnit activeBase;
	
	protected final String mainBaseFilePath;
	
	protected long minMainBaseSize = 0;
	protected long minSecondaryBaseSize = 0;
	
	protected final DatabaseConnectionFactory conFactory;
	
	protected final int minArticleBlockMemSize = 200000;
	protected final int minMediaResourceBlockMemSize = 200000;

	protected int abbreviationsNumber = 0;
	protected int wordsNumber = 0;
	protected int wordsMappingsNumber = 0;
	protected int wordsRelationsNumber = 0;
	protected int mediaResourcesNumber = 0;
	
	protected int articlesActualNumber = 0;
	
	protected int lastPartWordsNumber = 0;
	protected int lastPartMediaResourcesNumber = 0;
	 
	protected int curWordsNumber = 0;
	protected int curMediaResourcesNumber = 0;
	
	protected boolean isClosed = false;
	
	public FDBBaseWriter(String inBaseFilePath, DatabaseConnectionFactory inConFactory, Map<String, String> params) throws SQLException, IOException {
		if (params != null) {
			
			// Secondary base size
			String secBaseSizeLimit = params.get(FDBConstants.PARAM_KEY_BASE_SECONDARY_SIZE_LIMIT);
			if (secBaseSizeLimit != null) {
				if (FDBConstants.PARAM_VALUE_DEFAULT.equalsIgnoreCase(secBaseSizeLimit)) {
					//minSecondaryBaseSize = 3800000000L; // 4 294 967 295
					//minSecondaryBaseSize = 20000000;
					//minSecondaryBaseSize = 1073741824L; // 1 GB
					minSecondaryBaseSize = 1610612736L; // 1610612736L - 1.5 GB
				} else {
					minSecondaryBaseSize = parseLongNoException(secBaseSizeLimit);
				}
			}
			
			// Main base size
			if (minSecondaryBaseSize > 0) {
				String mainBaseSizeLimit = params.get(FDBConstants.PARAM_KEY_BASE_MAIN_SIZE_LIMIT);
				if (mainBaseSizeLimit != null) {
					minMainBaseSize = parseLongNoException(mainBaseSizeLimit);
				} 
				if (minMainBaseSize <= 0) {
					minMainBaseSize = minSecondaryBaseSize;
				}
			}
			
		}
		log.info("FDB minimum main/secondary base sizes: {} / {}", minMainBaseSize, minSecondaryBaseSize);
		
		// Create the parent directory for the base file
		File mainBaseDirPath = new File(inBaseFilePath).getParentFile();
		if (mainBaseDirPath != null && !mainBaseDirPath.exists()) {
			mainBaseDirPath.mkdirs();
		}
		
		this.mainBaseFilePath = inBaseFilePath;
		this.conFactory = inConFactory;
		this.mainBase = this.activeBase = new FDBBaseWriteUnit(1, inBaseFilePath, inConFactory.createConnection(inBaseFilePath, null));
		this.dbs.add(mainBase);
	}
	
	@Override
	public BasePropertiesInfo saveBasePropertiesInfo(BasePropertiesInfo baseInfo) throws SQLException, NoSuchAlgorithmException {
		progressInfo.setTotal(baseInfo.getWmraNumber());
		return mainBase.saveBasePropertiesInfo(baseInfo, FORMAT_INFO);
	}
	
	@Override
	public BasePropertiesInfo getBasePropertiesInfo() {
		return mainBase.getBasePropertiesInfo();
	}

	@Override
	public void createBase(String... params) throws Exception {
		mainBase.createBase(params);
	}
	
	@Override
	public void saveRawArticleInfo(ArticleInfo articleInfo) throws Exception {
		if (wordsNumber == 0) {
			saveArticlesBlockIdStart4BaseIndex(activeBase.getBaseIndex());
		}
		
		WordInfo wordInfo = articleInfo.getWordInfo();
		mainBase.saveWord(wordInfo.getWord().trim(), wordsNumber);
		
		boolean isActualArticle = true;
		
		// Save redirect only if it's 0 or positive
		if (wordInfo.getRedirectToId() >= 0) {
			mainBase.saveRelation(wordsRelationsNumber, wordsNumber, wordInfo.getRedirectToId(), FDBConstants.RELATION_REDIRECT_NORMAL);
			wordsRelationsNumber++;
			isActualArticle = false;
		}
		
		// Save redirect only if it's not null
		if (wordInfo.getWordMapping() != null) {
			mainBase.saveWordMapping(wordsNumber, wordInfo.getWordMapping(), null);
			wordsMappingsNumber++;
		}

		if (isActualArticle) {
			articlesActualNumber++;
		}
		
		boolean isFlashed = activeBase.saveArticle(articleInfo.getArticle().trim(), wordsNumber++);
		
		updateProgress();
		
		if (isFlashed) {
			flushArticles();
			reviseBaseFiles();
		}
	}
	
	@Override
	public void saveAdaptedArticleInfo(ArticleInfo articleInfo) throws Exception {
		saveRawArticleInfo(articleInfo);
	}
	
	@Override
	public void saveMediaResourceInfo(MediaResourceInfo mediaResourceInfo) throws Exception {
		if (mediaResourcesNumber == 0) {
			saveMediaResourcesBlockIdStart4BaseIndex(activeBase.getBaseIndex());
		}
		mainBase.saveMediaResourceKey(mediaResourceInfo.getKey().getResourceKey(), mediaResourcesNumber);
		boolean isFlashed = activeBase.saveMediaResource(mediaResourceInfo.getByteArray(), mediaResourcesNumber++);
		updateProgress();
		if (isFlashed) {
			flushMediaResources();
			reviseBaseFiles();
		}
	}
	
	@Override
	public void saveBaseResourceInfo(BaseResourceInfo baseResourceInfo) throws Exception {
		mainBase.saveBaseResourceInfo(baseResourceInfo);
	}
	
	@Override
	public void saveAbbreviationInfo(AbbreviationInfo abbreviationInfo) throws Exception {
		mainBase.saveAbbreviation(abbreviationInfo);
		abbreviationsNumber++;
		updateProgress();
	}
	
	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}
	
	@Override
	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return mainBase.getLanguageDirectionsInfo();
	}

	/**
	 * Flush everything and close the writer
	 */
	@Override
	public void close() throws Exception {
		if (!isClosed) {
			flush();
			for (int i = 0; i < dbs.size(); i++) {
				dbs.get(i).close();
			}
		}
		isClosed = true;
	}
	
	@Override
	public LanguageDirectionsInfo saveLanguageDirectionsInfo(LanguageDirectionsInfo languageDirectionsInfo) throws Exception {
		return mainBase.saveLanguageDirectionsInfo(languageDirectionsInfo);
	}
	
	@Override
	public void addObserver(Observer observer) {
		progressInfo.addObserver(observer);
	}

    @Override
    public void flush() throws Exception {
        if (!isClosed) {
            flushArticles();
            flushMediaResources();
        }
    }

    // Protected -----------------------------------------------
		
	protected void reviseBaseFiles() throws IOException, SQLException, NoSuchAlgorithmException {
		
		if (minSecondaryBaseSize == 0) {
			return;
		}
		
		long activeBaseFileLength = activeBase.getBaseFile().length();
		long currentMinBaseSize = (mainBase == activeBase) ? minMainBaseSize : minSecondaryBaseSize;
		
		if (activeBaseFileLength >= currentMinBaseSize) {
			
			log.info("Revising base files: min base size {}, current base size {}", currentMinBaseSize, activeBaseFileLength);
			
			int newBaseIndex = dbs.size() + 1;
			String newFilePath = mainBaseFilePath + newBaseIndex;
			File baseFile = new File(newFilePath);
			if (baseFile.exists()) {
				baseFile.delete();
			}
			baseFile.createNewFile();
			
			activeBase = new FDBBaseWriteUnit(newBaseIndex, newFilePath, conFactory.createConnection(newFilePath, null)); 
			activeBase.createBase();
			activeBase.saveBasePropertiesInfo(mainBase.getBasePropertiesInfo(), FORMAT_INFO);
			dbs.add(activeBase);
			
			if (newBaseIndex > 2) {
				mainBase.updateBaseProperty(
					BasePropertiesInfo.PrimaryKey.BASE_PARTS_TOTAL_NUMBER.getKey(), 
					Integer.toString(newBaseIndex)
				);
			} else {
				getBasePropertiesInfo().getPrimaryParameters().put(PrimaryKey.BASE_PARTS_MAIN_SIZE_MIN.getKey(), minMainBaseSize);
				getBasePropertiesInfo().getPrimaryParameters().put(PrimaryKey.BASE_PARTS_SECONDARY_SIZE_MIN.getKey(), minSecondaryBaseSize);
				mainBase.insertBaseProperty(PrimaryKey.BASE_PARTS_MAIN_SIZE_MIN.getKey(), Long.toString(minMainBaseSize));
				mainBase.insertBaseProperty(PrimaryKey.BASE_PARTS_SECONDARY_SIZE_MIN.getKey(), Long.toString(minSecondaryBaseSize));
				mainBase.insertBaseProperty(BasePropertiesInfo.PrimaryKey.BASE_PARTS_CURRENT_NUMBER.getKey(), "1");
				mainBase.insertBaseProperty(
					BasePropertiesInfo.PrimaryKey.BASE_PARTS_TOTAL_NUMBER.getKey(), 
					Integer.toString(newBaseIndex)
				);
			}
			
			if (lastPartWordsNumber < wordsNumber) {
				saveArticlesBlockIdStart4BaseIndex(newBaseIndex);
			}

			if (lastPartMediaResourcesNumber < mediaResourcesNumber) {
				saveMediaResourcesBlockIdStart4BaseIndex(newBaseIndex);
			}			
			
			lastPartWordsNumber = wordsNumber;
			lastPartMediaResourcesNumber = mediaResourcesNumber;
			
		}
		
	}

	protected void saveArticlesBlockIdStart4BaseIndex(int baseIndex) throws IOException, SQLException, NoSuchAlgorithmException {
		mainBase.insertBaseProperty(
			BasePropertiesInfo.PrimaryKey.getBasePartsArticlesBlockIdStartKey(baseIndex),
			Integer.toString(wordsNumber)
		);
	}
	
	protected void saveMediaResourcesBlockIdStart4BaseIndex(int baseIndex) throws IOException, SQLException, NoSuchAlgorithmException {
		mainBase.insertBaseProperty(
			BasePropertiesInfo.PrimaryKey.getBasePartsMediaResourcesBlockIdStartKey(baseIndex),
			Integer.toString(mediaResourcesNumber)
		);
	}
	
	protected void updateProgress() {
		int current = abbreviationsNumber + wordsNumber + mediaResourcesNumber;
		progressInfo.setCurrent(current);
		progressInfo.notifyObservers();
	}
	
	@SuppressWarnings("deprecation")
	protected void flushArticles() throws UnsupportedEncodingException, IOException, SQLException {
		mainBase.flushArticles(curWordsNumber);
		if (activeBase != mainBase) {
			activeBase.flushArticles(curWordsNumber);
		}
		// Only for partial compatibility with old viewers
		mainBase.updateBaseProperty(BasePropertiesInfo.PrimaryKey.ARTICLES_NUMBER.getKey(), Integer.toString(wordsNumber));
		mainBase.updateBaseProperty(BasePropertiesInfo.PrimaryKey.WORDS_NUMBER.getKey(), Integer.toString(wordsNumber));
		mainBase.updateBaseProperty(BasePropertiesInfo.PrimaryKey.WORDS_MAPPINGS_NUMBER.getKey(), Integer.toString(wordsMappingsNumber));
		mainBase.updateBaseProperty(BasePropertiesInfo.PrimaryKey.WORDS_RELATIONS_NUMBER.getKey(), Integer.toString(wordsRelationsNumber));
		mainBase.updateBaseProperty(BasePropertiesInfo.PrimaryKey.ARTICLES_ACTUAL_NUMBER.getKey(), Integer.toString(articlesActualNumber));
		curWordsNumber = wordsNumber;
	}
	
	protected void flushMediaResources() throws UnsupportedEncodingException, IOException, SQLException {
		mainBase.flushMediaResources(curMediaResourcesNumber);
		if (activeBase != mainBase) {
			activeBase.flushMediaResources(curMediaResourcesNumber);
		}
		mainBase.updateBaseProperty(BasePropertiesInfo.PrimaryKey.MEDIA_RESOURCES_NUMBER.getKey(), Integer.toString(mediaResourcesNumber));
		curMediaResourcesNumber = mediaResourcesNumber;
	}
	
	protected long parseLongNoException(String inString) {
		try {
			return Long.parseLong(inString);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return 0;
	}


}

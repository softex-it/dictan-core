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

import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.CollatorFactory;
import info.softex.dictionary.core.formats.fdb.collections.FDBDynamicListSet;
import info.softex.dictionary.core.io.BaseFormatException;
import info.softex.dictionary.core.zip.SmartInflaterInputStream;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.9, 11/11/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FDBBaseReadUnit {
	
	protected static final String ENC_UTF8 = "UTF-8";

	protected BasePropertiesInfo baseInfo = null;
	protected LanguageDirectionsInfo langDirections = null;
	
	protected final String baseFilePath;
	
	protected final Connection connection;
	
	protected List<String> words = null;
	
	protected Map<String, String> abbreviations = null;
	
	protected Collator collator = null; 
	
	protected final int wordListBlockSize;
	
	protected PreparedStatement selWordIdByWordSt;
	protected PreparedStatement selMediaResourceIdByKey;
	protected PreparedStatement selMediaResourceById; 
	
	protected final CollatorFactory collatorFactory;
	
	protected final boolean main;
	protected boolean loaded;
	
	public FDBBaseReadUnit(boolean main, String baseFilePath, Connection connection, int wordListBlockSize, CollatorFactory collatorFactory) {
		this.main = main;
		this.baseFilePath = baseFilePath;
		this.connection = connection;
		this.wordListBlockSize = wordListBlockSize;
		this.collatorFactory = collatorFactory;
	}
	
	private static final Logger log = LoggerFactory.getLogger(FDBBaseReadUnit.class.getSimpleName());
	
	public BasePropertiesInfo loadBasePropertiesInfo() throws BaseFormatException {
		
		baseInfo = new BasePropertiesInfo();
		Map<String, Object> dictMap = baseInfo.getPrimaryParameters();
		
		baseInfo.setBaseFilePath(baseFilePath);
		baseInfo.setBaseFileSize(new File(baseFilePath).length());
		baseInfo.setMediaBaseSeparate(false);
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(FDBSQLReadStatements.SELECT_ALL_BASE_PROPERTIES);
			
			while (rs.next()) {
				log.info(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
				String key = rs.getString(2);
				if (key != null) {
					dictMap.put(key, rs.getString(3));
				} else {
					log.warn("Key not found: {}", rs.getString(2));
				}
			}
			
			if (baseInfo.getMediaResourcesNumber() > 0) {
				baseInfo.setMediaFormatName(baseInfo.getFormatName());
				baseInfo.setMediaFormatVersion(Integer.toString(baseInfo.getFormatVersion()));
				baseInfo.setMediaFileSize(baseInfo.getBaseFileSize());
			}
			
			rs.close();
			
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't load BasePropertiesInfo: " + e.getMessage());
		}
				
		log.info("Dictionary Info: {}", baseInfo);
		
		return baseInfo;
	}

	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return langDirections;
	}
	
	// TODO: ADD
	public Set<String> getMediaResourceKeys() {
		return null;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public LanguageDirectionsInfo loadLanguageDirectionsInfo() throws BaseFormatException {
		langDirections = new LanguageDirectionsInfo();
		
		try {
			Statement statement = connection.createStatement();

			ResultSet dcrRS = statement.executeQuery(FDBSQLReadStatements.SELECT_BASE_RESOURCE_DEFAULT_COLLATION_RULES);
			if (dcrRS.next()) {
				this.langDirections.setDefaultCollationProperties(
						new String (dcrRS.getBytes(3), ENC_UTF8), 
						new String (dcrRS.getBytes(4), ENC_UTF8), 
						Integer.parseInt(dcrRS.getString(7))
					);
				
			}
			dcrRS.close();
			
			ResultSet lcrRS = statement.executeQuery(FDBSQLReadStatements.SELECT_BASE_RESOURCE_LOCALE_COLLATION_RULES);
			while (lcrRS.next()) {
				langDirections.addDirection(
						lcrRS.getString(1), lcrRS.getString(2),
						new String(lcrRS.getBytes(3), ENC_UTF8), 
						new String(lcrRS.getBytes(4), ENC_UTF8),
						lcrRS.getString(8), lcrRS.getString(9),
						Integer.parseInt(lcrRS.getString(7)), Boolean.parseBoolean(lcrRS.getString(10))
					);
			} 
			
			lcrRS.close();

			log.debug("Loaded Language Directions: {}", this.langDirections);
			
			//System.out.println(languageDirections.getDefaultCollationProperties().getAdditionalCollationRules());
//			System.out.println(langDirections.getLanguageDirections().get(new Locale("ru")));
//			System.out.println(langDirections.getCombinedCollationRules().equals(
//					langDirections.getDefaultCollationProperties().getBasicCollationRules() + 
//					langDirections.getDefaultCollationProperties().getAdditionalCollationRules() +
//					langDirections.getLanguageDirections().get(new Locale("ru")).get(0).getBasicCollationRules()
//			));
//			System.out.println(langDirections.getLanguageDirections());
			
			this.collator = collatorFactory.createCollator(this.langDirections.getCombinedCollationRules(), null, null);
			
		} catch (SQLException e) {
			log.error("SQL Error", e);
			throw new BaseFormatException("Couldn't load the LanguageDirectionsInfo: " + e.getMessage());
		} catch (ParseException e) {
			log.error("Parse Error", e);
			throw new BaseFormatException("Couldn't parse language data: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Unexpected error: " + e.getMessage());
		}
		
		return this.langDirections;
	}
	
	public void load() throws BaseFormatException {
		   
    	try {
    	
    		// Init Prepared Statements
    		this.selWordIdByWordSt = connection.prepareStatement(FDBSQLReadStatements.SELECT_WORD_ID_BY_WORD);
    		this.selMediaResourceIdByKey = connection.prepareStatement(FDBSQLReadStatements.SELECT_MEDIA_RESOURCE_ID_BY_MEDIA_RESOURCE_KEY);
    		this.selMediaResourceById = connection.prepareStatement(FDBSQLReadStatements.SELECT_MEDIA_RESOURCE_BY_MEDIA_RESOURCE_ID);
    		
    		long s1 = System.currentTimeMillis();
    		
			if (baseInfo == null) {
		        log.debug("Loading BasePropertiesInfo");
		        loadBasePropertiesInfo();
		    }
			
			long s2 = System.currentTimeMillis();
			log.debug("BasePropertiesInfo loaded, time: {}", s2 - s1);
			
			if (langDirections == null) {
		        log.debug("Loading LanguageDirectionsInfo");
		        loadLanguageDirectionsInfo();
		    }
			
			long s3 = System.currentTimeMillis();
			log.debug("LanguageDirectionsInfo loaded, time: {}", s3 - s2); 
	
		    words = new FDBDynamicListSet(
		    		baseInfo.getArticlesNumber(), 
		    		wordListBlockSize,
		    		connection
		    	);
		    
			long s4 = System.currentTimeMillis();
			log.debug("Word list created, time: {}", s4 - s3); 
		    
		    if (abbreviations == null) {
		    	log.debug("Loading Abbreviations");
		    	loadAbbreviations();
    		}
		    
			long s5 = System.currentTimeMillis();
			log.debug("Abbreviations loaded, time: {}", s5 - s4); 

    	} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't load the dictionary: " + e.getMessage());
		}
    	
    	loaded = true;
		
	}
	
	protected void loadAbbreviations() throws BaseFormatException {
		try {
			Statement st = connection.createStatement();
		
			ResultSet rs = st.executeQuery(FDBSQLReadStatements.SELECT_ABBREVIATIONS);
			
			HashMap<String, String> abbs = new HashMap<String, String>();
			while (rs.next()) {
				abbs.put(rs.getString(2), rs.getString(3));
			}
			rs.close();
			
			this.abbreviations = Collections.unmodifiableMap(abbs);
			
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't load abbreviatins: " + e.getMessage());
		}
		
	}
	
	/**
	 * FDB admits only lower case resource keys.
	 * 
	 * @param resourceKey
	 * @return -1 if the key is not found, index otherwise
	 * @throws BaseFormatException 
	 */
	public int searchMediaResourceKeyIndex(String resourceKey) throws BaseFormatException {
		try {
			resourceKey = resourceKey.toLowerCase();
			
			selMediaResourceIdByKey.setString(1, resourceKey);
			ResultSet resIdRS = selMediaResourceIdByKey.executeQuery();
			
			int resourceId = -1;
			if (resIdRS.next()) {
				resourceId = resIdRS.getInt(1);
			} else {
				return -1;
			}
			resIdRS.close();
			
			return resourceId;
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Media resource " + resourceKey + " couldn't be loaded: " + e.getMessage());
		}
	}
	
	public byte[] getMediaResource(int resourceId) throws BaseFormatException {
		try {

			byte[] resourceData = null;
			
			selMediaResourceById.setInt(1, resourceId);
			ResultSet resRS = selMediaResourceById.executeQuery();
			
			if (resRS.next()) {
				
				log.debug("Retrieving article: media_resource_id: {}, media_resource_block_id: {}", resourceId, resRS.getInt(1));
				int segmentNumber = resourceId - resRS.getInt(1);
				
				resourceData = readSegmentBytesFromStream(
						new SmartInflaterInputStream(new ByteArrayInputStream(resRS.getBytes(2))), segmentNumber
					);
				
			} else {
				resRS.close();
				throw new BaseFormatException("Data for media resourceId " + resourceId + " doesn't exist, possible base corruption");
			}
			resRS.close();

			return resourceData;

		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Media resource " + resourceId + " couldn't be loaded: " + e.getMessage());
		}
		
	}
	
	public AbbreviationInfo getAbbreviationInfo(String abbreviation) {
		AbbreviationInfo abbreviationInfo = null;

		String definition = abbreviations.get(abbreviation);
		
		if (definition != null && definition.length() > 0) {
			abbreviationInfo = new AbbreviationInfo(abbreviation, definition);
		}
		
		return abbreviationInfo;
	}
	
	public List<String> getWords() {
		return words;
	}
	
	public BasePropertiesInfo getBasePropertiesInfo() {
		return baseInfo;		
	}
	
	public int searchWordIndex(String word, boolean positive) throws BaseFormatException {
		int index = -1;
		try {
			index = Collections.binarySearch(getWords(), word, this.collator);
			if (index < 0 && positive) {
				index = Math.abs(index) - 1;
			}
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't find the word index: " + e.getMessage());
		}
		return index;
	}
	
	protected File getBaseFile() {
		return new File(baseFilePath);
	}
	
	//------------------------------------------
	
	protected byte[] readSegmentBytesFromStream(SmartInflaterInputStream is, int segmentNumber) throws IOException {

		long start = System.currentTimeMillis();
		
		byte[] readData = null;

		int blockSize = is.readInt();
		int segmentStart = 0;
		for (int i = 0; i < segmentNumber; i++) {
			segmentStart += is.readInt();
		}
		int segmentLength = is.readInt();
		
		log.trace("Start {}, Length {}, SN: " + segmentNumber + ", BNS: " + blockSize, segmentStart, segmentLength);
		
		is.skip(4 * (blockSize - segmentNumber - 1)); 
		is.skip(segmentStart);
		
		readData = new byte[segmentLength];
		is.read(readData);
		is.close();
		
		log.debug("Retrieve Segment Time: {}", (System.currentTimeMillis() - start));
			
		return readData;
	}
	
	/**
	 * 
	 * @param wordInfo - must always have id
	 * @param isRaw
	 * @return
	 * @throws BaseFormatException
	 */
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		
		ArticleInfo articleInfo = null;
		
		if (!wordInfo.hasIndex()) {
			throw new IllegalArgumentException("WordInfo must have an index to get an article");
		}
		
		try {
			Statement st = connection.createStatement();
			ResultSet rs = null;
						
			rs = st.executeQuery(
				"SELECT article_block_id, article_block FROM " + FDBTables.article_blocks + 
				" WHERE article_block_id=(SELECT MAX(article_block_id) FROM " + 
				FDBTables.article_blocks + " WHERE article_block_id<=" + wordInfo.getId() + ")");

			if (rs.next()) {
				
				int blockId = rs.getInt(1);
				
				log.debug("Retrieving article: word_id: {}, article_block_id: {}", wordInfo.getId(), blockId);
				int segmentNumber = wordInfo.getId() - blockId;
				
				byte[] decompBytes = readSegmentBytesFromStream(
						new SmartInflaterInputStream(new ByteArrayInputStream(rs.getBytes(2))), segmentNumber
					);
				
				articleInfo = new ArticleInfo(wordInfo, new String(decompBytes, ENC_UTF8));
				
			} else {
				throw new BaseFormatException("Article for the word " + wordInfo.getWord() + " doesn't exist, possible base corruption");
			}
			
			rs.close();

			log.debug("Article: {}", articleInfo);

		} catch (BaseFormatException e) {
			throw e;
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't retrieve the article: " + e.getMessage());
		}
		
//		if (!isRaw && articleInfo != null) {
//			String article = ArticleHtmlFormatter.prepareArticle(
//					articleInfo.getArticle(), getAbbreviationKeys(), 
//					baseInfo.getArticlesFormattingMode(), 
//					baseInfo.getAbbreviationsFormattingMode(),
//					baseInfo.getMediaResourcesNumber() != 0
//				);
//			articleInfo.setArticle(article);
//		}
		
		return articleInfo;
		
	}
	
//	/**
//	 * 
//	 * @param wordInfo - must always have index
//	 * @param isRaw
//	 * @return
//	 * @throws BaseFormatException
//	 */
//	public ArticleInfo getArticleInfo(WordInfo wordInfo, boolean isRaw) throws BaseFormatException {
//		
//		ArticleInfo articleInfo = null;
//		
//		try {
//			Statement st = connection.createStatement();
//			ResultSet rs = null;
//			
////			if (!wordInfo.hasIndex()) {
////				//int wordId = getWordIndex(wordInfo.getWord());
////				int wordId = searchWordIndex(wordInfo.getWord(), false);
////				if (wordId >= 0) {
////					wordInfo.setId(wordId);
////				} else {
////					return null;
////				}
////			}
//				
//			rs = st.executeQuery(
//				"SELECT article_block_id, article_block FROM " + FDBTables.article_blocks + 
//				" WHERE article_block_id=(SELECT MAX(article_block_id) FROM " + 
//				FDBTables.article_blocks + " WHERE article_block_id<=" + wordInfo.getId() + ")");
//
//			if (rs.next()) {
//				
//				int blockId = rs.getInt(1);
//				
//				log.debug("Retrieving article: word_id: {}, article_block_id: {}", wordInfo.getId(), blockId);
//				int segmentNumber = wordInfo.getId() - blockId;
//				
//				byte[] decompBytes = readSegmentBytesFromStream(
//						new SmartInflaterInputStream(new ByteArrayInputStream(rs.getBytes(2))), segmentNumber
//					);
//				
//				articleInfo = new ArticleInfo(wordInfo, new String(decompBytes, ENC_UTF8));
//				
//			} else {
//				throw new BaseFormatException("Article for the word " + wordInfo.getWord() + " doesn't exist, possible base corruption");
//			}
//			
//			rs.close();
//
//			log.debug("Article: {}", articleInfo);
//
//		} catch (BaseFormatException e) {
//			throw e;
//		} catch (Exception e) {
//			log.error("Error", e);
//			throw new BaseFormatException("Couldn't retrieve the article: " + e.getMessage());
//		}
//		
//		if (!isRaw && articleInfo != null) {
//			String article = ArticleHtmlFormatter.prepareArticle(
//					articleInfo.getArticle(), getAbbreviationKeys(), 
//					baseInfo.getArticlesFormattingMode(), 
//					baseInfo.getAbbreviationsFormattingMode(),
//					baseInfo.getMediaResourcesNumber() != 0
//				);
//			articleInfo.setArticle(article);
//		}
//		
//		return articleInfo;
//		
//	}
	
	public Set<String> getAbbreviationKeys() {
		return abbreviations == null ? null : abbreviations.keySet();
	}
	
	public void close() throws Exception {
		connection.close();
	}
	
	// Protected methods ----------------------------------------------------------
	
//	protected static void populateItemsNumbers(Statement statement, BasePropertiesInfo bpInfo) throws BaseFormatException, SQLException {
//
//		long start = System.currentTimeMillis();
//		
//		String[] queries = new String[] {FDBSQLReadStatements.SELECT_WORDS_NUMER, 
//										 FDBSQLReadStatements.SELECT_ABBREVIATIONS_NUMER, 
//										 FDBSQLReadStatements.SELECT_MEDIA_RESOURCES_NUMER};
//		int[] numbers = new int[queries.length];
//		
//		for (int i = 0; i < queries.length; i++) {
//			ResultSet rs = statement.executeQuery(queries[i]);
//			if (rs.next()) {
//				numbers[i] = rs.getInt(1);
//			}
//			rs.close();			
//		}
//
//		bpInfo.setWordsNumber(numbers[0]);
//		bpInfo.setAbbreviationsNumber(numbers[1]);
//		bpInfo.setMediaResourcesNumber(numbers[2]);
//		
//		if (bpInfo.getWordsNumber() < 1) {
//			throw new BaseFormatException("The words number couldn't be retrieved");
//		}
//			
//		log.debug("Number of items: words={}, abbreviations={}, media resources={}, time={}", new Object[] {bpInfo.getWordsNumber(), bpInfo.getAbbreviationsNumber(), bpInfo.getMediaResourcesNumber(), System.currentTimeMillis() - start});
//	}
	

}
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

import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.AbbreviationsFormattingMode;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.AbstractCollatorFactory;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.fdb.collections.FDBDynamicListSet;
import info.softex.dictionary.core.io.SmartInflaterInputStream;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.9, 11/11/2011
 * 
 * @modified version 4.0, 02/04/2014
 * @modified version 4.2, 03/05/2014
 * @modified version 4.6, 01/28/2015
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
	protected TreeMap<Integer, Integer> wordsRedirects = null;
	
	protected Map<String, String> abbreviations = null;
	protected Set<String> mediaResources = null; // Initialized only when getMediaResourceKeys is called
	
	protected Collator collator = null; 
	
	protected final int wordListBlockSize;
	
	protected PreparedStatement selWordIdByWordSt;
	protected PreparedStatement selWordRedirectByWordIdSt;
	protected PreparedStatement selWordMappingByWordIdSt;
	
	protected PreparedStatement selArticleBlockByIdSt;
	protected PreparedStatement selMediaResourceIdByKey;
	protected PreparedStatement selMediaResourceBlockById; 
	
	protected final AbstractCollatorFactory collatorFactory;
	
	protected final boolean main;
	protected boolean loaded;
	
	public FDBBaseReadUnit(boolean main, String baseFilePath, Connection connection, int wordListBlockSize, AbstractCollatorFactory collatorFactory) throws SQLException {
		this.main = main;
		this.baseFilePath = baseFilePath;
		this.connection = connection;
		this.wordListBlockSize = wordListBlockSize;
		this.collatorFactory = collatorFactory;
		
		// Initialize Prepared Statements
		if (main) {
			selWordIdByWordSt = connection.prepareStatement(FDBSQLReadStatements.SELECT_WORD_ID_BY_WORD);
			selMediaResourceIdByKey = connection.prepareStatement(FDBSQLReadStatements.SELECT_MEDIA_RESOURCE_ID_BY_MEDIA_RESOURCE_KEY);
			
			// Initialize statements for FDB version 3 and higher
			ResultSet rs = connection.createStatement().executeQuery(FDBSQLReadStatements.CHECK_TABLE_WORDS_RELATIONS_EXISTS);
			if (rs.next() && rs.getInt(1) > 0) {
    			selWordRedirectByWordIdSt = connection.prepareStatement(FDBSQLReadStatements.SELECT_WORD_RELATION_REDIRECT_BY_WORD_ID);
    			selWordMappingByWordIdSt = connection.prepareStatement(FDBSQLReadStatements.SELECT_WORD_MAPPING_BY_WORD_ID);
    			log.info("FDB version 3 or higher is detected");
			}
		}
		selMediaResourceBlockById = connection.prepareStatement(FDBSQLReadStatements.SELECT_MEDIA_RESOURCE_BLOCK_BY_MEDIA_RESOURCE_ID);
		selArticleBlockByIdSt = connection.prepareStatement(FDBSQLReadStatements.SELECT_ARTICLE_BLOCK_BY_ID);
		
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
				log.debug(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
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
			
			// Set abbreviations formating to disabled if there are no abbreviations
			if (baseInfo.getAbbreviationsNumber() == 0) {
				baseInfo.setAbbreviationsFormattingMode(AbbreviationsFormattingMode.DISABLED);
			}
			
			// Below version 3 number of articles instead of number of words was defined.
			// Setting number of words to number of articles if number of words is 0.
			if (baseInfo.getWordsNumber() == 0) {
				baseInfo.setWordsNumber(baseInfo.getArticlesDeprecatedNumber());
			// If number of words is defined, set the same to the number of articles
			} else {
				baseInfo.setArticlesDeprecatedNumber(baseInfo.getWordsNumber());
			}
			
			rs.close();
			
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't load BasePropertiesInfo: " + e.getMessage(), BaseFormatException.ERROR_CANT_LOAD_BASE_PROPERIES);
		}
				
		log.info("Dictionary Info: {}", baseInfo);
		
		return baseInfo;
	}

	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return langDirections;
	}
	
	public Set<String> getMediaResourceKeys() throws SQLException {
		if (mediaResources == null) {
			mediaResources = new HashSet<String>();
			PreparedStatement selMediaResourceKeys = connection.prepareStatement(FDBSQLReadStatements.SELECT_ALL_MEDIA_RESOURCE_KEYS);
			ResultSet resRS = selMediaResourceKeys.executeQuery();
			while (resRS.next()) {
				mediaResources.add(resRS.getString(1));
			}
			resRS.close();
		}
		return mediaResources;
	}
	
	public BaseResourceInfo getBaseResourceInfo(String resourceKey) {
		
		BaseResourceInfo resourceInfo = null;
		
//		insBaseResourceSt.setInt(1, baseResourcesNumber++);
//		insBaseResourceSt.setString(2, baseResourceInfo.getResourceKey());
//		insBaseResourceSt.setBytes(3, baseResourceInfo.getByteArray());
//		insBaseResourceSt.setBytes(4, new byte[0]);
//		insBaseResourceSt.setBytes(5, new byte[0]);
//		insBaseResourceSt.setBytes(6, new byte[0]);
//		insBaseResourceSt.setString(7, "");
//		insBaseResourceSt.setString(8, "");
//		insBaseResourceSt.setString(9, "");
//		insBaseResourceSt.setString(10, "");
//		insBaseResourceSt.setString(11, "");
//		insBaseResourceSt.setString(12, "");
//		insBaseResourceSt.executeUpdate();
		
		return resourceInfo;
		
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
			
			this.collator = collatorFactory.createCollator(this.langDirections.getCombinedCollationRules(), null, null);
			
		} catch (SQLException e) {
			log.error("SQL Error", e);
			throw new BaseFormatException("Couldn't load the LanguageDirectionsInfo: " + e.getMessage());
		} catch (ParseException e) {
			log.error("Parse Error", e);
			throw new BaseFormatException("Couldn't parse language data: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Unexpected error: " + e.getClass().getName() + ", " + e.getMessage());
		}
		
		return this.langDirections;
	}
	
	public void load() throws BaseFormatException {
		   
    	try {
    		
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
	
			// Create dynamic list for words
		    words = new FDBDynamicListSet(
		    		baseInfo.getWordsNumber(), 
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
			throw new BaseFormatException("Couldn't load base: " + e.getMessage());
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
	 * @param resourceKey
	 * @return -1 if the key is not found, index otherwise
	 * @throws BaseFormatException 
	 */
	public int searchMediaResourceKeyIndex(String resourceKey) throws BaseFormatException {
		try {
			
			selMediaResourceIdByKey.setString(1, resourceKey);
			ResultSet resIdRS = selMediaResourceIdByKey.executeQuery();
			
			int resourceId = -1;
			if (resIdRS.next()) {
				resourceId = resIdRS.getInt(1);
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
			
			selMediaResourceBlockById.setInt(1, resourceId);
			ResultSet resRS = selMediaResourceBlockById.executeQuery();
			
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
	
	/**
	 * Redirects are returned lazy way because they are mainly 
	 * required at conversions.
	 * 
	 * @return Map of word IDs for redirects
	 */
	public Map<Integer, Integer> getWordsRedirects() throws BaseFormatException {
		if (wordsRedirects == null) {
			wordsRedirects = new TreeMap<Integer, Integer>();
			try {
				
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(FDBSQLReadStatements.SELECT_ALL_RELATIONS_REDIRECTS);
				
				// Populate the redirects map
				while (rs.next()) {
					wordsRedirects.put(rs.getInt(1), rs.getInt(2));
				}
				rs.close();
				
			} catch (Exception e) {
				log.error("Error", e);
				throw new BaseFormatException("Couldn't load redirects: " + e.getMessage());
			}
		} 
		return wordsRedirects;
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
	 * @return
	 * @throws BaseFormatException
	 */
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		
		ArticleInfo articleInfo = null;
		
		if (!wordInfo.hasIndex()) {
			throw new IllegalArgumentException("WordInfo must have an index to get an article");
		}
		
		int articleId = wordInfo.getArticleId();
		
		try {
			
			selArticleBlockByIdSt.setInt(1, articleId);
			ResultSet rs = selArticleBlockByIdSt.executeQuery();

			if (rs.next()) {
				
				int blockId = rs.getInt(1);
				
				log.debug("Retrieving article: word_id: {}, article_block_id: {}", articleId, blockId);
				int segmentNumber = articleId - blockId;
				
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
			throw new BaseFormatException("Couldn't retrieve the article: " + e.getClass().getName() + ", " + e.getMessage());
		}
		
		return articleInfo;
		
	}
	
	public void getWordRedirect(WordInfo wordInfo) throws BaseFormatException {
		
		if (!wordInfo.hasIndex()) {
			throw new IllegalArgumentException("WordInfo must have an index to get its redirect");
		}
		
		int redirectToId = wordInfo.getId();
		
		try {
			
			// Check if redirect is available
			selWordRedirectByWordIdSt.setInt(1, redirectToId);
			ResultSet redirRS = selWordRedirectByWordIdSt.executeQuery();
			if (redirRS.next()) {
				redirectToId = redirRS.getInt(1);
				wordInfo.setRedirectToId(redirectToId);
				wordInfo.setRedirectToWord(redirRS.getString(2));
				log.debug("Relation to word_id={} is found from word_id={}", redirectToId, wordInfo.getId()); 
			}
			redirRS.close();
			
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't retrieve the relation: " + e.getClass().getName() + ", " + e.getMessage());
		}
		
	}
	
	public void getWordMapping(WordInfo wordInfo) throws BaseFormatException {
		
		if (!wordInfo.hasIndex()) {
			throw new IllegalArgumentException("WordInfo must have an index to get its mapping");
		}
		
		int redirectToId = wordInfo.getId();
		
		try {
			
			// Check if redirect is available
			selWordMappingByWordIdSt.setInt(1, redirectToId);
			ResultSet redirRS = selWordMappingByWordIdSt.executeQuery();
			if (redirRS.next()) {
				String mapping = redirRS.getString(1);
				if (mapping != null && !mapping.isEmpty()) {
					wordInfo.setWordMapping(mapping);
					log.debug("Relation to word_id={} is found from word_id={}", redirectToId, wordInfo.getId()); 
				}
			}
			redirRS.close();
			
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException("Couldn't retrieve the word mapping: " + e.getClass().getName() + ", " + e.getMessage());
		}
		
	}
	
	public Set<String> getAbbreviationKeys() {
		return abbreviations == null ? null : abbreviations.keySet();
	}
	
	public void close() throws Exception {
		connection.close();
	}
	
	/**
	 * The method is created for tests
	 */
	public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}

}

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.zip.DeflaterOutputStream;

import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.PrimaryKey;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo.CollationProperties;

/**
 * 
 * @since       version 2.9, 11/19/2011
 * 
 * @modified    version 3.5, 08/07/2012
 * @modified    version 3.9, 01/29/2014
 * @modified    version 4.0, 02/02/2014
 * @modified    version 4.5, 03/29/2014
 * @modified    version 4.6, 01/28/2015
 * @modified    version 4.7, 03/26/2015
 * @modified    version 5.2, 10/21/2018
 *
 * @author Dmitry Viktorov
 * 
 */
public class FDBBaseWriteUnit {

    private static final Logger log = LoggerFactory.getLogger(FDBBaseWriteUnit.class);

	protected static final String UTF8 = "UTF-8";

    protected UUID baseUid = UUID.randomUUID();

	protected BasePropertiesInfo baseInfo = null;
	protected LanguageDirectionsInfo langDirections = null;
	
	protected final String baseFilePath;
	protected final int baseIndex;
	
	protected final Connection connection;
	protected final int maxBatchSize = 1000;
	
	protected int curWordsBatchSize = 0;
	protected int curWordsMappingsBatchSize = 0;
	protected int curWordsRelationsBatchSize = 0;
	protected int curMediaResourcesBatchSize = 0;
	
	protected int basePropertiesRowsNumber = 0;
	
	protected int abbreviationsNumber = 0;
	protected int baseResourcesNumber = 0;
	
	protected final int minArticleBlockMemSize = 200000;
	protected final int minMediaResourceBlockMemSize = 200000;
	
	protected int curArticleBlockMemSize = 0;
	protected LinkedList<byte[]> articlesBuffer = new LinkedList<byte[]>();
	
	protected int curMediaResourceBlockMemSize = 0;
	protected LinkedList<byte[]> mediaResourcesBuffer = new LinkedList<byte[]>();
	
	protected PreparedStatement insWordSt;
	protected PreparedStatement insWordMappingSt;
	protected PreparedStatement insWordRelationSt;
	protected PreparedStatement insArticleSt;
	protected PreparedStatement insBasePropertySt;
	protected PreparedStatement insBaseResourceSt;
	protected PreparedStatement updBasePropertySt;
	protected PreparedStatement insMediaResourceKeySt;
	protected PreparedStatement insMediaResourceSt;
	protected PreparedStatement insAbbreviationSt;
	protected PreparedStatement selAllBaseInfoSt;	
	
	/**
	 * 
	 * @param baseIndex - starts from 1
	 * @param baseFilePath
	 * @param connection
	 * @throws IOException
	 */
	public FDBBaseWriteUnit(int baseIndex, String baseFilePath, Connection connection) throws IOException {
		this.baseIndex = baseIndex;
		this.baseFilePath = baseFilePath;
		this.connection = connection;		
	}

	public void createBase(String... params) throws SQLException, NoSuchAlgorithmException {
		Statement st = connection.createStatement();
		FDBTables[] tables = FDBTables.values();
		for (int i = 0; i < tables.length; i++) {
			st.executeUpdate(FDBSQLWriteStatements.DROP_TABLE_UNI + " " + tables[i]);
			log.debug("Dropping {}", tables[i]);
		}
		
		if (baseIndex == 1) {
			
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_WORDS);

			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_WORDS_MAPPINGS);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_INDEX_WORDS_MAPPINGS_WORD_MAPPINGS_1);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_INDEX_WORDS_MAPPINGS_WORD_MAPPINGS_2);
			
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_WORDS_RELATIONS);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_INDEX_WORDS_RELATIONS_WORD_ID);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_INDEX_WORDS_RELATIONS_TO_WORD_ID);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_INDEX_WORDS_RELATIONS_RELATION_TYPE);
			
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_ARTICLE_BLOCKS);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_ABBREVIATIONS);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_BASE_PROPERTIES);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_BASE_RESOURCES);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_LANGUAGE_DIRECTIONS);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_MEDIA_RESOURCE_KEYS);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_MEDIA_RESOURCE_BLOCKS);
			
			st.executeUpdate(FDBSQLWriteStatements.CREATE_INDEX_LANGUAGE_DIRECTIONS_FL);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_INDEX_LANGUAGE_DIRECTIONS_TL);
			
			// Create prepared statements
			insWordSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_WORD);
			insWordMappingSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_WORD_MAPPING);
			insWordRelationSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_WORD_RELATION);
			insArticleSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_ARTICLE);
			
			insMediaResourceKeySt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_MEDIA_RESOURCE_KEY);
			insMediaResourceSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_MEDIA_RESOURCE);
			insAbbreviationSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_ABBREVIATION);
			insBasePropertySt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_BASE_PROPERTY);
			insBaseResourceSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_BASE_RESOURCE);
			updBasePropertySt = connection.prepareStatement(FDBSQLWriteStatements.UDATE_BASE_PROPERTY);
			selAllBaseInfoSt = connection.prepareStatement(FDBSQLReadStatements.SELECT_ALL_BASE_PROPERTIES);
		} else {
			log.info("A new db has been created. Initializing.");
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_ARTICLE_BLOCKS);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_MEDIA_RESOURCE_BLOCKS);
			st.executeUpdate(FDBSQLWriteStatements.CREATE_TABLE_BASE_PROPERTIES);
			
			insBasePropertySt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_BASE_PROPERTY);
			updBasePropertySt = connection.prepareStatement(FDBSQLWriteStatements.UDATE_BASE_PROPERTY);
			
			insArticleSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_ARTICLE);
			insMediaResourceSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_MEDIA_RESOURCE);
			
			selAllBaseInfoSt = connection.prepareStatement(FDBSQLReadStatements.SELECT_ALL_BASE_PROPERTIES);
		}
	}
	
	
	public BasePropertiesInfo saveBasePropertiesInfo(BasePropertiesInfo inBaseInfo, FormatInfo inFormatInfo) throws SQLException, NoSuchAlgorithmException {
		if (baseIndex == 1) {
			this.baseInfo = inBaseInfo;
			
			inBaseInfo.setFormatName(inFormatInfo.getName());
			inBaseInfo.setFormatVersion(FDBConstants.CURRENT_FDB_VERSION);
			
			// Dates
			Date currentDate = new Date();
			if (inBaseInfo.getCompilationDate() == null) {
				inBaseInfo.setCompilationDate(currentDate);
			}
			if (inBaseInfo.getBaseDate() == null) {
				inBaseInfo.setBaseDate(currentDate);
			}
			
			// If words number is not set, it always must have a value
			if (inBaseInfo.getWordsNumber() == 0) {
				inBaseInfo.setWordsNumber(0);
			}
			
			// The property is not used since FDB version 3, so it's defaulted to words number.
			// Needed only for compatibility with previous viewers
			inBaseInfo.setArticlesDeprecatedNumber(inBaseInfo.getWordsNumber());
			
			// If abbreviations number is not set, it always must have a value
			if (inBaseInfo.getAbbreviationsNumber() == 0) {
				inBaseInfo.setAbbreviationsNumber(0);
			}
			
			// If words mappings number is not set, it always must have a value
			if (inBaseInfo.getWordsMappingsNumber() == 0) {
				inBaseInfo.setWordsMappingsNumber(0);
			}
			
			// If relations number is not set, it always must have a value
			if (inBaseInfo.getWordsRelationsNumber() == 0) {
				inBaseInfo.setWordsRelationsNumber(0);
			}
			
			// If actual articles number is not set, it always must have a value
			if (inBaseInfo.getArticlesActualNumber() == 0) {
				inBaseInfo.setArticlesActualNumber(0);
			}
			
			// If actual media resources number, it always must have a value
			if (inBaseInfo.getMediaResourcesNumber() == 0) {
				inBaseInfo.setMediaResourcesNumber(0);
			}
			
			inBaseInfo.getPrimaryParameters().put(PrimaryKey.ARTICLES_BLOCKS_SIZE_UNCOMPRESSED_MIN.getKey(), this.minArticleBlockMemSize);
			inBaseInfo.getPrimaryParameters().put(PrimaryKey.MEDIA_RESOURCES_BLOCKS_SIZE_UNCOMPRESSED_MIN.getKey(), this.minMediaResourceBlockMemSize);
			
			insertBaseProperty(PrimaryKey.BASE_SECURITY_PROPERTIES_MD5.getKey(), "");
			insertBaseProperty(PrimaryKey.BASE_UID.getKey(), baseUid.toString());
			
			Map<String, Object> params = new HashMap<String, Object>(inBaseInfo.getPrimaryParameters());
			LinkedHashMap<String, Object> orderedParams = new LinkedHashMap<String, Object>();
			PrimaryKey[] pkArr = PrimaryKey.values();
			for (int i = 0; i < pkArr.length; i++) {
				orderedParams.put(pkArr[i].getKey(), params.remove(pkArr[i]));
			}
			orderedParams.putAll(params);
			
			for (Iterator<Map.Entry<String, Object>> iterator = orderedParams.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, Object> entry = iterator.next();
				if (entry.getValue() != null) {
					insertBaseProperty(entry.getKey(), entry.getValue().toString());
				}
			}
		} else { // Dependent bases
			insertBaseProperty(PrimaryKey.BASE_SECURITY_PROPERTIES_MD5.getKey(), "");
            insertBaseProperty(PrimaryKey.BASE_UID.getKey(), baseUid.toString());
			insertBaseProperty(PrimaryKey.FORMAT_NAME.getKey(), inBaseInfo.getFormatName());
			insertBaseProperty(PrimaryKey.FORMAT_VERSION.getKey(), Integer.toString(inBaseInfo.getFormatVersion()));
			insertBaseProperty(PrimaryKey.BASE_VERSION.getKey(), inBaseInfo.getBaseVersion());
			insertBaseProperty(PrimaryKey.BASE_TYPE.getKey(), inBaseInfo.getBaseType());
			insertBaseProperty(PrimaryKey.BASE_DATE.getKey(), inBaseInfo.getBaseDate());
			insertBaseProperty(PrimaryKey.BASE_NAME_SHORT.getKey(), inBaseInfo.getBaseShortName());
			insertBaseProperty(PrimaryKey.BASE_NAME_FULL.getKey(), inBaseInfo.getBaseFullName());
			insertBaseProperty(PrimaryKey.BASE_PARTS_CURRENT_NUMBER.getKey(), Integer.toString(baseIndex));
		}
		return this.baseInfo;
	}
	
	public LanguageDirectionsInfo saveLanguageDirectionsInfo(LanguageDirectionsInfo languageDirectionsInfo) throws Exception {
		this.langDirections = languageDirectionsInfo;
		PreparedStatement insDirectionSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_LANGUAGE_DIRECTION);
		PreparedStatement insBaseResourceSt = connection.prepareStatement(FDBSQLWriteStatements.INSERT_BASE_RESOURCE);		

		Map<Locale, List<CollationProperties>> directions = languageDirectionsInfo.getLanguageDirections();
		
		insBaseResourceSt.setInt(1, baseResourcesNumber++);
		insBaseResourceSt.setString(2, "collation.rules.default");
		insBaseResourceSt.setBytes(3, languageDirectionsInfo.getDefaultCollationProperties().getBasicCollationRules().getBytes(UTF8));
		insBaseResourceSt.setBytes(4, languageDirectionsInfo.getDefaultCollationProperties().getAdditionalCollationRules().getBytes(UTF8));
		insBaseResourceSt.setBytes(5, new byte[0]);
		insBaseResourceSt.setBytes(6, new byte[0]);
		insBaseResourceSt.setString(7, Integer.toString(languageDirectionsInfo.getDefaultCollationProperties().getCollationVersion()));
		insBaseResourceSt.setString(8, languageDirectionsInfo.getDefaultCollationProperties().getCollationStrengthString());
		insBaseResourceSt.setString(9, languageDirectionsInfo.getDefaultCollationProperties().getCollationDecompositionString());
		insBaseResourceSt.setString(10, Boolean.toString(languageDirectionsInfo.getDefaultCollationProperties().isCollationIndependent()));
		insBaseResourceSt.setString(11, "");
		insBaseResourceSt.setString(12, "");
		insBaseResourceSt.addBatch();
		
		int i = 0;
		for (Map.Entry<Locale, List<CollationProperties>> direction : directions.entrySet()) {
			String fromLanguage = localeToLanguageString(direction.getKey());
			List<CollationProperties> propsSet = direction.getValue();
			
			for (CollationProperties langProps : propsSet) {
				insDirectionSt.setInt(1, i++);
				insDirectionSt.setString(2, fromLanguage);
				insDirectionSt.setString(3, localeToLanguageString(langProps.getLocale()));
				insDirectionSt.addBatch();
				
				insBaseResourceSt.setInt(1, baseResourcesNumber++);
				insBaseResourceSt.setString(2, "collation.rules." + fromLanguage);
				insBaseResourceSt.setBytes(3, langProps.getBasicCollationRules().getBytes(UTF8));
				insBaseResourceSt.setBytes(4, langProps.getAdditionalCollationRules().getBytes(UTF8));
				insBaseResourceSt.setBytes(5, new byte[0]);
				insBaseResourceSt.setBytes(6, new byte[0]);
				insBaseResourceSt.setString(7, Integer.toString(langProps.getCollationVersion()));
				insBaseResourceSt.setString(8, langProps.getCollationStrengthString());
				insBaseResourceSt.setString(9, langProps.getCollationDecompositionString());
				insBaseResourceSt.setString(10, Boolean.toString(langProps.isCollationIndependent()));
				insBaseResourceSt.setString(11, "");
				insBaseResourceSt.setString(12, "");
				insBaseResourceSt.addBatch();
			}
		}
		insDirectionSt.executeBatch();
		insBaseResourceSt.executeBatch();
		return languageDirectionsInfo;
	}
	
	/**
	 * Handle replacements for language codes if needed.
	 */
	protected String localeToLanguageString(Locale locale) throws Exception {
		if (locale == null || locale.getLanguage() == null) {
			log.warn("Null locale is passed what is not expected!");
			return null;
		}
		String lang = locale.getLanguage().toLowerCase();
		// ISO 693 changed for Hebrew, so change iw to he
		if (lang.equals("iw")) {
			lang = "he";
		}
		return lang;
	}
	
	public void saveAbbreviation(AbbreviationInfo abbreviationInfo) throws Exception {
		insAbbreviationSt.setInt(1, abbreviationsNumber++);
		insAbbreviationSt.setString(2, abbreviationInfo.getAbbreviation());
		insAbbreviationSt.setString(3, abbreviationInfo.getDefinition());
		insAbbreviationSt.executeUpdate();
		updateBaseProperty(BasePropertiesInfo.PrimaryKey.ABBREVIATIONS_NUMBER.getKey(), Integer.toString(abbreviationsNumber));
	}
	
	public void saveBaseResourceInfo(BaseResourceInfo baseResourceInfo) throws Exception {
		insBaseResourceSt.setInt(1, baseResourcesNumber++);
		insBaseResourceSt.setString(2, baseResourceInfo.getResourceKey());
		insBaseResourceSt.setBytes(3, baseResourceInfo.getByteArray());
		insBaseResourceSt.setBytes(4, new byte[0]);
		insBaseResourceSt.setBytes(5, new byte[0]);
		insBaseResourceSt.setBytes(6, new byte[0]);
		insBaseResourceSt.setString(7, baseResourceInfo.getInfo1());
		insBaseResourceSt.setString(8, baseResourceInfo.getInfo2());
		insBaseResourceSt.setString(9, "");
		insBaseResourceSt.setString(10, "");
		insBaseResourceSt.setString(11, "");
		insBaseResourceSt.setString(12, "");
		insBaseResourceSt.executeUpdate();
	}
		
	public boolean saveMediaResourceKey(String mediaResourceKey, int mediaResourcesNumber) throws Exception {
		insMediaResourceKeySt.setInt(1, mediaResourcesNumber);
		insMediaResourceKeySt.setString(2, mediaResourceKey);
		insMediaResourceKeySt.addBatch();
		
		curMediaResourcesBatchSize++;
		if (curMediaResourcesBatchSize == maxBatchSize) {
			insMediaResourceKeySt.executeBatch();
			insMediaResourceKeySt.clearBatch();
			curMediaResourcesBatchSize = 0;
			return true;
		}
		return false;
	}
	
	public boolean saveMediaResource(byte[] resourceData, int mediaResourcesNumber) throws Exception {
		mediaResourcesBuffer.add(resourceData);
		curMediaResourceBlockMemSize += resourceData.length;
		if (curMediaResourceBlockMemSize >= minMediaResourceBlockMemSize) {
			return true;
		}
		return false;
	}
	
	
	public void saveWord(String word, int wordsNumber) throws Exception {
		// Insert word
		insWordSt.setInt(1, wordsNumber);
		insWordSt.setString(2, word);
		insWordSt.addBatch();
		curWordsBatchSize++;
		if (curWordsBatchSize == maxBatchSize) {
			insWordSt.executeBatch();
			insWordSt.clearBatch();
			curWordsBatchSize = 0;
		}
	}
	
	public void saveRelation(int relationNumber, int wordNumber, int redirectToWordId, int relation) throws Exception {
		insWordRelationSt.setInt(1, relationNumber);
		insWordRelationSt.setInt(2, wordNumber);
		insWordRelationSt.setInt(3, redirectToWordId);
		insWordRelationSt.setInt(4, relation);
		insWordRelationSt.addBatch();
		curWordsRelationsBatchSize++;
		if (curWordsRelationsBatchSize == maxBatchSize) {
			insWordRelationSt.executeBatch();
			insWordRelationSt.clearBatch();
			curWordsRelationsBatchSize = 0;
		}
	}
	
	public void saveWordMapping(int wordNumber, String mapping1, String mapping2) throws Exception {
		insWordMappingSt.setInt(1, wordNumber);
		insWordMappingSt.setString(2, mapping1);
		insWordMappingSt.setString(3, mapping2);
		insWordMappingSt.addBatch();
		curWordsMappingsBatchSize++;
		if (curWordsMappingsBatchSize == maxBatchSize) {
			insWordMappingSt.executeBatch();
			insWordMappingSt.clearBatch();
			curWordsMappingsBatchSize = 0;
		}
	}
	
	public boolean saveArticle(String article, int articleNumber) throws Exception {
		byte[] articleData = article.getBytes(UTF8);
		articlesBuffer.add(articleData);
		curArticleBlockMemSize += articleData.length;
		if (curArticleBlockMemSize >= minArticleBlockMemSize) {
			return true;
		}
		return false;
	}
	
	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return langDirections;
	}
	
	public void close() throws SQLException {
		connection.close();
	}
	
	public BasePropertiesInfo getBasePropertiesInfo() {
		return baseInfo;
	}

	public void flushArticles(int curArticlesNumber) throws SQLException, UnsupportedEncodingException, IOException {
		// Flush words
		if (curWordsBatchSize != 0) {
			insWordSt.executeBatch();
			insWordSt.clearBatch();
		}
		
		// Flush words mappings
		if (curWordsMappingsBatchSize != 0) {
			insWordMappingSt.executeBatch();
			insWordMappingSt.clearBatch();
		}
		
		// Flush words redirects
		if (curWordsRelationsBatchSize != 0) {
			insWordRelationSt.executeBatch();
			insWordRelationSt.clearBatch();
		}
		insertBlockBatch(insArticleSt, articlesBuffer, curArticlesNumber, curArticleBlockMemSize);
		articlesBuffer = new LinkedList<byte[]>();
		curArticleBlockMemSize = 0;
	}
	
	public void flushMediaResources(int curMediaResourcesNumber) throws SQLException, UnsupportedEncodingException, IOException {
		long startTime = System.currentTimeMillis();
		if (curMediaResourcesBatchSize != 0) {
			insMediaResourceKeySt.executeBatch();
			insMediaResourceKeySt.clearBatch();
		}
		insertBlockBatch(insMediaResourceSt, mediaResourcesBuffer, curMediaResourcesNumber, curMediaResourceBlockMemSize);
		mediaResourcesBuffer = new LinkedList<byte[]>();
		curMediaResourceBlockMemSize = 0;
		log.debug("Media resources flushed, total time: {}", System.currentTimeMillis() - startTime);
	}
	
	//----------------------------------------------------
	
	protected void insertBaseProperty(String key, String value) throws SQLException, NoSuchAlgorithmException {
		insBasePropertySt.setInt(1, basePropertiesRowsNumber++);
		insBasePropertySt.setString(2, key);
		insBasePropertySt.setString(3, value);
		insBasePropertySt.executeUpdate();
		if (key.equalsIgnoreCase(BasePropertiesInfo.PrimaryKey.BASE_SECURITY_PROPERTIES_MD5.getKey())) {
			return;
		}
		
		ResultSet rs = selAllBaseInfoSt.executeQuery();
		StringBuffer sb = new StringBuffer();
		while (rs.next()) {
			log.debug(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
			String property = rs.getString(2);
			if (!property.equalsIgnoreCase(BasePropertiesInfo.PrimaryKey.BASE_SECURITY_PROPERTIES_MD5.getKey())) {
				sb.append(property);
			} 
		}
		String secCode = getMD5(sb.toString());
		log.debug("MD5 String: {}, {}, {}", new Object[] {sb, sb.length(), secCode});
		updateBaseProperty(
				BasePropertiesInfo.PrimaryKey.BASE_SECURITY_PROPERTIES_MD5.getKey(), 
				secCode
			);
	}
	
	protected static PreparedStatement insertBlockBatch(
			PreparedStatement insBlockSt, List<byte[]> block, int id, int curBlockMemSize) throws IOException, SQLException {
		if (block.size() == 0) {
			log.debug("Block size is 0 for id {} and mem size is {}. Skipping insert.", id, curBlockMemSize);
			return insBlockSt;
		}

		long startTime = System.currentTimeMillis();
		int blockSize = block.size(); // Only for logging
		int blockMemSize = curBlockMemSize + 4 * block.size() + 8;
		ByteBuffer buffer = ByteBuffer.allocate(blockMemSize);
		buffer.putInt(block.size());
		
		for (byte[] ba : block) {
			buffer.putInt(ba.length);
		}
		for (byte[] ba : block) {
			buffer.put(ba);
		}

		// No need to keep the block
		block = null; 
				
		byte[] uncompData = buffer.array();
		ByteArrayOutputStream compBaos = new ByteArrayOutputStream(uncompData.length);
		DeflaterOutputStream dos = new DeflaterOutputStream(compBaos);
		dos.write(uncompData);
		dos.close();
		byte[] compData = compBaos.toByteArray();

		// BlockId is the same as the wordId of the first article in the block
		insBlockSt.setInt(1, id);
		insBlockSt.setBytes(2, compData);
		insBlockSt.execute();
		
		log.debug(
			"Block flushed, elements: {}, uncomp size: {}, comp size: {}, time: {}", 
			new Object[] {blockSize, curBlockMemSize, compData.length, System.currentTimeMillis() - startTime}
		);
		return insBlockSt;
	}
	
	protected void updateBaseProperty(String key, String value) throws SQLException {
		updBasePropertySt.setString(1, value);
		updBasePropertySt.setString(2, key);
		updBasePropertySt.executeUpdate();		
	}
	
	protected File getBaseFile() {
		return new File(baseFilePath);
	}
	
	protected static String getMD5(String input) throws NoSuchAlgorithmException {
		 MessageDigest digest = MessageDigest.getInstance("MD5");
		 digest.update(input.getBytes());
		 return String.format("%1$032X", new BigInteger(1, digest.digest()));
	}
	
	public int getBaseIndex() {
		return baseIndex;
	}
}

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
import info.softex.dictionary.core.attributes.BasePropertiesInfo.PrimaryKeys;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.database.DatabaseConnectionFactory;
import info.softex.dictionary.core.io.BaseWriter;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.6, 08/21/2011
 * 
 * @modified version 2.9, 11/19/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
@DictionaryFormat(name = "FDB", primaryExtension = ".fdb", extensions = {".fdb"})
public class FDBBaseWriter implements BaseWriter {
	
	private static final Logger log = LoggerFactory.getLogger(FDBBaseWriter.class.getSimpleName());

	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(FDBBaseWriter.class);

	protected final ArrayList<FDBBaseWriteUnit> dbs = new ArrayList<FDBBaseWriteUnit>(1);
	protected final FDBBaseWriteUnit mainBase;
	protected FDBBaseWriteUnit activeBase;
	
	protected final String mainBaseFilePath;
	
	protected long minMainBaseSize = 0;
	
	protected final DatabaseConnectionFactory conFactory;
	
	protected final int minArticleBlockMemSize = 200000;
	protected final int minMediaResourceBlockMemSize = 200000;
	
	protected int wordsNumber = 0;
	protected int mediaResourcesNumber = 0;
	
	protected int lastPartWordsNumber = 0;
	protected int lastPartMediaResourcesNumber = 0;
	
	protected int curArticlesNumber = 0;
	protected int curMediaResourcesNumber = 0;
	
	public FDBBaseWriter(String baseFilePath, DatabaseConnectionFactory conFactory, Map<String, Object> params) throws SQLException, IOException {
		if (params != null) {
			Object baseSizeLimit = params.get(FDBConstants.PARAM_KEY_BASE_SIZE_LIMIT);
			if (baseSizeLimit != null && "default".equalsIgnoreCase(baseSizeLimit.toString())) {
				//minMainBaseSize = 3800000000L; // 4 294 967 295
				minMainBaseSize = 20000000;
				//minMainBaseSize = 1400000000L; // 1610612736L - 1.5 GB
				//minMainBaseSize = 1073741824L; // 1 GB
			}
		}
		
		this.mainBaseFilePath = baseFilePath;
		this.conFactory = conFactory;
		this.mainBase = this.activeBase = new FDBBaseWriteUnit(1, baseFilePath, conFactory.createConnection(baseFilePath, null));
		//this.activeBase = mainBase;
		this.dbs.add(mainBase);
		
		//this.fdbController = new FDBBaseWriteController(conFactory, filePath, minMainBaseSize);
	}
	
	@Override
	public BasePropertiesInfo saveBasePropertiesInfo(BasePropertiesInfo baseInfo) throws SQLException, NoSuchAlgorithmException {
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
	public void saveArticleInfo(ArticleInfo articleInfo) throws Exception {
		
		// Don't admit white spaces at the beginning or end
		articleInfo.getWordInfo().setWord(articleInfo.getWordInfo().getWord().trim());
		articleInfo.setArticle(articleInfo.getArticle().trim());
		
		mainBase.saveWord(articleInfo.getWordInfo().getWord().trim(), wordsNumber);
		
		boolean isFlashed = activeBase.saveArticle(articleInfo.getArticle().trim(), wordsNumber++);
		if (isFlashed) {
			flashArticles();
			reviseBaseFiles();
		}
	}
	
	@Override
	public void saveMediaResourceInfo(MediaResourceInfo mediaResourceInfo) throws Exception {
		mainBase.saveMediaResourceKey(mediaResourceInfo.getResourceKey(), mediaResourcesNumber);
		boolean isFlashed = activeBase.saveMediaResource(mediaResourceInfo.getByteArray(), mediaResourcesNumber++);
		if (isFlashed) {
			flashMediaResources();
			reviseBaseFiles();
		}
	}
	
	@Override
	public void saveBaseResourceInfo(BaseResourceInfo baseResourceInfo) throws Exception {
		mainBase.saveBaseResourceInfo(baseResourceInfo);
	}
	
	@Override
	public void saveAbbreviation(AbbreviationInfo abbreviationInfo) throws Exception {
		mainBase.saveAbbreviation(abbreviationInfo);
	}
	
	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}
	
	@Override
	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return mainBase.getLanguageDirectionsInfo();
	}

	public void close() throws Exception {
		for (int i = 0; i < dbs.size(); i++) {
			dbs.get(i).close();
		}
	}
	
	@Override
	public LanguageDirectionsInfo saveLanguageDirectionsInfo(LanguageDirectionsInfo languageDirectionsInfo) throws Exception {
		return mainBase.saveLanguageDirectionsInfo(languageDirectionsInfo);
	}

	// Protected -----------------------------------------------
		
	protected void reviseBaseFiles() throws IOException, SQLException, NoSuchAlgorithmException {
		if (minMainBaseSize == 0) {
			return;
		}
		
		File activeBaseFile = activeBase.getBaseFile();
		
		if (activeBaseFile.length() >= minMainBaseSize) {
			
			log.info("Revising base files: min base size {}, current base size {}", minMainBaseSize, activeBaseFile.length());
			
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
						BasePropertiesInfo.PrimaryKeys.BASE_PARTS_TOTAL_NUMBER.getKey(), 
						Integer.toString(newBaseIndex)
					);
			} else {
				getBasePropertiesInfo().getPrimaryParameters().put(PrimaryKeys.BASE_PARTS_SIZE_MIN.getKey(), minMainBaseSize);
				mainBase.insertBaseProperty(PrimaryKeys.BASE_PARTS_SIZE_MIN.getKey(), Long.toString(minMainBaseSize));
				mainBase.insertBaseProperty(BasePropertiesInfo.PrimaryKeys.BASE_PARTS_CURRENT_NUMBER.getKey(), "1");
				mainBase.insertBaseProperty(
					BasePropertiesInfo.PrimaryKeys.BASE_PARTS_TOTAL_NUMBER.getKey(), 
					Integer.toString(newBaseIndex));
			}
			
			if (lastPartWordsNumber < wordsNumber) {
				mainBase.insertBaseProperty(
					BasePropertiesInfo.PrimaryKeys.getBasePartsArticlesBlockIdStartKey(newBaseIndex),
					Integer.toString(wordsNumber));
			}

			if (lastPartMediaResourcesNumber < mediaResourcesNumber) {
				mainBase.insertBaseProperty(
					BasePropertiesInfo.PrimaryKeys.getBasePartsMediaResourcesBlockIdStartKey(newBaseIndex),
					Integer.toString(mediaResourcesNumber));
			}			
			
			lastPartWordsNumber = wordsNumber;
			lastPartMediaResourcesNumber = mediaResourcesNumber;
			
		}
		
	}
	
	// Protected -----------------------------------
	@Override
	public void flash() throws Exception {
		flashArticles();
		flashMediaResources();
	}
	
	protected void flashArticles() throws UnsupportedEncodingException, IOException, SQLException {
		mainBase.flashArticles(curArticlesNumber);
		if (activeBase != mainBase) {
			activeBase.flashArticles(curArticlesNumber);
		}
		mainBase.updateBaseProperty(BasePropertiesInfo.PrimaryKeys.ARTICLES_NUMBER.getKey(), Integer.toString(wordsNumber));
		curArticlesNumber = wordsNumber;
	}
	
	protected void flashMediaResources() throws UnsupportedEncodingException, IOException, SQLException {
		mainBase.flashMediaResources(curMediaResourcesNumber);
		if (activeBase != mainBase) {
			activeBase.flashMediaResources(curMediaResourcesNumber);
		}
		mainBase.updateBaseProperty(BasePropertiesInfo.PrimaryKeys.MEDIA_RESOURCES_NUMBER.getKey(), Integer.toString(mediaResourcesNumber));
		curMediaResourcesNumber = mediaResourcesNumber;
	}


}

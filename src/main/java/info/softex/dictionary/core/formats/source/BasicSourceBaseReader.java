/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2014  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.formats.source;

import info.softex.dictionary.core.annotations.BaseFormat;
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo.AbbreviationsFormattingMode;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.commons.BaseFormatException;
import info.softex.dictionary.core.formats.commons.BaseReader;
import info.softex.dictionary.core.formats.source.io.BufferedRandomAccessFile;
import info.softex.dictionary.core.io.DataAdapter;
import info.softex.dictionary.core.utils.ArticleHtmlFormatter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
 * @since version 3.4, 07/02/2012
 * 
 * @modified version 3.5, 08/06/2012
 * @modified version 4.0, 08/06/2012
 * 
 * @author Dmitry Viktorov
 *
 */
@BaseFormat(name = "BASIC_SOURCE", primaryExtension = "", extensions = {})
public class BasicSourceBaseReader implements BaseReader {
	
	protected static final String UTF8 = "UTF-8";
	
	private static final Logger log = LoggerFactory.getLogger(BasicSourceBaseReader.class);
	
	protected static byte[] contentBuffer = new byte[1048576]; // 1 MB buffer 
	
	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(BasicSourceBaseReader.class);

	protected BasePropertiesInfo baseInfo = null;
	protected LanguageDirectionsInfo langDirections = null;
	
	protected List<String> words;
	
	protected List<Long> articlePointers;
	protected TreeMap<Integer, String> articleCache;

	protected Map<String, Long> abbreviations;
	protected TreeMap<String, String> abbreviationCache;
	
	protected Set<String> mediaResources;
	
	protected final File sourceDirectory;

	protected final int articleCacheSize;
	protected final int abbreviationCacheSize;
	
	protected final String mediaDirectoryPath;
	protected final File mediaDirectory;

	protected final BufferedRandomAccessFile artRAF;
	protected final BufferedRandomAccessFile abbRAF;
	
	protected final long artFileSize;

	protected boolean loaded = false;
	
	public BasicSourceBaseReader(File sourceDirectory, int articleCacheSize, int abbreviationCacheSize) throws IOException {
		if (sourceDirectory == null || sourceDirectory.exists() && !sourceDirectory.isDirectory()) {
			throw new IOException("The source must be a directory, not a file!");
		}
		this.sourceDirectory = sourceDirectory;
		this.articleCacheSize = articleCacheSize;
		this.abbreviationCacheSize = abbreviationCacheSize;

		File articlesFile = new File(sourceDirectory + File.separator + BasicSourceFileNames.FILE_ARTICLES);
		this.artRAF = new BufferedRandomAccessFile(articlesFile, "r");
		this.artFileSize = articlesFile.length();
		
		File abbFile = new File(sourceDirectory + File.separator + BasicSourceFileNames.FILE_ABBREVIATIONS);
		if (abbFile.exists() && abbFile.isFile()) {
			this.abbRAF = new BufferedRandomAccessFile(abbFile, "r");
		} else {
			this.abbRAF = null;
		}
		this.mediaDirectoryPath = sourceDirectory + File.separator + BasicSourceFileNames.DIRECTORY_MEDIA;
		this.mediaDirectory = new File(this.mediaDirectoryPath);
	}

	@Override
	public void close() throws Exception {
		if (artRAF != null) {
			artRAF.close();
		}
		if (abbRAF != null) {
			abbRAF.close();
		}
	}

	@Override
	public void load() throws BaseFormatException, Exception {
				
		if (baseInfo == null) {
	        log.debug("Loading BasePropertiesInfo");
	        loadBasePropertiesInfo();
	    }
		
		if (langDirections == null) {
	        log.debug("Loading LanguageDirectionsInfo");
	        loadLanguageDirectionsInfo();
	    }
		
		loaded = true;

		log.debug("Number of Articles: {}", words.size());
		
	}

	@Override
	public BasePropertiesInfo loadBasePropertiesInfo() throws BaseFormatException, Exception {
		
		baseInfo = new BasePropertiesInfo();

		// Articles
		articlePointers = new ArrayList<Long>(30000);
		articleCache = new TreeMap<Integer, String>();
		words = loadWords(artRAF, articlePointers);
		
		// Abbreviations
		abbreviationCache = new TreeMap<String, String>();
		abbreviations = loadAbbreviations(abbRAF);

		// Media Resources
		mediaResources = loadMediaResources(mediaDirectory);
		
		baseInfo.setArticlesNumber(words.size());
		baseInfo.setAbbreviationsNumber(abbreviations.size());
		baseInfo.setMediaResourcesNumber(mediaResources.size());
		
		// Set abbreviations formating to disabled if there are no abbreviations
		if (baseInfo.getAbbreviationsNumber() == 0) {
			baseInfo.setAbbreviationsFormattingMode(AbbreviationsFormattingMode.DISABLED);
		}
		
		return baseInfo;
	}

	/**
	 * Method is not supported.
	 */
	@Override
	public LanguageDirectionsInfo loadLanguageDirectionsInfo() throws BaseFormatException, Exception {
		return null;
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public BasePropertiesInfo getBasePropertiesInfo() {
		return baseInfo;
	}

	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}

	/**
	 * Method is not supported.
	 */
	@Override
	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return null;
	}

	@Override
	public Set<String> getMediaResourceKeys() throws BaseFormatException {
		return mediaResources;
	}

	@Override
	public MediaResourceInfo getMediaResourceInfo(MediaResourceInfo.Key mediaKey) throws BaseFormatException {
		MediaResourceInfo resourceInfo = null;
		if (mediaResources.contains(mediaKey.getResourceKey())) {
			try {
				DataAdapter isAdapter = new DataAdapter(new File(mediaDirectoryPath + File.separator + mediaKey.getResourceKey()));
				resourceInfo = new MediaResourceInfo(mediaKey, isAdapter.toByteArray());
			} catch (IOException e) {
				log.error("Error", e);
				throw new BaseFormatException("Couldn't retrieve media resource: " + e.getMessage());
			}
		}
		return resourceInfo;
	}

	@Override
	public Set<String> getAbbreviationKeys() throws BaseFormatException {
		return abbreviations.keySet();
	}

	@Override
	public AbbreviationInfo getAbbreviationInfo(String abbreviationKey) throws BaseFormatException {
		
		String definition = abbreviationCache.get(abbreviationKey);
		if (definition == null) {
			int ci = 0;
			try {
				abbreviationCache.clear();
				Long pointer = abbreviations.get(abbreviationKey);
				if (pointer != null) {
					abbRAF.seek(abbreviations.get(abbreviationKey));
					for (; ci < abbreviationCacheSize; ci++) {
						StringBuffer strLine = readLineUTF(abbRAF);
						if (strLine == null) {
							break;
						} else if (strLine.toString().trim().length() == 0) {
							continue;
						}
						int delimPosition = strLine.indexOf("  ");			
						abbreviationCache.put(
								new String(strLine.substring(0, delimPosition).trim()), 
								new String(strLine.substring(delimPosition).trim())
							);
					}
					definition = abbreviationCache.get(abbreviationKey);
				}
			} catch (Exception e) {
				log.error("Error", e);
				throw new BaseFormatException("Couldn't read abbreviation number " + ci + ": " + e.getMessage());
			}
			
		} 

		return definition == null ? null : new AbbreviationInfo(abbreviationKey, definition);
	}

	/**
	 * Unsupported method.
	 */
	@Override
	public int searchWordIndex(String word, boolean positive) throws BaseFormatException {
		return -1;
	}

	@Override
	public List<String> getWords() throws BaseFormatException {
		return words;
	}

	@Override
	public ArticleInfo getArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		ArticleInfo articleInfo = getRawArticleInfo(wordInfo);
		if (articleInfo != null) {
			String article = ArticleHtmlFormatter.prepareArticle(
					wordInfo.getWord(),
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

		//long t1 = System.currentTimeMillis();
		
		int wid = wordInfo.getId();
		String article = articleCache.get(wid);

		if (article == null) {
			int ci = wid;
			try {
				articleCache.clear();
				artRAF.seek(articlePointers.get(wid));
				
				for (;ci < wid + articleCacheSize && ci < words.size(); ci++) {

					int byteSize = 0;
					int nextWid = ci + 1;
					
					if (nextWid < articlePointers.size()) {
						byteSize = (int) (articlePointers.get(ci + 1) - articlePointers.get(ci));
					} else {
						byteSize = (int) (artFileSize - articlePointers.get(ci));
					}
					
					StringBuffer strLine = readLineUTF(artRAF, byteSize);
					if (strLine == null) {
						break;
					} else {
						String trimmedLine = strLine.toString().trim();
						if (trimmedLine.length() == 0) {
							continue;
						} else {
							int delimPosition = trimmedLine.indexOf("  ");	
							articleCache.put(ci, trimmedLine.substring(delimPosition));	
						}
					}
					
				}
				
				article = articleCache.get(wid);

			} catch (Exception e) {
				log.error("Error", e);
				throw new BaseFormatException("Couldn't read article number " + ci + ": " + e.getMessage());
			}
			
		}
		
		//log.info("Source | Get raw article time: {}", System.currentTimeMillis() - t1);
		
		return (article == null) ? null : new ArticleInfo(wordInfo, article);
		
	}
	
	
	// Protected Methods -----------------------------------------------------------
	
	protected static List<String> loadWords(BufferedRandomAccessFile raf, List<Long> pointerData) throws BaseFormatException, Exception {

		ArrayList<String> keys = new ArrayList<String>(10000);
		
		StringBuffer line = null;
		
		int i = 0;
		int m = 0;
		
		pointerData.add(raf.getFilePointer());
		
		while ((line = readLineUTF(raf)) != null) {
			
			int delimiter = line.indexOf("  ");	
			if (delimiter < 0) {
				if (line.length() != 0) {
					log.warn("Couldn't split the article line #{}, skipping: {}", i, line);
				} else {
					log.warn("Article line #{} is empty, skipping", i);
				}
				continue;
			}

			String key = line.substring(0, delimiter);
			
			if (line.length() > 300000) {
				log.info("Long line found! Key: {}, Length: {}", key, line.length());
			}
			
			keys.add(key);
			pointerData.add(raf.getFilePointer());
						
			i++;
			m += key.length();

			if (i % 10000 == 0) {
				log.debug("I " + i + " M " + m + ", P " + raf.getFilePointer());
			}
		}
		
		return keys;
	}
	
	protected static Map<String, Long> loadAbbreviations(BufferedRandomAccessFile raf) throws BaseFormatException, Exception {
		HashMap<String, Long> keys = new HashMap<String, Long>(100);
		if (raf != null) {
			StringBuffer line = null;
			int i = 0;
			long prevPointer = raf.getFilePointer();
			while ((line = readLineUTF(raf)) != null) {
				int delimiter = line.indexOf("  ");	
				if (delimiter < 0) {
					if (line.toString().length() != 0) {
						log.warn("Couldn't split the abbreviation line #{}, skipping: {}", i, line);
					} else {
						log.warn("Abbreviation line #{} is empty, skipping", i);
					}
					continue;
				}
				String key = line.substring(0, delimiter).trim();
				keys.put(key, prevPointer);
				prevPointer = raf.getFilePointer();	
				
				i++;
				
			}
		}
		return Collections.unmodifiableMap(keys);
	}
	
	protected static Set<String> loadMediaResources(File mediaDirectory) throws BaseFormatException, Exception {
		HashSet<String> resources = new HashSet<String>();
		if (mediaDirectory.exists() && mediaDirectory.isDirectory()) {
            for (File file : mediaDirectory.listFiles()) {
                if (file.isFile()) {
                	resources.add(file.getName());
                }
            }
		}		
		return Collections.unmodifiableSet(resources);
	}
	
	/**
	 * It's important to return string buffer because the String transferred 
	 * to StringBuffer by the compiler may cause OOM in the loop.
	 * 
	 * @param raf
	 * @return
	 * @throws IOException
	 */
	protected static StringBuffer readLineUTF(BufferedRandomAccessFile raf) throws IOException {

		byte[] lineBuffer = raf.readLineBuffer();
		if (lineBuffer == null) {
			return null;
		}

		return new StringBuffer(new String(lineBuffer, UTF8));
		
	}
	
	protected static StringBuffer readLineUTF(BufferedRandomAccessFile raf, int length) throws IOException {
		
		if (length > contentBuffer.length) {
			contentBuffer = new byte[length];
			log.warn("Read buffer is extended to: {}", length);
		}
		
		int read = raf.readFullBuffer(contentBuffer, 0, length);
		if (read < 0) {
			return null;
		}

		return new StringBuffer(new String(contentBuffer, 0, length, UTF8));
		
	}
	
}

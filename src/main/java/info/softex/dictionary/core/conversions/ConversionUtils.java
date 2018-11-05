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

package info.softex.dictionary.core.conversions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Set;

import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.BaseResourceKey;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.MediaResourceKey;
import info.softex.dictionary.core.attributes.ProgressInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.collation.BasicCollatorFactory;
import info.softex.dictionary.core.formats.api.BaseReader;
import info.softex.dictionary.core.formats.api.BaseWriter;

/**
 * Utilities for base conversions.
 * 
 * @since		version 4.6, 02/26/2015
 *
 * @modified	version 5.2, 10/21/2018
 *
 * @author Dmitry Viktorov
 *
 */
public class ConversionUtils {
	
	private final static Logger log = LoggerFactory.getLogger(ConversionUtils.class);
	
	/**
	 * Method converts the input base to the output. The observer operates on <code>ProgressInfo</code>, 
	 * so the submitted observer instance is expected to recognize it.
	 * 
	 * Reader and writer are not closed after the process, so it's expected at the calling layer.
	 * 
	 * @param reader - input base
	 * @param writer - output base
	 * @param observer - tracks the conversion progress
	 * @throws Exception 
	 */
	public static void convert(BaseReader reader, BaseWriter writer, Observer observer) throws Exception {
		ProgressInfo progress = new ProgressInfo();
		
		// Apply simplified conversion when the output base contains keys only
        if (writer.getFormatInfo().hasKeysOnly()) {
			// Convert keys
			convertAbbreviationsKeys(reader, writer);
			convertArticlesKeys(reader, writer);
			convertMediaResourcesKeys(reader, writer);
        } else {
			// Convert keys & articles
        	convertBaseResources(reader, writer);
			convertAbbreviations(reader, writer, observer, progress);
			convertArticles(reader, writer, observer, progress);
			convertMediaResources(reader, writer, observer, progress);
        }
	}
	
	/**
	 * Creates collator for the writer base on it's format info an properties
	 */
	protected static Collator createCollator(BaseWriter writer) throws Exception {
		Collator collator = null;
        if (writer.getFormatInfo().isSortingExpected()) {
			LanguageDirectionsInfo directionsInfo = writer.getLanguageDirectionsInfo();
			collator = new BasicCollatorFactory().createCollator(
				directionsInfo.getCombinedCollationRules(), 
				directionsInfo.getDefaultCollationProperties().getCollationStrength(), 
				directionsInfo.getDefaultCollationProperties().getCollationDecomposition()
			);     
        }
        return collator;
	}
	
	protected static void convertBaseResources(BaseReader reader, BaseWriter writer) throws Exception {
		for (BaseResourceKey brk : BaseResourceKey.values()) {
			if (brk.isAutomatic()) {
				BaseResourceInfo resInfo = reader.getBaseResourceInfo(brk.getKey());
				if (resInfo != null) {
					writer.saveBaseResourceInfo(resInfo);
				}
			}
		}
	}
	
	protected static void convertAbbreviations(BaseReader reader, BaseWriter writer, Observer observer, ProgressInfo progress) throws Exception {
		Set<String> keys = reader.getAbbreviationKeys();
		if (keys == null) {
			log.info("No abbreviations available");
			return;
		}
		
		log.info("Number of abbreviations: {}", keys.size());
		
		// Update progress
		observer.update(progress.setMessage("Sorting Abbreviations"), null);
		
		ArrayList<String> sortedKeys = new ArrayList<String>(keys.size());
		for (String key : keys) {
			sortedKeys.add(key);
		}
		
        // Create Collator and sort abbreviations
		Collator collator = createCollator(writer);
		if (collator != null) {
			Collections.sort(sortedKeys, collator);		
		}  else {
			log.warn("Collator couldn't be created, sorting of Abbreviations is skipped");
		}
		
		// Update progress
		observer.update(progress.setMessage("Converting Abbreviations"), null);
		
		for (Iterator<String> iterator = sortedKeys.iterator(); iterator.hasNext();) {
			String abbr = iterator.next();
			AbbreviationInfo abbrInfo = reader.getAbbreviationInfo(abbr);
			String definition = abbrInfo.getAbbreviation();			
			if (abbr == null || definition == null || abbr.trim().equals("") || definition.trim().equals("")) {
				log.info("Abbreviation {} is excluded", abbrInfo);
				continue;
			}
			
			abbrInfo.setAbbreviation(abbrInfo.getAbbreviation().trim());
			abbrInfo.setDefinition(abbrInfo.getDefinition().trim());
			
			writer.saveAbbreviationInfo(reader.getAbbreviationInfo(abbr));
		}
		
		writer.flush();
	}
	
	protected static void convertArticles(BaseReader reader, BaseWriter writer, Observer observer, ProgressInfo progress) throws Exception {
		log.info("Started converting articles: Size {}", reader.getWords().size());
		
		// Update progress
		observer.update(progress.setMessage("Mapping and Sorting Articles"), null);
		
		// Clone words to speed up the process (it can be based on buffered list)
		List<String> srcWords = new ArrayList<String>(reader.getWords());
		
		// Mappings and redirects
		Map<Integer, String> srcMappings = reader.getAdaptedWordsMappings();
		Map<Integer, Integer> srcRedirects = reader.getWordsRedirects();
		
        // Create Collator and sort articles
		Collator collator = createCollator(writer);
		
		// Create and loadBaseInfo words mapper
		ConversionWordsMapper wordsMapper = new ConversionWordsMapper(collator, srcWords, srcMappings, srcRedirects);
		wordsMapper.init();
				
		List<String> normWords = wordsMapper.getNormalizedWords();

		// Update progress
		observer.update(progress.setMessage("Converting Articles"), null);
		
		for (int i = 0; i < normWords.size(); i++) {
			
			String curWord = normWords.get(i);
			int oldWordId = wordsMapper.getOldWordId(curWord);
			
			// Word info to retrieve articles. Can't be moved outside because 
			// it' overridden every time article is retrieved.
			WordInfo oldWordInfo = new WordInfo(null, oldWordId);
			
			ArticleInfo inArticleInfo = reader.getAdaptedArticleInfo(oldWordInfo);
			
			if (inArticleInfo == null || inArticleInfo.getArticle() == null) {
				log.warn("Couldn't find article for {}", oldWordInfo);
				throw new IllegalStateException("Couldn't find article for " + oldWordInfo);
			}
			
			WordInfo newWordInfo = new WordInfo(null, i, curWord);
			
			// Add mapping if any
			String newMapping = wordsMapper.getNewWordMappingByOldWordId(oldWordId);
			if (newMapping != null) {
				newWordInfo.setWordMapping(newMapping);
			}
			
			// Add redirect if any
			int newRedirectToId = wordsMapper.getNewWordRedirect(oldWordId);
			if (newRedirectToId >= 0 && newRedirectToId != i) { // Don't allow redirects to itself
				newWordInfo.setRedirectToId(newRedirectToId);
			}
			
			ArticleInfo outArticleInfo = new ArticleInfo(newWordInfo, inArticleInfo.getArticle());

			writer.saveAdaptedArticleInfo(outArticleInfo);

			//if (i > 10000) break;

		}

		writer.flush();
	}
	
	protected static void convertMediaResources(BaseReader reader, BaseWriter writer, Observer observer, ProgressInfo progress) throws Exception {
		Set<String> keys = reader.getMediaResourceKeys();
		if (keys == null) {
			log.info("No media resources available");
			return;
		}
		
		log.info("Number of media resources: {}", keys.size());
		
		// Update progress
		observer.update(progress.setMessage("Sorting Media Resources"), null);
		
		ArrayList<String> sortedKeys = new ArrayList<String>(keys.size());
		for (String key : keys) {
			sortedKeys.add(key);
		}
		
        // Create Collator and sort articles
		Collator collator = createCollator(writer);
		if (collator != null) {
			Collections.sort(sortedKeys, collator);
		} else {
			log.warn("Collator couldn't be created, sorting of Media Resources is skipped");
		}
		
		// Update progress
		observer.update(progress.setMessage("Converting Media Resources"), null);
		
		for (Iterator<String> iterator = sortedKeys.iterator(); iterator.hasNext();) {
			String sortedKey = iterator.next();
			MediaResourceInfo mediaInfo = reader.getMediaResourceInfo(new MediaResourceKey(null, sortedKey));
			mediaInfo.getKey().setResourceKey(mediaInfo.getKey().getResourceKey());
			writer.saveMediaResourceInfo(mediaInfo);			
		}
		
		writer.flush();
	}
	
	protected static void convertAbbreviationsKeys(BaseReader reader, BaseWriter writer) throws Exception {
		Set<String> keys = reader.getAbbreviationKeys();
		AbbreviationInfo abbrevInfo = new AbbreviationInfo("", "");
		for (String key : keys) {
			abbrevInfo.setKey(key);
			writer.saveAbbreviationInfo(abbrevInfo);
		}
	}
	
	protected static void convertArticlesKeys(BaseReader reader, BaseWriter writer) throws Exception {
		List<String> words = reader.getWords();
		ArticleInfo articleInfo = new ArticleInfo(new WordInfo(null, ""), "");
		for (String word : words) {
			articleInfo.getWordInfo().setWord(word);
			writer.saveAdaptedArticleInfo(articleInfo);
		}
	}
	
	protected static void convertMediaResourcesKeys(BaseReader reader, BaseWriter writer) throws Exception {
		Set<String> mediaKeys = reader.getMediaResourceKeys();
		MediaResourceInfo resourceInfo = new MediaResourceInfo(new MediaResourceKey(null, ""), null);
		for (String mkey : mediaKeys) {
			resourceInfo.getKey().setResourceKey(mkey);
			writer.saveMediaResourceInfo(resourceInfo);
		}
	}

}

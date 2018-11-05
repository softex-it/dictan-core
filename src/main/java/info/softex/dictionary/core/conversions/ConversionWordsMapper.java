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

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Normalizes and filters the input data for conversion.
 * 
 * @since version 4.6, 		03/02/2015
 * 
 * @modified version 4.7, 	03/25/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class ConversionWordsMapper {
	
	private final static Logger log = LoggerFactory.getLogger(ConversionWordsMapper.class);
	
	protected final Collator collator;
	protected final List<String> origWords;
	protected List<String> normalizedWords;
	
	
	protected final Map<Integer, String> origWordsMappings;
	protected final Map<Integer, Integer> origWordsRedirects;
	
	protected final Map<String, Integer> wordsToOldWordIds = new HashMap<String, Integer>();
	protected final Map<String, Integer> wordsToNewWordIds = new HashMap<String, Integer>();
	
	protected final Set<Integer> removedOldWordIds = new HashSet<Integer>();
	
	public ConversionWordsMapper(Collator inCollator, List<String> inWordList, Map<Integer, String> inWordsMappings, Map<Integer, Integer> inWordRedirects) {
		this.collator = inCollator;
		this.origWords = inWordList;
		this.origWordsMappings = inWordsMappings;
		this.origWordsRedirects = inWordRedirects;
	}
	
	public void init() {
		
		log.info("Normalization executed");
		
		// Transform the list to word to old id and removedWordIds
		wordsToOldIndex(origWords, wordsToOldWordIds, removedOldWordIds);
		
		// Clone word list excluding the duplicates
		normalizedWords = cloneWordListWithoutDupes(origWords, removedOldWordIds);
		
		// Sort list if collator is defined
		if (collator != null) {
			Collections.sort(normalizedWords, collator);
		}
		
		// Transform the new sorted list to word to new id
		wordsToNewIndex(normalizedWords, wordsToNewWordIds);
		
		log.info("Finished Normalizing. Total words: {}", normalizedWords.size());
		
	}
	
	protected static void wordsToOldIndex(List<String> wordList, Map<String, Integer> wordsToOldIds, Set<Integer> removedWordIds) {
		for (int i = 0; i < wordList.size(); i++) {
			String word = wordList.get(i);
			if (!wordsToOldIds.containsKey(word)) {
				wordsToOldIds.put(word, i);
			} else {
				log.warn("The word {} (index {}) is already in the map. Duplicates are not allowed, removing it.", word, i);
				removedWordIds.add(i);
			}
		}
	}
	
	
	protected static void wordsToNewIndex(List<String> normWordList, Map<String, Integer> wordsToNewIds) {
		for (int i = 0; i < normWordList.size(); i++) {
			String word = normWordList.get(i);
			wordsToNewIds.put(word, i);
		}
	}
	
	protected static List<String> cloneWordListWithoutDupes(List<String> wordList, Set<Integer> removedWordIds) {
		ArrayList<String> cleanedList = new ArrayList<String>(wordList.size() - removedWordIds.size());
		
		log.info("List size before removing duplicates: {}", wordList.size());
		for (int i = 0; i < wordList.size(); i++) {
			if (!removedWordIds.contains(i)) {
				cleanedList.add(wordList.get(i));
			}
		}
		log.info("List size after removing duplicates: {}", cleanedList.size());

		log.info("Number of removed duplicate words: {}", removedWordIds.size());
		
		return cleanedList;
	}
	
	public List<String> getNormalizedWords() {
		return normalizedWords;
	}
	
	public int getOldWordId(String word) {
		Integer oldWordId = wordsToOldWordIds.get(word);
		if (oldWordId == null) {
			throw new IllegalStateException("Old word id is not found for word:" + word);
		}
		return oldWordId;
	}
	
	public String getNewWordMappingByOldWordId(int oldWordId) {
		if (origWordsMappings != null) {
			return origWordsMappings.get(oldWordId);
		}
		return null;
	}
	
	public int getNewWordRedirect(int oldWordId) {
		if (origWordsRedirects != null) {
			Integer oldRedirectToId = origWordsRedirects.get(oldWordId);
			if (oldRedirectToId != null) {
				String redirectToWord = origWords.get(oldRedirectToId);
				Integer newWordId = wordsToNewWordIds.get(redirectToWord);
				if (newWordId != null) {
					return newWordId;
				} else {
					throw new IllegalStateException("New word id is not found for word: " + redirectToWord);
				}
			}
		}		
		return -1;
	}

}

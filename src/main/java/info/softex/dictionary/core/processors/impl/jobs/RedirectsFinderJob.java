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

package info.softex.dictionary.core.processors.impl.jobs;

import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.processors.api.DataInjector;
import info.softex.dictionary.core.processors.api.JobData;
import info.softex.dictionary.core.processors.api.JobRunnable;
import info.softex.dictionary.core.utils.FileUtils;
import info.softex.dictionary.core.utils.PreconditionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.8,		04/30/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class RedirectsFinderJob implements JobRunnable {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected final Map<String, LinkedHashSet<Integer>> articles = new LinkedHashMap<>();
	protected final LinkedHashMap<Integer, Integer> redirects = new LinkedHashMap<>();
	protected final LinkedHashMap<Integer, String> redirectedWords = new LinkedHashMap<>();
	protected BufferedWriter infoWriter;
	protected Collection<LinkedHashSet<Integer>> references;
	
	public RedirectsFinderJob(String outputFile) throws IOException {
		this.infoWriter = FileUtils.openBufferedWriter(new File(outputFile), UTF8);
	}
	
	@Override
	public JobRunnable injectData(DataInjector dataInjector) throws Exception {
		return this;
	}

	@Override
	public boolean processItem(JobData jobData) throws Exception {
		
		ArticleInfo articleInfo = (ArticleInfo)jobData.getDataObject();
		int wordId = articleInfo.getWordInfo().getId();
		String word = articleInfo.getWordInfo().getWord();
		String article = articleInfo.getArticle();
		
		LinkedHashSet<Integer> words = articles.get(article);
		
		if (words == null) {
			words = new LinkedHashSet<>();
			articles.put(article, words);
		}
		
		if (!words.contains(wordId)) {
			words.add(wordId);
			redirectedWords.put(wordId, word);
			return true;
		}
		
		return false;

	}

	@Override
	public void finish() throws Exception {
		
		log.info("Finishing the job");
		
		int referencedEntries = 0;
		
		// Write all referenced sets to output
		references = articles.values();
		for (LinkedHashSet<Integer> curSet : references) {
			if (curSet.size() > 1) {
				referencedEntries++;
				
				List<Integer> curList = new ArrayList<>(curSet);
				
				// Check all redirects for duplicates and write them to file
				HashSet<String> uniqueWords = new HashSet<>();
				int count = 0;
				for (; count < curList.size(); count++) {
					String resolvedWord = redirectedWords.get(curList.get(count));
					PreconditionUtils.checkNotNull(resolvedWord, "Word can't be resolved");
					
					if (uniqueWords.contains(resolvedWord)) {
						throw new IllegalArgumentException("Duplicate words are not expected. Found: " + resolvedWord);
					}
					
					infoWriter.write(resolvedWord);
					if (count < curList.size() - 1) {
						infoWriter.write(" | ");
					}
				}
				infoWriter.write("\r\n");
				
				int mainWordId = curList.get(0);
				for (int i = 1; i < curList.size(); i++) {
					int curWordId = curList.get(i);
					if (!redirects.containsKey(curWordId)) {
						redirects.put(curWordId, mainWordId);
					} else {
						throw new IllegalArgumentException("Word ID " + curWordId + "is already added as a reference to " + mainWordId);
					}
				}
				
			}
		}
		
		log.info("Total number of referenced entries: {}", referencedEntries);
		
		if (infoWriter != null) {
			infoWriter.close();
			infoWriter = null;
		}
		
	}
	
	public Collection<LinkedHashSet<Integer>> getArticleReferences() {
		return references;
	}
	
	public LinkedHashMap<Integer, Integer> getRedirects() {
		return redirects;
	}
	
	public LinkedHashMap<Integer, String> getRedirectedWords() {
		return redirectedWords;
	}

}

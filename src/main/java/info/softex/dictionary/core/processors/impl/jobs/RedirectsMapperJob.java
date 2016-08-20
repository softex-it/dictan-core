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
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.8,		04/30/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class RedirectsMapperJob extends AbstractDSLJob {
		
	private final Logger log = LoggerFactory.getLogger(getClass());
	protected final LinkedHashMap<Integer, Integer> redirects;
	protected final LinkedHashMap<Integer, String> redirectedWords;
	protected BufferedWriter infoWriter;
	protected int mappedArticles = 0;
	
	public RedirectsMapperJob(String inBasePath, LinkedHashMap<Integer, Integer> inRedirects, LinkedHashMap<Integer, String> inRedirectedWords, String outputFile) throws IOException {
		super(inBasePath);
		this.redirects = inRedirects;
		this.redirectedWords = inRedirectedWords;
		this.infoWriter = FileUtils.openBufferedWriter(new File(outputFile), UTF8);
	}
	
	@Override
	public JobRunnable injectData(DataInjector dataInjector) throws Exception {
		super.injectData(dataInjector);
		writer.saveBaseResourceInfo(artDslResource);
		return this;
	}

	@Override
	public boolean processItem(JobData jobData) throws IOException {
		
		PreconditionUtils.checkNotNull(artDslResource, 
			"Article DSL Resource can't be null, it has to be injected before processing");
		
		ArticleInfo articleInfo = (ArticleInfo)jobData.getDataObject();
		String word = articleInfo.getWordInfo().getWord();
		int wordId = articleInfo.getWordInfo().getId();
		
		Integer redirectTo = redirects.get(wordId);
		if (redirectTo != null) {
			articleInfo.getWordInfo().setRedirectToId(redirectTo);
			infoWriter.write(word + " | " + redirectedWords.get(redirectTo) + "\r\n");
			mappedArticles++;
		}

		writer.saveRawArticleInfo(articleInfo);
		
		return true;
	}
	
	@Override
	public void finish() throws Exception {
		log.info("Number of mapped articles: {}", mappedArticles);
		
		if (infoWriter != null) {
			infoWriter.close();
			infoWriter = null;
		}
		
		super.finish();
	}
	
}

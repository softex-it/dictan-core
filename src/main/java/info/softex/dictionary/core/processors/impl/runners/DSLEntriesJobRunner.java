package info.softex.dictionary.core.processors.impl.runners;

import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.BaseResourceKey;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.dsl.DSLBaseReader;
import info.softex.dictionary.core.processors.api.DataInjector;
import info.softex.dictionary.core.processors.api.JobRunnable;
import info.softex.dictionary.core.processors.api.JobRunner;
import info.softex.dictionary.core.processors.impl.BasicJobData;
import info.softex.dictionary.core.utils.PreconditionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since version 4.8,		04/29/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLEntriesJobRunner implements JobRunner {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected final File baseFile;
	protected final DSLBaseReader reader;
	
	public DSLEntriesJobRunner(File inBaseFile) throws IOException {
		this.baseFile = inBaseFile;
		this.reader = new DSLBaseReader(inBaseFile);
		log.info("DSL Reader is created at {}", inBaseFile);
	}
	
	public DSLEntriesJobRunner(String inBasePath) throws IOException {
		this(new File(inBasePath));
	}

	@Override
	public void run(JobRunnable job) throws Exception {
		
		long t1 = System.currentTimeMillis();
		
		reader.load();
		
		log.info("Reader is loaded. Processing lines from DSL base at: {}", baseFile);
		
		// Headers
		BaseResourceInfo artDslRes = reader.getBaseResourceInfo(BaseResourceKey.BASE_ARTICLES_META_DSL.getKey());
		PreconditionUtils.checkNotNull(artDslRes, "DSL Article Resource is not found");
		job.injectData(new DSLHeaderInjector(artDslRes));
		
		List<String> words = reader.getWords();
		
		BasicJobData jobData = new BasicJobData();
		
		int totalItems = 0;
		int ignoredItems = 0;
		
		for (; totalItems < words.size(); totalItems++) {
			
			if (totalItems % 50000 == 0 && totalItems > 0) {
				long partTime = (System.currentTimeMillis() - t1) / 1000;
				log.info("Processed Items: {}. Total Time: {} sec", totalItems, partTime);
			}
			
			ArticleInfo articleInfo = reader.getRawArticleInfo(new WordInfo(totalItems));
			jobData.setDataObject(articleInfo);
			
			boolean isProcessed = job.processItem(jobData);
			if (!isProcessed) {
				ignoredItems++;
			}
			
		}
		
		reader.close();
		
		job.finish();
		
		int processedItems = totalItems - ignoredItems;
		long time = (System.currentTimeMillis() - t1) / 1000;
		log.info("Processing complete. Time: {} sec. Items ignored: {}, processed: {}, total: {}", 
			time, ignoredItems, processedItems, totalItems
		);
		
	}
	
	protected class DSLHeaderInjector implements DataInjector {
		protected final BaseResourceInfo artDslResource;
		
		public DSLHeaderInjector(BaseResourceInfo inDslResource) {
			this.artDslResource = inDslResource;
		}

		@Override
		public void inject(Map<DataKey, Object> dataMap) {
			dataMap.put(DataInjector.DataKey.DATA_OBJECT_1, artDslResource);
		}
	}

}

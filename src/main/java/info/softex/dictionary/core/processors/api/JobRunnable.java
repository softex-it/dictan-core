package info.softex.dictionary.core.processors.api;

/**
 * Ported from wiki-crawler.
 * 
 * @since version 1.0,	03/17/2014
 * 
 * @modified version 2.1,	01/25/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public interface JobRunnable {
	
	public final static String UTF8 = "UTF-8";
	
	public JobRunnable injectData(DataInjector dataInjector) throws Exception;
	
	public boolean processItem(JobData jobData) throws Exception;
	
	public void finish() throws Exception;

}


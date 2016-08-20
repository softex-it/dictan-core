package info.softex.dictionary.core.processors.api;

/**
 * Ported from wiki-crawler.
 * 
 * @since version 1.0,		03/18/2014
 * 
 * @modified version 2.2,	04/16/2015
 * @modified version 2.3,	04/30/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public interface JobData {
	
	public String getTitle();
	
	public String getContent();
	
	public Object getDataObject();
	
}

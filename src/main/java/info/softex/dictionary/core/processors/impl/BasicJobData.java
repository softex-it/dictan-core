package info.softex.dictionary.core.processors.impl;

import info.softex.dictionary.core.processors.api.JobData;

/**
 * Ported from wiki-crawler.
 * 
 * @since version 1.0,	03/18/2014
 * 
 * @modified version 2.2,	04/16/2015
 * @modified version 2.3,	04/30/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class BasicJobData implements JobData {
	
	private String content = null;
	private String title = null;	

	private Object dataObject = null;
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTitle(String inTitle) {
		this.title = inTitle;
	}
	
	@Override
	public Object getDataObject() {
		return dataObject;
	}
	
	public void setDataObject(Object inDataObject) {
		this.dataObject = inDataObject;
	}

}

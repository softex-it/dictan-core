package info.softex.dictionary.core.processors.api;

import java.util.Map;

/**
 * 
 * @since version 2.1,		01/25/2015
 * 
 * @modified version 2.3,	04/30/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public interface DataInjector {

	public void inject(Map<DataInjector.DataKey, Object> dataMap) throws Exception;
	
	public static enum DataKey {
		FILES_LC_TO_FILES_MAP,
		WORDS_LC_TO_WORDS_MAP,
		
		MATCHING_SET,
		
		// Universal field for any objects
		DATA_OBJECT_1,
		DATA_OBJECT_2
	}
	
}

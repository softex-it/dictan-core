package info.softex.dictionary.core.formats.dsl.utils;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @since version 4.8, 04/30/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLReadFormatUtilsTest {
	
	@SuppressWarnings("serial")
	public static final Map<String, String> DSL_WORDS = new LinkedHashMap<String, String>() {{
		put(null, null);
		put("active {(}duty{)} forces", "active duty forces");
		put("active {(duty) }forces", "active forces");
		put("active {(duty)} forces", "active forces");
		put("at {(smb.'s) }pleasure", "at pleasure");
		put("at {(}smb.'s{)} pleasure", "at smb.'s pleasure");
	}};
	
	@Test
	public void testAddDSLLineBreaks() throws Exception {
		int count = 0;
		for (String key : DSL_WORDS.keySet()) {
			String processed = DSLReadFormatUtils.convertDSLWordToIndexedWord(key);
			assertEquals("Lines at #" + count + " don't match", DSL_WORDS.get(key), processed);
			count++;
		}
	}

}

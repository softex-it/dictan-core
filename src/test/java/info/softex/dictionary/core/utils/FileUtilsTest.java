package info.softex.dictionary.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @since version 4.5, 04/02/2014
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FileUtilsTest {

	private final Map<String, String> title2FileNameMap = new HashMap<String, String>();
	
	private final static String T2F_PATH = "/info/softex/dictionary/core/utils/titles2filenames.txt";
	
	public FileUtilsTest() throws IOException {
		
		URL url = getClass().getResource(T2F_PATH);
		
		assertNotNull("Resource URL is null", url);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(url.getPath()))));
		String line = null;
		int lines = 0;
		while ((line = reader.readLine()) != null){
		    String[] kv = line.split(",");
		    title2FileNameMap.put(kv[0].trim(), kv[1].trim());
		    lines++;
		}
		reader.close();
		
		assertEquals(8, lines);
		
	}
	
	@Test
	public void testTitle2FileName() throws Exception {
		for (Map.Entry<String, String> entry : title2FileNameMap.entrySet()) {
			String value = FileUtils.title2FileName(entry.getKey());
			assertEquals(entry.getValue(), value);
		}
	}
	
	@Test
	public void testFileName2Title() throws Exception {
		for (Map.Entry<String, String> entry : title2FileNameMap.entrySet()) {
			String value = FileUtils.fileName2Title(entry.getValue());
			assertEquals(entry.getKey(), value);
		}
	}

}

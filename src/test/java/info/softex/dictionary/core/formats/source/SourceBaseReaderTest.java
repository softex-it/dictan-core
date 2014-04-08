package info.softex.dictionary.core.formats.source;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import info.softex.dictionary.core.formats.api.BaseReader;

import java.io.File;
import java.net.URL;

import org.junit.Test;

/**
 * 
 * @since version 4.5, 04/05/2014
 * 
 * @author Dmitry Viktorov
 *
 */
public class SourceBaseReaderTest {
	
	private final static String PATH_BASE_SOURCE_FULL = "/info/softex/dictionary/core/formats/source/testbasefull";
	
	@Test
	public void testSourceBaseFull() throws Exception {
		URL url = getClass().getResource(PATH_BASE_SOURCE_FULL);
		BaseReader srcReader = new SourceBaseReader(new File(url.getPath()));
		srcReader.load();
		
		assertNotNull(srcReader.getWords());
		assertEquals(4, srcReader.getWords().size());
		
		assertNotNull(srcReader.getAbbreviationKeys());
		assertEquals(2, srcReader.getAbbreviationKeys().size());
		
		assertNotNull(srcReader.getMediaResourceKeys());
		assertEquals(1, srcReader.getMediaResourceKeys().size());
	}

}

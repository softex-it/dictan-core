package info.softex.dictionary.core.formats.fdb;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.formats.fdb.FDBBaseReader;
import info.softex.dictionary.core.formats.zd.ZDBaseReader;

import org.junit.Test;

/**
 * 
 * @since version 2.6, 09/03/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class FormatInfoTest {

	@Test
	public void testZDFormatInfo() throws ClassNotFoundException {
		
		FormatInfo format = FormatInfo.buildFormatInfoFromAnnotation(ZDBaseReader.class);
		
		assertNotNull(format); 
		assertEquals(format.getName(), "ZD");
		assertEquals(format.getPrimaryExtension(), ".zd");
		
		assertArrayEquals(format.getExtensions().toArray(), new String [] {".zd"});
		
	}
	
	@Test
	public void testFDBFormatInfo() throws ClassNotFoundException {
		
		FormatInfo format = FormatInfo.buildFormatInfoFromAnnotation(FDBBaseReader.class);
		
		assertNotNull(format);
		assertEquals(format.getName(), "FDB");
		assertEquals(format.getPrimaryExtension(), ".fdb");
		
		assertArrayEquals(format.getExtensions().toArray(), new String [] {".fdb"});
		
	}
	
}

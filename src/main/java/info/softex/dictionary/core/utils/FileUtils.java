package info.softex.dictionary.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @since version 4.5, 03/30/2014
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FileUtils {
	
	protected static final String UTF8 = "UTF-8";
	
	public final static String EXT_HTML = ".html";
	public final static String EXT_HTM = ".htm";
	
	@SuppressWarnings("serial")
	private final static Map<String, String> FILE_NAME_REPLACEMENTS = new HashMap<String, String>() {{
		put("\"", "&#34;");
		put("\\*", "&#42;");
		put("/", "&#47;");
		put(":", "&#58;");
		put("<", "&#60;");
		put(">", "&#62;");
		put("\\?", "&#63;");
		put("\\\\", "&#92;");
		put("\\|", "&#124;");
	}};
	
	public static File string2File(String filePath, String content) throws IOException {
		File outFile = new File(filePath);
		Writer writer = createWriter(outFile);
		writer.write(content);
		writer.flush();
		writer.close();
		return outFile;
	}
	
	public static String file2String(File file) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public static BufferedWriter createWriter(File file) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF8));
	}
	
	public static String title2FileName(String title) {
		for (Map.Entry<String, String> repEntry : FILE_NAME_REPLACEMENTS.entrySet()) {
			title = title.replaceAll(repEntry.getKey(), repEntry.getValue());
		}
		return title.trim() + EXT_HTML;
	}
	
	public static String fileName2Title(String fileName) {
		for (Map.Entry<String, String> repEntry : FILE_NAME_REPLACEMENTS.entrySet()) {
			fileName = fileName.replaceAll(repEntry.getValue(), repEntry.getKey());
		}
		String lcFileName = fileName.toLowerCase();
		if (lcFileName.endsWith(EXT_HTML)) {
			return fileName.substring(0, fileName.length() - EXT_HTML.length()).trim();
		} else if (lcFileName.endsWith(EXT_HTM)) {
			return fileName.substring(0, fileName.length() - EXT_HTM.length()).trim();
		} else {
			return fileName.trim();
		}
	}

}

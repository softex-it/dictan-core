package info.softex.dictionary.core.formats.source.utils;

/**
 * 
 * @since version 4.2, 04/04/2014
 * 
 * @author Dmitry Viktorov
 *
 */
public class SourceFormatUtils {
	
	public static String removeLineBreaks(String text) {
		return text.replaceAll("(\\r|\\n)", " ").trim();
	}

}

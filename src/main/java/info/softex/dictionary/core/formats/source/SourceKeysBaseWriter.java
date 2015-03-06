package info.softex.dictionary.core.formats.source;

import info.softex.dictionary.core.annotations.BaseFormat;
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 
 * @since version 4.4, 03/17/2014
 *
 * @author Dmitry Viktorov
 *
 */
@BaseFormat(name = "SOURCE_KEYS", primaryExtension = "", extensions = {}, sortingExpected = false, hasKeysOnly = true)
public class SourceKeysBaseWriter extends SourceBaseWriter {
	
	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(SourceKeysBaseWriter.class);

	protected Writer mediaWriter;

	public SourceKeysBaseWriter(File outDirectory) throws IOException {
		super(outDirectory);
	}
	
	@Override
	public void saveRawArticleInfo(ArticleInfo articleInfo) throws Exception {
		saveArticleLine(articleInfo.getWordInfo().getWord() + "\r\n");
	}
	
	@Override
	public void saveAbbreviationInfo(AbbreviationInfo abbreviationInfo) throws Exception {
		saveAbbreviationLine(abbreviationInfo.getAbbreviation() + "\r\n");
	}
	
	@Override
	public void saveMediaResourceInfo(MediaResourceInfo mediaResourceInfo) throws Exception {
		saveMediaKeyLine(mediaResourceInfo.getKey().getResourceKey() + "\r\n");
	}
	
	@Override
	protected void createWriters() throws Exception {
		artWriter = createWriter(SourceFileNames.FILE_ARTICLES_KEYS);
		abbWriter = createWriter(SourceFileNames.FILE_ABBREVIATIONS_KEYS);
		debugWriter = createWriter(SourceFileNames.FILE_DEBUG);
		mediaWriter = createWriter(SourceFileNames.FILE_MEDIA_KEYS);
	}
	
	protected void saveMediaKeyLine(String inAbbrevLine) throws Exception {
		mediaWriter.write(inAbbrevLine);
	    mediaResourcesNumber++;
	    updateProgress();
	}
	
	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}

}

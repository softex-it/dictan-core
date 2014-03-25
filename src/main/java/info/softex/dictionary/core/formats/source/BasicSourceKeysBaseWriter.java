package info.softex.dictionary.core.formats.source;

import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
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
public class BasicSourceKeysBaseWriter extends BasicSourceBaseWriter {
	
	protected Writer mediaWriter;

	public BasicSourceKeysBaseWriter(File outDirectory) throws IOException {
		super(outDirectory);
	}
	
	@Override
	public void saveArticleInfo(ArticleInfo articleInfo) throws Exception {
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
		artWriter = createWriter(BasicSourceFileNames.FILE_ARTICLES_KEYS);
		abbWriter = createWriter(BasicSourceFileNames.FILE_ABBREVIATIONS_KEYS);
		debugWriter = createWriter(BasicSourceFileNames.FILE_DEBUG);
		mediaWriter = createWriter(BasicSourceFileNames.FILE_MEDIA_KEYS);
	}
	
	protected void saveMediaKeyLine(String inAbbrevLine) throws Exception {
		mediaWriter.write(inAbbrevLine);
	    mediaResourcesNumber++;
	    updateProgress();
	}

}

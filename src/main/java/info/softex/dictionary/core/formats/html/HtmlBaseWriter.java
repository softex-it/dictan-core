package info.softex.dictionary.core.formats.html;

import info.softex.dictionary.core.annotations.BaseFormat;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.formats.source.SourceBaseWriter;
import info.softex.dictionary.core.formats.source.SourceFileNames;
import info.softex.dictionary.core.utils.FileConversionUtils;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @since version 4.5, 03/30/2014
 * 
 * @author Dmitry Viktorov
 * 
 */
@BaseFormat(name = "HTML", primaryExtension = "", extensions = {})
public class HtmlBaseWriter extends SourceBaseWriter {
	
	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(HtmlBaseWriter.class);

	private final String outHtmlPath;
	
	public HtmlBaseWriter(File inOutPathFile) throws IOException {
		super(inOutPathFile);
		this.outHtmlPath = inOutPathFile.getAbsolutePath() + File.separator + SourceFileNames.DIRECTORY_ARTICLES_HTML;
		new File(this.outHtmlPath).mkdirs();
	}
	
	@Override
	public void saveArticleInfo(ArticleInfo articleInfo) throws Exception {
		String fileName = FileConversionUtils.title2FileName(articleInfo.getKey());
		FileConversionUtils.string2File(
			outHtmlPath + File.separator + fileName, 
			articleInfo.getArticle()
		);
		articleNumber++;
		updateProgress();
	}
	
	@Override
	protected void createWriters() throws Exception {
		abbWriter = createWriter(SourceFileNames.FILE_ABBREVIATIONS);
		debugWriter = createWriter(SourceFileNames.FILE_DEBUG);
	}
	
	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}

}

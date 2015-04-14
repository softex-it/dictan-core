/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2015  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
 *	
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License (LGPL) as 
 *  published by the Free Software Foundation, either version 3 of the License, 
 *  or any later version.
 *	
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *	
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
 * @since version 4.5,		03/30/2014
 * 
 * @modified version 4.7,	03/23/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
@BaseFormat(name = "HTML", primaryExtension = "", extensions = {}, sortingExpected = false)
public class HtmlBaseWriter extends SourceBaseWriter {
	
	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(HtmlBaseWriter.class);

	private final String outHtmlPath;
	
	public HtmlBaseWriter(File inOutPathFile) throws IOException {
		super(inOutPathFile);
		this.outHtmlPath = inOutPathFile.getAbsolutePath() + File.separator + SourceFileNames.DIRECTORY_ARTICLES_HTML;
		new File(this.outHtmlPath).mkdirs();
	}
	
	@Override
	public void saveRawArticleInfo(ArticleInfo articleInfo) throws Exception {
		String fileName = FileConversionUtils.title2FileName(articleInfo.getKey());
		FileConversionUtils.string2File(
			outHtmlPath + File.separator + fileName, 
			articleInfo.getArticle()
		);
		articleNumber++;
		updateProgress();
	}
	
	@Override
	protected void createWriters() throws IOException {
		abbWriter = createWriter(SourceFileNames.FILE_ABBREVIATIONS);
		debugWriter = createWriter(SourceFileNames.FILE_DEBUG);
	}
	
	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}

}

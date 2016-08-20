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
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.source.SourceBaseReader;
import info.softex.dictionary.core.formats.source.SourceFileNames;
import info.softex.dictionary.core.formats.source.utils.SourceFormatUtils;
import info.softex.dictionary.core.utils.FileConversionUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.5,		04/02/2014
 * 
 * @modified version 4.7,	03/23/2015
 * @modified version 4.9,   12/08/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
@BaseFormat(name = "HTML", primaryExtension = "", extensions = {}, sortingExpected = false)
public class HtmlBaseReader extends SourceBaseReader {
	
	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(HtmlBaseReader.class);
	
	private static final Logger log = LoggerFactory.getLogger(HtmlBaseReader.class);
	
	private final String sourceHtmlPath;
	
	public HtmlBaseReader(File inSourceDirectory) throws IOException {
		super(inSourceDirectory);
		this.sourceHtmlPath = inSourceDirectory.getAbsolutePath() + File.separator + SourceFileNames.DIRECTORY_ARTICLES_HTML;
	}
	
	@Override
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		int wid = wordInfo.getId();
		File file = new File(sourceHtmlPath + File.separator + FileConversionUtils.title2FileName(words.get(wid)));
		try {
			ArticleInfo articleInfo = new ArticleInfo(wordInfo, null);
			String article = SourceFormatUtils.removeLineBreaks(FileConversionUtils.file2String(file));
			articleInfo.setArticle(article);
			articleInfo.setBaseInfo(getBasePropertiesInfo());
			return articleInfo;
		} catch (IOException e) {
			log.error("Error", e);
			throw new BaseFormatException(e.getMessage());
		}
	}
	
	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}
	
	@Override
	protected List<String> loadWords() throws BaseFormatException {
		File[] htmlFiles = new File(sourceHtmlPath).listFiles(new HtmlFileFilter());
		ArrayList<String> resultWords = new ArrayList<String>(htmlFiles.length);
		for (int i = 0; i < htmlFiles.length; i++) {
			resultWords.add(FileConversionUtils.fileName2Title(htmlFiles[i].getName()));
		}
		return resultWords;
	}
	
	private class HtmlFileFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			String lcPath = file.getPath().toLowerCase();
			return file.isFile() && (lcPath.endsWith(".html") || lcPath.endsWith(".htm"));
		}
	}
	
}

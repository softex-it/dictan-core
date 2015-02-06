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

package info.softex.dictionary.core.formats.dsl;

import info.softex.dictionary.core.annotations.BaseFormat;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.dsl.utils.DSLFormatUtils;
import info.softex.dictionary.core.formats.source.SourceBaseReader;
import info.softex.dictionary.core.formats.source.SourceFileNames;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The DSL Base Reader enables reading the DSL (Dictionary Specification Language)
 * format developed by ABBYY and mainly used at Lingvo application.
 * 
 * @since version 4.6, 01/26/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
@BaseFormat(name = "DSL", primaryExtension = ".dsl", extensions = {".dsl"})
public class DSLBaseReader extends SourceBaseReader {
	
	public final static FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(DSLBaseReader.class);

	protected final List<String> headers = new ArrayList<String>();
	
	protected TreeMap<Integer, Integer> wordRedirects;
	
	protected DSLFileReader dslArticleReader;
	protected DSLFileReader dslAbbrevReader;
	
	public DSLBaseReader(File inSourceDirectory) throws IOException {
		super(inSourceDirectory);
	}

	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}
	
	@Override
	public Map<Integer, Integer> getWordRedirects() throws BaseFormatException {
		return wordRedirects;
	}
	
	@Override
	protected List<String> loadWords() throws BaseFormatException, Exception {
		File articleFile = new File(sourceDirectory + File.separator + SourceFileNames.FILE_ARTICLES_DSL);
		dslArticleReader = new DSLFileReader(articleFile, BUF_SIZE_ARTICLES);
		dslArticleReader.load(false);
		
		// Get headers
		List<String> headers = dslArticleReader.getHeaders();
		if (headers != null && !headers.isEmpty()) {
			String strHeaders = "";
			for (String head : headers) {
				strHeaders += head.trim() + "\r\n";
			}
			baseInfo.setHeaderComments(strHeaders.trim());
		}
		
		wordRedirects = dslArticleReader.getWordRedirects();
		
		return dslArticleReader.getLineKeys();
	}
	
	@Override
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		ArticleInfo articleInfo = new ArticleInfo(wordInfo, null);
		if (dslArticleReader.readArticleInfo(articleInfo)) {
			return articleInfo;
		}
		return null;
	}
	
	@Override
	public ArticleInfo getAdaptedArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		ArticleInfo articleInfo = getRawArticleInfo(wordInfo);
		if (articleInfo != null) {
			String adaptedArticle = DSLFormatUtils.convertDSLToAdaptedHtml(articleInfo.getArticle());
			articleInfo.setArticle(adaptedArticle);
		}
		return articleInfo;
	}
	
	@Override
	public void close() throws Exception {
		super.close();
		if (dslArticleReader != null) {
			dslArticleReader.close();
		}
		if (dslAbbrevReader != null) {
			dslAbbrevReader.close();
		}
	}

}

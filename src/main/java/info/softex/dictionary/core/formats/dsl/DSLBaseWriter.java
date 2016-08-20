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
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.BaseResourceKey;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.dsl.utils.DSLWriteFormatUtils;
import info.softex.dictionary.core.formats.source.SourceBaseWriter;
import info.softex.dictionary.core.formats.source.SourceFileNames;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DSL Base Writer enables writing the DSL (Dictionary Specification Language)
 * format developed by ABBYY and mainly used at Lingvo application.
 * 
 * @since version 4.6,		02/28/2015
 * 
 * @modified version 4.7,	03/23/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
@BaseFormat(name = "DSL", primaryExtension = ".dsl", extensions = {".dsl"}, sortingExpected = false)
public class DSLBaseWriter extends SourceBaseWriter {

	private final static Logger log = LoggerFactory.getLogger(DSLBaseWriter.class);

	public final static FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(DSLBaseWriter.class);

	protected DSLBaseWriteUnit dslArticleWriter;
	protected DSLBaseWriteUnit dslAbbrevWriter;
	
	public DSLBaseWriter(File inOutDirectory) throws IOException {
		super(inOutDirectory);
	}
	
	@Override
	protected void createWriters() throws IOException {
		dslArticleWriter = new DSLBaseWriteUnit(outDirectory.getPath(), SourceFileNames.FILE_DSL_ARTICLES_NO_EXT);
		dslAbbrevWriter = new DSLBaseWriteUnit(outDirectory.getPath(), SourceFileNames.FILE_DSL_ABBREVIATIONS_NO_EXT);
	}

	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}

	@Override
	public void saveRawArticleInfo(ArticleInfo articleInfo) throws IOException {
		WordInfo wordInfo = articleInfo.getWordInfo();
		dslArticleWriter.saveEntry(wordInfo.getWord(), wordInfo.getWordMapping(), wordInfo.getRedirectToId(), articleInfo.getArticle());
	}
	
	@Override
	public void saveAdaptedArticleInfo(ArticleInfo articleInfo) throws IOException {
		
		// Convert article
		ArticleInfo dslArticleInfo = articleInfo.clone();
		String dslArticle = DSLWriteFormatUtils.convertAdaptedHtmlToDSL(articleInfo.getArticle());
		dslArticleInfo.setArticle(dslArticle);
		
		// Convert word mapping
		String wordMapping = articleInfo.getWordInfo().getWordMapping();
		if (wordMapping != null) {
			WordInfo dslWordInfo = articleInfo.getWordInfo().clone();
			String dslWordMapping = DSLWriteFormatUtils.convertAdaptedHtmlToDSLDesignTags(wordMapping);
			dslWordInfo.setWordMapping(dslWordMapping);
			dslArticleInfo.setWordInfo(dslWordInfo);
		}
		
		saveRawArticleInfo(dslArticleInfo);
	}
	
	@Override
	public void saveAbbreviationInfo(AbbreviationInfo abbreviationInfo) throws Exception {
		dslAbbrevWriter.saveEntry(abbreviationInfo.getAbbreviation(), null, -1, abbreviationInfo.getDefinition());
	}
	
	@Override
	public void saveBaseResourceInfo(BaseResourceInfo baseResourceInfo) throws IOException {

		BaseResourceKey brk = BaseResourceKey.resolveKey(baseResourceInfo.getResourceKey());
		if (brk == null) {
			log.info("Couldn't resolve the resource key: {}", baseResourceInfo.getResourceKey());
			return;
		}
		
		switch (brk) {
		
			case BASE_ARTICLES_META_DSL:
				dslArticleWriter.saveDSLIcon(baseResourceInfo.getByteArray());
				dslArticleWriter.saveDSLHeaders(baseResourceInfo.getInfo1());
				dslArticleWriter.saveDSLDescription(baseResourceInfo.getInfo2());				
			break;

			case BASE_ABBREVIATIONS_META_DSL:
				dslAbbrevWriter.saveDSLIcon(baseResourceInfo.getByteArray());
				dslAbbrevWriter.saveDSLHeaders(baseResourceInfo.getInfo1());
				dslAbbrevWriter.saveDSLDescription(baseResourceInfo.getInfo2());	
			break;
				
			default:
		}
		
	}
	
	@Override
	public void close() throws IOException {
		
		if (!isClosed) {
			if (dslArticleWriter != null) {
				dslArticleWriter.close();
			}
			if (dslAbbrevWriter != null) {
				dslAbbrevWriter .close();
			}			
		}
		
		super.close();

	}

}

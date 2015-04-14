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
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.BaseResourceKey;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.WordInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.dsl.utils.DSLReadFormatUtils;
import info.softex.dictionary.core.formats.source.SourceBaseReader;
import info.softex.dictionary.core.formats.source.SourceFileNames;
import info.softex.dictionary.core.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DSL Base Reader enables reading the DSL (Dictionary Specification Language)
 * format developed by ABBYY and mainly used at Lingvo application.
 * 
 * @since version 4.6,		01/26/2015
 * 
 * @modified version 4.7,	03/23/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
@BaseFormat(name = "DSL", primaryExtension = ".dsl", extensions = {".dsl"}, sortingExpected = false)
public class DSLBaseReader extends SourceBaseReader {
	
	public final static FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(DSLBaseReader.class);
	
	private final static Logger log = LoggerFactory.getLogger(DSLBaseReader.class);

	protected final List<String> headers = new ArrayList<String>();
	
	protected final TreeMap<Integer, String> adaptedWordsMappings = new TreeMap<Integer, String>();
	
	protected DSLBaseReadUnit dslArticleReader;
	protected DSLBaseReadUnit dslAbbrevReader;
	
	public DSLBaseReader(File inSourceDirectory) throws IOException {
		super(inSourceDirectory);
	}

	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}
	
	@Override
	public Map<Integer, Integer> getWordsRedirects() {
		return dslArticleReader.getWordRedirects();
	}
	
	@Override
	public Map<Integer, String> getWordsMappings() {
		return dslArticleReader.getWordsMappings();
	}
	
	@Override
	public Map<Integer, String> getAdaptedWordsMappings() throws BaseFormatException {
		return adaptedWordsMappings;
	}
	
	@Override
	protected List<String> loadWords() throws IOException, BaseFormatException {
		
		// Load articles
		dslArticleReader = new DSLBaseReadUnit(sourceDirectory.getAbsolutePath(), SourceFileNames.FILE_DSL_ARTICLES_NO_EXT);
		dslArticleReader.load(false);
		
		// Convert mappings to adapted mappings
		Map<Integer, String> mappings = getWordsMappings();
		if (!mappings.isEmpty()) {
			for (Integer wordId: mappings.keySet()) {
				adaptedWordsMappings.put(
					wordId, 
					DSLReadFormatUtils.convertDSLDesignTagsToAdaptedHtml(mappings.get(wordId))
				);
			}
		}
		
		return dslArticleReader.getWords();
	}
	
	@Override
	protected Set<String> loadAbbreviations() throws IOException, BaseFormatException {
		Set<String> abbKeys = new LinkedHashSet<String>();
		
		File abbrevFile = new File(sourceDirectory.getAbsolutePath() + File.separator + 
			SourceFileNames.FILE_DSL_ABBREVIATIONS_NO_EXT + SourceFileNames.FILE_DSL_EXT_MAIN);
		
		if (abbrevFile.exists() && abbrevFile.isFile()) {
			dslAbbrevReader = new DSLBaseReadUnit(sourceDirectory.getAbsolutePath(), SourceFileNames.FILE_DSL_ABBREVIATIONS_NO_EXT);
			dslAbbrevReader.load(true);
			abbKeys = dslAbbrevReader.getLineMapper().keySet();
		} else {
			dslAbbrevReader = null;
		}
		return abbKeys;
	}
	
	@Override
	public AbbreviationInfo getAbbreviationInfo(String abbreviationKey) throws BaseFormatException {		
		AbbreviationInfo abbrevInfo = new AbbreviationInfo(abbreviationKey, null);
		if (dslAbbrevReader != null && dslAbbrevReader.readEntry(abbrevInfo, abbreviationKey)) {
			return abbrevInfo;
		}
		return null;
	}
	
	@Override
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		ArticleInfo articleInfo = new ArticleInfo(wordInfo, null);
		int wordId = wordInfo.getId();
		if (dslArticleReader.readEntry(articleInfo, wordId)) {
			
			// Set word redirect
			Integer redirecToId = getWordsRedirects().get(wordId);
			if (redirecToId != null && redirecToId >=0) {
				wordInfo.setRedirectToId(redirecToId);
				wordInfo.setRedirectToWord(words.get(redirecToId));
			}
			
			// Set word mapping
			String wordMapping = getWordsMappings().get(wordId);
			if (StringUtils.isNotBlank(wordMapping)) {
				wordInfo.setWordMapping(wordMapping);
			}
			
			return articleInfo;
		}
		return null;
	}
	
	@Override
	public ArticleInfo getAdaptedArticleInfo(WordInfo wordInfo) throws BaseFormatException {
		ArticleInfo articleInfo = getRawArticleInfo(wordInfo);
		if (articleInfo != null) {
			
			// Convert word mapping to adapted HTML
			String adaptedMapping = adaptedWordsMappings.get(wordInfo.getId());
			if (StringUtils.isNotBlank(adaptedMapping)) {
				wordInfo.setWordMapping(adaptedMapping);
			}
			
			String adaptedArticle = DSLReadFormatUtils.convertDSLToAdaptedHtml(articleInfo.getArticle());
			articleInfo.setArticle(adaptedArticle);
		}
		return articleInfo;
	}
	
	@Override
	public BaseResourceInfo getBaseResourceInfo(String resourceKey) throws BaseFormatException {
		
		BaseResourceKey brk = BaseResourceKey.resolveKey(resourceKey);
		if (brk == null) {
			return null;
		}
		
		BaseResourceInfo resInfo = null;
		
		switch (brk) {
			case BASE_ARTICLES_META_DSL:
				try {
					resInfo = dslArticleReader.getDSLMetaBaseResourceInfo(brk.getKey());
				} catch (IOException e) {
					log.error("Error", e);
					throw new BaseFormatException("Couldn'r read base resource: " + brk);
				}
			break;

			case BASE_ABBREVIATIONS_META_DSL:
				if (dslAbbrevReader != null) {
					try {
						resInfo = dslAbbrevReader.getDSLMetaBaseResourceInfo(brk.getKey());
					} catch (IOException e) {
						log.error("Error", e);
						throw new BaseFormatException("Couldn'r read base resource: " + brk);
					}
				}
			break;
				
			default:
				return null;
		}
		
		return resInfo;

	}
	
	@Override
	public void close() throws IOException {
		super.close();
		if (dslArticleReader != null) {
			dslArticleReader.close();
		}
		if (dslAbbrevReader != null) {
			dslAbbrevReader.close();
		}
	}
	
	@Override
	public BasePropertiesInfo loadBasePropertiesInfo() throws IOException, BaseFormatException {
		super.loadBasePropertiesInfo();
		baseInfo.setArticlesActualNumber(words.size() - getWordsRedirects().size());
		baseInfo.setWordsMappingsNumber(getWordsMappings().size());
		baseInfo.setWordsRelationsNumber(getWordsRedirects().size());
		return baseInfo;
	}
	
}

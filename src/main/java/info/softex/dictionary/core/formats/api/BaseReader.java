/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2018  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.formats.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.IntegrityInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.MediaResourceKey;
import info.softex.dictionary.core.attributes.WordInfo;

/**
 * 
 * @since       version 1.0, 09/23/2010
 * 
 * @modified    version 2.0, 03/10/2011
 * @modified    version 2.5, 07/13/2011
 * @modified    version 2.6, 08/21/2011
 * @modified    version 4.2, 03/06/2014
 * @modified    version 4.6, 02/01/2015
 * @modified    version 4.7, 03/26/2015
 * @modified    version 5.1, 02/20/2017
 * @modified    version 5.2, 10/28/2018
 *
 * @author Dmitry Viktorov
 * 
 */
public interface BaseReader extends AutoCloseable {
	
	public static final String UTF8 = "UTF-8";

    /**
     * Represents base location which can base a path or a URI.
     */
    public String getBaseLocation();

    /**
     * Unique base identifier that should be available before the base is initialized.
     * In most cases base location can be used for the its generation.
     * The same base in different locations is expected to have different identifiers.
     *
     * @return true unique base identifier.
     */
    public String getBaseLocationUid();

	/**
	 * If reader is closed, sequential attempts to close it should not raise exceptions.
	 */
	@Override
	public void close() throws Exception;

    /**
     * Shows if base is loaded or not.
     *
     * @return true if base is closed, false otherwise.
     */
    public boolean isClosed();
	
	/**
	 * Loads the whole base. 
	 * loadDictionaryInfo() and loadLanguageDirections() should be called within this method.
	 * 
	 * @throws BaseFormatException
	 * @throws Exception
	 */
	public void load() throws BaseFormatException, Exception;

    /**
     * Shows if base is loaded or not.
     *
     * @return true if base is loaded, false otherwise.
     */
    public boolean isLoaded();
	
	/**
	 * Loads only the dictionary info.
	 * 
	 * @return
	 * @throws BaseFormatException
	 * @throws Exception
	 */
	public BasePropertiesInfo loadBaseInfo() throws BaseFormatException, Exception;
	
	/**
	 * Loads only the supported language directions.
	 * 
	 * @return
	 * @throws BaseFormatException
	 * @throws Exception
	 */
	public LanguageDirectionsInfo loadLanguageDirectionsInfo() throws BaseFormatException, Exception;
	
	public BasePropertiesInfo getBaseInfo();
	public FormatInfo getFormatInfo();
	public LanguageDirectionsInfo getLanguageDirectionsInfo();

    /**
     * Returns the result of a set of integrity tests. Should return null if integrity check is not
     * supported.
     */
	public IntegrityInfo getIntegrityInfo();
	
	public BaseResourceInfo getBaseResourceInfo(String resourceKey) throws BaseFormatException;
	
	public Set<String> getMediaResourceKeys() throws BaseFormatException;
	public MediaResourceInfo getMediaResourceInfo(MediaResourceKey mediaKey) throws BaseFormatException;
	
	public Set<String> getAbbreviationKeys() throws BaseFormatException;
	public AbbreviationInfo getAbbreviationInfo(String abbreviationKey) throws BaseFormatException;

	/**
	 * Search the word index. The result must be returned even if the word is not found.
	 * In this case the index denotes the item where the word would be placed.
	 * 
	 * @param word - word to be searched
	 * @param positive - If true, only positive index is returned regardless if the word is found or not. 
	 * 					 If false, a negative one is returned if the word is not found.
	 * @return -
	 * 			The word id if the word is found, or the id where the word would be placed if is not.
	 * 
	 * @throws BaseFormatException
	 */
	public int searchWordIndex(String word, boolean positive) throws BaseFormatException;
	
	public List<String> getWords();

    /**
     * Returns words based on SQL 'like' query.
     */
	public Map<Integer, String> getWordsLike(String rootWord, int limit) throws BaseFormatException;
	
	public Map<Integer, String> getWordsMappings() throws BaseFormatException;
	public Map<Integer, String> getAdaptedWordsMappings() throws BaseFormatException;	
	public Map<Integer, Integer> getWordsRedirects() throws BaseFormatException;	
	
	/**
	 * Returns the full formatted article
	 */
	public ArticleInfo getArticleInfo(WordInfo wordInfo) throws BaseFormatException;
	
	/**
	 * Returns the article with the basic set of rules applied. The method is mainly 
	 * needed to speed up rendering, e.g. it can transfer a specific formatting to HTML.
	 * If no adaptation should be done, the method should simply reader to
	 * <code>getRawArticleInfo</code>. 
	 * 
	 * If the method is defined, the writer is supposed to have the rules to convert 
	 * the article back to its raw view. Otherwise the original markup can be lost.
	 */
	public ArticleInfo getAdaptedArticleInfo(WordInfo wordInfo) throws BaseFormatException;
	
	/**
	 * Returns the raw article as is, w/o any explicit formatting
	 */
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException;
	
}

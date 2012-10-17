/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2012  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.formats.commons;

import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.WordInfo;

import java.util.List;
import java.util.Set;

/**
 * 
 * @since version 1.0, 09/23/2010
 * 
 * @modified version 2.0, 03/10/2011
 * @modified version 2.5, 07/13/2011
 * @modified version 2.6, 08/21/2011
 *  
 * @author Dmitry Viktorov
 * 
 */
public interface BaseReader {
	
	public void close() throws Exception;
	
	/**
	 * Loads the whole base. 
	 * loadDictionaryInfo() and loadLanguageDirections() should be called within this method.
	 * 
	 * @throws BaseFormatException
	 * @throws Exception
	 */
	public void load() throws BaseFormatException, Exception;
	
	/**
	 * Loads only the dictionary info.
	 * 
	 * @return
	 * @throws BaseFormatException
	 * @throws Exception
	 */
	public BasePropertiesInfo loadBasePropertiesInfo() throws BaseFormatException, Exception;
	
	/**
	 * Loads only the supported language directions.
	 * 
	 * @return
	 * @throws BaseFormatException
	 * @throws Exception
	 */
	public LanguageDirectionsInfo loadLanguageDirectionsInfo() throws BaseFormatException, Exception;
	
	public boolean isLoaded();
	
	public BasePropertiesInfo getBasePropertiesInfo();
	public FormatInfo getFormatInfo();
	public LanguageDirectionsInfo getLanguageDirectionsInfo();
	
	public Set<String> getMediaResourceKeys() throws BaseFormatException;
	public MediaResourceInfo getMediaResourceInfo(MediaResourceInfo.Key mediaKey) throws BaseFormatException;
	
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
	
//	/**
//	 * Get the word index. Doesn't work for capital/low
//	 * 
//	 * @param word
//	 * @return Word index if it is found, otherwise -1.
//	 * @throws BaseFormatException
//	 */
//	public int getWordIndex(String word) throws BaseFormatException;
	
	public List<String> getWords() throws BaseFormatException;
	public ArticleInfo getArticleInfo(WordInfo wordInfo) throws BaseFormatException;
	public ArticleInfo getRawArticleInfo(WordInfo wordInfo) throws BaseFormatException;
	
}

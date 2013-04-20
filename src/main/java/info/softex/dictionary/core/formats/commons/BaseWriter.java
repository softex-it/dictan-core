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

import java.util.Observer;

import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;

/**
 * 
 * @since version 2.6, 08/21/2011
 *  
 * @author Dmitry Viktorov
 * 
 */
public interface BaseWriter {
	
	public void createBase(String... params) throws Exception;
	
	public BasePropertiesInfo saveBasePropertiesInfo(BasePropertiesInfo basePropertiesInfo) throws Exception;
	public BasePropertiesInfo getBasePropertiesInfo();

	public LanguageDirectionsInfo saveLanguageDirectionsInfo(LanguageDirectionsInfo languageDirectionsInfo) throws Exception;
	public LanguageDirectionsInfo getLanguageDirectionsInfo();
	
	public void saveArticleInfo(ArticleInfo articleInfo) throws Exception;
	public void saveAbbreviation(AbbreviationInfo abbreviationInfo) throws Exception;
	public void saveBaseResourceInfo(BaseResourceInfo baseResourceInfo) throws Exception;
	public void saveMediaResourceInfo(MediaResourceInfo mediaResourceInfo) throws Exception;
	
	public void flush() throws Exception;
	public void close() throws Exception;
	
	public FormatInfo getFormatInfo();
	
	public void addObserver(Observer observer);

}

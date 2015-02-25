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
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.formats.api.BaseWriter;
import info.softex.dictionary.core.formats.fdb.FDBBaseWriter;

import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DSL Base Writer enables writing the DSL (Dictionary Specification Language)
 * format developed by ABBYY and mainly used at Lingvo application.
 * 
 * @since version 4.6, 02/21/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
@BaseFormat(name = "DSL", primaryExtension = ".dsl", extensions = {".dsl"})
public class DSLBaseWriter implements BaseWriter {
	
	private static final Logger log = LoggerFactory.getLogger(DSLBaseWriter.class);

	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(FDBBaseWriter.class);

	@Override
	public void createBase(String... params) throws Exception {
		
	}

	@Override
	public BasePropertiesInfo saveBasePropertiesInfo(BasePropertiesInfo basePropertiesInfo) throws Exception {
		return null;
	}

	@Override
	public BasePropertiesInfo getBasePropertiesInfo() {
		return null;
	}

	@Override
	public LanguageDirectionsInfo saveLanguageDirectionsInfo(LanguageDirectionsInfo languageDirectionsInfo) throws Exception {
		return null;
	}

	@Override
	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return null;
	}

	@Override
	public void saveArticleInfo(ArticleInfo articleInfo) throws Exception {
		
	}

	@Override
	public void saveAbbreviationInfo(AbbreviationInfo abbreviationInfo) throws Exception {
		
	}

	@Override
	public void saveBaseResourceInfo(BaseResourceInfo baseResourceInfo) throws Exception {
		
	}

	@Override
	public void saveMediaResourceInfo(MediaResourceInfo mediaResourceInfo) throws Exception {
		
	}

	@Override
	public void flush() throws Exception {
		
	}

	@Override
	public void close() throws Exception {
		
	}

	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}

	@Override
	public void addObserver(Observer observer) {
		
	}

}

/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2011  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.regional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 1.0, 10/26/2010 
 * 
 * @modified version 1.7, 01/25/2011 
 * @modified version 2.0, 03/19/2011 
 * @modified version 2.5, 08/02/2011 
 * 
 * @author Dmitry Viktorov
 *
 */
public class BasicDictionaryRegionalResolver implements DictionaryRegionalResolver {
	
	private static final String PATH_RESOURCES = "info/softex/dictionary/core/regional";
	
	private final Logger log = LoggerFactory.getLogger(BasicDictionaryRegionalResolver.class.getSimpleName());
	
	protected final Map<Integer, String> codepages;
	
	protected final Map<Integer, String> languages;
	
	public BasicDictionaryRegionalResolver() {
		this(
				BasicDictionaryRegionalResolver.class.getClassLoader().
					getResourceAsStream(PATH_RESOURCES + "/codepages.list"),
					
				BasicDictionaryRegionalResolver.class.getClassLoader().
					getResourceAsStream(PATH_RESOURCES + "/locales.list")	
			);
	}
	
	public BasicDictionaryRegionalResolver(InputStream isCodepages, InputStream isLocales) {
		
		this.codepages = new HashMap<Integer, String>();
		if (isCodepages == null) {
			throw new ExceptionInInitializerError("could not find codepages.list");
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(isCodepages));
			Pattern p = Pattern.compile("^(\\d+)\\s+(\\S+)");
			Matcher m = null;
			String line;
			while ((line = reader.readLine()) != null) {
				if (m == null) {
					m = p.matcher(line);
				} else {
					m.reset(line);
				}
				if (!m.find()) {
					throw new ExceptionInInitializerError("Could not parse line '" + line + "'");
				}
				String name = m.group(2);
				this.codepages.put(new Integer(m.group(1)), name);
			}
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
		
		try {
			isCodepages.close();
		} catch (Exception e) {
		}

		this.languages = new HashMap<Integer, String>();
		loadLanguages(isLocales, this.languages);
		
	}
	
	protected static void loadLanguages(InputStream is, Map<Integer, String> map) {
		if (is == null) {
			throw new ExceptionInInitializerError("Could not find codepages.list");
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			Pattern p = Pattern.compile("^0x\\w{2}(\\w{2})\\s+(\\S+)");
			Matcher m = null;
			String line;
			while ((line = reader.readLine()) != null) {
				if (m == null) {
					m = p.matcher(line);
				} else {
					m.reset(line);
				}
				if (!m.find()) {
					throw new ExceptionInInitializerError("Could not parse line '" + line + "'");
				}
				
				Integer langId = Integer.parseInt(m.group(1), 16);
				map.put(langId, m.group(2));
			}
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
		
		try {
			is.close();
		} catch (Exception e) {
		}
	}

	public Locale getLanguageLocale(int langId) {
		
		String localeString = this.languages.get(langId);
		Locale locale;
		
		if (localeString != null) {
			String[] lcs = localeString.split("-");
			locale = new Locale(lcs[0]);
			log.info("Locale: " + locale);
		} else {
			locale = Locale.ENGLISH;
			log.info("Locale " + langId + " is not found, using the default one: " + locale);
		}
		return locale;
	}	
	
	public Charset toCharset(int codepage) {
		String name = (String)this.codepages.get(Integer.valueOf(codepage));
		if (name == null) {
			throw new UnsupportedCharsetException("Codepage is not found: " + codepage);
		} else {
			return Charset.forName(name);
		}
	}

	public String toCharsetName(int codepage) {
		String name = (String)this.codepages.get(Integer.valueOf(codepage));
		if (name == null) {
			throw new UnsupportedCharsetException("Codepage is not found: " + codepage);
		} else {
			return name;
		}
	}
	
}

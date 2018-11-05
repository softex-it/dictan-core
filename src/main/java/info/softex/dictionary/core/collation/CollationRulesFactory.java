/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2013  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.collation;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Collation Rules Versions:
 * 
 * version  0 - native collation rules
 * version 10 - default (Latin) predefined collation rules
 * version 20 - local appendix of predefined collation rules version
 * version 30 - predefined default (Latin) + predefined local appendix (i.e. full) collation rules
 * version 40 - predefined default (Latin) + native local appendix (i.e. full) collation rules
 * 
 * @since version 2.6, 09/29/2011
 * 
 * @modified version 3.7, 06/11/2013
 * @modified version 3.9, 12/03/2013
 * 
 * @author Dmitry Viktorov
 * 
 */
public class CollationRulesFactory {
	
	public static final int COL_RULES_NATIVE_LOCAL_APPENDIX = 0;
	//public static final int COL_RULES_NATIVE_LOCAL_LATIN = 1;
	public static final int COL_RULES_NATIVE_LOCAL_FULL = 2;
	//public static final int COL_RULES_PREDEFINED_LOCAL_APPENDIX = 9;
	public static final int COL_RULES_PREDEFINED_LATIN = 10;
	public static final int COL_RULES_PREDEFINED_FULL = 30;
	public static final int COL_RULES_MIXED_FULL = 40;
	
	private static final Logger log = LoggerFactory.getLogger(CollationRulesFactory.class);

	protected static final Locale DEFAULT_LOCALE = new Locale("en", "US");
	
	@SuppressWarnings("serial")
	protected static final List<String> LANGS_CYRILLIC = new ArrayList<String>() {{
		add("ru"); add("uk"); add("bg");
	}};
	
	public static SimpleCollationProperties createLocaleAppendixCollationProperties(Locale locale) throws UnsupportedCollationException {
		return getNativeAppendixCollationProperties(locale);		
	}
	
	public static SimpleCollationProperties createDefaultCollationProperties() throws UnsupportedCollationException {
		return new SimpleCollationProperties(DefaultCollationRules.DEFAULT_RULES, COL_RULES_PREDEFINED_LATIN, true);
	}

//	public static SimpleCollationProperties createFullMixedCollationProperties(Locale locale) throws UnsupportedCollationException {
//		
//		SimpleCollationProperties props = new SimpleCollationProperties(
//				DefaultCollationRules.DEFAULT_RULES + 
//				getNativeAppendixCollationProperties(locale).getCollationRules(), 
//				COL_RULES_MIXED_FULL, true
//			);
//		
//		return props;
//	}
	
	/**
	 * Create predefined collation rules. It's useful for the applications that don't have the 
	 * collation rules out of the box.
	 * 
	 * @param locale
	 * @return Predefined collation properties or null if they are not defined for the locale 
	 */
	public static SimpleCollationProperties createFullPredefinedCollationProperties(Locale locale) {
		
		SimpleCollationProperties props = null;
		
		if (LANGS_CYRILLIC.contains(locale.getLanguage())) {
			props = new SimpleCollationProperties(DefaultCollationRules.DEFAULT_RULES + CyrillicCollationRules.CYRILLIC_RULES, COL_RULES_PREDEFINED_FULL, true);
		}
		
		log.debug("Predefined Collation Properties: {}; Language {}", props, locale.getLanguage());
		
		return props;
	}
	
	private static SimpleCollationProperties getNativeAppendixCollationProperties(Locale locale) throws UnsupportedCollationException {
		
		SimpleCollationProperties props = null;
		
		String defRules = getNativeCollationProperties(DEFAULT_LOCALE).getCollationRules();
		String langRules = getNativeCollationProperties(locale).getCollationRules();
		
		if (langRules.startsWith(defRules)) {
			String appendRules = langRules.replace(defRules, "");
			if (appendRules.trim().length() == 0) {
				appendRules = "";
			}
			props = new SimpleCollationProperties(appendRules, COL_RULES_NATIVE_LOCAL_APPENDIX, false);
		} else {
			log.info("RuleBasedCollator for the locale {} is independent from the default locale", locale);
			throw new UnsupportedCollationException("Couldn't retrieve collation rules for the locale " + locale + ": no dependency on the default locale");
		}
		
		return props;
	}
	
	/**
	 * Always returns the appendix of native collation rules
	 * 
	 * @return
	 * @throws UnsupportedCollationException 
	 */
	private static SimpleCollationProperties getNativeCollationProperties(Locale locale) throws UnsupportedCollationException {
		SimpleCollationProperties defRules = null;
		Collator defCollator = Collator.getInstance(locale);
		if (defCollator instanceof RuleBasedCollator) {
			RuleBasedCollator defRuleCollator = (RuleBasedCollator)defCollator;
			defRules = new SimpleCollationProperties(defRuleCollator.getRules(), COL_RULES_NATIVE_LOCAL_FULL, true);
		}
		if (defRules == null) {
			throw new UnsupportedCollationException("Couldn't getReader the default collation rules");
		}
		return defRules;
	}
	
	/**
	 * Independent - whether the props are full or appendix
	 */
	public static class SimpleCollationProperties {
		final String collationRules;
		final int version;
		final boolean independent;
		public SimpleCollationProperties(String collationRules, int version, boolean independent) {
			this.collationRules = collationRules;
			this.version = version;
			this.independent = independent;
		}
		public String getCollationRules() {
			return collationRules;
		}
		public int getCollationVersion() {
			return version;
		}
		public boolean isCollationIndependent() {
			return independent;
		}
		@Override
		public String toString() {
			return "CollationProperties: version " + version + ", independent " + independent;
		}
	}
	
}

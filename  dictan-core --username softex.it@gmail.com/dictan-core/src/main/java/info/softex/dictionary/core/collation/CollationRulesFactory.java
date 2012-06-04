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

package info.softex.dictionary.core.collation;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Collation Rules Versions (are the same for default and localized collations):
 * 
 *  0 - native collation rules
 *  1 - native collation rules followed by any sequence of additional rules, i.e. connected with &
 * 10 - custom collation rules version 10 
 * 
 * @since version 2.6, 09/29/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class CollationRulesFactory {
	
	private static final Logger log = LoggerFactory.getLogger(CollationRulesFactory.class.getSimpleName());

	private static Locale defaultLocale = new Locale("en", "US");
	
	public static SimpleCollationProperties createLocaleCollationProperties(Locale locale, int version, boolean independent) throws UnsupportedCollationException {

		SimpleCollationProperties props = null;
		
		if (version == 0) {
			
			props = independent ? getNativeCollationProperties(locale) : getNativeAppendixCollationProperties(locale);
			
		} else if (version == 10) {
			if (independent) {
				props = new SimpleCollationProperties(
						DefaultCollationRulesV10.DEFAULTRULES + 
						getNativeAppendixCollationProperties(locale).getCollationRules(), 
						version, true
					);
			} else {
				throw new UnsupportedCollationException("Collation rules version 10 cannot currently be dependent");		
			}
			
			log.debug("Rules for the {} locale: {}", locale, props);
		
		} else {
			throw new UnsupportedCollationException("Collation version " + version + " is not supported");
		}
		
		return props;
		
	}
	
	public static SimpleCollationProperties createDefaultCollationProperties(int version) throws UnsupportedCollationException {
		SimpleCollationProperties props = null;
		if (version == 0) {
			props = getNativeCollationProperties(defaultLocale);
		} else if (version == 10) {
			props = new SimpleCollationProperties(DefaultCollationRulesV10.DEFAULTRULES, 10, true);
		}
		return props;
	}
	
	private static SimpleCollationProperties getNativeAppendixCollationProperties(Locale locale) throws UnsupportedCollationException {
		
		SimpleCollationProperties props = null;
		
		String defRules = getNativeCollationProperties(defaultLocale).getCollationRules();
		String langRules = getNativeCollationProperties(locale).getCollationRules();
		
		if (langRules.startsWith(defRules)) {
			String appendRules = langRules.replace(defRules, "");
			if (appendRules.trim().length() == 0) {
				appendRules = "";
			}
			props = new SimpleCollationProperties(appendRules, 0, false);
		} else {
			log.info("RuleBasedCollator for the locale {} is independent from the default locale", locale);
			throw new UnsupportedCollationException("Couldn't retrieve collation rules for the locale " + locale + ": no dependency on the default locale");
		}
		
		return props;
	}
	
	/**
	 * Always returns the native collation rules: independent, version 0
	 * 
	 * @return
	 * @throws UnsupportedCollationException 
	 */
	private static SimpleCollationProperties getNativeCollationProperties(Locale locale) throws UnsupportedCollationException {
		SimpleCollationProperties defRules = null;
		Collator defCollator = Collator.getInstance(locale);
		if (defCollator instanceof RuleBasedCollator) {
			RuleBasedCollator defRuleCollator = (RuleBasedCollator)defCollator;
			defRules = new SimpleCollationProperties(defRuleCollator.getRules(), 0, true);
		}
		if (defRules == null) {
			throw new UnsupportedCollationException("Couldn't get the default collation rules version 0");
		}
		return defRules;
	}
	
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

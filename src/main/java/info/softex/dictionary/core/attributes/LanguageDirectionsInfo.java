/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2014  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.attributes;

import java.text.Collator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * @since version 2.6, 09/18/2011
 * 
 * @modified version 3.4, 07/04/2012
 * @modified version 3.9, 12/05/2013
 * 
 * @author Dmitry Viktorov
 * 
 */
public class LanguageDirectionsInfo {

	private final LinkedHashMap<Locale, List<CollationProperties>> directions = new LinkedHashMap<Locale, List<CollationProperties>>();
	
	public static final String UNDEFINED = "UNDEFINED";

	private CollationProperties defaultCollationProperties = null;
	
	public void addDirection(String fromLocaleSt, String toLocaleSt, String fromBasicCollationRules, String fromAdditionalCollationRules, String fromCollationStrengthString, String fromCollationDecompositionString, int version, boolean independent) throws ParseException {
		addDirection(
				fromLocaleSt, toLocaleSt, fromBasicCollationRules, fromAdditionalCollationRules,
				CollationProperties.toCollationStrengthInt(fromCollationStrengthString), 
				CollationProperties.toCollationDecompositionInt(fromCollationDecompositionString), 
				version, independent
			);
	}
	
	public void addDirection(String fromLocaleSt, String toLocaleSt, String fromBasicCollationRules, String fromAdditionalCollationRules, int fromCollationStrength, int fromCollationDecomposition, int version, boolean independent) throws ParseException {
		
		if (fromLocaleSt == null || toLocaleSt == null) {
			throw new IllegalArgumentException("Languages must not be null!");
		}
		
		Locale fromLocale = new Locale(fromLocaleSt);
		
		List<CollationProperties> keyDirections = this.directions.get(fromLocale);
		if (keyDirections == null) {
			keyDirections = new ArrayList<CollationProperties>();
		}
		
		CollationProperties props = new CollationProperties(
				fromBasicCollationRules, fromAdditionalCollationRules, fromCollationStrength, 
				fromCollationDecomposition, version, independent, new Locale(toLocaleSt)
			);
		keyDirections.add(props);
		
		this.directions.put(fromLocale, keyDirections);
	
	}
	
	public Map<Locale, List<CollationProperties>> getLanguageDirections() {
		return this.directions;
	}
	
	/**
	 * This method should be called if each 'from locale' has only one language direction.
	 * 
	 * @return
	 */
	public String getCombinedCollationRules() {
		String langAppendix = "";
		for (List<CollationProperties> valuesSet : directions.values()) {
			for (CollationProperties langProps : valuesSet) {
				langAppendix += langProps.getBasicCollationRules() + langProps.getAdditionalCollationRules();
			}
		}
		return defaultCollationProperties.getBasicCollationRules() + 
			langAppendix + 
			defaultCollationProperties.getAdditionalCollationRules();
	}
	
	public void setDefaultCollationProperties(String defaultBasicCollationRules, String defaultAdditionalCollationRules, int defaultCollationVersion) {
		defaultCollationProperties = new CollationProperties(
				defaultBasicCollationRules, defaultAdditionalCollationRules, -1, -1, 
				defaultCollationVersion, true, null
			);
	}
	
	public CollationProperties getDefaultCollationProperties() {
		return defaultCollationProperties;
	}

	@Override
	public String toString() {
		return "default properties: " + defaultCollationProperties + ", directions: " + directions ; 
	}
	
	private List<String> toLanguagePairs() {
		
		List<String> langPairs = new ArrayList<String>();
		Map<Locale, List<CollationProperties>> langDirs = getLanguageDirections();
		
		if (langDirs != null) {
			for (Locale fromLocale : langDirs.keySet()) {
				if (fromLocale == null) continue; // Just for safety
				String fromLang = fromLocale.getLanguage();
				List<CollationProperties> colProps = langDirs.get(fromLocale);
				if (colProps != null) {
					for (CollationProperties toProps : colProps) {
						if (toProps == null) continue; // Just for safety
						Locale toLocale = toProps.getLocale();
						if (toLocale == null) continue; // Just for safety
						langPairs.add(fromLang + "-" + toLocale.getLanguage());
					}
				}
			}
		}
		
		return langPairs;
		
	}
	
	public String toLanguagePairsString(boolean skipUndefined) {
		
		String langPairsString = "";
		
		List<String> langPairs = toLanguagePairs();
		
		if (!langPairs.isEmpty()) {
			for (int i = 0; i < langPairs.size(); i++){
				String curPair = langPairs.get(i).toUpperCase();
				if (!skipUndefined || !curPair.contains(UNDEFINED)) {
					langPairsString += curPair;
					if (i < langPairs.size() - 1) {
						langPairsString += ", ";
					}
				}
			}
		}

		return langPairsString;
	}
	
	public static class CollationProperties {
		private static final String PRIMARY = "PRIMARY";
		private static final String SECONDARY = "SECONDARY";
		private static final String TERTIARY = "TERTIARY";

		private static final String NO_DECOMPOSITION = "NO_DECOMPOSITION";
		private static final String CANONICAL_DECOMPOSITION = "CANONICAL_DECOMPOSITION";
		private static final String FULL_DECOMPOSITION = "FULL_DECOMPOSITION";
		private static final String IDENTICAL = "IDENTICAL";
		
		final String basicCollationRules;
		final String additionalCollationRules;
		final int collationStrength;
		final int collationDecomposition;
		final Locale locale;
		final int version;
		final boolean independent;
		public CollationProperties(
				String basicCollationRules, String additionalCollationRules, 
				int collationStrength, int collationDecomposition, int version, boolean independent, Locale locale) {
			this.basicCollationRules = basicCollationRules == null ? "" : basicCollationRules;
			this.additionalCollationRules = additionalCollationRules == null ? "" : additionalCollationRules;
			this.collationStrength = collationStrength;
			this.collationDecomposition = collationDecomposition;
			this.locale = locale;
			this.version = version;
			this.independent = independent;
		}
		public String getBasicCollationRules() {
			return basicCollationRules;
		}
		public String getAdditionalCollationRules() {
			return additionalCollationRules;
		}
		public Locale getLocale() {
			return locale;
		}
		public int getCollationVersion() {
			return version;
		}
		public boolean isCollationIndependent() {
			return independent;
		}
		public int getCollationStrength() {
			return collationStrength;
		}
		public String getCollationStrengthString() {
			switch (this.collationStrength) {
				case Collator.PRIMARY: return PRIMARY;
				case Collator.SECONDARY: return SECONDARY;
				case Collator.TERTIARY: return TERTIARY;
				default: return "";
			}
		}
		public int getCollationDecomposition() {
			return collationDecomposition;
		}
		public String getCollationDecompositionString() {
			switch (this.collationDecomposition) {
				case Collator.NO_DECOMPOSITION: return "NO_DECOMPOSITION";
				case Collator.CANONICAL_DECOMPOSITION: return "CANONICAL_DECOMPOSITION";
				case Collator.FULL_DECOMPOSITION: return "FULL_DECOMPOSITION";
				case Collator.IDENTICAL: return "IDENTICAL";
				default: return "";
			}
		}
		@Override
		public String toString() {
			return "version: " + version + ", independent: " + independent + 
				", to locale: " + locale + ", rules: additional " + additionalCollationRules + 
				" | basic " + basicCollationRules; 
		}
		
		static int toCollationStrengthInt(String strength) {
			if (PRIMARY.equalsIgnoreCase(strength)) {
				return Collator.PRIMARY;
			} else if (SECONDARY.equalsIgnoreCase(strength)) {
				return Collator.SECONDARY;
			} else if (TERTIARY.equalsIgnoreCase(strength)) {
				return Collator.TERTIARY;
			} else {
				return -1;
			}
		}
		
		static int toCollationDecompositionInt(String decomposition) {
			if (NO_DECOMPOSITION.equalsIgnoreCase(decomposition)) {
				return Collator.NO_DECOMPOSITION;
			} else if (CANONICAL_DECOMPOSITION.equalsIgnoreCase(decomposition)) {
				return Collator.CANONICAL_DECOMPOSITION;
			} else if (FULL_DECOMPOSITION.equalsIgnoreCase(decomposition)) {
				return Collator.FULL_DECOMPOSITION;
			} else if (IDENTICAL.equalsIgnoreCase(decomposition)) {
				return Collator.IDENTICAL;
			} else {
				return -1;
			}
		}
	}
	
}

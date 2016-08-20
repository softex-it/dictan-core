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

package info.softex.dictionary.core.formats.fdb.testutils;

import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @since version 4.6,	02/08/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class FDBBaseSampleContent {
	
	public final static String SHORT_NAME = "FDB Test Base";
	public final static String FULL_NAME = "FDB Test Base for internal use";
	
	public final static int WORDS_NUMBER = 3002;
	public final static int RELATIONS_NUMBER = 2;
	public final static int ARTICLES_ACTUAL_NUMBER = WORDS_NUMBER - RELATIONS_NUMBER;
	
	@SuppressWarnings("serial")
	public static final TreeMap<Integer, Integer> REDIRECTS = new TreeMap<Integer, Integer>() {{
		put(2000, 123);
		put(3001, 237);
	}};
	
	public static Map<String, String> createWordsArticles() {
		Map<String, String> wordsArticles = new TreeMap<String, String>();
		for (int i = 0; i < WORDS_NUMBER; i++) {
			Integer redirect = REDIRECTS.get(i);
			if (redirect != null) {
				wordsArticles.put(createRedirectedWord(i, redirect), "");
			} else {
				wordsArticles.put(createWord(i), createArticle(i));
			}
		}
		
		return wordsArticles;
	}
	
	public static String createWord(int wordId) {
		return "Sample Word " + prefixZeroes(wordId);
	}
	
	public static String createRedirectedWord(int wordId, int toWordId) {
		return "Sample Word " + prefixZeroes(wordId) + " redirect to " + toWordId;
	}
	
	public static String createArticle(int wordId) {
		return "Simple text string is used as an article for word " + wordId;
	}
	
	public static String prefixZeroes(int number) {
		return String.format("%04d", number);
	}

}

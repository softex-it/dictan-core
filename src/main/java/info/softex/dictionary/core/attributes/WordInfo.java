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

package info.softex.dictionary.core.attributes;

/**
 * 
 * @since		version 1.4,	01/09/2011
 * 
 * @modified	version 1.10,	02/24/2011
 * @modified	version 2.5,	07/24/2011
 * @modified	version 4.6,	01/28/2015
 * @modified	version 5.2,	10/21/2018
 *
 * @author Dmitry Viktorov
 *
 */
public class WordInfo implements Cloneable {
	
	public static final int UNKNOWN_ID = -1;

    // Base ID of the base that issued the word.
    private String baseId = null;

    // Word ID in the base that issued the word
	private int id = UNKNOWN_ID;

	private String word = null;
	private String wordMapping = null;
	
	private int redirectToId = UNKNOWN_ID;
	private String redirectToWord = null;

    public WordInfo(String baseId, String word) {
        setBaseId(baseId);
        setWord(word);
    }

	public WordInfo(String baseId, int wordId) {
		setBaseId(baseId);
		setId(wordId);
	}
	
	public WordInfo(String baseId, int index, String word) {
	    setBaseId(baseId);
		setId(index);
		setWord(word);
	}
	
	public WordInfo(String baseId, int index, String word, int redirectToIndex) {
        setBaseId(baseId);
		setId(index);
		setWord(word);
		setRedirectToId(redirectToIndex);
	}

	public String getWord() {
		return word;
	}
	
	public String getArticleWord() {
		return wordMapping != null ? wordMapping : word;
	}

	public void setWord(String word) {
		if (word != null) {
			word = word.trim();
		}
		this.word = word;
	}
	
	public boolean hasKey() {
		return this.word != null;
	}

	public int getId() {
		return id;
	}
	
	public int getArticleId() {
		return redirectToId < 0 ? id : redirectToId;
	}

	public void setId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("ID must be greater than or equal to 0");
		}
		this.id = id;
	}
	
	public boolean hasIndex() {
		return this.id >= 0;
	}
	
	public boolean hasRedirect() {
		return this.redirectToId >= 0;
	}
	
	@Override
	public String toString() {
		return "WordInfo: " + word + ", " + id;
	}
	
	@Override
	public WordInfo clone() {
		try {
			return (WordInfo)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public boolean isHttpRelated() {
		if (word == null) {
			return false;
		}
		String lcWord = word.toLowerCase();
		return lcWord.startsWith("http") || lcWord.startsWith("https");
	}
	
	public int getRedirectToId() {
		return redirectToId;
	}
	
	public String getRedirectToWord() {
		return redirectToWord;
	}
	
	public void setRedirectToId(int inRedirectToId) {
		this.redirectToId = inRedirectToId;
	}
	
	public void setRedirectToWord(String inRedirectToWord) {
		this.redirectToWord = inRedirectToWord;
	}
	
	public String getWordMapping() {
		return wordMapping;
	}
	
	public void setWordMapping(String inWordMapping) {
		this.wordMapping = inWordMapping;
	}

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }
}

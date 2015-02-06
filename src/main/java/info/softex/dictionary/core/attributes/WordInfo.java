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

package info.softex.dictionary.core.attributes;


/**
 * 
 * @since version 1.4,		01/09/2011
 * 
 * @modified version 1.10,	02/24/2011
 * @modified version 2.5,	07/24/2011
 * @modified version 4.6,	01/28/2015
 * 
 * @author Dmitry Viktorov
 *
 */
public class WordInfo implements Cloneable {
	
	public static final int UNKNOWN_ID = -1;
	
	private String word = null;
	
	private int id = UNKNOWN_ID;
	
	private int redirectToId = UNKNOWN_ID;
	
	public WordInfo(String word) {
		setWord(word);
	}
	
	public WordInfo(int wordId) {
		setId(wordId);
	}
	
	public WordInfo(int index, String word) {
		setId(index);
		setWord(word);
	}
	
	public WordInfo(int index, String word, int redirectToIndex) {
		setId(index);
		setWord(word);
		setRedirectToId(redirectToIndex);
	}

	public String getWord() {
		return word;
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
	protected WordInfo clone() {
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
	
	public void setRedirectToId(int inRedirectToId) {
		this.redirectToId = inRedirectToId;
	}

}

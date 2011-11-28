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

package info.softex.dictionary.core.attributes;

import info.softex.dictionary.core.utils.ArticleHtmlBuilder;

import java.lang.ref.SoftReference;

/**
 * 
 * @since version 1.4, 01/09/2011
 * 
 * @modified version 1.7, 01/24/2011
 * @modified version 1.10, 02/24/2011
 * @modified version 2.5, 07/24/2011
 * @modified version 2.6, 09/20/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class ArticleInfo implements Cloneable {
	
	public static enum RT {
		STRONG,
		SOFT
	}
		
	private WordInfo wordInfo = null;

	private String article = null;
	
	private SoftReference<String> translationSoftRef = null;
	
	private RT referenceType;
	
	public ArticleInfo(WordInfo wordInfo, String translation) {
		this.referenceType = RT.STRONG;
		this.wordInfo = wordInfo;
		this.setArticle(translation);
	}

	public WordInfo getWordInfo() {
		return this.wordInfo;
	}

	public void setWordInfo(WordInfo wordInfo) {
		this.wordInfo = wordInfo;
	}
	
	public String getArticle() {
		if (this.referenceType == RT.STRONG) {
			return this.article;
		} 
		
		if (this.referenceType == RT.SOFT) {
			return this.translationSoftRef.get();
		}
		
		return null;
	}
	
	public void setArticle(String trans) {
		if (this.referenceType == RT.STRONG) {
			this.article = trans;
			this.translationSoftRef = null;
		} else if (this.referenceType == RT.SOFT) {
			this.translationSoftRef = new SoftReference<String>(trans);
			this.article = null;
		}
	}
	
	public void setReferenceType(RT refType) {
		if (refType != RT.SOFT && refType != RT.STRONG) {
			throw new IllegalArgumentException("Reference Type " + refType + " is illegal");
		}
		
		this.referenceType = refType;

		// Soft reference
		if (this.referenceType == RT.SOFT) {
			if (article != null) {
				translationSoftRef = new SoftReference<String>(article);
				article = null;
			}
		}
		
		// Strong reference
		if (this.referenceType == RT.STRONG) {
			if (translationSoftRef != null) {
				article = translationSoftRef.get();
				translationSoftRef = null;
			}
		}
	
	}
	
	/**
	 * Check if the translation reference is strong before calling it, 
	 * or assign the translation to a variable by the soft reference, and
	 * check if this translation is not null.
	 * 
	 * @return
	 */
	public String getFullArticle() {
		String word = this.wordInfo.getWord();
		String postTrans = ArticleHtmlBuilder.postArticle(word, getArticle());
		return postTrans;
	}
	
	public String getFullHtmlArticle(String bodyCSSFN) {
		return ArticleHtmlBuilder.buildHtmlArticle(
			getFullArticle(), bodyCSSFN
		);
	}
	
	public boolean isStrongTranslationRefernce() {
		return this.referenceType == RT.STRONG;
	}
	
	@Override
	public String toString() {
		if (getArticle() != null) {
			return "Article: exists, Ref type: " + referenceType + "; " + wordInfo;
		} else {
			return "Article: null, Ref type: " + referenceType + "; " + wordInfo;
		}
	}
	
	public ArticleInfo clone(RT refType) {
		ArticleInfo transInfo = new ArticleInfo(this.wordInfo.clone(), this.article);
		transInfo.setReferenceType(refType);
		return transInfo;
	}
	
	public boolean isHttpRelated() {
		return this.wordInfo == null ? false : this.wordInfo.isHttpRelated();
	}
	
}

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
 * @modified version 4.0, 02/02/2014
 * @modified version 4.2, 03/07/2014
 * 
 * @author Dmitry Viktorov
 *
 */
public class ArticleInfo implements Cloneable, KeyValueInfo<String> {
	
	public static enum RT {
		STRONG,
		SOFT
	}
		
	private WordInfo wordInfo = null;

	private String article = null;
	
	private SoftReference<String> articleSoftRef = null;
	
	private RT referenceType;
	
	public ArticleInfo(WordInfo inWordInfo, String inArticle) {
		this.referenceType = RT.STRONG;
		this.wordInfo = inWordInfo;
		setArticle(inArticle);
	}

	public WordInfo getWordInfo() {
		return wordInfo;
	}

	public void setWordInfo(WordInfo inWordInfo) {
		this.wordInfo = inWordInfo;
	}
	
	public String getArticle() {
		if (this.referenceType == RT.STRONG) {
			return this.article;
		} 
		
		if (this.referenceType == RT.SOFT) {
			return this.articleSoftRef.get();
		}
		
		return null;
	}
	
	public void setArticle(String inArticle) {
		if (referenceType == RT.STRONG) {
			article = inArticle;
			articleSoftRef = null;
		} else if (referenceType == RT.SOFT) {
			articleSoftRef = new SoftReference<String>(inArticle);
			article = null;
		}
	}
	
	public void setReferenceType(RT refType) {
		if (refType != RT.SOFT && refType != RT.STRONG) {
			throw new IllegalArgumentException("Reference Type " + refType + " is illegal");
		}
		
		referenceType = refType;

		// Soft reference
		if (referenceType == RT.SOFT) {
			if (article != null) {
				articleSoftRef = new SoftReference<String>(article);
				article = null;
			}
		}
		
		// Strong reference
		if (referenceType == RT.STRONG) {
			if (articleSoftRef != null) {
				article = articleSoftRef.get();
				articleSoftRef = null;
			}
		}
	
	}
	
	public String getFullArticle(String inGlobalCSSPath, FontInfo inFontInfo) {
		return ArticleHtmlBuilder.buildHtmlArticle(getArticle(), inGlobalCSSPath, inFontInfo);
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
		ArticleInfo transInfo = new ArticleInfo(wordInfo.clone(), article);
		transInfo.setReferenceType(refType);
		return transInfo;
	}
	
	public boolean isHttpRelated() {
		return wordInfo == null ? false : wordInfo.isHttpRelated();
	}

	@Override
	public String getKey() {
		return getWordInfo().getWord();
	}

	@Override
	public void setKey(String key) {
		getWordInfo().setWord(key);
	}

	@Override
	public String getValue() {
		return getArticle();
	}

	@Override
	public void setValue(String value) {
		setArticle(value);
	}
	
}

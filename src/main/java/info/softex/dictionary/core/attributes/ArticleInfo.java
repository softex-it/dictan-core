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

import java.lang.ref.SoftReference;

import info.softex.dictionary.core.utils.ArticleHtmlBuilder;

/**
 * Main storage for article data.
 * 
 * @since		version 1.4,	01/09/2011
 * 
 * @modified	version 1.7,	01/24/2011
 * @modified	version 1.10,	02/24/2011
 * @modified	version 2.5,	07/24/2011
 * @modified	version 2.6,	09/20/2011
 * @modified	version 4.0,	02/02/2014
 * @modified	version 4.2,	03/07/2014
 * @modified	version 4.6,	02/01/2015
 * @modified	version 4.9,	12/06/2015
 * @modified	version 5.2,	10/21/2018
 *
 * @author Dmitry Viktorov
 *
 */
public class ArticleInfo implements Cloneable, KeyValueInfo<String, String> {
	
	public static enum RT {
		STRONG,
		SOFT
	}
		
	private WordInfo wordInfo = null;
	private String article = null;

	// Information about the base that issued the article
	private BasePropertiesInfo baseInfo = null;
	
	private SoftReference<String> articleSoftRef = null;
	private RT referenceType;;
	
	public ArticleInfo(WordInfo inWordInfo, String inArticle) {
		this.referenceType = RT.STRONG;
		this.wordInfo = inWordInfo;
		setArticle(inArticle);
	}

	public WordInfo getWordInfo() {
		return wordInfo;
	}
	
	public RT getReferenceType() {
		return referenceType;
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
			articleSoftRef = new SoftReference<>(inArticle);
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
				articleSoftRef = new SoftReference<>(article);
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
	
	public String getFullArticle(FontInfo inFontInfo, String inGlobalCSSPath, String inSpecificCSS) {
		return ArticleHtmlBuilder.buildHtmlArticle(getArticle(), inFontInfo, inGlobalCSSPath, inSpecificCSS);
	}
	
	public boolean isStrongTranslationReference() {
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

	public ArticleInfo clone() {
		try {
			return (ArticleInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public ArticleInfo clone(RT refType) {
		ArticleInfo result = new ArticleInfo(wordInfo.clone(), article);
		result.setReferenceType(refType);
		result.setBaseInfo(baseInfo);
		return result;
	}
	
	public boolean isHttpRelated() {
		return wordInfo != null && wordInfo.isHttpRelated();
	}
	
	public void setBaseInfo(BasePropertiesInfo inBaseInfo) {
		this.baseInfo = inBaseInfo;
		this.wordInfo.setBaseId(inBaseInfo.getBaseLocationUid());
	}
	
	public BasePropertiesInfo getBaseInfo() {
		return baseInfo;
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

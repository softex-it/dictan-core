/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries.
 *
 *  Copyright (C) 2010 - 2017  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.collections;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;

/**
 * Adapter List which represents a word list from an article list returned from a BaseReader
 * or utilities.
 *
 * @since    version 5.1, 02/27/2017
 *
 * @author Dmitry Viktorov
 *
 */
public class WordsNavigationArticleList extends AbstractList<String> implements MetaAwareList<String, BasePropertiesInfo>, RandomAccess {

    private final List<ArticleInfo> articles;

    public WordsNavigationArticleList(List<ArticleInfo> inArticles) {
        if (inArticles == null) {
            throw new NullPointerException();
        }
        this.articles = inArticles;
    }

    @Override
    public String get(int index) {
        return articles.get(index).getWordInfo().getWord();
    }

    @Override
    public int getMetaIndex(int index) {
        ArticleInfo articleInfo = articles.get(index);
        return articleInfo.getWordInfo().getId();
    }

    @Override
    public BasePropertiesInfo getMetaInfo(int index) {
        ArticleInfo articleInfo = articles.get(index);
        return articleInfo.getBaseInfo();
    }

    @Override
    public int size() {
        return articles.size();
    }

}

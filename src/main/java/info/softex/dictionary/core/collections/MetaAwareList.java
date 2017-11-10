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

import java.util.List;

/**
 * Adapter List which represent a list with meta data for each item, primarily from a BaseReader.
 *
 * @since    version 5.1, 02/27/2017
 *
 * @author Dmitry Viktorov
 *
 */
public interface MetaAwareList<E, M> extends List<E> {

    public int getMetaIndex(int index);

    public M getMetaInfo(int index);

}

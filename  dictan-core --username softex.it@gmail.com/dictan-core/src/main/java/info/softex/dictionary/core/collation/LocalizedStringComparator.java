/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2011 - 2012  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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
import java.util.Comparator;
import java.util.Locale;

/**
 * 
 * @since version 1.0, 09/23/2010
 * 
 * @modified version 2.0, 03/19/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class LocalizedStringComparator implements Comparator<String> {

	private final Collator collator;
	
	public LocalizedStringComparator(Locale locale) {
		collator = Collator.getInstance(locale);
		collator.setStrength(Collator.PRIMARY);
		collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION); // used for accented languages/words
	}

	@Override
    public int compare(String s1, String s2) {
        return collator.compare(s1, s2);
    }

}
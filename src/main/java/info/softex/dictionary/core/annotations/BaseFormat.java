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

package info.softex.dictionary.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation represents the meta information about the base format. 
 * It's used to get the <code>FormatInfo</code> of a base.
 * 
 * @since version 2.6,		09/03/2011
 * 
 * @modified version 3.4,	07/05/2012
 * @modified version 4.6,	02/26/2015
 * @modified version 4.7,	03/29/2015
 *
 * @author Dmitry Viktorov
 * 
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BaseFormat {
	
	String name();
	
	String primaryExtension();
	
	String[] extensions();
	
	// States if the base is expected to be sorted
	boolean sortingExpected();
	
	// States if the base contains keys only, i.e. body is not provides for its entries
	boolean hasKeysOnly() default false;

    // States if the format supports the DB like search
    boolean likeSearchSupported() default false;

}

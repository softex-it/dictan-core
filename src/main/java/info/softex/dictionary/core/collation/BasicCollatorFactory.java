/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2013  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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
import java.text.ParseException;
import java.text.RuleBasedCollator;

/**
 * 
 * @since version 2.5, 07/12/2011
 * 
 * @modified version 3.7, 06/12/2013
 * 
 * @author Dmitry Viktorov
 * 
 */
public class BasicCollatorFactory extends AbstractCollatorFactory {

	@Override
	protected Collator createRuleBasedCollator(String rules) throws ParseException {
		return new RuleBasedCollator(rules);
	}
	
}

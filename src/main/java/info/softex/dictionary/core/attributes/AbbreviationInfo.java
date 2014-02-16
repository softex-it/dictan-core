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

/**
 * 
 * @since version 2.5, 07/13/2011
 * 
 * @modified version 2.6, 10/15/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class AbbreviationInfo {

	private String abbreviation = null;
	private String definition = null;
	
	public AbbreviationInfo(String abbreviation, String definition) {
		setAbbreviation(abbreviation);
		setDefinition(definition);
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		if (abbreviation == null) {
			throw new IllegalArgumentException("Abbreviation cannot be null");
		}
		this.abbreviation = abbreviation;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	@Override
	public String toString() {
		return "AbbreviationInfo: " + abbreviation + "=" + definition;
	}
	
}

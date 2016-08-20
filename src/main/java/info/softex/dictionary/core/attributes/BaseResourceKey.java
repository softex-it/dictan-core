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
 * @since version 4.6,		02/22/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public enum BaseResourceKey {
	
	BASE_DESCRIPTION_SHORT("base.description.short", false),
	
	BASE_ARTICLES_META_DSL("base.articles.meta.dsl", true), // DSL meta
	BASE_ABBREVIATIONS_META_DSL("base.abbreviations.meta.dsl", true), // DSL meta
	
	;
	
	private final String key;
	private final boolean automatic;
	
	private BaseResourceKey(String inKey, boolean inAutomatic) {
		this.key = inKey;
		this.automatic = inAutomatic;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public static BaseResourceKey resolveKey(String key) {
        if (key != null) {
    		for (BaseResourceKey brk : BaseResourceKey.values()) {
    			if (key.equalsIgnoreCase(brk.getKey())) {
    				return brk;
    			}
    		}
        }
        return null;	
	}
	
	public boolean isAutomatic() {
		return automatic;
	}

}

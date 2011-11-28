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

package info.softex.dictionary.core.formats.zd;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since version 2.0, 03/23/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class ZDBaseFlags {
	
	public static enum UnitFlags {
		
		ABBREVIATIONS_PRESENT(1, "Abbreviations"), 
		WORDLIST_SORTED(2, "Sorted"),
		//UNKNOWN(4, "UNKNOWN"),
		NO_AUTO_FORMAT(8, "No Autoformat"); 
		
		private int intFlag;
		private String stringFlag;
		
	    UnitFlags(int flags, String stringFlag) {
	    	this.intFlag = flags;
	    	this.stringFlag = stringFlag; 
	    }
	    
	    public int getIntFlag() { 
	    	return this.intFlag; 
	    }
	    
	    public String getStringFlag() { 
	    	return this.stringFlag;
	    }
	    
	}
	
	private int flags;
	
	public ZDBaseFlags(int flags) {
		this.flags = flags;
	}
	
	public boolean isArticleFormatEnabled() {
		return ((flags & UnitFlags.NO_AUTO_FORMAT.getIntFlag()) == UnitFlags.NO_AUTO_FORMAT.getIntFlag() ? false : true);
	}
	
	public boolean areAbbreviationsPresent() {
		return ((flags & UnitFlags.ABBREVIATIONS_PRESENT.getIntFlag()) == UnitFlags.ABBREVIATIONS_PRESENT.getIntFlag() ? true : false);
	}
	
	public boolean isWordListSorted() {
		return ((flags & UnitFlags.WORDLIST_SORTED.getIntFlag()) == UnitFlags.WORDLIST_SORTED.getIntFlag() ? true : false);
	}

	public List<String> getStringFlagsAsArray() {
		ArrayList<String> list = new ArrayList<String>();
		
		for (UnitFlags flag: UnitFlags.values()) {
			if ((this.flags & flag.getIntFlag()) == flag.getIntFlag()) {
				list.add(flag.getStringFlag());
			}
		}
		
		return list;
	}
	
	public String getStringFlagsAsString() {
		
		List<String> stFlags = this.getStringFlagsAsArray();
		
		String resFlags = "";
		int i = 0;
		while (i < stFlags.size() - 1) {
			resFlags += stFlags.get(i) + ", ";
			i++;
		}
		if (stFlags.size() > 0) {
			resFlags += stFlags.get(i);
		}
		
		return resFlags;
	}
	
	public int getIntFlags() {
		return this.flags;
	}
	
	@Override
	public String toString() {
		return "Abbreviations Present: " + areAbbreviationsPresent() + 
			", Autoformat: " + isArticleFormatEnabled() + 
			", Word List Sorted: " + isWordListSorted();
	}
	
}

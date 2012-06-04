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

package info.softex.dictionary.core.formats.zd;

import info.softex.dictionary.core.regional.RegionalResolver;

import java.io.DataInput;
import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * 
 * @since version 1.2, 09/28/2010
 * 
 * @modified version 1.3, 12/19/2010
 * @modified version 2.0, 03/12/2011
 * @modified version 2.6, 08/15/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class ZDHeader {
	
	private int collateLocaleId;
	private int flags;
	
	private int abbreviationsNumber;
	private int abbreviationsSize;
	private int abbreviationsZSize;
	
	private int transBlockSize;
	private int transBlocksNumber;
	private int transCodepage;
	
	private int wordsNumber;
	private int wordsSize;
	private int wordsZSize;
	private int wordsCodepage;
	
	private short formatVersion;
	
	// Additional Parameters ----------
	private int wordsStartPosition;
	private long dictionaryFileSize;
	
	private String transCodepageName;
	private String wordsCodepageName;
	
	public int getTransBlocksNumber() {
		return transBlocksNumber;
	}

	public ZDBaseFlags getFlags() {
		return new ZDBaseFlags(this.flags);
	}

	public int getAbbreviationsNumber() {
		return abbreviationsNumber;
	}

	public int getAbbreviationsZSize() {
		return abbreviationsZSize;
	}

	public int getAbbreviationsSize() {
		return abbreviationsSize;
	}

	public int getTransCodepage() {
		return transCodepage;
	}

	public short getFormatVersion() {
		return formatVersion;
	}

	public int getWordsNumber() {
		return wordsNumber;
	}

	public int getWordsSize() {
		return wordsSize;
	}

	public int getWordsZSize() {
		return wordsZSize;
	}

	public void setTransBlocksNumber(int i) {
		transBlocksNumber = i;
	}

	public void setFlags(int i) {
		flags = i;
	}

	public void setAbbreviationsNumber(int i) {
		abbreviationsNumber = i;
	}

	public void setAbbreviationsSize(int i) {
		abbreviationsSize = i;
	}

	public void setAbbreviationsZSize(int i) {
		abbreviationsZSize = i;
	}

	public void setTransCodepage(int i) {
		transCodepage = i;
	}

	public void setFormatVersion(short word0) {
		formatVersion = word0;
	}

	public void setWordsNumber(int i) {
		wordsNumber = i;
	}

	public void setWordsSize(int i) {
		wordsSize = i;
	}

	public void setWordsZsize(int i) {
		wordsZSize = i;
	}
	
	public void setDictionaryFileSize(long l) {
		dictionaryFileSize = l;
	}
	
	public long getDictionaryFileSize() {
		return dictionaryFileSize;
	}
	
	public void setWordsStartPosition(int i) {
		wordsStartPosition = i;
	}
	
	public int getWordsStartPosition() {
		return wordsStartPosition;
	}
	
	public void setWordsCodepageName(String s) {
		wordsCodepageName = s;
	}
	
	public void setTransCodepageName(String s) {
		transCodepageName = s;
	}
	
	public String getWordsCodepageName() {
		return wordsCodepageName;
	}
	
	public String getTransCodepageName() {
		return transCodepageName;
	}
	
	public int getCollateLocaleId() {
		return collateLocaleId;
	}

	public void setCollateLocaleId(int collateLocaleId) {
		this.collateLocaleId = collateLocaleId;
	}

	public int getTransBlockSize() {
		return transBlockSize;
	}

	public void setTransBlockSize(int transBlockSize) {
		this.transBlockSize = transBlockSize;
	}

	public int getWordsCodepage() {
		return wordsCodepage;
	}

	public void setWordsCodepage(int wordsCodepage) {
		this.wordsCodepage = wordsCodepage;
	}

	public ZDHeader readExternalData(DataInput in, RegionalResolver resolver) throws IOException, DataFormatException {
		
		this.setFormatVersion(in.readShort());
		
		switch (this.getFormatVersion()) {
		
			case 5:
				
					this.setWordsNumber(in.readInt());
					this.setWordsSize(in.readInt());
					this.setWordsZsize(in.readInt());
					this.setTransBlockSize(in.readInt());
					this.setTransBlocksNumber(in.readInt());
					this.setAbbreviationsNumber(in.readInt());
					this.setAbbreviationsSize(in.readInt());
					this.setAbbreviationsZSize(in.readInt());
					this.setFlags(in.readInt());
					this.setWordsCodepage(in.readInt());
					this.setTransCodepage(in.readInt());
					this.setCollateLocaleId(in.readInt());
					this.setWordsStartPosition(50);
					
				break;
			
			case 2:
				
					in.readShort();
					this.setWordsNumber(in.readInt());
					this.setWordsSize(in.readInt());
					this.setWordsZsize(in.readInt());
					this.setTransBlockSize(100);
					this.setTransBlocksNumber(in.readInt());
					this.setAbbreviationsNumber(in.readInt());
					this.setAbbreviationsSize(in.readInt());
					this.setAbbreviationsZSize(in.readInt());
					this.setFlags(in.readInt());				
					this.setWordsCodepage(1251);
					this.setTransCodepage(1251);
					this.setCollateLocaleId(25);
					this.setWordsStartPosition(36);
				
				break;
				
			case 3:
				
					this.setWordsNumber(in.readInt());
					this.setWordsSize(in.readInt());
					this.setWordsZsize(in.readInt());
					this.setTransBlockSize(in.readInt());
					this.setTransBlocksNumber(in.readInt());
					this.setAbbreviationsNumber(in.readInt());
					this.setAbbreviationsSize(in.readInt());
					this.setAbbreviationsZSize(in.readInt());
					this.setFlags(in.readInt());		
					this.setWordsCodepage(1251);
					this.setTransCodepage(1251);
					this.setCollateLocaleId(25);
					this.setWordsStartPosition(38);
				
				break;
				
			default: 
				throw new DataFormatException("ZD version is not supported: " + this.getFormatVersion());
		}
		
		// Set Code Pages
		this.setWordsCodepageName(resolver.toCharsetName(this.getWordsCodepage()));
		this.setTransCodepageName(resolver.toCharsetName(this.getTransCodepage()));

		return this;
		
	}
	
}

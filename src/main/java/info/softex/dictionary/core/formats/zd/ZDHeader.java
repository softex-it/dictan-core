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

package info.softex.dictionary.core.formats.zd;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.zip.DataFormatException;

import info.softex.dictionary.core.regional.RegionalResolver;

/**
 * 
 * @since		version 1.2, 09/28/2010
 * 
 * @modified	version 1.3, 12/19/2010
 * @modified	version 2.0, 03/12/2011
 * @modified	version 2.6, 08/15/2011
 * @modified	version 5.2, 10/26/2018
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
	
	private int articlesBlockSize;
	private int articlesBlocksNumber;
	private int articlesCodepage;
	
	private int wordsNumber;
	private int wordsSize;
	private int wordsZSize;
	private int wordsCodepage;
	
	private short formatVersion;
	
	// Additional Parameters ----------
	private int wordsStartPosition;
	private long dictionaryFileSize;
	
	private String articlesCodepageName;
	private String wordsCodepageName;

    private UUID uuid;

    private ZDHeader() {}

    public int getArticlesBlocksNumber() {
		return articlesBlocksNumber;
	}

	public ZDBaseFlags getFlags() {
		return new ZDBaseFlags(flags);
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

	public int getArticlesCodepage() {
		return articlesCodepage;
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

	public void setArticlesBlocksNumber(int i) {
		articlesBlocksNumber = i;
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

	public void setArticlesCodepage(int i) {
		articlesCodepage = i;
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
	
	public void setArticlesCodepageName(String s) {
		articlesCodepageName = s;
	}
	
	public String getWordsCodepageName() {
		return wordsCodepageName;
	}
	
	public String getArticlesCodepageName() {
		return articlesCodepageName;
	}
	
	public int getCollateLocaleId() {
		return collateLocaleId;
	}

	public void setCollateLocaleId(int collateLocaleId) {
		this.collateLocaleId = collateLocaleId;
	}

	public int getArticlesBlockSize() {
		return articlesBlockSize;
	}

	public void setArticlesBlockSize(int transBlockSize) {
		this.articlesBlockSize = transBlockSize;
	}

	public int getWordsCodepage() {
		return wordsCodepage;
	}

	public void setWordsCodepage(int wordsCodepage) {
		this.wordsCodepage = wordsCodepage;
	}

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static ZDHeader load(DataInput in, RegionalResolver resolver) throws IOException, DataFormatException {
        ZDHeader header = new ZDHeader();
        header.setFormatVersion(in.readShort());
		switch (header.getFormatVersion()) {
			case 5:
                header.setWordsNumber(in.readInt());
                header.setWordsSize(in.readInt());
                header.setWordsZsize(in.readInt());
                header.setArticlesBlockSize(in.readInt());
                header.setArticlesBlocksNumber(in.readInt());
                header.setAbbreviationsNumber(in.readInt());
                header.setAbbreviationsSize(in.readInt());
                header.setAbbreviationsZSize(in.readInt());
                header.setFlags(in.readInt());
                header.setWordsCodepage(in.readInt());
                header.setArticlesCodepage(in.readInt());
                header.setCollateLocaleId(in.readInt());
                header.setWordsStartPosition(50);
                break;
			
			case 2:
			    in.readShort();
                header.setWordsNumber(in.readInt());
                header.setWordsSize(in.readInt());
                header.setWordsZsize(in.readInt());
                header.setArticlesBlockSize(100);
                header.setArticlesBlocksNumber(in.readInt());
                header.setAbbreviationsNumber(in.readInt());
                header.setAbbreviationsSize(in.readInt());
                header.setAbbreviationsZSize(in.readInt());
                header.setFlags(in.readInt());
                header.setWordsCodepage(1251);
                header.setArticlesCodepage(1251);
                header.setCollateLocaleId(25);
                header.setWordsStartPosition(36);
				break;
				
			case 3:
                header.setWordsNumber(in.readInt());
                header.setWordsSize(in.readInt());
                header.setWordsZsize(in.readInt());
                header.setArticlesBlockSize(in.readInt());
                header.setArticlesBlocksNumber(in.readInt());
                header.setAbbreviationsNumber(in.readInt());
                header.setAbbreviationsSize(in.readInt());
                header.setAbbreviationsZSize(in.readInt());
                header.setFlags(in.readInt());
                header.setWordsCodepage(1251);
                header.setArticlesCodepage(1251);
                header.setCollateLocaleId(25);
                header.setWordsStartPosition(38);
				break;
				
			default: 
				throw new DataFormatException("ZD version is not supported: " + header.getFormatVersion());
		}

        // Calculate base UUID
        header.setUuid(calculateUUID(header));
		
		// Set code pages
        header.setWordsCodepageName(resolver.toCharsetName(header.getWordsCodepage()));
        header.setArticlesCodepageName(resolver.toCharsetName(header.getArticlesCodepage()));

		return header;
	}

    private static UUID calculateUUID(ZDHeader header) {
        ByteBuffer buf = ByteBuffer.allocate(52);
        buf.putInt(header.getWordsNumber());
        buf.putInt(header.getWordsSize());
        buf.putInt(header.getWordsZSize());
        buf.putInt(header.getArticlesBlockSize());
        buf.putInt(header.getArticlesBlocksNumber());
        buf.putInt(header.getAbbreviationsNumber());
        buf.putInt(header.getAbbreviationsSize());
        buf.putInt(header.getAbbreviationsZSize());
        buf.putInt(header.getFlags().getIntFlags());
        buf.putInt(header.getWordsCodepage());
        buf.putInt(header.getArticlesCodepage());
        buf.putInt(header.getCollateLocaleId());
        buf.putInt(header.getWordsStartPosition());
        UUID uuid = UUID.nameUUIDFromBytes(buf.array());
        return uuid;
    }
	
}

/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2012  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

import info.softex.dictionary.core.formats.commons.BaseFormatException;
import info.softex.dictionary.core.formats.zd.collections.ZDDynamicListSet;
import info.softex.dictionary.core.formats.zd.io.LittleEndianDataInputStream;
import info.softex.dictionary.core.formats.zd.io.LittleEndianRandomAccessFile;
import info.softex.dictionary.core.formats.zd.io.zip.TIIStream;
import info.softex.dictionary.core.io.SmartInflaterInputStream;
import info.softex.dictionary.core.regional.RegionalResolver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 1.3, 11/13/2010
 * 
 * @modified version 2.0, 03/12/2011
 * @modified version 2.5, 08/02/2011
 * @modified version 2.6, 09/18/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class ZDDynamicArticlesReader {

	private final Logger log = LoggerFactory.getLogger(ZDDynamicArticlesReader.class);
	
	protected RegionalResolver regionalResolver = null;
	
	protected final File dictFile;
	protected LittleEndianRandomAccessFile raf = null;
	
	protected ZDDynamicListSet dynamicWords = null;
	
	protected Map<String, String> abbreviations = null;
	protected int blockOffsets[] = null;
	protected BlockCache lastLoadedBlock = null;
	protected ZDHeader zdHeader = null;
	//protected final ProgressInfo progressInfo = new ProgressInfo();
	
	protected boolean loaded = false;
	
	protected LittleEndianDataInputStream ledis = null;
	
	protected static class BlockCache {
		int blockNumber;
		List<SoftReference<String>> strings;
		public BlockCache(int blockNumber, int stringsAmount) {
			this.blockNumber = blockNumber;
			this.strings = new ArrayList<SoftReference<String>>(stringsAmount);
		}
	}

	public static class TransBlockInfo {
		int size;
		int zsize;
		public static final int SIZE = 8;
	}

	public ZDDynamicArticlesReader(RegionalResolver regionalResolver, File file) throws IOException {
		
		this.regionalResolver = regionalResolver;
		
		this.lastLoadedBlock = null;
		this.dictFile = file;
		this.raf = new LittleEndianRandomAccessFile(file, "r");
		
		FileInputStream fis = new FileInputStream(dictFile);
		ledis = new LittleEndianDataInputStream(fis);
		
		log.debug("Dictionary Size: {}", raf.length());
	}

	public void close() throws IOException {
		if (raf != null) {
			raf.close();
			raf = null;
		}
		if (ledis != null) {
			ledis.close();
			ledis = null;
		}
	}

	public void load() throws IOException, BaseFormatException {
		try {
		    long l = System.currentTimeMillis();
		    
		    if (this.zdHeader == null) {
		        loadHeader();
		    }
		    
		    this.dynamicWords = new ZDDynamicListSet(
		    		this.dictFile,
		    		ZDConstants.COMPRESSED_BUFFER_SIZE,
		    		ZDConstants.WORD_LIST_BLOCK_SIZE,
		    		this.zdHeader.getWordsNumber(),
		    		this.zdHeader.getWordsStartPosition(), 
		    		this.zdHeader.getWordsSize(), 
		    		this.zdHeader.getWordsCodepageName()
		    	);
		    
		    TIIStream tiis = dynamicWords.getTIIStream().createNewZippedSetIS();
		    
		    this.abbreviations = ZDReadUtils.loadAbbreviations(tiis, zdHeader);
	
		    tiis.close();
		    
		    this.blockOffsets = ZDReadUtils.loadBlockOffsets(raf, zdHeader);
		    
		    this.loaded = true;
		    
		    long loadTime = System.currentTimeMillis() - l;
		    log.info("Dictionary Loading Time: {}", loadTime);
		    
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException(e.getMessage());
		}
	    
	}

	public ZDHeader loadHeader() throws IOException, BaseFormatException {

        log.debug("Loading ZD Header");

        try {
			this.zdHeader  = new ZDHeader().readExternalData(ledis, regionalResolver);
		} catch (DataFormatException e) {
			log.error("Error", e);
			throw new BaseFormatException(e.getMessage());
		}

		log.debug("ZD Header: {}", this);
		
		this.zdHeader.setDictionaryFileSize(this.dictFile.length());
		return zdHeader;
	}

	public String getArticle(int index) throws IOException, BaseFormatException {
		if (index < 0 || index > dynamicWords.size()) {
			throw new IndexOutOfBoundsException("" + index);
		}
		int iob = index / zdHeader.getTransBlockSize();
		int iow = index % zdHeader.getTransBlockSize();
		
		String transResult = null;
		
		// Check if the translation is available by soft reference, and return if found
		if (lastLoadedBlock != null && lastLoadedBlock.blockNumber == iob) {
			transResult = lastLoadedBlock.strings.get(iow).get();
			if (transResult != null) {
				log.debug("Translation has been retrieved by soft reference");
				return transResult;
			} else {
				log.debug("Translation's reference is lost, recreating");
				lastLoadedBlock = null;
			}
		} else {
			log.debug("The requested block is different from the last one");
		}
		
		if (lastLoadedBlock == null) {
			lastLoadedBlock = new BlockCache(iob, zdHeader.getTransBlockSize());
		} else {
			lastLoadedBlock.blockNumber = iob;
			lastLoadedBlock.strings.clear();
		}
		
		raf.seek(blockOffsets[iob]);

		int size = raf.readInt();
		int zsize = raf.readInt();
		byte[] compressedData = new byte[zsize];
		raf.read(compressedData);
				
		byte[] uncompressedData = readBytesFromStream(
				new SmartInflaterInputStream(new ByteArrayInputStream(compressedData)), size
			);

		String csName = zdHeader.getTransCodepageName();
		log.debug("Translate | Codepage: {}", csName);
		
		int startIdx = 0;
		int curTransNumber = -1; 
		for (int i = 0; i < uncompressedData.length; i++) {
			if (uncompressedData[i] == 0) {
				String translation = new String(uncompressedData, startIdx, i - startIdx, csName);
				lastLoadedBlock.strings.add(new SoftReference<String>(translation));
				curTransNumber++;
				if (curTransNumber == iow) {
					transResult = translation;
				}
				startIdx = i + 1;
			}
		}
		return transResult;
	}
	
	private byte[] readBytesFromStream(InputStream is, int bytesNumber) throws IOException {
		byte[] readData = new byte[bytesNumber];
		int readBytesNumber = 0;
		while(readBytesNumber < bytesNumber) {
			readBytesNumber += is.read(readData, readBytesNumber, readData.length - readBytesNumber);
		}
		is.close();
		return readData;
	}

	public List<String> getWords() {
		return dynamicWords;
	}

	public String getFileName() {
		return dictFile.getName();
	}

	public String getFilePath() {
		return dictFile.getAbsolutePath();
	}
	
	public boolean isLoaded() {
		return this.loaded;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.close();
	}

    public Set<String> getAbbreviationKeys() {
        return this.abbreviations.keySet();
    }
    
    public Map<String, String> getAbbreviations() {
        return this.abbreviations;
    }

    public ZDHeader getHeader() {
        return this.zdHeader;
    }
	
}

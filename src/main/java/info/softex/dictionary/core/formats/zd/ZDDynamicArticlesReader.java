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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.zd.collections.ZDDynamicListSet;
import info.softex.dictionary.core.formats.zd.io.LittleEndianDataInputStream;
import info.softex.dictionary.core.formats.zd.io.LittleEndianRandomAccessFile;
import info.softex.dictionary.core.formats.zd.io.zip.TIIStream;
import info.softex.dictionary.core.io.ReliableInflaterInputStream;
import info.softex.dictionary.core.regional.RegionalResolver;

/**
 * 
 * @since       version 1.3, 11/13/2010
 * 
 * @modified    version 2.0, 03/12/2011
 * @modified    version 2.5, 08/02/2011
 * @modified    version 2.6, 09/18/2011
 * @modified    version 5.2, 10/26/2018
 *
 * @author Dmitry Viktorov
 *
 */
public class ZDDynamicArticlesReader {

	private final Logger log = LoggerFactory.getLogger(ZDDynamicArticlesReader.class);
	
	protected final RegionalResolver regionalResolver;
	protected final File baseFile;
	protected LittleEndianRandomAccessFile raf = null;

	protected ZDHeader zdHeader = null;
	protected ZDDynamicListSet dynamicWords = null;

	protected Map<String, String> abbreviations = null;
    protected int[] blockOffsets = null;
    protected BlockCache lastLoadedBlock = null;

	protected boolean loaded = false;
    protected boolean closed = false;
	
	protected LittleEndianDataInputStream ledis = null;

//	public static class BaseBlockInfo {
//		int size;
//		int zsize;
//		public static final int SIZE = 8;
//	}
	
	protected static class BlockCache {
		int blockNumber;
		List<SoftReference<String>> strings;
		public BlockCache(int blockNumber, int stringsNumber) {
			this.blockNumber = blockNumber;
			this.strings = new ArrayList<>(stringsNumber);
		}
	}

	public ZDDynamicArticlesReader(RegionalResolver regionalResolver, File baseFile) {
		this.regionalResolver = regionalResolver;
		this.baseFile = baseFile;
	}

    public void initialize() throws IOException {
        raf = new LittleEndianRandomAccessFile(baseFile, "r");
        FileInputStream fis = new FileInputStream(baseFile);
        ledis = new LittleEndianDataInputStream(fis);
        log.debug("Dictionary Size: {}", raf.length());
    }

	public void close() throws IOException {
        closed = true;
		if (raf != null) {
			raf.close();
			raf = null;
		}
		if (ledis != null) {
			ledis.close();
			ledis = null;
		}
	}

	public void load() throws BaseFormatException {
		try {
		    long start = System.currentTimeMillis();
		    if (zdHeader == null) {
		        loadHeader();
		    }
		    
		    this.dynamicWords = new ZDDynamicListSet(
		    		baseFile,
		    		ZDConstants.COMPRESSED_BUFFER_SIZE,
		    		ZDConstants.WORD_LIST_BLOCK_SIZE,
		    		zdHeader.getWordsNumber(),
		    		zdHeader.getWordsStartPosition(),
		    		zdHeader.getWordsSize(),
		    		zdHeader.getWordsCodepageName()
		    	);
		    
		    TIIStream tiis = dynamicWords.getTIIStream().createNewZippedSetIS();
		    abbreviations = ZDReadUtils.loadAbbreviations(tiis, zdHeader);
		    tiis.close();
		    blockOffsets = ZDReadUtils.loadBlockOffsets(raf, zdHeader);
		    loaded = true;
		    
		    long loadTime = System.currentTimeMillis() - start;
		    log.info("Dictionary Loading Time: {}", loadTime);
		} catch (Exception e) {
			log.error("Error", e);
			throw new BaseFormatException(e.getMessage());
		}
	}

	public ZDHeader loadHeader() throws IOException, BaseFormatException {
        log.debug("Loading ZD Header");
        try {
			zdHeader = ZDHeader.load(ledis, regionalResolver);
		} catch (DataFormatException e) {
			log.error("Error", e);
			throw new BaseFormatException(e.getMessage());
		}
		log.debug("ZD Header: {}", zdHeader);
		zdHeader.setDictionaryFileSize(baseFile.length());
		return zdHeader;
	}

	public String getArticle(int index) throws IOException, BaseFormatException {
		if (index < 0 || index > dynamicWords.size()) {
			throw new IndexOutOfBoundsException("" + index);
		}
		int iob = index / zdHeader.getArticlesBlockSize();
		int iow = index % zdHeader.getArticlesBlockSize();
		
		String article = null;
		
		// Check if the translation is available by soft reference, and return if found
		if (lastLoadedBlock != null && lastLoadedBlock.blockNumber == iob) {
			article = lastLoadedBlock.strings.get(iow).get();
			if (article != null) {
				log.debug("Translation has been retrieved by soft reference");
				return article;
			} else {
				log.debug("Translation's reference is lost, recreating");
				lastLoadedBlock = null;
			}
		} else {
			log.debug("The requested block is different from the last one");
		}
		
		if (lastLoadedBlock == null) {
			lastLoadedBlock = new BlockCache(iob, zdHeader.getArticlesBlockSize());
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
				new ReliableInflaterInputStream(new ByteArrayInputStream(compressedData)), size
			);

		String csName = zdHeader.getArticlesCodepageName();
		log.debug("Translate | Codepage: {}", csName);
		
		int startIdx = 0;
		int curTransNumber = -1; 
		for (int i = 0; i < uncompressedData.length; i++) {
			if (uncompressedData[i] == 0) {
				String retrievedArticle = new String(uncompressedData, startIdx, i - startIdx, csName);
				lastLoadedBlock.strings.add(new SoftReference<>(retrievedArticle));
				curTransNumber++;
				if (curTransNumber == iow) {
					article = retrievedArticle;
				}
				startIdx = i + 1;
			}
		}
		return article;
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
		return baseFile.getName();
	}

	public String getFilePath() {
		return baseFile.getAbsolutePath();
	}
	
	public boolean isLoaded() {
		return loaded;
	}

    public boolean isClosed() {
        return closed;
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

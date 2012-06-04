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

import static info.softex.dictionary.core.formats.zd.ZDConstants.UNCOMPRESSED_BUFFER_SIZE;
import info.softex.dictionary.core.formats.zd.io.LittleEndianRandomAccessFile;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.InflaterInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 1.3, 11/14/2010
 * 
 * @modified version 2.0, 03/12/2011
 * @modified version 2.5, 08/02/2011
 * @modified version 2.6, 09/15/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class ZDReadUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ZDReadUtils.class.getSimpleName());

	public static int readBuffer(InputStream is, byte[] dataBuffer, int length) throws IOException {
		
		int readCNT = 0;
		
		while(readCNT < length) {
			int readCUR = is.read(dataBuffer, readCNT, length - readCNT);
			if (readCUR == -1) {
				throw new EOFException("Reached unexpected end of file");
			} else if (readCUR == 0) {
				throw new EOFException("Couldn't read more data from the stream");
			}
			readCNT += readCUR;
		}
		
		return readCNT;
	}
	
	public static int[] buildWordBlockIndices(
			final InflaterInputStream iis, 
			final int wordsNumber, final int wordsSize,
			final int blockSize) throws DataFormatException, IOException {
		
		//log.info("Build Words Indices | Count: " + wordsNumber + ", Compressed Size: " + dictInfo.words_zsize + ", Uncompressed Size: " + dictInfo.words_size);
				
		final int numberOfBlocks = (int)Math.ceil((double)wordsNumber / blockSize);
		final int[] indices = new int[numberOfBlocks];
		
		//progressInfo.setMessage("Unpacking words...");
		//progressInfo.setTotal(wordsNumber);
		
		byte[] uncompressedData = null;
		if (wordsSize < UNCOMPRESSED_BUFFER_SIZE) {
			uncompressedData = new byte[wordsSize];
		} else {
			uncompressedData = new byte[UNCOMPRESSED_BUFFER_SIZE];				
		}
			    
		log.info("Build Words Indices | Uncompressed Data Buffer Size: " + uncompressedData.length);
		
		//progressInfo.setCurrent(0);

		int readGLB = 0;
		int curBlockNumber = 0;
		int curBlockIndex = 0;
		int curBlockOffset = 0;
		int wordsCount = 0;
		
		while (readGLB < wordsSize) {
			
			int curUncompLength = uncompressedData.length;
			int rest = wordsSize - readGLB;
			if (uncompressedData.length > rest) {
				curUncompLength = rest;
			}

			readGLB += readBuffer(iis, uncompressedData, curUncompLength);

			log.info("Build Words Indices | Load Cycle | Bytes Read: " + readGLB + ", Bytes Total: " + wordsSize);
			
			for (int i = 0; i < curUncompLength; i++) {
				if (uncompressedData[i] == 0) {

					//progressInfo.step(1);
					
					if (curBlockIndex < blockSize-1) {
						curBlockIndex++;
					} else {
						indices[curBlockNumber] = curBlockOffset + i;
						curBlockNumber++;
						wordsCount += curBlockIndex + 1;
						curBlockIndex = 0;
					}
				}
			}
			
			curBlockOffset += curUncompLength;
		
		}
		
		if (curBlockNumber < indices.length) {// Exception of RU-DE_Lingvo from Vlad Solovey
		
			indices[curBlockNumber] = readGLB;
			
			wordsCount += curBlockIndex;
		
		}
		
		if (wordsNumber != wordsCount) {
			log.error("Load Words | Words number differs from the expected number | Expected Number: " + wordsNumber + ", Actual: " + wordsCount);
		}
		
		if (readGLB != wordsSize) {
			throw new DataFormatException("Uncompressed words' size is invalid: should be " + wordsSize + ", got " + readGLB);
		}
		
		log.info("Build Words Indices | Words Loaded: " + wordsCount + ", Expected Words Number: " + wordsNumber + ", Cycles: " + (readGLB/UNCOMPRESSED_BUFFER_SIZE + 1));

		return indices;
		
	}
	
	public static int[][] buildWordsIndices(
			final InflaterInputStream iis, final ZDHeader dictInfo, 
			final String csName, final int blockSize) throws DataFormatException, IOException {
		
		log.info("Build Words Indices | Count: " + dictInfo.getWordsNumber() + ", Compressed Size: " + dictInfo.getWordsZSize() + ", Uncompressed Size: " + dictInfo.getWordsSize());
				
		final int numberOfBlocks = (int)Math.ceil((double)dictInfo.getWordsNumber() / blockSize);
		
		final int[][] indices = new int[numberOfBlocks][blockSize];
		
		//progressInfo.setMessage("Unpacking words...");
		//progressInfo.setTotal(dictInfo.getWordsNumber());
		
		byte[] uncompressedData = null;
		if (dictInfo.getWordsSize() < UNCOMPRESSED_BUFFER_SIZE) {
			uncompressedData = new byte[dictInfo.getWordsSize()];
		} else {
			uncompressedData = new byte[UNCOMPRESSED_BUFFER_SIZE];				
		}
			    
		log.info("Build Words Indices | Uncompressed Data Buffer Size: " + uncompressedData.length);
		
		//progressInfo.setCurrent(0);

		int readGLB = 0;
		int curBlockNumber = 0;
		int curBlockIndex = 0;
		int curBlockOffset = 0;
		int wordsCount = 0;
		
		while (readGLB < dictInfo.getWordsSize()) {
			
			int curUncompLength = uncompressedData.length;
			int rest = dictInfo.getWordsSize() - readGLB;
			if (uncompressedData.length > rest) {
				curUncompLength = rest;
			}

			readGLB += readBuffer(iis, uncompressedData, curUncompLength);

			log.info("Build Words Indices | Load Cycle | Bytes Read: " + readGLB + ", Bytes Total: " + dictInfo.getWordsSize());
			
			for (int i = 0; i < curUncompLength; i++) {
				if (uncompressedData[i] == 0) {
					
					indices[curBlockNumber][curBlockIndex] = curBlockOffset + i;
					//progressInfo.step(1);
					
					if (curBlockIndex < blockSize-1) {
						curBlockIndex++;
					} else {
						curBlockNumber++;
						wordsCount += curBlockIndex + 1;
						curBlockIndex = 0;
					}
					
				}
			}
			
			curBlockOffset += curUncompLength;
		
		}
		
		int[] lastBlock = new int[curBlockIndex];
		System.arraycopy(indices[curBlockNumber], 0, lastBlock, 0, lastBlock.length);
		indices[curBlockNumber] = lastBlock;
		
		wordsCount += curBlockIndex;

		if (dictInfo.getWordsNumber() != wordsCount) {
			log.error("Load Words | Words number differs from the expected number | Expected Number: " + dictInfo.getWordsNumber() + ", Actual: " + wordsCount);
		}
		
		if (readGLB != dictInfo.getWordsSize()) {
			throw new DataFormatException("Uncompressed words' size is invalid: should be " + dictInfo.getWordsSize() + ", got " + readGLB);
		}
		
		log.info("Build Words Indices | Words Loaded: " + wordsCount + ", Expected Words Number: " + dictInfo.getWordsNumber() + ", Cycles: " + (readGLB/UNCOMPRESSED_BUFFER_SIZE + 1));

		return indices;
		
	}
		
	
	public static List<String> loadWords(InflaterInputStream iis, String csName, ZDHeader dictInfo) throws DataFormatException, IOException {
		
		log.info("Loading Words | Count: " + dictInfo.getWordsNumber() + ", Compressed Size: " + dictInfo.getWordsZSize() + ", Uncompressed Size: " + dictInfo.getWordsSize());

		//progressInfoLoc.setMessage("Unpacking words...");
		//progressInfoLoc.setTotal(dictInfo.getWordsNumber());
		
		byte[] uncompressedData = null;
		if (dictInfo.getWordsSize() < UNCOMPRESSED_BUFFER_SIZE) {
			uncompressedData = new byte[dictInfo.getWordsSize()];
		} else {
			uncompressedData = new byte[UNCOMPRESSED_BUFFER_SIZE];				
		}
			    
		log.info("Loading Words | Uncompressed Data Buffer Size: " + uncompressedData.length);
		
		ArrayList<String> list = new ArrayList<String>(dictInfo.getWordsNumber());
		int readGLB = 0;
		byte[] uncompressedDataRest = null;
		//progressInfoLoc.setCurrent(0);
		
		readCycle: while (readGLB < dictInfo.getWordsSize()) {
			
			int curUncompLength = uncompressedData.length;
			int rest = dictInfo.getWordsSize() - readGLB;
			if (uncompressedData.length > rest) {
				curUncompLength = rest;
			}
			
			int readCNT = 0;
			if (uncompressedDataRest != null) {
				String lastWordPiece = new String(uncompressedDataRest, csName);
				log.info("Loading Words | Last Word Piece: " + lastWordPiece);
				System.arraycopy(uncompressedDataRest, 0, uncompressedData, 0, uncompressedDataRest.length);
				readCNT = uncompressedDataRest.length;
				uncompressedDataRest = null;
			}
			
			while(readCNT < curUncompLength) {
				int readCUR = iis.read(uncompressedData, readCNT, curUncompLength - readCNT);
				if (readCUR == -1) {
					log.error("Loading Words | Reached unexpected end of file");
					break readCycle;
				} else if (readCUR == 0) {
					log.error("Loading Words | Couldn't read more data from compressed stream");
					break readCycle;					
				}
				readCNT += readCUR;
			}
			readGLB += readCNT;

			log.info("Loading Words | Load Cycle | Bytes Read: " + readGLB + ", Bytes Total: " + dictInfo.getWordsSize());
			
			int startIdx = 0;
			for (int i = 0; i < curUncompLength; i++) {
				if (uncompressedData[i] == 0) {
					String word = new String(uncompressedData, startIdx, i - startIdx, csName);
					list.add(word);
					//progressInfoLoc.step(1);
					startIdx = i + 1;
				}
			}
			
			if (curUncompLength > startIdx) {
				uncompressedDataRest = new byte[curUncompLength - startIdx];
				System.arraycopy(uncompressedData, startIdx, uncompressedDataRest, 0, uncompressedDataRest.length);
				readGLB -= uncompressedDataRest.length; 
			}
		
		}
		
		if (uncompressedDataRest != null) {
			log.error("Load Words | Unexpected Uncompressed Data Rest Size: " + uncompressedDataRest.length);
		}
		
		if (dictInfo.getWordsNumber() != list.size()) {
			log.error("Load Words | Words number differs from the expected number | Expected Number: " + dictInfo.getWordsNumber() + ", Actual: "  + list.size());
		}
		
		if (readGLB != dictInfo.getWordsSize()) {
			throw new DataFormatException("Uncompressed words' size is invalid: should be " + dictInfo.getWordsSize() + ", got " + readGLB);
		}
		
		log.info("Load Words | Words Loaded: " + list.size() + ", Expected Words Number: " + dictInfo.getWordsNumber() + ", Cycles: " + (readGLB/UNCOMPRESSED_BUFFER_SIZE + 1));
		return Collections.unmodifiableList(list);
		
	}

	
	public static Map<String, String> loadAbbreviations(InflaterInputStream iis, ZDHeader dictInfo) throws IOException, DataFormatException, DataFormatException {
		
		log.info("Load Abbreviations | Number: {}, Compressed Size: {}, Uncompressed Size: " + dictInfo.getAbbreviationsSize(), dictInfo.getAbbreviationsNumber(), dictInfo.getAbbreviationsZSize());	
				
		HashMap<String, String> abbs = new HashMap<String, String>(dictInfo.getAbbreviationsNumber());
		int readGLB = 0;
		
		if (dictInfo.getAbbreviationsNumber() > 0) {
		
			byte[] uncompressedData = null;
			if (dictInfo.getAbbreviationsSize() < UNCOMPRESSED_BUFFER_SIZE) {
				uncompressedData = new byte[dictInfo.getAbbreviationsSize()];
			} else {
				uncompressedData = new byte[UNCOMPRESSED_BUFFER_SIZE];				
			}
			
			log.info("Uncompressed Data Buffer Size: {}", uncompressedData.length);
			
			byte[] uncompressedDataRest = null;
			
			readCycle: while (readGLB < dictInfo.getAbbreviationsSize()) {
				
				int curUncompLength = uncompressedData.length;
				int rest = dictInfo.getAbbreviationsSize() - readGLB;
				if (uncompressedData.length > rest) {
					curUncompLength = rest;
				}
				
				int readCNT = 0;
				if (uncompressedDataRest != null) {
					String lastWordPiece = new String(uncompressedDataRest, dictInfo.getTransCodepageName());
					log.info("Last Abbreviation Piece: {}", lastWordPiece);
					System.arraycopy(uncompressedDataRest, 0, uncompressedData, 0, uncompressedDataRest.length);
					readCNT = uncompressedDataRest.length;
					uncompressedDataRest = null;
				}
				
				while(readCNT < curUncompLength) {
										
					int readCUR = iis.read(uncompressedData, readCNT, curUncompLength - readCNT);
					if (readCUR == -1) {
						log.error("Reached unexpected end of file");
						break readCycle;
					} else if (readCUR == 0) {
						log.error("Couldn't read more data from the compressed stream");
						break readCycle;					
					}
					readCNT += readCUR;
				}
				readGLB += readCNT;
	
				log.info("Load Cycle | Bytes Read: {}, Bytes Total: {}", readGLB, dictInfo.getAbbreviationsSize());
				
				int startIdx = 0;
				for (int i = 0; i < curUncompLength; i++) {
					if (uncompressedData[i] == 0) {				
						String gram = new String(uncompressedData, startIdx, i - startIdx, dictInfo.getTransCodepageName());
						int idx = gram.indexOf("  ");
						if (idx != -1) {
							abbs.put(gram.substring(0, idx), gram.substring(idx + 2));
						} else {
							throw new DataFormatException("Could not parse abbreviation '" + gram + "'. Should be like 'abbrev_word\032\032description'");
						}
						startIdx = i + 1;
					}
				}
				
				if (curUncompLength > startIdx) {
					uncompressedDataRest = new byte[curUncompLength - startIdx];
					System.arraycopy(uncompressedData, startIdx, uncompressedDataRest, 0, uncompressedDataRest.length);
					readGLB -= uncompressedDataRest.length; 
				}
			
			}
			
			if (readGLB != dictInfo.getAbbreviationsSize()) {
				throw new DataFormatException("Uncompressed Abbreviations' size is invalid: should be " + dictInfo.getAbbreviationsSize() + ", got " + readGLB);
			}
			
			if (dictInfo.getAbbreviationsNumber() != abbs.size()) {
				log.error("Abbreviations number differs from the expected number | Expected Number: {}, Actual: {}", dictInfo.getAbbreviationsNumber(), abbs.size());
			}
			
			if (uncompressedDataRest != null) {
				log.error("Uncompressed Data Rest Size: {}", uncompressedDataRest.length);
			}
			
		}

		log.info("Load Abbreviations | Abbreviations Loaded: " + abbs.size() + ", Expected Abbreviations Number: " + dictInfo.getAbbreviationsNumber() + ", Cycles: " + (readGLB/UNCOMPRESSED_BUFFER_SIZE + 1));
		return Collections.unmodifiableMap(abbs);
	}
	
	public static int[] loadBlockOffsets(LittleEndianRandomAccessFile raf, ZDHeader dictInfo) throws IOException {
		int[] blockOffsets = new int[dictInfo.getTransBlocksNumber()];
		int newPos = dictInfo.getWordsStartPosition() + dictInfo.getWordsZSize() + dictInfo.getAbbreviationsZSize();
		raf.seek(newPos);

		int blockCountBytes = 4 * dictInfo.getTransBlocksNumber();
		log.info("Block Offsests Size: " + blockCountBytes);
		
		byte[] blockOffsetsBytes = new byte[blockCountBytes];
		
		raf.read(blockOffsetsBytes);
		ByteBuffer bbuf = ByteBuffer.wrap(blockOffsetsBytes);
		bbuf.order(ByteOrder.LITTLE_ENDIAN);
		bbuf.asIntBuffer().get(blockOffsets);
		
		return blockOffsets;
	}

}

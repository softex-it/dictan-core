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

package info.softex.dictionary.core.io;

import info.softex.dictionary.core.utils.StringUtils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * The wrapper for the BufferedRandomAccessFile with a set of helper methods
 * to read lines from different positions of a file. 
 * 
 * It's able to maintain initial read and non-initial reads, and supports 
 * <code>StringProcessor</code> to process the read string.
 * 
 * @since version 4.6, 02/01/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class TextLineReader {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected final File file;
	protected final BufferedRandomAccessFile raf;
	protected StringProcessor stringProcessor = null;
	
	protected String lastLine = null;
	protected long lastPointer = 0;
	protected long linesRead = 0;
	
	protected boolean loadReadMode = true;
	
	public TextLineReader(File inFile, StringProcessor inStringProcessor) throws IOException {
		this.file = inFile;
		this.raf = new BufferedRandomAccessFile(inFile, "r");
		this.stringProcessor = inStringProcessor;
	}
	
	public String getLastLine() {
		return lastLine;
	}
	
	public String getLastLineTrimmed() {
		return lastLine != null ? lastLine.trim() : null;
	}
	
	public String getLastLineLTrimmed() {
		return StringUtils.ltrim(lastLine);
	}
	
	public boolean isLastLineBlank() {
		return lastLine == null || lastLine.trim().isEmpty();
	}
	
	public boolean isLastLineNotBlank() {
		return !isLastLineBlank();
	}
	
	public long getLastPointer() {
		return lastPointer;
	}
	
	public void readEmptyLines(String logMessage) throws IOException {
		while (readLine() != null && lastLine.trim().isEmpty()) {
			if (logMessage != null) {
				log.warn(logMessage);
			}
		}
	}
	
	public void setLoadReadMode(boolean inLoadReadMode) {
		this.loadReadMode = inLoadReadMode;
	}
	
	public boolean isLoadReadMode() {
		return loadReadMode;
	}
	
	public String readLine() throws IOException {
		
		lastPointer = raf.getFilePointer();
		
		// Apply string processor if it's not null
		if (stringProcessor != null) {
			lastLine = stringProcessor.process(raf.readLineUTF());	
		} else {
			lastLine = raf.readLineUTF();
		}

		// Increase lines count only if it's initial read mode and not EOF
		if (loadReadMode && lastLine != null) {
			linesRead++;
		}
		
		//System.out.println("READ LINE " + linesRead + " " + lastLine);
		
		return lastLine;
		
	}
	
	public String readLine(long inPointer) throws IOException {
		raf.seek(inPointer);
		return readLine();
	}
	public long getLinesRead() {
		return linesRead;
	}
	
	public void close() throws IOException {
		if (raf != null) {
			raf.close();
		}
	}
	
	public String getFilePath() {
		return file.getPath();
	}
	
	// Utility classes -----------------------------------------
	
	public static interface StringProcessor {
		public String process(String str);
	}
	
	public static class InitialBomRemover implements StringProcessor {
		protected final static String UTF8_BOM = "\uFEFF";
		public String process(String str) {
		    if (str != null && str.startsWith(UTF8_BOM)) {
		    	str = str.substring(1);
		    }
		    return str;
		}
	}

}

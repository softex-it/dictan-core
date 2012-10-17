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

package info.softex.dictionary.core.formats.zd.io.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.1, 04/13/2011
 * 
 * @modified version 3.4, 06/27/2012
 * 
 * @author Dmitry Viktorov
 *
 */
public class TIIStreamFactory {
	
	private static final Logger log = LoggerFactory.getLogger(TIIStreamFactory.class);

	private final File inputFile;

	private final long baseFileSkipLength;
	
	private final int bufferSize;
	
	public TIIStreamFactory(File inputFile, long baseFileSkipLength, int bufferSize) {
		this.inputFile = inputFile;
		this.baseFileSkipLength = baseFileSkipLength;
		this.bufferSize = bufferSize;
	}
	
	public TIIStream createTIIStream() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(this.inputFile);
			fis.skip(this.baseFileSkipLength);
		} catch (IOException e) {
			log.error("Error", e);
			throw new RuntimeException("Couldn't create a stream from file: " + e.getMessage());
		}
		return new TIIStream(fis, new Inflater(), this.bufferSize);
	}
	
	public TIIStream createTIIStream(long position) {
		TIIStream is = createTIIStream();
		if (position != 0) {
			try {
				is.skip(position);
			} catch (IOException e) {
				log.error("Error", e);
				throw new RuntimeException("Couldn't skip the stream " + is + ": " + e.getMessage());
			}
		}
		return is;
	}
	
}

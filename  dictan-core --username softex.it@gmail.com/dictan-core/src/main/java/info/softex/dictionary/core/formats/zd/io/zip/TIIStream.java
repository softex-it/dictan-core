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

import info.softex.dictionary.core.io.SmartInflaterInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 1.2, 09/28/2010
 *
 * @modified version 1.4, 12/19/2010
 * @modified version 2.1, 04/10/2011
 * @modified version 2.2, 05/08/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class TIIStream extends SmartInflaterInputStream {
	
	private final Logger log = LoggerFactory.getLogger(TIIStream.class.getSimpleName());

	private final int bufferSize;

	public TIIStream(InputStream is, Inflater inflater, int bufferSize) {
		super(is, inflater, bufferSize);
		this.bufferSize = bufferSize;
	}

	private byte[] getRemainingBuffer() {
		byte remBuffer[] = new byte[inf.getRemaining()];
		int k = len - inf.getRemaining();
		System.arraycopy(buf, k, remBuffer, 0, inf.getRemaining());
		return remBuffer;
	}

	public TIIStream createNewZippedSetIS() throws IOException {
		
		byte remBuffer[] = getRemainingBuffer();

		log.debug("Load | Remaining Buffer Size: {}", remBuffer.length);
		
		Inflater inflater = new Inflater();
		inflater.setInput(remBuffer);
		return new TIIStream(in, inflater, bufferSize);
		
	}
		
}

/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2014  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * 
 * The InflaterInputStream implementation which guarantees the requested number of bytes will be
 * read or skipped. The class also presents convenience methods.
 * 
 * @since version 1.2, 09/28/2010
 *
 * @modified version 2.6, 09/24/2011
 * @modified version 5.1, 02/23/2017
 *
 * @author Dmitry Viktorov
 * 
 */
public class ReliableInflaterInputStream extends InflaterInputStream {
	
	protected long totalBytesPassed;
	
	public ReliableInflaterInputStream(InputStream is) {
		super(is);
	}

	public ReliableInflaterInputStream(InputStream is, Inflater inflater) {
		super(is, inflater);
	}
	
	public ReliableInflaterInputStream(InputStream is, Inflater inflater, int bufferSize) {
		super(is, inflater, bufferSize);
	}
		
	/**
	 * skip() and read() methods call this method, so the passed bytes 
	 * counter is incremented here. There is also a loop to make sure that 
	 * all the maximum possible requested bytes read.
	 */
	@Override 
	public int read(byte[] buffer, int offset, int length) throws IOException {
		int read = 0;
		while(read < length && super.available() > 0) {
			int curRead = super.read(buffer, read, length - read);
			if (curRead >= 0) {
				read += curRead;
				this.totalBytesPassed += curRead;
			}
		}
		return read;
	}
	
	public int readInt() throws IOException {
		byte[] intData = new byte[4];
		if (read(intData, 0, intData.length) < 4) {
			throw new IOException("Couldn't read int: no enough bytes available");
		}
		return ByteBuffer.wrap(intData).getInt();
	}
	
	public long getTotalBytesPassed() {
		return this.totalBytesPassed;
	}

}

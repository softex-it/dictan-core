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

package info.softex.dictionary.core.formats.zd.io;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @since version 1.0, 09/27/2010
 * 
 * @author Dmitry Viktorov
 * 
 */
public class LittleEndianDataInputStream extends InputStream implements DataInput {
	
	private DataInputStream dis = null;
	private InputStream is = null;
	
	// Input buffer
	private byte buffer[];

	public LittleEndianDataInputStream(InputStream is) {
		this.is = is;
		this.dis = new DataInputStream(is);
		this.buffer = new byte[8];
	}

	public int available() throws IOException {
		return dis.available();
	}

	public final short readShort() throws IOException {
		this.dis.readFully(buffer, 0, 2);
		return (short)((buffer[1] & 0xff) << 8 | (buffer[0] & 0xff));
	}

	public final int readUnsignedShort() throws IOException {
		this.dis.readFully(buffer, 0, 2);
		return ((buffer[1] & 0xff) << 8 | (buffer[0] & 0xff));
	}

	public final char readChar() throws IOException {
		this.dis.readFully(buffer, 0, 2);
		return (char) ((buffer[1] & 0xff) << 8 | (buffer[0] & 0xff));
	}

	public final int readInt() throws IOException {
		this.dis.readFully(buffer, 0, 4);
		return (buffer[3]) << 24 | (buffer[2] & 0xff) << 16 | (buffer[1] & 0xff) << 8 | (buffer[0] & 0xff);
	}

	public final long readLong() throws IOException {
		this.dis.readFully(buffer, 0, 8);
		return (long)(buffer[7]) << 56 | (long)(buffer[6] & 0xff) << 48 |
				(long)(buffer[5] & 0xff) << 40 | (long)(buffer[4] & 0xff) << 32 |
				(long)(buffer[3] & 0xff) << 24 | (long)(buffer[2] & 0xff) << 16 |
				(long)(buffer[1] & 0xff) << 8 | (long)(buffer[0] & 0xff);
	}

	public final float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	public final double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	public final int read(byte b[], int off, int len) throws IOException {
		return is.read(b, off, len);
	}

	public final void readFully(byte b[]) throws IOException {
		this.dis.readFully(b, 0, b.length);
	}

	public final void readFully(byte b[], int off, int len) throws IOException {
		this.dis.readFully(b, off, len);
	}

	public final boolean readBoolean() throws IOException {
		return this.dis.readBoolean();
	}

	public final byte readByte() throws IOException {
		return this.dis.readByte();
	}

	public int read() throws IOException {
		return this.is.read();
	}

	public final int readUnsignedByte() throws IOException {
		return this.dis.readUnsignedByte();
	}

	@Deprecated
	public final String readLine() throws IOException {
		return this.dis.readLine();
	}

	public final String readUTF() throws IOException {
		return this.dis.readUTF();
	}

	public final int skipBytes(int n) throws IOException {
		return this.dis.skipBytes(n);
	}
	
	public final void close() throws IOException {
		this.dis.close();
	}

}
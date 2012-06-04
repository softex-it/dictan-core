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

package info.softex.dictionary.core.formats.zd.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * @since version 1.0, 09/27/2010
 * 
 * @author Dmitry Viktorov
 * 
 */
public class LittleEndianRandomAccessFile implements DataInput, DataOutput {
	
	protected RandomAccessFile raf = null;
	
	// Input buffer
	byte buffer[] = null; 

	public LittleEndianRandomAccessFile(File f, String mode) throws IOException {
		this.raf = new RandomAccessFile(f, mode);
		this.buffer = new byte[8];
	}

	public LittleEndianRandomAccessFile(String fileName, String mode) throws IOException {
		this(new File(fileName), mode);
	}
	
	public final short readShort() throws IOException {
		this.raf.readFully(buffer, 0, 2);
		return (short)((buffer[1] & 0xff) << 8 | (buffer[0] & 0xff));
	}

	public final int readUnsignedShort() throws IOException {
		this.raf.readFully(buffer, 0, 2);
		return ((buffer[1] & 0xff) << 8 | (buffer[0] & 0xff));
	}

	public final char readChar() throws IOException {
		this.raf.readFully(buffer, 0, 2);
		return (char)((buffer[1] & 0xff) << 8 | (buffer[0] & 0xff));
	}

	public final int readInt() throws IOException {
		this.raf.readFully(buffer, 0, 4);
		return (buffer[3]) << 24 | (buffer[2] & 0xff) << 16 | (buffer[1] & 0xff) << 8 | (buffer[0] & 0xff);
	}

	public final long readLong() throws IOException {
		this.raf.readFully(buffer, 0, 8);
		return (long)(buffer[7]) << 56 |
		 		(long)(buffer[6] & 0xff) << 48 | (long)(buffer[5] & 0xff) << 40 |
		 		(long)(buffer[4] & 0xff) << 32 | (long)(buffer[3] & 0xff) << 24 |
		 		(long)(buffer[2] & 0xff) << 16 | (long)(buffer[1] & 0xff) << 8 |
		 		(long)(buffer[0] & 0xff);
	}

	public final float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	public final double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}
	
	public final void writeShort(int v) throws IOException {
		this.buffer[0] = (byte)v;
		this.buffer[1] = (byte)(v >> 8);
		this.raf.write(buffer, 0, 2);
	}

	public final void writeChar(int v) throws IOException {
		this.writeShort(v);
	}

	public final void writeInt(int v) throws IOException {
		this.buffer[0] = (byte)v;
		this.buffer[1] = (byte)(v >> 8);
		this.buffer[2] = (byte)(v >> 16);
		this.buffer[3] = (byte)(v >> 24);
		this.raf.write(buffer, 0, 4);
	}

	public final void writeLong(long v) throws IOException {
		this.buffer[0] = (byte) v;
		this.buffer[1] = (byte) (v >> 8);
		this.buffer[2] = (byte) (v >> 16);
		this.buffer[3] = (byte) (v >> 24);
		this.buffer[4] = (byte) (v >> 32);
		this.buffer[5] = (byte) (v >> 40);
		this.buffer[6] = (byte) (v >> 48);
		this.buffer[7] = (byte) (v >> 56);
		this.raf.write(this.buffer, 0, 8);
	}

	public final void writeFloat(float flt) throws IOException {
		writeInt(Float.floatToIntBits(flt));
	}

	public final void writeDouble(double dbl) throws IOException {
		writeLong(Double.doubleToLongBits(dbl));
	}

	public final void writeChars(String chars) throws IOException {
		for (int i = 0; i < chars.length(); i++) {
			writeChar(chars.charAt(i));
		}
	} 

	
	// RandomAccessFile wrappers --------------------------------

	public final long getFilePointer() throws IOException {
		return raf.getFilePointer();
	}

	public final FileDescriptor getFD() throws IOException {
		return raf.getFD();
	}

	public final long length() throws IOException {
		return raf.length();
	}

	public final int read(byte[] byteArray) throws IOException {
		return raf.read(byteArray);
	}
	
	public final int read(byte[] byteArray, int offset, int length) throws IOException {
		return raf.read(byteArray, offset, length);
	}

	public final int read() throws IOException {
		return raf.read();
	}

	public final void readFully(byte[] byteArray) throws IOException {
		this.raf.readFully(byteArray, 0, byteArray.length);
	}

	public final void readFully(byte[] byteArray, int offset, int lenght) throws IOException {
		this.raf.readFully(byteArray, offset, lenght);
	}

	public final boolean readBoolean() throws IOException {
		return this.raf.readBoolean();
	}

	public final byte readByte() throws IOException {
		return this.raf.readByte();
	}

	public final int readUnsignedByte() throws IOException {
		return this.raf.readUnsignedByte();
	}

	public final String readLine() throws IOException {
		return this.raf.readLine();
	}

	public final String readUTF() throws IOException {
		return this.raf.readUTF();
	}

	public final synchronized void write(int bt) throws IOException {
		this.raf.write(bt);
	}

	public final synchronized void write(byte[] byteArray, int offset, int length) throws IOException {
		this.raf.write(byteArray, offset, length);
	}

	public final void writeBoolean(boolean bl) throws IOException {
		this.raf.writeBoolean(bl);
	}

	public final void writeBytes(String str) throws IOException {
		this.raf.writeBytes(str);
	}

	public final void writeByte(int bt) throws IOException {
		this.raf.writeByte(bt);
	}

	public final void writeUTF(String utf) throws IOException {
		this.raf.writeUTF(utf);
	}

	public final void write(byte[] byteArray) throws IOException {
		this.raf.write(byteArray, 0, byteArray.length);
	}
	
	public final int skipBytes(int n) throws IOException {
		return this.raf.skipBytes(n);
	}
	
	public final void seek(long pos) throws IOException {
		this.raf.seek(pos);
	}
	
	public final void close() throws IOException {
		this.raf.close();
	}

} 
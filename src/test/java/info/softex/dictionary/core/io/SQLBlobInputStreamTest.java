/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2017  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.PreparedStatement;

import org.junit.Test;

/**
 * 
 * Basic tests of SQLBlobInputStream.
 *
 * @since       version 5.1, 02/18/2017
 *
 * @author Dmitry Viktorov
 *
 */
public class SQLBlobInputStreamTest {
	
	private final static int STREAM_SIZE = 1950;

	private final static byte[] INIT_BUFFER = new byte[270];
	static {
		for (int i = 0; i < INIT_BUFFER.length; i++) {
			INIT_BUFFER[i] = (byte) i;
		}	
	}

	@Test
	public void testBlobStreamReadBytes() throws Exception {
		
		SQLBlobInputExtStream is = new SQLBlobInputExtStream(INIT_BUFFER, null, 123, STREAM_SIZE);
		
		assertEquals(true, is.markSupported());
		assertEquals(STREAM_SIZE, is.getCount());
		assertEquals(STREAM_SIZE, is.available());
		
		int readBlocks = 0;
		int readBytes = 0;
		while (readBytes < is.getCount()) {
			assertEquals((byte) readBlocks, (byte) is.read());
			readBytes++;
			assertEquals(STREAM_SIZE - readBytes, is.available());
			for (int i = 1; i < INIT_BUFFER.length && is.available() > 0; i++) {
				assertEquals((byte) i, (byte) is.read());
				readBytes++;
				assertEquals(STREAM_SIZE - readBytes, is.available());
			}
			readBlocks++;
			assertEquals(readBlocks - 1, is.dataCallCounter);
			assertEquals(readBlocks - 1, is.dataCallLastBufferNumber);

			//System.out.println(readBlocks);
		}
		
		// Test completion
		assertEquals(STREAM_SIZE, readBytes);
		assertEquals(0, is.available());
		assertEquals(7, is.dataCallCounter);
		assertEquals(7, is.dataCallLastBufferNumber);
		
		// Test reset
		is.reset();
		assertEquals(STREAM_SIZE, is.available());
		
		is.close();
		
	}
	
	@Test
	public void testBlobStreamReadByteArray() throws Exception {
		
		SQLBlobInputExtStream is = new SQLBlobInputExtStream(INIT_BUFFER, null, 123, STREAM_SIZE);
		
		assertEquals(true, is.markSupported());
		assertEquals(STREAM_SIZE, is.getCount());
		assertEquals(STREAM_SIZE, is.available());
		
		int offset = 20;
		byte[] testBuffer = new byte[INIT_BUFFER.length * 3];
		
		is.read(testBuffer, offset, testBuffer.length - offset);
		
		// Check number of zero-bytes
		int zeroNum = 0;
		for (int i = 0; i < testBuffer.length; i++) {
			if (testBuffer[i] == 0) {
				zeroNum++;
			}
		}
		
		// 4 more because of 1st el in 1st buffer, and byte conversion in 2 buffers
		assertEquals(offset + 3, zeroNum);
		
		// Check read bytes
		int readBytes = 0;
		for (; readBytes < testBuffer.length - offset; readBytes++) {
			int bt = readBytes % INIT_BUFFER.length;
			bt = bt > 0 ? bt : readBytes / INIT_BUFFER.length;
			assertEquals("Error in iteration " + readBytes, (byte) bt, testBuffer[readBytes + offset]);
		}
		
		// Main test completion
		assertEquals(readBytes, testBuffer.length - offset);
		assertEquals(STREAM_SIZE - readBytes, is.available());
		assertEquals(2, is.dataCallCounter);
		assertEquals(2, is.dataCallLastBufferNumber);
		
		// Read the rest of bytes
		testBuffer = new byte[STREAM_SIZE - readBytes];
		is.read(testBuffer);
		
		// Full read completion
		assertEquals(0, is.available());
		assertEquals(7, is.dataCallCounter);
		assertEquals(7, is.dataCallLastBufferNumber);
		
		is.close();
		
	}
	
	@Test
	public void testBlobStreamByteArrayStreamBehavior() throws Exception {
		
		SQLBlobInputExtStream is = new SQLBlobInputExtStream(INIT_BUFFER, null, -1, INIT_BUFFER.length - 10);
		
		assertEquals(true, is.markSupported());
		assertEquals(INIT_BUFFER.length, is.getCount());
		assertEquals(INIT_BUFFER.length, is.available());
		
		is.skip(INIT_BUFFER.length -1);
		
		assertEquals((byte) INIT_BUFFER.length - 1, (byte) is.read());
		assertEquals(0, is.dataCallCounter);
		assertEquals(0, is.dataCallLastBufferNumber);
		
		is.close();
		
	}
	
	private class SQLBlobInputExtStream extends SQLBlobInputStream {
		
		int dataCallCounter = 0;
		int dataCallLastBufferNumber = 0;
		
		public SQLBlobInputExtStream(byte[] inBuf, PreparedStatement inStatement, int inBlockId, int inLength) {
			super(inBuf, inStatement, inBlockId, inLength);
		}
		
		@Override
		public byte[] getBuffer(int inBufferNumber) throws IOException {
			dataCallCounter++;
			dataCallLastBufferNumber = inBufferNumber;
			byte[] buffer = INIT_BUFFER.clone();
			buffer[0] = (byte) inBufferNumber;
			return buffer;
		}
		
		public int getCount() {
			return count;
		}
		
	}
	
}

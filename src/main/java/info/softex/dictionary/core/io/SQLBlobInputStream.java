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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * FDBSQLBlobInputStream is a dynamic DB Blob stream. It's purpose is to override the Android SQLite Blob size limitation, 
 * so it reads blobs by smaller parts. It's based on ByteArrayInputStream. If stream size is equal or smaller than the 
 * initial buffer, it behaves the same way as ByteArrayInputStream.
 *
 * @since       version 5.1, 02/18/2017
 *
 * @author Dmitry Viktorov
 *
 */
public class SQLBlobInputStream extends InputStream {

    private static final Logger log = LoggerFactory.getLogger(SQLBlobInputStream.class.getSimpleName());

    protected final PreparedStatement selectStatement;
    protected final int blockId;
    protected final int maxBufferSize;

    protected byte curBuffer[];
    protected int curBufferNumber;

    protected int pos;
    protected int mark = 0;
    protected int count;
    
    public SQLBlobInputStream(byte[] inBuf) {
    	this(inBuf, null, -1, -1);
    }

    public SQLBlobInputStream(byte[] inBuf, PreparedStatement inStatement, int inBlockId, int inLength) {
    	
    	// Set the right length
    	if (inLength < inBuf.length) {
    		inLength = inBuf.length;
    	}
    	
        this.selectStatement = inStatement;
        this.blockId = inBlockId;
        this.curBuffer = inBuf;
        this.maxBufferSize = inBuf.length;
        this.curBufferNumber = 0;
        this.pos = 0;
        this.count = inLength;
    }

    @Override
    public synchronized int read() throws IOException {
        if (pos < count) {
            return readByte(pos++) & 0xff;
        } else {
            return -1;
        }
    }
    
    public synchronized int read(byte[] b, int off, int len) throws IOException {
    	
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        }

        if (pos >= count) {
            return -1;
        }

        int avail = count - pos;
        if (len > avail) {
            len = avail;
        }
        if (len <= 0) {
            return 0;
        }
        
        // Custom implementation
        readByteArray(b, off, len);
        
        return len;
        
    }
    
    @Override
    public synchronized long skip(long n) {
        long k = count - pos;
        if (n < k) {
            k = n < 0 ? 0 : n;
        }
        pos += k;
        return k;
    }

    @Override
    public synchronized int available() {
        return count - pos;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void mark(int readAheadLimit) {
        mark = pos;
    }

    @Override
    public synchronized void reset() {
        pos = mark;
    }

    @Override
    public void close() throws IOException {
    }

    // Protected methods ================================================

    /**
     * Custom byte read.
     */
    protected byte readByte(int pos) throws IOException {
        int reqBufferNumber = pos / maxBufferSize;
        int bufferPos = pos - reqBufferNumber * maxBufferSize;
        updateBuffer(reqBufferNumber);
        byte result = curBuffer[bufferPos];
        return result;
    }
    
    /**
     * Custom byte array read.
     */
    protected void readByteArray(byte[] inBuffer, int off, int len) throws IOException {
       
    	
    	while (len > 0) {
    		
            int reqBufferNumber = pos / maxBufferSize;
            int bufferPos = pos - reqBufferNumber * maxBufferSize;
            int curBufferMaxLength = maxBufferSize - bufferPos;
            int curLen = len > curBufferMaxLength ? curBufferMaxLength : len;
            
            updateBuffer(reqBufferNumber);
            
        	System.arraycopy(curBuffer, bufferPos, inBuffer, off, curLen);
        	len -= curLen;
        	off += curLen;
        	pos += curLen;
        	
            //System.out.println("Read Point: " + i + " " + off + " " + len + " " + curLen);
        	
		}
    	
    }

    protected boolean updateBuffer(int inBufferNumber) throws IOException {

        if (curBufferNumber == inBufferNumber) {
            return false;
        }
        
        this.curBuffer = getBuffer(inBufferNumber);
        this.curBufferNumber = inBufferNumber;
        
        return true;

    }

    protected byte[] getBuffer(int inBufferNumber) throws IOException {

        int posStart = inBufferNumber * maxBufferSize + 1;
        //int posEnd = (inBufferNumber + 1) * maxBufferSize;

        try {

            selectStatement.setInt(1, posStart);
            selectStatement.setInt(2, maxBufferSize);
            selectStatement.setInt(3, blockId);

            ResultSet resRS = selectStatement.executeQuery();
            if (resRS.next()) {
                byte[] buffer = resRS.getBytes(2);
                resRS.close();
                log.debug("SQL Input Stream - retrieved buffer {} with size {}", inBufferNumber, buffer.length);
                return buffer;
            } else {
                resRS.close();
                throw new IOException("Buffer for Block " + blockId + " was not returned");
            }

        } catch (Exception e) {
            log.error("Error while retrieving buffer={}, start={}, length={}, stream_length={}", inBufferNumber, posStart, maxBufferSize, count, e);
            throw new IOException("Buffer for Block " + blockId + " couldn't be loaded: " + e.getMessage(), e);
        }

    }

}

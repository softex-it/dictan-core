/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2015  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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
import java.io.PushbackInputStream;

/**
 * Checks the Unicode encoding of the text file by its BOM (Byte Order Mark).
 * The recognized encodings are:
 * 
 * UTF-8, UTF-16LE, UTF-16BE, UTF-32LE, UTF-32BE
 * 
 * See http://www.unicode.org/unicode/faq/utf_bom.html
 * 
 * @since version 4.6, 02/01/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class UnicodeInputStream {
	
	public final static String UTF8 = "UTF-8";
	public final static String UTF16LE = "UTF-16LE";
	public final static String UTF16BE = "UTF-16BE";
	public final static String UTF32LE = "UTF-32LE";
	public final static String UTF32BE = "UTF-32BE";
	
	/**
	 * BOM Marker for UTF 8.
	 */
	public static final UnicodeBOM UTF8_BOM = new UnicodeBOM(UTF8,
			new byte[] { (byte) 0xef, (byte) 0xbb, (byte) 0xbf });

	/**
	 * BOM Marker for UTF 16, little endian.
	 */
	public static final UnicodeBOM UTF16LE_BOM = new UnicodeBOM(UTF16LE,
			new byte[] { (byte) 0xff, (byte) 0xfe });

	/**
	 * BOM Marker for UTF 16, big endian.
	 */
	public static final UnicodeBOM UTF16BE_BOM = new UnicodeBOM(UTF16BE,
			new byte[] { (byte) 0xfe, (byte) 0xff });

	/**
	 * BOM Marker for UTF 32, little endian.
	 */
	public static final UnicodeBOM UTF32LE_BOM = new UnicodeBOM(UTF32LE,
			new byte[] { (byte) 0xff, (byte) 0xfe, (byte) 0x00, (byte) 0x00 });

	/**
	 * BOM Marker for UTF 32, big endian.
	 */
	public static final UnicodeBOM UTF32BE_BOM = new UnicodeBOM(UTF32BE,
			new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0xfe, (byte) 0xff });

	/** The maximum amount of bytes to read for a BOM */
	private static final int MAX_BOM_SIZE = 4;

	/** Buffer for BOM reading */
	private byte[] buf = new byte[MAX_BOM_SIZE];

	/** Buffer pointer. */
	private int pos = 0;

	/** The stream encoding as read from the BOM or null. */
	private final String encoding;

	/** True if the BOM itself should be skipped and not read. */
	private final boolean skipBOM;

	private final PushbackInputStream inputStream;

	/**
	 * Creates a new UnicodeInputStream object. Skips a BOM which defines the
	 * file encoding.
	 * 
	 * @param inputStream
	 *            The input stream to use for reading.
	 */
	public UnicodeInputStream(final InputStream inputStream) throws IllegalStateException, IOException {
		this(inputStream, true);
	}

	/**
	 * Creates a new UnicodeInputStream object.
	 * 
	 * @param inputStream
	 *            The input stream to use for reading.
	 * @param skipBOM
	 *            If this is set to true, a BOM read from the stream is
	 *            discarded. This parameter should normally be true.
	 */
	public UnicodeInputStream(final InputStream inputStream, boolean skipBOM) throws IllegalStateException, IOException {
		super();

		this.skipBOM = skipBOM;
		this.inputStream = new PushbackInputStream(inputStream, MAX_BOM_SIZE);

		try {
			this.encoding = readEncoding();
		} catch (IOException ioe) {
			throw new IllegalStateException("Could not read BOM from Stream");
		}
	}

	/**
	 * Returns true if the input stream discards the BOM.
	 * 
	 * @return True if the input stream discards the BOM.
	 */
	public boolean isSkipBOM() {
		return skipBOM;
	}

	/**
	 * Read encoding based on BOM.
	 * 
	 * @return The encoding based on the BOM.
	 * 
	 * @throws IllegalStateException
	 *             When a problem reading the BOM occurred.
	 */
	public String getUnicodeEncoding() {
		return encoding;
	}
	
	/**
	 * Checks if 
	 * 
	 * @return
	 */
	public boolean isUnicodeEncodingUndefinedOrUTF8() {
		return getUnicodeEncoding() != null ? UTF8.equals(getUnicodeEncoding()) : true;
	}

	/**
	 * This method gets the encoding from the stream contents if a BOM exists.
	 * If no BOM exists, the encoding is undefined.
	 * 
	 * @return The encoding of this streams contents as decided by the BOM or
	 *         null if no BOM was found.
	 */
	protected String readEncoding() throws IOException {
		pos = 0;

		UnicodeBOM encoding = null;

		// Read first byte.
		if (readByte()) {
			
			// Build a list of matches
			//
			// 00 00 FE FF --> UTF 32 BE
			// EF BB BF --> UTF 8
			// FE FF --> UTF 16 BE
			// FF FE --> UTF 16 LE
			// FF FE 00 00 --> UTF 32 LE
			switch (buf[0]) {
			case (byte) 0x00: // UTF32 BE
				encoding = match(UTF32BE_BOM, null);
				break;
			case (byte) 0xef: // UTF8
				encoding = match(UTF8_BOM, null);
				break;
			case (byte) 0xfe: // UTF16 BE
				encoding = match(UTF16BE_BOM, null);
				break;
			case (byte) 0xff: // UTF16/32 LE
				encoding = match(UTF16LE_BOM, null);

				if (encoding != null) {
					encoding = match(UTF32LE_BOM, encoding);
				}
				break;

			default:
				encoding = null;
				break;
			}
		}

		pushback(encoding);

		return (encoding != null) ? encoding.getEncoding() : null;
	}

	private final UnicodeBOM match(final UnicodeBOM matchEncoding,
			final UnicodeBOM noMatchEncoding) throws IOException {
		byte[] bom = matchEncoding.getBytes();

		for (int i = 0; i < bom.length; i++) {
			if (pos <= i) // Byte has not yet been read
			{
				if (!readByte()) {
					return noMatchEncoding;
				}
			}

			if (bom[i] != buf[i]) {
				return noMatchEncoding;
			}
		}

		return matchEncoding;
	}

	private final boolean readByte() throws IOException {
		int res = inputStream.read();
		if (res == -1) {
			return false;
		}

		if (pos >= buf.length) {
			throw new IOException("BOM read error");
		}

		buf[pos++] = (byte) res;
		return true;
	}

	private final void pushback(final UnicodeBOM matchBOM) throws IOException {
		int count = pos; // By default, all bytes are pushed back.
		int start = 0;

		if (matchBOM != null && skipBOM) {
			// We have a match (some bytes are part of the BOM)
			// and we want to skip the BOM. Push back only the bytes
			// after the BOM.
			start = matchBOM.getBytes().length;
			count = (pos - start);

			if (count < 0) {
				throw new IllegalStateException(
						"Match has more bytes than available!");
			}
		}

		inputStream.unread(buf, start, count);
	}

	/**
	 * @see java.io.InputStream#close()
	 */
	public void close() throws IOException {
		inputStream.close();
	}

	/**
	 * @see java.io.InputStream#available()
	 */
	public int available() throws IOException {
		return inputStream.available();
	}

	/**
	 * @see java.io.InputStream#mark(int)
	 */
	public void mark(final int readlimit) {
		inputStream.mark(readlimit);
	}

	/**
	 * @see java.io.InputStream#markSupported()
	 */
	public boolean markSupported() {
		return inputStream.markSupported();
	}

	/**
	 * @see java.io.InputStream#read()
	 */
	public int read() throws IOException {
		return inputStream.read();
	}

	/**
	 * @see java.io.InputStream#read(byte[])
	 */
	public int read(final byte[] b) throws IOException {
		return inputStream.read(b);
	}

	/**
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	public int read(final byte[] b, final int off, final int len)
			throws IOException {
		return inputStream.read(b, off, len);
	}

	/**
	 * @see java.io.InputStream#reset()
	 */
	public void reset() throws IOException {
		inputStream.reset();
	}

	/**
	 * @see java.io.InputStream#skip(long)
	 */
	public long skip(final long n) throws IOException {
		return inputStream.skip(n);
	}

	/**
	 * Helper class to bundle encoding and BOM marker.
	 */
	static final class UnicodeBOM {
		private final String encoding;

		private final byte[] bytes;

		private UnicodeBOM(final String encoding, final byte[] bytes) {
			this.encoding = encoding;
			this.bytes = bytes;
		}

		String getEncoding() {
			return encoding;
		}

		byte[] getBytes() {
			return bytes;
		}
	}

}

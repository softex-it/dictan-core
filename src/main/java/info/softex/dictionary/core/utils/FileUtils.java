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

package info.softex.dictionary.core.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 1.0,		06/10/2012
 * 
 * @modified version 3.7,	11/27/2013
 * @modified version 4.6,	02/17/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FileUtils {
	
	private static final int BUF_SIZE = 16384; // 16K

	/**
	 * Returns the extension of the file, e.g.:
	 * 
	 * null returns ""
	 * "noextfile" returns ""
	 * "myfile.txt" returns "txt"
	 * 
	 */
	public static String getFileExtension(String f) {
		String ext = "";
		if (f != null) {
			int i = f.lastIndexOf('.');
			if (i > 0 && i < f.length() - 1) {
				ext = f.substring(i + 1);
			}
		}
		return ext;
	}
	
	public static String renameFileExtension(String source, String newExtension) {
		String target;
		String currentExtension = getFileExtension(source);

		if (currentExtension.equals("")) {
			target = source + "." + newExtension;
		} else {
			target = source.replaceFirst(Pattern.quote("." + currentExtension) + "$", Matcher.quoteReplacement("." + newExtension));
		}
		return target;
	}
	
	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	public static boolean directoryExists(String dirPath) {
        if (dirPath == null) {
        	return false;
        } 
    	File dirFile = new File(dirPath);
        return dirFile.exists() && dirFile.isDirectory();
	}
	
	public static boolean fileExists(String filePath) {
        if (filePath == null) {
        	return false;
        } 
    	File file = new File(filePath);
        return file.exists() && file.isFile();
	}
	
	public static String getFileNameFromPath(String filePath) {
		String fn = null;
		// Check file path is not blank
        if (filePath != null && filePath.trim().length() != 0) {
            try {
            	File file = new File(filePath);
            	if (!file.isDirectory()) {
            		fn = file.getName();
            	}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fn;
	}
	
	/**
	 * Checks if the directory is empty. 
	 * Also tries to create the directory if it doesn't exist if the boolean property is true.
	 * All exceptions are ignored if the directory couldn't be created. 
	 * 
	 * @return whether the directory is empty
	 */
	public static boolean isDirectoryEmpty(String inDirectory, FileFilter filter, boolean createNonExistent) {
		File directoryFile = new File(inDirectory);
		if (directoryFile.exists()) {
			File[] dictList = directoryFile.listFiles(filter);
			return (dictList.length == 0 ? true : false);
		} else if (createNonExistent) {
			try {
				directoryFile.mkdirs();
			} catch (Exception e) {
				log.error("Directory couldn't be created", e);
			}
		}
		return true;
	}
	
	/**
	 * Sort files in a file list by segmenting folders and files
	 * 
	 * @param filesList
	 * @return sorted file list
	 */
	public static List<File> sortFiles(File[] filesList) {
		
		// Exclude null pointer exceptions
		if (filesList == null) {
			return null;
		}
		
		ArrayList<File> fileNames = new ArrayList<File>();
		ArrayList<File> resultNames = new ArrayList<File>();

		for (int i = 0; i < filesList.length; i++) {
			if (filesList[i].isDirectory()) {
				resultNames.add(filesList[i]);
			} else {
				fileNames.add(filesList[i]);
			}
		}	
		
		Collections.sort(resultNames);
		Collections.sort(fileNames);
		
		resultNames.addAll(fileNames);
		
		return resultNames;
	}
	
	/**
	 * Get sorted files in a directory using a FileFilter.
	 * 
	 * @param dir
	 * @param fileFilter
	 * @return sorted list of files in a directory
	 */
	public static List<String> getFilesInFolder(File dir, FileFilter fileFilter) throws IOException {
		if (dir == null) {
			log.warn("Directory is null");
			return null;
		}
		File[] filteredFilesList = dir.listFiles(fileFilter);
		log.debug("File list: {}", (Object) filteredFilesList);
		// List may be null if directory doesn't exist or permission denied
		if (filteredFilesList == null) {
			throw new IOException("Couldn't get access to the folder: " + dir.getAbsolutePath());
		}
		List<File> sortedFiles = sortFiles(filteredFilesList);
		List<String> sortedFilesPaths = new ArrayList<String>();
		for (File file : sortedFiles) {
			sortedFilesPaths.add(file.getAbsolutePath());
		}
		return sortedFilesPaths;
	}
	
	/**
	 * Get the temporary directory.
	 * 
	 * @return path to temp directory
	 */
	public static File getTempDirectory() {
		File tempDir = null;
		try {
			tempDir = new File(System.getProperty("java.io.tmpdir"));
		} catch (Exception e) {
			log.error("Error", e);
		}
		return tempDir;
	}
	
	/**
	 * Reads all bytes from a file into a byte array. The method is similar to the one at Guava 18.0.
	 *
	 * @param file the file to read from
	 * @return a byte array containing all the bytes from file
	 * @throws IllegalArgumentException if the file is bigger than the largest possible byte array (2^31 - 1)
	 * @throws IOException if an I/O error occurs
	 */
	public static byte[] toByteArray(File file) throws IOException {
		long expectedSize = file.length();
		
		if (file.length() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("File size is too large to fit in a byte array: " + expectedSize + " bytes");
		}
		
		return toByteArray(new FileInputStream(file));
	}
	
	/**
	 * Reads a file of the given expected size from the given input stream.
	 */
	public static byte[] toByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copy(is, baos);
		baos.close();
		return baos.toByteArray();
	}
	
	/**
	 * Copies input stream to the output stream.
	 */
	public static long copy(InputStream from, OutputStream to) throws IOException {
		byte[] buf = new byte[BUF_SIZE];
		long total = 0;
		while (true) {
			int r = from.read(buf);
			if (r < 0) {
				break;
			}
			to.write(buf, 0, r);
			total += r;
		}
		return total;
	}
	
	/**
	 * Opens a {@link FileOutputStream} for the specified file, checking and
	 * creating the parent directory if it does not exist.
	 */
	public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file + "' cannot be written to");
			}
		} else {
			final File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.mkdirs() && !parent.isDirectory()) {
					throw new IOException("Directory '" + parent + "' could not be created");
				}
			}
		}
		return new FileOutputStream(file, append);
	}
	
	/**
	 * Opens a {@link FileOutputStream} for the specified file, checking and
	 * creating the parent directory if it does not exist.
	 */
	public static FileOutputStream openOutputStream(final File file) throws IOException {
		return openOutputStream(file, false);
	}
	
	public static BufferedWriter openBufferedWriter(File file, String encoding) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(openOutputStream(file, false), encoding));
	}
	
	/**
	 * Closes a <code>Closeable</code> unconditionally.
	 * <p>
	 * Equivalent to {@link Closeable#close()}, except any exceptions will be ignored. 
	 * This is typically used in finally blocks.
	 * </p>
	 */
	public static void closeQuietly(final Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (final IOException ioe) {
			// ignore
		}
	}
	
	/**
	 * Writes {@code len} bytes from the specified byte array starting
	 * at offset {@code off} to a file, creating the file if it does
	 * not exist.
	 */
	public static void toFile(final File file, final byte[] data, final int off, final int len, final boolean append) throws IOException {
		OutputStream out = null;
		try {
			out = openOutputStream(file, append);
			out.write(data, off, len);
			out.close(); // Don't swallow close Exception if copy completes normally
		} finally {
			closeQuietly(out);
		}
	}
	
	/**
	 * Writes a byte array to a file creating the file if it does not exist.
	 */
	public static void toFile(final File file, final byte[] data) throws IOException {
		toFile(file, data, false);
	}
	
	/**
	 * Writes a byte array to a file creating the file if it does not exist.
	 */
	public static void toFile(final File file, final byte[] data, final boolean append) throws IOException {
		toFile(file, data, 0, data.length, append);
	}
	
	/**
	 * Writes chars from a <code>String</code> to bytes on an
	 * <code>OutputStream</code> using the specified character encoding.
	 * <p>This method uses {@link String#getBytes(String)}.</p>
	 */
	public static void write(final String data, final OutputStream output, final Charset encoding) throws IOException {
		if (data != null) {
			output.write(data.getBytes(encoding));
		}
	}
	
	/**
	 * Writes a String to a file creating the file if it does not exist.
	 */
	public static void toFile(final File file, final String data, final Charset encoding, final boolean append) throws IOException {
		OutputStream out = null;
		try {
			out = openOutputStream(file, append);
			write(data, out, encoding);
			out.close(); // Don't swallow close Exception if copy completes normally
		} finally {
			closeQuietly(out);
		}
	}
	
	/**
	 * Writes a String to a file creating the file if it does not exist.
	 * The parent directories of the file will be created if they do not exist.
	 */
	public static void toFile(final File file, final String data, final Charset encoding) throws IOException {
		toFile(file, data, encoding, false);
	}
	
}

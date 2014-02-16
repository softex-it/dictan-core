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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @since version 2.5, 06/30/2011
 * 
 * @modified version 3.4, 07/07/2012
 * 
 * @author Dmitry Viktorov
 *
 */
public class DataAdapter {
	
	private final InputStream is; 
	
	public DataAdapter(File file) throws IOException {
		if (file.length() > Integer.MAX_VALUE) {
            throw new IOException("File is too large!");
        }
		this.is = new FileInputStream(file);
	}
	
	public DataAdapter(InputStream is) {
		this.is = is;
	}
	
	public File toFile(File tempDir, String name, String prefix) throws IOException {
		
		prefix = (prefix == null ? "": prefix);
		
		byte buffer[] = new byte[16384];
		
		File file = new File(tempDir.getAbsolutePath() + File.separator + prefix + name);
		
	    FileOutputStream fos = new FileOutputStream(file);
	    int len = 0;
	    while ((len = is.read(buffer)) >= 0) {
	    	fos.write(buffer,0,len);
	    }
	    
	    fos.close();
	    is.close();
		
		return file;
	}
	
	public byte[] toByteArray() throws IOException {
		
		byte buffer[] = new byte[16384];

		int nRead;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((nRead = is.read(buffer, 0, buffer.length)) >= 0) {
			baos.write(buffer, 0, nRead);
		}

		baos.close();
		is.close();

		return baos.toByteArray();

	}

}

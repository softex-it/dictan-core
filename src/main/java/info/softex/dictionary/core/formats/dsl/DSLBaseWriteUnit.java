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

package info.softex.dictionary.core.formats.dsl;

import info.softex.dictionary.core.formats.source.SourceFileNames;
import info.softex.dictionary.core.utils.FileUtils;
import info.softex.dictionary.core.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * File writer for the DSL format developed by ABBYY Lingvo. It's used
 * separately for the articles and abbreviations to write to a DSL base.
 * 
 * @since version 4.6, 02/28/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLBaseWriteUnit {
	
	private final static Charset UTF8 = Charset.forName("UTF-8");
	
	protected final FileOutputStream outStream;
	
	protected final File descFile;
	protected final File iconFile;
	
	private boolean outputStarted = false;
	
	private final TreeMap<Integer, String[]> articles = new TreeMap<Integer, String[]>();
	private final TreeMap<Integer, List<String>> redirects = new TreeMap<Integer, List<String>>();
	
	private int curWordsSize = 0;
	
	public DSLBaseWriteUnit(String inOutDirPath, String nameBasis) throws IOException {
		
		File outputFile = new File(inOutDirPath + File.separator + nameBasis + SourceFileNames.FILE_DSL_EXT_MAIN);
		outStream = FileUtils.openOutputStream(outputFile);
		
		this.descFile = new File(inOutDirPath + File.separator + nameBasis + SourceFileNames.FILE_DSL_EXT_DESCRIPTION);
		this.iconFile = new File(inOutDirPath + File.separator + nameBasis + SourceFileNames.FILE_DSL_EXT_ICON);
	}
	
	public void saveDSLDescription(String input) throws UnsupportedEncodingException, IOException {
		FileUtils.toFile(descFile, input, UTF8);
	}
	
	public void saveDSLIcon(byte[] input) throws IOException {
		if (input != null) {
			FileUtils.toFile(iconFile, input);
		}
	}
	
	public void saveDSLHeaders(String headers) throws IOException {
		if (outputStarted) {
			throw new IllegalStateException("Headers can't be written after the output of entries is started");
		}
		if (headers != null) {
			FileUtils.write(headers, outStream, UTF8);
		}
	}

	public void saveEntry(final String word, final String wordMapping, final int redirectToId, final String article) throws IOException {
		if (!outputStarted) {
			outputStarted = true;
		}
		
		// Determine word key
		String key = word;
		if (wordMapping != null && !wordMapping.isEmpty()) {
			key = wordMapping;
		}
		
		if (redirectToId < 0) {
			articles.put(curWordsSize, new String[] {key, article});
		} else {
			List<String> artRedirects = redirects.get(redirectToId);
			if (artRedirects == null) {
				artRedirects = new ArrayList<String>();
				redirects.put(redirectToId, artRedirects);
			}
			artRedirects.add(key);
		}
		
		curWordsSize++;
		
		//System.out.println(key  + " " + curWordsSize + " | " + redirectToId + " | " + redirects.get(redirectToId));
		
	}
	
	public void close() throws IOException {
		
		flushArticles();
		
		if (outStream != null) {
			outStream.close();
		}
	}
	
	protected void flushArticles() throws IOException {
		
		// Add 2 more line breaks after the header
		FileUtils.write("\r\n\r\n", outStream, UTF8);
		
		for (Map.Entry<Integer, String[]> entry : articles.entrySet()) {		

			FileUtils.write(entry.getValue()[0] + "\r\n", outStream, UTF8);
			
			List<String> curRedirects = redirects.get(entry.getKey());
			//System.out.println(entry.getValue()[0] + " --- " + entry.getKey() + " " +curRedirects);
			
			if (curRedirects != null) {
				for (String curRedirect : curRedirects) {
					FileUtils.write(curRedirect + "\r\n", outStream, UTF8);
				}
			}
			
			String[] articleLines = StringUtils.splitByLineBreaks(entry.getValue()[1]);
			for (String curLine : articleLines) {
				FileUtils.write("\t" + curLine + "\r\n", outStream, UTF8);
			}

			// Add 1 more line break after the article is completely written
			FileUtils.write("\r\n", outStream, UTF8);
			
		}

	}
	
}

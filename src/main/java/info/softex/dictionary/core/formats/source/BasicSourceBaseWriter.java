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

package info.softex.dictionary.core.formats.source;

import info.softex.dictionary.core.annotations.BaseFormat;
import info.softex.dictionary.core.attributes.AbbreviationInfo;
import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.BasePropertiesInfo;
import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.attributes.FormatInfo;
import info.softex.dictionary.core.attributes.LanguageDirectionsInfo;
import info.softex.dictionary.core.attributes.MediaResourceInfo;
import info.softex.dictionary.core.attributes.ProgressInfo;
import info.softex.dictionary.core.formats.api.BaseWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 3.4, 07/02/2012
 * 
 * @modified version 4.0, 02/08/2014
 * @modified version 4.2, 03/08/2014
 * @modified version 4.4, 03/17/2014
 * 
 * @author Dmitry Viktorov
 *
 */
@BaseFormat(name = "BASIC_SOURCE", primaryExtension = "", extensions = {})
public class BasicSourceBaseWriter implements BaseWriter {
	
	protected static final String UTF8 = "UTF-8";
	
	private final Logger log = LoggerFactory.getLogger(BasicSourceBaseWriter.class);
	
	public static final FormatInfo FORMAT_INFO = FormatInfo.buildFormatInfoFromAnnotation(BasicSourceBaseWriter.class);
		
	byte mediaBuffer[] = new byte[16384];
	
	protected BasePropertiesInfo baseInfo;
	
	protected LanguageDirectionsInfo ldInfo;
	
	protected Writer artWriter;
	protected Writer abbWriter;
	protected Writer debugWriter;
	
	protected File outDirectory;
	protected File mediaDirectory;

	protected ProgressInfo progressInfo = new ProgressInfo();
	
	protected int abbreviationsNumber = 0;
	protected int articleNumber = 0;
	protected int mediaResourcesNumber = 0;
	
	public BasicSourceBaseWriter(File outDirectory) throws IOException {
		if (outDirectory == null || outDirectory.exists() && !outDirectory.isDirectory()) {
			throw new IOException("The target must be a directory, not a file!");
		}
		this.outDirectory = outDirectory;
	}
	
	@Override
	public void createBase(String... params) throws Exception {
		
		// Create environment
		outDirectory.mkdirs();
		mediaDirectory = new File(outDirectory.getAbsolutePath() + File.separator + BasicSourceFileNames.DIRECTORY_MEDIA);
		mediaDirectory.mkdirs();
		
		createWriters();
	}

	@Override
	public BasePropertiesInfo saveBasePropertiesInfo(BasePropertiesInfo baseInfo) throws Exception {
		this.baseInfo = baseInfo;
		int total = baseInfo.getAbbreviationsNumber() + baseInfo.getArticlesNumber() + baseInfo.getMediaResourcesNumber();
		progressInfo.setTotal(total);
		return this.baseInfo;
	}

	@Override
	public BasePropertiesInfo getBasePropertiesInfo() {
		return this.baseInfo;
	}

	@Override
	public LanguageDirectionsInfo saveLanguageDirectionsInfo(LanguageDirectionsInfo languageDirectionsInfo) throws Exception {
		return this.ldInfo = languageDirectionsInfo;
	}

	@Override
	public LanguageDirectionsInfo getLanguageDirectionsInfo() {
		return this.ldInfo;
	}

	@Override
	public void saveArticleInfo(ArticleInfo articleInfo) throws Exception {
		saveArticleLine(articleInfo.getWordInfo().getWord() + SourceReaderUtils.SOURCE_DELIMITER + articleInfo.getArticle() + "\r\n");
	}

	@Override
	public void saveAbbreviationInfo(AbbreviationInfo abbreviationInfo) throws Exception {
		saveAbbreviationLine(abbreviationInfo.getAbbreviation() + SourceReaderUtils.SOURCE_DELIMITER + abbreviationInfo.getDefinition() + "\r\n");
	}

	@Override
	public void saveBaseResourceInfo(BaseResourceInfo baseResourceInfo) {
		try {
			debugWriter.write(baseResourceInfo.getResourceKey() + "  " + new String(baseResourceInfo.getByteArray(), "UTF-8") + "\r\n");
		} catch (IOException e) {
			log.error("Error", e);
		}
	}

	@Override
	public void saveMediaResourceInfo(MediaResourceInfo mediaResourceInfo) throws Exception {
		InputStream is = mediaResourceInfo.getInputStream();
	    FileOutputStream fos = new FileOutputStream(mediaDirectory.getAbsoluteFile() + File.separator + mediaResourceInfo.getKey().getResourceKey());
	    int len;
	    while ((len = is.read(mediaBuffer)) > 0) {
	    	fos.write(mediaBuffer, 0, len);
	    }
	    fos.close();
	    is.close();	
	    mediaResourcesNumber++;
	    updateProgress();
	}

	@Override
	public void flush() throws IOException {
		artWriter.flush();
		abbWriter.flush();
		debugWriter.flush();
	}

	@Override
	public void close() throws IOException {
		artWriter.close();
		abbWriter.close();
		debugWriter.close();
	}

	@Override
	public FormatInfo getFormatInfo() {
		return FORMAT_INFO;
	}

	@Override
	public void addObserver(Observer observer) {
		progressInfo.addObserver(observer);
	}
	
	protected void createWriters() throws Exception {
		artWriter = createWriter(BasicSourceFileNames.FILE_ARTICLES);
		abbWriter = createWriter(BasicSourceFileNames.FILE_ABBREVIATIONS);
		debugWriter = createWriter(BasicSourceFileNames.FILE_DEBUG);
	}
	
	protected void saveArticleLine(String inArticleLine) throws Exception {
		artWriter.write(inArticleLine);
		articleNumber++;
		updateProgress();
	}

	protected void saveAbbreviationLine(String inAbbrevLine) throws Exception {
		abbWriter.write(inAbbrevLine);
		abbreviationsNumber++;
		updateProgress();
	}
	
	protected Writer createWriter(String fileName) throws Exception {
		File abbFile = new File(outDirectory.getAbsolutePath() + File.separator + fileName);
		if (abbFile.exists()) {
			abbFile.delete();
		}
		abbFile.createNewFile();
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(abbFile), UTF8));
	}
	
	protected void updateProgress() {
		int current = abbreviationsNumber + articleNumber + mediaResourcesNumber;
		progressInfo.setCurrent(current);
		progressInfo.notifyObservers();
	}
	
}

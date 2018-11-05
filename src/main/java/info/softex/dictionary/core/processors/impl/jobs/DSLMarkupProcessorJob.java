/*
 *  Dictan Open Dictionary Java Library presents the core interface and functionality for dictionaries. 
 *	
 *  Copyright (C) 2010 - 2018  Dmitry Viktorov <dmitry.viktorov@softex.info> <http://www.softex.info>
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

package info.softex.dictionary.core.processors.impl.jobs;

import info.softex.dictionary.core.attributes.ArticleInfo;
import info.softex.dictionary.core.attributes.KeyValueInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.processors.api.DataInjector;
import info.softex.dictionary.core.processors.api.JobData;
import info.softex.dictionary.core.processors.api.JobRunnable;
import info.softex.dictionary.core.utils.PreconditionUtils;
import info.softex.dictionary.core.utils.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since		4.8,	 04/29/2015
 * 
 * @modified		5.2, 09/30/2018
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLMarkupProcessorJob extends AbstractDSLJob {
	
	protected final static String EMPTY_PAR = "[m1]\\ [/m]";
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public DSLMarkupProcessorJob(String inBasePath) throws IOException {
		super(inBasePath);
		log.info("Markup Processor Job is created");
	}
	
	@Override
	public JobRunnable injectData(DataInjector dataInjector) throws Exception {
		super.injectData(dataInjector);
		writer.saveBaseResourceInfo(artDslResource);
		return this;
	}

	@Override
	public boolean processItem(JobData jobData) throws Exception {
		PreconditionUtils.checkNotNull(artDslResource, 
			"Article DSL Resource can't be null, it has to be injected before processing");
		
		ArticleInfo articleInfo = (ArticleInfo) jobData.getDataObject();
		processEntry(articleInfo);
		writer.saveRawArticleInfo(articleInfo);
		return true;
	}
	
	// Processing methods ------------------------------------------------------------
	
	protected void processEntry(KeyValueInfo<String, String> article) throws IOException, BaseFormatException {
		List<String> lines = new LinkedList<String>(Arrays.asList(StringUtils.splitByLineBreaks(article.getValue())));
		checkAssets(lines);
		
		//lines = resLowerCase(lines);
		
		//lines = endWithM(lines);
		
		//lines = processTranscriptions(lines);
		//lines = wrapIfStartsFromBNum(lines);
		
		//lines = wrapImages(lines);
		//lines = capitalizeParagraphs(lines);
		
		String text = StringUtils.joinWithLineBreaks(lines);
		article.setValue(text);
	}
	
	protected void checkAssets(List<String> list) {
		Pattern p = Pattern.compile("\\[s\\](.*?)\\[/s\\]");
		list.forEach(s -> {
			Matcher m = p.matcher(s);
			while (m.find()) {
				String resourceKey = (m.group(1));
				usedMediaResourceKeys.add(resourceKey);
				//log.info("File " + m.group(1));
			}

		});
	}
	
	protected static void addEmptyParagraphConditionally(List<String> list, String s) throws IOException, BaseFormatException {
		if (EMPTY_PAR.equals(s)) {
			return;
		}
		
		if (list.size() ==0) {
			return;
		}
		
		if (!list.get(list.size() - 1).equals(EMPTY_PAR)) {
			//System.out.println(EMPTY_PAR + " | " + list.getReader(list.size() - 1));
			list.add(EMPTY_PAR);	
		}
	}
	
	protected static List<String> wrapIfStartsFromBNum(List<String> lines) {
		List<String> tempLines = new LinkedList<String>();
		
		//String mt = "^\\[b\\](.*?)\\.\\[/b\\]";
		String mt = "^\\[b\\](.*?)\\[/b\\]";
		Pattern p = Pattern.compile(mt);
		
		for (int i = 0; i < lines.size(); i++) {
			String s = lines.get(i);
			Matcher m = p.matcher(s);
			
			if (m.matches()) {
				tempLines.add("[m1]" + s + "[/m]");		
			} else {
				tempLines.add(s);
			}
		}
		return tempLines;
	}
	
	protected static List<String> processTranscriptions(List<String> lines) {
		
		List<String> tempLines = new LinkedList<String>();
		
		String mt = "\\\\\\[(.*?)\\[t\\](.*?)\\[/t\\](.*?)\\\\\\]";
		Pattern p = Pattern.compile(mt);
		
		for (int i = 0; i < lines.size(); i++) {
			
			String s = lines.get(i);
			Matcher m = p.matcher(s);
			
			StringBuffer sb = new StringBuffer(s.length());
			while (m.find()) {
				String tran = "[b]\\[[/b] [t]" + m.group(2) + "[/t] [b]\\][/b]";
				m.appendReplacement(sb, Matcher.quoteReplacement(tran));
			}
			
			m.appendTail(sb);
			tempLines.add(sb.toString());
			
		}
		
		return tempLines;
	
	}
	
	// BSE, Britannica
	protected static List<String> capitalizeParagraphs(List<String> lines) {
		
		List<String> tempLines = new LinkedList<String>();
		//String mt = "        (.{1})";
		String mt = "      (.{1})";
		//String mt = "\\[m1\\] (.{1})";
		Pattern p = Pattern.compile(mt);
		
		for (int i = 0; i < lines.size(); i++) {
			
			String s = lines.get(i);
			Matcher m = p.matcher(s);
			
			StringBuffer sb = new StringBuffer(s.length());
			
			while (m.find()) {
				
				String starParag = m.group(1).toUpperCase();
				m.appendReplacement(sb, Matcher.quoteReplacement(starParag));
			
			}
			
			m.appendTail(sb);
			tempLines.add(sb.toString());
			
		}
		
		return tempLines;
		
		
	}
	
	// Britannica
	protected static List<String> wrapImages(List<String> lines) {
		
		List<String> tempLines = new LinkedList<String>();

		String mt = "\\[s\\](.*?)\\[/s\\]";		
		Pattern p = Pattern.compile(mt);
		
		for (int i = 0; i < lines.size(); i++) {
			
			String s = lines.get(i);
			Matcher m = p.matcher(s);
			
			StringBuffer sb = new StringBuffer(s.length());
			while (m.find()) {
				String starParag = "[/m]\r\n[m1][s]" + m.group(1).trim() + "[/s][/m]\r\n[m1]";
				//System.out.println(starParag);
				m.appendReplacement(sb, Matcher.quoteReplacement(starParag));
			
			}
			
			m.appendTail(sb);
			
			List<String> resLines = Arrays.asList(StringUtils.splitByLineBreaks(sb.toString()));
			
			tempLines.addAll(resLines);
			
		}
		
		return tempLines;
		
	}
	
	protected static List<String> resLowerCase(List<String> lines) {
		
		List<String> tempLines = new LinkedList<String>();

		String mt = "\\[s\\](.*?)\\[/s\\]";		
		Pattern p = Pattern.compile(mt);
		
		for (int i = 0; i < lines.size(); i++) {
			
			String s = lines.get(i);
			Matcher m = p.matcher(s);
			
			StringBuffer sb = new StringBuffer(s.length());
			while (m.find()) {
				String starParag = "[s]" + m.group(1).trim().toLowerCase() + "[/s]";
				//System.out.println(starParag);
				m.appendReplacement(sb, Matcher.quoteReplacement(starParag));
			}
			
			m.appendTail(sb);

			tempLines.add(sb.toString());
			
		}
		
		return tempLines;
		
	}
	
	protected static List<String> endWithM(List<String> lines) {
		
		List<String> tempLines = new LinkedList<String>();

		for (int i = 0; i < lines.size(); i++) {
			
			String s = lines.get(i);
			
			if (s.trim().startsWith("[m") && !s.trim().endsWith("[/m]")) {
				s = s.trim() + "[/m]";
			} else if (s.trim().startsWith("{{")) {
				int firstInd = s.indexOf("{{");
				int lastInd = s.lastIndexOf("{{");
				
				if (firstInd == lastInd) {
					throw new RuntimeException("Incorrect line: " + s);
				}
				
				int closeFirstInd = s.indexOf("}}");
				int mInd = closeFirstInd + 2;
				String fullMTag = s.substring(mInd, mInd + 4);
				if (!fullMTag.startsWith("[m")) {
					throw new RuntimeException("Can't find m tag: " + fullMTag + "|" + s);
				}
				
				s = fullMTag + s.substring(0, mInd) + s.substring(mInd + 4) + "[/m]";
				
			}

			tempLines.add(s);
			
		}
		
		return tempLines;
		
	}

}

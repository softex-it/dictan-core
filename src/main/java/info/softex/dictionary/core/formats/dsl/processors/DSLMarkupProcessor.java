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

package info.softex.dictionary.core.formats.dsl.processors;

import info.softex.dictionary.core.attributes.KeyValueStringInfo;
import info.softex.dictionary.core.formats.api.BaseFormatException;
import info.softex.dictionary.core.formats.dsl.DSLBaseReadUnit;
import info.softex.dictionary.core.formats.dsl.DSLBaseWriteUnit;
import info.softex.dictionary.core.utils.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility can be used for various pre-processings of the DSL markup.
 * The change of markup may be necessary to improve the view of the cards.
 * 
 * The processor doesn't work with redirects. To work with redirects it repeat 
 * the normal conversion flow.
 * 
 * @since version 4.7, 03/07/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLMarkupProcessor {
	
	protected final static String EMPTY_PAR = "[m1]\\ [/m]";
	
	public static void main(String[] args) throws IOException, BaseFormatException {
		
		DSLMarkupProcessor dslProc = new DSLMarkupProcessor();
		//dslProc.process(args[0], args[1], args[2]);
		//dslProc.process("/ext/ln/Great_Soviet_Encyclopaedia_2013/dictan-utf8_processed", "articles", "articles_out");
		dslProc.process("/ext/ln/Oxford_American", "articles", "articles_out");
		//dslProc.process("/ext/ln/Britannica_2010", "articles", "articles_out");
		//dslProc.process("/Volumes/Media/ln/Lingvo_Universal_EN-RU-EN/Lingvo_Universal_EN-RU_2013/dictan-utf8_processed", "articles", "articles_out");
		
	}
	
	protected void process(String basePath, String inName, String outName) throws IOException, BaseFormatException {
		
		DSLBaseReadUnit dslReader = new DSLBaseReadUnit(basePath, inName);
		DSLBaseWriteUnit dslWriter = new DSLBaseWriteUnit(basePath, outName);
		
		dslReader.load(true);
		List<String> words = dslReader.getWords();
		Map<Integer, String> wordsMappings = dslReader.getWordsMappings();
		
		// Headers
		dslWriter.saveDSLHeaders(dslReader.getDSLHeadersAsString());
		
		int count = 0;
		for (String word : words) {
			KeyValueStringInfo kvInfo = new KeyValueStringInfo();
			
			dslReader.readEntry(kvInfo, word);
			
			processEntry(kvInfo);
			
			String wordOut = word;
			if (StringUtils.isNotBlank(wordsMappings.get(count))) {
				wordOut = wordsMappings.get(count);
			}
			
			dslWriter.saveEntry(wordOut, null, -1, kvInfo.getValue());
			
			count++;
		}
		
		dslReader.close();
		dslWriter.close();
		
	}
	
	protected void processEntry(KeyValueStringInfo article) throws IOException, BaseFormatException {
		
		List<String> lines = new LinkedList<String>(Arrays.asList(StringUtils.splitByLineBreaks(article.getValue())));
		
		//lines = resLowerCase(lines);
		
		lines = processTranscriptions(lines);
		lines = wrapIfStartsFromBNum(lines);
		
		//lines = wrapImages(lines);
		//lines = capitalizeParagraphs(lines);
		
		String text = StringUtils.joinWithLineBreaks(lines);
		
		article.setValue(text);
		
	}
	
	protected static void addEmptyParagraphConditionally(List<String> list, String s) throws IOException, BaseFormatException {
		
		if (EMPTY_PAR.equals(s)) {
			return;
		}
		
		if (list.size() ==0) {
			return;
		}
		
		if (!list.get(list.size() - 1).equals(EMPTY_PAR)) {
			//System.out.println(EMPTY_PAR + " | " + list.get(list.size() - 1));
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

}

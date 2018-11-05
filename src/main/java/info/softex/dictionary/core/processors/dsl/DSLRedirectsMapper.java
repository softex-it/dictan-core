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

package info.softex.dictionary.core.processors.dsl;

import info.softex.dictionary.core.processors.api.JobRunner;
import info.softex.dictionary.core.processors.impl.jobs.RedirectsFinderJob;
import info.softex.dictionary.core.processors.impl.jobs.RedirectsMapperJob;
import info.softex.dictionary.core.processors.impl.runners.DSLEntriesJobRunner;

import java.util.LinkedHashMap;

/**
 * 
 * @since version 4.8,		04/29/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLRedirectsMapper {
	
	public static void main(String[] args) throws Exception {
		
		String in = "/ext/utf8_processed_2";
		String out = "/ext/out";
		
		// Find all references
		RedirectsFinderJob finderJob = new RedirectsFinderJob(out + "/redirects_found.txt");
		JobRunner runner = new DSLEntriesJobRunner(in);
		runner.run(finderJob);
		
		LinkedHashMap<Integer, Integer> redirects = finderJob.getRedirects();
		LinkedHashMap<Integer, String> redirectedWords = finderJob.getRedirectedWords();
		finderJob = null; // Free memory
		
		RedirectsMapperJob mapperJob = new RedirectsMapperJob(out, redirects, redirectedWords, out + "/redirects_mapped.txt");
		runner = new DSLEntriesJobRunner(in);
		runner.run(mapperJob);
		
	}
	
}

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

package info.softex.dictionary.core.processors.dsl;

import info.softex.dictionary.core.processors.api.JobRunner;
import info.softex.dictionary.core.processors.impl.jobs.RedirectsFinderJob;
import info.softex.dictionary.core.processors.impl.jobs.RedirectsMapperJob;
import info.softex.dictionary.core.processors.impl.runners.DSLEntriesJobRunner;

import java.util.TreeMap;

/**
 * 
 * @since version 4.8,		04/29/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLRedirectsMapper {
	
	public static void main(String[] args) throws Exception {
		
		String in = "/processed";
		String out = "/ext/out";
		
		// Find all references
		RedirectsFinderJob finderJob = new RedirectsFinderJob(out + "/redirects.txt");
		JobRunner runner = new DSLEntriesJobRunner(in);
		runner.run(finderJob);
		
		//Collection<LinkedHashSet<Integer>> references = job.getArticleReferences();
		
		TreeMap<Integer, Integer> redirects = finderJob.getRedirects();
		finderJob = null; // free memory
		
		RedirectsMapperJob mapperJob = new RedirectsMapperJob(out, redirects);
		runner = new DSLEntriesJobRunner(in);
		runner.run(mapperJob);
		
	}
	
}

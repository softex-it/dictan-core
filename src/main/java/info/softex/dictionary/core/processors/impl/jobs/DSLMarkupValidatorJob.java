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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.softex.dictionary.core.processors.api.DataInjector;
import info.softex.dictionary.core.processors.api.JobData;
import info.softex.dictionary.core.processors.api.JobRunnable;
import info.softex.dictionary.core.utils.StringUtils;

/**
 * 
 * @since version 4.8,		04/29/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLMarkupValidatorJob implements JobRunnable {
	
	protected final static Pattern EMPTY_BRACKETS = Pattern.compile("[(.+?)]");

	@Override
	public JobRunnable injectData(DataInjector dataInjector) throws Exception {
		return this;
	}

	@Override
	public boolean processItem(JobData jobData) throws Exception {
		return isValidDSL(jobData.getContent());
	}

	@Override
	public void finish() throws Exception {
	}
	
	protected static boolean isValidDSL(String dsl) {
		
		// Check for empty brackets [ ]
		Matcher matcher = EMPTY_BRACKETS.matcher(dsl);
		while (matcher.find()) {
			String body = matcher.group(1);
			if (body != null && StringUtils.isBlank(body)) {
				return false;
			}
		}

		return true;
		
	}

}

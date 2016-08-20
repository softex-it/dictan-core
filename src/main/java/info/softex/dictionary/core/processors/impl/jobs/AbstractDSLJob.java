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

package info.softex.dictionary.core.processors.impl.jobs;

import info.softex.dictionary.core.attributes.BaseResourceInfo;
import info.softex.dictionary.core.formats.dsl.DSLBaseWriter;
import info.softex.dictionary.core.processors.api.DataInjector;
import info.softex.dictionary.core.processors.api.JobRunnable;
import info.softex.dictionary.core.utils.PreconditionUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.8,		04/29/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public abstract class AbstractDSLJob implements JobRunnable {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected final File baseFile;
	protected final DSLBaseWriter writer;
	
	protected BaseResourceInfo artDslResource;
	
	public AbstractDSLJob(File inBaseFile) throws IOException {
		this.baseFile = inBaseFile;
		this.writer = new DSLBaseWriter(inBaseFile);
		this.writer.createBase();
		log.info("DSL base is created at {}", baseFile);
	}
	
	public AbstractDSLJob(String inBasePath) throws IOException {
		this(new File(inBasePath));
	}
	
	@Override
	public JobRunnable injectData(DataInjector dataInjector) throws Exception {
		
		Map<DataInjector.DataKey, Object> injectedData = new HashMap<>();
		
		dataInjector.inject(injectedData);
		
		artDslResource = (BaseResourceInfo)injectedData.get(DataInjector.DataKey.DATA_OBJECT_1);
		PreconditionUtils.checkNotNull(artDslResource, "Article DSL Resource can't be null");
		
		log.info("Data is injected");
		
		return this;
	}
	
	@Override
	public void finish() throws Exception {
		if (writer != null) {
			writer.close();
		}
		log.info("The job is finished");
	}

}

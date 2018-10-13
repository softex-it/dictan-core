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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 4.8,		04/29/2015
 * @modified version 5.2,	09/30/2018
 * 
 * @author Dmitry Viktorov
 * 
 */
public abstract class AbstractDSLJob implements JobRunnable {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected final File baseFile;
	protected final DSLBaseWriter writer;
	
	protected final Set<String> usedMediaResourceKeys = new LinkedHashSet<>();
	
	// Injected data
	protected BaseResourceInfo artDslResource;
	protected Set<String> mediaResourceKeys;
	
	public AbstractDSLJob(File inBaseFile) throws IOException {
		this.baseFile = inBaseFile;
		this.writer = new DSLBaseWriter(inBaseFile);
		this.writer.createBase();
		log.info("DSL base is created at: {}", baseFile);
	}
	
	public AbstractDSLJob(String inBasePath) throws IOException {
		this(new File(inBasePath));
	}
	
	@Override
	public JobRunnable injectData(DataInjector dataInjector) throws Exception {
		
		Map<DataInjector.DataKey, Object> injectedData = new HashMap<>();
		
		dataInjector.inject(injectedData);
		
		artDslResource = (BaseResourceInfo) injectedData.get(DataInjector.DataKey.DATA_OBJECT_1);
		PreconditionUtils.checkNotNull(artDslResource, "Article DSL Resource can't be null");
		
		mediaResourceKeys = (Set<String>) injectedData.get(DataInjector.DataKey.DATA_OBJECT_2);
		PreconditionUtils.checkNotNull(mediaResourceKeys, "Media Resource Keys can't be null. An empty set should be used if they are not available.");
		
		if (mediaResourceKeys.isEmpty()) {
			log.warn("Set of Media Resource Keys is empty");
		}
		
		log.info("Data is injected");
		
		return this;
	}
	
	@Override
	public void finish() throws Exception {
		if (writer != null) {
			writer.close();
		}
		
		Set<String> notFoundMediaResourceKeys = new LinkedHashSet<>(usedMediaResourceKeys);
		notFoundMediaResourceKeys.removeAll(mediaResourceKeys);
		
		Set<String> notUsedMediaResourceKeys = new LinkedHashSet<>(mediaResourceKeys);
		notUsedMediaResourceKeys.removeAll(usedMediaResourceKeys);

		log.info("Used media resources: {}", usedMediaResourceKeys.size());
		log.info("Not found media resources ({}): {}", notFoundMediaResourceKeys.size(), notFoundMediaResourceKeys);
		log.info("Not used media resources ({}): {}", notUsedMediaResourceKeys.size(), notUsedMediaResourceKeys);
		log.info("The main job is finished");
	}

}

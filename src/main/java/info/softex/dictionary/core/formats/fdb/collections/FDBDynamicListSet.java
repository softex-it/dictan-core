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

package info.softex.dictionary.core.formats.fdb.collections;

import info.softex.dictionary.core.collections.AbstractDynamicListSet;
import info.softex.dictionary.core.collections.BasicCacheBlock;
import info.softex.dictionary.core.collections.CacheBlockReferenceTypes;
import info.softex.dictionary.core.formats.fdb.FDBSQLReadStatements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since version 2.6, 09/10/2011
 * 
 * @author Dmitry Viktorov
 * 
 */
public class FDBDynamicListSet extends AbstractDynamicListSet<String> {
	
	private final Logger log = LoggerFactory.getLogger(FDBDynamicListSet.class.getSimpleName());
	
	private Connection connection = null;
	
	private PreparedStatement wordsRangeSt = null;

	public FDBDynamicListSet(int maxSize, int blockSize, Connection inConnection) throws SQLException {
		
		super(maxSize, blockSize, CacheBlockReferenceTypes.SOFT);
		
		this.connection = inConnection;
		
		wordsRangeSt = this.connection.prepareStatement(FDBSQLReadStatements.SELECT_WORDS_IN_RANGE);
		
		// Set other blocks
		for (int i = 0; i < weakBlocksList.length; i++) {
			weakBlocksList[i] = new BasicCacheBlock<String>(i, this.referenceType);
		}
		
	}

	@Override
	protected List<String> restoreBlockContent(BasicCacheBlock<String> block) throws Exception {
		
		int startRow = block.getBlockNumber() * blockSize;
		int endRow = startRow + blockSize - 1;
		
		long start = System.currentTimeMillis();
			
		wordsRangeSt.setInt(1, startRow);
		wordsRangeSt.setInt(2, endRow);
		
		log.debug("Restoring block elements: start {}, end {}", startRow, endRow);
		
		ResultSet rs = wordsRangeSt.executeQuery();
		
		List<String> strongElements = new ArrayList<String>(blockSize);

		while (rs.next()) {
			strongElements.add(rs.getString(1));
		}
		
		block.setElementsReference(strongElements, referenceType);

		// 22 for 35100, 256
		// 2 for 35100, 1
		
		log.debug("Restored FDB Block {}, size: {}, time: {}", new Object[] {block, strongElements.size(), System.currentTimeMillis() - start});
		
		return strongElements;
	}



}

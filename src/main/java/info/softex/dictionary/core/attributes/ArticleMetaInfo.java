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

package info.softex.dictionary.core.attributes;

/**
 * 
 * @since version 1.10, 02/24/2011
 * 
 * @author Dmitry Viktorov
 *
 */
public class ArticleMetaInfo {
	
	// Position by default
	private int textPosition;
	
	public ArticleMetaInfo() {
		this.textPosition = 0;
	}
	
	public ArticleMetaInfo(int textPosition) {
		this.textPosition = textPosition;
	}
	
	public int getTextPosition() {
		return textPosition;
	}

	public void setTextPosition(int textPosition) {
		this.textPosition = textPosition;
	}

}

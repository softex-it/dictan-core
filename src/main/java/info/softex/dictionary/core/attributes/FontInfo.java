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

package info.softex.dictionary.core.attributes;

/**
 * 
 * @since version 4.1, 02/20/2014
 * 
 * @author Dmitry Viktorov
 *
 */
public class FontInfo {
	
	private String name = null;
	private String filePath = null;
	
	private String style = Style.normal.name();
	private String weight = Weight.normal.name();
	
	private int size;
	
	public FontInfo() {}
	
	public FontInfo(String inFontName, int inFontSize) {
		this(inFontName, inFontSize, null);
	}
	
	public FontInfo(String inFontName, int inFontSize, String inFilePath) {
		this.name = inFontName;
		this.size = inFontSize;
		this.filePath = inFilePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String inStyle) {
		Style candStyle = Style.resolveStyle(inStyle);
		this.style = candStyle != null ? candStyle.name() : Style.normal.name();
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String inWeight) {
		Weight candWeight = Weight.resolveWeight(inWeight);
		this.weight = candWeight != null ? candWeight.name() : Weight.normal.name();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "Name: " + name + ", Style: " + style + ", Size: " + size + ", File Path: " + filePath;
	}
	
	public static enum Style {
		normal,
		italic,
		oblique;
		public static Style resolveStyle(String style) {
			Style[] values = values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].name().equalsIgnoreCase(style)) {
					return values[i];
				}
			}
			return null;
		}
	}
	
	public static enum Weight {
		normal,
		bold,
		bolder,
		lighter;
		public static Weight resolveWeight(String weight) {
			Weight[] values = values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].name().equalsIgnoreCase(weight)) {
					return values[i];
				}
			}
			return null;
		}
	}

}

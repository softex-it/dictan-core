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

package info.softex.dictionary.core.formats.api;

/**
 * 
 * @since		2.6, 09/01/2011
 * 
 * @modified	3.9, 01/25/2014
 * @modified	5.2, 10/27/2018
 * 
 * @author Dmitry Viktorov
 * 
 */
@SuppressWarnings("serial")
public class BaseFormatException extends Exception {

	public static final int ERROR_UNDEFINED =					0;
	public static final int ERROR_CANT_LOAD_BASE =				10;
	public static final int ERROR_CANT_LOAD_BASE_PROPERTIES =	20;
	public static final int ERROR_CANT_FIND_BASE_PARTS =		30;
	public static final int ERROR_CANT_LOAD_MEDIA_KEYS =		90;
	
	private final String message;
	private final int errorCode;

	public BaseFormatException(String inMessage) {
		this(inMessage, ERROR_UNDEFINED);
	}
	
	public BaseFormatException(String inMessage, int inErrorCode) {
		this.errorCode = inErrorCode;
		this.message = inMessage;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

}

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

package info.softex.dictionary.core.formats.dsl;

/**
 * 
 * @since version 4.6,		02/15/2015
 * 
 * @modified version 4.7,	03/07/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class DSLStyles {
	
	protected final static String DSL_CSS_COMMON = 
		"span.cd {color:#008000} ";
		;
	
	protected final static String DSL_CSS_BROWSER =
			
		"div.header {font-weight:bold;font-size:115%} " +
		"div.m0,div.m1 {margin-top:1em} " +
		"div.m2 {margin-left:1em} " +
		"div.m3 {margin-left:2em} " +
		"div.m4 {margin-left:3em} " +
		"div.m5 {margin-left:4em} " +
		"div.m6 {margin-left:5em} " +
		"div.m7 {margin-left:6em} " +
		"div.m8 {margin-left:7em} " +
		"div.m9 {margin-left:8em} " +

		"img.int {max-width:100%;height:auto} " +
		
		"t {font-weight:bold} " +
		"o {font-weight:bold} " + // mainly for wrapping transcription t
		"v {color:#FF0000} " +
		"e {color:#808080} "
		
		;
	
	protected final static String DSL_CSS_JAVA =
		"div.header {font-weight:bold;font-size:105%} " +
		"div.m0 {margin-top:15px}" +
		"div.m1 {margin-top:15px} " +
		"div.m2 {margin-left:15px} " +
		"div.m3 {margin-left:30px} " +
		"div.m4 {margin-left:45px} " +
		"div.m5 {margin-left:60px} " +
		"div.m6 {margin-left:75px} " +
		"div.m7 {margin-left:90px} " +
		"div.m8 {margin-left:105px} " +
		"div.m9 {margin-left:120px} " +
		
		"span.t {font-weight:bold} " +
		"span.o {font-weight:bold} " + // mainly for wrapping transcription t
		"span.v {color:#FF0000} " +
		"span.e {color:#808080} "
		
		;

	public static String getDSLStylesForJava() {
		return DSL_CSS_COMMON + DSL_CSS_JAVA;
	}
	
	public static String getDSLStylesForBrowser() {
		return DSL_CSS_COMMON + DSL_CSS_BROWSER;
	}
	
}

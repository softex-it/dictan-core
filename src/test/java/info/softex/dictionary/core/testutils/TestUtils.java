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

package info.softex.dictionary.core.testutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * 
 * @since version 4.6, 02/03/2015
 * 
 * @author Dmitry Viktorov
 * 
 */
public class TestUtils {
	
	protected final static String MVN_TARGET_SUBPATH = "../../target/";
	protected final static String MVN_TEST_FILES_SUBPATH = MVN_TARGET_SUBPATH + "test-files/";
	protected final static String MVN_TEST_DICTS_SUBPATH = MVN_TARGET_SUBPATH + "test-dicts/";
	
	protected static File getCodeSourceRelevantDirectory(String subPath) {
		String relPath = TestUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		if (!relPath.endsWith(File.separator)) {
			relPath += File.separator;
		}
		File targetDir = new File(relPath + subPath);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
		return targetDir;
	}

	public static File getMavenTargetDirectory() {
		return getCodeSourceRelevantDirectory(MVN_TARGET_SUBPATH);
	}
	
	public static File getMavenTestFile(String fileName) throws IOException {
		return getMavenTestFile(MVN_TEST_FILES_SUBPATH, fileName);
	}
	
	public static File getMavenTestDictFile(String fileName) throws IOException {
		return getMavenTestFile(MVN_TEST_DICTS_SUBPATH, fileName);
	}
	
	public static Path getMavenTestPath(String filePath) throws IOException {
		return getMavenTestFile(filePath).toPath();
	}
	
	protected static File getMavenTestFile(String directory, String fileName) throws IOException {
		File testFilesDir = getCodeSourceRelevantDirectory(directory);
		String relPath = testFilesDir.getCanonicalPath();
		if (!relPath.endsWith(File.separator)) {
			relPath += File.separator;
		}
		return new File(relPath + fileName);
	}

}

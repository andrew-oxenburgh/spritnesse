package org.oxenburgh.spritnesse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.List;

/**
 This file is part of Spritnesse.

 Spritnesse is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Spritnesse is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Spritnesse.  If not, see <http://www.gnu.org/licenses/>.

 Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.

 */
public class JarTestsFinder_AppVerify {

    public static final String TEST_JAR = "./spritnesse-test/target/spritnesse-test-1.0.0-SNAPSHOT.jar";


    @Test(expected = RuntimeException.class)
    public void noJarFile() throws Exception {
        new JarTestsFinder().calcMethods("NON-EXISTENT.jar");
    }


    @Test
    public void findTestWithName() throws Exception {
        String expectedClassName = ".*Another_AppVerify";

        List<String> classes = new JarTestsFinder().calcMethods(TEST_JAR, expectedClassName);

        assertExpectedNumberOfClasses(expectedClassName, classes, 1);
        assertEquals("should be right name of '" + expectedClassName + "'", true, classes.get(0).endsWith(".Another_AppVerify"));
    }


    @Test
    public void findTestWithoutName() throws Exception {
        String expectedClassName = "NOT .*Another_AppVerify";

        List<String> classes = new JarTestsFinder().calcMethods(TEST_JAR, expectedClassName);

        assertExpectedNumberOfClasses(expectedClassName, classes, 7);
    }


    @Test
    public void findTestWithRegularExpression() throws Exception {
        String expectedClassName = "org.oxenburgh.*";

        List<String> classes = new JarTestsFinder().calcMethods(TEST_JAR, expectedClassName);

        assertExpectedNumberOfClasses(expectedClassName, classes, 8);
    }


    private void assertExpectedNumberOfClasses(String expectedClassName, List<String> classes, int expected) {
        assertEquals("should find "
                + expected
                + " tests in " + expectedClassName + ", found: \n" + StringUtils.join(classes, "\n"), expected, classes.size());
    }

}

package org.oxenburgh.spritnesse;

import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This file is part of Spritnesse.
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 * <p/>
 * Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.
 */
public class JarTestsFinder_TestCase {

    public static final String TEST_JAR = "./test-junit/target/test-junit-1.0.0-SNAPSHOT.jar";
    public final static int NUMBER_OF_GOOD_CLASSES = 6;


    @BeforeClass
    public static void before() {
        assertTrue("can't find file " + TEST_JAR + " in " + new File(".").getAbsolutePath(), new File(TEST_JAR).exists());
    }


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
    public void findTestWithRegularExpression() throws Exception {
        String expectedClassName = "org.oxenburgh.*";

        List<String> classes = new JarTestsFinder().calcMethods(TEST_JAR, expectedClassName);

        assertExpectedNumberOfClasses(expectedClassName, classes, NUMBER_OF_GOOD_CLASSES);
    }


    private void assertExpectedNumberOfClasses(String expectedClassName, List<String> classes, int expected) {
        assertEquals("should find "
                + expected
                + " tests in " + expectedClassName + ", found: \n" + StringUtils.join(classes, "\n"), expected, classes.size());
    }

}

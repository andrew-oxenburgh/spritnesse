package org.oxenburgh.spritnesse;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.oxenburgh.spritnesse.Utils.assertExpectedNumberOfClasses;

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
public class JarTestsFinder_JUnit_TestCase {

    public static final String TEST_JAR = "test-junit/target/test-junit-jar-with-dependencies.jar";
    public final static int NUMBER_OF_GOOD_CLASSES = 7;


    @BeforeClass
    public static void before() {
        assertTrue("can't find file " + TEST_JAR + " in " + new File(".").getAbsolutePath(), new File(TEST_JAR).exists());
    }


    @Test(expected = RuntimeException.class)
    public void noJarFile() throws Exception {
        new JarTestsFinder().findClassesIn("NON-EXISTENT.jar");
    }


    @Test
    public void findTestWithName() throws Exception {
        String expectedClassName = ".*Another_AppVerify";

        List<String> classes = new JarTestsFinder().findClassesInLike(TEST_JAR, expectedClassName);

        assertExpectedNumberOfClasses(expectedClassName, classes, 1);
        assertEquals("should be right name of '" + expectedClassName + "'", true, classes.get(0).endsWith(".Another_AppVerify"));
    }


    @Test
    public void findTestWithRegularExpression() throws Exception {
        String expectedClassName = "org.oxenburgh.*";

        List<String> classes = new JarTestsFinder().findClassesInLike(TEST_JAR, expectedClassName);

        assertExpectedNumberOfClasses(expectedClassName, classes, NUMBER_OF_GOOD_CLASSES);
    }
}

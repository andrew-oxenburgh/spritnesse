package org.oxenburgh.spritnesse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

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
public class Spock_TestCase {

    static Logger logger = LoggerFactory.getLogger(Spock_TestCase.class);

    public static final String TEST_JAR = "./test-spock/target/test-spock-1.0-jar-with-dependencies.jar";


    @BeforeClass
    public static void before() {
        logger.error("AAAAAAAHHHHHHHH");
        assertTrue("can't find file " + TEST_JAR + " in " + new File(".").getAbsolutePath(), new File(TEST_JAR).exists());
    }

    @Test
    public void spockTable_expectToHaveSpockAvailable() throws Exception {
        new SpockTable("") {
            @Override
            boolean checkSpockAvailable() {
                return false;
            }
        };
    }

    @Test(expected = RuntimeException.class)
    public void noJarFile() throws Exception {
        new JarTestsFinder().findClassesIn("NON-EXISTENT.jar");
    }


    @Test
    public void findTestWithName() throws Exception {
        List<String> classes = new JarTestsFinder().findClassesInLike(TEST_JAR, "org.oxenburgh.*");
        System.out.println("classes = " + classes);
    }
}

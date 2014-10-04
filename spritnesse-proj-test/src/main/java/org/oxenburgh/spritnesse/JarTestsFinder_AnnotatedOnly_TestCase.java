package org.oxenburgh.spritnesse;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.oxenburgh.spritnesse.Utils.createClassLoader;
import static org.oxenburgh.spritnesse.Utils.loadClass;

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
public class JarTestsFinder_AnnotatedOnly_TestCase {

    public static final String TEST_JAR = "test-junit/target/test-junit-jar-with-dependencies.jar";

    @Test
    public void findOnlyAnnotatedClasses() throws Exception {
        List<String> annotatedClasses = new JarTestsFinder().findAnnotatedClasses(TEST_JAR);
        assertEquals(1, annotatedClasses.size());
        Class<?> clazz = loadClass(createClassLoader(TEST_JAR), annotatedClasses.get(0));
        assertNotNull(clazz.getAnnotation(SpritnesseInclude.class));
    }

}

package org.oxenburgh.spritnesse;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.List;

/**
 * This file is part of Spritnesse.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3.0 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * <p/>
 * Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.
 */
public class Utils {

    public static String toString(Collection setOfTests) {
        String errorSet = "[\n";

        for (Object test : setOfTests) {
            errorSet += test + ", \n";
        }
        errorSet += "]";
        return errorSet;
    }


    public static Class<?> loadClass(URLClassLoader loader, String clazzName) {
        try {
            return loader.loadClass(clazzName);
        }
        catch (NoClassDefFoundError e) {
//            throw new RuntimeException("can't find '" + clazzName + "' in loader '" + loader + "'", e);
        }
        catch (ClassNotFoundException e) {
//            throw new RuntimeException("can't find '" + clazzName + "' in loader '" + loader + "'", e);
        }
        return null;
    }


    public static void assertExpectedNumberOfClasses(String expectedClassName, List<String> classes, int expected) {
        assertEquals("should find " + expected + " tests in " + expectedClassName + ", found: \n" + StringUtils.join(classes, "\n"),
                expected,
                classes.size());
    }


    static public URLClassLoader createClassLoader(String fileName) {
        String urlPath = makeUrlPath(fileName);
        URL url = null;
        try {
            url = new URL(urlPath);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URLClassLoader loader = URLClassLoader.newInstance(new URL[] { url });

        return loader;
    }


    static private String makeUrlPath(String fileName) {
        String path = new File(".").getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        return "file:///" + path + "/" + fileName;
    }

}

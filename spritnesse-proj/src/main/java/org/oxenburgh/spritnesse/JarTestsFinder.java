package org.oxenburgh.spritnesse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spock.lang.Specification;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import static java.lang.reflect.Modifier.isAbstract;
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
public class JarTestsFinder {

    static Logger logger = LoggerFactory.getLogger(JarTestsFinder.class);

    public List<String> calcMethods(String jarName) {
        return handleJar(jarName);
    }


    public List<String> calcMethods(String jarName, String filterString) {
        ArrayList<String> testNames = handleJar(jarName);
        if (filterString == null) {
            return testNames;
        }

        filterString = filterString.trim();

        return filter(filterString, testNames);
    }


    private ArrayList<String> filter(String filterString, ArrayList<String> testNames) {
        Pattern pattern = Pattern.compile(filterString + ".*");
        ArrayList<String> ret = new ArrayList<String>();
        for (String testName : testNames) {
            if (pattern.matcher(testName).matches()) {
                ret.add(testName);
            }
        }
        return ret;
    }


    ArrayList<String> handleJar(String jarName) {
        ArrayList<String> tests = new ArrayList<String>();
        try {
            Enumeration<JarEntry> entries = new JarFile(jarName).entries();
            URLClassLoader loader = createClassLoader(jarName);
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                handleEntry(tests, loader, jarEntry);
            }
        } catch (IOException e) {
            throw new RuntimeException("can't find file '" + jarName + "', root from '" + new File(".").getAbsolutePath() + "'", e);
        }
        return tests;
    }


    private void handleEntry(List<String> res, URLClassLoader loader, JarEntry possibleClass) {
        String clazzPath = possibleClass.getName();
        if (clazzPath.endsWith(".class")) {
            String clazzName = massageToClassPath(clazzPath);
            try {
                Class clazz = loadClass(loader, clazzName);
                if (!isAbstract(clazz.getModifiers())) {
                    handleClass(res, clazz);
                }
            } catch (NoClassDefFoundError e) {
                // add it anyway. let junit show error
                logger.info("can't find class - ", e);
                res.add(clazzName);
            }
        }
    }

    void handleClass(List<String> res, Class<?> clazz) {
        boolean isSpec = isSpec(clazz);
        if (isSpec) {
            res.add(clazz.getName());
            logger.info("adding spec class {}", clazz.getName());
            return;
        } else {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    String annotationName = annotation.annotationType().getName();
                    if (annotationName.endsWith(".Test")) {
                        res.add(clazz.getName());
                        logger.info("adding test class {}", clazz.getName());
                        return;
                    }
                }
            }
        }
    }

    private boolean isSpec(Class<?> clazz) {
        boolean ret = false;
        try {
            ret = Specification.class.isAssignableFrom(clazz);
        } catch (RuntimeException e) {
            logger.info("", e);
        } catch (NoClassDefFoundError e) {
            logger.info("no class found", e);
        }
        return ret;
    }


    URLClassLoader createClassLoader(String fileName) {
        String urlPath = makeUrlPath(fileName);
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return URLClassLoader.newInstance(new URL[]{url});
    }


    protected String massageToClassPath(String name) {
        String substring = name.substring(0, name.length() - 6);
        substring = substring.replaceAll("/", ".");
        substring = substring.replace("\\", ".");
        return substring;
    }


    private String makeUrlPath(String fileName) {
        String path = new File(".").getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        return "file:///" + path + "/" + fileName;
    }

}

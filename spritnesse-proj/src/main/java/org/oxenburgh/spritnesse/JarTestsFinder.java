package org.oxenburgh.spritnesse;

import static java.lang.reflect.Modifier.isAbstract;
import static org.oxenburgh.spritnesse.Utils.createClassLoader;
import static org.oxenburgh.spritnesse.Utils.loadClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spock.lang.Specification;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

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
public class JarTestsFinder {

    static Logger logger = LoggerFactory.getLogger(JarTestsFinder.class);

    boolean annotatedOnly = false;

    private static final int NUMBER_OF_CLASSES_TO_LOAD_BEFORE_NEW_LOADER = 1000;


    public List<String> findAnnotatedClasses(String jarPath) {
        annotatedOnly = true;

        return findTestClassesInJar(jarPath);
    }


    public List<String> findClassesIn(String jarPath) {
        return findTestClassesInJar(jarPath);
    }


    public List<String> findClassesInLike(String jarPath, String filter) {
        List<String> testNames = findTestClassesInJar(jarPath);
        if (filter == null) {
            return testNames;
        }

        filter = filter.trim();

        return filterByRegEx(testNames, filter);
    }


    List<String> findTestClassesInJar(String jarPath) {
        ArrayList<String> tests = new ArrayList<String>();
        try {
            Enumeration<JarEntry> entries = new JarFile(jarPath).entries();
            URLClassLoader loader = createClassLoader(jarPath);
            int cnt = 0 ;
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                handleJarEntry(tests, loader, jarEntry);
                cnt ++;
                if(cnt > NUMBER_OF_CLASSES_TO_LOAD_BEFORE_NEW_LOADER){
                    cnt = 0 ;
                    loader = createClassLoader(jarPath);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("can't find file '" + jarPath + "', root from '" + new File(".").getAbsolutePath() + "'", e);
        }
        return tests;
    }


    private ArrayList<String> filterByRegEx(List<String> tests, String regexp) {
        Pattern pattern = Pattern.compile(regexp + ".*");
        ArrayList<String> ret = new ArrayList<String>();
        for (String testName : tests) {
            if (pattern.matcher(testName).matches()) {
                ret.add(testName);
            }
        }
        return ret;
    }


    private void handleJarEntry(List<String> validEntries, URLClassLoader loader, JarEntry possibleClass) {
        String clazzPath = possibleClass.getName();
        if (clazzPath.endsWith(".class")) {
            String clazzName = massageFilePathToClassPath(clazzPath);
            try {
                Class clazz = loadClass(loader, clazzName);
                if (clazz != null && !isAbstract(clazz.getModifiers())) {
                    handleClass(validEntries, clazz);
                }
            }
            catch (NoClassDefFoundError e) {
                // add it anyway. let junit show error
                logger.info("can't find class - ", e);
                //                validEntries.add(clazzName);
            }
        }
    }


    void handleClass(List<String> res, Class<?> clazz) {
        if (annotatedOnly) {
            if (isSpritnesseInclude(clazz)) {
                res.add(clazz.getName());
                logger.info("adding @SpritnesseInclude class {}", clazz.getName());
                return;
            }
        }
        else if (isSpec(clazz)) {
            res.add(clazz.getName());
            logger.info("adding spec class {}", clazz.getName());
            return;
        }
        else if (isRunWith(clazz)) {
            res.add(clazz.getName());
            logger.info("adding RunWith class {}", clazz.getName());
            return;
        }
        else {
            findAllTests(res, clazz);
        }
    }


    private void findAllTests(List<String> res, Class<?> clazz) {
        try {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    String annotationName = annotation.annotationType().getName();
                    if (annotationName.endsWith(".Test") || annotationName.endsWith(".RunWith")) {
                        res.add(clazz.getName());
                        logger.info("adding test class {}", clazz.getName());
                        return;
                    }
                }
            }
        }
        catch (SecurityException e) {
            //swallow
        }
        catch (NoClassDefFoundError e) {
            //swallow
        }
        catch (IllegalAccessError e) {
            //swallow
        }
    }


    private boolean isRunWith(Class<?> clazz) {
        return clazz.getAnnotation(org.junit.runner.RunWith.class) != null;
    }


    private boolean isSpritnesseInclude(Class<?> clazz) {
        return clazz.getAnnotation(org.oxenburgh.spritnesse.SpritnesseInclude.class) != null;
    }


    private boolean isSpec(Class<?> clazz) {
        boolean ret = false;
        try {
            ret = Specification.class.isAssignableFrom(clazz);
        }
        catch (RuntimeException e) {
            logger.info("", e);
        }
        catch (NoClassDefFoundError e) {
            logger.info("no class found", e);
        }
        return ret;
    }


    protected String massageFilePathToClassPath(String name) {
        String substring = name.substring(0, name.length() - 6);
        substring = substring.replaceAll("/", ".");
        substring = substring.replace("\\", ".");
        return substring;
    }

}

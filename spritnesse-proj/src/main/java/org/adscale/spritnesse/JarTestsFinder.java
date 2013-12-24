package org.adscale.spritnesse;

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

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class JarTestsFinder {

    public List<String> calcMethods(String jarName) {
        return handleJar(jarName);
    }


    public List<String> calcMethods(String jarName, String filterString) {
        ArrayList<String> testNames = handleJar(jarName);
        if (filterString == null) {
            return testNames;
        }

        filterString = filterString.trim();

        ArrayList<String> ret;
        boolean startsWithNOT = filterString.toLowerCase().startsWith("not ");
        if (startsWithNOT) {
            ret = filter(filterString.substring(4), false, testNames);
        }
        else {
            ret = filter(filterString, true, testNames);
        }

        return ret;
    }


    private ArrayList<String> filter(String filterString, boolean not, ArrayList<String> testNames) {
        Pattern pattern = Pattern.compile(filterString);
        ArrayList<String> ret = new ArrayList<String>();
        for (String testName : testNames) {
            if (pattern.matcher(testName).matches() == not) {
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
        }
        catch (IOException e) {
            throw new RuntimeException("can't find file '" + jarName + "', root from '" + new File(".").getAbsolutePath() + "'", e);
        }
        return tests;
    }


    private void handleEntry(List<String> res, URLClassLoader loader, JarEntry possibleClass) {
        String clazzPath = possibleClass.getName();
        if (clazzPath.endsWith(".class")) {
            String clazzName = massageToClassPath(clazzPath);
            try {
                Class<?> clazz = loadClass(loader, clazzName);
                handleClass(res, clazz);
            }
            catch (NoClassDefFoundError e) {
                // add it anyway. let junit show error
                res.add(clazzName);
            }
        }
    }


    void handleClass(List<String> res, Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getName().endsWith(".Test")) {
                    res.add(clazz.getName());
                    return;
                }
            }
        }
    }


    Class<?> loadClass(URLClassLoader loader, String clazzName) {
        try {
            return loader.loadClass(clazzName);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("can't find '" + clazzName + "' in loader '" + loader + "'", e);
        }
    }


    URLClassLoader createClassLoader(String fileName) {
        String urlPath = makeUrlPath(fileName);
        URL url = null;
        try {
            url = new URL(urlPath);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return URLClassLoader.newInstance(new URL[] { url });
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

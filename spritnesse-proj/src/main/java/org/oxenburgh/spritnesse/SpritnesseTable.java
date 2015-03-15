package org.oxenburgh.spritnesse;

import static java.lang.reflect.Modifier.isAbstract;
import static java.util.Arrays.asList;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is part of Spritnesse.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3.0 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * <p>
 * Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.
 */
public abstract class SpritnesseTable {

    static Logger logger = LoggerFactory.getLogger(SpritnesseTable.class);

    private final CamelCaser camelCaser = new CamelCaser();

    String jarName;


    public SpritnesseTable(String jarName) {
        this.jarName = jarName;
    }


    public List doTable(List<List<String>> args) throws ClassNotFoundException {
        logger.info("inside doTable");
        List ret = asList(asList("failed somewhere."));
        try {
            ret = intDoTable(args);
        }
        catch (RuntimeException e) {
            logger.error("", e);
            e.printStackTrace();
        }

        if (ret.size() < 1) {
            ret.add(asList("should find 1 test"));
        }
        args.addAll(ret);
        return args;
    }


    List intDoTable(List<List<String>> args) throws ClassNotFoundException {
        if (!new File(jarName).exists()) {
            return asList(asList("no such jar found [" + jarName + "]"));
        }

        List<String> classNames = getClassesToBeTested(args);

        List<Class> classes = listOfClasses(classNames);
        List<SpritnesseTestResult> tests = runTests(classes);
        List table = makeTable(tests);

        for (List<String> row : args) {
            for (int i = 0; i < row.size(); i++) {
                String s = row.get(i);
                row.set(i, "ignore:" + s);
            }
        }
        return table;
    }


    abstract List getClassesToBeTested(List<List<String>> args);


    List<Class> listOfClasses(List<String> classNames) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        for (String clazz : classNames) {
            if (clazz.isEmpty()) {
                continue;
            }
            Class<?> aClass = this.getClass().getClassLoader().loadClass(clazz);
            boolean isNotAbstract = !isAbstract(aClass.getModifiers());
            if (isNotAbstract) {
                classes.add(aClass);
            }
        }
        return classes;
    }


    List<SpritnesseTestResult> runTests(List<Class> clazzes) {
        JUnitCore core = new JUnitCore();
        JunitListener listener = new JunitListener();
        core.addListener(listener);

        core.run(Request.classes(arrayOfClasses(clazzes)));
        return listener.getTestResults();
    }


    Class[] arrayOfClasses(List<Class> clazzes) {
        Class[] classes = new Class[clazzes.size()];

        for (int i = 0; i < clazzes.size(); i++) {
            classes[i] = clazzes.get(i);
        }
        return classes;
    }


    public List makeTable(List<SpritnesseTestResult> strings) {
        List<List<String>> arrayLists = new ArrayList();

        String currentClass = "";

        for (SpritnesseTestResult parts : strings) {
            String status = parts.getStatus();
            String clazzName = parts.getClassName();
            if (currentClass.equals(clazzName)) {
                clazzName = "";
            }
            else {
                currentClass = clazzName;
            }
            String methodName = parts.getMethodName();
            String error = parts.getError();

            String clazz = "report:" + camelCaser.classMassage(clazzName);
            String method = "report:" + camelCaser.sentenize(methodName);

            List<String> list;

            if (error == null) {
                list = asList(clazz, method, status);
            }
            else {
                String message = parts.getTrace();
                String text = "fail:" + error + "->" + message;
                list = asList(clazz, method, text);
            }
            arrayLists.add(list);
        }
        return arrayLists;
    }
}

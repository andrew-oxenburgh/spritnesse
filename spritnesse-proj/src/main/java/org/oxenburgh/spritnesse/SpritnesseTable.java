package org.oxenburgh.spritnesse;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Modifier.isAbstract;
import static util.ListUtility.list;

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
public abstract class SpritnesseTable {
    static Logger logger = LoggerFactory.getLogger(JunitTable.class);
    private final CamelCaser camelCaser = new CamelCaser();
    String jarName;

    public SpritnesseTable(String jarName) {
        this.jarName = jarName;
    }

    public List doTable(List<List<String>> args) throws ClassNotFoundException {
        logger.info("inside doTable");
        List ret = null;
        try {
            ret = intDoTable(args);
        }catch(RuntimeException e){
            logger.error("", e);
        }
        return ret;
    }

    private List intDoTable(List<List<String>> args) throws ClassNotFoundException {
        if (!new File(jarName).exists()) {
            return list(list("no such jar found [" + jarName + "]"));
        }

        List classNames = getClassesToBeTested(args);

        List classes = listOfClasses(classNames);
        List tests = runTests(classes);
        List table = makeTable(tests);

        for (List<String> row : args) {
            for (int i = 0; i < row.size(); i++) {
                String s = row.get(i);
                row.set(i, "ignore:" + s);
            }
        }

        args.addAll(table);
        return args;
    }

    abstract List getClassesToBeTested(List<List<String>> args);

    private String errorMessage() {
        return "can't find file " + jarName + " in " + new File(".").getAbsolutePath();
    }

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

    List<String> runTests(List<Class> clazzes) {
        JUnitCore core = new JUnitCore();
        JunitListener listener = new JunitListener();
        core.addListener(listener);

        core.run(Request.classes(arrayOfClasses(clazzes)));
        return listener.getTestNames();
    }

    Class[] arrayOfClasses(List<Class> clazzes) {
        Class[] classes = new Class[clazzes.size()];

        for (int i = 0; i < clazzes.size(); i++) {
            classes[i] = clazzes.get(i);
        }
        return classes;
    }

    public List makeTable(List<String> strings) {
        List<List<String>> arrayLists = new ArrayList();

        String currentClass = "";

        for (String string : strings) {
            String[] parts = string.split(":", 6);

            String status = parts[0];
            String clazzName = parts[1];
            if (currentClass.equals(clazzName)) {
                clazzName = "";
            } else {
                currentClass = clazzName;
            }
            String methodName = parts.length > 2 ? parts[2] : "";
            String error = parts.length > 3 ? parts[3] : null;

            String clazz = "report:" + camelCaser.classMassage(clazzName);
            String method = "report:" + camelCaser.sentenize(methodName);

            List<String> list;

            if (error == null) {
                list = list(clazz, method, status);
            } else {
                String message = parts[4];
                String text = "fail:" + error + "->" + message;
                list = list(clazz, method, text);
            }
            arrayLists.add(list);
        }
        return arrayLists;
    }
}

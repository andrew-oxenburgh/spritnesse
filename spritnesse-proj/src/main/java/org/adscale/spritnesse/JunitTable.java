package org.adscale.spritnesse;

import static util.ListUtility.list;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class JunitTable {

    private final CamelCaser camelCaser = new CamelCaser();

    String jarName;


    public JunitTable(String jarName) {
        this.jarName = jarName;
    }


    public List doTable(List<List<String>> args) throws ClassNotFoundException {
        if (!new File(jarName).exists()) {
            throw new RuntimeException(errorMessage());
        }

        for (List<String> strings : args) {
            for (String string : strings) {
                System.out.println("string = " + string);
            }
        }
        System.out.println("ignore = " + ToStringBuilder.reflectionToString(args, ToStringStyle.MULTI_LINE_STYLE, true));

        List classNames;
        if (args == null || args.isEmpty()) {
            classNames = new JarTestsFinder().calcMethods(jarName);
        }
        else {
            classNames = new JarTestsFinder().calcMethods(jarName, args.get(0).get(0));
        }
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
            classes.add(aClass);
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
            }
            else {
                currentClass = clazzName;
            }
            String methodName = parts.length > 2 ? parts[2] : "";
            String error = parts.length > 3 ? parts[3] : null;

            String clazz = "report:" + camelCaser.classMassage(clazzName);
            String method = "report:" + camelCaser.sentenize(methodName);

            List<String> list;

            if (error == null) {
                list = list(clazz, method, status);
            }
            else {
                String message = parts[4];

                String trace = parts[5];
                String text = "fail:" + error + "->" + message;
                System.out.println(trace);
                list = list(clazz, method, text);
            }
            arrayLists.add(list);
        }
        return arrayLists;
    }
}

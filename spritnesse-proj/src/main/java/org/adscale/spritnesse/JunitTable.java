package org.adscale.spritnesse;

import static util.ListUtility.list;

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


    public List doTable(List<List<String>> ignore) throws ClassNotFoundException {
        if (!new File(jarName).exists()) {
            throw new RuntimeException(errorMessage());
        }

        List classNames = new JarTestsFinder().calcMethods(jarName);
        List classes = listOfClasses(classNames);
        List tests = runTests(classes);
        List table = makeTable(tests);
        return table;
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
                trace = trace.replaceAll("\\'", "\\\\'");
                trace = trace.replaceAll("\n", "'+\n'");
                String alert = " onclick=\"alert('" + trace + "');\"";
                String text = "fail:<span" + alert + ">" + error + "->" + message + "</span>";
                list = list(clazz, method, text);
            }
            arrayLists.add(list);
        }
        return arrayLists;
    }
}

package org.oxenburgh.spritnesse;

import java.net.URLClassLoader;
import java.util.Collection;

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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("can't find '" + clazzName + "' in loader '" + loader + "'", e);
        }
    }
}

package org.oxenburgh.spritnesse;

import org.apache.commons.lang.StringUtils;

import java.net.URLClassLoader;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

    public static void assertExpectedNumberOfClasses(String expectedClassName, List<String> classes, int expected) {
        assertEquals("should find "
                + expected
                + " tests in " + expectedClassName + ", found: \n" + StringUtils.join(classes, "\n"), expected, classes.size());
    }
}

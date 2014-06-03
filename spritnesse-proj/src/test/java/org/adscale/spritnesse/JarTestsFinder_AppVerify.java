package org.adscale.spritnesse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

import java.util.List;

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class JarTestsFinder_AppVerify {

    public static final String TEST_JAR = "./spritnesse-test/target/spritnesse-test-1.0.0-SNAPSHOT.jar";


    @Test(expected = RuntimeException.class)
    public void noJarFile() throws Exception {
        new JarTestsFinder().calcMethods("NON-EXISTENT.jar");
    }


    @Test
    public void findTestWithName() throws Exception {
        String expectedClassName = ".*Another_AppVerify";

        List<String> classes = new JarTestsFinder().calcMethods(TEST_JAR, expectedClassName);

        assertEquals("should find only one test" + expectedClassName, 1, classes.size());
        assertEquals("should be right name of '" + expectedClassName + "'", true, classes.get(0).endsWith(".Another_AppVerify"));
    }


    @Test
    public void findTestWithoutName() throws Exception {
        String expectedClassName = "NOT .*Another_AppVerify";

        List<String> classes = new JarTestsFinder().calcMethods(TEST_JAR, expectedClassName);

        assertExpectedNumberOfClasses(expectedClassName, classes, 7);
    }


    @Test
    public void findTestWithRegularExpression() throws Exception {
        String expectedClassName = "org.adscale.*";

        List<String> classes = new JarTestsFinder().calcMethods(TEST_JAR, expectedClassName);

        assertExpectedNumberOfClasses(expectedClassName, classes, 8);
    }


    private void assertExpectedNumberOfClasses(String expectedClassName, List<String> classes, int expected) {
        assertEquals("should find "
                + expected
                + " tests in " + expectedClassName + ", found " + StringUtils.join(classes, ", "), expected, classes.size());
    }

}

package org.adscale.spritnesse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
    public void findTestWithRegularExpression() throws Exception {
        String expectedClassName = "org.adscale.*";

        List<String> classes = new JarTestsFinder().calcMethods(TEST_JAR, expectedClassName);

        assertEquals("should find all tests" + expectedClassName, 6, classes.size());
    }

}

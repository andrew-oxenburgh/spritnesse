package org.adscale.junitFitnesse;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.adscale.JarTestsFinder;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class JarTestsFinder_AppVerify {

    @Test
    @Ignore
    public void test() throws Exception {
        List<String> strings = new JarTestsFinder().calcMethods("fitnesse.jar");
        for (String string : strings) {
            System.out.println(string);
        }
    }


    @Test
    public void demo() throws Exception {
        List<String> strings = new JarTestsFinder().calcMethods("../spritnesse-test/target/spritnesse-test-1.0-SNAPSHOT-tests.jar");
        assertTrue("finds some tests", !strings.isEmpty());
        for (String string : strings) {
            String appVerify = "AppVerify";
            String appTest = "AppTest";
            if (!(string.endsWith(appVerify) || string.endsWith(appTest))) {
                fail("shouldn't find test with name not ending in - " + appVerify + " or " + appTest + " your test was called " + string);
            }
        }
    }


    @Test(expected = RuntimeException.class)
    public void noJarFile() throws Exception {
        new JarTestsFinder().calcMethods("NON-EXISTENT.jar");
    }

}

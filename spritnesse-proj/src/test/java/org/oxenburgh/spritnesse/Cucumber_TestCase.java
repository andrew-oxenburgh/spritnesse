package org.oxenburgh.spritnesse;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.oxenburgh.spritnesse.Utils.assertExpectedNumberOfClasses;

@Ignore("unimplemented")
public class Cucumber_TestCase {
    public static final String TEST_JAR = "./test-cucumber/target/test-cucumber-1.0.0-SNAPSHOT-tests.jar";
    public final static int NUMBER_OF_GOOD_CLASSES = 6;


    @BeforeClass
    public static void before() {
        assertTrue("can't find file " + TEST_JAR + " in " + new File(".").getAbsolutePath(), new File(TEST_JAR).exists());
    }


    @Test(expected = RuntimeException.class)
    public void noJarFile() throws Exception {
        new JarTestsFinder().findClassesIn("NON-EXISTENT.jar");
    }


    @Test
    public void findTestWithName() throws Exception {
        String expectedClassName = ".*Belly.*";

        List<String> classes = new JarTestsFinder().findClassesInLike(TEST_JAR, expectedClassName);

        assertExpectedNumberOfClasses(expectedClassName, classes, 1);
        assertEquals("should be right name of '" + expectedClassName + "'", true, classes.get(0).matches(expectedClassName));
    }



    @Test
    public void cucumberTable_expectToHaveSpockAvailable() throws Exception {
        new CucumberTable("") {
            @Override
            boolean checkCucumberAvailable() {
                return false;
            }
        };
    }


    @Test
    public void findCucumberTests() throws Exception {


    }
}

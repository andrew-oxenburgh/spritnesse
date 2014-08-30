package org.oxenburgh.spritnesse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Spock_TestCase {

    static Logger logger = LoggerFactory.getLogger("chapters.introduction.HelloWorld1");

    public static final String TEST_JAR = "./test-spock/target/test-spock-1.0-jar-with-dependencies.jar";


    @BeforeClass
    public static void before() {
        logger.error("AAAAAAAHHHHHHHH");
        assertTrue("can't find file " + TEST_JAR + " in " + new File(".").getAbsolutePath(), new File(TEST_JAR).exists());
    }


    @Test(expected = RuntimeException.class)
    public void noJarFile() throws Exception {
        new JarTestsFinder().calcMethods("NON-EXISTENT.jar");
    }


    @Test
    public void findTestWithName() throws Exception {
        List<String> classes = new JarTestsFinder().calcMethods(TEST_JAR, "org.oxenburgh.*");
        System.out.println("classes = " + classes);
    }
}

package org.adscale.spritnesse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.adscale.spritnesse.JunitListener;
import org.adscale.spritnesse.enclosed.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.List;

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class JunitListener_TestCase {

    JunitListener listener = new JunitListener();

    JUnitCore core = new JUnitCore();


    @Before
    public void before() throws Exception {
        core.addListener(listener);
    }


    @Test
    public void oneMethod_getName() throws Exception {
        List<String> names = runWithClass(TestWithOnePassingMethod.class);

        assertEquals(1, names.size());
        assertEquals("pass:org.adscale.spritnesse.enclosed.TestWithOnePassingMethod:testPass", names.get(0));
    }


    @Test
    public void twoMethods_getNames() throws Exception {
        List<String> names = runWithClass(TestWithTwoPassingMethods.class);

        assertEquals(2, names.size());
        assertEquals("pass:org.adscale.spritnesse.enclosed.TestWithTwoPassingMethods:testOnePass", names.get(0));
        assertEquals("pass:org.adscale.spritnesse.enclosed.TestWithTwoPassingMethods:testTwoPass", names.get(1));
    }


    @Test
    public void twoMethods_oneFail() throws Exception {
        List<String> names = runWithClass(TestTwoMethodsOneFail.class);

        assertEquals(2, names.size());
        assertTrue("should include stack trace",
                names.get(0).startsWith(
                        "fail:org.adscale.spritnesse.enclosed.TestTwoMethodsOneFail:testFail:java.lang.AssertionError: something more:something more:java.lang.AssertionError: something more\n"
                                + "\tat org.junit.Assert.fail(Assert.java:88)"));
        assertEquals("pass:org.adscale.spritnesse.enclosed.TestTwoMethodsOneFail:testPass", names.get(1));
    }


    @Test
    public void ignoredMethodsIgnored() throws Exception {
        List<String> names = runWithClass(TestWithIgnoredMethod.class);
        assertEquals(1, names.size());
        assertEquals("ignore:org.adscale.spritnesse.enclosed.TestWithIgnoredMethod:testIgnored", names.get(0));
    }


    @Test
    public void ignoredClassIgnored() throws Exception {
        List<String> names = runWithClass(IgnoredClass_withoutValue.class);
        assertEquals(1, names.size());
        assertEquals("ignore:org.adscale.spritnesse.enclosed.IgnoredClass_withoutValue:class ignored", names.get(0));
    }


    @Test
    public void ignoredClassIgnored_showValue() throws Exception {
        List<String> names = runWithClass(IgnoredClass_withValue.class);
        assertEquals(1, names.size());
        assertEquals("ignore:org.adscale.spritnesse.enclosed.IgnoredClass_withValue:some value", names.get(0));
    }


    private List<String> runWithClass(Class clazz) throws Exception {
        core.run(clazz);
        return listener.getTestNames();
    }
}

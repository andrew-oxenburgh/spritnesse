package org.oxenburgh.spritnesse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.oxenburgh.HelloSpockTest;
import org.oxenburgh.spritnesse.enclosed.*;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This file is part of Spritnesse.
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 * <p/>
 * Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.
 */
public class JunitListener_TestCase {

    JunitListener listener = new JunitListener();

    JUnitCore core = new JUnitCore();


    @Before
    public void before() throws Exception {
        core.addListener(listener);
    }


    @Test
    public void oneMethod_spock() throws Exception {
        List<String> names = runWithClass(HelloSpockTest.class);
        assertEquals(6, names.size());
        assertFoundLine(names, "ignore:org.oxenburgh.HelloSpockTest:length of Spock's and his friends' names");
        assertFoundLine(names, "ignore:org.oxenburgh.HelloSpockTest:demo method error");
        assertFoundLine(names, "ignore:org.oxenburgh.HelloSpockTest:addition");
        assertFoundLine(names, "pass:org.oxenburgh.HelloSpockTest:demo method 3");
        assertFoundLine(names, "pass:org.oxenburgh.HelloSpockTest:demo method 2");
        assertFoundLine(names, "pass:org.oxenburgh.HelloSpockTest:demo method 1");
    }

    private void assertFoundLine(List<String> actualSet, String expected) {
        HashSet<String> setOfTests = new HashSet(actualSet);
        if (!setOfTests.contains(expected)) {
            String errorSet = Utils.toString(setOfTests);
            assertTrue(errorSet + " doesn't contain \n" + expected, setOfTests.contains(expected));
        }
    }


    @Test
    public void oneMethod_getName() throws Exception {
        List<String> names = runWithClass(TestWithOnePassingMethod.class);

        assertEquals(1, names.size());
        assertEquals("pass:org.oxenburgh.spritnesse.enclosed.TestWithOnePassingMethod:testPass", names.get(0));
    }


    @Test
    public void twoMethods_getNames() throws Exception {
        List<String> names = runWithClass(TestWithTwoPassingMethods.class);

        assertEquals(2, names.size());
        assertEquals("pass:org.oxenburgh.spritnesse.enclosed.TestWithTwoPassingMethods:testOnePass", names.get(0));
        assertEquals("pass:org.oxenburgh.spritnesse.enclosed.TestWithTwoPassingMethods:testTwoPass", names.get(1));
    }


    @Test
    public void twoMethods_oneFail() throws Exception {
        List<String> names = runWithClass(TestTwoMethodsOneFail.class);

        assertEquals(2, names.size());
        String prefix = "fail:org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail:testFail:java.lang.AssertionError: something more:something more:java.lang.AssertionError: something more\n";
        assertEquals("should include stack trace", prefix.trim(), names.get(0).substring(0, prefix.length()).trim());
        assertEquals("pass:org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail:testPass", names.get(1));
    }


    @Test
    public void ignoredMethodsIgnored() throws Exception {
        List<String> names = runWithClass(TestWithIgnoredMethod.class);
        assertEquals(1, names.size());
        assertEquals("ignore:org.oxenburgh.spritnesse.enclosed.TestWithIgnoredMethod:testIgnored", names.get(0));
    }


    @Test
    public void ignoredClassIgnored() throws Exception {
        List<String> names = runWithClass(IgnoredClass_withoutValue.class);
        assertEquals(1, names.size());
        assertEquals("ignore:org.oxenburgh.spritnesse.enclosed.IgnoredClass_withoutValue:class ignored", names.get(0));
    }


    @Test
    public void ignoredClassIgnored_showValue() throws Exception {
        List<String> names = runWithClass(IgnoredClass_withValue.class);
        assertEquals(1, names.size());
        assertEquals("ignore:org.oxenburgh.spritnesse.enclosed.IgnoredClass_withValue:some value", names.get(0));
    }


    private List<String> runWithClass(Class clazz) throws Exception {
        core.run(clazz);
        return listener.getTestNames();
    }
}

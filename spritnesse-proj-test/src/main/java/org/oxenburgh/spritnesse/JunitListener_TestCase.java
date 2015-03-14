package org.oxenburgh.spritnesse;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.oxenburgh.spritnesse.Helper.assertFoundLine;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.oxenburgh.HelloSpockTest;
import org.oxenburgh.spritnesse.enclosed.ExceptionThrownClass;
import org.oxenburgh.spritnesse.enclosed.IgnoredClass_withValue;
import org.oxenburgh.spritnesse.enclosed.IgnoredClass_withoutValue;
import org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail;
import org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail_withSemiColon;
import org.oxenburgh.spritnesse.enclosed.TestWithIgnoredMethod;
import org.oxenburgh.spritnesse.enclosed.TestWithOnePassingMethod;
import org.oxenburgh.spritnesse.enclosed.TestWithTwoPassingMethods;

import java.util.List;

/**
 * This file is part of Spritnesse.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3.0 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * <p>
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
    public void oneMethod_getName() throws Exception {
        List<List<String>> names = runWithClass(TestWithOnePassingMethod.class);

        assertEquals(1, names.size());
        assertListContainsString("pass.+org.oxenburgh.spritnesse.enclosed.TestWithOnePassingMethod.+testPass", names.get(0));
    }


    @Test
    public void twoMethods_getNames() throws Exception {
        List<List<String>> names = runWithClass(TestWithTwoPassingMethods.class);

        assertEquals(2, names.size());
        assertListContainsString("pass.+org.oxenburgh.spritnesse.enclosed.TestWithTwoPassingMethods.+testOnePass", names.get(0));
        assertListContainsString("pass.+org.oxenburgh.spritnesse.enclosed.TestWithTwoPassingMethods.+testTwoPass", names.get(1));
    }


    @Test
    public void twoMethods_oneFail() throws Exception {
        List<List<String>> names = runWithClass(TestTwoMethodsOneFail.class);

        assertEquals(2, names.size());
        String prefix =
                "fail.+org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail.+testFail:java.lang.AssertionError: something more.+something more,java.lang.AssertionError.+ something more\n";
        //        assertEquals("should include stack trace", prefix.trim(), names.get(0).substring(0, prefix.length()).trim());
        assertFoundLine(names, "pass:org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail:testPass");
    }


    @Test
    public void twoMethods_oneFail_withSemiColon() throws Exception {
        List<List<String>> names = runWithClass(TestTwoMethodsOneFail_withSemiColon.class);

        assertEquals(2, names.size());
        String prefix =
                "fail:org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail_withSemiColon:testFail:java.lang.AssertionError: there are 2 semi-colons here:here's the first:and here's the second:th";
//                assertStringEqualsList("should include stack trace", prefix.trim(), names.get(0).substring(0, prefix.length()).trim());
        assertListContainsString("pass.+org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail_withSemiColon.+testPass", names.get(1));
    }


    @Test
    public void ignoredMethodsIgnored() throws Exception {
        List<List<String>> names = runWithClass(TestWithIgnoredMethod.class);
        assertEquals(1, names.size());
        assertListContainsString("ignore, org.oxenburgh.spritnesse.enclosed.TestWithIgnoredMethod, testIgnored", names.get(0));
    }


    @Test
    public void ignoredClassIgnored() throws Exception {
        List<List<String>> names = runWithClass(IgnoredClass_withoutValue.class);
        assertEquals(1, names.size());
        assertListContainsString("ignore.+org.oxenburgh.spritnesse.enclosed.IgnoredClass_withoutValue.+class ignored", names.get(0));
    }


    @Test
    public void ignoredClassIgnored_showValue() throws Exception {
        List<List<String>> names = runWithClass(IgnoredClass_withValue.class);
        assertEquals(1, names.size());
        assertListContainsString("ignore.+org.oxenburgh.spritnesse.enclosed.IgnoredClass_withValue.+some value", names.get(0));
    }


    private void assertListContainsString(String expected, List<String> actual) {
        String errorMessage = format("didn't find [%s] in [%s]", expected, actual);
        String actualAsString = actual + "";
        String regex = "^.+" + expected + ".+$";

        assertTrue(errorMessage, actualAsString.matches(regex));
    }


    private List<List<String>> runWithClass(Class clazz) throws Exception {
        core.run(clazz);
        return listener.getTestResults();
    }
}

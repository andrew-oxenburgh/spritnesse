package org.oxenburgh.spritnesse;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
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
        List<SpritnesseTestResult> results = runWithClass(TestWithOnePassingMethod.class);

        List<String> expectedList = asList("pass", "org.oxenburgh.spritnesse.enclosed.TestWithOnePassingMethod", "testPass");
        assertTrue("should find correct line", results.get(0).compareToByRegexp(expectedList));
    }


    @Test
    public void twoMethods_getNames() throws Exception {
        List<SpritnesseTestResult> results = runWithClass(TestWithTwoPassingMethods.class);

        assertEquals(2, results.size());

        List<String> expectedList = asList("pass", "org.oxenburgh.spritnesse.enclosed.TestWithTwoPassingMethods", "testOnePass");
        assertTrue("should find correct line", results.get(0).compareToByRegexp(expectedList));

        List<String> expectedList2 = asList("pass", "org.oxenburgh.spritnesse.enclosed.TestWithTwoPassingMethods", "testTwoPass");
        assertTrue("should find correct line", results.get(1).compareToByRegexp(expectedList2));
    }


    @Test
    public void twoMethods_oneFail() throws Exception {
        List<SpritnesseTestResult> results = runWithClass(TestTwoMethodsOneFail.class);

        assertEquals(2, results.size());
        List<String> expectedList =
                asList("fail", "org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail", "testFail", "something more", ".*java.lang.AssertionError: something more.*");
        assertTrue("should find correct line", results.get(0).compareToByRegexp(expectedList));
    }


    @Test
    public void twoMethods_oneFail_withSemiColon() throws Exception {
        List<SpritnesseTestResult> results = runWithClass(TestTwoMethodsOneFail_withSemiColon.class);

        assertEquals(2, results.size());

        List<String> expectedList = asList("fail",
                "org.oxenburgh.spritnesse.enclosed.TestTwoMethodsOneFail_withSemiColon",
                "testFail",
                "there are 2 semi-colons here:here's the first:and here's the second",
                ".*java.lang.AssertionError: there are 2 semi-colons here:here's the first:and here's the second.*");
        assertTrue("should find correct line", results.get(0).compareToByRegexp(expectedList));
    }


    @Test
    public void ignoredMethodsIgnored() throws Exception {
        List<SpritnesseTestResult> results = runWithClass(TestWithIgnoredMethod.class);
        assertEquals(1, results.size());

        List<String> expectedList = asList("ignore", "org.oxenburgh.spritnesse.enclosed.TestWithIgnoredMethod", "testIgnored");
        assertTrue("should find correct line", results.get(0).compareToByRegexp(expectedList));
    }


    @Test
    public void ignoredClassIgnored() throws Exception {
        List<SpritnesseTestResult> results = runWithClass(IgnoredClass_withoutValue.class);
        assertEquals(1, results.size());
        List<String> expectedList = asList("ignore", "org.oxenburgh.spritnesse.enclosed.IgnoredClass_withoutValue", "class ignored");
        assertTrue("should find correct line", results.get(0).compareToByRegexp(expectedList));
    }


    @Test
    public void ignoredClassIgnored_showValue() throws Exception {
        List<SpritnesseTestResult> results = runWithClass(IgnoredClass_withValue.class);
        assertEquals(1, results.size());
        List<String> expectedList = asList("ignore", "org.oxenburgh.spritnesse.enclosed.IgnoredClass_withValue", "some value");
        assertTrue("should find correct line", results.get(0).compareToByRegexp(expectedList));
    }


    @Test
    public void cucumberWillCallAFinalFinish_whichWillCauseADuplicatedLastLine_OrdinaryTestsLinesAreLast() throws Exception {
        JunitListener listener = new JunitListener();

        String expectedDesc = "good start and end";
        Description desc = Description.createTestDescription(String.class, expectedDesc);
        listener.testStarted(desc);
        listener.testFinished(desc);
        String actualDesc = listener.getTestResults().get(0).getMethodName();
        assertEquals(expectedDesc, actualDesc);
    }


    @Test
    public void cucumberWillCallAFinalFinish_whichWillCauseADuplicatedLastLine_DifferingLinesAreIgnored() throws Exception {
        JunitListener listener = new JunitListener();

        listener.testStarted(Description.createTestDescription(String.class, "starting line"));
        listener.testFinished(Description.createTestDescription(String.class, "ending line"));
        assertEquals("should ignore where current test and finished tests description differ", 0, listener.getTestResults().size());
    }


    private List<SpritnesseTestResult> runWithClass(Class clazz) throws Exception {
        core.run(clazz);
        return listener.getTestResults();
    }
}

package org.oxenburgh.spritnesse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.oxenburgh.spritnesse.enclosed.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.List;

/**
 This file is part of Spritnesse.
 <p/>
 Spritnesse is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 <p/>
 Spritnesse is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 <p/>
 You should have received a copy of the GNU General Public License
 along with Spritnesse.  If not, see <http://www.gnu.org/licenses/>.

 Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.

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

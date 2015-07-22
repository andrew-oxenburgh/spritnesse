package org.oxenburgh.spritnesse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 This file is part of Spritnesse.

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 3.0 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library.

 Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.

 */
public class CamelCaser_TestCase {

    private CamelCaser camelCaser = new CamelCaser();


    @Test
    public void className_removePackage() throws Exception {
        assertEquals("another", camelCaser.classMassage("org.oxenburgh.integration.AnotherTest"));
    }


    @Test
    public void className_removeClassSuffix() throws Exception {
        assertEquals("another", camelCaser.classMassage("org.oxenburgh.integration.AnotherTestDemo"));
    }


    @Test
    public void className_removePackage_withNoPackage() throws Exception {
        assertEquals("another", camelCaser.classMassage("AnotherTest"));
    }


    @Test
    public void removeCamelCasing_everythingLowerCase_spacesInsteadOfCapitals() throws Exception {
        assertSentenizes("testMeHere", "me here");
    }


    @Test
    public void removeCamelCasing_keepsPrefisNumbers() throws Exception {
        assertSentenizes("1. test Me Here", "1. test  me  here");
        assertSentenizes("1.test Me Here", "1.test  me  here");
    }


    @Test
    public void handlesUnderScores_andNumbers() throws Exception {
        assertSentenizes("a1_advert", "a 1, advert");
    }


    @Test
    public void handlesMultiDigitNumbers() throws Exception {
        assertSentenizes("a12A", "a 12 a");
    }


    @Test
    public void handlesSingleDigitNumbers() throws Exception {
        assertSentenizes("a1A", "a 1 a");
    }


    @Test
    public void handlesLeadingNumbers() throws Exception {
        assertSentenizes("1. A", "1.  a");
        assertSentenizes("1. a", "1. a");
    }


    @Test
    public void numbers_littleLettersFollowingNumberAreAppendedToNumber() throws Exception {
        assertSentenizes("a1a", "a 1a");
    }


    @Test
    public void numbers_bigLettersFollowingNumberAreSeparatedFromNumber() throws Exception {
        assertSentenizes("a1A", "a 1 a");
    }


    @Test
    public void ignorePrefixTest() throws Exception {
        assertSentenizes("TestAB", "a b");
    }


    @Test
    public void ignorePrefixTest_lowerCase() throws Exception {
        assertSentenizes("testAB", "a b");
    }


    @Test
    public void includeMidfixTest() throws Exception {
        assertSentenizes("aTestA", "a test a");
    }


    @Test
    public void removePostfixTestCase() throws Exception {
        assertSentenizes("aTestCase", "a");
    }


    @Test
    public void removePostfixTest() throws Exception {
        assertSentenizes("aTest", "a");
    }


    @Test
    public void includeMidfixTestCase() throws Exception {
        assertSentenizes("aTestCaseA", "a test case a");
    }


    @Test
    public void ignorePostfixAppVerify() throws Exception {
        assertSentenizes("aAppVerify", "a");
    }


    @Test
    public void includeMidfixAppVerify() throws Exception {
        assertSentenizes("aAppVerifyA", "a app verify a");
    }

    private void assertSentenizes(String input, String expected) {
        assertEquals(expected, camelCaser.sentenize(input));
    }

}

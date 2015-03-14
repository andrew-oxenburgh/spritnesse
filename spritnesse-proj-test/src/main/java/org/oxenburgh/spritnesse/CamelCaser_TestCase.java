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
        assertSentenizes("me here", "testMeHere");
    }


    @Test
    public void removeCamelCasing_keepsPrefisNumbers() throws Exception {
        assertSentenizes("1. test  me  here", "1. test Me Here");
        assertSentenizes("1.test  me  here", "1.test Me Here");
    }


    @Test
    public void handlesUnderScores_andNumbers() throws Exception {
        assertSentenizes("a 1, advert", "a1_advert");
    }


    @Test
    public void handlesMultiDigitNumbers() throws Exception {
        assertSentenizes("a 12 a", "a12A");
    }


    @Test
    public void handlesSingleDigitNumbers() throws Exception {
        assertSentenizes("a 1 a", "a1A");
    }


    @Test
    public void numbers_littleLettersFollowingNumberAreAppendedToNumber() throws Exception {
        assertSentenizes("a 1a", "a1a");
    }


    @Test
    public void numbers_bigLettersFollowingNumberAreSeparatedFromNumber() throws Exception {
        assertSentenizes("a 1 a", "a1A");
    }


    @Test
    public void ignorePrefixTest() throws Exception {
        assertSentenizes("a b", "TestAB");
    }


    @Test
    public void ignorePrefixTest_lowerCase() throws Exception {
        assertSentenizes("a b", "testAB");
    }


    @Test
    public void includeMidfixTest() throws Exception {
        assertSentenizes("a test a", "aTestA");
    }


    @Test
    public void removePostfixTestCase() throws Exception {
        assertSentenizes("a", "aTestCase");
    }


    @Test
    public void removePostfixTest() throws Exception {
        assertSentenizes("a", "aTest");
    }


    @Test
    public void includeMidfixTestCase() throws Exception {
        assertSentenizes("a test case a", "aTestCaseA");
    }


    @Test
    public void ignorePostfixAppVerify() throws Exception {
        assertSentenizes("a", "aAppVerify");
    }


    @Test
    public void includeMidfixAppVerify() throws Exception {
        assertSentenizes("a app verify a", "aAppVerifyA");
    }

    private void assertSentenizes(String expected, String input) {
        assertEquals(expected, camelCaser.sentenize(input));
    }

}

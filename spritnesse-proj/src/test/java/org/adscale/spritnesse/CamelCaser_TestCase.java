package org.adscale.spritnesse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class CamelCaser_TestCase {

    private CamelCaser camelCaser = new CamelCaser();


    @Test
    public void className_removePackage() throws Exception {
        assertEquals("another", camelCaser.classMassage("org.adscale.integration.AnotherTest"));
    }


    @Test
    public void className_removeClassSuffix() throws Exception {
        assertEquals("another", camelCaser.classMassage("org.adscale.integration.AnotherTestDemo"));
    }


    @Test
    public void className_removePackage_withNoPackage() throws Exception {
        assertEquals("another", camelCaser.classMassage("AnotherTest"));
    }


    @Test
    public void removeCamelCasing_everythingLowerCase_spacesInsteadOfCapitals() throws Exception {
        assertTransorm("me here", "testMeHere");
    }


    @Test
    public void handlesUnderScores_andNumbers() throws Exception {
        assertTransorm("a 1, advert", "a1_advert");
    }


    @Test
    public void handlesMultiDigitNumbers() throws Exception {
        assertTransorm("a 12 a", "a12A");
    }


    @Test
    public void handlesSingleDigitNumbers() throws Exception {
        assertTransorm("a 1 a", "a1A");
    }


    @Test
    public void numbers_littleLettersFollowingNumberAreAppendedToNumber() throws Exception {
        assertTransorm("a 1a", "a1a");
    }


    @Test
    public void numbers_bigLettersFollowingNumberAreSeparatedFromNumber() throws Exception {
        assertTransorm("a 1 a", "a1A");
    }


    @Test
    public void ignorePrefixTest() throws Exception {
        assertTransorm("a b", "TestAB");
    }


    @Test
    public void ignorePrefixTest_lowerCase() throws Exception {
        assertTransorm("a b", "testAB");
    }


    @Test
    public void includeMidfixTest() throws Exception {
        assertTransorm("a test a", "aTestA");
    }


    @Test
    public void removePostfixTestCase() throws Exception {
        assertTransorm("a", "aTestCase");
    }


    @Test
    public void removePostfixTest() throws Exception {
        assertTransorm("a", "aTest");
    }


    @Test
    public void includeMidfixTestCase() throws Exception {
        assertTransorm("a test case a", "aTestCaseA");
    }


    @Test
    public void ignorePostfixAppVerify() throws Exception {
        assertTransorm("a", "aAppVerify");
    }


    @Test
    public void includeMidfixAppVerify() throws Exception {
        assertTransorm("a app verify a", "aAppVerifyA");
    }

    private void assertTransorm(String expected, String input) {
        assertEquals(expected, camelCaser.sentenize(input));
    }

}

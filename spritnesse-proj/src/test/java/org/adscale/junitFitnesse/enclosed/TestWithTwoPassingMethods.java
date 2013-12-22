package org.adscale.junitFitnesse.enclosed;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestWithTwoPassingMethods {

    @Test
    public void testOnePass() throws Exception {
        assertTrue(true);
    }


    @Test
    public void testTwoPass() throws Exception {
        assertTrue(true);
    }

}

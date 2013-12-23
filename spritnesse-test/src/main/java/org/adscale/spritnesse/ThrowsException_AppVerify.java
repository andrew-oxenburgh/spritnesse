package org.adscale.spritnesse;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class ThrowsException_AppVerify {

    @Test
    public void throwsException() throws Exception {
        throw new RuntimeException("throw me");
    }


    @Test
    public void failsAssertion_withMessage() throws Exception {
        assertEquals("here's a message", true, false);
    }

    @Test
    public void failsAssertion_withoutMessage() throws Exception {
        assertEquals(true, false);
    }

}

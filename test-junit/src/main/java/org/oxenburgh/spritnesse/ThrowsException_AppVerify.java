package org.oxenburgh.spritnesse;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

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
 * <p>
 * Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.
 */
public class ThrowsException_AppVerify {

    @Test
    public void throwsException() throws Exception {
        throw new RuntimeException("throw me");
    }


    @Test
    public void throwsNestedException() throws Exception {
        try {
            throw new RuntimeException("inner exception");
        }
        catch (RuntimeException e) {
            throw new RuntimeException("outer exception", e);
        }
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

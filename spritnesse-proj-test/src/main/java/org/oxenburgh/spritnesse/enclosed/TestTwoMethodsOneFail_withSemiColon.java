package org.oxenburgh.spritnesse.enclosed;

import static org.junit.Assert.fail;

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
public class TestTwoMethodsOneFail_withSemiColon {

    @Test
    public void testPass() throws Exception {

    }


    @Test
    public void testFail() throws Exception {
        fail("there are 2 semi-colons here:here's the first:and here's the second");
    }

}

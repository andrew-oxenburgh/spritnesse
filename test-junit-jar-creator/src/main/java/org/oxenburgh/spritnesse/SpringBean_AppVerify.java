package org.oxenburgh.spritnesse;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

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

 <p/>
 Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.
 */

@ContextConfiguration("classpath:/spring.xml")
public class SpringBean_AppVerify extends AbstractJUnit4SpringContextTests {

    @Resource(name = "thingy")
    String thingy;


    @Test
    public void demo() {
        assertNotNull(thingy);
    }
}

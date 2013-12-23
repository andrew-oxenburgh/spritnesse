package org.adscale.spritnesse;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class IgnoreMethod_AppVerify {

    @Test
    @Ignore
    public void testIgnoreMe() throws Exception {

    }

    @Test
    public void testNotIgnored() throws Exception {

    }

}

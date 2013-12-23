package org.adscale.spritnesse.enclosed;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class TestWithIgnoredMethod {

    @Test
    @Ignore
    public void testIgnored() throws Exception {
        assertTrue(true);
    }
}

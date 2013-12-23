package org.adscale.spritnesse.enclosed;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA. User: andrewo Date: 9/05/13 Time: 1:19 PM To change this template use File | Settings | File Templates.
 */
public class TestTwoMethodsOneFail {

    @Test
    public void testPass() throws Exception {

    }


    @Test
    public void testFail() throws Exception {
        fail("something more");
    }

}

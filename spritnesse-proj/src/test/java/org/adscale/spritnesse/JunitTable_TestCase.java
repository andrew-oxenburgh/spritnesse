package org.adscale.spritnesse;

import static org.junit.Assert.assertEquals;
import static util.ListUtility.list;

import org.junit.Test;

import java.util.List;

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class JunitTable_TestCase {

    private final JunitTable table = new JunitTable("");


    @Test
    public void givenSinglePassingTest_createTable() throws Exception {
        List list = list("pass:class:method");

        List expected = list(list("report:class", "report:method", "pass"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void givenSingleIgnoredTest_markedIgnore() throws Exception {
        List list = list("ignored:class:method");

        List expected = list(list("report:class", "report:method", "ignored"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void givenTwoPassingTest_secondOneDoesntHaveClassName_createTable() throws Exception {
        List list = list("pass:class:method", "pass:class:method2");

        List expected = list(list("report:class", "report:method", "pass"), list("report:", "report:method 2", "pass"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void givenTwoFailingTests_secondOneDoesntHaveClassName_createTable() throws Exception {
        List list = list("fail:class:method", "fail:class:method2");

        List expected = list(list("report:class", "report:method", "fail"), list("report:", "report:method 2", "fail"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void givenTwoFailingTests_oneWithError_secondOneDoesntHaveClassName_createTable() throws Exception {
        List list = list("fail:class:method:exception:message:trace", "fail:class:method2:exception2:message2:trace2");

        List expected = list(list("report:class", "report:method",
                "fail:exception->message"), list("report:", "report:method 2",
                "fail:exception2->message2"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void shouldntHaveUnescapedSingleQuotesInTrace() throws Exception {
        List list = list("fail:class:method:exception:message:here's an error");

        List expected = list(list("report:class", "report:method",
                "fail:exception->message"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void givenFailingTests_withColon_showsFullError() throws Exception {
        List list = list("fail:class:method:exception:message:trace", "fail:class:method2:exception2:message2:trace2");

        List expected = list(list("report:class", "report:method",
                "fail:exception->message"), list("report:", "report:method 2",
                "fail:exception2->message2"));

        assertEquals(expected, table.makeTable(list));
    }
}

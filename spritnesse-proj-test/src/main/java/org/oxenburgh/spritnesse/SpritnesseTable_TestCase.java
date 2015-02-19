package org.oxenburgh.spritnesse;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.oxenburgh.spritnesse.Helper.assertFoundCell;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
 * Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.
 */
public class SpritnesseTable_TestCase {

    private final JunitTable table = new JunitTable("");

    private List<List<String>> EMPTY_LIST_OF_LISTS ;


    @Before
    public void before() {
        EMPTY_LIST_OF_LISTS = new ArrayList<List<String>>();
        EMPTY_LIST_OF_LISTS.add(new ArrayList<String>());
    }


    @Test
    public void givenSinglePassingTest_createTable() throws Exception {
        List list = asList("pass:class:method");

        List expected = asList(asList("report:class", "report:method", "pass"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void whenPassedUnknownJar_saySomething() throws Exception {
        List expected = asList(asList(), asList("no such jar found [unknown.jar]"));
        assertEquals(expected + "", new JunitTable("unknown.jar").doTable(EMPTY_LIST_OF_LISTS) + "");
    }


    @Test
    public void givenSingleIgnoredTest_markedIgnore() throws Exception {
        List list = asList("ignored:class:method");

        List expected = asList(asList("report:class", "report:method", "ignored"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void givenTwoPassingTest_secondOneDoesntHaveClassName_createTable() throws Exception {
        List list = asList("pass:class:method", "pass:class:method2");

        List expected = asList(asList("report:class", "report:method", "pass"), asList("report:", "report:method 2", "pass"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void givenTwoFailingTests_secondOneDoesntHaveClassName_createTable() throws Exception {
        List list = asList("fail:class:method", "fail:class:method2");

        List expected = asList(asList("report:class", "report:method", "fail"), asList("report:", "report:method 2", "fail"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void givenTwoFailingTests_oneWithError_secondOneDoesntHaveClassName_createTable() throws Exception {
        List list = asList("fail:class:method:exception:message:trace", "fail:class:method2:exception2:message2:trace2");

        List expected = asList(asList("report:class", "report:method", "fail:exception->message"),
                asList("report:", "report:method 2", "fail:exception2->message2"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void shouldntHaveUnescapedSingleQuotesInTrace() throws Exception {
        List list = asList("fail:class:method:exception:message:here's an error");

        List expected = asList(asList("report:class", "report:method", "fail:exception->message"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void givenFailingTests_withColon_showsFullError() throws Exception {
        List list = asList("fail:class:method:exception:message:trace", "fail:class:method2:exception2:message2:trace2");

        List expected = asList(asList("report:class", "report:method", "fail:exception->message"),
                asList("report:", "report:method 2", "fail:exception2->message2"));

        assertEquals(expected, table.makeTable(list));
    }


    @Test
    public void noTestsFound() throws Exception {
        SpritnesseTable spritnesseTable = new SpritnesseTable("") {
            @Override
            List getClassesToBeTested(List<List<String>> args) {
                return new ArrayList();
            }


            @Override
            List intDoTable(List<List<String>> args) throws ClassNotFoundException {
                return new ArrayList();
            }
        };

        List<List<String>> results = spritnesseTable.doTable(EMPTY_LIST_OF_LISTS);
        assertEquals("should get 2 lines", 2, results.size());
        assertFoundCell(results, "should find 1 test");
    }
}

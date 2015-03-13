package org.oxenburgh.spritnesse;

import static junit.framework.TestCase.fail;

import java.util.Arrays;
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
public class Helper {

    public static void assertFoundLine(List<List<String>> actualSet, String expected) {
        List<String> expectedList = Arrays.asList(expected.split(":"));
        if (foundLine(actualSet, expectedList)) {
            return;
        }
        fail(String.format("doesn't contain expected string %s, %s", expected, actualSet));
    }


    private static boolean foundLine(List<List<String>> actualSet, List<String> expected) {
        for (List<String> s : actualSet) {
            if (!s.isEmpty() && (expected + "").matches("^.+" + s + ".+$")) {
                return true;
            }
        }
        return false;
    }
}

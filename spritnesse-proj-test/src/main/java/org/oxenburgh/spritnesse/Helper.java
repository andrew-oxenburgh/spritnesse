package org.oxenburgh.spritnesse;

import static junit.framework.TestCase.fail;

import java.util.List;
import java.util.regex.Pattern;

/**
 * This file is part of Spritnesse.
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 * <p/>
 * Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.
 */
public class Helper {

    public static void assertFoundLine(List<String> actualSet, String expected) {
        if (foundLine(actualSet, expected)) {
            return;
        }
        fail(String.format("doesn't contain expected string %s, %s", expected, actualSet));
    }


    private static boolean foundLine(List<String> actualSet, String expected) {
        Pattern pattern = Pattern.compile(expected);
        for (String s : actualSet) {
            if (pattern.matcher(s).find()) {
                return true;
            }
        }
        return false;
    }


    public static void assertFoundCell(List<List<String>> actualSet, String expected) {
        for (List<String> strings : actualSet) {
            if (foundLine(strings, expected)) {
                return;
            }
        }
        fail(String.format("doesn't contain expected string %s, %s", expected, actualSet));
    }
}

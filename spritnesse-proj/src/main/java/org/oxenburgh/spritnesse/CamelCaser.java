package org.oxenburgh.spritnesse;

/**
 This file is part of Spritnesse.

 Spritnesse is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Spritnesse is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

public class CamelCaser {

    public String sentenize(String input) {
        String ret = input;
        ret = ret.trim().replaceAll("^[Tt]est", "");
        ret = ret.trim().replaceAll("TestCase$", "");
        ret = ret.trim().replaceAll("Test$", "");
        ret = ret.trim().replaceAll("AppVerify$", "");
        ret = ret.trim().replaceAll("TestDemo$", "");
        ret = replace(ret, "(?=[A-Z])", " ");

        ret = replace(ret, "_", ", ");

        ret = replace(ret, "(?=\\d)(?<=\\p{L})", " ");

        ret = ret.trim().toLowerCase();
        return ret.trim();
    }


    private String replace(String input, String pattern, String replace) {
        String[] strings;
        strings = input.split(pattern);
        return catStrings(strings, replace);
    }


    private String catStrings(String[] strings, String sepChar) {
        String ret = "";
        boolean first = true;
        for (String string : strings) {
            if (first) {
                ret += string;
                first = false;
            }
            else {
                ret += sepChar + string;
            }
        }
        return ret;
    }


    public String classMassage(String input) {
        return sentenize(input.substring(input.lastIndexOf(".") + 1));
    }

}
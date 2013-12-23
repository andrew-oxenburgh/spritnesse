package org.adscale.spritnesse;

/**
 * Copyright AdScale, GmbH, Germany (c) 2007 - 2013
 */
public class CamelCaser {

    public String sentenize(String input) {
        String ret = input;
        ret = ret.trim().replaceAll("^[Tt]est", "");
        ret = ret.trim().replaceAll("TestCase$", "");
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

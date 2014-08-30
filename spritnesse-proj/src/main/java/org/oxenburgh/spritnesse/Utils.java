package org.oxenburgh.spritnesse;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by andrew on 30/08/14.
 */
public class Utils {
    public static String toString(Collection setOfTests) {
        String errorSet = "[\n";

        for (Object test : setOfTests) {
            errorSet += test + ", \n";
        }
        errorSet += "]";
        return errorSet;
    }


}

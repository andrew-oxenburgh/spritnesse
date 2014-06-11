package org.oxenburgh.spritnesse;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class JunitListener extends RunListener {

    private List<String> testNames = new ArrayList<String>();

    String key;

    boolean failed;


    @Override
    public void testStarted(Description description) throws Exception {
        key = createKey(description);
        testNames.add(key);
        failed = false;
    }


    @Override
    public void testFinished(Description ignore) throws Exception {
        if (!failed) {
            prependCurrentTest("pass");
        }
    }


    @Override
    public void testFailure(Failure failure) throws Exception {
        testNames.remove(key);
        failed = true;

        testNames.add("fail" + ":" + key + ":" + failure.getException() + ":" + failure.getMessage() + ":" + failure.getTrace());
    }


    private void prependCurrentTest(String prepend) {
        testNames.remove(key);
        testNames.add(prepend + ":" + key);
    }


    @Override
    public void testIgnored(Description description) throws Exception {
        testNames.add("ignore:" + createKey(description));
    }


    public List<String> getTestNames() {
        return testNames;
    }


    private String createKey(Description description) {
        String methodName = description.getMethodName();
        if (methodName == null) {
            Iterator<Annotation> iter = description.getAnnotations().iterator();

            while (iter.hasNext()) {
                Annotation note = iter.next();
                if (note.annotationType().getName().equals("org.junit.Ignore")) {
                    String s = note.toString();
                    String[] parts = s.split("=");
                    String[] littleParts = parts[1].split("\\)");
                    methodName = littleParts.length > 0 ? littleParts[0] : "";
                    break;
                }
            }
            if (methodName == null || methodName.isEmpty()) {
                methodName = "class ignored";
            }
        }
        return description.getClassName() + ":" + methodName;
    }
}
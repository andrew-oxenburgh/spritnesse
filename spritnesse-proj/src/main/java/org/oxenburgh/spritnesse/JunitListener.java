package org.oxenburgh.spritnesse;

import static java.util.Arrays.asList;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 This file is part of Spritnesse.

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 3.0 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library.

 Copyright (c) 2014, Andrew Oxenburgh, All rights reserved.

 */
public class JunitListener extends RunListener {

    static Logger log = LoggerFactory.getLogger(JunitListener.class);


    private List<String> testNames = new ArrayList<>();

    List<SpritnesseTestResult> testResults = new ArrayList<>();

    private List<String> currentKey;

    boolean failed;


    @Override
    public void testStarted(Description description) throws Exception {
        log.debug("test started - {}", description);
        currentKey = createKey(description);
        testNames.add(currentKey + "");
        failed = false;
    }


    @Override
    public void testFinished(Description ignore) throws Exception {
        if(!currentKey.equals(createKey(ignore))){
            log.debug("this is not what we signed up for");
            return;
        }
        log.debug("test finished - {} = {}", ignore, failed);
        if (!failed) {
            prependCurrentTest("pass");
        }
    }


    @Override
    public void testFailure(Failure failure) throws Exception {
        SpritnesseTestResult testResult = new SpritnesseTestResult();

        log.debug("test failure - {}", failure);
        testNames.remove(currentKey);
        failed = true;
        testResult.setStatus("fail");
        testResult.setClassName(currentKey.get(0));
        testResult.setMethodName(currentKey.get(1));
        testResult.setError(failure.getMessage());
        testResult.setTrace(failure.getTrace());

        testResults.add(testResult);
    }


    @Override
    public void testIgnored(Description description) throws Exception {
        SpritnesseTestResult testResult = new SpritnesseTestResult();
        
        log.debug("test ignored - {}", description);
        List<String> key = createKey(description);
        testNames.add("ignore:" + key);
        testResult.setStatus("ignore");
        testResult.setClassName(key.get(0));
        testResult.setMethodName(key.get(1));

        testResults.add(testResult);
    }


    @Override
    public void testRunStarted(Description description) throws Exception {
        log.debug("test run started - {}", description);
    }


    @Override
    public void testRunFinished(Result result) throws Exception {
        log.debug("test run finished - {}", result);
    }


    @Override
    public void testAssumptionFailure(Failure failure) {
        log.debug("test assumption failed - {}", failure);
    }


    private void prependCurrentTest(String prepend) {
        SpritnesseTestResult testResult = new SpritnesseTestResult();

        testNames.remove(currentKey);
        testResult.setStatus(prepend);
        testResult.setClassName(currentKey.get(0));
        testResult.setMethodName(currentKey.get(1));

        testResults.add(testResult);
    }


    public List<String> getTestNames() {
        return testNames;
    }

    public List<SpritnesseTestResult> getTestResults() {
        return testResults;
    }


    private List<String> createKey(Description description) {
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
        return asList(description.getClassName(), methodName);
    }
}
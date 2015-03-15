package org.oxenburgh.spritnesse;

import static java.util.regex.Pattern.compile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by andrewo on 15/03/15.
 */
public class SpritnesseTestResult {

    static Logger log = LoggerFactory.getLogger(SpritnesseTestResult.class);


    private String status;

    private String className;

    private String methodName;

    private String trace;

    private String error;


    public SpritnesseTestResult(String status, String className, String methodName, String error, String trace) {
        this.status = status;
        this.className = className;
        this.methodName = methodName;
        this.trace = trace;
        this.error = error;
    }


    public SpritnesseTestResult(String status, String className, String methodName) {
        this.status = status;
        this.className = className;
        this.methodName = methodName;
        this.trace = null;
        this.error = null;
    }


    public boolean compareToByRegexp(List<String> regexes) {
        List<String> listRep = new ArrayList<>();
        listRep.add(status);
        listRep.add(className);
        listRep.add(methodName);
        listRep.add(error);
        listRep.add(trace);

        return regexEachRegexMatchesInOrder(regexes, listRep);
    }


    private boolean regexEachRegexMatchesInOrder(List<String> list, List<String> listRep) {
        for (int i = 0; i < list.size(); i++) {
            String obj = listRep.get(i);
            String cmp = "^" + list.get(i) + "$";

            Pattern compile = compile(cmp, Pattern.DOTALL);

            if (!compile.matcher(obj).find()) {
                log.debug("{} does not match {} on line {}", obj, cmp, i);
                return false;
            }
        }
        return true;
    }


    public SpritnesseTestResult() {
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public void setClassName(String className) {
        this.className = className;
    }


    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    public void setTrace(String trace) {
        this.trace = trace;
    }


    public String getStatus() {
        return status;
    }


    public String getClassName() {
        return className;
    }


    public String getMethodName() {
        return methodName;
    }


    public String getTrace() {
        return trace;
    }


    public String toString() {
        return className + ", " + methodName + ", " + status + ", " + trace;
    }


    public String getError() {
        return error;
    }


    public void setError(String error) {
        this.error = error;
    }
}

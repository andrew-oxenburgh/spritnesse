package org.oxenburgh.spritnesse;

import java.util.List;

import static util.ListUtility.list;

public class CucumberTable extends JunitTable {
    boolean cucumberAvailable = true;

    public CucumberTable(String jarName) {
        super(jarName);
//        cucumberAvailable = checkCucumberAvailable();
    }

    boolean checkCucumberAvailable() {
        String spockSpecName = "cucumber.api.junit.Cucumber";
        try {
            this.getClass().getClassLoader().loadClass(spockSpecName);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public List doTable(List<List<String>> args) throws ClassNotFoundException {
        if (!cucumberAvailable) {
            return list(list("cucumber unavailable on classpath"));
        }
        return super.doTable(args);
    }
}

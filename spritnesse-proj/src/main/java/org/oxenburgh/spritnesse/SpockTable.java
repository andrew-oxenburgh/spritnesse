package org.oxenburgh.spritnesse;

import java.util.List;

import static util.ListUtility.list;

public class SpockTable extends JunitTable {

    boolean spockAvailable = false;

    public SpockTable(String jarName) {
        super(jarName);
        spockAvailable = checkSpockAvailable();
    }

    boolean checkSpockAvailable() {
        String spockSpecName = "spock.lang.Specification";
        try {
            this.getClass().getClassLoader().loadClass(spockSpecName);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public List doTable(List<List<String>> args) throws ClassNotFoundException {
        if(!spockAvailable) {
            return list(list("spock unavailable on classpath"));
        }
        return super.doTable(args);
    }
}

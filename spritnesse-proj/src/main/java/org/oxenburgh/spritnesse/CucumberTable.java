package org.oxenburgh.spritnesse;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            return asList(asList("cucumber unavailable on classpath"));
        }
        return super.doTable(args);
    }
}

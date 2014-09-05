package org.oxenburgh.spritnesse;

import java.util.List;

import static util.ListUtility.list;

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
public class SpockTable extends SpritnesseTable {

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

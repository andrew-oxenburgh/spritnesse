package org.oxenburgh.spritnesse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class JunitTable extends SpritnesseTable {

    static Logger log = LoggerFactory.getLogger(JunitTable.class);

    public JunitTable(String jarName) {
        super(jarName);
    }

    @Override
    List getClassesToBeTested(List<List<String>> args) {
        log.info("entering JunitTable");
        List classNames;
        if (args == null || args.isEmpty()) {
            classNames = new JarTestsFinder().findClassesIn(jarName);
        } else {
            classNames = new JarTestsFinder().findClassesInLike(jarName, args.get(0).get(0));
        }
        return classNames;
    }

}

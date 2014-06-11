package org.oxenburgh.spritnesse.enclosed;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
public class TestWithTwoPassingMethods {

    @Test
    public void testOnePass() throws Exception {
        assertTrue(true);
    }


    @Test
    public void testTwoPass() throws Exception {
        assertTrue(true);
    }

}

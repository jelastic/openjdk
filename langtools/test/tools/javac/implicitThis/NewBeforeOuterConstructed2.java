/*
 * Copyright 2002 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

/**
 * @test
 * @bug 4689058
 * @summary unverifiable code for implicit outer in super constructor call
 *
 * @compile/fail NewBeforeOuterConstructed2.java
 */

public class NewBeforeOuterConstructed2 {
    NewBeforeOuterConstructed2(Object o) {}
    class Middle extends NewBeforeOuterConstructed2 {
        Middle(int i) {
            super(null);
        }
        Middle() {
            // The 'new' below is illegal, as the outer
            // constructor has not been called when the
            // implicit reference to 'this' is evaluated
            // during the new instance expression.
            super(/*Middle.this.*/new Middle(1));
        }
        class Inner {}
        void f() {
            System.out.println("ok");
        }
    }
    public static void main(String[] args) {
        NewBeforeOuterConstructed2 c = new NewBeforeOuterConstructed2(new Object());
        Middle m = c.new Middle();
        m.f();
    }
}

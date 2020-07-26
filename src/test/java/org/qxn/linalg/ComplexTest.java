package org.qxn.linalg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComplexTest {

    @Test
    void complexFromModulusArgument() {
        Complex testNumber = Complex.complexFromModulusArgument(1.0, 1.5 * Math.PI);
        Complex expectedNumber = new Complex(0.0, -1.0);
        assertEquals(testNumber.real, expectedNumber.real, 0.01);
        assertEquals(testNumber.imaginary, expectedNumber.imaginary, 0.01);
    }

    @Test
    void getMagnitude() {
        Complex testNumber = Complex.complexFromModulusArgument(5.3, Math.PI / 4.0);
        assertEquals(5.3, testNumber.getMagnitude(), 0.01);
    }

    @Test
    void getMagnitude2() {
        Complex testNumber = Complex.complexFromModulusArgument(5.3, Math.PI / 4.0);
        assertEquals(Math.pow(5.3, 2), testNumber.getMagnitude2(), 0.01);
    }

    @Test
    void testEquals() {
        Complex testNumber = new Complex(3.0, 2.1);
        Complex testNumber2 = new Complex(3.0, 2.1);
        assertTrue(testNumber.equals(testNumber2));
    }
}
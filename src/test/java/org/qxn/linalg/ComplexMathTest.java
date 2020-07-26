package org.qxn.linalg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComplexMathTest {

    @Test
    void add() {
        Complex num1 = Complex.complexFromModulusArgument(1.0, Math.PI); // -1
        Complex num2 = new Complex(2, -1); // 2 - i
        Complex result = ComplexMath.add(num1, num2);

        // Expect 1 - i
        assertEquals(result.real, 1, 0.01);
        assertEquals(result.imaginary, -1, 0.01);
    }

    @Test
    void subtract() {
        Complex num1 = Complex.complexFromModulusArgument(1.0, Math.PI); // -1
        Complex num2 = new Complex(2, -1); // 2 - i
        Complex result = ComplexMath.subtract(num1, num2);

        // Expect -3 + i
        assertEquals(result.real, -3, 0.01);
        assertEquals(result.imaginary, 1, 0.01);
    }

    @Test
    void multiply() {
        Complex num1 = Complex.complexFromModulusArgument(1.5, 1.5 * Math.PI); // -1.5i
        Complex num2 = new Complex(2, -1); // 2 - i
        Complex result = ComplexMath.multiply(num1, num2);

        // Expect -1.5 - 3i
        assertEquals(result.real, -1.5, 0.01);
        assertEquals(result.imaginary, -3, 0.01);
    }

    @Test
    void conjugate() {
        Complex num = new Complex(3.5, -2.5); // 3.5 - 2.5i

        // Expect 3.5 + 2.5i
        Complex expected = new Complex(3.5, 2.5);
        assertTrue(expected.equals(ComplexMath.conjugate(num)));
    }
}
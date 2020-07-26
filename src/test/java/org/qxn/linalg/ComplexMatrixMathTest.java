package org.qxn.linalg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ComplexMatrixMathTest {

    @Test
    void conjugate() {
        ComplexMatrix S = new ComplexMatrix(2, 2);
        S.data[0][0].real = 1.0;
        S.data[1][1].imaginary = 1.0;

        ComplexMatrix SConjugate = new ComplexMatrix(2, 2);
        SConjugate.data[0][0].real = 1.0;
        SConjugate.data[1][1].imaginary = -1.0;

        assertTrue(ComplexMatrixMath.conjugate(S).equals(SConjugate));
    }

    @Test
    void transpose() {

        // (1 , -i)^T
        ComplexMatrix test = new ComplexMatrix(2, 1);
        test.data[0][0].real = 1.0;
        test.data[1][0].imaginary = -1.0;

        // (1 , -i)
        ComplexMatrix expected = new ComplexMatrix(1, 2);
        expected.data[0][0].real = 1.0;
        expected.data[0][1].imaginary = -1.0;

        assertTrue(ComplexMatrixMath.transpose(test).equals(expected));

    }

    @Test
    void hermitianAdjoint() {

        // (1 , -i)^T
        ComplexMatrix test = new ComplexMatrix(2, 1);
        test.data[0][0].real = 1.0;
        test.data[1][0].imaginary = -1.0;

        // (1 , i)
        ComplexMatrix expected = new ComplexMatrix(1, 2);
        expected.data[0][0].real = 1.0;
        expected.data[0][1].imaginary = 1.0;

        assertTrue(ComplexMatrixMath.hermitianAdjoint(test).equals(expected));
    }

    @Test
    void scale() {
        ComplexMatrix i = ComplexMatrix.identity(2);

        ComplexMatrix expected = new ComplexMatrix(2, 2);
        expected.data[0][0].imaginary = -2;
        expected.data[1][1].imaginary = -2;

        assertTrue(ComplexMatrixMath.scale(new Complex(0, -2), i).equals(expected));
    }

    @Test
    void multiply() {
        ComplexMatrix zero = new ComplexMatrix(2, 1);
        zero.data[0][0].real = 1.0;

        ComplexMatrix X = new ComplexMatrix(2, 2);
        X.data[0][1].real = 1.0;
        X.data[1][0].real = 1.0;

        ComplexMatrix one = new ComplexMatrix(2, 1);
        one.data[1][0].real = 1.0;

        try {assertTrue(ComplexMatrixMath.multiply(X, zero).equals(one));} catch (Exception e) {fail();}
    }

    @Test
    void tensorProduct() {
        ComplexMatrix i2 = ComplexMatrix.identity(2);
        ComplexMatrix i4 = ComplexMatrix.identity(4);

        assertTrue(ComplexMatrixMath.tensorProduct(i2, i2).equals(i4));
    }
}
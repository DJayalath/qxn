package org.qxn.linalg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComplexMatrixTest {

    @Test
    void identity() {
        ComplexMatrix identity4x4 = ComplexMatrix.identity(4);

        ComplexMatrix expected = new ComplexMatrix(4, 4);
        for (int i = 0; i < 4; i++)
            expected.data[i][i].real = 1.0;

        assertTrue(identity4x4.equals(expected));
    }

    @Test
    void testEquals() {
        ComplexMatrix X = new ComplexMatrix(2, 2);
        X.data[0][1].real = 1.0;
        X.data[1][0].real = 1.0;

        ComplexMatrix anotherX = new ComplexMatrix(2, 2);
        anotherX.data[0][1].real = 1.0;
        anotherX.data[1][0].real = 1.0;

        assertTrue(X.equals(anotherX));
    }

    @Test
    void isUnitary() {
        ComplexMatrix S = new ComplexMatrix(2, 2);
        S.data[0][0].real = 1.0;
        S.data[1][1].imaginary = 1.0;

        assertTrue(S.isUnitary());
    }

    @Test
    void isUnitaryWithNonUnitary() {
        ComplexMatrix nonUnitary = new ComplexMatrix(2, 2);
        nonUnitary.data[0][0].real = 1.0;

        assertFalse(nonUnitary.isUnitary());
    }
}
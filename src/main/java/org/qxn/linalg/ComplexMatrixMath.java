package org.qxn.linalg;

public class ComplexMatrixMath {

    public static ComplexMatrix conjugate(ComplexMatrix a) {
        ComplexMatrix b = new ComplexMatrix(a.rows, a.columns);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.columns; j++) {
                b.data[i][j] = ComplexMath.conjugate(a.data[i][j]);
            }
        }
        return b;
    }

    public static ComplexMatrix transpose(ComplexMatrix a) {
        ComplexMatrix b = new ComplexMatrix(a.columns, a.rows);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.columns; j++) {
                b.data[j][i] = a.data[i][j];
            }
        }
        return b;
    }

    public static ComplexMatrix hermitianAdjoint(ComplexMatrix a) {
        return transpose(conjugate(a));
    }

    public static ComplexMatrix scale(Complex scalar, ComplexMatrix a) {

        ComplexMatrix b = new ComplexMatrix(a.rows, a.columns);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.columns; j++) {
                b.data[i][j] = ComplexMath.multiply(scalar, a.data[i][j]);
            }
        }

        return b;
    }

    public static ComplexMatrix multiply(ComplexMatrix a, ComplexMatrix b) {

        assert a.columns == b.rows : "Matrices must be of the form m x n and n x p to be multiplied";

        ComplexMatrix c = new ComplexMatrix(a.rows, b.columns);

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < b.columns; j++) {
                for (int k = 0; k < a.columns; k++) {
                    c.data[i][j] = ComplexMath.add(c.data[i][j], ComplexMath.multiply(a.data[i][k], b.data[k][j]));
                }
            }
        }

        return c;

    }

    public static ComplexMatrix tensorProduct(ComplexMatrix a, ComplexMatrix b) {

        ComplexMatrix c = new ComplexMatrix(a.rows * b.rows, a.columns * b.columns);

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.columns; j++) {
                Complex alpha = a.data[i][j];

                for (int rowB = 0; rowB < b.rows; rowB++) {
                    for (int colB = 0; colB < b.columns; colB++) {
                        Complex val = ComplexMath.multiply(alpha, b.data[rowB][colB]);
                        c.data[i * b.rows + rowB][j * b.columns + colB] = val;
                    }
                }
            }
        }

        return c;

    }

}

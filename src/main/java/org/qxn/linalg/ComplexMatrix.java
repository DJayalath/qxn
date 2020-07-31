package org.qxn.linalg;

import java.io.Serializable;

public class ComplexMatrix implements Serializable {

    public Complex[][] data;

    public int rows;
    public int columns;

    public ComplexMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        data = new Complex[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = new Complex();
            }
        }
    }

    public static ComplexMatrix identity(int rows) {
        ComplexMatrix identity = new ComplexMatrix(rows, rows);
        for (int i = 0; i < rows; i++) {
            identity.data[i][i].real = 1;
        }
        return identity;
    }

    public ComplexMatrix(int rows, int columns, Complex[][] data) {
        this.rows = rows;
        this.columns = columns;
        this.data = data;
    }

    public boolean equals(ComplexMatrix b) {
        if (this.rows == b.rows && this.columns == b.columns) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (!this.data[i][j].equals(b.data[i][j]))
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isUnitary() {

        ComplexMatrix hermitianAdjoint = ComplexMatrixMath.hermitianAdjoint(this);

        ComplexMatrix UDaggerU = ComplexMatrixMath.multiply(hermitianAdjoint, this);
        ComplexMatrix UUDagger = ComplexMatrixMath.multiply(this, hermitianAdjoint);
        return UDaggerU.equals(UUDagger) && UDaggerU.equals(ComplexMatrix.identity(rows));

    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j].print();
                System.out.print("    ");
            }
            System.out.println();
        }
    }

}

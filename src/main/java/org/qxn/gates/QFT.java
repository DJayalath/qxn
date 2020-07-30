package org.qxn.gates;

import org.qxn.linalg.Complex;
import org.qxn.linalg.ComplexMatrix;
import org.qxn.linalg.ComplexMatrixMath;

/**
 * Quantum Fourier Transform
 */
public class QFT extends Gate {

    /**
     * Constructs a quantum fourier transform gate
     * @param inputWire The index of the first wire input to the gate
     * @param numInputs The number of inputs into the gate
     */
    public QFT(int inputWire, int numInputs) {

        this.startWire = inputWire;
        this.numInputs = numInputs;

        matrix = new ComplexMatrix(1 << numInputs, 1 << numInputs);

        int N = 1 << numInputs;
        for (int i = 0; i < 1 << numInputs; i++) {
            for (int j = 0; j < 1 << numInputs; j++) {
                if (i == 0 || j == 0) {
                    matrix.data[i][j].real = 1.0;
                } else {
                    matrix.data[i][j] = Complex.complexFromModulusArgument(1.0, (j * i) * ((2.0 * Math.PI) / N));
                }
            }
        }

        matrix = ComplexMatrixMath.scale(new Complex(1.0 / Math.sqrt(N), 0.0), matrix);

    }

}

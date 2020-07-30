package org.qxn.gates;

import org.qxn.linalg.ComplexMatrixMath;

/**
 * Hermitian adjoin of quantum fourier transform
 */
public class QFTHA extends QFT {

    /**
     * Constructs hermitian adjoint of quantum fourier transform gate
     *
     * @param inputWire The index of the first wire input to the gate
     * @param numInputs The number of inputs into the gate
     */
    public QFTHA(int inputWire, int numInputs) {
        super(inputWire, numInputs);
        matrix = ComplexMatrixMath.hermitianAdjoint(matrix);
    }
}

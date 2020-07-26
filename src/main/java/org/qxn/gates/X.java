package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

/**
 * A Pauli-X gate
 */
public class X extends Gate {

    public static final ComplexMatrix X = new ComplexMatrix(2, 2);
    static {
        X.data[0][1].real = 1;
        X.data[1][0].real = 1;
    }

    /**
     * Constructs an X gate
     * @param inputWire The index of the wire input to the gate
     */
    public X(int inputWire) {

        this.startWire = inputWire;
        this.matrix = X;
        numInputs = 1;

    }
}

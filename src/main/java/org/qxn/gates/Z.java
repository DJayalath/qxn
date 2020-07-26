package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

/**
 * A Pauli-Z gate
 */
public class Z extends Gate {

    public static final ComplexMatrix Z = new ComplexMatrix(2, 2);
    static {
        Z.data[0][0].real = 1;
        Z.data[1][1].real = -1;
    }

    /**
     * Constructs a Z gate
     * @param inputWire The index of the wire input to the gate
     */
    public Z(int inputWire) {

        this.startWire = inputWire;
        this.matrix = Z;
        numInputs = 1;

    }
}

package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

/**
 * A Pauli-Y gate
 */
public class Y extends Gate{

    public static final ComplexMatrix Y = new ComplexMatrix(2, 2);
    static {
        Y.data[0][1].imaginary = -1;
        Y.data[1][0].imaginary = 1;
    }

    /**
     * Constructs a Y gate
     * @param inputWire The index of the wire input to the gate
     */
    public Y(int inputWire) {

        this.startWire = inputWire;
        this.matrix = Y;
        numInputs = 1;

    }

}

package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

/**
 * A primitive S gate
 */
public class S extends Gate {

    public static ComplexMatrix S = new ComplexMatrix(2, 2);
    static {
        S.data[0][0].real = 1;
        S.data[1][1].imaginary = 1;
    }

    /**
     * Constructs an S gate
     * @param inputWire The index of the wire input to the gate
     */
    public S(int inputWire) {

        this.startWire = inputWire;
        this.matrix = S;
        numInputs = 1;

    }
}

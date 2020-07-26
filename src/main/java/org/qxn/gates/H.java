package org.qxn.gates;

import org.qxn.linalg.Complex;
import org.qxn.linalg.ComplexMatrix;
import org.qxn.linalg.ComplexMatrixMath;

/**
 * A Hadamard gate
 */
public class H extends Gate {

    public static ComplexMatrix H = new ComplexMatrix(2, 2);
    static {
        H.data[0][0].real = 1;
        H.data[0][1].real = 1;
        H.data[1][0].real = 1;
        H.data[1][1].real = -1;
        H = ComplexMatrixMath.scale(new Complex(1.0 / Math.sqrt(2.0), 0), H);
    }

    /**
     * Constructs a single-qubit hadamard gate
     * @param inputWire The index of the wire input to the gate
     */
    public H(int inputWire) {

        this.startWire = inputWire;
        this.matrix = H;
        numInputs = 1;

    }

}

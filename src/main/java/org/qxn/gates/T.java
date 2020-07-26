package org.qxn.gates;

import org.qxn.linalg.Complex;
import org.qxn.linalg.ComplexMatrix;

/**
 * A primitive T gate
 */
public class T extends Gate {

    public static final ComplexMatrix T = new ComplexMatrix(2, 2);
    static {
        T.data[0][0].real = 1;
        T.data[0][1].real = 0;
        T.data[1][0].real = 0;
        T.data[1][1] = Complex.complexFromModulusArgument(1.0, Math.PI / 4.0);
    }

    /**
     * Constructs a T gate
     * @param inputWire The index of the wire input to the gate
     */
    public T(int inputWire) {

        this.startWire = inputWire;
        this.matrix = T;
        numInputs = 1;

    }
}

package org.qxn.gates;

import org.qxn.linalg.Complex;
import org.qxn.linalg.ComplexMatrix;

/**
 * A Toffoli (CCNOT) gate
 */
public class CCNOT extends Gate {

    public static final ComplexMatrix CCNOT = ComplexMatrix.identity(1 << 3);
    static {
        CCNOT.data[6][6] = new Complex(0, 0);
        CCNOT.data[6][7].real = 1;
        CCNOT.data[7][7] = new Complex(0, 0);
        CCNOT.data[7][6].real = 1;
    }

    /**
     * Constructs a Toffoli gate
     * @param controlWireA First control wire
     * @param controlWireB Second control wire
     * @param targetWire NOT operation acts on quantum state of this wire when both control wires are set
     */
    public CCNOT(int controlWireA, int controlWireB, int targetWire) {

        assert controlWireA == controlWireB - 1 : "Control wires must be adjacent and in sequence";
        assert targetWire == controlWireB + 1 : "Target wire must be adjacent and succeed last control wire";

        this.numInputs = 3;
        this.startWire = controlWireA;
        this.matrix = CCNOT;

    }

}

package org.qxn.gates;

import org.qxn.linalg.Complex;
import org.qxn.linalg.ComplexMatrix;

/**
 * A Fredkin (CSWAP) gate
 */
public class CSWAP extends Gate {

    public static final ComplexMatrix CSWAP = ComplexMatrix.identity(1 << 3);
    static {
        CSWAP.data[5][5] = new Complex(0, 0);
        CSWAP.data[5][6].real = 1;
        CSWAP.data[6][6] = new Complex(0, 0);
        CSWAP.data[6][5].real = 1;
    }

    /**
     * Constructs a Fredkin gate
     * @param swapGate The SWAP gate used to swap the quantum states of two wires
     * @param controlWire The control wire used to activate the swap gate
     */
    public CSWAP(SWAP swapGate, int controlWire) {

        assert controlWire == swapGate.getStartWire() - 1 : "Control wire must be adjacent to and proceed swap wires";

        this.numInputs = 3;
        this.startWire = controlWire;
        this.matrix = CSWAP;

    }
}

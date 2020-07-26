package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

/**
 * A controlled NOT gate (a controlled Pauli-X gate)
 */
public class CNOT extends Gate {

    public static final ComplexMatrix CNOT = new ComplexMatrix(4, 4);
    static {
        CNOT.data[0][0].real = 1;
        CNOT.data[1][1].real = 1;
        CNOT.data[2][3].real = 1;
        CNOT.data[3][2].real = 1;
    }

    public static final ComplexMatrix CNOT_INVERTED = new ComplexMatrix(4, 4);
    static {
        CNOT_INVERTED.data[0][0].real = 1;
        CNOT_INVERTED.data[1][3].real = 1;
        CNOT_INVERTED.data[2][2].real = 1;
        CNOT_INVERTED.data[3][1].real = 1;
    }

    /**
     * Creates CNOT gate
     * @param controlWire When qubit is 1, flips target qubit
     * @param targetWire Flipped when control qubit is 1
     */
    public CNOT(int controlWire, int targetWire) {

        assert Math.abs(controlWire - targetWire) == 1
                : "Control and target wires must be adjacent. Consider using SWAP gates to achieve this first.";

        numInputs = 2;

        if (controlWire < targetWire) {
            this.startWire = controlWire;
            this.matrix = CNOT;

        } else {
            this.startWire = targetWire;
            this.matrix = CNOT_INVERTED;
        }

    }

}

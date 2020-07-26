package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

/**
 * Controlled version of single qubit gate
 */
public class C extends Gate {

    /**
     * Constructs a controlled gate from a given gate. Control wire must be the wire
     * preceding the input wire to the input gate.
     * @param inputGate A gate which will be controlled by the control wire
     * @param controlWire Control wire to control gate action
     */
    public C(Gate inputGate, int controlWire) {

        assert controlWire == inputGate.getStartWire() - 1
                : "Control wire must be wire preceding wire input to primitive gate";

        this.numInputs = inputGate.getNumInputs() + 1;

        matrix = ComplexMatrix.identity(1 << this.numInputs);

        for (int i = 0; i < inputGate.matrix.rows; i++) {
            for (int j = 0; j < inputGate.matrix.columns; j++) {
                int k = (1 << (this.numInputs - 1)) + i;
                int l = (1 << (this.numInputs - 1)) + j;
                matrix.data[k][l] = inputGate.getMatrix().data[i][j];
            }
        }

        this.startWire = controlWire;

    }

}

package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

/**
 * A custom gate defined by a given unitary matrix
 */
public class CustomGate extends Gate {

    /**
     * Constructs a custom gate
     * @param startWire The index of the wire from which the gate starts
     * @param numInputs The number of inputs that the gate takes (including startWire)
     * @param matrix Matrix defining the operation of the gate
     */
    public CustomGate(int startWire, int numInputs, ComplexMatrix matrix) {

        assert numInputs >= 1 : "Gate must take at least one input";
        assert matrix.rows == matrix.columns : "Gate matrix must be square";
        assert matrix.rows == 1 << numInputs : "Gate matrix must have at least 2^numInputs rows";
        assert matrix.isUnitary() : "Gate matrix must be unitary";

        this.startWire = startWire;
        this.numInputs = numInputs;
        this.matrix = matrix;
    }

}

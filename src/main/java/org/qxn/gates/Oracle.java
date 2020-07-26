package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

/**
 * A functionally-defined oracle
 */
public class Oracle extends Gate {

    public interface BitStringMap {
        boolean test(int x);
    }

    /**
     * Constructs a functionally-defined oracle. Where an oracle is defined by a map U : (x, y) to (x, y XOR f(x)).
     * y is implied to be the qubit on the wire after the final qubit of x.
     * @param startWireX The index of the wire from which the input x starts
     * @param numInputsX The number of qubits of x (including startWire)
     * @param wireY The index of the wire input y
     * @param bitStringMap A function f(x) mapping x to 0 (false) or 1 (true)
     */
    public Oracle(int startWireX, int numInputsX, int wireY, BitStringMap bitStringMap) {

        assert  wireY == startWireX + numInputsX
                : "The wire for register y must be adjacent to and succeed wires for x";

        this.startWire = startWireX;
        this.numInputs = numInputsX + 1;

        matrix = new ComplexMatrix(1 << this.getNumInputs(), 1 << this.getNumInputs());

        // Loop over all possible outputs and construct linear map
        for (int i = 0; i < 1 << this.getNumInputs(); i++) {
            if (bitStringMap.test(i >> 1)) {
                // Map input to toggled output
                getMatrix().data[i ^ 0b1][i].real = 1;
            } else {
                // Map input to itself
                getMatrix().data[i][i].real = 1;
            }
        }

        // Normalise matrix rows
        for (int i = 0; i < 1 << this.getNumInputs(); i++) {
            int count = 0;
            for (int j = 0; j < 1 << this.getNumInputs(); j++) {
                if (getMatrix().data[i][j].real == 1)
                    count++;
            }

            double scalar = Math.sqrt(1.0 / count);

            for (int j = 0; j < 1 << this.getNumInputs(); j++) {
                if (getMatrix().data[i][j].real == 1)
                    getMatrix().data[i][j].real = scalar;
            }
        }

        if (!getMatrix().isUnitary())
            System.out.println("WARNING: Defined oracle does not evaluate to a unitary matrix.");

    }

}

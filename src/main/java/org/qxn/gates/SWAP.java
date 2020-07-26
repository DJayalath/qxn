package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

/**
 * A SWAP gate (swaps the states of two wires)
 */
public class SWAP extends Gate {

    public static final ComplexMatrix SWAP = new ComplexMatrix(4, 4);
    static {
        SWAP.data[0][0].real = 1;
        SWAP.data[1][2].real = 1;
        SWAP.data[2][1].real = 1;
        SWAP.data[3][3].real = 1;
    }

    /**
     * Constructs a SWAP gate that swaps the states of the given wires
     * @param wireA State of this wire is swapped with wireB
     * @param wireB State of this wire is swapped with wireA
     */
    public SWAP(int wireA, int wireB) {

        assert Math.abs(wireA - wireB) == 1
                : "Wires must be adjacent. Consider using SWAP gates to achieve this first.";

        this.matrix = SWAP;
        this.numInputs = 2;

        this.startWire = Math.min(wireA, wireB);

    }

}

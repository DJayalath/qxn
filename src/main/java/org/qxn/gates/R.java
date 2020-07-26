package org.qxn.gates;

import org.qxn.linalg.Complex;
import org.qxn.linalg.ComplexMatrix;

/**
 * A phase shift gate R. Modifies the quantum state equivalent to a rotation about the Z-axis of the Bloch sphere
 * by the specified phase shift.
 */
public class R extends Gate {


    /**
     * Constructs phase shift gate R
     * @param inputWire The index of the wire input to the gate
     * @param phi Phase shift
     */
    public R(int inputWire, double phi) {

        matrix = new ComplexMatrix(2, 2);
        matrix.data[0][0].real = 1;
        matrix.data[1][1] = Complex.complexFromModulusArgument(1.0, phi);

        this.numInputs = 1;
        this.startWire = inputWire;
    }

}

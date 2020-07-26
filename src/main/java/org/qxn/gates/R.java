package org.qxn.gates;

import org.qxn.linalg.Complex;
import org.qxn.linalg.ComplexMatrix;

/**
 * A phase shift gate R. Modifies the quantum state equivalent to a rotation about the Z-axis of the Bloch sphere
 * by the specified phase shift.
 */
public class R extends Gate {

    public final ComplexMatrix R = new ComplexMatrix(2, 2);

    /**
     * Constructs phase shift gate R
     * @param inputWire The index of the wire input to the gate
     * @param phi Phase shift
     */
    public R(int inputWire, double phi) {
        R.data[0][0].real = 1;
        R.data[1][1] = Complex.complexFromModulusArgument(1.0, phi);

        this.numInputs = 1;
        this.startWire = inputWire;
        this.matrix = R;
    }

}

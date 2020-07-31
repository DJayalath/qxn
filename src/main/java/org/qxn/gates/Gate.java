package org.qxn.gates;

import org.qxn.linalg.ComplexMatrix;

import java.io.Serializable;

public abstract class Gate implements Serializable {

    protected int numInputs;
    protected int startWire;
    protected ComplexMatrix matrix;

    public ComplexMatrix getMatrix() {
        return matrix;
    }

    public int getNumInputs() {
        return numInputs;
    }

    public int getStartWire() {
        return startWire;
    }
}

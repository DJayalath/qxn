package org.qxn;

import org.qxn.gates.Gate;
import org.qxn.linalg.Complex;
import org.qxn.linalg.ComplexMatrix;
import org.qxn.linalg.ComplexMatrixMath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Constructs a quantum circuit using the Quantum Abstract Machine (QAM) model
 */
public class QuantumMachine {

    private final int numWires;
    private final List<Gate> gates;
    private ComplexMatrix qubits;
    private int randomSeed = Integer.MIN_VALUE;

    /**
     * Creates a quantum machine and initialises qubits/wires to 0 computational basis state
     * @param numWires Number of qubits/wires in quantum machine
     */
    public QuantumMachine(int numWires) {

        this.numWires = numWires;

        // Create vector of size 2^numWires where first element (|0...0>) is 1
        qubits = new ComplexMatrix(1 << numWires, 1);
        qubits.data[0][0].real = 1;

        gates = new ArrayList<>();
    }

    /**
     * Sets the seed used by the measure method. Useful for deterministic testing of quantum algorithms.
     * If a seed is not supplied, a random one will be used on each call of the measure method.
     * @param randomSeed Seed used for state collapse on measurement
     */
    public void setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
    }

    /**
     * Get the qubits in the machine
     * @return Vector of qubit states in computational basis
     */
    public ComplexMatrix getQubits() {
        return qubits;
    }

    /**
     * Adds a gate to the machine's gate set
     * @param gate Quantum gate to be added
     */
    public void addGate(Gate gate) {

        assert gate.getStartWire() < numWires : "Gate input wires must exist in machine";
        assert gate.getNumInputs() <= numWires : "Gate must not be larger than machine";

        gates.add(gate);
    }

    /**
     * Executes the quantum circuit
     */
    public void execute() {

        // Go through gates in order
        for (Gate g : gates) {

                // Create identity for prior qubits
                ComplexMatrix priorIdentity = ComplexMatrix.identity(1 << g.getStartWire());

                // Create identity for later qubits
                ComplexMatrix afterIdentity = ComplexMatrix.identity(1 << (numWires - g.getStartWire() - g.getNumInputs()));

                // Intermediate tensor product calculation for this "step"

                ComplexMatrix intermediate;

                if (g.getStartWire() == 0 && numWires - g.getStartWire() - g.getNumInputs() == 0) {
                    intermediate = g.getMatrix();
                } else if (g.getStartWire() == 0) {
                    intermediate = ComplexMatrixMath.tensorProduct(g.getMatrix(), afterIdentity);
                } else if (numWires - g.getStartWire() - g.getNumInputs() == 0) {
                    intermediate = ComplexMatrixMath.tensorProduct(priorIdentity, g.getMatrix());
                } else {
                    intermediate = ComplexMatrixMath.tensorProduct(
                            ComplexMatrixMath.tensorProduct(priorIdentity, g.getMatrix()), afterIdentity);
                }

                // Apply solution to state of qubits
                qubits = ComplexMatrixMath.multiply(intermediate, qubits);
        }

        // Remove queued gate operations
        gates.clear();
    }

    /**
     * Shows the probability of each possible qubit state with the current state. Use after execute() method to show
     * result of quantum circuit execution.
     */
    public void printState() {
        for (int i = 0; i < 1 << numWires; i++) {
            String binary = String.format("%" + numWires + "s",
                    Integer.toBinaryString(i)).replace(' ', '0');
            System.out.println(binary + " with probability " + qubits.data[i][0].getMagnitude2());
        }
    }

    /**
     * Measures the state of a qubit and causes state to collapse
     * @param qubit The qubit to measure
     * @return The measured value of the qubit
     */
    public int measure(int qubit) {

        double setProb = 0;

        // Sum probabilities that this qubit will be set
        for (int i = 0; i < 1 << numWires; i++) {

            int n = 1 << (numWires - qubit - 1);

            boolean isSet = (i & n) != 0;
            if (isSet) {
                setProb += qubits.data[i][0].getMagnitude2();
            }
        }

        // Use random value to collapse state with probability
        Random random;
        if (randomSeed > Integer.MIN_VALUE) {
            random = new Random(randomSeed);
        } else {
            random = new Random();
        }

        int collapsedValue;
        if (random.nextFloat() < setProb) {
            collapsedValue = 1;
        } else {
            collapsedValue = 0;
        }

        // Redistribute probabilities by setting values inconsistent with collapsed value to zero
        double sumMagnitude2 = 0;
        for (int i = 0; i < 1 << numWires; i++) {
            int n = 1 << (numWires - qubit - 1);
            boolean isSet = (i & n) != 0;
            if (collapsedValue == 1) {
                if (!isSet) {
                    qubits.data[i][0].real = 0;
                    qubits.data[i][0].imaginary = 0;
                } else {
                    sumMagnitude2 += qubits.data[i][0].getMagnitude2();
                }
            } else {
                if (isSet) {
                    qubits.data[i][0].real = 0;
                    qubits.data[i][0].imaginary = 0;
                } else {
                    sumMagnitude2 += qubits.data[i][0].getMagnitude2();
                }
            }
        }

        // Normalize probability to get post-measurement state
        qubits = ComplexMatrixMath.scale(new Complex(1.0 / Math.sqrt(sumMagnitude2), 0), qubits);

        return collapsedValue;
    }

}
import org.junit.jupiter.api.Test;
import org.qxn.QuantumMachine;
import org.qxn.gates.H;
import org.qxn.gates.Oracle;
import org.qxn.linalg.ComplexMatrix;

import static org.junit.jupiter.api.Assertions.*;

public class OracleIntegrationTest {

    @Test
    void testOracleWith1Qubits() {

        QuantumMachine quantumMachine = new QuantumMachine(2);
        quantumMachine.addGate(new H(0));
        quantumMachine.addGate(new H(1));
        quantumMachine.addGate(new Oracle(0, 1, 1, (bitString) -> bitString == 0));
        quantumMachine.execute();

        ComplexMatrix result = quantumMachine.getQubits();

        assertEquals(0.25, result.data[0][0].getMagnitude2(), 0.01);
        assertEquals(0.25, result.data[1][0].getMagnitude2(), 0.01);
        assertEquals(0.25, result.data[2][0].getMagnitude2(), 0.01);
        assertEquals(0.25, result.data[3][0].getMagnitude2(), 0.01);
    }

    @Test
    void testOracleWith2Qubits() {
        QuantumMachine quantumMachine = new QuantumMachine(3);
        quantumMachine.addGate(new H(0));
        quantumMachine.addGate(new Oracle(0, 2, 2, (bitString) -> bitString == 0));
        quantumMachine.execute();

        ComplexMatrix result = quantumMachine.getQubits();

        for (int i = 0; i < 8; i++) {
            if (i == 1)
                assertEquals(0.5, result.data[i][0].getMagnitude2(), 0.01);
            else if (i == 4)
                assertEquals(0.5, result.data[i][0].getMagnitude2(), 0.01);
            else
                assertEquals(0, result.data[i][0].getMagnitude2(), 0.01);
        }
    }

    @Test
    void testOracleWithNonEdgeWire() {
        QuantumMachine quantumMachine = new QuantumMachine(3);
        quantumMachine.addGate(new H(0));
        quantumMachine.addGate(new Oracle(0, 1, 1, (bitString) -> bitString == 0));
        quantumMachine.execute();

        ComplexMatrix result = quantumMachine.getQubits();

        for (int i = 0; i < 8; i++) {
            if (i == 2)
                assertEquals(0.5, result.data[i][0].getMagnitude2(), 0.01);
            else if (i == 4)
                assertEquals(0.5, result.data[i][0].getMagnitude2(), 0.01);
            else
                assertEquals(0, result.data[i][0].getMagnitude2(), 0.01);
        }

    }

}

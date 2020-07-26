import org.junit.jupiter.api.Test;
import org.qxn.QuantumMachine;
import org.qxn.gates.CNOT;
import org.qxn.gates.H;
import org.qxn.gates.X;
import org.qxn.linalg.ComplexMatrix;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuantumMachineTest {

    @Test
    void testMeasure() {
        int randomSeed = 278;

        QuantumMachine quantumMachine = new QuantumMachine(1);
        quantumMachine.setRandomSeed(randomSeed); // Inject seed
        quantumMachine.addGate(new H(0));
        quantumMachine.execute();

        // Expected value
        Random random = new Random(randomSeed);
        int expected = (random.nextFloat() < 0.5) ? 1 : 0;

        assertEquals(expected, quantumMachine.measure(0));
    }

    @Test
    void testExecute() {
        QuantumMachine quantumMachine = new QuantumMachine(2);
        quantumMachine.addGate(new X(1));
        quantumMachine.addGate(new CNOT(1, 0));
        quantumMachine.execute();

        ComplexMatrix result = quantumMachine.getQubits();
        ComplexMatrix expected = new ComplexMatrix(4, 1);
        expected.data[3][0].real = 1.0;

        assertTrue(expected.equals(result));
    }

}
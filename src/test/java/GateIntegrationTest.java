import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.qxn.QuantumMachine;
import org.qxn.gates.*;
import org.qxn.linalg.Complex;
import org.qxn.linalg.ComplexMatrix;
import org.qxn.linalg.ComplexMatrixMath;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GateIntegrationTest {

    QuantumMachine quantumMachine;

    @BeforeEach
    void setUp() {
        quantumMachine = new QuantumMachine(2);
    }

    @Test
    void testCNOT() {

        quantumMachine.addGate(new X(0));

        quantumMachine.addGate(new CNOT(0, 1));

        quantumMachine.execute();

        assertEquals(1, quantumMachine.measure(1));
    }

    @Test
    void testCNOTWithInvertedGate() {
        quantumMachine.addGate(new X(1));

        quantumMachine.addGate(new CNOT(1, 0));

        quantumMachine.execute();

        assertEquals(1, quantumMachine.measure(0));
    }

    @Test
    void testH() {
        int randomSeed = 31419;
        quantumMachine.setRandomSeed(randomSeed);
        quantumMachine.addGate(new H(0));
        quantumMachine.execute();

        Random random = new Random(randomSeed);
        int expected = (random.nextFloat() < 0.5) ? 1 : 0;

        assertEquals(expected, quantumMachine.measure(0));
    }

    @Test
    void testS() {
        // 90-degree rotation about Z-axis (no change in probability, just phase)
        // Maps |1> to i|1>
        quantumMachine.addGate(new X(0)); // Start in |10> state
        quantumMachine.addGate(new S(0));
        quantumMachine.execute();

        ComplexMatrix result = quantumMachine.getQubits();
        ComplexMatrix expected = new ComplexMatrix(4, 1);
        expected.data[2][0].imaginary = 1.0;

        assertTrue(result.equals(expected));
    }

    @Test
    void testT() {
        // 45-degree rotation about Z-axis (only phase change no probability change)
        // Maps |1> to e^(i * pi/4)|1>
        quantumMachine.addGate(new X(0)); // Start in |10> state
        quantumMachine.addGate(new T(0));
        quantumMachine.execute();

        ComplexMatrix result = quantumMachine.getQubits();
        ComplexMatrix expected = new ComplexMatrix(4, 1);
        expected.data[2][0] = Complex.complexFromModulusArgument(1.0, Math.PI / 4.0);

        assertTrue(result.equals(expected));
    }

    @Test
    void testX() {
        quantumMachine.addGate(new X(0));
        quantumMachine.execute();

        assertEquals(1, quantumMachine.measure(0));
    }

    @Test
    void testYWith0() {
//        quantumMachine.addGate(new X(1)); // Start in |01> state
        quantumMachine.addGate(new Y(1)); // |00> --> |0i1>
        quantumMachine.execute();
        ComplexMatrix result = quantumMachine.getQubits();

        ComplexMatrix expected = new ComplexMatrix(4, 1);
        expected.data[1][0].imaginary = 1;

        assertTrue(result.equals(expected));
    }

    @Test
    void testYWith1() {
        quantumMachine.addGate(new X(1)); // Start in |01> state
        quantumMachine.addGate(new Y(1)); // |01> --> |0-i0>
        quantumMachine.execute();
        ComplexMatrix result = quantumMachine.getQubits();

        ComplexMatrix expected = new ComplexMatrix(4, 1);
        expected.data[0][0].imaginary = -1;

        assertTrue(result.equals(expected));
    }

    @Test
    void testZ() {
        quantumMachine.addGate(new X(1)); // Start in |01> state
        quantumMachine.addGate(new Z(1)); // |01> --> |0-1>
        quantumMachine.execute();
        ComplexMatrix result = quantumMachine.getQubits();

        ComplexMatrix expected = new ComplexMatrix(4, 1);
        expected.data[1][0].real = -1;

        assertTrue(result.equals(expected));
    }

    @Test
    void testCustomGate() {
        ComplexMatrix customGateMatrix = new ComplexMatrix(2, 2);
        customGateMatrix.data[0][1].real = 1;
        customGateMatrix.data[1][0].real = 1;
        quantumMachine.addGate(new X(0)); // Start in |10> state
        quantumMachine.addGate(new CustomGate(1, 1, customGateMatrix)); // |10> --> |11>
        quantumMachine.execute();
        ComplexMatrix result = quantumMachine.getQubits();

        ComplexMatrix expected = new ComplexMatrix(4, 1);
        expected.data[3][0].real = 1;

        assertTrue(result.equals(expected));
    }

    @Test
    void testCustomGateWithNonUnitary() {
        ComplexMatrix customGateMatrix = new ComplexMatrix(2, 2);
        customGateMatrix.data[0][0].real = 1;

        assertThrows(AssertionError.class,
                () -> quantumMachine.addGate(new CustomGate(1, 1, customGateMatrix)));
    }

    @Test
    void testSWAP() {
        // Start |01>
        quantumMachine.addGate(new X(1));
        // Swap --> |10>
        quantumMachine.addGate(new SWAP(1, 0));
        quantumMachine.execute();

        ComplexMatrix result = quantumMachine.getQubits();

        ComplexMatrix expected = new ComplexMatrix(4, 1);
        expected.data[2][0].real = 1;

        assertTrue(result.equals(expected));
    }

    @Test
    void testR() {
        quantumMachine.addGate(new X(0)); // Start in |10> state
        quantumMachine.addGate(new R(0, Math.PI / 2.0)); // Phase shift gate equivalent to S
        quantumMachine.execute();

        ComplexMatrix result = quantumMachine.getQubits();

        assertTrue(result.data[0][0].equals(new Complex(0, 0)));
        assertTrue(result.data[1][0].equals(new Complex(0, 0)));
        assertEquals(0, result.data[2][0].real, 0.01);
        assertEquals(1, result.data[2][0].imaginary, 0.01);
        assertTrue(result.data[3][0].equals(new Complex(0, 0)));
    }

    @Test
    void testC() {
        quantumMachine.addGate(new X(0));

        quantumMachine.addGate(new C(new X(1), 0)); // Create a CNOT gate using CX

        quantumMachine.execute();

        assertEquals(1, quantumMachine.measure(1));
    }

    @Test
    void testCCNOT() {
        quantumMachine = new QuantumMachine(3);
        quantumMachine.addGate(new X(0));
        quantumMachine.addGate(new X(1));
        quantumMachine.addGate(new CCNOT(0, 1, 2));
        quantumMachine.execute();
        assertEquals(quantumMachine.measure(2), 1);

        quantumMachine = new QuantumMachine(3);
        quantumMachine.addGate(new X(1));
        quantumMachine.addGate(new CCNOT(0, 1, 2));
        quantumMachine.execute();
        assertEquals(quantumMachine.measure(2), 0);

        quantumMachine = new QuantumMachine(3);
        quantumMachine.addGate(new X(0));
        quantumMachine.addGate(new CCNOT(0, 1, 2));
        quantumMachine.execute();
        assertEquals(quantumMachine.measure(2), 0);

        quantumMachine = new QuantumMachine(3);
        quantumMachine.addGate(new CCNOT(0, 1, 2));
        quantumMachine.execute();
        assertEquals(quantumMachine.measure(2), 0);
    }

    @Test
    void testCSWAP() {
        quantumMachine = new QuantumMachine(3);

        // Start |101>
        quantumMachine.addGate(new X(0));
        quantumMachine.addGate(new X(2));
        // Swap --> |110>
        quantumMachine.addGate(new CSWAP(new SWAP(1, 2), 0));
        quantumMachine.execute();

        ComplexMatrix result = quantumMachine.getQubits();

        ComplexMatrix expected = new ComplexMatrix(1 << 3, 1);
        expected.data[6][0].real = 1;

        assertTrue(result.equals(expected));

        quantumMachine = new QuantumMachine(3);

        // Start |001>
        quantumMachine.addGate(new X(2));
        // NO Swap --> |001>
        quantumMachine.addGate(new CSWAP(new SWAP(1, 2), 0));
        quantumMachine.execute();

        result = quantumMachine.getQubits();

        expected = new ComplexMatrix(1 << 3, 1);
        expected.data[1][0].real = 1;

        assertTrue(result.equals(expected));
    }

    @Test
    void testQFT() {
        QFT qft = new QFT(0, 2);
        ComplexMatrix result = qft.getMatrix();

        ComplexMatrix expected = new ComplexMatrix(1 << 2, 1 << 2);

        expected.data[0][0].real = 1;
        expected.data[0][1].real = 1;
        expected.data[0][2].real = 1;
        expected.data[0][3].real = 1;

        expected.data[1][0].real = 1;
        expected.data[1][1].imaginary = 1;
        expected.data[1][2].real = -1;
        expected.data[1][3].imaginary = -1;

        expected.data[2][0].real = 1;
        expected.data[2][1].real = -1;
        expected.data[2][2].real = 1;
        expected.data[2][3].real = -1;

        expected.data[3][0].real = 1;
        expected.data[3][1].imaginary = -1;
        expected.data[3][2].real = -1;
        expected.data[3][3].imaginary = 1;

        expected = ComplexMatrixMath.scale(new Complex(0.5, 0.0), expected);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected.data[i][j].real, result.data[i][j].real, 0.01);
                assertEquals(expected.data[i][j].imaginary, result.data[i][j].imaginary, 0.01);
            }
        }

    }

    @Test
    void testQFTHA() {
        QFTHA qftha = new QFTHA(0, 2);
        ComplexMatrix result = qftha.getMatrix();

        ComplexMatrix expected = new ComplexMatrix(1 << 2, 1 << 2);

        expected.data[0][0].real = 1;
        expected.data[0][1].real = 1;
        expected.data[0][2].real = 1;
        expected.data[0][3].real = 1;

        expected.data[1][0].real = 1;
        expected.data[1][1].imaginary = 1;
        expected.data[1][2].real = -1;
        expected.data[1][3].imaginary = -1;

        expected.data[2][0].real = 1;
        expected.data[2][1].real = -1;
        expected.data[2][2].real = 1;
        expected.data[2][3].real = -1;

        expected.data[3][0].real = 1;
        expected.data[3][1].imaginary = -1;
        expected.data[3][2].real = -1;
        expected.data[3][3].imaginary = 1;

        expected = ComplexMatrixMath.scale(new Complex(0.5, 0.0), expected);
        expected = ComplexMatrixMath.hermitianAdjoint(expected);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected.data[i][j].real, result.data[i][j].real, 0.01);
                assertEquals(expected.data[i][j].imaginary, result.data[i][j].imaginary, 0.01);
            }
        }
    }


}

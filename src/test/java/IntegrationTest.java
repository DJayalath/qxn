import org.junit.jupiter.api.Test;
import org.qxn.QuantumMachine;
import org.qxn.gates.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {

    @Test
    void testWithDeutschJoszaConstant() {
        // Construct a 9-qubit machine where x is the first 8 wires and y is the last wire
        QuantumMachine deutschJoszaMachine = new QuantumMachine(3);

        // Add Hadamard gate to entangle all x inputs
        for (int i = 0; i < 2; i++)
            deutschJoszaMachine.addGate(new H(i));

        // Initialise y to |1>
        deutschJoszaMachine.addGate(new X(2));
        // Apply Hadamard to y
        deutschJoszaMachine.addGate(new H(2));

        // Use a functionally-defined oracle to for ease
        // This function definition should result in a constant boolean function f(x)
        deutschJoszaMachine.addGate(new Oracle(0, 2, 2, (bitString) -> true));

        // Interfere bits of x
        for (int i = 0; i < 2; i++)
            deutschJoszaMachine.addGate(new H(i));

        deutschJoszaMachine.execute();

        int result = 0;
        for (int i = 0; i < 2; i++)
            result += deutschJoszaMachine.measure(i);

        // We know that P(x = 0) = 1 if f(x) constant and P(x = 0) = 0 if f(x) balanced
        assertEquals(0, result);
    }

    @Test
    void testWithDeutschJoszaBalanced() {
        // Construct a 9-qubit machine where x is the first 8 wires and y is the last wire
        QuantumMachine deutschJoszaMachine = new QuantumMachine(3);

        // Add Hadamard gate to entangle all x inputs (initially all |0>)
        for (int i = 0; i < 2; i++)
            deutschJoszaMachine.addGate(new H(i));

        // Initialise y to |1>
        deutschJoszaMachine.addGate(new X(2));
        // Apply Hadamard to entangle y
        deutschJoszaMachine.addGate(new H(2));

        // Use a functionally-defined oracle for ease
        // This function definition should result in a balanced boolean function f(x) as it returns 1
        // whenever x is even. Our algorithm should therefore determine this to be a balanced function
        deutschJoszaMachine.addGate(new Oracle(0, 2, 2, (bitString) -> bitString % 2 == 0));

        // Interfere bits of x
        for (int i = 0; i < 2; i++)
            deutschJoszaMachine.addGate(new H(i));

        deutschJoszaMachine.execute();

        // We know that P(x = 0) = 1 if f(x) constant and P(x = 0) = 0 if f(x) balanced
        // Therefore if result is 0, f(x) is constant, otherwise it is balanced
        int result = 0;
        for (int i = 0; i < 2; i++)
            result += deutschJoszaMachine.measure(i);
        assertTrue(result > 0);
    }

    @Test
    void testWithQuantumTeleportation() {
        QuantumMachine teleportationMachine = new QuantumMachine(3);

        // Set qubit to teleport (Remove X gate here to teleport 0 instead)
        teleportationMachine.addGate(new X(0));

        // Construct bell state between Alice and Bob (entangled pair)
        teleportationMachine.addGate(new H(1));
        teleportationMachine.addGate(new CNOT(1, 2));

        // Teleportation circuit
        teleportationMachine.addGate(new CNOT(0, 1));
        teleportationMachine.addGate(new H(0));

        teleportationMachine.execute();

        int m1 = teleportationMachine.measure(0);
        int m2 = teleportationMachine.measure(1);

        // Apply correction
        if (m2 == 1) {
            teleportationMachine.addGate(new X(2));
        }
        if (m1 == 1) {
            teleportationMachine.addGate(new Z(2));
        }

        teleportationMachine.execute();

        assertEquals(1, teleportationMachine.measure(2));
    }

}
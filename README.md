[![qxn](qxn.png)](https://github.com/armytricks/qxn)
# qxn

[![Build](https://travis-ci.org/armytricks/qxn.svg?branch=master)](https://github.com/armytricks/qxn/releases/latest)
[![License](https://img.shields.io/badge/license-MIT-blue)](LICENSE)
[![Documentation](https://img.shields.io/badge/doc-javadoc-orange)](https://dulhanjayalath.com/qxn/overview-tree.html)

[Releases](https://github.com/armytricks/qxn/releases/latest) | [License](LICENSE) | [Documentation](https://dulhanjayalath.com/qxn/overview-tree.html)

**qxn** is an open-source library for noiseless quantum circuit and algorithm simulation.

qxn is designed for mocking and verifying the behaviour of quantum algorithms quickly and efficiently through programmatically
defined quantum oracles.

```java
...
quantumMachine.addGate(new Oracle(0, 2, 2, (x) -> {
    if (x == 0b00 || x == 0b01)
        return true;
    return false;
}));
...
```

## Getting Started
STEP 1: Construct a quantum machine with your desired number of qubits
```java
// Initialise a quantum virtual machine with 2 'wires' (2-qubit register)
QuantumMachine myQuantumMachine = new QuantumMachine(2);
```
STEP 2: Define your quantum circuit
```java
...
myQuantumMachine.addGate(new H(0)); // Add Hadamard gate to wire 0
myQuantumMachine.addGate(new X(1)); // Add Pauli-X gate to wire 1
...
```
STEP 3: Run your quantum algorithm

```java
...
myQuantumMachine.execute(); // Executes the quantum algorithm on the virtual quantum machine
myQuantumMachine.printState(); // Shows the final resulting bit strings and their respective probabilities
```
Some Notes:
- Wires/Qubits are indexed starting from zero
- The quantum virtual machine executes gates in the order they are added
- The quantum machine will clear the internal set of gates after calling `execute()`

## Using oracles
The defining feature of **qxn** is the ability to define oracles not only via a matrix but also through lambda
functions.

An oracle is defined by a linear map `U : (x, y) -> (x, y XOR f(x))`

With qxn, you can define the function f(x) through a boolean lambda function:
```java
// Example oracle where x is an 8-qubit register (wires 0-7) and y is on wire 8
// The lambda defines f(x) = 1 whenever x is even and f(x) = 0 otherwise
Oracle myOracle = new Oracle(0, 8, 8, (x) -> x % 2 == 0);
myQuantumMachine.addGate(myOracle);
```
When the lambda evaluates to true, f(x) = 1, and f(x) = 0 otherwise. The lambda allows you to provide a mapping for
each possible input x.

qxn will generate a matrix for your oracle using the function definition which you can access:
```java
myOracle.getMatrix().print();
```

qxn will even check if the provided function evaluates to a unitary matrix and warn you if it does not!

For an example of oracles in action, see the [Deutsch-Josza algorithm](#Deutsch-Josza-Algorithm) example.
## Useful features
#### View underlying quantum state
qxn is a Schrödinger full state-vector simulator so you can access the underlying quantum state at any time. 

After calling `execute()` on your quantum virtual machine, you can view the quantum state matrix with
`myQuantumMachine.getQubits().print()`

#### Measure qubits
You can measure qubits to retrieve a classical bit. This, like a real quantum machine, will collapse the underlying
quantum state to the measured result.

```java
int classicalBit = myQuantumMachine.measure(0); // Measure state of wire 0 (0th qubit)
```

#### Custom gates
qxn gives you the ability to define gates and oracles using matrices:
```java
// Define the identity matrix to create a gate that preserves state (I gate)
ComplexMatrix myGateMatrix = new ComplexMatrix(2, 2);
myGateMatrix.data[0][0] = new Complex(1, 0);
myGateMatrix.data[0][1] = new Complex(0, 0);
myGateMatrix.data[1][0] = new Complex(0, 0);
myGateMatrix.data[1][1] = new Complex(1, 0);

Gate myCustomGate = new CustomGate(0, 1, myGateMatrix);
```

#### Controlled gates
You can make your own controlled gates using qxn's controlled gate composition:
```java
// Create a controlled X gate with control wire 1
Gate CX = new C(new X(2), 1);

// Create a gate with two control wires by composition of controlled gates
Gate CCX = new C(CX, 0)
```

## Examples
#### Quantum Teleportation
```java
QuantumMachine teleportationMachine = new QuantumMachine(3);

// Set qubit to teleport in 0th wire (Remove X gate here to teleport 0 instead)
teleportationMachine.addGate(new X(0));
System.out.println("TELEPORTING: 1");

// Construct bell state between Alice and Bob (entangled pair) in wires 1 and 2

// Add Hadamard gate to wire 1
teleportationMachine.addGate(new H(1));
// Add CNOT gate to wire 1 (control) and wire 2 (target)
teleportationMachine.addGate(new CNOT(1, 2));

// Teleportation circuit

// Add CNOT gate to wire 0 (control) and wire 1 (target)
teleportationMachine.addGate(new CNOT(0, 1));
// Add Hadamard gate to wire 0
teleportationMachine.addGate(new H(0));

// Execute quantum machine        
teleportationMachine.execute();

// Measure state of wire 0 (collapses state and gives classical bit)
int m1 = teleportationMachine.measure(0);
// Measure state of wire 1 (collapses state and gives classical bit)
int m2 = teleportationMachine.measure(1);

// Apply correction
if (m2 == 1) {
    teleportationMachine.addGate(new X(2));
}
if (m1 == 1) {
    teleportationMachine.addGate(new Z(2));
}

teleportationMachine.execute();

System.out.println("RESULT: " + teleportationMachine.measure(2));
```

#### Deutsch-Josza Algorithm
```java
// Construct a 9-qubit machine where x is the first 8 wires and y is the last wire
QuantumMachine deutschJoszaMachine = new QuantumMachine(9);

// Add Hadamard gate to entangle all x inputs (initially all |0>)
for (int i = 0; i < 8; i++)
    deutschJoszaMachine.addGate(new H(i));

// Initialise y to |1>
deutschJoszaMachine.addGate(new X(8));
// Apply Hadamard to entangle y
deutschJoszaMachine.addGate(new H(8));

// Use a functionally-defined oracle for ease
// This function definition should result in a balanced boolean function f(x) as it returns 1
// whenever x is even. Our algorithm should therefore determine this to be a balanced function
deutschJoszaMachine.addGate(new Oracle(0, 8, 8, (x) -> x % 2 == 0));

// Interfere bits of x
for (int i = 0; i < 8; i++)
    deutschJoszaMachine.addGate(new H(i));

deutschJoszaMachine.execute();

// We know that P(x = 0) = 1 if f(x) constant and P(x = 0) = 0 if f(x) balanced
// Therefore if the sum of the bits of x measured is 0, f(x) is constant,
// otherwise it is balanced
int bitSum = 0;
for (int i = 0; i < 8; i++)
    bitSum += deutschJoszaMachine.measure(i);

System.out.println((bitSum == 0) ? "CONSTANT" : "BALANCED");
```
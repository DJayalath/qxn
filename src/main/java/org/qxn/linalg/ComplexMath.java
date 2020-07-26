package org.qxn.linalg;

public class ComplexMath {

    public static Complex add(Complex a, Complex b) {
        return new Complex(a.real + b.real, a.imaginary + b.imaginary);
    }

    public static Complex subtract(Complex a, Complex b) {
        return new Complex(a.real - b.real, a.imaginary - b.imaginary);
    }

    public static Complex multiply(Complex a, Complex b) {
        return new Complex(a.real * b.real - a.imaginary * b.imaginary,
                a.real * b.imaginary + b.real * a.imaginary);
    }

    public static Complex conjugate(Complex a) {
        return new Complex(a.real, -a.imaginary);
    }

}

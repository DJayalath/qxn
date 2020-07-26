package org.qxn.linalg;

public class Complex {

    public double real;
    public double imaginary;

    public Complex() {
        this.real = 0;
        this.imaginary = 0;
    }

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public static Complex complexFromModulusArgument(double modulus, double argument) {
        return new Complex(modulus * Math.cos(argument), modulus * Math.sin(argument));
    }

    public double getMagnitude() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    public double getMagnitude2() {
        return real * real + imaginary * imaginary;
    }

    public boolean equals(Complex b) {
        return this.real == b.real && this.imaginary == b.imaginary;
    }

    public void print() {
        System.out.print(real + " + " + imaginary + "i");
    }

}

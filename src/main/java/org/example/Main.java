package org.example;

// Class declaration

public class Main {
    // Method declaration
    public static void main(String[] args) {

    // Creating instance of class A named objectA
    A name = new A("Logitech Vertical Mouse");
    C[] versionArray = new C[3];
    versionArray[0] = new C(20);
    versionArray[1] = new C(21);
    versionArray[2] = new C(22);
    B productInfo = new B(name, versionArray);

    OutputDevice outputDevice = new OutputDevice();
    outputDevice.printAvailableVersions(productInfo, "Logitech Vertical Mouse");
    }
}

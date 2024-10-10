package org.example;

// Class declaration

public class Main {
    // Method declaration
    public static void main(String[] args) {

        // InputDevice object
        InputDevice inputDevice = new InputDevice();

        // OutputDevice object
        OutputDevice outputDevice = new OutputDevice();

        // pass InputDevice object to Application object
        Application app = new Application(inputDevice, outputDevice);

        app.run();
    }
}

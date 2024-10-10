package org.example;

public class InputDevice {
    public String getType() {
        return "random";
    }

    public int nextInt() {
        return (int)(Math.random()*100) + 1;
    }
}

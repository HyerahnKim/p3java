package com.animalshelter;

public class Rabbit extends Animal implements Diet{
    private String color;

    public Rabbit(String name, int age, String sex, String color) {
        super(name, age, sex);
        this.color = color;
    }

    // Getter and Setter for color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void eat() {
        System.out.println("Rabbit is eating cabbages.");
    }

    @Override
    public String toString() {
        return "Rabbit [Color=" + color + "]";
    }
}

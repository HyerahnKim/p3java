package com.animalshelter;
import org.json.JSONObject;

public class Cat extends Animal implements Voice, Diet, Comparable<Cat> {
    private String color;

    public Cat(String name, int age, String sex, String color) {
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

    // Interface Voice and Diet
    public void makeSound() {
        System.out.println("Meow! Meow!");
    }
    public void eat() {
        System.out.println("Cat is eating yogurt.");
    }

    // json
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("type", "Cat");
        json.put("color", color);
        return json;
    }

    // Implementing compareTo to compare by age
    @Override
    public int compareTo(Cat otherCat) {
        return Integer.compare(this.age, otherCat.age);
    }

    @Override
    public String toString() {
        return "Cat    [Name=" + name + ", Age=" + age + ", Sex=" + sex +  ", Color=" + color + "]";
    }
}

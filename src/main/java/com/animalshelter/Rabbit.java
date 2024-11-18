package com.animalshelter;
import org.json.JSONObject;

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


    // Interface for Diet
    public void eat() {
        System.out.println("Rabbit is eating cabbages.");
    }

    @Override
    public String toString() {
        return "Rabbit [Name=" + name + ", Age=" + age + ", Sex=" + sex +  ", Color=" + color + "]";
    }

    // json
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("type", "Rabbit");
        json.put("color", color);
        return json;
    }

}

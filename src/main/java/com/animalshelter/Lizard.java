package com.animalshelter;
import org.json.JSONObject;

public class Lizard extends Animal implements Diet {
    private boolean poisonous;

    public Lizard(String name, int age, String sex, boolean poisonous) {
        super(name, age, sex);
        this.poisonous = poisonous;
    }

    // Getter and Setter for poisonous
    public boolean isPoisonous() {
        return poisonous;
    }
    public void setPoisonous(boolean poisonous) {
        this.poisonous = poisonous;
    }

    // json


    // Interface for Diet
    public void eat() {
        System.out.println("Lizard is eating insects.");
    }

    @Override
    public String toString() {
        return "Lizard [Name=" + name + ", Age=" + age + ", Sex=" + sex + ", Poisonous=" + poisonous + "]";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("type", "Lizard");
        json.put("poisonous", poisonous);
        return json;
    }
}


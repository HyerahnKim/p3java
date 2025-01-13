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

    // Interface for Diet
    public String eat() {
        return("Lizard is eating insects.");
    }

    // Interface for Sound
    public String makeSound() {
        return("The lizard makes a soft clicking sound.");
    }
    @Override
    public String toString() {
        return "Lizard [Name=" + name + ", Age=" + age + ", Sex=" + sex + ", Poisonous=" + poisonous + "]";
    }

    // json
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("type", "Lizard");
        json.put("poisonous", poisonous);
        return json;
    }
}


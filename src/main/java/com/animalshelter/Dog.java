package com.animalshelter;
import org.json.JSONObject;

public class Dog extends Animal implements Voice, Diet, Comparable<Dog>{
    private float weight;

    public Dog(String name, int age, String sex, float weight) {
        super(name, age, sex);
        this.weight = weight;
    }

    // Interface Voice and Diet
    public void makeSound() {
        System.out.println("Woof Woof!");
    }
    public void eat() {
        System.out.println("Dogs eat blueberries.");
    }

    // Getter and Setter for weight
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    // json
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("type", "Dog");
        json.put("weight", weight);
        return json;
    }

    @Override
    public int compareTo(Dog otherDog) {
        return Integer.compare(this.age, otherDog.age);
    }

    public String toString() {
        return "Dog    [Name=" + name + ", Age=" + age + ", Sex=" + sex + ", Weight(KG)=" + weight + "]";
    }
}

package com.animalshelter;

public class Dog extends Animal implements Voice, Diet, Comparable<Dog>{
    private String breed;

    public Dog(String name, int age, String sex, String breed) {
        super(name, age, sex);
        this.breed = breed;
    }

    public void makeSound() {
        System.out.println("Woof! Woof!");
    }

    public void eat() {
        System.out.println("Dog is eating blueberries.");
    }

    // Getter and Setter for breed
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public int compareTo(Dog otherDog) {
        return Integer.compare(this.age, otherDog.age);
    }

    public String toString() {
        return "Dog [Name=" + name + ", Age=" + age + ", Sex=" + sex + ", Breed=" + breed + "]";
    }
}

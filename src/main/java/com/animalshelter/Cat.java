package com.animalshelter;

public class Cat extends Animal implements Voice, Diet, Comparable<Cat> {
    private String pattern;

    public Cat(String name, int age, String sex, String pattern) {
        super(name, age, sex);
        this.pattern = pattern;
    }

    // Getter and Setter for pattern
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void makeSound() {
        System.out.println("Meow! Meow!");
    }

    public void eat() {
        System.out.println("Cat is eating yogurt.");
    }

    // Implementing compareTo to compare by age
    @Override
    public int compareTo(Cat otherCat) {
        return Integer.compare(this.age, otherCat.age);
    }

    @Override
    public String toString() {
        return "Cat [Name=" + name + ", Age=" + age + ", Sex=" + sex + "]";
    }
}

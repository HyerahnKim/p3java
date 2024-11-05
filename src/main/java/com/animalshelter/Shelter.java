package com.animalshelter;

// Shelter can have a constructor that initializes an array of Animal (or arrays of Dog and Cat)
public class Shelter {
    private Animal[] animals;
    private int currentIndex = 0;

    public Shelter(int capacity) {
        animals = new Animal[capacity];
    }

    public void addAnimal(Animal animal) {
        if (currentIndex < animals.length) {
            animals[currentIndex++] = animal;
            System.out.println("Animal added to shelter.");
        } else {
            System.out.println("Shelter is full.");
        }
    }

    public void displayAnimals() {
        for (int i = 0; i < currentIndex; i++) {
            System.out.println(animals[i].toString());
        }
    }
}

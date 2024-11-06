package com.animalshelter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



// Shelter can have a constructor that initializes an array of Animal (or arrays of Dog and Cat)
public class Shelter {
    private List<Animal> animals;
    private int currentIndex = 0;

    public Shelter() {
        animals = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        System.out.println(animal.getName() + " has been added to the shelter.");
    }

    public void displayAnimals() {
        if (animals.isEmpty()) {
            System.out.println("No animals in the shelter.");
        } else {
            System.out.println("Animals in the shelter:");
            animals.forEach(System.out::println);
        }
    }



    // 1. Sort animals by age
    public void sortAnimalsByAge() {
        List<Animal> sortedAnimals = animals.stream()
                .sorted(Comparator.comparingInt(animal -> animal.age))
                .collect(Collectors.toList());

        System.out.println("Animals sorted by age:");
        for (Animal animal : sortedAnimals) {
            System.out.println(animal.toString());
        }
    }


    // 2. Group animals by type (e.g., Dogs and Cats)
    public void groupAnimalsByType() {
        System.out.println("Animals grouped by type:");
        List<Dog> dogs = animals.stream()
                .filter(animal -> animal instanceof Dog)
                .map(animal -> (Dog) animal)
                .collect(Collectors.toList());
        List<Cat> cats = animals.stream()
                .filter(animal -> animal instanceof Cat)
                .map(animal -> (Cat) animal)
                .collect(Collectors.toList());

        System.out.println("Dogs:");
        for (Dog dog : dogs) {
            System.out.println(dog.toString());
        }

        System.out.println("Cats:");
        for (Cat cat : cats) {
            System.out.println(cat.toString());
        }
    }

    // 3. Sort and display all dogs by age
    public void sortDogsByAge() {
        List<Dog> dogs = animals.stream()
                .filter(animal -> animal instanceof Dog)
                .map(animal -> (Dog) animal)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Dogs sorted by age:");
        for (Dog dog : dogs) {
            System.out.println(dog.toString());
        }
    }


}

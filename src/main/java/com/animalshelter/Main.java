package com.animalshelter;

public class Main {
    public static void main(String[] args) {
        Shelter shelter = new Shelter();

        // Creating instances of different animals
        Dog dog1 = new Dog("Rex", 2, "Male", "German Shepherd");
        Dog dog2 = new Dog("Buddy", 5, "Male", "Labrador");
        Cat cat1 = new Cat("Whiskers", 3, "Female", "Tabby");
        Cat cat2 = new Cat("Luna", 1, "Female", "Siamese");

        // Adding animals to the shelter
        shelter.addAnimal(dog1);
        shelter.addAnimal(dog2);
        shelter.addAnimal(cat1);
        shelter.addAnimal(cat2);

        // Display all animals in the shelter
        shelter.displayAnimals();

        // Sort animals by age
        shelter.sortAnimalsByAge();

        // Group animals by type
        shelter.groupAnimalsByType();

        // Sort and display all dogs by age
        shelter.sortDogsByAge();
    }
}

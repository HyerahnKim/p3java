package com.animalshelter;

public class Main {
    public static void main(String[] args) {
        Shelter shelter = new Shelter(10);

        // Check each argument
        for (String arg : args) {
            if (arg.equals("add")) {
                shelter.addAnimal(new Dog("Rex", 2, "Male", "German Shepherd"));
            } else if (arg.equals("display")) {
                shelter.displayAnimals();
            }
        }
    }
}
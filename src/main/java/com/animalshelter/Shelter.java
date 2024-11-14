package com.animalshelter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;


// Shelter can have a constructor that initializes an array of Animal (or arrays of Dog and Cat)
public class Shelter {
    private List<Animal> animals;
    private int currentIndex = 0;
    private ExceptionHandler exceptionHandler = new ExceptionHandler();

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
    // Ask user for file
    public void askUserForFile() {
        String filename;

        while (true) {
            System.out.print("Please enter the filename to load animals from: ");
            filename = exceptionHandler.handleIOException(""); // Initial prompt without an error message

            try {
                // Try to read the content of the file
                String content = new String(Files.readAllBytes(Paths.get(filename)));

                // If reading is successful, print the content and load animals
                System.out.println("File content:");
                System.out.println(content);

                // Optionally, load animals if the JSON format is correct
                loadAnimalsFromJson(filename);

                // Exit the loop after successfully loading the file
                break;

            } catch (IOException e) {
                // Use ExceptionHandler to prompt for another filename if an IOException occurs
                filename = exceptionHandler.handleIOException("Error: Could not open file '" + filename + "'. Please try again.");
            }
        }
    }


    // Save animals to a JSON file
    public void saveAnimalsToJson(String filename) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Animal animal : animals) {
            jsonArray.put(animal.toJson()); // Convert each animal to JSON
        }

        JSONObject shelterJson = new JSONObject();
        shelterJson.put("animals", jsonArray);

        try (FileWriter file = new FileWriter(filename)) {
            file.write(shelterJson.toString(4)); // Pretty print with 4 spaces
        }
        System.out.println("Animals have been saved to " + filename);
    }

    // Load animals from a JSON file
    public void loadAnimalsFromJson(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        JSONObject shelterJson = new JSONObject(content);
        JSONArray jsonArray = shelterJson.getJSONArray("animals");

        animals.clear(); // Clear existing list
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonAnimal = jsonArray.getJSONObject(i);
            String type = jsonAnimal.getString("type");

            switch (type) {
                case "Dog":
                    Dog dog = new Dog(
                            jsonAnimal.getString("name"),
                            jsonAnimal.getInt("age"),
                            jsonAnimal.getString("sex"),
                            jsonAnimal.getString("breed")
                    );
                    animals.add(dog);
                    break;
                case "Cat":
                    Cat cat = new Cat(
                            jsonAnimal.getString("name"),
                            jsonAnimal.getInt("age"),
                            jsonAnimal.getString("sex"),
                            jsonAnimal.getString("pattern")
                    );
                    animals.add(cat);
                    break;
                case "Rabbit":
                    Rabbit rabbit = new Rabbit(
                            jsonAnimal.getString("name"),
                            jsonAnimal.getInt("age"),
                            jsonAnimal.getString("sex"),
                            jsonAnimal.getString("color")
                    );
                    animals.add(rabbit);
                    break;
                case "Lizard":
                    Lizard lizard = new Lizard(
                            jsonAnimal.getString("name"),
                            jsonAnimal.getInt("age"),
                            jsonAnimal.getString("sex"),
                            jsonAnimal.getBoolean("poisonous")
                    );
                    animals.add(lizard);
                    break;
                default:
                    System.out.println("Unknown animal type: " + type);
                    break;
            }
        }
        System.out.println("Animals have been loaded from " + filename);
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

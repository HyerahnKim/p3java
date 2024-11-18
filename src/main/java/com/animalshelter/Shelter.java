package com.animalshelter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;


// Shelter can have a constructor that initializes an array of Animal (or arrays of Dog and Cat)
public class Shelter {
    private List<Animal> animals;
    private Set<String> animalNames; // Set to track unique names
    private ExceptionHandler exceptionHandler;

    public Shelter() {
        animals = new ArrayList<>();
        animalNames = new HashSet<>();
        exceptionHandler = new ExceptionHandler();
    }

    public void addAnimal(Animal animal) {
        if (animalNames.contains(animal.getName().toLowerCase())) {
            exceptionHandler.sameNameException();
            return; // Exit without adding the animal
        }

        animals.add(animal);
        animalNames.add(animal.getName().toLowerCase());
        System.out.println(animal.getName() + " has been added to the shelter.");
        autoSave();
    }

    public void displayAnimals() {
        if (animals.isEmpty()) {
            System.out.println("No animals in the shelter.");
        } else {
            System.out.println("Animals in the shelter:");
            animals.forEach(System.out::println);
        }
    }

    // Auto-save method
    private void autoSave() {
        try {
            saveAnimalsToJson("animals.json");
        } catch (IOException e) {
            System.out.println("Error saving animals to json.");
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
    }

    // Load animals from a JSON file
    public void loadAnimalsFromJson(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));

        JSONObject shelterJson = new JSONObject(content);
        JSONArray jsonArray = shelterJson.getJSONArray("animals");

        animals.clear(); // Clear existing list
        animalNames.clear(); // Clear the names set

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonAnimal = jsonArray.getJSONObject(i);
            String type = jsonAnimal.getString("type");

            Animal animal = null;
            switch (type) {
                case "Dog":
                    animal = new Dog(
                            jsonAnimal.getString("name"),
                            jsonAnimal.getInt("age"),
                            jsonAnimal.getString("sex"),
                            jsonAnimal.getInt("weight")
                    );
                    //animals.add(dog);
                    break;
                case "Cat":
                    animal = new Cat(
                            jsonAnimal.getString("name"),
                            jsonAnimal.getInt("age"),
                            jsonAnimal.getString("sex"),
                            jsonAnimal.getString("color")
                    );
                    //animals.add(cat);
                    break;
                case "Rabbit":
                    animal = new Rabbit(
                            jsonAnimal.getString("name"),
                            jsonAnimal.getInt("age"),
                            jsonAnimal.getString("sex"),
                            jsonAnimal.getString("color")
                    );
                    //animals.add(rabbit);
                    break;
                case "Lizard":
                    animal = new Lizard(
                            jsonAnimal.getString("name"),
                            jsonAnimal.getInt("age"),
                            jsonAnimal.getString("sex"),
                            jsonAnimal.getBoolean("poisonous")
                    );
                    //animals.add(lizard);
                    break;
                default:
                    System.out.println("Unknown animal type: " + type);
                    break;
            }
            animals.add(animal); // Add to the list
            animalNames.add(animal.getName().toLowerCase()); // Add the name to the set
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

    // 2. Group animals by type
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
        List<Rabbit> rabbits = animals.stream()
                .filter(animal -> animal instanceof Rabbit)
                .map(animal -> (Rabbit) animal)
                .collect(Collectors.toList());
        List<Lizard> lizards = animals.stream()
                .filter(animal -> animal instanceof Lizard)
                .map(animal -> (Lizard) animal)
                .collect(Collectors.toList());



        System.out.println("Dogs:");
        for (Dog dog : dogs) {
            System.out.println(dog.toString());
        }

        System.out.println("Cats:");
        for (Cat cat : cats) {
            System.out.println(cat.toString());
        }

        System.out.println("Rabbits:");
        for (Rabbit rabbit : rabbits) {
            System.out.println(rabbit.toString());
        }

        System.out.println("Lizards:");
        for (Lizard lizard : lizards) {
            System.out.println(lizard.toString());
        }
    }

    // 3. Find animals by name
    public Animal findAnimalByName(String name) {
        return animals.stream()
                .filter(animal -> animal.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // 4. Remove animal by name
    public void removeAnimal(String name) {
        Animal animal = findAnimalByName(name);
        if (animal != null) {
            animals.remove(animal);
            animalNames.remove(name.toLowerCase()); // Remove name from Set
            System.out.println(name + " has been removed from the shelter.");
        } else {
            System.out.println("No animal with the name '" + name + "' found.");
        }
    }



}

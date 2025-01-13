package com.animalshelter;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.json.JSONArray;
import org.json.JSONObject;


// Shelter can have a constructor that initializes an array of Animal (or arrays of Dog and Cat)
public class Shelter {
    private static Shelter instance;
    private List<Animal> animals;
    private Set<String> animalNames; // Set to track unique names
    private ExceptionHandler exceptionHandler;

    // Constructor
    public Shelter() {
        animals = new ArrayList<>();
        animalNames = new HashSet<>();
        exceptionHandler = new ExceptionHandler();
    }

    public static Shelter getInstance() {
        if (instance == null) {
            instance = new Shelter();
        }
        return instance;
    }

    // Get animals provides access to the internal animals list, which is used to display animals in the UI.
    public List<Animal> getAnimals() {
        System.out.println("Fetching animals. Total count: " + animals.size());
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    // Add an animal to the LIST
    public void addAnimal(Animal animal) {
        if (animalNames.contains(animal.getName().toLowerCase())) {
            exceptionHandler.sameNameException();
            return; // Exit without adding the animal
        }
        // Add to database
        saveAnimalToDatabase(animal);

        animals.add(animal);
        animalNames.add(animal.getName().toLowerCase());


        // Fetch the updated list of animals from the database
        //animals = loadAnimalsFromDatabase();

        System.out.println(animal.getName() + " has been added to the shelter.");
        autoSave();

    }

    public void displayAnimals() {
        // Default: Display animals from the shelter's internal list
        displayAnimals(this.animals);
    }

    public void displayAnimals(List<Animal> animals) {
        if (animals.isEmpty()) {
            System.out.println("No animals in the shelter.");
        } else {
            for (Animal animal : animals) {
                System.out.println(animal.toString());
            }
        }
    }

    // Auto-save method
    private void autoSave() {
        saveAnimalsToJsonInBackground("animals.json");
    }

    // Save animal LIST to a JSON file
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

    // Save Animal LIST to a JSON file using THREADS
    public void saveAnimalsToJsonInBackground(String filename) {
        new Thread(() -> {
            try {
                saveAnimalsToJson(filename); // Reuse the synchronous method
                System.out.println("Data saved in the background using threads to " + filename);
            } catch (IOException e) {
                System.out.println("Error while saving animals in the background: " + e.getMessage());
            }
        }).start(); // Start the thread immediately
    }

    // Save Animals to Mysql
    public void saveAnimalToDatabase(Animal animal) {
        String sqlAnimal = "INSERT INTO Animal (name, age, sex, type) VALUES (?, ?, ?, ?)";
        String sqlDog = "INSERT INTO Dog (animal_id, weight) VALUES (?, ?)";
        String sqlCat = "INSERT INTO Cat (animal_id, color) VALUES (?, ?)";
        String sqlRabbit = "INSERT INTO Rabbit (animal_id, color) VALUES (?, ?)";
        String sqlLizard = "INSERT INTO Lizard (animal_id, poisonous) VALUES (?, ?)";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmtAnimal = conn.prepareStatement(sqlAnimal, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Insert into Animal table
            stmtAnimal.setString(1, animal.getName());
            stmtAnimal.setInt(2, animal.getAge());
            stmtAnimal.setString(3, animal.getSex());
            stmtAnimal.setString(4, animal.getClass().getSimpleName()); // Use the class name as the type
            stmtAnimal.executeUpdate();

            // Get the generated animal ID
            ResultSet rs = stmtAnimal.getGeneratedKeys();
            if (rs.next()) {
                int animalId = rs.getInt(1);

                // Insert into type-specific table
                if (animal instanceof Dog) {
                    try (PreparedStatement stmtDog = conn.prepareStatement(sqlDog)) {
                        stmtDog.setInt(1, animalId);
                        stmtDog.setFloat(2, ((Dog) animal).getWeight());
                        stmtDog.executeUpdate();
                    }
                } else if (animal instanceof Cat) {
                    try (PreparedStatement stmtCat = conn.prepareStatement(sqlCat)) {
                        stmtCat.setInt(1, animalId);
                        stmtCat.setString(2, ((Cat) animal).getColor());
                        stmtCat.executeUpdate();
                    }
                } else if (animal instanceof Rabbit) {
                    try (PreparedStatement stmtRabbit = conn.prepareStatement(sqlRabbit)) {
                        stmtRabbit.setInt(1, animalId);
                        stmtRabbit.setString(2, ((Rabbit) animal).getColor());
                        stmtRabbit.executeUpdate();
                    }
                } else if (animal instanceof Lizard) {
                    try (PreparedStatement stmtLizard = conn.prepareStatement(sqlLizard)) {
                        stmtLizard.setInt(1, animalId);
                        stmtLizard.setBoolean(2, ((Lizard) animal).isPoisonous());
                        stmtLizard.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load Animals from Mysql
    public List<Animal> loadAnimalsFromDatabase() {
        List<Animal> loadedAnimals = new ArrayList<>();
        // Base SQL query to load all animals
        String sqlAnimal = "SELECT * FROM Animal";
        // SQL queries for specific animal types
        String sqlDog = "SELECT * FROM Dog WHERE animal_id = ?";
        String sqlCat = "SELECT * FROM Cat WHERE animal_id = ?";
        String sqlRabbit = "SELECT * FROM Rabbit WHERE animal_id = ?";
        String sqlLizard = "SELECT * FROM Lizard WHERE animal_id = ?";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmtAnimal = conn.prepareStatement(sqlAnimal);
             ResultSet rsAnimal = stmtAnimal.executeQuery()) {

            while (rsAnimal.next()) {
                int id = rsAnimal.getInt("id");
                String name = rsAnimal.getString("name");
                int age = rsAnimal.getInt("age");
                String sex = rsAnimal.getString("sex");
                String type = rsAnimal.getString("type");

                switch (type.toLowerCase()) {
                    case "dog":
                        try (PreparedStatement stmtDog = conn.prepareStatement(sqlDog)) {
                            stmtDog.setInt(1, id);
                            ResultSet rsDog = stmtDog.executeQuery();
                            if (rsDog.next()) {
                                float weight = rsDog.getFloat("weight");
                                loadedAnimals.add(new Dog(name, age, sex, weight));
                            }
                        }
                        break;

                    case "cat":
                        try (PreparedStatement stmtCat = conn.prepareStatement(sqlCat)) {
                            stmtCat.setInt(1, id);
                            ResultSet rsCat = stmtCat.executeQuery();
                            if (rsCat.next()) {
                                String color = rsCat.getString("color");
                                loadedAnimals.add(new Cat(name, age, sex, color));
                            }
                        }
                        break;

                    case "rabbit":
                        try (PreparedStatement stmtRabbit = conn.prepareStatement(sqlRabbit)) {
                            stmtRabbit.setInt(1, id);
                            ResultSet rsRabbit = stmtRabbit.executeQuery();
                            if (rsRabbit.next()) {
                                String color = rsRabbit.getString("color");
                                loadedAnimals.add(new Rabbit(name, age, sex, color));
                            }
                        }
                        break;

                    case "lizard":
                        try (PreparedStatement stmtLizard = conn.prepareStatement(sqlLizard)) {
                            stmtLizard.setInt(1, id);
                            ResultSet rsLizard = stmtLizard.executeQuery();
                            if (rsLizard.next()) {
                                boolean poisonous = rsLizard.getBoolean("poisonous");
                                loadedAnimals.add(new Lizard(name, age, sex, poisonous));
                            }
                        }
                        break;

                    default:
                        System.out.println("Unknown animal type: " + type);
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading animals from database: " + e.getMessage());
        }
        return loadedAnimals;
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
        long startTime = System.nanoTime(); // Start measuring time

        List<Animal> sortedAnimals = animals.stream()
                .sorted(Comparator.comparingInt(animal -> animal.age))
                .collect(Collectors.toList());

        long endTime = System.nanoTime(); // End measuring time
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        // Print Animals order by age asc
        for (Animal animal : sortedAnimals) {
            System.out.println(animal.toString());
        }
        // Print the execution time
        System.out.println("Animals sorted by age in " + duration + " ms:");

    }

    // 1-1. Sort animals by age using THREADS
    public void sortAnimalsByAgeInBackground() {
        new Thread(() -> {
            long startTime = System.nanoTime();
            List<Animal> sortedAnimals = animals.stream()
                    .sorted(Comparator.comparingInt(Animal::getAge))
                    .collect(Collectors.toList());
            long endTime = System.nanoTime();

            // Print Animals order by age asc
            System.out.println("Animals sorted by age:");
            sortedAnimals.forEach(System.out::println);

            // Print the execution time
            System.out.println("Sorting time: " + (endTime - startTime) / 1_000_000 + " ms");
        }).start();
    }

    // 1-2. getAnimalsSortedByAge
    public List<String> getAnimalsSortedByAge() {
        return animals.stream()
                .sorted(Comparator.comparingInt(Animal::getAge))
                .map(Animal::toString) // Convert to String for display
                .collect(Collectors.toList());
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

    // 2-1. Get Grouped Animals
    public Map<String, List<Animal>> getGroupedAnimals() {
        Map<String, List<Animal>> groupedAnimals = new HashMap<>();

        groupedAnimals.put("Dogs", animals.stream()
                .filter(animal -> animal instanceof Dog)
                .collect(Collectors.toList()));
        groupedAnimals.put("Cats", animals.stream()
                .filter(animal -> animal instanceof Cat)
                .collect(Collectors.toList()));
        groupedAnimals.put("Rabbits", animals.stream()
                .filter(animal -> animal instanceof Rabbit)
                .collect(Collectors.toList()));
        groupedAnimals.put("Lizards", animals.stream()
                .filter(animal -> animal instanceof Lizard)
                .collect(Collectors.toList()));

        return groupedAnimals;
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
        String lowercaseName = name.toLowerCase(); // Normalize the input to lowercase
        String sqlAnimal = "DELETE FROM Animal WHERE LOWER(name) = ?"; // SQL query for case-insensitive removal

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlAnimal)) {

            stmt.setString(1, lowercaseName);

            int rowsDeleted = stmt.executeUpdate(); // Execute the delete query
            if (rowsDeleted > 0) {
                // Also remove from the local list
                animals.removeIf(animal -> animal.getName().equalsIgnoreCase(lowercaseName));
                animalNames.remove(lowercaseName); // Remove the name from the Set
                System.out.println(lowercaseName + " has been removed from the shelter and database.");
            } else {
                System.out.println("No animal found with the name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Error while removing animal: " + e.getMessage());
        }
    }




}

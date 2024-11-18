package com.animalshelter;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public enum Role {
        ADOPTER,
        ADMIN
    }

    public static void main(String[] args) {
        Shelter shelter = new Shelter();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you an adopter or an admin?");
        System.out.println("1. Adopter");
        System.out.println("2. Admin");

        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Role userRole;

        if (roleChoice == 1) {
            userRole = Role.ADOPTER;
        } else if (roleChoice == 2) {
            userRole = Role.ADMIN;
        } else {
            System.out.println("Invalid choice. Exiting...");
            return;
        }

        boolean running = true;
        while (running) {
            if (userRole == Role.ADOPTER) {
                displayAdopterMenu(shelter, scanner);
            } else if (userRole == Role.ADMIN) {
                displayAdminMenu(shelter, scanner);
            }

            System.out.println("Do you want to continue? (y/n)");
            String continueChoice = scanner.nextLine().trim().toLowerCase();
            if (!continueChoice.equals("y")) {
                running = false;
            }
        }

        scanner.close();
    }

    private static void displayAdopterMenu(Shelter shelter, Scanner scanner) {
        System.out.println("\nAdopter Menu:");
        System.out.println("1. Display all animals");
        System.out.println("2. Sort animals by age");
        System.out.println("3. Group animals by type");
        System.out.println("4. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                shelter.displayAnimals();
                break;
            case 2:
                shelter.sortAnimalsByAge();
                break;
            case 3:
                shelter.groupAnimalsByType();
                break;
            case 4:
                System.out.println("Exiting Adopter Menu...");
                break;
            default:
                System.out.println("Invalid option. Please choose again.");
                break;
        }
    }

    private static void displayAdminMenu(Shelter shelter, Scanner scanner) {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Add an Animal");
        System.out.println("2. Display all animals");
        System.out.println("3. Sort animals by age");
        System.out.println("4. Group animals by type");
        System.out.println("5. Save animals to JSON");
        System.out.println("6. Load animals from JSON");
        System.out.println("7. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addAnimal(shelter, scanner);
                break;
            case 2:
                shelter.displayAnimals();
                break;
            case 3:
                shelter.sortAnimalsByAge();
                break;
            case 4:
                shelter.groupAnimalsByType();
                break;
            case 5:
                try {
                    shelter.saveAnimalsToJson("animals.json");
                    System.out.println("Animals saved to JSON.");
                } catch (IOException e) {
                    System.out.println("Error saving animals to JSON.");
                }
                break;
            case 6:
                try {
                    shelter.loadAnimalsFromJson("animals.json");
                    System.out.println("Animals loaded from JSON.");
                } catch (IOException e) {
                    System.out.println("Error loading animals from JSON.");
                }
                break;
            case 7:
                System.out.println("Exiting Admin Menu...");
                break;
            default:
                System.out.println("Invalid option. Please choose again.");
                break;
        }
    }

    private static void addAnimal(Shelter shelter, Scanner scanner) {
        System.out.println("Choose the type of animal to add:");
        System.out.println("1. Dog");
        System.out.println("2. Cat");
        System.out.println("3. Rabbit");
        System.out.println("4. Lizard");

        int animalChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (animalChoice) {
            case 1:
                addDog(shelter, scanner);
                break;
            case 2:
                addCat(shelter, scanner);
                break;
            case 3:
                addRabbit(shelter, scanner);
                break;
            case 4:
                addLizard(shelter, scanner);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    // Methods for adding Dog, Cat, Rabbit, and Lizard (same as before)
    private static void addDog(Shelter shelter, Scanner scanner) {
        System.out.print("Enter dog's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter dog's age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter dog's sex: ");
        String sex = scanner.nextLine();
        System.out.print("Enter dog's breed: ");
        String breed = scanner.nextLine();

        Dog dog = new Dog(name, age, sex, breed);
        shelter.addAnimal(dog);
    }

    private static void addCat(Shelter shelter, Scanner scanner) {
        System.out.print("Enter cat's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter cat's age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter cat's sex: ");
        String sex = scanner.nextLine();
        System.out.print("Enter cat's pattern: ");
        String pattern = scanner.nextLine();

        Cat cat = new Cat(name, age, sex, pattern);
        shelter.addAnimal(cat);
    }

    private static void addRabbit(Shelter shelter, Scanner scanner) {
        System.out.print("Enter rabbit's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter rabbit's age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter rabbit's sex: ");
        String sex = scanner.nextLine();
        System.out.print("Enter rabbit's color: ");
        String color = scanner.nextLine();

        Rabbit rabbit = new Rabbit(name, age, sex, color);
        shelter.addAnimal(rabbit);
    }

    private static void addLizard(Shelter shelter, Scanner scanner) {
        System.out.print("Enter lizard's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter lizard's age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter lizard's sex: ");
        String sex = scanner.nextLine();
        System.out.print("Is the lizard poisonous? (true/false): ");
        boolean poisonous = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        Lizard lizard = new Lizard(name, age, sex, poisonous);
        shelter.addAnimal(lizard);
    }
}

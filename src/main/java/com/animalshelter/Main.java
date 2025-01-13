package com.animalshelter;

import java.util.Scanner;

public class Main {

    public enum Role {
        ADOPTER,
        ADMIN
    }

    public static void main(String[] args) {
        Shelter shelter = Shelter.getInstance();
        Scanner scanner = new Scanner(System.in);

        // Load animals from the database or JSON
        try {
            shelter.setAnimals(shelter.loadAnimalsFromDatabase()); // Ensure animals are loaded
            System.out.println("Animals loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading animals: " + e.getMessage());
        }

        boolean programRunning = true;

        while (programRunning) {
            Role userRole = getUserRole(scanner);

            boolean inMenu = true;
            while (inMenu) {
                switch (userRole) {
                    case ADOPTER -> inMenu = handleAdopterMenu(shelter, scanner);
                    case ADMIN -> inMenu = handleAdminMenu(shelter, scanner);
                }
            }

            System.out.println("Do you want to continue? (y/n)");
            String continueChoice = scanner.nextLine().trim().toLowerCase();
            programRunning = continueChoice.equals("y");
        }

        System.out.println("Exiting the program. Goodbye!");
        scanner.close();
    }

    private static Role getUserRole(Scanner scanner) {
        while (true) {
            try {
                System.out.println("=================================");
                System.out.println("Are you an adopter or an admin?");
                System.out.println("1. Adopter");
                System.out.println("2. Admin");
                System.out.println("=================================");

                int roleChoice = Integer.parseInt(scanner.nextLine().trim());
                if (roleChoice == 1) {
                    return Role.ADOPTER;
                } else if (roleChoice == 2) {
                    return Role.ADMIN;
                } else {
                    System.out.println("Invalid choice. Please select 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static boolean handleAdopterMenu(Shelter shelter, Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nAdopter Menu:");
                System.out.println("1. Display all animals");
                System.out.println("2. Sort animals by age");
                System.out.println("3. Group animals by type");
                System.out.println("4. Interact with an animal");
                System.out.println("5. Exit");

                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1 -> shelter.displayAnimals();
                    case 2 -> shelter.sortAnimalsByAge();
                    case 3 -> shelter.groupAnimalsByType();
                    case 4 -> interactWithAnimal(shelter, scanner);
                    case 5 -> {
                        System.out.println("Exiting Adopter Menu...");
                        return false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static boolean handleAdminMenu(Shelter shelter, Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Add an Animal");
                System.out.println("2. Display all animals");
                System.out.println("3. Sort animals by age");
                System.out.println("4. Group animals by type");
                System.out.println("5. Exit");

                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1 -> addAnimal(shelter, scanner);
                    case 2 -> shelter.displayAnimals();
                    case 3 -> shelter.sortAnimalsByAge();
                    case 4 -> shelter.groupAnimalsByType();
                    case 5 -> {
                        System.out.println("Exiting Admin Menu...");
                        return false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void interactWithAnimal(Shelter shelter, Scanner scanner) {
        System.out.println("Enter the name of the animal to interact with:");
        String name = scanner.nextLine();

        Animal animal = shelter.findAnimalByName(name);
        if (animal == null) {
            System.out.println("No animal with that name found.");
        } else {
            if (animal instanceof Voice) {
                ((Voice) animal).makeSound();
            } else {
                System.out.println("This animal does not have a voice.");
            }

            if (animal instanceof Diet) {
                ((Diet) animal).eat();
            } else {
                System.out.println("This animal does not have a diet.");
            }
        }
    }

    private static void addAnimal(Shelter shelter, Scanner scanner) {
        System.out.println("Choose the type of animal to add:");
        System.out.println("1. Dog");
        System.out.println("2. Cat");
        System.out.println("3. Rabbit");
        System.out.println("4. Lizard");

        try {
            int animalChoice = Integer.parseInt(scanner.nextLine().trim());

            switch (animalChoice) {
                case 1 -> {
                    System.out.print("Enter dog's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter dog's age: ");
                    int age = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter dog's sex: ");
                    String sex = scanner.nextLine();
                    System.out.print("Enter dog's weight in KG: ");
                    float weight = Float.parseFloat(scanner.nextLine().trim());
                    shelter.addAnimal(new Dog(name, age, sex, weight));
                }
                case 2 -> {
                    System.out.print("Enter cat's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter cat's age: ");
                    int age = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter cat's sex: ");
                    String sex = scanner.nextLine();
                    System.out.print("Enter cat's color: ");
                    String color = scanner.nextLine();
                    shelter.addAnimal(new Cat(name, age, sex, color));
                }
                case 3 -> {
                    System.out.print("Enter rabbit's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter rabbit's age: ");
                    int age = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter rabbit's sex: ");
                    String sex = scanner.nextLine();
                    System.out.print("Enter rabbit's color: ");
                    String color = scanner.nextLine();
                    shelter.addAnimal(new Rabbit(name, age, sex, color));
                }
                case 4 -> {
                    System.out.print("Enter lizard's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter lizard's age: ");
                    int age = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter lizard's sex: ");
                    String sex = scanner.nextLine();
                    System.out.print("Is the lizard poisonous (true/false)? ");
                    boolean poisonous = Boolean.parseBoolean(scanner.nextLine().trim());
                    shelter.addAnimal(new Lizard(name, age, sex, poisonous));
                }
                default -> System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}

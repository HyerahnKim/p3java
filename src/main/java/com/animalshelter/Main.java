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

        // Load animals from JSON at startup
        try {
            shelter.loadAnimalsFromJson("animals.json");
            System.out.println("Animals loaded successfully from animals.json.");
        } catch (IOException e) {
            System.out.println("Error loading animals from JSON file: " + e.getMessage());
        }

        boolean programRunning = true; // Main program loop

        while (programRunning) {
            Role userRole = getUserRole(scanner); // Ask user for their role

            boolean inMenu = true;
            while (inMenu) {
                switch (userRole) {
                    case ADOPTER:
                        inMenu = handleAdopterMenu(shelter, scanner);
                        break;
                    case ADMIN:
                        inMenu = handleAdminMenu(shelter, scanner);
                        break;
                }
            }

            System.out.println("Do you want to continue? (y/n)");
            String continueChoice = scanner.nextLine().trim().toLowerCase();
            if (!continueChoice.equals("y")) {
                programRunning  = false;
            }
        }

        System.out.println("Exiting the program. Goodbye!");
        scanner.close();
    }

    private static Role getUserRole(Scanner scanner) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(); // Create an instance of ExceptionHandler

        while (true) {
            try {
                System.out.println("=================================");
                System.out.println("Are you an adopter or an admin?");
                System.out.println("1. Adopter");
                System.out.println("2. Admin");
                System.out.println("=================================");

                int roleChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (roleChoice == 1) {
                    return Role.ADOPTER;
                } else if (roleChoice == 2) {
                    return Role.ADMIN;
                } else {
                    exceptionHandler.handleInvalidInput();
                }
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine(); // Clear invalid input
                exceptionHandler.handleInvalidInput();
            }
        }
    }

    private static boolean handleAdopterMenu(Shelter shelter, Scanner scanner) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(); // Create an instance of ExceptionHandler
        try {
            Thread.sleep(500); // Half a second delay before showing the menu
        } catch (InterruptedException e) {
            exceptionHandler.handleInterruptedException();
        }
        try {
            System.out.println("============================");
            System.out.println("\nAdopter Menu:");
            System.out.println("1. Display all animals");
            System.out.println("2. Sort animals by age");
            System.out.println("3. Group animals by type");
            System.out.println("4. Interact with an animal");
            System.out.println("5. Exit");

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
                    interactWithAnimal(shelter, scanner);
                    break;
                case 5:
                    System.out.println("Exiting Adopter Menu...");
                    return false; // This takes the user to the previous menu
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
         catch (java.util.InputMismatchException e) {

                 exceptionHandler.handleInvalidInput();
                 scanner.nextLine();
         }

        return true; // stay in the adopter menu
    }

    private static void interactWithAnimal(Shelter shelter, Scanner scanner) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(); // Create an instance of ExceptionHandler
        try {
        System.out.println("============================");
        System.out.println("Enter the name of the animal to interact with:");
        String name = scanner.nextLine();

        Animal animal = shelter.findAnimalByName(name);
        if (animal == null) {
            System.out.println("No animal with that name found.");
            return;
        }

        if (animal instanceof Voice) {
            ((Voice) animal).makeSound();
        } else {
            System.out.println("This animal does not have a voice.");
        }

        if (animal instanceof Diet) {
            ((Diet) animal).eat();
        } else {
            System.out.println("This animal does not have a diet.");
        }}
        catch (java.util.InputMismatchException e) {
            exceptionHandler.handleInvalidInput();
        }
    }

    private static Boolean handleAdminMenu(Shelter shelter, Scanner scanner) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(); // Create an instance of ExceptionHandler

        try {
            Thread.sleep(500); // Half a second delay before showing the menu
        } catch (InterruptedException e) {
            exceptionHandler.handleInterruptedException();
        }

        try {
            System.out.println("============================");
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add an Animal");
            System.out.println("2. Display all animals");
            System.out.println("3. Sort animals by age");
            System.out.println("4. Group animals by type");
            System.out.println("5. Exit");

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
                    System.out.println("Exiting Admin Menu...");
                    return false; // Exit Admin Menu
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }

        } catch (java.util.InputMismatchException e) {
            exceptionHandler.handleInvalidInput();
            scanner.nextLine();
        }
        return true; // Stay in the admin menu
    }

    private static void addAnimal(Shelter shelter, Scanner scanner) {
        System.out.println("==================================");
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
    private static void addDog(Shelter shelter, Scanner scanner){
    System.out.print("Enter dog's name: ");
    String name = scanner.nextLine();
    System.out.print("Enter dog's age: ");
    int age = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    System.out.print("Enter dog's sex: ");
    String sex = scanner.nextLine();
    System.out.print("Enter dog's weight in KG: ");
    float weight = scanner.nextFloat();
    scanner.nextLine(); // Consume newline to clear the buffer
    Dog dog = new Dog(name, age, sex, weight);
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
        System.out.print("Enter cat's color: ");
        String color = scanner.nextLine();

        Cat cat = new Cat(name, age, sex, color);
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

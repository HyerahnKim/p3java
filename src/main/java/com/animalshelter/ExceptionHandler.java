package com.animalshelter;

import java.util.Scanner;

public class ExceptionHandler {

    private Scanner scanner;

    public ExceptionHandler() {
        scanner = new Scanner(System.in);
    }

    // Handle IOException and prompt the user for a new filename
    public String handleIOException(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public void handleInvalidInput() {
        System.out.println("Invalid choice. Please choose again.");

    }

    public void handleInterruptedException() {
        System.out.println("Thread was interrupted. Resuming operation...");
        Thread.currentThread().interrupt(); // Reset the interrupted status of the thread
    }

    public void handleFileNotFound(String filename) {
        System.out.println("Error: File not found. Please ensure the file exists and try again.");
    }

    public void sameNameException() {
    System.out.println("The name already exists. Choose another name for the animal.");
    }
}

package com.animalshelter;

import java.util.Scanner;

public class ExceptionHandler {

    private Scanner scanner;

    public ExceptionHandler() {
        scanner = new Scanner(System.in);
    }

    // Handle IOException and prompt the user for a new filename
    public String handleIOException(String errorMessage) {
        System.out.println(errorMessage);
        System.out.print("Please enter a valid filename: ");
        return scanner.nextLine();
    }
}

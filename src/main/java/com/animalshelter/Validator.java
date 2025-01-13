package com.animalshelter;

import java.util.Arrays;
import java.util.List;

public class Validator {

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("^[a-zA-Z]+$");
    }

    public static boolean isValidAge(String ageInput) {
        try {
            int age = Integer.parseInt(ageInput);
            return age >= 0; // Age should be non-negative
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidSex(String sex) {
        return sex != null && (sex.equalsIgnoreCase("Male") || sex.equalsIgnoreCase("Female"));
    }

    public static boolean isValidWeight(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false; // Null or empty input is invalid
        }
        try {
            float weight = Float.parseFloat(input.trim());
            return weight > 0; // Weight must be positive
        } catch (NumberFormatException e) {
            return false; // Invalid if not a valid float
        }
    }


    public static boolean isValidColor(String input) {
        return input != null && !input.trim().isEmpty(); // Color must be non-empty
    }

    public static boolean isValidType(String type) {
        List<String> validTypes = Arrays.asList("dog", "cat", "rabbit", "lizard");
        return type != null && validTypes.contains(type.toLowerCase());
    }

    public static boolean isValidPoisonous(String input) {
        if (input == null) {
            return false; // Null input is invalid
        }
        String trimmedInput = input.trim().toLowerCase();
        return trimmedInput.equals("true") || trimmedInput.equals("yes") || trimmedInput.equals("1") ||
                trimmedInput.equals("false") || trimmedInput.equals("no") || trimmedInput.equals("0");
    }

    public static boolean parsePoisonous(String input) {
        String trimmedInput = input.trim().toLowerCase();
        return trimmedInput.equals("true") || trimmedInput.equals("yes") || trimmedInput.equals("1");
    }
}


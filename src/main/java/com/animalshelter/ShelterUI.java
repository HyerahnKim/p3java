package com.animalshelter;

import java.util.*;

import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ShelterUI extends Application {

    private Shelter shelter = new Shelter(); // Connect to your Shelter backend
    private Role currentUserRole;

    @Override
    public void start(Stage primaryStage) {
        // Load animals from database or JSON
        try {
            List<Animal> loadedAnimals = shelter.loadAnimalsFromDatabase();
            shelter.setAnimals(loadedAnimals); // Ensure animals are set in the shelter
            System.out.println("Animals loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading animals: " + e.getMessage());
        }

        // Title Label with styling
        Label titleLabel = new Label("Timisoara Animal Shelter");
        titleLabel.getStyleClass().add("title"); // Add CSS class for styling

        // Role Selection Label
        Label roleLabel = new Label("Don't shop, ADOPT! \n\n\n\n\nSelect Your Role:\n");
        roleLabel.getStyleClass().add("label"); // Add CSS class for styling

        // Buttons for Admin and Adopter roles
        Button adminButton = new Button("I'm the admin");
        adminButton.getStyleClass().add("button"); // Add CSS class for styling

        Button adopterButton = new Button("I'd like to adopt");
        adopterButton.getStyleClass().add("button"); // Add CSS class for styling

        // Handle Admin Login
        adminButton.setOnAction(e -> {
            currentUserRole = Role.ADMIN; // Set role
            showAdminMenu(primaryStage); // Navigate to Admin Menu
        });

        // Handle Adopter Login
        adopterButton.setOnAction(e -> {
            currentUserRole = Role.ADOPTER; // Set role
            showAdopterMenu(primaryStage); // Navigate to Adopter Menu
        });

        // Layout and Scene
        VBox loginLayout = new VBox(20, titleLabel, roleLabel, adminButton, adopterButton);
        loginLayout.getStyleClass().add("vbox"); // Add CSS class for layout styling

        Scene loginScene = new Scene(loginLayout, 500, 600);
        // Apply the CSS file to the scene
        loginScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Set up the stage
        primaryStage.setTitle("Animal Shelter Management");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showAdminMenu(Stage primaryStage) {
        // Admin Menu
        VBox adminLayout = new VBox(20);
        adminLayout.getStyleClass().add("vbox"); // Apply CSS class for VBox

        // Title for Admin Menu
        Label titleLabel = new Label("Admin Menu");
        titleLabel.getStyleClass().add("title"); // Apply CSS class for the title

        // Buttons for Admin Actions
        Button viewAnimalsButton = new Button("View All Animals");
        viewAnimalsButton.getStyleClass().add("button"); // Apply CSS class for buttons

        Button addAnimalButton = new Button("Add Animal");
        addAnimalButton.getStyleClass().add("button");

        Button backButton = new Button("Back to Main Menu");
        backButton.getStyleClass().add("button");

        // Set button actions
        viewAnimalsButton.setOnAction(e -> showAnimalList(primaryStage));
        addAnimalButton.setOnAction(e -> showSelectAnimalTypeForm(primaryStage));
        backButton.setOnAction(e -> start(primaryStage));

        // Add components to the layout
        adminLayout.getChildren().addAll(titleLabel, viewAnimalsButton, addAnimalButton, backButton);

        // Create the Scene and apply CSS
        Scene adminScene = new Scene(adminLayout, 500, 600);
        adminScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Set the scene to the primary stage
        primaryStage.setScene(adminScene);
        primaryStage.show();
    }

    private void showAdopterMenu(Stage primaryStage) {
        // Layout for Adopter Menu
        VBox adopterLayout = new VBox(20);
        adopterLayout.getStyleClass().add("vbox"); // Apply CSS class for VBox layout

        // Title for Adopter Menu
        Label titleLabel = new Label("Adopter Menu");
        titleLabel.getStyleClass().add("title"); // Apply CSS class for title

        // Buttons for Adopter Actions
        Button viewAnimalsButton = new Button("View All Animals");
        viewAnimalsButton.getStyleClass().add("button"); // Apply CSS class for buttons

        Button interactButton = new Button("Interact with an Animal");
        interactButton.getStyleClass().add("button");

        Button adoptButton = new Button("Adopt an Animal");
        interactButton.getStyleClass().add("button");

        Button backButton = new Button("Back to Main Menu");
        backButton.getStyleClass().add("button");

        // Set button actions
        viewAnimalsButton.setOnAction(e -> showAnimalList(primaryStage));
        adoptButton.setOnAction(e -> showAdoptForm(primaryStage));
        interactButton.setOnAction(e -> showInteractWithAnimalForm(primaryStage));
        backButton.setOnAction(e -> start(primaryStage));

        // Add components to the layout
        adopterLayout.getChildren().addAll(titleLabel, viewAnimalsButton, adoptButton, interactButton, backButton);

        // Create the Scene and apply CSS
        Scene adopterScene = new Scene(adopterLayout, 500, 800);
        adopterScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); // Link CSS
        primaryStage.setScene(adopterScene);
        primaryStage.show();
    }

    private void showSelectAnimalTypeForm(Stage primaryStage) {
        VBox selectTypeLayout = new VBox(20);
        selectTypeLayout.getStyleClass().add("vbox");

        Label titleLabel = new Label("Select the Type of Animal to Add:");
        titleLabel.getStyleClass().add("title");

        Button dogButton = new Button("Dog");
        dogButton.getStyleClass().add("button");

        Button catButton = new Button("Cat");
        catButton.getStyleClass().add("button");

        Button rabbitButton = new Button("Rabbit");
        rabbitButton.getStyleClass().add("button");

        Button lizardButton = new Button("Lizard");
        lizardButton.getStyleClass().add("button");

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");

        dogButton.setOnAction(e -> showAddAnimalForm(primaryStage, "Dog"));
        catButton.setOnAction(e -> showAddAnimalForm(primaryStage, "Cat"));
        rabbitButton.setOnAction(e -> showAddAnimalForm(primaryStage, "Rabbit"));
        lizardButton.setOnAction(e -> showAddAnimalForm(primaryStage, "Lizard"));
        backButton.setOnAction(e -> showAdminMenu(primaryStage));

        selectTypeLayout.getChildren().addAll(titleLabel, dogButton, catButton, rabbitButton, lizardButton, backButton);

        Scene selectTypeScene = new Scene(selectTypeLayout, 500, 700);
        selectTypeScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(selectTypeScene);
        primaryStage.show();
    }

    private void showSelectAnimalSexForm(Stage primaryStage) {
        VBox sexSelectionLayout = new VBox(20);
        Label titleLabel = new Label("Filter Animals by Sex:");
        ComboBox<String> sexFilterBox = new ComboBox<>();
        sexFilterBox.getItems().addAll("All", "Male", "Female");
        sexFilterBox.setValue("All"); // Default value

        Button applyFilterButton = new Button("Apply Filter");
        applyFilterButton.setOnAction(e -> {
            String selectedSex = sexFilterBox.getValue();
            // Navigate to animal list with the selected filter
            showAnimalList(primaryStage); // Adjust for specific filtering logic
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showAnimalList(primaryStage));

        sexSelectionLayout.getChildren().addAll(titleLabel, sexFilterBox, applyFilterButton, backButton);
        sexSelectionLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene sexSelectionScene = new Scene(sexSelectionLayout, 400, 300);
        primaryStage.setScene(sexSelectionScene);
        primaryStage.show();
    }

    private void showAddAnimalForm(Stage primaryStage, String animalType) {
        VBox addAnimalLayout = new VBox(20);
        addAnimalLayout.getStyleClass().add("vbox"); // Apply VBox CSS

        Label titleLabel = new Label("Add a New " + animalType);
        titleLabel.getStyleClass().add("title"); // Apply title CSS

        // Common fields for all animals
        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        nameField.getStyleClass().add("text-field"); // Apply TextField CSS

        TextField ageField = new TextField();
        ageField.setPromptText("Enter Age");
        ageField.getStyleClass().add("text-field"); // Apply TextField CSS

        ComboBox<String> sexBox = new ComboBox<>();
        sexBox.getItems().addAll("Male", "Female");
        sexBox.getStyleClass().add("combo-box"); // Apply ComboBox CSS

        Label messageLabel = new Label();
        messageLabel.getStyleClass().add("label"); // Apply Label CSS for messages

        // Animal-specific fields
        TextField additionalField = new TextField();
        additionalField.getStyleClass().add("text-field"); // Apply TextField CSS
        if (animalType.equalsIgnoreCase("Dog")) {
            additionalField.setPromptText("Enter Weight (kg)");
        } else if (animalType.equalsIgnoreCase("Cat")) {
            additionalField.setPromptText("Enter Color");
        } else if (animalType.equalsIgnoreCase("Rabbit")) {
            additionalField.setPromptText("Enter Color");
        } else if (animalType.equalsIgnoreCase("Lizard")) {
            additionalField.setPromptText("Is Poisonous (true/false)");
        }

        // Add Button
        Button addButton = new Button("Add");
        addButton.getStyleClass().add("button"); // Apply Button CSS
        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String ageInput = ageField.getText().trim();
            String sex = sexBox.getValue();
            String additionalInput = additionalField.getText().trim();

            // Validate inputs
            if (!Validator.isValidName(name)) {
                messageLabel.setText("Invalid name. Name should only contain letters.");
                return;
            }
            if (!Validator.isValidAge(ageInput)) {
                messageLabel.setText("Invalid age. Please enter a positive integer.");
                return;
            }
            if (!Validator.isValidSex(sex)) {
                messageLabel.setText("Invalid sex. Please select Male or Female.");
                return;
            }

            try {
                int age = Integer.parseInt(ageInput);

                // Validate and add based on animal type
                switch (animalType.toLowerCase()) {
                    case "dog":
                        if (!Validator.isValidWeight(additionalInput)) {
                            messageLabel.setText("Invalid weight. Please enter a positive number.");
                            return;
                        }
                        float weight = Float.parseFloat(additionalInput); // Assume Validator ensures no NumberFormatException
                        shelter.addAnimal(new Dog(name, age, sex, weight));
                        break;

                    case "cat":
                        if (!Validator.isValidColor(additionalInput)) {
                            messageLabel.setText("Invalid color. Please provide a valid color.");
                            return;
                        }
                        shelter.addAnimal(new Cat(name, age, sex, additionalInput));
                        break;

                    case "rabbit":
                        if (!Validator.isValidColor(additionalInput)) {
                            messageLabel.setText("Invalid color. Please provide a valid color.");
                            return;
                        }
                        shelter.addAnimal(new Rabbit(name, age, sex, additionalInput));
                        break;

                    case "lizard":
                        if (!Validator.isValidPoisonous(additionalInput)) {
                            messageLabel.setText("Invalid input for Poisonous. Use 'true', 'false', 'yes', 'no', '1', or '0'.");
                            return;
                        }
                        boolean isPoisonous = Validator.parsePoisonous(additionalInput);
                        shelter.addAnimal(new Lizard(name, age, sex, isPoisonous));
                        break;

                    default:
                        messageLabel.setText("Unknown animal type. Please select a valid type.");
                }


                messageLabel.setText("Animal added successfully!");
            } catch (Exception ex) {
                messageLabel.setText("Error adding animal: " + ex.getMessage());
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button"); // Apply Button CSS
        backButton.setOnAction(e -> showSelectAnimalTypeForm(primaryStage));

        addAnimalLayout.getChildren().addAll(
                titleLabel,
                new Label("Name:"), nameField,
                new Label("Age:"), ageField,
                new Label("Sex:"), sexBox,
                new Label("Additional Info:"), additionalField,
                addButton, backButton, messageLabel
        );

        ScrollPane scrollPane = new ScrollPane(addAnimalLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true); // Ensures content fits vertically
        scrollPane.setPannable(true);


        Scene addAnimalScene = new Scene(scrollPane, 500, 600);
        addAnimalScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(addAnimalScene);
        primaryStage.show();
    }

    private void showAnimalList(Stage primaryStage) {
        VBox animalListLayout = new VBox(20);
        animalListLayout.getStyleClass().add("vbox"); // Apply VBox CSS

        Label titleLabel = new Label("Animals in the Shelter:");
        titleLabel.getStyleClass().add("title"); // Apply title CSS
        animalListLayout.getChildren().add(titleLabel);

        // Dropdown for filtering by type
        Label typeFilterLabel = new Label("Filter by Type:");
        ComboBox<String> typeFilterBox = new ComboBox<>();
        typeFilterBox.getItems().addAll("All", "Dog", "Cat", "Rabbit", "Lizard");
        typeFilterBox.setValue("All"); // Default value

        // Dropdown for filtering by sex
        Label sexFilterLabel = new Label("Filter by Sex:");
        ComboBox<String> sexFilterBox = new ComboBox<>();
        sexFilterBox.getItems().addAll("All", "Male", "Female"); // Add filtering options
        sexFilterBox.setValue("All"); // Default selection

        // Sort by Age Button
        Button sortButton = new Button("Sort by Age");
        sortButton.getStyleClass().add("button");

        // Animal List Display
        VBox animalDisplay = new VBox(10); // Contains the list of animals
        animalDisplay.setStyle("-fx-padding: 10; -fx-alignment: top-left;");

        // Add filters and sorting event handlers
        typeFilterBox.setOnAction(e -> updateAnimalList(animalDisplay, typeFilterBox.getValue(), sexFilterBox.getValue(), false));
        sexFilterBox.setOnAction(e -> updateAnimalList(animalDisplay, typeFilterBox.getValue(), sexFilterBox.getValue(), false));
        sortButton.setOnAction(e -> updateAnimalList(animalDisplay, typeFilterBox.getValue(), sexFilterBox.getValue(), true));

        // Add filters and buttons to the layout
        HBox filterBox = new HBox(10, typeFilterLabel, typeFilterBox, sexFilterLabel, sexFilterBox, sortButton);
        filterBox.setStyle("-fx-padding: 10;");
        animalListLayout.getChildren().addAll(filterBox, animalDisplay);

        // Initial load: Show all animals
        updateAnimalList(animalDisplay, "All", "All", false);

        // Back Button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> {
            if (currentUserRole == Role.ADMIN) {
                showAdminMenu(primaryStage);
            } else {
                showAdopterMenu(primaryStage);
            }
        });

        animalListLayout.getChildren().add(backButton);

        // Wrap the VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(animalListLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true); // Ensures content fits vertically
        scrollPane.setPannable(true);

        // Create the scene and apply CSS
        Scene animalListScene = new Scene(scrollPane, 600, 600);
        animalListScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(animalListScene);
        primaryStage.show();
    }


    private void updateAnimalList(VBox animalListLayout, String typeFilter, String sexFilter, boolean sortByAge) {
        animalListLayout.getChildren().clear();

        List<Animal> animals = shelter.getAnimals();

        // Apply type filter
        if (!typeFilter.equals("All")) {
            animals = animals.stream()
                    .filter(animal -> animal.getClass().getSimpleName().equalsIgnoreCase(typeFilter))
                    .collect(Collectors.toList());
        }

        // Apply sex filter
        if (!sexFilter.equals("All")) {
            animals = animals.stream()
                    .filter(animal -> animal.getSex().equalsIgnoreCase(sexFilter))
                    .collect(Collectors.toList());
        }

        // Sort by age if requested
        if (sortByAge) {
            animals = animals.stream()
                    .sorted(Comparator.comparingInt(Animal::getAge))
                    .collect(Collectors.toList());
        }

        // Display animals or message
        if (animals.isEmpty()) {
            animalListLayout.getChildren().add(new Label("No animals match the selected filters."));
        } else {
            for (Animal animal : animals) {
                Label animalLabel = new Label(animal.toString());
                animalLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5;");
                animalListLayout.getChildren().add(animalLabel);
            }
        }
    }


    private void showAdoptForm(Stage primaryStage) {
        VBox adoptLayout = new VBox(10);
        Label titleLabel = new Label("Adopt an Animal");
        adoptLayout.getChildren().add(titleLabel);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter the name of the animal to adopt");
        Button adoptButton = new Button("Adopt");
        Label resultLabel = new Label();

        adoptButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                resultLabel.setText("Please enter a valid name.");
                return;
            }

            Animal animal = shelter.findAnimalByName(name);
            if (animal == null) {
                resultLabel.setText("No animal found with the name: " + name);
            } else {
                shelter.removeAnimal(name); // Call the method in Shelter.java
                resultLabel.setText(name + " has been adopted!");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showAdopterMenu(primaryStage));

        adoptLayout.getChildren().addAll(new Label("Animal Name:"), nameField, adoptButton, resultLabel, backButton);
        adoptLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Scene adoptScene = new Scene(adoptLayout, 400, 400);

        primaryStage.setScene(adoptScene);
    }


    private void showInteractWithAnimalForm(Stage primaryStage) {
        VBox interactLayout = new VBox(20);
        interactLayout.getStyleClass().add("vbox"); // Apply VBox CSS

        Label titleLabel = new Label("Interact with an Animal");
        titleLabel.getStyleClass().add("title"); // Apply Title CSS
        interactLayout.getChildren().add(titleLabel);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter the name of the animal");
        nameField.getStyleClass().add("text-field"); // Apply TextField CSS

        Button interactButton = new Button("Interact");
        interactButton.getStyleClass().add("button"); // Apply Button CSS

        TextArea resultTextArea = new TextArea(); // Use TextArea for multi-line interaction results
        resultTextArea.setEditable(false);       // Make it non-editable
        resultTextArea.getStyleClass().add("text-area"); // Apply TextArea CSS

        interactButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            Animal animal = shelter.findAnimalByName(name);

            if (animal == null) {
                resultTextArea.setText("No animal with that name found.");
            } else {
                StringBuilder interaction = new StringBuilder();

                if (animal instanceof Voice) {
                    interaction.append(((Voice) animal).makeSound()).append("\n"); // Capture sound response
                } else {
                    interaction.append("This animal does not have a voice.\n");
                }

                if (animal instanceof Diet) {
                    interaction.append(((Diet) animal).eat()).append("\n"); // Capture eating response
                } else {
                    interaction.append("This animal does not have a diet.\n");
                }

                resultTextArea.setText(interaction.toString()); // Display results in the TextArea
            }
        });

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button"); // Apply Button CSS
        backButton.setOnAction(e -> {
            if (currentUserRole == Role.ADMIN) {
                showAdminMenu(primaryStage);
            } else {
                showAdopterMenu(primaryStage);
            }
        });

        interactLayout.getChildren().addAll(nameField, interactButton, resultTextArea, backButton);

        Scene interactScene = new Scene(interactLayout, 500, 800);
        interactScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(interactScene);
        primaryStage.show();
    }

    private void showSortedAnimals(Stage primaryStage) {
        VBox sortedListLayout = new VBox(20);
        sortedListLayout.getStyleClass().add("vbox"); // Apply VBox CSS

        Label titleLabel = new Label("Animals Sorted by Age:");
        titleLabel.getStyleClass().add("title"); // Apply Title CSS
        sortedListLayout.getChildren().add(titleLabel);

        // Fetch sorted animals from Shelter
        List<String> sortedAnimals = shelter.getAnimalsSortedByAge();

        if (sortedAnimals.isEmpty()) {
            Label noAnimalsLabel = new Label("No animals to display.");
            noAnimalsLabel.getStyleClass().add("label"); // Apply Label CSS
            sortedListLayout.getChildren().add(noAnimalsLabel);
        } else {
            for (String animal : sortedAnimals) {
                Label animalLabel = new Label(animal);
                animalLabel.getStyleClass().add("label"); // Apply Label CSS
                sortedListLayout.getChildren().add(animalLabel);
            }
        }

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button"); // Apply Button CSS
        backButton.setOnAction(e -> showAdopterMenu(primaryStage));
        sortedListLayout.getChildren().add(backButton);

        ScrollPane scrollPane = new ScrollPane(sortedListLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true); // Ensures content fits vertically
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showGroupedAnimals(Stage primaryStage) {
        VBox groupedLayout = new VBox(20);
        groupedLayout.getStyleClass().add("vbox"); // Apply VBox CSS

        Label titleLabel = new Label("Animals Grouped by Type:");
        titleLabel.getStyleClass().add("title"); // Apply Title CSS
        groupedLayout.getChildren().add(titleLabel);

        // Get grouped animals from Shelter
        Map<String, List<Animal>> groupedAnimals = shelter.getGroupedAnimals();

        for (Map.Entry<String, List<Animal>> entry : groupedAnimals.entrySet()) {
            String type = entry.getKey();
            List<Animal> animals = entry.getValue();

            Label typeLabel = new Label(type + ":");
            typeLabel.getStyleClass().add("header"); // Apply Header CSS
            groupedLayout.getChildren().add(typeLabel);

            if (animals.isEmpty()) {
                Label noAnimalsLabel = new Label("No " + type.toLowerCase() + " available.");
                noAnimalsLabel.getStyleClass().add("label"); // Apply Label CSS
                groupedLayout.getChildren().add(noAnimalsLabel);
            } else {
                for (Animal animal : animals) {
                    Label animalLabel = new Label(animal.toString());
                    animalLabel.getStyleClass().add("label"); // Apply Label CSS
                    groupedLayout.getChildren().add(animalLabel);
                }
            }
        }

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button"); // Apply Button CSS
        backButton.setOnAction(e -> showAdopterMenu(primaryStage)); // Adjust to your role-based menu
        groupedLayout.getChildren().add(backButton);

        ScrollPane scrollPane = new ScrollPane(groupedLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true); // Ensures content fits vertically
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

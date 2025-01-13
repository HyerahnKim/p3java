import com.animalshelter.Animal;
import com.animalshelter.Cat;
import com.animalshelter.Dog;
import com.animalshelter.Shelter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class ShelterTest {
    private Shelter shelter;
    @BeforeEach
    void setUp() {
        shelter = new Shelter();
    }

    @Test
    void testAddAnimal() {
        Shelter shelter = new Shelter();

        // Initial animal count
        int initialCount = shelter.getAnimals().size();
        System.out.println("Fetching animals. Initial count: " + initialCount);

        Dog dog = new Dog("Buddy", 3, "Male", 15.0f);
        shelter.addAnimal(dog);

        // New animal count
        int updatedCount = shelter.getAnimals().size();
        System.out.println("Fetching animals. Updated count: " + updatedCount);

        assertEquals(initialCount + 1, updatedCount, "Animals list should increase by one after adding a new animal.");
        assertTrue(shelter.getAnimals().contains(dog), "The newly added dog should be in the animals list.");
    }



    @Test
    void testNoSameNames() {
        Shelter shelter = new Shelter();

        // Ensure the name "Buddy" exists in the database or shelter
        Dog existingDog = new Dog("Buddy", 3, "Male", 15.0f);
        shelter.addAnimal(existingDog);

        int initialCount = shelter.getAnimals().size();

        // Attempt to add another animal with the same name
        Dog duplicateDog = new Dog("Buddy", 4, "Female", 12.0f);
        shelter.addAnimal(duplicateDog); // Should not be added

        assertEquals(initialCount, shelter.getAnimals().size(), "Duplicate names should not increase the animal count.");
        assertTrue(shelter.getAnimals().contains(existingDog), "The existing dog should remain in the list.");
    }

    @Test
    void testRemoveAnimal() {
        Shelter shelter = new Shelter();

        Dog dog1 = new Dog("Buddy", 3, "Male", 15.0f);
        Cat cat = new Cat("Whiskers", 2, "Female", "Black");

        shelter.addAnimal(dog1);
        shelter.addAnimal(cat);

        int initialCount = shelter.getAnimals().size();

        // Remove one animal
        shelter.removeAnimal("Buddy");

        assertEquals(initialCount - 1, shelter.getAnimals().size(), "Animals list should have one less animal.");
        assertFalse(shelter.getAnimals().contains(dog1), "The removed animal should no longer be in the list.");
        assertTrue(shelter.getAnimals().contains(cat), "Other animals should remain in the list.");
    }



}

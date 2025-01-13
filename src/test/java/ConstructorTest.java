import com.animalshelter.Cat;
import com.animalshelter.Dog;
import com.animalshelter.Lizard;
import com.animalshelter.Rabbit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConstructorTest {

    @Test
    void testDogConstructor() {
        Dog dog = new Dog("Buddy", 3, "Male", 15.0f);
        assertAll(
                () -> assertNotNull(dog, "Dog object should not be null."),
                () -> assertEquals("Buddy", dog.getName(), "Dog name should be 'Buddy'."),
                () -> assertEquals(3, dog.getAge(), "Dog age should be 3."),
                () -> assertEquals("Male", dog.getSex(), "Dog sex should be 'Male'."),
                () -> assertEquals(15.0f, dog.getWeight(), "Dog weight should be 15.0 kg.")
        );
    }

    @Test
    void testCatConstructor() {
        Cat cat = new Cat("Whiskers", 2, "Female", "Black");
        assertAll(
                () -> assertNotNull(cat, "Cat object should not be null."),
                () -> assertEquals("Whiskers", cat.getName(), "Cat name should be 'Whiskers'."),
                () -> assertEquals(2, cat.getAge(), "Cat age should be 2."),
                () -> assertEquals("Female", cat.getSex(), "Cat sex should be 'Female'."),
                () -> assertEquals("Black", cat.getColor(), "Cat color should be 'Black'.")
        );
    }

    @Test
    void testRabbitConstructor() {
        Rabbit rabbit = new Rabbit("Fluffy", 1, "Male", "White");
        assertAll(
                () -> assertNotNull(rabbit, "Rabbit object should not be null."),
                () -> assertEquals("Fluffy", rabbit.getName(), "Rabbit name should be 'Fluffy'."),
                () -> assertEquals(1, rabbit.getAge(), "Rabbit age should be 1."),
                () -> assertEquals("Male", rabbit.getSex(), "Rabbit sex should be 'Male'."),
                () -> assertEquals("White", rabbit.getColor(), "Rabbit color should be 'White'.")
        );
    }

    @Test
    void testLizardConstructor() {
        Lizard lizard = new Lizard("Scaly", 4, "Female", true);
        assertAll(
                () -> assertNotNull(lizard, "Lizard object should not be null."),
                () -> assertEquals("Scaly", lizard.getName(), "Lizard name should be 'Scaly'."),
                () -> assertEquals(4, lizard.getAge(), "Lizard age should be 4."),
                () -> assertEquals("Female", lizard.getSex(), "Lizard sex should be 'Female'."),
                () -> assertTrue(lizard.isPoisonous(), "Lizard should be poisonous.")
        );
    }
}

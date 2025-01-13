import com.animalshelter.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    void testIsValidWeight() {
        assertTrue(Validator.isValidWeight("10"), "Positive numbers should be valid weight.");
        assertTrue(Validator.isValidWeight("10.5"), "Decimal numbers should be valid weight.");
        assertFalse(Validator.isValidWeight("-5"), "Negative numbers should not be valid weight.");
        assertFalse(Validator.isValidWeight("abc"), "Non-numeric strings should not be valid weight.");
        assertFalse(Validator.isValidWeight(""), "Empty string should not be valid weight.");
        assertFalse(Validator.isValidWeight(null), "Null input should not be valid weight.");
    }

    @Test
    void testIsValidColor() {
        assertTrue(Validator.isValidColor("Black"), "Non-empty strings should be valid color.");
        assertTrue(Validator.isValidColor("Blue"), "Non-empty strings should be valid color.");
        assertFalse(Validator.isValidColor(""), "Empty strings should not be valid color.");
        assertFalse(Validator.isValidColor(null), "Null input should not be valid color.");
    }

    @Test
    void testIsValidPoisonous() {
        assertTrue(Validator.isValidPoisonous("true"), "'true' should be valid poisonous input.");
        assertTrue(Validator.isValidPoisonous("false"), "'false' should be valid poisonous input.");
        assertTrue(Validator.isValidPoisonous("yes"), "'yes' should be valid poisonous input.");
        assertTrue(Validator.isValidPoisonous("no"), "'no' should be valid poisonous input.");
        assertTrue(Validator.isValidPoisonous("1"), "'1' should be valid poisonous input.");
        assertTrue(Validator.isValidPoisonous("0"), "'0' should be valid poisonous input.");
        assertFalse(Validator.isValidPoisonous("maybe"), "'maybe' should not be valid poisonous input.");
        assertFalse(Validator.isValidPoisonous(""), "Empty strings should not be valid poisonous input.");
        assertFalse(Validator.isValidPoisonous(null), "Null input should not be valid poisonous input.");
    }

    @Test
    void testParsePoisonous() {
        assertTrue(Validator.parsePoisonous("true"), "'true' should be parsed as true.");
        assertTrue(Validator.parsePoisonous("yes"), "'yes' should be parsed as true.");
        assertTrue(Validator.parsePoisonous("1"), "'1' should be parsed as true.");
        assertFalse(Validator.parsePoisonous("false"), "'false' should be parsed as false.");
        assertFalse(Validator.parsePoisonous("no"), "'no' should be parsed as false.");
        assertFalse(Validator.parsePoisonous("0"), "'0' should be parsed as false.");
    }
}

package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WeddingNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Passing null to the constructor should throw a NullPointerException
        assertThrows(NullPointerException.class, () -> new WeddingName(null));
    }

    @Test
    public void constructor_invalidWeddingName_throwsIllegalArgumentException() {
        // A name that is only whitespace is invalid
        String invalidName = "   ";
        assertThrows(IllegalArgumentException.class, () -> new WeddingName(invalidName));

        // An empty string is also invalid
        String emptyName = "";
        assertThrows(IllegalArgumentException.class, () -> new WeddingName(emptyName));
    }

    @Test
    public void isValidName_null_throwsNullPointerException() {
        // The static method should throw if passed null
        assertThrows(NullPointerException.class, () -> WeddingName.isValidName(null));
    }

    @Test
    public void isValidName_invalidFormats_returnsFalse() {
        // Empty string
        assertFalse(WeddingName.isValidName(""), "Expected empty string to be invalid");

        // Whitespace only
        assertFalse(WeddingName.isValidName("   "), "Expected whitespace-only string to be invalid");
    }

    @Test
    public void isValidName_validFormats_returnsTrue() {
        // Single word
        assertTrue(WeddingName.isValidName("Wedding"), "Expected single word to be valid");

        // Multiple words, plus symbols
        assertTrue(WeddingName.isValidName("Alice & Bob Wedding"),
                "Expected multiple words with '&' to be valid");

        // Contains punctuation or special characters
        assertTrue(WeddingName.isValidName("John's Wedding #1"),
                "Expected name with punctuation to be valid");
    }

    @Test
    public void toString_sameValue_correctString() {
        WeddingName name = new WeddingName("Alice & Bob Wedding");
        assertEquals("Alice & Bob Wedding", name.toString(),
                "toString() should return the exact name string");
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        WeddingName name1 = new WeddingName("Central Park Wedding");
        WeddingName name2 = new WeddingName("Central Park Wedding");

        // Both have the same name string
        assertEquals(name1, name2, "Names with the same string should be equal");
        assertEquals(name1.hashCode(), name2.hashCode(),
                "Equal objects must have the same hash code");
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        WeddingName name1 = new WeddingName("Beach Wedding");
        WeddingName name2 = new WeddingName("Mountain Wedding");
        assertNotEquals(name1, name2, "Names with different strings should not be equal");
    }

    @Test
    public void equals_differentType_returnsFalse() {
        WeddingName name = new WeddingName("Beach Wedding");
        assertNotEquals(name, "some string", "Comparing to a different object type should return false");
    }

    @Test
    public void equals_null_returnsFalse() {
        WeddingName name = new WeddingName("Beach Wedding");
        assertNotEquals(name, null, "Comparing to null should return false");
    }
}

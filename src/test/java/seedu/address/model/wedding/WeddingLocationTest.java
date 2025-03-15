package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WeddingLocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Passing null to the constructor should throw a NullPointerException
        assertThrows(NullPointerException.class, () -> new WeddingLocation(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        // A location that is only whitespace is invalid
        String invalidLocation = "   ";
        assertThrows(IllegalArgumentException.class, () -> new WeddingLocation(invalidLocation));

        // An empty string is also invalid
        String emptyLocation = "";
        assertThrows(IllegalArgumentException.class, () -> new WeddingLocation(emptyLocation));
    }

    @Test
    public void isValidLocation_null_throwsNullPointerException() {
        // The static method should throw if passed null
        assertThrows(NullPointerException.class, () -> WeddingLocation.isValidLocation(null));
    }

    @Test
    public void isValidLocation_invalidFormats_returnsFalse() {
        // Empty string
        assertFalse(WeddingLocation.isValidLocation(""), "Expected empty string to be invalid");

        // Whitespace only
        assertFalse(WeddingLocation.isValidLocation("   "), "Expected whitespace-only string to be invalid");

        // Possibly add other invalid patterns if your logic requires them
    }

    @Test
    public void isValidLocation_validFormats_returnsTrue() {
        // Single word
        assertTrue(WeddingLocation.isValidLocation("Beach"), "Expected single word to be valid");

        // Multiple words
        assertTrue(WeddingLocation.isValidLocation("Hotel Ballroom"),
                "Expected multiple words to be valid");

        // Mixed with punctuation
        assertTrue(WeddingLocation.isValidLocation("Grand Ballroom #1"),
                "Expected location with punctuation to be valid");

        // Leading/trailing spaces are handled by the caller (if you trim input).
        // But if your design allows direct user input here, consider testing:
        // e.g., "  Beach  " => If not trimmed externally, this might pass or fail depending on your usage.
    }

    @Test
    public void toString_sameValue_correctString() {
        WeddingLocation location = new WeddingLocation("Beach Resort");
        assertEquals("Beach Resort", location.toString(),
                "toString() should return the exact location string");
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        WeddingLocation loc1 = new WeddingLocation("Central Park");
        WeddingLocation loc2 = new WeddingLocation("Central Park");

        // Both have the same venue string
        assertEquals(loc1, loc2, "Locations with the same string should be equal");
        assertEquals(loc1.hashCode(), loc2.hashCode(),
                "Equal objects must have the same hash code");
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        WeddingLocation loc1 = new WeddingLocation("Beach");
        WeddingLocation loc2 = new WeddingLocation("Mountain");

        // Different venue strings => not equal
        assertNotEquals(loc1, loc2, "Locations with different strings should not be equal");
    }

    @Test
    public void equals_differentType_returnsFalse() {
        WeddingLocation loc = new WeddingLocation("Beach");
        assertNotEquals(loc, "some string", "Comparing to a different object type should return false");
    }

    @Test
    public void equals_null_returnsFalse() {
        WeddingLocation loc = new WeddingLocation("Beach");
        assertNotEquals(loc, null, "Comparing to null should return false");
    }
}


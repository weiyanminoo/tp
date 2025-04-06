package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WeddingIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WeddingId(null));
    }

    @Test
    public void constructor_invalidWeddingId_throwsIllegalArgumentException() {
        // Must start with 'W' followed by digits.
        // For example, "WABC" or "1W5" is invalid.
        String invalidId = "Wabc";
        assertThrows(IllegalArgumentException.class, () -> new WeddingId(invalidId));
    }

    @Test
    public void isValidWeddingId_nullInput_returnsFalse() {
        assertFalse(WeddingId.isValidWeddingId(null));
    }

    @Test
    public void isValidWeddingId_invalidFormats_returnsFalse() {
        // Missing 'W'
        assertFalse(WeddingId.isValidWeddingId("1"));
        // Lowercase 'w'
        assertFalse(WeddingId.isValidWeddingId("w5"));
        // Letters after W
        assertFalse(WeddingId.isValidWeddingId("Wabc"));
        // No digits after W
        assertFalse(WeddingId.isValidWeddingId("W"));
        // Empty string
        assertFalse(WeddingId.isValidWeddingId(""));
        // Whitespace only
        assertFalse(WeddingId.isValidWeddingId("  "));
        // Negative number
        assertFalse(WeddingId.isValidWeddingId("W-1"));
        // Contains special characters
        assertFalse(WeddingId.isValidWeddingId("W@1"));
    }

    @Test
    public void isValidWeddingId_validFormats_returnsTrue() {
        // Single digit
        assertTrue(WeddingId.isValidWeddingId("W1"));
        // Multiple digits
        assertTrue(WeddingId.isValidWeddingId("W42"));
        // Large number
        assertTrue(WeddingId.isValidWeddingId("W9999"));
    }

    @Test
    public void toString_sameValue_equals() {
        WeddingId id = new WeddingId("W10");
        assertEquals("W10", id.toString());
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        WeddingId id1 = new WeddingId("W10");
        WeddingId id2 = new WeddingId("W10");
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        WeddingId id1 = new WeddingId("W10");
        WeddingId id2 = new WeddingId("W11");
        assertNotEquals(id1, id2);
    }
}

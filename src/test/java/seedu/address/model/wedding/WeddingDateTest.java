package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WeddingDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Passing null to the constructor should throw a NullPointerException
        assertThrows(NullPointerException.class, () -> new WeddingDate(null));
    }

    @Test
    public void constructor_invalidWeddingDate_throwsIllegalArgumentException() {
        // Must match "DD-MMM-YYYY", e.g. "20-Dec-2025"
        // For instance, "2025-12-01" does NOT match, so it's invalid
        String invalidDate = "2025-12-01";
        assertThrows(IllegalArgumentException.class, () -> new WeddingDate(invalidDate));
    }

    @Test
    public void isValidDate_null_throwsNullPointerException() {
        // The static method should throw if passed null
        assertThrows(NullPointerException.class, () -> WeddingDate.isValidDate(null));
    }

    @Test
    public void isValidDate_invalidFormats_returnsFalse() {
        // Empty string
        assertFalse(WeddingDate.isValidDate(""));
        // Whitespace only
        assertFalse(WeddingDate.isValidDate("   "));

        // Wrong order or missing dashes
        assertFalse(WeddingDate.isValidDate("2025-Dec-20"));
        assertFalse(WeddingDate.isValidDate("20-Dec2025"));
        assertFalse(WeddingDate.isValidDate("20/Dec/2025"));

        // Month not exactly 3 letters
        assertFalse(WeddingDate.isValidDate("20-December-2025"));
        assertFalse(WeddingDate.isValidDate("20-De-2025"));

        // Year not 4 digits
        assertFalse(WeddingDate.isValidDate("20-Dec-25"));
        assertFalse(WeddingDate.isValidDate("20-Dec-202"));
    }

    @Test
    public void isValidDate_validFormats_returnsTrue() {
        // Single-digit day
        assertTrue(WeddingDate.isValidDate("1-Jan-2025"));
        // Double-digit day
        assertTrue(WeddingDate.isValidDate("20-Dec-2025"));
        // Edge-case examples
        assertTrue(WeddingDate.isValidDate("31-Jan-9999"));
        // Mixed-case month is allowed by [A-Za-z]{3}
        assertTrue(WeddingDate.isValidDate("10-dEc-2025"));
    }

    @Test
    public void toString_sameValue_equals() {
        WeddingDate date = new WeddingDate("20-Dec-2025");
        assertEquals("20-Dec-2025", date.toString());
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        WeddingDate date1 = new WeddingDate("20-Dec-2025");
        WeddingDate date2 = new WeddingDate("20-Dec-2025");
        assertEquals(date1, date2);
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        WeddingDate date1 = new WeddingDate("20-Dec-2025");
        WeddingDate date2 = new WeddingDate("21-Dec-2025");
        assertNotEquals(date1, date2);
    }
}

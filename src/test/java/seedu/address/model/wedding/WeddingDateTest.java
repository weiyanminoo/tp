package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class WeddingDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Passing null to the constructor should throw a NullPointerException
        assertThrows(NullPointerException.class, () -> new WeddingDate(null));
    }

    @Test
    public void constructor_invalidWeddingDate_throwsParseException() {
        // Date should not be in the past
        // For instance, "2024-12-01" is in the past, so it is invalid
        String invalidDate = "2024-12-01";
        assertThrows(ParseException.class, () -> new WeddingDate(invalidDate));
    }

    @Test
    public void isValidDate_null_throwsNullPointerException() {
        // The static method should throw if passed null
        assertThrows(NullPointerException.class, () -> WeddingDate.isValidDate(null));
    }

    @Test
    public void isValidDate_validFormats_returnsTrue() throws ParseException {
        assertTrue(WeddingDate.isValidDate("01-Jan-2026"));
        assertTrue(WeddingDate.isValidDate("01/Jan/2026"));
        assertTrue(WeddingDate.isValidDate("01.Jan.2026"));
        assertTrue(WeddingDate.isValidDate("01 Jan 2026"));

        assertTrue(WeddingDate.isValidDate("2026-01-01"));
        assertTrue(WeddingDate.isValidDate("2026/01/01"));
        assertTrue(WeddingDate.isValidDate("2026.01.01"));
        assertTrue(WeddingDate.isValidDate("2026 01 01"));

        assertTrue(WeddingDate.isValidDate("2026-Jan-01"));
        assertTrue(WeddingDate.isValidDate("2026/Jan/01"));
        assertTrue(WeddingDate.isValidDate("2026.Jan.01"));
        assertTrue(WeddingDate.isValidDate("2026 Jan 01"));
        assertTrue(WeddingDate.isValidDate("2026Jan01"));

        assertTrue(WeddingDate.isValidDate("2026-January-01"));
        assertTrue(WeddingDate.isValidDate("2026/January/01"));
        assertTrue(WeddingDate.isValidDate("2026.January.01"));
        assertTrue(WeddingDate.isValidDate("2026 January 01"));
        assertTrue(WeddingDate.isValidDate("2026January01"));

        assertTrue(WeddingDate.isValidDate("01-2026-01"));
        assertTrue(WeddingDate.isValidDate("01/2026/01"));
        assertTrue(WeddingDate.isValidDate("01.2026.01"));
        assertTrue(WeddingDate.isValidDate("01 2026 01"));

        assertTrue(WeddingDate.isValidDate("01-01-2026"));
        assertTrue(WeddingDate.isValidDate("01/01/2026"));
        assertTrue(WeddingDate.isValidDate("01.01.2026"));
        assertTrue(WeddingDate.isValidDate("01 01 2026"));
        assertTrue(WeddingDate.isValidDate("01012026"));

        assertTrue(WeddingDate.isValidDate("01-Jan-2026"));
        assertTrue(WeddingDate.isValidDate("01/Jan/2026"));
        assertTrue(WeddingDate.isValidDate("01.Jan.2026"));
        assertTrue(WeddingDate.isValidDate("01 Jan 2026"));
        assertTrue(WeddingDate.isValidDate("01Jan2026"));

        assertTrue(WeddingDate.isValidDate("01-January-2026"));
        assertTrue(WeddingDate.isValidDate("01/January/2026"));
        assertTrue(WeddingDate.isValidDate("01.January.2026"));
        assertTrue(WeddingDate.isValidDate("01 January 2026"));
        assertTrue(WeddingDate.isValidDate("01January2026"));
    }

    @Test
    public void toString_sameValue_equals() throws ParseException {
        WeddingDate date = new WeddingDate("20-Dec-2025");
        assertEquals("20-Dec-2025", date.toString());
    }

    @Test
    public void equals_sameValue_returnsTrue() throws ParseException {
        WeddingDate date1 = new WeddingDate("20-Dec-2025");
        WeddingDate date2 = new WeddingDate("20-Dec-2025");
        assertEquals(date1, date2);
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void equals_differentValue_returnsFalse() throws ParseException {
        WeddingDate date1 = new WeddingDate("20-Dec-2025");
        WeddingDate date2 = new WeddingDate("21-Dec-2025");
        assertNotEquals(date1, date2);
    }


    @Test
    public void isValidDate_pastDate_throwsParseException() {
        String pastDate = "2020-01-01";
        assertThrows(ParseException.class, () -> WeddingDate.isValidDate(pastDate));
    }
}

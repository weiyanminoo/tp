package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WeddingTest {

    private static final String VALID_ID = "W001";
    private static final String VALID_NAME = "John & Jane Wedding";
    private static final String VALID_DATE = "2025-06-15";
    private static final String VALID_LOCATION = "Central Park";

    private static final String OTHER_ID = "W002";
    private static final String OTHER_NAME = "Alice & Bob Wedding";
    private static final String OTHER_DATE = "2025-09-10";
    private static final String OTHER_LOCATION = "Beachside Resort";

    @Test
    public void constructor_allFields_success() {
        Wedding wedding = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertEquals(VALID_ID, wedding.getWeddingId());
        assertEquals(VALID_NAME, wedding.getWeddingName());
        assertEquals(VALID_DATE, wedding.getWeddingDate());
        assertEquals(VALID_LOCATION, wedding.getLocation());
    }

    // ========================== isSameWedding ==========================
    @Test
    public void isSameWedding_nullWedding_returnsFalse() {
        Wedding wedding = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertFalse(wedding.isSameWedding(null));
    }

    @Test
    public void isSameWedding_sameObject_returnsTrue() {
        Wedding wedding = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertTrue(wedding.isSameWedding(wedding));
    }

    @Test
    public void isSameWedding_differentId_returnsFalse() {
        Wedding weddingA = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        Wedding weddingB = new Wedding(OTHER_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertFalse(weddingA.isSameWedding(weddingB));
    }

    @Test
    public void isSameWedding_sameIdDifferentDetails_returnsTrue() {
        Wedding weddingA = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        // Same ID, but name/date/location differ
        Wedding weddingB = new Wedding(VALID_ID, OTHER_NAME, OTHER_DATE, OTHER_LOCATION);
        assertTrue(weddingA.isSameWedding(weddingB));
    }

    // ========================== equals ==========================
    @Test
    public void equals_sameObject_returnsTrue() {
        Wedding wedding = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertTrue(wedding.equals(wedding));
    }

    @Test
    public void equals_null_returnsFalse() {
        Wedding wedding = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertFalse(wedding.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Wedding wedding = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertFalse(wedding.equals(5)); // different type
    }

    @Test
    public void equals_differentId_returnsFalse() {
        Wedding weddingA = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        Wedding weddingB = new Wedding(OTHER_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertFalse(weddingA.equals(weddingB));
    }

    @Test
    public void equals_differentDetailsSameId_returnsFalse() {
        // The equals method checks all fields, so if name/date/location differ, it returns false
        Wedding weddingA = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        Wedding weddingB = new Wedding(VALID_ID, OTHER_NAME, OTHER_DATE, OTHER_LOCATION);
        assertFalse(weddingA.equals(weddingB));
    }

    @Test
    public void equals_allFieldsSame_returnsTrue() {
        Wedding weddingA = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        Wedding weddingB = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertTrue(weddingA.equals(weddingB));
    }

    // ========================== hashCode ==========================
    @Test
    public void hashCode_consistentForEqualObjects() {
        Wedding weddingA = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        Wedding weddingB = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        assertEquals(weddingA.hashCode(), weddingB.hashCode());
    }

    // ========================== toString ==========================
    @Test
    public void toString_correctFormat() {
        Wedding wedding = new Wedding(VALID_ID, VALID_NAME, VALID_DATE, VALID_LOCATION);
        String expected = "Wedding: " + VALID_NAME + " (ID: " + VALID_ID + ") Date: " + VALID_DATE
                + " Location: " + VALID_LOCATION;
        assertEquals(expected, wedding.toString());
    }
}

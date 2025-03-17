package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wedding.Wedding;

public class JsonAdaptedWeddingTest {

    private static final String VALID_WEDDING_ID = "W001";
    private static final String VALID_WEDDING_NAME = "John & Jane Wedding";
    private static final String VALID_WEDDING_DATE = "15-Jun-2026";
    private static final String VALID_LOCATION = "Central Park";

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Wedding's %s field is missing!";

    @Test
    public void toModelType_validWeddingDetails_returnsWedding() throws Exception {
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION);
        Wedding wedding = adaptedWedding.toModelType();

        assertEquals(VALID_WEDDING_ID, wedding.getWeddingId().value);
        assertEquals(VALID_WEDDING_NAME, wedding.getWeddingName().fullWeddingName);
        assertEquals(VALID_WEDDING_DATE, wedding.getWeddingDate().value);
        assertEquals(VALID_LOCATION, wedding.getWeddingLocation().venue);
    }

    @Test
    public void toModelType_nullWeddingId_throwsIllegalValueException() {
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                null, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "weddingId");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedWedding::toModelType);
    }

    @Test
    public void toModelType_nullWeddingName_throwsIllegalValueException() {
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                VALID_WEDDING_ID, null, VALID_WEDDING_DATE, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "weddingName");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedWedding::toModelType);
    }

    @Test
    public void toModelType_nullWeddingDate_throwsIllegalValueException() {
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, null, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "weddingDate");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedWedding::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "location");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedWedding::toModelType);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        JsonAdaptedWedding weddingA = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION);
        JsonAdaptedWedding weddingB = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION);

        // same values -> returns true
        assertEquals(weddingA, weddingB);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        JsonAdaptedWedding weddingA = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION);
        JsonAdaptedWedding weddingB = new JsonAdaptedWedding(
                "W002", VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION);

        // different ID -> returns false
        assertNotEquals(weddingA, weddingB);
    }
}

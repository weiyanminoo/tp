package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wedding.Wedding;

public class JsonAdaptedWeddingTest {

    private static final String VALID_WEDDING_ID = "W1";
    private static final String VALID_WEDDING_NAME = "John & Jane Wedding";
    private static final String VALID_WEDDING_DATE = "15-Jun-2026";
    private static final String VALID_LOCATION = "Central Park";

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Wedding's %s field is missing!";

    private static final List<JsonAdaptedTask> VALID_TASKS;
    static {
        VALID_TASKS = new ArrayList<>();
        VALID_TASKS.add(new JsonAdaptedTask("Book florist", false));
        VALID_TASKS.add(new JsonAdaptedTask("Order wedding cake", true));
    }

    @Test
    public void toModelType_validWeddingDetails_returnsWedding() throws Exception {
        // Wedding with no tasks
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION, null);
        Wedding wedding = adaptedWedding.toModelType();

        assertEquals(VALID_WEDDING_ID, wedding.getWeddingId().value);
        assertEquals(VALID_WEDDING_NAME, wedding.getWeddingName().fullWeddingName);
        assertEquals(VALID_WEDDING_DATE, wedding.getWeddingDate().value);
        assertEquals(VALID_LOCATION, wedding.getWeddingLocation().venue);

        // No tasks were provided, so the wedding's task list should be empty
        assertEquals(0, wedding.getTasks().size());
    }

    /**
     * Tests a scenario where the JSON includes a list of tasks.
     */
    @Test
    public void toModelType_validWeddingDetailsWithTasks_returnsWedding() throws Exception {
        // Wedding with tasks
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION, VALID_TASKS);
        Wedding wedding = adaptedWedding.toModelType();

        // Basic wedding fields
        assertEquals(VALID_WEDDING_ID, wedding.getWeddingId().value);
        assertEquals(VALID_WEDDING_NAME, wedding.getWeddingName().fullWeddingName);
        assertEquals(VALID_WEDDING_DATE, wedding.getWeddingDate().value);
        assertEquals(VALID_LOCATION, wedding.getWeddingLocation().venue);

        // Check tasks
        assertEquals(2, wedding.getTasks().size());

        // First task: "Book florist", not done
        assertEquals("Book florist", wedding.getTasks().get(0).getDescription());
        org.junit.jupiter.api.Assertions.assertFalse(wedding.getTasks().get(0).isDone());

        // Second task: "Order wedding cake", done
        assertEquals("Order wedding cake", wedding.getTasks().get(1).getDescription());
        org.junit.jupiter.api.Assertions.assertTrue(wedding.getTasks().get(1).isDone());
    }

    @Test
    public void toModelType_nullWeddingId_throwsIllegalValueException() {
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                null, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "weddingId");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedWedding::toModelType);
    }

    @Test
    public void toModelType_nullWeddingName_throwsIllegalValueException() {
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                VALID_WEDDING_ID, null, VALID_WEDDING_DATE, VALID_LOCATION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "weddingName");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedWedding::toModelType);
    }

    @Test
    public void toModelType_nullWeddingDate_throwsIllegalValueException() {
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, null, VALID_LOCATION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "weddingDate");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedWedding::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedWedding adaptedWedding = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "location");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedWedding::toModelType);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        JsonAdaptedWedding weddingA = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION, null);
        JsonAdaptedWedding weddingB = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION, null);

        // same values -> returns true
        assertEquals(weddingA, weddingB);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        JsonAdaptedWedding weddingA = new JsonAdaptedWedding(
                VALID_WEDDING_ID, VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION, null);
        JsonAdaptedWedding weddingB = new JsonAdaptedWedding(
                "W2", VALID_WEDDING_NAME, VALID_WEDDING_DATE, VALID_LOCATION, null);

        // different ID -> returns false
        assertNotEquals(weddingA, weddingB);
    }
}

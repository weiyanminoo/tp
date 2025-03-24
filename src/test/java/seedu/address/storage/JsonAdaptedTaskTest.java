package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wedding.WeddingTask;

/**
 * Contains unit tests for {@code JsonAdaptedTask}.
 */
public class JsonAdaptedTaskTest {

    private static final String VALID_DESCRIPTION = "Buy flowers";
    private static final boolean VALID_IS_DONE = true;

    private static final String INVALID_DESCRIPTION_EMPTY = "";
    private static final String INVALID_DESCRIPTION_SPACES = "   ";

    @Test
    public void constructor_fromWeddingTask_success() throws IllegalValueException {
        // Given a real WeddingTask
        WeddingTask sourceTask = new WeddingTask(VALID_DESCRIPTION);
        sourceTask.markAsDone(); // now it's done

        // When we construct a JsonAdaptedTask from it
        JsonAdaptedTask adapted = new JsonAdaptedTask(sourceTask);

        // Then the fields should match
        // (We can't access adapted.description/isDone directly if they're private,
        //  but we can call toModelType() to check.)
        assertEquals(VALID_DESCRIPTION, adapted.toModelType().getDescription());
        assertTrue(adapted.toModelType().isDone());
    }

    @Test
    public void toModelType_validInputs_returnsWeddingTask() throws Exception {
        // Construct directly with string + boolean
        JsonAdaptedTask adapted = new JsonAdaptedTask(VALID_DESCRIPTION, VALID_IS_DONE);

        // Convert to model
        WeddingTask modelTask = adapted.toModelType();

        // Check fields
        assertEquals(VALID_DESCRIPTION, modelTask.getDescription());
        assertTrue(modelTask.isDone());
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        // If description is null, we expect an IllegalValueException
        JsonAdaptedTask adapted = new JsonAdaptedTask(null, VALID_IS_DONE);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_emptyDescription_throwsIllegalValueException() {
        // Empty string
        JsonAdaptedTask adaptedEmpty = new JsonAdaptedTask(INVALID_DESCRIPTION_EMPTY, VALID_IS_DONE);
        assertThrows(IllegalValueException.class, adaptedEmpty::toModelType);

        // Only whitespace
        JsonAdaptedTask adaptedSpaces = new JsonAdaptedTask(INVALID_DESCRIPTION_SPACES, VALID_IS_DONE);
        assertThrows(IllegalValueException.class, adaptedSpaces::toModelType);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        // Two JsonAdaptedTask objects with the same fields
        JsonAdaptedTask taskA = new JsonAdaptedTask(VALID_DESCRIPTION, VALID_IS_DONE);
        JsonAdaptedTask taskB = new JsonAdaptedTask(VALID_DESCRIPTION, VALID_IS_DONE);

        assertEquals(taskA, taskB);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        // Different description
        JsonAdaptedTask taskA = new JsonAdaptedTask(VALID_DESCRIPTION, VALID_IS_DONE);
        JsonAdaptedTask taskB = new JsonAdaptedTask("Another desc", VALID_IS_DONE);
        assertNotEquals(taskA, taskB);

        // Different isDone status
        JsonAdaptedTask taskC = new JsonAdaptedTask(VALID_DESCRIPTION, !VALID_IS_DONE);
        assertNotEquals(taskA, taskC);

        // Null
        assertFalse(taskA.equals(null));

        // Different type
        assertFalse(taskA.equals("string"));
    }
}
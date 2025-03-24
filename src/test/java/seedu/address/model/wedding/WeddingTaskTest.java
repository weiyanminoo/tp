package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for {@code WeddingTask}.
 */
public class WeddingTaskTest {

    private static final String DESCRIPTION = "Buy flowers";

    @Test
    public void constructor_validDescription_isDoneFalse() {
        WeddingTask task = new WeddingTask(DESCRIPTION);
        // The task should store the description and be not done by default
        assertEquals(DESCRIPTION, task.getDescription());
        assertFalse(task.isDone(), "Newly created task should not be done by default");
    }

    @Test
    public void markAsDone_setsIsDoneTrue() {
        WeddingTask task = new WeddingTask(DESCRIPTION);
        task.markAsDone();
        assertTrue(task.isDone(), "Task should be marked as done after calling markAsDone()");
    }

    @Test
    public void unmark_setsIsDoneFalse() {
        WeddingTask task = new WeddingTask(DESCRIPTION);
        task.markAsDone();
        // Now unmark it
        task.unmark();
        assertFalse(task.isDone(), "Task should be marked as not done after calling unmark()");
    }

    @Test
    public void toString_notDone_showsEmptyBracket() {
        WeddingTask task = new WeddingTask(DESCRIPTION);
        String expected = "[ ] " + DESCRIPTION;
        assertEquals(expected, task.toString());
    }

    @Test
    public void toString_done_showsXBracket() {
        WeddingTask task = new WeddingTask(DESCRIPTION);
        task.markAsDone();
        String expected = "[X] " + DESCRIPTION;
        assertEquals(expected, task.toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        WeddingTask task = new WeddingTask(DESCRIPTION);
        assertTrue(task.equals(task), "A task should be equal to itself");
    }

    @Test
    public void equals_null_returnsFalse() {
        WeddingTask task = new WeddingTask(DESCRIPTION);
        assertFalse(task.equals(null), "A task should not be equal to null");
    }

    @Test
    public void equals_differentType_returnsFalse() {
        WeddingTask task = new WeddingTask(DESCRIPTION);
        assertFalse(task.equals("some string"), "A task should not be equal to an unrelated type");
    }

    @Test
    public void equals_sameDescriptionSameStatus_returnsTrue() {
        WeddingTask task1 = new WeddingTask(DESCRIPTION);
        WeddingTask task2 = new WeddingTask(DESCRIPTION);

        // Both not done initially
        assertTrue(task1.equals(task2), "Tasks with same description and status should be equal");
        // Mark both as done => still equal
        task1.markAsDone();
        task2.markAsDone();
        assertTrue(task1.equals(task2));
    }

    @Test
    public void equals_differentDescription_returnsFalse() {
        WeddingTask task1 = new WeddingTask("Buy flowers");
        WeddingTask task2 = new WeddingTask("Buy cake");
        assertFalse(task1.equals(task2), "Tasks with different descriptions should not be equal");
    }

    @Test
    public void equals_differentStatus_returnsFalse() {
        WeddingTask task1 = new WeddingTask(DESCRIPTION);
        WeddingTask task2 = new WeddingTask(DESCRIPTION);

        // Mark only one
        task1.markAsDone();
        assertFalse(task1.equals(task2), "Tasks with different statuses should not be equal");
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        WeddingTask task1 = new WeddingTask(DESCRIPTION);
        WeddingTask task2 = new WeddingTask(DESCRIPTION);
        assertEquals(task1.hashCode(), task2.hashCode(),
                "Tasks with same description and status should have the same hash code");

        // Mark both as done
        task1.markAsDone();
        task2.markAsDone();
        assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        WeddingTask task1 = new WeddingTask(DESCRIPTION);
        WeddingTask task2 = new WeddingTask("Another task");
        assertNotEquals(task1.hashCode(), task2.hashCode(),
                "Tasks with different descriptions should have different hash codes");

        // Mark only one as done
        task2.markAsDone();
        assertNotEquals(task1.hashCode(), task2.hashCode(),
                "Tasks with different statuses should also have different hash codes");
    }
}

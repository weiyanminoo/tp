package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;
import seedu.address.model.wedding.WeddingTask;

/**
 * Contains unit tests for {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {

    private static final WeddingId VALID_WEDDING_ID = new WeddingId("W1");
    private static final WeddingId INVALID_WEDDING_ID = new WeddingId("W999");

    @Test
    public void execute_validWeddingId_success() throws Exception {
        // Model stub with one wedding that has ID W1 and two tasks: "Task 1" and "Task 2"
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(VALID_WEDDING_ID);

        // Attempt to delete the second task (1-based index = 2)
        DeleteTaskCommand command = new DeleteTaskCommand(VALID_WEDDING_ID, 2);
        CommandResult result = command.execute(modelStub);

        // The second task is "[ ] Task 2" (not done by default)
        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS,
                VALID_WEDDING_ID.value, "[ ] Task 2");
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Only one task should remain
        Wedding wedding = modelStub.getFilteredWeddingList().get(0);
        assertEquals(1, wedding.getTasks().size());
        assertEquals("Task 1", wedding.getTasks().get(0).getDescription());
    }

    @Test
    public void execute_weddingNotFound_throwsCommandException() {
        // Model stub with wedding ID "W2" instead of W999
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(new WeddingId("W2"));
        DeleteTaskCommand command = new DeleteTaskCommand(INVALID_WEDDING_ID, 1);

        assertThrows(CommandException.class,
                String.format(DeleteTaskCommand.MESSAGE_WEDDING_NOT_FOUND, INVALID_WEDDING_ID.value),
                () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Model stub has 2 tasks, so index 3 is invalid
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(VALID_WEDDING_ID);
        DeleteTaskCommand command = new DeleteTaskCommand(VALID_WEDDING_ID, 3);

        assertThrows(CommandException.class,
                String.format(DeleteTaskCommand.MESSAGE_INVALID_TASK_INDEX, VALID_WEDDING_ID.value),
                () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        DeleteTaskCommand deleteTaskCommandA = new DeleteTaskCommand(VALID_WEDDING_ID, 1);
        DeleteTaskCommand deleteTaskCommandB = new DeleteTaskCommand(VALID_WEDDING_ID, 2);
        DeleteTaskCommand deleteTaskCommandC = new DeleteTaskCommand(new WeddingId("W2"), 1);

        // same object -> returns true
        assertTrue(deleteTaskCommandA.equals(deleteTaskCommandA));

        // same values -> returns true
        DeleteTaskCommand deleteTaskCommandACopy = new DeleteTaskCommand(VALID_WEDDING_ID, 1);
        assertTrue(deleteTaskCommandA.equals(deleteTaskCommandACopy));

        // different types -> returns false
        assertFalse(deleteTaskCommandA.equals(1));

        // null -> returns false
        assertFalse(deleteTaskCommandA.equals(null));

        // different task index -> returns false
        assertFalse(deleteTaskCommandA.equals(deleteTaskCommandB));

        // different wedding ID -> returns false
        assertFalse(deleteTaskCommandA.equals(deleteTaskCommandC));
    }

    // ========== MODEL STUBS ==========

    /**
     * A Model stub that contains exactly one wedding with the given ID
     * and two tasks: "Task 1" and "Task 2".
     */
    private class ModelStubWithOneWedding extends ModelStub {
        private final ObservableList<Wedding> internalWeddingList = FXCollections.observableArrayList();

        ModelStubWithOneWedding(WeddingId weddingId) {
            Wedding singleWedding = new Wedding(
                    weddingId,
                    new WeddingName("Stub Wedding"),
                    new WeddingDate("01-Jan-2025"),
                    new WeddingLocation("Stub Location"),
                    true
            );
            singleWedding.addTask(new WeddingTask("Task 1"));
            singleWedding.addTask(new WeddingTask("Task 2"));

            internalWeddingList.add(singleWedding);
        }

        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            return internalWeddingList;
        }
    }
}
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;
import seedu.address.model.wedding.WeddingTask;

/**
 * Contains unit tests for {@code AddTaskCommand}.
 */
public class AddTaskCommandTest {

    private static final WeddingId VALID_WEDDING_ID = new WeddingId("W1");
    private static final WeddingId INVALID_WEDDING_ID = new WeddingId("W999");
    private static final String VALID_DESCRIPTION = "Book photographer";

    @Test
    public void execute_validWeddingId_success() throws Exception {
        // Stub with a wedding that has ID W1 (and 1 initial task for variety)
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(VALID_WEDDING_ID, 1);

        AddTaskCommand command = new AddTaskCommand(VALID_WEDDING_ID, VALID_DESCRIPTION);
        CommandResult result = command.execute(modelStub);

        // The new task is "[ ] Book photographer"
        String expectedMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, VALID_WEDDING_ID.value,
                "[ ] " + VALID_DESCRIPTION);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Check the wedding's task list has 2 tasks now
        Wedding wedding = modelStub.internalWeddingList.get(0);
        assertEquals(2, wedding.getTasks().size());
        assertEquals(VALID_DESCRIPTION, wedding.getTasks().get(1).getDescription());
    }

    @Test
    public void execute_weddingNotFound_throwsCommandException() {
        // Stub with wedding ID = W2, not W999
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(new WeddingId("W2"), 1);
        AddTaskCommand command = new AddTaskCommand(INVALID_WEDDING_ID, VALID_DESCRIPTION);

        assertThrows(CommandException.class,
                String.format(AddTaskCommand.MESSAGE_WEDDING_NOT_FOUND, INVALID_WEDDING_ID.value), () ->
                        command.execute(modelStub));
    }

    @Test
    public void equals() {
        AddTaskCommand addTaskCommandA = new AddTaskCommand(VALID_WEDDING_ID, "Task A");
        AddTaskCommand addTaskCommandB = new AddTaskCommand(VALID_WEDDING_ID, "Task B");
        AddTaskCommand addTaskCommandC = new AddTaskCommand(new WeddingId("W2"), "Task A");

        // same object -> true
        assertTrue(addTaskCommandA.equals(addTaskCommandA));

        // same values -> true
        AddTaskCommand addTaskCommandACopy = new AddTaskCommand(VALID_WEDDING_ID, "Task A");
        assertTrue(addTaskCommandA.equals(addTaskCommandACopy));

        // different types -> false
        assertFalse(addTaskCommandA.equals(1));

        // null -> false
        assertFalse(addTaskCommandA.equals(null));

        // different description -> false
        assertFalse(addTaskCommandA.equals(addTaskCommandB));

        // different wedding ID -> false
        assertFalse(addTaskCommandA.equals(addTaskCommandC));
    }

    /**
     * A Model stub that contains exactly one wedding with the given ID and
     * an initial number of tasks.
     */
    private class ModelStubWithOneWedding extends ModelStub {
        private final ObservableList<Wedding> internalWeddingList = FXCollections.observableArrayList();

        ModelStubWithOneWedding(WeddingId weddingId, int initialTaskCount) {
            Wedding singleWedding = new Wedding(
                    weddingId,
                    new WeddingName("Stub Wedding"),
                    new WeddingDate("01-Jan-2025"),
                    new WeddingLocation("Stub Location"),
                    true
            );
            // Add some tasks
            for (int i = 1; i <= initialTaskCount; i++) {
                singleWedding.addTask(new WeddingTask("Initial Task " + i));
            }

            internalWeddingList.add(singleWedding);
        }

        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            return internalWeddingList;
        }
    }
}

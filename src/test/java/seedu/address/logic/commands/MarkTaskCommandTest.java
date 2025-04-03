package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;
import seedu.address.model.wedding.WeddingTask;

/**
 * Contains unit tests for {@code MarkTaskCommand}.
 */
public class MarkTaskCommandTest {

    private static final WeddingId VALID_WEDDING_ID = new WeddingId("W1");
    private static final WeddingId INVALID_WEDDING_ID = new WeddingId("W999");

    @Test
    public void execute_validWeddingAndIndex_success() throws Exception {
        // Stub with a wedding that has ID=W1 and 2 tasks
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(VALID_WEDDING_ID, 2);

        // Mark the 2nd task
        MarkTaskCommand command = new MarkTaskCommand(VALID_WEDDING_ID, 2);
        CommandResult result = command.execute(modelStub);

        // The second task was "[ ] Initial Task 2" -> now "[X] Initial Task 2"
        String expectedMessage = "Task marked as done:\n[X] Initial Task 2\nIn Wedding: Stub Wedding";
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Check that the second task is now done
        Wedding wedding = modelStub.internalWeddingList.get(0);
        WeddingTask task = wedding.getTasks().get(1);
        assertTrue(task.isDone());
    }

    @Test
    public void execute_weddingNotFound_throwsCommandException() throws ParseException {
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(new WeddingId("W2"), 2);
        MarkTaskCommand command = new MarkTaskCommand(INVALID_WEDDING_ID, 1);

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_WEDDING_NOT_FOUND, INVALID_WEDDING_ID.value), () ->
                        command.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws ParseException {
        // Only 2 tasks
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(VALID_WEDDING_ID, 2);
        MarkTaskCommand command = new MarkTaskCommand(VALID_WEDDING_ID, 3);

        assertThrows(CommandException.class,
                String.format(MarkTaskCommand.MESSAGE_INVALID_TASK_INDEX, VALID_WEDDING_ID.value), () ->
                        command.execute(modelStub));
    }

    @Test
    public void equals() {
        MarkTaskCommand commandA = new MarkTaskCommand(VALID_WEDDING_ID, 1);
        MarkTaskCommand commandB = new MarkTaskCommand(VALID_WEDDING_ID, 2);
        MarkTaskCommand commandC = new MarkTaskCommand(new WeddingId("W2"), 1);

        // same object -> returns true
        assertTrue(commandA.equals(commandA));

        // same values -> returns true
        MarkTaskCommand commandACopy = new MarkTaskCommand(VALID_WEDDING_ID, 1);
        assertTrue(commandA.equals(commandACopy));

        // different types -> returns false
        assertFalse(commandA.equals(1));

        // null -> returns false
        assertFalse(commandA.equals(null));

        // different index -> returns false
        assertFalse(commandA.equals(commandB));

        // different wedding ID -> returns false
        assertFalse(commandA.equals(commandC));
    }

    private class ModelStubWithOneWedding extends ModelStub {
        private final ObservableList<Wedding> internalWeddingList = FXCollections.observableArrayList();

        ModelStubWithOneWedding(WeddingId weddingId, int taskCount) throws ParseException {
            Wedding singleWedding = new Wedding(
                    weddingId,
                    new WeddingName("Stub Wedding"),
                    new WeddingDate("01-Jan-2026"),
                    new WeddingLocation("Stub Location"),
                    true
            );
            for (int i = 1; i <= taskCount; i++) {
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

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
 * Contains unit tests for {@code ListTaskCommand}.
 */
public class ListTaskCommandTest {

    private static final WeddingId VALID_WEDDING_ID = new WeddingId("W1");
    private static final WeddingId INVALID_WEDDING_ID = new WeddingId("W999");

    @Test
    public void execute_validWeddingWithTasks_success() throws Exception {
        // Stub with a wedding that has ID=W1 and 2 tasks
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(VALID_WEDDING_ID, 2);

        ListTaskCommand command = new ListTaskCommand(VALID_WEDDING_ID);
        CommandResult result = command.execute(modelStub);

        String expectedHeader = "Tasks for Wedding W1:\n";
        String expectedBody = "1. [ ] Initial Task 1\n2. [ ] Initial Task 2";
        String expectedMessage = expectedHeader + expectedBody;

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_validWeddingNoTasks_showsNoTasksMessage() throws Exception {
        // Stub with wedding ID=W1 but 0 tasks
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(VALID_WEDDING_ID, 0);

        ListTaskCommand command = new ListTaskCommand(VALID_WEDDING_ID);
        CommandResult result = command.execute(modelStub);

        // "No tasks found for wedding W1"
        assertEquals("No tasks found for wedding W1", result.getFeedbackToUser());
    }

    @Test
    public void execute_weddingNotFound_throwsCommandException() throws ParseException {
        ModelStubWithOneWedding modelStub = new ModelStubWithOneWedding(new WeddingId("W2"), 2);
        ListTaskCommand command = new ListTaskCommand(INVALID_WEDDING_ID);

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_WEDDING_NOT_FOUND, INVALID_WEDDING_ID.value), (
                ) -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        ListTaskCommand commandA = new ListTaskCommand(VALID_WEDDING_ID);
        ListTaskCommand commandB = new ListTaskCommand(new WeddingId("W2"));

        // same object -> returns true
        assertTrue(commandA.equals(commandA));

        // different wedding ID -> false
        assertFalse(commandA.equals(commandB));

        // different type -> false
        assertFalse(commandA.equals(1));

        // null -> false
        assertFalse(commandA.equals(null));

        // same values -> true
        ListTaskCommand commandACopy = new ListTaskCommand(VALID_WEDDING_ID);
        assertTrue(commandA.equals(commandACopy));
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

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.wedding.Wedding;

/**
 * Unit tests for {@code SortByDateCommand}.
 */
public class SortByWeddingDateCommandTest {

    @Test
    public void execute_sortByDate_success() throws CommandException {
        ModelStubAcceptingSort modelStub = new ModelStubAcceptingSort();
        SortByWeddingDateCommand command = new SortByWeddingDateCommand();
        CommandResult result = command.execute(modelStub);
        assertEquals(SortByWeddingDateCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        // Verify that setSortWeddingsByDate was called with true.
        assertTrue(modelStub.isSortByDateCalled());
    }

    @Test
    public void equals() {
        SortByWeddingDateCommand command1 = new SortByWeddingDateCommand();
        SortByWeddingDateCommand command2 = new SortByWeddingDateCommand();
        // same object -> returns true
        assertTrue(command1.equals(command1));
        // same values -> returns true
        assertTrue(command1.equals(command2));
        // different types -> returns false
        assertFalse(command1.equals("some string"));
        // null -> returns false
        assertFalse(command1.equals(null));
    }

    @Test
    public void hashCode_sameForAllInstances() {
        SortByWeddingDateCommand command1 = new SortByWeddingDateCommand();
        SortByWeddingDateCommand command2 = new SortByWeddingDateCommand();
        assertEquals(command1.hashCode(), command2.hashCode());
    }

    /**
     * A ModelStub that supports sorting, extending the base ModelStub.
     */
    private class ModelStubAcceptingSort extends ModelStub {
        private boolean sortByIdCalled = false;
        private boolean sortByDateCalled = false;

        public boolean isSortByIdCalled() {
            return sortByIdCalled;
        }

        public boolean isSortByDateCalled() {
            return sortByDateCalled;
        }

        @Override
        public void setSortWeddingsById() {
            sortByIdCalled = true;
        }

        @Override
        public void setSortWeddingsByDate(boolean sortByDate) {
            sortByDateCalled = sortByDate;
        }

        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public void updateFilteredWeddingList(Predicate<Wedding> predicate) {

        }
    }
}

package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

public class MainWindowTest {

    @Test
    public void executeCommand_listCommand_success() throws Exception {
        // Create mocks
        LogicStub logic = new LogicStub();

        // Test execution
        CommandResult result = logic.execute("list");

        // Verify result
        assertEquals("Listed all persons", result.getFeedbackToUser());
        assertFalse(result.isShowWeddingList());
    }

    @Test
    public void executeCommand_listWeddingCommand_success() throws Exception {
        // Create mocks
        LogicStub logic = new LogicStub();

        // Test execution
        CommandResult result = logic.execute("listWedding");

        // Verify result
        assertEquals("All wedding events: ", result.getFeedbackToUser());
        assertTrue(result.isShowWeddingList());
    }

    /**
     * A simple stub of Logic used for testing MainWindow logic
     */
    private class LogicStub implements Logic {
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            // Do nothing for test
        }

        @Override
        public AddressBook getAddressBook() {
            // Do nothing for test
            return null;
        }

        @Override
        public CommandResult execute(String commandText) {
            if ("list".equals(commandText)) {
                return new CommandResult("Listed all persons");
            } else if ("listWedding".equals(commandText)) {
                return new CommandResult("All wedding events: ", false, false, true, false);
            }
            return new CommandResult("");
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            return java.nio.file.Paths.get("data", "addressbook.json");
        }
    }
}

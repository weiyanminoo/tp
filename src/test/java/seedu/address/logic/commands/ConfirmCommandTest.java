package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;

public class ConfirmCommandTest {

    /**
     * Sets up a clean ConfirmationManager state before each test ( no pending commands).
     */
    @BeforeEach
    public void setUp() {
        ConfirmationManager.getInstance().clearPendingCommand();
    }

    /**
     * Tests that executing ConfirmCommand when no command is pending throws a CommandException.
     */
    @Test
    public void execute_noPendingCommand_throwsCommandException() {
        ConfirmCommand confirmCommand = new ConfirmCommand();
        ModelStub modelStub = new ModelStub();
        assertThrows(CommandException.class, () -> confirmCommand.execute(modelStub));
    }

    /**
     * Tests that when a pending forceable command is stored, executing ConfirmCommand successfully
     * re-executes the forced version of that command.
     *
     * <p>The test sets a dummy forceable command into the ConfirmationManager, then executes ConfirmCommand,
     * and verifies that the returned CommandResult is as expected and that the pending command is cleared.</p>
     */
    @Test
    public void execute_pendingForceableCommand_success() throws Exception {
        // Create a dummy forceable command that simulates a pending command.
        DummyForceableCommand dummyCommand = new DummyForceableCommand();
        ConfirmationManager.getInstance().setPendingCommand(dummyCommand);

        ConfirmCommand confirmCommand = new ConfirmCommand();
        ModelStub modelStub = new ModelStub();
        CommandResult result = confirmCommand.execute(modelStub);

        // Verify that the forced command's result is returned and pending command is cleared.
        assertEquals("Forced command executed", result.getFeedbackToUser());
        assertTrue(ConfirmationManager.getInstance().getPendingCommand() == null);
    }

    /**
     * A dummy forceable command for testing purposes.
     * <p>
     * This command simulates a command that can be forced.
     * When executed, it returns a CommandResult with a fixed message.
     * </p>
     */
    private class DummyForceableCommand extends Command implements ForceableCommand {
        private boolean executed = false;

        /**
         * Executes this dummy command.
         *
         * @param model the Model (unused in this dummy command).
         * @return a CommandResult with the message "Forced command executed".
         */
        @Override
        public CommandResult execute(Model model) {
            executed = true;
            return new CommandResult("Forced command executed");
        }

        /**
         * Creates and returns a forced version of this command.
         *
         * @return a new instance of DummyForceableCommand representing the forced command.
         */
        @Override
        public ForceableCommand createForceCommand() {
            // For the sake of testing, simply return a new dummy command instance.
            return new DummyForceableCommand();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof DummyForceableCommand)) {
                return false;
            }
            return ((DummyForceableCommand) other).executed == this.executed;
        }
    }

    /**
     * A minimal Model stub that implements Model.
     * <p>
     * All methods throw AssertionError since they are not expected to be called
     * during the execution of ConfirmCommand.
     * </p>
     */
    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void forceAddPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWedding(Wedding target, Wedding editedWedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void tagPerson(Person person, Tag tag) {
            throw new AssertionError("This method should not be called.");
        }
    }
}

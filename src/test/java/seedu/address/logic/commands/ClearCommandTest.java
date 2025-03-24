package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code ClearCommand}.
 */
public class ClearCommandTest {

    /**
     * Tests that executing {@code ClearCommand} on an empty address book immediately returns success.
     */
    @Test
    public void execute_emptyAddressBook_success() {
        AddressBook emptyAddressBook = new AddressBook();
        ModelStubForClear modelStub = new ModelStubForClear(emptyAddressBook);
        ClearCommand clearCommand = new ClearCommand(); // normal mode

        CommandResult result = clearCommand.execute(modelStub);

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        // Contact book should remain empty.
        assertTrue(modelStub.getAddressBook().getPersonList().isEmpty());
    }

    /**
     * Tests that executing {@code ClearCommand} in normal mode when the address book is non-empty
     * returns a confirmation-required result, and the address book is not cleared.
     */
    @Test
    public void execute_nonEmptyAddressBook_returnsConfirmationResult() {
        // Create a contact book with one person.
        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(new PersonBuilder().withName("Test Person").build())
                .build();
        ModelStubForClear modelStub = new ModelStubForClear(addressBook);
        ClearCommand clearCommand = new ClearCommand(); // normal mode

        CommandResult result = clearCommand.execute(modelStub);

        assertEquals(ClearCommand.MESSAGE_CONFIRMATION_REQUIRED, result.getFeedbackToUser());
        assertTrue(result.isRequiresConfirmation());
        // The contact book remains non-empty.
        assertFalse(modelStub.getAddressBook().getPersonList().isEmpty());
    }

    /**
     * Tests that executing {@code ClearCommand} in force mode successfully clears the contact book.
     */
    @Test
    public void execute_forceClear_success() {
        // Create a contact book with one person.
        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(new PersonBuilder().withName("Test Person").build())
                .build();
        ModelStubForClear modelStub = new ModelStubForClear(addressBook);
        ClearCommand clearCommand = new ClearCommand(true); // force mode

        CommandResult result = clearCommand.execute(modelStub);

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        // The contact book should now be empty.
        assertTrue(modelStub.getAddressBook().getPersonList().isEmpty());
    }

    /**
     * Tests that the createForceCommand() method returns a ClearCommand with the force flag set to true.
     */
    @Test
    public void createForceCommand_returnsCommandWithForceTrue() {
        ClearCommand clearCommand = new ClearCommand(); // normal mode
        ForceableCommand forceCommand = clearCommand.createForceCommand();
        assertTrue(forceCommand instanceof ClearCommand);
        ClearCommand forcedClearCommand = (ClearCommand) forceCommand;
        // Compare with a new ClearCommand constructed in force mode.
        ClearCommand expected = new ClearCommand(true);
        assertEquals(expected, forcedClearCommand);
    }

    /**
     * Tests the toString() method for ClearCommand in normal mode.
     */
    @Test
    public void toString_normalMode_returnsExpectedString() {
        ClearCommand clearCommand = new ClearCommand(false);
        String expected = ClearCommand.class.getCanonicalName() + "{isForce=false}";
        assertEquals(expected, clearCommand.toString());
    }

    /**
     * Tests the toString() method for ClearCommand in force mode.
     */
    @Test
    public void toString_forceMode_returnsExpectedString() {
        ClearCommand clearCommand = new ClearCommand(true);
        String expected = ClearCommand.class.getCanonicalName() + "{isForce=true}";
        assertEquals(expected, clearCommand.toString());
    }

    /**
     * Tests the equals method of {@code ClearCommand}.
     */
    @Test
    public void equals() {
        ClearCommand clearNormal1 = new ClearCommand();
        ClearCommand clearNormal2 = new ClearCommand();
        ClearCommand clearForce = new ClearCommand(true);

        // same object -> returns true
        assertTrue(clearNormal1.equals(clearNormal1));

        // same values (normal mode) -> returns true
        assertTrue(clearNormal1.equals(clearNormal2));

        // different types -> returns false
        assertFalse(clearNormal1.equals(1));

        // null -> returns false
        assertFalse(clearNormal1.equals(null));

        // different mode (force vs normal) -> returns false
        assertFalse(clearNormal1.equals(clearForce));
    }

    /**
     * A Model stub that simulates a modifiable AddressBook.
     * <p>
     * This stub holds an {@code AddressBook} instance and implements only the methods used by {@code ClearCommand}.
     * </p>
     */
    private class ModelStubForClear extends ModelStub {
        private AddressBook addressBook;

        ModelStubForClear(AddressBook addressBook) {
            this.addressBook = addressBook;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            // Simulate clearing by replacing the address book with a new empty one.
            this.addressBook = new AddressBook(newData);
        }
    }
}

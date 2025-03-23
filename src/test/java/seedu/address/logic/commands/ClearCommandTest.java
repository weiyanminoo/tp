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
        // Address book should remain empty.
        assertTrue(modelStub.getAddressBook().getPersonList().isEmpty());
    }

    /**
     * Tests that executing {@code ClearCommand} in normal mode when the address book is non-empty
     * returns a confirmation-required result, and the address book is not cleared.
     */
    @Test
    public void execute_nonEmptyAddressBook_returnsConfirmationResult() {
        // Create an AddressBook with one person.
        AddressBook addressBook = new AddressBookBuilder().withPerson(
                new PersonBuilder().withName("Test Person").build()).build();
        ModelStubForClear modelStub = new ModelStubForClear(addressBook);
        ClearCommand clearCommand = new ClearCommand(); // normal mode

        CommandResult result = clearCommand.execute(modelStub);

        assertEquals(ClearCommand.MESSAGE_CONFIRMATION_REQUIRED, result.getFeedbackToUser());
        assertTrue(result.isRequiresConfirmation());
        // The address book remains non-empty.
        assertFalse(modelStub.getAddressBook().getPersonList().isEmpty());
    }

    /**
     * Tests that executing {@code ClearCommand} in force mode successfully clears the address book.
     */
    @Test
    public void execute_forceClear_success() {
        // Create an AddressBook with one person.
        AddressBook addressBook = new AddressBookBuilder().withPerson(
                new PersonBuilder().withName("Test Person").build()).build();
        ModelStubForClear modelStub = new ModelStubForClear(addressBook);
        ClearCommand clearCommand = new ClearCommand(true); // force mode

        CommandResult result = clearCommand.execute(modelStub);

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        // The address book should now be empty.
        assertTrue(modelStub.getAddressBook().getPersonList().isEmpty());
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
     * A default Model stub that has all methods failing.
     * <p>
     * This is used to ensure that only the methods relevant to ClearCommand are called during testing.
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
        public Wedding getWeddingById(WeddingId id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void tagPerson(Person person, Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void untagPerson(Person person, Tag tag) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void removeTagFromAllContacts(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }
    }
}

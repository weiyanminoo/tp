package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddWeddingCommand}.
 */
public class AddWeddingCommandTest {

    @Test
    public void constructor_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddWeddingCommand(null));
    }

    @Test
    public void execute_weddingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingWeddingAdded modelStub = new ModelStubAcceptingWeddingAdded();
        // Create a valid wedding event.
        Wedding validWedding = new Wedding(
                new WeddingName("John & Jane's Wedding"),
                new WeddingDate("20-Feb-2025"),
                new WeddingLocation("Grand Ballroom")
        );

        CommandResult commandResult = new AddWeddingCommand(validWedding).execute(modelStub);

        assertEquals(String.format(seedu.address.logic.commands.AddWeddingCommand.MESSAGE_SUCCESS, validWedding),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validWedding), modelStub.weddingsAdded);
    }

    @Test
    public void execute_duplicateWedding_throwsCommandException() {
        // Create a wedding event.
        Wedding validWedding = new Wedding(
                new WeddingName("John & Jane's Wedding"),
                new WeddingDate("20-Feb-2025"),
                new WeddingLocation("Grand Ballroom")
        );
        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(validWedding);
        ModelStub modelStub = new ModelStubWithWedding(validWedding);

        // Expect a CommandException because modelStub already has `validWedding`
        assertThrows(CommandException.class,
                AddWeddingCommand.MESSAGE_DUPLICATE_WEDDING, () -> addWeddingCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Wedding aliceWedding = new Wedding(
                new WeddingName("Alice & Bob's Wedding"),
                new WeddingDate("21-Feb-2025"),
                new WeddingLocation("Central Park")
        );
        Wedding bobWedding = new Wedding(
                new WeddingName("Bob & Charlie's Wedding"),
                new WeddingDate("22-Feb-2025"),
                new WeddingLocation("Garden")
        );

        AddWeddingCommand addAliceWeddingCommand = new AddWeddingCommand(aliceWedding);
        AddWeddingCommand addBobWeddingCommand = new AddWeddingCommand(bobWedding);

        // same object -> returns true
        assertTrue(addAliceWeddingCommand.equals(addAliceWeddingCommand));

        // same values -> returns true
        AddWeddingCommand addAliceWeddingCommandCopy = new AddWeddingCommand(aliceWedding);
        assertTrue(addAliceWeddingCommand.equals(addAliceWeddingCommandCopy));

        // different types -> returns false
        assertFalse(addAliceWeddingCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceWeddingCommand.equals(null));

        // different wedding -> returns false
        assertFalse(addAliceWeddingCommand.equals(addBobWeddingCommand));
    }

    // ============================ MODEL STUBS ============================

    /**
     * A default Model stub that has all methods throwing {@code AssertionError}.
     * This prevents unimplemented methods from accidentally being called in tests.
     */
    private class ModelStub implements Model {

        @Override
        public boolean hasWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWedding(Wedding target, Wedding editedWedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Wedding getWeddingById(WeddingId weddingId) {
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

        // --- Person-related methods (not used by this command test) ---
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void forceAddPerson(Person person) {
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

        // --- Wedding-related filter methods (if your Model has them) ---
        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        // --- AddressBook / UserPrefs methods (not used by this command test) ---
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(seedu.address.commons.core.GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public seedu.address.commons.core.GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that already contains one wedding.
     */
    private class ModelStubWithWedding extends ModelStub {
        private final Wedding wedding;

        ModelStubWithWedding(Wedding wedding) {
            requireNonNull(wedding);
            this.wedding = wedding;
        }

        @Override
        public boolean hasWedding(Wedding wedding) {
            requireNonNull(wedding);
            // Use isSameWedding if your Wedding class has such a method
            return this.wedding.isSameWedding(wedding);
        }
    }

    /**
     * A Model stub that always accepts the wedding being added.
     */
    private class ModelStubAcceptingWeddingAdded extends ModelStub {
        final ArrayList<Wedding> weddingsAdded = new ArrayList<>();

        @Override
        public boolean hasWedding(Wedding wedding) {
            requireNonNull(wedding);
            return weddingsAdded.stream().anyMatch(wedding::isSameWedding);
        }

        @Override
        public void addWedding(Wedding wedding) {
            requireNonNull(wedding);
            weddingsAdded.add(wedding);
        }
    }
}

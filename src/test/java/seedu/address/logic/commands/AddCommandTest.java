package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    /**
     * Clears any pending command from the ConfirmationManager before each test,
     * ensuring that each test runs with a clean state.
     */
    @BeforeEach
    public void setUp() {
        ConfirmationManager.getInstance().clearPendingCommand();
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_returnsConfirmationResult() throws Exception {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        // Execute the add command in normal mode.
        CommandResult commandResult = addCommand.execute(modelStub);

        // Instead of expecting an exception, a duplicate warning with confirmation required is expected.
        assertEquals(AddCommand.MESSAGE_DUPLICATE_PERSON, commandResult.getFeedbackToUser());
        assertTrue(commandResult.isRequiresConfirmation());
    }

    /**
     * Tests that the equals method returns true for commands with the same values and false otherwise.
     */
    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Tests that the toString method outputs the expected string representation, including the force flag.
     */
    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + ", isForce=false}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * Tests that createForceCommand returns a new AddCommand instance with the force flag set to true.
     */
    @Test
    public void createForceCommand_returnsForcedVersion() {
        Person validPerson = new PersonBuilder().build();
        AddCommand normalCommand = new AddCommand(validPerson);
        ForceableCommand forcedCommand = normalCommand.createForceCommand();

        // The forced command should be an instance of AddCommand with isForce true.
        assertTrue(forcedCommand instanceof AddCommand);
        AddCommand forcedAddCommand = (AddCommand) forcedCommand;
        AddCommand expected = new AddCommand(validPerson, true);
        assertEquals(expected, forcedAddCommand);
    }

    /**
     * Tests that the overloaded constructor sets the force flag correctly.
     */
    @Test
    public void constructor_forceMode_setsForceFlag() {
        Person validPerson = new PersonBuilder().build();
        AddCommand forcedCommand = new AddCommand(validPerson, true);
        // Verify that forcedCommand equals a new forced command created via createForceCommand.
        assertEquals(new AddCommand(validPerson, true), forcedCommand);
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accepts the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}

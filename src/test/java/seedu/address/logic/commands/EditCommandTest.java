package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        Person editedPerson = new PersonBuilder(lastPerson)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .build();

        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        // If no fields are specified, expect an error.
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_NO_CHANGES);
    }

    /**
     * Tests that if the user attempts to edit a person with the exact same values,
     * a CommandException is thrown indicating no changes were detected.
     */
    @Test
    public void execute_noChangesDetected_failure() {
        Person personToEdit = model.getFilteredPersonList().get(0);
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(personToEdit).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        assertThrows(CommandException.class, EditCommand.MESSAGE_NO_CHANGES, () -> editCommand.execute(model));
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).withTags().build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        CommandResult commandResult = editCommand.execute(model);
        assertEquals(EditCommand.MESSAGE_DUPLICATE_PERSON, commandResult.getFeedbackToUser());
        assertTrue(commandResult.isRequiresConfirmation());
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        CommandResult commandResult = editCommand.execute(model);
        assertEquals(EditCommand.MESSAGE_DUPLICATE_PERSON, commandResult.getFeedbackToUser());
        assertTrue(commandResult.isRequiresConfirmation());
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests that forcing an edit (to bypass duplicate checks) successfully updates the person.
     *
     * <p>
     * For example, editing BENSON to have the same details as ALICE would normally be rejected.
     * When re-executed in force mode, the duplicate check is bypassed and the update succeeds.
     * </p>
     */
    @Test
    public void execute_duplicatePersonForceMode_success() throws Exception {
        // Assume our typical address book has at least two persons: ALICE and BENSON.
        // We will edit BENSON to have the same details as ALICE.
        Person alice = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person benson = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Build an EditPersonDescriptor that changes BENSON's details to match ALICE.
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(alice).build();
        // Create an EditCommand in force mode targeting BENSON.
        EditCommand forceEditCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor, true);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(alice));

        // Create an expected model. We start with a copy of the typical address book.
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // Force-update BENSON in the expected model.
        expectedModel.forceSetPerson(benson, alice);

        // Execute the force edit command.
        assertCommandSuccess(forceEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = INDEX_FIRST_PERSON;
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + ", isForced=false}";
        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void createForceCommand_returnsCommandWithForceTrue() {
        // Construct a normal EditCommand.
        EditCommand normalEditCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);
        // Create the forced version using createForceCommand().
        ForceableCommand forceable = normalEditCommand.createForceCommand();
        assertTrue(forceable instanceof EditCommand);
        EditCommand forcedEditCommand = (EditCommand) forceable;
        // Construct the expected forced EditCommand.
        EditCommand expectedCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY, true);
        assertEquals(expectedCommand, forcedEditCommand);
    }

}

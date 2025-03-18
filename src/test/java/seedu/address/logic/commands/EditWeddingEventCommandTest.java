package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.EditWeddingEventCommand.EditWeddingDescriptor;
import seedu.address.model.*;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.*;
import seedu.address.testutil.EditWeddingDescriptorBuilder;
import seedu.address.testutil.TypicalWeddings;
import seedu.address.testutil.WeddingBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EditWeddingEventCommand}.
 */
public class EditWeddingEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

//    @Test
//    public void execute_allFieldsSpecifiedUnfilteredList_success() {
//        // 1. Identify the "originalWedding" from the model's current list
//        Wedding originalWedding = model.getFilteredWeddingList().get(0);
//
//        // 2. Create the "editedWedding" with new details (same ID, but different name/date/location)
//        Wedding editedWedding = new WeddingBuilder()
//                .withWeddingId(originalWedding.getWeddingId().toString())
//                .withWeddingName("Unique Wedding Name")
//                .withWeddingDate("25-Dec-2028")
//                .withWeddingLocation("Some Unique Location")
//                .build();
//
//        // 3. Build the descriptor from the editedWedding
//        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(editedWedding).build();
//
//        // 4. Create the EditWeddingEventCommand with the original wedding's ID
//        EditWeddingEventCommand command = new EditWeddingEventCommand(
//                originalWedding.getWeddingId(), descriptor);
//
//        // 5. Construct the expected message
//        String expectedMessage = String.format(EditWeddingEventCommand.MESSAGE_EDIT_WEDDING_SUCCESS,
//                Messages.format(editedWedding));
//
//        // 6. Create an expectedModel that replicates the same replacement
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.setWedding(originalWedding, editedWedding);
//        // Make sure to call updateFilteredWeddingList if your command does it
//        expectedModel.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
//
//        // 7. Debug prints to confirm alignment
//        System.out.println("Original Wedding (Before Edit): " + originalWedding);
//        System.out.println("Edited Wedding: " + editedWedding);
//        System.out.println("Wedding ID used in Command: " + originalWedding.getWeddingId());
//        System.out.println("Model before execution: " + model.getFilteredWeddingList());
//        System.out.println("Expected model before execution: " + expectedModel.getFilteredWeddingList());
//        System.out.println(model.equals(expectedModel));
//
//        // 8. Assert that command success aligns with the expected model
//        assertCommandSuccess(command, model, expectedMessage, expectedModel);
//    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Wedding editedWedding = new WeddingBuilder().build();
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(editedWedding).build();
        EditWeddingEventCommand editCommand = new EditWeddingEventCommand(WEDDING_ONE.getWeddingId(), descriptor);

        String expectedMessage = String.format(EditWeddingEventCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setWedding(WEDDING_ONE, editedWedding);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Wedding editedWedding = new WeddingBuilder().withWeddingName("Unique Wedding Name").build();
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder().withWeddingName("Unique Wedding Name").build();
        EditWeddingEventCommand editCommand = new EditWeddingEventCommand(WEDDING_ONE.getWeddingId(), descriptor);

        String expectedMessage = String.format(EditWeddingEventCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setWedding(WEDDING_ONE, editedWedding);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditWeddingEventCommand editCommand = new EditWeddingEventCommand(WEDDING_ONE.getWeddingId(), new EditWeddingDescriptor());
        Wedding editedWedding = model.getFilteredWeddingList().get(0);

        String expectedMessage = String.format(EditWeddingEventCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicateWeddingUnfilteredList_failure() {
        Wedding firstWedding = model.getFilteredWeddingList().get(0);
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(firstWedding).build();
        EditWeddingEventCommand editCommand = new EditWeddingEventCommand(WEDDING_TWO.getWeddingId(), descriptor);

        assertThrows(CommandException.class, EditWeddingEventCommand.MESSAGE_DUPLICATE_WEDDING, () -> editCommand.execute(model));
    }

    @Test
    public void execute_invalidWeddingIdUnfilteredList_failure() {
        WeddingId invalidWeddingId = new WeddingId("W999");
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder().withWeddingName("Unique Wedding Name").build();
        EditWeddingEventCommand editCommand = new EditWeddingEventCommand(invalidWeddingId, descriptor);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX, () -> editCommand.execute(model));
    }


    @Test
    public void equals() {
        final EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(WEDDING_ONE).build();
        final EditWeddingEventCommand standardCommand = new EditWeddingEventCommand(WEDDING_ONE.getWeddingId(), descriptor);

        // same values -> returns true
        EditWeddingDescriptor copyDescriptor = new EditWeddingDescriptor(descriptor);
        EditWeddingEventCommand commandWithSameValues = new EditWeddingEventCommand(WEDDING_ONE.getWeddingId(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditWeddingEventCommand(WEDDING_TWO.getWeddingId(), descriptor)));

        // different descriptor -> returns false
        EditWeddingDescriptor differentDescriptor = new EditWeddingDescriptorBuilder(WEDDING_TWO).build();
        assertFalse(standardCommand.equals(new EditWeddingEventCommand(WEDDING_ONE.getWeddingId(), differentDescriptor)));
    }

    @Test
    public void toStringMethod() {
        final EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(WEDDING_ONE).build();
        final EditWeddingEventCommand standardCommand = new EditWeddingEventCommand(WEDDING_ONE.getWeddingId(), descriptor);

        String expectedString = "EditWeddingEventCommand{index=W001, editWeddingDescriptor=EditWeddingDescriptor{"
                + "weddingName=Optional[John & Jane Wedding], weddingDate=Optional[15-Jun-2025], weddingLocation=Optional[Central Park]}}";
        System.out.println(standardCommand.toString());
        assertEquals(expectedString, standardCommand.toString());
    }

}

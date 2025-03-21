package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;
import seedu.address.testutil.EditWeddingDescriptorBuilder;
import seedu.address.testutil.WeddingBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EditWeddingCommand}.
 */
public class EditWeddingCommandTest {

    private static final Wedding WEDDING_ONE = new WeddingBuilder()
            .withWeddingId("W001")
            .withWeddingName("John & Jane Wedding")
            .withWeddingDate("15-Jun-2025")
            .withWeddingLocation("Central Park")
            .build();

    private static final Wedding WEDDING_TWO = new WeddingBuilder()
            .withWeddingId("W002")
            .withWeddingName("Alice & Bob Wedding")
            .withWeddingDate("10-Sep-2025")
            .withWeddingLocation("Beachside Resort")
            .build();

    private final Model model = createModelWithWeddings();

    private static Model createModelWithWeddings() {
        AddressBook ab = new AddressBook();
        ab.addWedding(WEDDING_ONE);
        ab.addWedding(WEDDING_TWO);
        return new ModelManager(ab, new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Wedding editedWedding = new WeddingBuilder()
                .withWeddingId("W001")
                .withWeddingName("Unique Wedding Name")
                .withWeddingDate("25-Dec-2028")
                .withWeddingLocation("Some Unique Location")
                .build();

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(editedWedding).build();
        EditWeddingCommand editCommand = new EditWeddingCommand(
                new WeddingId("W001"), descriptor);

        String expectedMessage = String.format(
                EditWeddingCommand.MESSAGE_EDIT_WEDDING_SUCCESS,
                Messages.format(editedWedding)
        );

        Model expectedModel = createModelWithWeddings();
        expectedModel.setWedding(WEDDING_ONE, editedWedding);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Wedding partialEditWedding = new WeddingBuilder(WEDDING_ONE)
                .withWeddingName("Unique Wedding Name")
                .build();

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder()
                .withWeddingName("Unique Wedding Name")
                .build();

        EditWeddingCommand editCommand = new EditWeddingCommand(
                new WeddingId("W001"), descriptor);

        String expectedMessage = String.format(
                EditWeddingCommand.MESSAGE_EDIT_WEDDING_SUCCESS,
                Messages.format(partialEditWedding)
        );

        Model expectedModel = createModelWithWeddings();
        expectedModel.setWedding(WEDDING_ONE, partialEditWedding);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditWeddingCommand editCommand = new EditWeddingCommand(
                new WeddingId("W001"), new EditWeddingDescriptor()
        );
        Wedding unmodifiedWedding = model.getFilteredWeddingList().get(0);

        String expectedMessage = String.format(
                EditWeddingCommand.MESSAGE_EDIT_WEDDING_SUCCESS,
                Messages.format(unmodifiedWedding)
        );

        Model expectedModel = createModelWithWeddings();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateWeddingUnfilteredList_failure() {
        // Attempt to edit WEDDING_TWO to have the same details as WEDDING_ONE
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(WEDDING_ONE).build();
        EditWeddingCommand editCommand = new EditWeddingCommand(
                new WeddingId("W002"), descriptor
        );

        assertThrows(CommandException.class,
                EditWeddingCommand.MESSAGE_DUPLICATE_WEDDING, () -> editCommand.execute(model));
    }

    @Test
    public void execute_invalidWeddingIdUnfilteredList_failure() {
        WeddingId invalidWeddingId = new WeddingId("W999");
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder()
                .withWeddingName("Unique Wedding Name")
                .build();

        EditWeddingCommand editCommand = new EditWeddingCommand(
                invalidWeddingId, descriptor
        );

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX, () -> editCommand.execute(model));
    }

    @Test
    public void equals() {
        final EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(WEDDING_ONE).build();
        final EditWeddingCommand standardCommand = new EditWeddingCommand(
                new WeddingId("W001"), descriptor
        );

        // same values -> returns true
        EditWeddingDescriptor copyDescriptor = new EditWeddingDescriptor(descriptor);
        EditWeddingCommand commandWithSameValues = new EditWeddingCommand(
                new WeddingId("W001"), copyDescriptor
        );
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditWeddingCommand(
                new WeddingId("W002"), descriptor)));

        // different descriptor -> returns false
        EditWeddingDescriptor differentDescriptor = new EditWeddingDescriptorBuilder(WEDDING_TWO).build();
        assertFalse(standardCommand.equals(new EditWeddingCommand(
                new WeddingId("W001"), differentDescriptor)));
    }

    @Test
    public void toStringMethod() {
        final EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(WEDDING_ONE).build();
        final EditWeddingCommand standardCommand = new EditWeddingCommand(
                new WeddingId("W001"), descriptor
        );

        String expectedString = "EditWeddingCommand{index=W001, "
                + "editWeddingDescriptor=EditWeddingDescriptor{"
                + "weddingName=Optional[John & Jane Wedding], "
                + "weddingDate=Optional[15-Jun-2025], "
                + "weddingLocation=Optional[Central Park]}}";

        assertEquals(expectedString, standardCommand.toString());
    }
}

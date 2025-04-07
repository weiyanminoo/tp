package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
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
                new WeddingDate("20-Feb-2026"),
                new WeddingLocation("Grand Ballroom")
        );

        CommandResult commandResult = new AddWeddingCommand(validWedding).execute(modelStub);

        assertEquals(String.format(AddWeddingCommand.MESSAGE_SUCCESS, validWedding),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validWedding), modelStub.weddingsAdded);
    }

    @Test
    public void execute_duplicateWedding_throwsCommandException() throws ParseException {
        // Create a wedding event.
        Wedding validWedding = new Wedding(
                new WeddingName("John & Jane's Wedding"),
                new WeddingDate("20-Feb-2026"),
                new WeddingLocation("Grand Ballroom")
        );
        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(validWedding);
        ModelStub modelStub = new ModelStubWithWedding(validWedding);

        // Expect a CommandException because modelStub already has `validWedding`
        assertThrows(CommandException.class,
                AddWeddingCommand.MESSAGE_DUPLICATE_WEDDING, () -> addWeddingCommand.execute(modelStub));
    }

    @Test
    public void equals() throws ParseException {
        Wedding aliceWedding = new Wedding(
                new WeddingName("Alice & Bob's Wedding"),
                new WeddingDate("21-Feb-2026"),
                new WeddingLocation("Central Park")
        );
        Wedding bobWedding = new Wedding(
                new WeddingName("Bob & Charlie's Wedding"),
                new WeddingDate("22-Feb-2026"),
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

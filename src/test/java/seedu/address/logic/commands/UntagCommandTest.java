package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;
import seedu.address.testutil.PersonBuilder;

public class UntagCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Wedding defaultWedding = WEDDING_ONE;

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UntagCommand untagCommand = new UntagCommand(outOfBoundIndex, defaultWedding.getWeddingId());

        assertCommandFailure(untagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personNotTagged_throwsCommandException() {
        // create a person with weddingId not tagged
        WeddingId weddingId = new WeddingId("W12345");
        Person person = new PersonBuilder().build();
        model.addPerson(person);

        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, weddingId);

        assertCommandFailure(untagCommand, model, UntagCommand.MESSAGE_PERSON_NOT_TAGGED);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, defaultWedding.getWeddingId());
        assertTrue(untagCommand.equals(untagCommand));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, defaultWedding.getWeddingId());
        UntagCommand differentUntagCommand = new UntagCommand(INDEX_FIRST_PERSON, new WeddingId("W54321"));
        assertFalse(untagCommand.equals(differentUntagCommand));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, defaultWedding.getWeddingId());
        assertFalse(untagCommand.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, defaultWedding.getWeddingId());
        assertFalse(untagCommand.equals(5));
    }
}

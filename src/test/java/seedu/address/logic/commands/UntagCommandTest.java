package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import seedu.address.model.tag.TagMatchesPredicate;
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
    public void execute_untagPerson_keepsFilterApplied() throws Exception {
        WeddingId weddingId = new WeddingId("W1");

        Person personWithTag = new PersonBuilder()
                .withName("Test Person")
                .withTags("W1")
                .build();
        model.addPerson(personWithTag);

        // Apply the filter so that only persons with wedding tag W1 are shown.
        model.updateFilteredPersonList(new TagMatchesPredicate(weddingId));

        // Verify that the person is in the filtered list.
        assertTrue(model.getFilteredPersonList().contains(personWithTag));

        // Get the index of the person in the filtered list.
        int index = model.getFilteredPersonList().indexOf(personWithTag);
        UntagCommand untagCommand = new UntagCommand(Index.fromZeroBased(index), weddingId);

        untagCommand.execute(model);

        // The untag command updates the filtered list to show only persons still tagged with W1.
        // Since the tag was removed, personWithTag should no longer be in the filtered list.
        assertFalse(model.getFilteredPersonList().contains(personWithTag));
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

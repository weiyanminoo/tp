package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagMatchesPredicate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.testutil.PersonBuilder;

public class UntagCommandTest {

    private Model model;
    private final WeddingId defaultWeddingId = WEDDING_ONE.getWeddingId();

    @BeforeEach
    public void setUp() {
        // Initialize with an empty AddressBook to isolate this test.
        model = new ModelManager(new seedu.address.model.AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UntagCommand untagCommand = new UntagCommand(outOfBoundIndex, defaultWeddingId);

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

        // Apply a filter so that only persons with tag "W1" are shown.
        model.updateFilteredPersonList(new TagMatchesPredicate(weddingId));
        Person filteredPerson = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().toString().equals("Test Person"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test Person not found in filtered list"));
        assertTrue(filteredPerson.getTags().contains(new Tag(weddingId)));

        // Execute the untag command using the filtered index.
        int filteredIndex = model.getFilteredPersonList().indexOf(filteredPerson);
        UntagCommand untagCommand = new UntagCommand(Index.fromZeroBased(filteredIndex), weddingId);
        untagCommand.execute(model);

        // Reapply the same filter.
        model.updateFilteredPersonList(new TagMatchesPredicate(weddingId));
        // The person should no longer be in the filtered list.
        assertFalse(model.getFilteredPersonList().contains(filteredPerson));
    }

    @Test
    public void execute_nonFilteredUntagPerson_success() throws Exception {
        // Non-filter mode: start with an empty AddressBook.
        model = new ModelManager(new seedu.address.model.AddressBook(), new UserPrefs());
        WeddingId weddingId = new WeddingId("W1");
        Tag expectedTag = new Tag(weddingId);
        // Create a person with tag "W1" and add to the model.
        Person personWithTag = new PersonBuilder()
                .withName("Test Person")
                .withTags("W1")
                .build();
        model.addPerson(personWithTag);

        // Ensure non-filter mode by resetting the filtered list to show all persons.
        model.updateFilteredPersonList(p -> true);
        Person originalPerson = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().toString().equals("Test Person"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test Person not found in full list"));
        assertTrue(originalPerson.getTags().contains(expectedTag));

        // Execute the untag command.
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, weddingId);
        untagCommand.execute(model);

        // After untagging in non-filter mode, the command resets the filtered list to show all.
        model.updateFilteredPersonList(p -> true);
        Person updatedPerson = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().toString().equals("Test Person"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test Person not found after untagging"));
        assertFalse(updatedPerson.getTags().contains(expectedTag));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, defaultWeddingId);
        assertTrue(untagCommand.equals(untagCommand));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, defaultWeddingId);
        UntagCommand differentUntagCommand = new UntagCommand(INDEX_FIRST_PERSON, new WeddingId("W54321"));
        assertFalse(untagCommand.equals(differentUntagCommand));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, defaultWeddingId);
        assertFalse(untagCommand.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, defaultWeddingId);
        assertFalse(untagCommand.equals(5));
    }
}

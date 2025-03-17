package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagMatchesPredicate;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FilterCommand}.
 */
public class FilterCommandTest {
    private static final WeddingId WEDDING_ID_W1 = new WeddingId("W1");
    private static final WeddingId WEDDING_ID_W2 = new WeddingId("W2");
    private static final WeddingId WEDDING_ID_NONEXISTENT = new WeddingId("W999");

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Add test weddings to the model
        Wedding wedding1 = new Wedding(
                WEDDING_ID_W1,
                new WeddingName("John & Jane's Wedding"),
                new WeddingDate("20-Feb-2025"),
                new WeddingLocation("Grand Ballroom"));

        Wedding wedding2 = new Wedding(
                WEDDING_ID_W2,
                new WeddingName("Bob & Alice's Wedding"),
                new WeddingDate("21-Feb-2025"),
                new WeddingLocation("Garden Pavilion"));

        model.addWedding(wedding1);
        model.addWedding(wedding2);
        expectedModel.addWedding(wedding1);
        expectedModel.addWedding(wedding2);

        // Add some persons with wedding tags
        Person person1 = new PersonBuilder().withName("John").withTags(WEDDING_ID_W1.toString()).build();
        Person person2 = new PersonBuilder().withName("Jane").withTags(WEDDING_ID_W1.toString()).build();
        Person person3 = new PersonBuilder().withName("Bob").withTags(WEDDING_ID_W2.toString()).build();

        model.addPerson(person1);
        model.addPerson(person2);
        model.addPerson(person3);
        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);
        expectedModel.addPerson(person3);
    }

    @Test
    public void equals() {
        FilterCommand filterFirstCommand = new FilterCommand(WEDDING_ID_W1);
        FilterCommand filterSecondCommand = new FilterCommand(WEDDING_ID_W2);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(WEDDING_ID_W1);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different wedding ID -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_validWeddingId_success() {
        // Get the wedding from the model
        Wedding wedding = model.getFilteredWeddingList().stream()
                .filter(w -> w.getWeddingId().equals(WEDDING_ID_W1))
                .findFirst()
                .orElseThrow();

        FilterCommand filterCommand = new FilterCommand(WEDDING_ID_W1);
        String expectedMessage = String.format(FilterCommand.MESSAGE_SUCCESS,
                wedding.getWeddingId(),
                wedding.getWeddingName(),
                wedding.getWeddingDate(),
                wedding.getLocation());

        expectedModel.updateFilteredPersonList(new TagMatchesPredicate(WEDDING_ID_W1));

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_nonExistentWeddingId_throwsCommandException() {
        FilterCommand filterCommand = new FilterCommand(WEDDING_ID_NONEXISTENT);
        String expectedMessage = "The specified wedding id " + WEDDING_ID_NONEXISTENT + " does not exist.";

        assertCommandFailure(filterCommand, model, expectedMessage);
    }

    @Test
    public void execute_emptyFilteredList_showsEmptyList() {
        // Create a wedding with no associated persons
        WeddingId emptyWeddingId = new WeddingId("W3");
        Wedding emptyWedding = new Wedding(
                emptyWeddingId,
                new WeddingName("Empty Wedding"),
                new WeddingDate("22-Feb-2025"),
                new WeddingLocation("Empty Hall"));

        model.addWedding(emptyWedding);
        expectedModel.addWedding(emptyWedding);

        FilterCommand filterCommand = new FilterCommand(emptyWeddingId);
        String expectedMessage = String.format(FilterCommand.MESSAGE_SUCCESS,
                emptyWedding.getWeddingId(),
                emptyWedding.getWeddingName(),
                emptyWedding.getWeddingDate(),
                emptyWedding.getLocation());

        expectedModel.updateFilteredPersonList(new TagMatchesPredicate(emptyWeddingId));

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
        assertEquals(0, model.getFilteredPersonList().size());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;
import seedu.address.testutil.PersonBuilder;

/**
 *
 */
public class ListWeddingEventCommandTest {
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
        ListWeddingEventCommand listWeddingEventCommand = new ListWeddingEventCommand();
        ListWeddingEventCommand listWeddingEventCommandCopy = new ListWeddingEventCommand();
        ListCommand listCommand = new ListCommand();
        System.out.println("model" + model.getFilteredWeddingList());

        assertTrue(listWeddingEventCommand.equals(listWeddingEventCommand));
        assertTrue(listWeddingEventCommand.equals(listWeddingEventCommandCopy));
        assertFalse(listWeddingEventCommand.equals(listCommand));
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        StringBuilder output = new StringBuilder();
        for (Wedding wedding : model.getFilteredWeddingList()) {
            output.append("\nWedding: ").append(wedding.getWeddingName().toString())
                    .append(" (ID: ").append(wedding.getWeddingId().toString()).append(")")
                    .append("\nDate: ").append(wedding.getWeddingDate().toString())
                    .append("\nLocation: ").append(wedding.getWeddingLocation().toString())
                    .append("\n");
        }
        assertCommandSuccess(new ListWeddingEventCommand(), model,
                ListWeddingEventCommand.MESSAGE_SUCCESS + output, expectedModel);
    }
}

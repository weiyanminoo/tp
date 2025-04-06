package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

public class DeleteWeddingCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_validWeddingIdForWeddingDeleted_nonFiltered() throws Exception {
        // Non-filtered scenario: No filter is applied, so deletion should update the filtered lists to show
        // all remaining items.
        WeddingId weddingId = new WeddingId("W12345");
        WeddingName weddingName = new WeddingName("Wedding Name");
        WeddingDate weddingDate = new WeddingDate("01-Jan-2026");
        WeddingLocation weddingLocation = new WeddingLocation("Paris");
        Wedding wedding = new Wedding(weddingId, weddingName, weddingDate, weddingLocation);
        model.addWedding(wedding);

        // No filter applied, so the filtered wedding list equals the full list.
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(weddingId);
        deleteWeddingCommand.execute(model);

        // Since the only wedding was deleted, the filtered wedding list should be empty.
        // And, since no persons were added, the filtered person list should be empty too.
        assertTrue(model.getFilteredWeddingList().isEmpty());
        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_filteredWeddingDeletion_resultsInEmptyLists() throws Exception {

        WeddingId targetWeddingId = new WeddingId("W12345");
        Wedding targetWedding = new Wedding(targetWeddingId,
                new WeddingName("Target Wedding"),
                new WeddingDate("01-Jan-2026"),
                new WeddingLocation("Paris"));
        WeddingId otherWeddingId = new WeddingId("W67890");
        Wedding otherWedding = new Wedding(otherWeddingId,
                new WeddingName("Other Wedding"),
                new WeddingDate("05-Feb-2026"),
                new WeddingLocation("London"));

        model.addWedding(targetWedding);
        model.addWedding(otherWedding);

        // Add a person tagged with the target wedding.
        Tag targetTag = new Tag(targetWeddingId);
        Person taggedPerson = new Person(new Name("Alice"), new Phone("12345678"),
                new Email("alice@example.com"), new Role("Guest"),
                new Address("123 Street"), Set.of(targetTag));
        model.addPerson(taggedPerson);

        // Apply filter: show only weddings with targetWeddingId and persons with the corresponding tag.
        model.updateFilteredWeddingList(w -> w.getWeddingId().equals(targetWeddingId));
        model.updateFilteredPersonList(p -> p.getTags().contains(targetTag));

        // Ensure that the filter is active.
        assertEquals(1, model.getFilteredWeddingList().size());
        assertEquals(1, model.getFilteredPersonList().size());

        // Delete the target wedding.
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(targetWeddingId);
        deleteWeddingCommand.execute(model);

        // Since a filter was active, deleteWedding command resets the filtered lists.
        // Hence, the filtered wedding and person lists should be empty.
        assertTrue(model.getFilteredWeddingList().isEmpty());
        assertTrue(model.getFilteredPersonList().isEmpty());

        // Check that the non-deleted wedding is still in the data.
        model.updateFilteredWeddingList(w -> true);
        assertEquals(1, model.getFilteredWeddingList().size());
        assertEquals(otherWedding, model.getFilteredWeddingList().get(0));
    }

    @Test
    public void execute_invalidWeddingId_throwsCommandException() {
        WeddingId weddingId = new WeddingId("W12345");
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(weddingId);

        assertThrows(CommandException.class, () -> deleteWeddingCommand.execute(model));
    }

    @Test
    public void execute_validWeddingId_tagRemovedFromAllContacts() throws Exception {
        WeddingId weddingId = new WeddingId("W123456");
        WeddingName weddingName = new WeddingName("Wedding Name");
        WeddingDate weddingDate = new WeddingDate("01-Jan-2026");
        WeddingLocation weddingLocation = new WeddingLocation("Paris");
        Wedding wedding = new Wedding(weddingId, weddingName, weddingDate, weddingLocation);
        model.addWedding(wedding);

        Tag tag = new Tag(weddingId);
        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@example.com"),
                new Role("Guest"), new Address("123 Street"), Set.of(tag));
        model.addPerson(person);

        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(weddingId);
        deleteWeddingCommand.execute(model);

        model.updateFilteredPersonList(p -> true);
        assertTrue(model.getFilteredPersonList().get(0).getTags().isEmpty());
    }

    @Test
    public void equals_differentObjectSameId_returnsTrue() {
        WeddingId weddingId = new WeddingId("W12345");
        DeleteWeddingCommand deleteWeddingCommand1 = new DeleteWeddingCommand(weddingId);
        DeleteWeddingCommand deleteWeddingCommand2 = new DeleteWeddingCommand(weddingId);
        assertTrue(deleteWeddingCommand1.equals(deleteWeddingCommand2));
    }

    @Test
    public void equals_differentObjectDifferentId_returnsFalse() {
        WeddingId weddingId1 = new WeddingId("W12345");
        WeddingId weddingId2 = new WeddingId("W67890");
        DeleteWeddingCommand deleteWeddingCommand1 = new DeleteWeddingCommand(weddingId1);
        DeleteWeddingCommand deleteWeddingCommand2 = new DeleteWeddingCommand(weddingId2);
        assertFalse(deleteWeddingCommand1.equals(deleteWeddingCommand2));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        WeddingId weddingId = new WeddingId("W12345");
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(weddingId);
        assertFalse(deleteWeddingCommand.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        WeddingId weddingId = new WeddingId("W12345");
        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(weddingId);
        assertFalse(deleteWeddingCommand.equals(new Object()));
    }
}

package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteWeddingCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_validWeddingId_weddingDeleted() throws Exception {
        WeddingId weddingId = new WeddingId("W12345");
        WeddingName weddingName = new WeddingName("Wedding Name");
        WeddingDate weddingDate = new WeddingDate("01-Jan-2025");
        WeddingLocation weddingLocation = new WeddingLocation("Paris");
        Wedding wedding = new Wedding(weddingId, weddingName, weddingDate, weddingLocation);
        model.addWedding(wedding);

        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(weddingId);
        deleteWeddingCommand.execute(model);

        assertTrue(model.getFilteredWeddingList().isEmpty());
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
        WeddingDate weddingDate = new WeddingDate("01-Jan-2025");
        WeddingLocation weddingLocation = new WeddingLocation("Paris");
        Wedding wedding = new Wedding(weddingId, weddingName, weddingDate, weddingLocation);
        model.addWedding(wedding);

        Tag tag = new Tag(weddingId);
        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@example.com"),
                new Role("Guest"), new Address("123 Street"), Set.of(tag));
        model.addPerson(person);

        DeleteWeddingCommand deleteWeddingCommand = new DeleteWeddingCommand(weddingId);
        deleteWeddingCommand.execute(model);

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

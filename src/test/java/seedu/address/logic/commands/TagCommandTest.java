package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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

public class TagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_validIndexAndWeddingId_tagAdded() throws Exception {
        WeddingId weddingId = new WeddingId("W12345");
        WeddingName weddingName = new WeddingName("Wedding Name");
        WeddingDate weddingDate = new WeddingDate("01-Jan-2026");
        WeddingLocation weddingLocation = new WeddingLocation("Paris");
        Wedding wedding = new Wedding(weddingId, weddingName, weddingDate, weddingLocation);
        model.addWedding(wedding);

        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@example.com"),
                new Role("Guest"), new Address("123 Street"), Set.of());
        model.addPerson(person);

        TagCommand tagCommand = new TagCommand(Index.fromZeroBased(0), weddingId);
        tagCommand.execute(model);

        List<Person> personList = model.getFilteredPersonList();
        assertTrue(personList.get(0).getTags().contains(new Tag(weddingId)));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        WeddingId weddingId = new WeddingId("W12345");
        TagCommand tagCommand = new TagCommand(Index.fromZeroBased(1), weddingId);

        assertThrows(CommandException.class, () -> tagCommand.execute(model));
    }

    @Test
    public void execute_nonExistentWeddingId_throwsCommandException() {
        WeddingId weddingId = new WeddingId("W12345");
        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@example.com"),
                new Role("Guest"), new Address("123 Street"), Set.of());
        model.addPerson(person);

        TagCommand tagCommand = new TagCommand(Index.fromZeroBased(0), weddingId);

        assertThrows(CommandException.class, () -> tagCommand.execute(model));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        WeddingId weddingId = new WeddingId("W12345");
        TagCommand tagCommand = new TagCommand(Index.fromZeroBased(0), weddingId);
        assertTrue(tagCommand.equals(tagCommand));
    }

    @Test
    public void equals_differentObjectSameValues_returnsTrue() {
        WeddingId weddingId = new WeddingId("W12345");
        TagCommand tagCommand1 = new TagCommand(Index.fromZeroBased(0), weddingId);
        TagCommand tagCommand2 = new TagCommand(Index.fromZeroBased(0), weddingId);
        assertTrue(tagCommand1.equals(tagCommand2));
    }

    @Test
    public void equals_differentObjectDifferentValues_returnsFalse() {
        WeddingId weddingId1 = new WeddingId("W12345");
        WeddingId weddingId2 = new WeddingId("W67890");
        TagCommand tagCommand1 = new TagCommand(Index.fromZeroBased(0), weddingId1);
        TagCommand tagCommand2 = new TagCommand(Index.fromZeroBased(1), weddingId2);
        assertFalse(tagCommand1.equals(tagCommand2));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        WeddingId weddingId = new WeddingId("W12345");
        TagCommand tagCommand = new TagCommand(Index.fromZeroBased(0), weddingId);
        assertFalse(tagCommand.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        WeddingId weddingId = new WeddingId("W12345");
        TagCommand tagCommand = new TagCommand(Index.fromZeroBased(0), weddingId);
        assertFalse(tagCommand.equals(new Object()));
    }

    @Test
    public void execute_personAlreadyTaggedWithWeddingId_throwsCommandException() {
        WeddingId weddingId = new WeddingId("W12345");
        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@example.com"),
                new Role("Guest"), new Address("123 Street"), Set.of(new Tag(weddingId)));
        model.addPerson(person);

        TagCommand tagCommand = new TagCommand(Index.fromZeroBased(0), weddingId);

        assertThrows(CommandException.class, () -> tagCommand.execute(model));
    }

    @Test
    public void toString_validInputs_correctStringRepresentation() {
        Index targetIndex = Index.fromZeroBased(0);
        WeddingId weddingId = new WeddingId("W12345");
        TagCommand tagCommand = new TagCommand(targetIndex, weddingId);
        String expectedString = "seedu.address.logic.commands."
                + "TagCommand{targetIndex=seedu.address.commons.core.index.Index{zeroBasedIndex=0}, weddingId=W12345}";
        assertEquals(expectedString, tagCommand.toString());
    }

    @Test
    public void toString_differentInputs_correctStringRepresentation() {
        Index targetIndex = Index.fromZeroBased(1);
        WeddingId weddingId = new WeddingId("W67890");
        TagCommand tagCommand = new TagCommand(targetIndex, weddingId);
        String expectedString = "seedu.address.logic.commands."
               + "TagCommand{targetIndex=seedu.address.commons.core.index.Index{zeroBasedIndex=1}, weddingId=W67890}";
        assertEquals(expectedString, tagCommand.toString());
    }
}

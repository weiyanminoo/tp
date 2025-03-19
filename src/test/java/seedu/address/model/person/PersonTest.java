package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.WeddingId;
import seedu.address.testutil.PersonBuilder;

import java.util.HashSet;
import java.util.Set;

public class PersonTest {

    private Person person;
    private Tag tag1;
    private Tag tag2;

    @BeforeEach
    void setUp() {
        WeddingId weddingId1 = new WeddingId("1");
        WeddingId weddingId2 = new WeddingId("2");
        tag1 = new Tag(weddingId1);
        tag2 = new Tag(weddingId2);
        Set<Tag> initialTags = new HashSet<>();
        Name name = new Name("Alice");
        Phone phone = new Phone("12345678");
        Email email = new Email("alice@example.com");
        Role role = new Role("Bride");
        Address address = new Address("123 Wedding St");
        person = new Person(name, phone, email, role, address, initialTags);

    }

    @Test
    void addTag_newTag_tagAddedSuccessfully() {
        Person updatedPerson = person.addTag(tag1);

        // Ensure the original person is unchanged (immutability)
        assertFalse(person.getTags().contains(tag1));

        // Ensure the new person contains the tag
        assertTrue(updatedPerson.getTags().contains(tag1));
        assertEquals(1, updatedPerson.getTags().size());
    }

    @Test
    void addTag_duplicateTag_noDuplicateAdded() {
        Person updatedPerson = person.addTag(tag1);
        Person updatedAgain = updatedPerson.addTag(tag1);

        // Ensure the tag is not duplicated
        assertEquals(1, updatedAgain.getTags().size());
    }

    @Test
    void removeTag_existingTag_tagRemovedSuccessfully() {
        Person updatedPerson = person.addTag(tag1);
        Person finalPerson = updatedPerson.removeTag(tag1);

        // Ensure the tag is removed
        assertFalse(finalPerson.getTags().contains(tag1));
        assertEquals(0, finalPerson.getTags().size());
    }

    @Test
    void removeTag_nonExistentTag_noChangeToTags() {
        Person updatedPerson = person.removeTag(tag1);

        // Ensure that trying to remove a tag that doesn't exist doesn't break anything
        assertEquals(0, updatedPerson.getTags().size());
    }

    @Test
    void removeTag_multipleTagsOnlyOneRemoved_remainingTagsUnchanged() {
        Person updatedPerson = person.addTag(tag1).addTag(tag2);
        Person finalPerson = updatedPerson.removeTag(tag1);

        // Ensure only tag1 is removed
        assertFalse(finalPerson.getTags().contains(tag1));
        assertTrue(finalPerson.getTags().contains(tag2));
        assertEquals(1, finalPerson.getTags().size());
    }

    @Test
    void addTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> person.addTag(null));
    }

    @Test
    void removeTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> person.removeTag(null));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withRole(VALID_ROLE_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true after normalization
        // this is to ensure that user does not accidentally enter a duplicate with different case
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true after normalization
        // this is to ensure that user does not accidentally enter a duplicate with extra space
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different role -> returns false
        editedAlice = new PersonBuilder(ALICE).withRole(VALID_ROLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", role=" + ALICE.getRole() + ", address=" + ALICE.getAddress()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}

package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.wedding.WeddingId;

public class TagMatchesPredicateTest {

    @Test
    public void equals() {
        TagMatchesPredicate firstPredicate = new TagMatchesPredicate(new WeddingId("W1"));
        TagMatchesPredicate secondPredicate = new TagMatchesPredicate(new WeddingId("W2"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagMatchesPredicate firstPredicateCopy = new TagMatchesPredicate(new WeddingId("W1"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsTag_returnsTrue() {
        // Create a Person with the wedding id "W1"
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(new WeddingId("W1")));

        Person personWithTag = new Person(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new Role("Stylist"),
                new Address("Block 123"),
                tagSet
        );

        // Predicate with matching wedding id "W1"
        TagMatchesPredicate predicate = new TagMatchesPredicate(new WeddingId("W1"));

        // The predicate should return true since the person has a matching tag.
        assertTrue(predicate.test(personWithTag));
    }

    @Test
    public void test_personDoesNotContainTag_returnsFalse() {
        // Create a Person with wedding id "W2"
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(new WeddingId("W2")));

        Person personWithDifferentTag = new Person(
                new Name("Bob"),
                new Phone("87654321"),
                new Email("bob@example.com"),
                new Role("Stylist"),
                new Address("Block 456"),
                tagSet
        );

        // Predicate checking for wedding id "W1"
        TagMatchesPredicate predicate = new TagMatchesPredicate(new WeddingId("W1"));

        // The predicate should return false since the person only has tag "W2"
        assertFalse(predicate.test(personWithDifferentTag));
    }

    @Test
    public void test_personWithEmptyTags_returnsFalse() {
        // Create a Person with an empty tag set.
        Set<Tag> emptyTagSet = new HashSet<>();
        Person personWithNoTag = new Person(
                new Name("Charlie"),
                new Phone("99999999"),
                new Email("charlie@example.com"),
                new Role("Stylist"),
                new Address("Block 789"),
                emptyTagSet
        );

        // Predicate for any wedding id should return false.
        TagMatchesPredicate predicate = new TagMatchesPredicate(new WeddingId("W1"));
        assertFalse(predicate.test(personWithNoTag));
    }
}

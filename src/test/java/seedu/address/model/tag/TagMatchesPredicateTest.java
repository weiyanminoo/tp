package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;

public class TagMatchesPredicateTest {

    @Test
    public void equals() {
        TagMatchesPredicate firstPredicate = new TagMatchesPredicate("Client");
        TagMatchesPredicate secondPredicate = new TagMatchesPredicate("Vendor");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagMatchesPredicate firstPredicateCopy = new TagMatchesPredicate("Client");
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
        // Create a Person with the "Client" tag
        Set<Tag> clientTagSet = new HashSet<>();
        clientTagSet.add(new Tag("Client"));

        Person personWithClientTag = new Person(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new Role("Stylist"),
                new Address("Block 123"),
                clientTagSet
        );

        // Predicate with a lowercase "client" to check case-insensitivity
        TagMatchesPredicate predicate = new TagMatchesPredicate("client");

        // The predicate should return true since the person has a "Client" tag
        assertTrue(predicate.test(personWithClientTag));
    }

    @Test
    public void test_personDoesNotContainTag_returnsFalse() {
        // Create a Person with the "Vendor" tag
        Set<Tag> vendorTagSet = new HashSet<>();
        vendorTagSet.add(new Tag("Vendor"));

        Person personWithVendorTag = new Person(
                new Name("Bob"),
                new Phone("87654321"),
                new Email("bob@example.com"),
                new Role("Stylist"),
                new Address("Block 456"),
                vendorTagSet
        );

        // Predicate checking for "Client"
        TagMatchesPredicate predicate = new TagMatchesPredicate("Client");

        // The predicate should return false since the person only has "Vendor"
        assertFalse(predicate.test(personWithVendorTag));
    }

    @Test
    public void constructor_noTags_throwsIllegalArgumentException() {
        // Create an empty set of tags
        Set<Tag> emptyTagSet = Collections.emptySet();

        // Expect the Person constructor to throw IllegalArgumentException
        // because we enforce tag field to be either 'Client' or 'Vendor'
        assertThrows(IllegalArgumentException.class, () -> new Person(
                new Name("Charlie"),
                new Phone("99999999"),
                new Email("charlie@example.com"),
                new Role("Stylist"),
                new Address("Block 789"),
                emptyTagSet
        ));
    }
}

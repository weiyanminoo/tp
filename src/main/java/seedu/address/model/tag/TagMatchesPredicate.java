package seedu.address.model.tag ;

import seedu.address.model.person.Person;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s tags contain the specified tag (case-insensitive).
 */
public class TagMatchesPredicate implements Predicate<Person> {
    private final String tagToMatch;

    public TagMatchesPredicate(String tagToMatch) {
        this.tagToMatch = tagToMatch;
    }

    @Override
    public boolean test(Person person) {
        // Check each tag in the person's tag set
        for (Tag tag : person.getTags()) {
            if (tag.tagName.equalsIgnoreCase(tagToMatch)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TagMatchesPredicate
                && tagToMatch.equalsIgnoreCase(((TagMatchesPredicate) other).tagToMatch));
    }
}

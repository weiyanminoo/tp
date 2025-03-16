package seedu.address.model.tag;

import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.wedding.WeddingId;

/**
 * Tests that a {@code Person}'s tags contain the specified WeddingId.
 */
public class TagMatchesPredicate implements Predicate<Person> {
    private final WeddingId weddingIdToMatch;

    public TagMatchesPredicate(WeddingId weddingIdToMatch) {
        this.weddingIdToMatch = weddingIdToMatch;
    }

    @Override
    public boolean test(Person person) {
        // Check each tag in the person's tag set for a matching WeddingId
        for (Tag tag : person.getTags()) {
            if (tag.weddingId.equals(weddingIdToMatch)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TagMatchesPredicate
                && weddingIdToMatch.equals(((TagMatchesPredicate) other).weddingIdToMatch));
    }
}

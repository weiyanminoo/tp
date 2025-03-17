package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword
        RoleContainsKeywordsPredicate predicate =
                new RoleContainsKeywordsPredicate(Collections.singletonList("florist"));
        assertTrue(predicate.test(new PersonBuilder().withRole("florist photographer").build()));

        // Multiple keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("florist", "photographer"));
        assertTrue(predicate.test(new PersonBuilder().withRole("florist photographer").build()));

        // Only one matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("caterer", "photographer"));
        assertTrue(predicate.test(new PersonBuilder().withRole("florist photographer").build()));

        // Mixed-case keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("flORist", "pHoToGrApHeR"));
        assertTrue(predicate.test(new PersonBuilder().withRole("florist photographer").build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRole("florist").build()));

        // Non-matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("florist"));
        assertFalse(predicate.test(new PersonBuilder().withRole("photographer").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Main",
                "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withRole("florist").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(keywords);

        String expected = RoleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

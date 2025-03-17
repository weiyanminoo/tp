package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstKeywords = Collections.singletonList("first");
        List<String> secondKeywords = Collections.singletonList("second");

        FindCommand findFirstCommand = new FindCommand(firstKeywords);
        FindCommand findSecondCommand = new FindCommand(secondKeywords);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstKeywords);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        List<String> keywords = prepareKeywords(" ");
        FindCommand command = new FindCommand(keywords);
        // Build the composite predicate as done in FindCommand.
        Predicate<Person> compositePredicate = new NameContainsKeywordsPredicate(keywords)
                .or(new RoleContainsKeywordsPredicate(keywords));
        expectedModel.updateFilteredPersonList(compositePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        List<String> keywords = prepareKeywords("Kurz Elle Kunz");
        FindCommand command = new FindCommand(keywords);
        // Build the composite predicate as done in FindCommand.
        Predicate<Person> compositePredicate = new NameContainsKeywordsPredicate(keywords)
                .or(new RoleContainsKeywordsPredicate(keywords));
        expectedModel.updateFilteredPersonList(compositePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = Arrays.asList("keyword");
        FindCommand findCommand = new FindCommand(keywords);
        String expected = FindCommand.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a list of keywords.
     */
    private List<String> prepareKeywords(String userInput) {
        return Arrays.stream(userInput.trim().split("\\s+"))
                // Remove any empty strings
                .filter(keyword -> !keyword.isEmpty())
                // Collect into a list
                .collect(Collectors.toList());
    }
}

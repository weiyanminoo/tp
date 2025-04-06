package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // Basic case
        FilterCommand expectedFilterCommand = new FilterCommand(new WeddingId(VALID_TAG_AMY));
        assertParseSuccess(parser, VALID_TAG_AMY, expectedFilterCommand);

        // Leading and trailing whitespaces
        assertParseSuccess(parser, " \t " + VALID_TAG_AMY + " \t ", expectedFilterCommand);

        // Different wedding ID
        FilterCommand anotherExpectedCommand = new FilterCommand(new WeddingId(VALID_TAG_BOB));
        assertParseSuccess(parser, VALID_TAG_BOB, anotherExpectedCommand);
    }

    @Test
    public void equals() {
        FilterCommand filterFirstCommand = new FilterCommand(new WeddingId(VALID_TAG_AMY));
        FilterCommand filterSecondCommand = new FilterCommand(new WeddingId(VALID_TAG_BOB));
        FilterCommand filterFirstCommandCopy = new FilterCommand(new WeddingId(VALID_TAG_AMY));

        // Same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // Same values -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // Different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        assertFalse(filterFirstCommand.equals(-1));

        assertFalse(filterFirstCommand.equals(0));

        // Null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // Different wedding ID -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void parse_negativeOrZeroWeddingId_throwsParseException() {
        // Construct the expected error message that includes both constraints and usage
        String expectedMessage = WeddingId.MESSAGE_NEGATIVE_CONSTRAINTS + "\n"
                + FilterCommand.MESSAGE_USAGE;

        // Negative wedding ID
        assertThrows(ParseException.class,
                expectedMessage, () -> parser.parse("W-1"));

        // Zero wedding ID
        assertThrows(ParseException.class,
                expectedMessage, () -> parser.parse("W0"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Arrange
        String userInput = "";

        // Act & Assert
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_multipleArgs_throwsParseException() {
        // Arrange
        String userInput = "W12345 W67890";

        // Act & Assert
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidWeddingId_throwsParseException() {
        // Arrange
        String userInput = "invalid_wedding_id";

        // Act & Assert
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

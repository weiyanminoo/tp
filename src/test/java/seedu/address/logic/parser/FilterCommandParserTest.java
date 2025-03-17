package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
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
    public void parse_invalidArgs_throwsParseException() {
        // Empty string
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // Whitespace only
        assertParseFailure(parser, "   \t",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // Invalid wedding ID format (if there are specific validation rules for wedding IDs)
        // For example, if wedding IDs must start with 'W' followed by numbers
        assertParseFailure(parser, "InvalidWeddingId",
                WeddingId.MESSAGE_CONSTRAINTS);

        // Multiple arguments (should only accept one wedding ID)
        assertParseFailure(parser, VALID_TAG_AMY + " " + VALID_TAG_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
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

        // Null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // Different wedding ID -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }
}

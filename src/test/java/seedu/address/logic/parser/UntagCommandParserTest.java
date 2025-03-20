package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UntagCommand;
import seedu.address.model.wedding.WeddingId;

public class UntagCommandParserTest {
    private UntagCommandParser parser = new UntagCommandParser();

    @Test
    public void parse_validArgs_returnsUntagCommand() {
        // Valid index and wedding ID
        assertParseSuccess(parser, "1 W123",
                new UntagCommand(INDEX_FIRST_PERSON, new WeddingId("W123")));
    }

    @Test
    public void parse_emptyOrMissingArgs_throwsParseException() {
        // Empty string
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));

        // Only index
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));

        // Invalid index
        assertParseFailure(parser, "a W123",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidWeddingId_throwsIllegalArgumentException() {
        // These cases will cause IllegalArgumentException due to WeddingId validation
        assertThrows(IllegalArgumentException.class, () -> parser.parse("1 123"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("1 W"));
    }

    @Test
    public void parse_onlyWeddingId_throwsParseException() {
        // Only wedding ID - this might need adjustment based on your parser implementation
        assertParseFailure(parser, "W123",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
    }
}

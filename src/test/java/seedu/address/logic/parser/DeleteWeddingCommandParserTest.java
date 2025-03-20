package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteWeddingCommand;
import seedu.address.model.wedding.WeddingId;

public class DeleteWeddingCommandParserTest {

    private final DeleteWeddingCommandParser parser = new DeleteWeddingCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteWeddingCommand() throws Exception {
        WeddingId weddingId = new WeddingId("W12345");
        DeleteWeddingCommand command = parser.parse("W12345");
        assertEquals(new DeleteWeddingCommand(weddingId), command);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteWeddingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        String userInput = "";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteWeddingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgs_throwsParseException() {
        String userInput = "1 extra";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteWeddingCommand.MESSAGE_USAGE));
    }
}

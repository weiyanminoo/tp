package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.wedding.WeddingId.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgs_returnsTagCommand() throws Exception {
        WeddingId weddingId = new WeddingId("W12345");
        String userInput = "1 W12345";
        Index targetIndex = Index.fromOneBased(1);
        TagCommand expectedCommand = new TagCommand(targetIndex, weddingId);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a W12345";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX + "\n" + TagCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidWeddingId_throwsParseException() {
        String userInput = "1 invalidWeddingId";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingParts_throwsParseException() {
        // no index specified
        assertParseFailure(parser, "W12345", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));

        // no weddingId specified
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 someInvalidArgs", MESSAGE_CONSTRAINTS + "\n" + TagCommand.MESSAGE_USAGE);
    }
}

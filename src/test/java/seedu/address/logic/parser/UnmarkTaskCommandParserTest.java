package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnmarkTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

public class UnmarkTaskCommandParserTest {

    private UnmarkTaskCommandParser parser = new UnmarkTaskCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        // e.g. " w/W1 i/3"
        String userInput = " w/W1 i/3";
        UnmarkTaskCommand command = parser.parse(userInput);

        UnmarkTaskCommand expected = new UnmarkTaskCommand(new WeddingId("W1"), 3);
        assertEquals(expected, command);
    }

    @Test
    public void parse_missingWeddingIdPrefix_throwsParseException() {
        // e.g. " i/1"
        String userInput = " i/1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingTaskIndexPrefix_throwsParseException() {
        // e.g. " w/W1 1"
        String userInput = " w/W1 1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // e.g. " w/W1 i/abc"
        String userInput = " w/W1 i/abc";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_extraPreamble_throwsParseException() {
        // e.g. "preamble w/W1 i/1"
        String userInput = "preamble w/W1 i/1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

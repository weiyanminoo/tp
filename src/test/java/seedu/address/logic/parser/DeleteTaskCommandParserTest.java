package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

public class DeleteTaskCommandParserTest {

    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        // " w/W1 i/2"
        String userInput = " w/W1 i/2";
        DeleteTaskCommand command = parser.parse(userInput);

        // Expect weddingId=W1, taskIndex=2
        DeleteTaskCommand expected = new DeleteTaskCommand(new WeddingId("W1"), 2);
        assertEquals(expected, command);
    }

    @Test
    public void parse_missingWeddingIdPrefix_throwsParseException() {
        // e.g. " i/2"
        String userInput = " i/2";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingTaskIndexPrefix_throwsParseException() {
        // e.g. " w/W1 2"
        String userInput = " w/W1 2";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // e.g. " w/W1 i/notANumber"
        String userInput = " w/W1 i/notANumber";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_extraPreamble_throwsParseException() {
        // e.g. "preamble w/W1 i/1"
        String userInput = "preamble w/W1 i/1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

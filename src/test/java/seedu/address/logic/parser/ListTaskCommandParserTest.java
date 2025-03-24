package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

public class ListTaskCommandParserTest {

    private ListTaskCommandParser parser = new ListTaskCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        // e.g. " w/W1"
        String userInput = " w/W1";
        ListTaskCommand command = parser.parse(userInput);

        // We can't directly compare with equals() unless you implemented it,
        // but we can check the weddingId if needed. For brevity, assume
        // instanceOf check or a direct equals is fine:
        assertTrue(command.equals(new ListTaskCommand(new WeddingId("W1"))));
    }

    @Test
    public void parse_missingWeddingIdPrefix_throwsParseException() {
        // e.g. "W1" without "w/"
        String userInput = " W1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_extraPreamble_throwsParseException() {
        // e.g. "somePreamble w/W1"
        String userInput = "somePreamble w/W1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        // e.g. ""
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
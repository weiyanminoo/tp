package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.AddTaskCommand.MESSAGE_INVALID_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

public class AddTaskCommandParserTest {

    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        // Example: " w/W1 desc/Buy flowers"
        String userInput = " w/W1 desc/Buy flowers";
        AddTaskCommand command = parser.parse(userInput);

        // Expect an AddTaskCommand with weddingId=W1, description="Buy flowers"
        AddTaskCommand expected = new AddTaskCommand(new WeddingId("W1"), "Buy flowers");
        assertEquals(expected, command);
    }

    @Test
    public void parse_missingWeddingIdPrefix_throwsParseException() {
        // Missing "w/" prefix
        String userInput = " d/Buy flowers";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingDescriptionPrefix_throwsParseException() {
        // Missing "d/" prefix
        String userInput = " w/W1 Buy flowers";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        // Empty or no prefixes
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_extraPreamble_throwsParseException() {
        // If your parser disallows preamble text
        String userInput = "somePreamble w/W1 d/Buy flowers";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
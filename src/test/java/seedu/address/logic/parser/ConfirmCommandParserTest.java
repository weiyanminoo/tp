package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ConfirmCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ConfirmCommandParserTest {
    private ConfirmCommandParser parser = new ConfirmCommandParser();

    /**
     * Tests that an empty argument string returns a valid ConfirmCommand.
     */
    @Test
    public void parse_emptyArgs_returnsConfirmCommand() throws Exception {
        ConfirmCommand command = parser.parse("");
        assertEquals(new ConfirmCommand(), command);
    }

    /**
     * Tests that an argument string containing only whitespace returns a valid ConfirmCommand.
     */
    @Test
    public void parse_whitespaceArgs_returnsConfirmCommand() throws Exception {
        ConfirmCommand command = parser.parse(PREAMBLE_WHITESPACE);
        assertEquals(new ConfirmCommand(), command);
    }

    /**
     * Tests that any non-empty arguments cause a ParseException.
     */
    @Test
    public void parse_nonEmptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("unexpected argument"));
    }
}

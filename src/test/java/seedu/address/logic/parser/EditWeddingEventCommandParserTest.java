package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEDDING_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEDDING_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEDDING_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_DATE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_LOCATION_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_NAME_JOHN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditWeddingEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

public class EditWeddingEventCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeddingEventCommand.MESSAGE_USAGE);

    private final EditWeddingEventCommandParser parser = new EditWeddingEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_WEDDING_NAME_JOHN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // invalid index
        assertParseFailure(parser, "a" + " n/" + VALID_WEDDING_NAME_JOHN, MESSAGE_INVALID_FORMAT);

        // invalid index followed by valid wedding name
        assertParseFailure(parser, "a" + " n/" + VALID_WEDDING_NAME_JOHN, MESSAGE_INVALID_FORMAT);

        // invalid index followed by valid wedding date
        assertParseFailure(parser, "a" + " d/" + VALID_WEDDING_DATE_JOHN, MESSAGE_INVALID_FORMAT);

        // invalid index followed by valid wedding location
        assertParseFailure(parser, "a" + " l/" + VALID_WEDDING_LOCATION_JOHN, MESSAGE_INVALID_FORMAT);

        // invalid index followed by valid wedding name and date
        assertParseFailure(parser, "a" + " n/" + VALID_WEDDING_NAME_JOHN
                + " d/" + VALID_WEDDING_DATE_JOHN, MESSAGE_INVALID_FORMAT);

        // invalid index followed by valid wedding name and location
        assertParseFailure(parser, "a" + " n/" + VALID_WEDDING_NAME_JOHN
                + " l/" + VALID_WEDDING_LOCATION_JOHN, MESSAGE_INVALID_FORMAT);

        // invalid index followed by valid wedding date and location
        assertParseFailure(parser, "a" + " d/" + VALID_WEDDING_DATE_JOHN
                + " l/" + VALID_WEDDING_LOCATION_JOHN, MESSAGE_INVALID_FORMAT);

        // invalid index followed by valid wedding name, date and location
        assertParseFailure(parser, "a" + " n/" + VALID_WEDDING_NAME_JOHN + " d/" + VALID_WEDDING_DATE_JOHN
                + " l/" + VALID_WEDDING_LOCATION_JOHN, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // valid index followed by valid wedding name, date and location
        assertParseFailure(parser, "1" + " n/" + VALID_WEDDING_NAME_JOHN + " d/" + VALID_WEDDING_DATE_JOHN
                + " l/" + VALID_WEDDING_LOCATION_JOHN, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        // valid index followed by valid wedding name
        assertParseFailure(parser, "1" + " n/" + VALID_WEDDING_NAME_JOHN, MESSAGE_INVALID_FORMAT);

        // valid index followed by valid wedding date
        assertParseFailure(parser, "1" + " d/" + VALID_WEDDING_DATE_JOHN, MESSAGE_INVALID_FORMAT);

        // valid index followed by valid wedding location
        assertParseFailure(parser, "1" + " l/" + VALID_WEDDING_LOCATION_JOHN, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // valid index followed by invalid wedding name
        assertParseFailure(parser, "1" + " n/" + INVALID_WEDDING_NAME_DESC, MESSAGE_INVALID_FORMAT);

        // valid index followed by invalid wedding date
        assertParseFailure(parser, "1" + " d/" + INVALID_WEDDING_DATE_DESC, MESSAGE_INVALID_FORMAT);

        // valid index followed by invalid wedding location
        assertParseFailure(parser, "1" + " l/" + INVALID_WEDDING_LOCATION_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidWeddingId_throwsParseException() {
        // Arrange
        String userInput = "a n/John's Wedding d/2023-12-25 l/Beach";

        // Act & Assert
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noFieldsSpecified_throwsParseException() {
        // Arrange
        String userInput = "1";

        // Act & Assert
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        // Arrange
        String userInput = "1 n/John's Wedding n/Another Wedding d/2023-12-25 l/Beach";

        // Act & Assert
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noWeddingId_throwsParseException() {
        // Arrange
        String userInput = "n/John's Wedding d/2023-12-25 l/Beach";

        // Act & Assert
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

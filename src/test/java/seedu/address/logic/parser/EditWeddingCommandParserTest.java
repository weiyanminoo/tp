package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEDDING_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEDDING_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEDDING_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_DATE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_LOCATION_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_NAME_JOHN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

public class EditWeddingCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeddingCommand.MESSAGE_USAGE);

    private final EditWeddingCommandParser parser = new EditWeddingCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_WEDDING_NAME_JOHN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "W1", EditWeddingCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // invalid index
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);

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
    public void parse_invalidFieldValues_failure() {
        // invalid wedding name
        assertParseFailure(parser, "W1" + INVALID_WEDDING_NAME_DESC,
                WeddingName.MESSAGE_CONSTRAINTS);

        // invalid wedding date
        assertParseFailure(parser, "W1" + INVALID_WEDDING_DATE_DESC,
                WeddingDate.MESSAGE_CONSTRAINTS);

        // invalid wedding location
        assertParseFailure(parser, "W1" + INVALID_WEDDING_LOCATION_DESC,
                WeddingLocation.MESSAGE_CONSTRAINTS);

        // valid index with invalid name and valid date
        assertParseFailure(parser, "W1" + INVALID_WEDDING_NAME_DESC + " d/" + VALID_WEDDING_DATE_JOHN,
                WeddingName.MESSAGE_CONSTRAINTS);

        // valid index with valid name and invalid date
        assertParseFailure(parser, "W1 n/" + VALID_WEDDING_NAME_JOHN + INVALID_WEDDING_DATE_DESC,
                WeddingDate.MESSAGE_CONSTRAINTS);

        // valid index with invalid date and invalid location
        assertParseFailure(parser, "W1" + INVALID_WEDDING_DATE_DESC + INVALID_WEDDING_LOCATION_DESC,
                WeddingDate.MESSAGE_CONSTRAINTS);

        // all fields invalid
        assertParseFailure(parser, "W1" + INVALID_WEDDING_NAME_DESC + INVALID_WEDDING_DATE_DESC
                + INVALID_WEDDING_LOCATION_DESC, WeddingName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        EditWeddingCommand.EditWeddingDescriptor descriptor = new EditWeddingCommand.EditWeddingDescriptor();
        try {
            descriptor.setWeddingName(ParserUtil.parseWeddingName(VALID_WEDDING_NAME_JOHN));
            descriptor.setWeddingDate(ParserUtil.parseWeddingDate(VALID_WEDDING_DATE_JOHN));
            descriptor.setWeddingLocation(ParserUtil.parseWeddingLocation(VALID_WEDDING_LOCATION_JOHN));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid test data", pe);
        }
        EditWeddingCommand expectedCommand = new EditWeddingCommand(new WeddingId("W1"), descriptor);

        assertParseSuccess(parser, "W1" + " n/" + VALID_WEDDING_NAME_JOHN + " d/" + VALID_WEDDING_DATE_JOHN
                + " l/" + VALID_WEDDING_LOCATION_JOHN, expectedCommand);
    }

    @Test
    public void parse_oneFieldsSpecified_success() {
        // name only
        EditWeddingCommand.EditWeddingDescriptor nameDescriptor = new EditWeddingCommand.EditWeddingDescriptor();
        try {
            nameDescriptor.setWeddingName(ParserUtil.parseWeddingName(VALID_WEDDING_NAME_JOHN));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid test data", pe);
        }
        EditWeddingCommand expectedNameCommand = new EditWeddingCommand(new WeddingId("W1"), nameDescriptor);
        assertParseSuccess(parser, "W1 n/" + VALID_WEDDING_NAME_JOHN, expectedNameCommand);

        // date only
        EditWeddingCommand.EditWeddingDescriptor dateDescriptor = new EditWeddingCommand.EditWeddingDescriptor();
        try {
            dateDescriptor.setWeddingDate(ParserUtil.parseWeddingDate(VALID_WEDDING_DATE_JOHN));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid test data", pe);
        }
        EditWeddingCommand expectedDateCommand = new EditWeddingCommand(new WeddingId("W1"), dateDescriptor);
        assertParseSuccess(parser, "W1 d/" + VALID_WEDDING_DATE_JOHN, expectedDateCommand);

        // location only
        EditWeddingCommand.EditWeddingDescriptor locationDescriptor = new EditWeddingCommand.EditWeddingDescriptor();
        try {
            locationDescriptor.setWeddingLocation(ParserUtil.parseWeddingLocation(VALID_WEDDING_LOCATION_JOHN));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid test data", pe);
        }
        EditWeddingCommand expectedLocationCommand = new EditWeddingCommand(new WeddingId("W1"), locationDescriptor);
        assertParseSuccess(parser, "W1 l/" + VALID_WEDDING_LOCATION_JOHN, expectedLocationCommand);
    }

    @Test
    public void parse_multipleFieldsSpecified_success() {
        // name and date
        EditWeddingCommand.EditWeddingDescriptor nameAndDateDescriptor = new EditWeddingCommand.EditWeddingDescriptor();
        try {
            nameAndDateDescriptor.setWeddingName(ParserUtil.parseWeddingName(VALID_WEDDING_NAME_JOHN));
            nameAndDateDescriptor.setWeddingDate(ParserUtil.parseWeddingDate(VALID_WEDDING_DATE_JOHN));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid test data", pe);
        }
        EditWeddingCommand expectedNameAndDateCommand =
                new EditWeddingCommand(new WeddingId("W1"), nameAndDateDescriptor);
        assertParseSuccess(parser, "W1 n/" + VALID_WEDDING_NAME_JOHN + " d/" + VALID_WEDDING_DATE_JOHN,
                expectedNameAndDateCommand);

        // name and location
        EditWeddingCommand.EditWeddingDescriptor nameAndLocationDescriptor =
                new EditWeddingCommand.EditWeddingDescriptor();
        try {
            nameAndLocationDescriptor.setWeddingName(ParserUtil.parseWeddingName(VALID_WEDDING_NAME_JOHN));
            nameAndLocationDescriptor.setWeddingLocation(ParserUtil.parseWeddingLocation(VALID_WEDDING_LOCATION_JOHN));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid test data", pe);
        }
        EditWeddingCommand expectedNameAndLocationCommand = new EditWeddingCommand(new WeddingId("W1"),
                nameAndLocationDescriptor);
        assertParseSuccess(parser, "W1 n/" + VALID_WEDDING_NAME_JOHN + " l/" + VALID_WEDDING_LOCATION_JOHN,
                expectedNameAndLocationCommand);

        // date and location
        EditWeddingCommand.EditWeddingDescriptor dateAndLocationDescriptor =
                new EditWeddingCommand.EditWeddingDescriptor();
        try {
            dateAndLocationDescriptor.setWeddingDate(ParserUtil.parseWeddingDate(VALID_WEDDING_DATE_JOHN));
            dateAndLocationDescriptor.setWeddingLocation(ParserUtil.parseWeddingLocation(VALID_WEDDING_LOCATION_JOHN));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid test data", pe);
        }
        EditWeddingCommand expectedDateAndLocationCommand = new EditWeddingCommand(new WeddingId("W1"),
                dateAndLocationDescriptor);
        assertParseSuccess(parser, "W1 d/" + VALID_WEDDING_DATE_JOHN + " l/" + VALID_WEDDING_LOCATION_JOHN,
                expectedDateAndLocationCommand);
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

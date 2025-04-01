package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_DATE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_LOCATION_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_NAME_JOHN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains unit tests for {@code AddWeddingCommandParser}.
 */
public class AddWeddingCommandParserTest {

    private static final String WEDDING_NAME_DESC_JOHN = " n/" + VALID_WEDDING_NAME_JOHN;
    private static final String WEDDING_DATE_DESC_JOHN = " d/" + VALID_WEDDING_DATE_JOHN;
    private static final String WEDDING_LOCATION_DESC_JOHN = " l/" + VALID_WEDDING_LOCATION_JOHN;

    private final AddWeddingCommandParser parser = new AddWeddingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        // Example user input: " n/John & Jane's Wedding d/20-Dec-2025 l/Beach Resort"
        String userInput = WEDDING_NAME_DESC_JOHN + WEDDING_DATE_DESC_JOHN + WEDDING_LOCATION_DESC_JOHN;

        // We expect a successful parse, producing an AddWeddingCommand
        Wedding expectedWedding = new Wedding(
                new WeddingName(VALID_WEDDING_NAME_JOHN),
                new WeddingDate(VALID_WEDDING_DATE_JOHN),
                new WeddingLocation(VALID_WEDDING_LOCATION_JOHN)
        );

        AddWeddingCommand expectedCommand = new AddWeddingCommand(expectedWedding);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingFields_failure() {
        // Missing wedding name prefix
        String userInputMissingName = " d/20-Dec-2025 l/Beach Resort";
        assertParseFailure(parser, userInputMissingName,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWeddingCommand.MESSAGE_USAGE));

        // Missing date prefix
        String userInputMissingDate = " n/John & Jane's Wedding l/Beach Resort";
        assertParseFailure(parser, userInputMissingDate,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWeddingCommand.MESSAGE_USAGE));

        // Missing location prefix
        String userInputMissingLocation = " n/John & Jane's Wedding d/20-Dec-2025";
        assertParseFailure(parser, userInputMissingLocation,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWeddingCommand.MESSAGE_USAGE));
    }
}

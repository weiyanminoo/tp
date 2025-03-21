package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

/**
 * Parses input arguments and creates a new AddWeddingCommand object.
 */
public class AddWeddingCommandParser implements Parser<AddWeddingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddWeddingCommand
     * and returns an AddWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public AddWeddingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        // Check that all required prefixes are present
        if (!arePrefixesPresent(argMultimap,
                PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, seedu.address.logic.commands.AddWeddingCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes for the required fields
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        // Extract field values
        WeddingName weddingName = ParserUtil.parseWeddingName(argMultimap.getValue(PREFIX_WEDDING_NAME).get());
        WeddingDate weddingDate = ParserUtil.parseWeddingDate(argMultimap.getValue(PREFIX_WEDDING_DATE).get());
        WeddingLocation location = ParserUtil.parseWeddingLocation(argMultimap.getValue(PREFIX_WEDDING_LOCATION).get());

        Wedding wedding = new Wedding(weddingName, weddingDate, location);

        return new AddWeddingCommand(wedding);
    }

    /**
     * Returns true if all specified prefixes contain non-empty values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

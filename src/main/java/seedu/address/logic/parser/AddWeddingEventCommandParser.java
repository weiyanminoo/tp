package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddWeddingEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;

/**
 * Parses input arguments and creates a new AddWeddingEventCommand object.
 */
public class  AddWeddingEventCommandParser implements Parser<AddWeddingEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddWeddingEventCommand
     * and returns an AddWeddingEventCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public AddWeddingEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        // Check that all required prefixes are present
        if (!arePrefixesPresent(argMultimap,
                PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddWeddingEventCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes for the required fields
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        // Extract field values
        String weddingName = argMultimap.getValue(PREFIX_WEDDING_NAME).get();
        String weddingDate = argMultimap.getValue(PREFIX_WEDDING_DATE).get();
        String location = argMultimap.getValue(PREFIX_WEDDING_LOCATION).get();

        Wedding wedding = new Wedding(weddingName, weddingDate, location);

        return new AddWeddingEventCommand(wedding);
    }

    /**
     * Returns true if all specified prefixes contain non-empty values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

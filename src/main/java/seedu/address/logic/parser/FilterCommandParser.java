package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        // Trim any leading/trailing spaces
        String trimmedArgs = args.trim();

        // If user didn't provide a tag
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format("Invalid command format.\n"
                    + FilterCommand.MESSAGE_USAGE));
        }

        // Create a new FilterCommand with the user-specified tag
        return new FilterCommand(trimmedArgs);
    }
}


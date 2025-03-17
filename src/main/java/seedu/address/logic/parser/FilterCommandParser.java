package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @param args the input arguments provided by the user.
     * @return a new FilterCommand with the specified wedding id.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FilterCommand parse(String args) throws ParseException {
        // Trim any trailing spaces.
        String trimmedArgs = args.trim();
        String[] tokens = trimmedArgs.split("\\s+");

        // If the user didn't provide a wedding id or provides more than 1 wedding id
        if (tokens.length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + FilterCommand.MESSAGE_USAGE));
        }

        try {
            WeddingId weddingId = ParserUtil.parseWeddingId(tokens[0]);
            return new FilterCommand(weddingId);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage() + "\n" + FilterCommand.MESSAGE_USAGE, pe);
        }
    }
}


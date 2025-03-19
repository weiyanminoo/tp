package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;


/**
 * Parses input arguments and creates a new DeleteWeddingCommand object.
 */
public class DeleteWeddingCommandParser implements Parser<DeleteWeddingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteWeddingCommand
     * and returns a DeleteWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteWeddingCommand parse(String args) throws ParseException {
        try {
            WeddingId weddingId = ParserUtil.parseWeddingId(args);
            return new DeleteWeddingCommand(weddingId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteWeddingCommand.MESSAGE_USAGE), pe);
        }
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

/**
 * Parses input arguments and creates a new TagCommand object.
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     *
     * @param args the input arguments from the user.
     * @return a TagCommand with the parsed index and wedding id.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public TagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] tokens = trimmedArgs.split("\\s+");

        // Expecting exactly two tokens: index and wedding id.
        if (tokens.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(tokens[0]);
        WeddingId weddingId = ParserUtil.parseWeddingId(tokens[1]);
        return new TagCommand(index, weddingId);
    }
}

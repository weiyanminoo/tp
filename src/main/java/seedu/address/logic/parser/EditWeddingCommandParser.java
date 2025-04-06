package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

import seedu.address.logic.commands.EditWeddingCommand;
import seedu.address.logic.commands.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

/**
 * Parses input arguments and creates a new EditWeddingCommand object.
 */
public class EditWeddingCommandParser implements Parser<EditWeddingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditWeddingCommand
     * and returns an EditWeddingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public EditWeddingCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        WeddingId index;
        try {
            index = ParserUtil.parseWeddingId(argMultimap.getPreamble());
        } catch (ParseException pe) {
            if (pe.getMessage().equals(WeddingId.MESSAGE_NEGATIVE_CONSTRAINTS)) {
                throw pe;
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditWeddingCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        EditWeddingDescriptor editWeddingDescriptor = new EditWeddingDescriptor();

        if (argMultimap.getValue(PREFIX_WEDDING_NAME).isPresent()) {
            editWeddingDescriptor.setWeddingName(
                    ParserUtil.parseWeddingName(argMultimap.getValue(PREFIX_WEDDING_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_WEDDING_DATE).isPresent()) {
            editWeddingDescriptor.setWeddingDate(
                    ParserUtil.parseWeddingDate(argMultimap.getValue(PREFIX_WEDDING_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_WEDDING_LOCATION).isPresent()) {
            editWeddingDescriptor.setWeddingLocation(
                    ParserUtil.parseWeddingLocation(argMultimap.getValue(PREFIX_WEDDING_LOCATION).get()));
        }

        if (!editWeddingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditWeddingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditWeddingCommand(index, editWeddingDescriptor);
    }
}

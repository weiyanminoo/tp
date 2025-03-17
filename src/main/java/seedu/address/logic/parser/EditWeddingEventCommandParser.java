package seedu.address.logic.parser;

import seedu.address.logic.commands.EditWeddingEventCommand;
import seedu.address.logic.commands.EditWeddingEventCommand.EditWeddingDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.UniqueWeddingList;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new EditWeddingEventCommand object.
 */
public class EditWeddingEventCommandParser implements Parser<EditWeddingEventCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the EditWeddingEventCommand
     * and returns an EditWeddingEventCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public EditWeddingEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        System.out.println("EditWeddingEventCommandParser called");
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        WeddingId index;
        try {
            System.out.println("argMultiMap: " + argMultimap.getPreamble());
            System.out.println("reach try print out index");
            index = ParserUtil.parseWeddingId(argMultimap.getPreamble());
            System.out.println("Wedding ID: " + index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeddingEventCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        EditWeddingDescriptor editWeddingDescriptor = new EditWeddingDescriptor();
        System.out.println("EditWeddingDescriptor created");

        if (argMultimap.getValue(PREFIX_WEDDING_NAME).isPresent()) {
            System.out.println("Wedding Name edit called: " + argMultimap.getValue(PREFIX_WEDDING_NAME).get());
            editWeddingDescriptor.setWeddingName(ParserUtil.parseWeddingName(argMultimap.getValue(PREFIX_WEDDING_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_WEDDING_DATE).isPresent()) {
            editWeddingDescriptor.setWeddingDate(ParserUtil.parseWeddingDate(argMultimap.getValue(PREFIX_WEDDING_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_WEDDING_LOCATION).isPresent()) {
            editWeddingDescriptor.setWeddingLocation(ParserUtil.parseWeddingLocation(argMultimap.getValue(PREFIX_WEDDING_LOCATION).get()));
        }

        if (!editWeddingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditWeddingEventCommand.MESSAGE_NOT_EDITED);
        }

        System.out.println("reached before creating EditWeddingEventCommand");
        return new EditWeddingEventCommand(index, editWeddingDescriptor);
    }
}

package seedu.address.logic.parser;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

import java.util.stream.Stream;

public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    private static final Prefix PREFIX_WEDDING_ID = new Prefix("w/");
    private static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");

    @Override
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING_ID, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_WEDDING_ID, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(AddTaskCommand.MESSAGE_INVALID_FORMAT);
        }

        String weddingIdStr = argMultimap.getValue(PREFIX_WEDDING_ID).get();
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();

        WeddingId weddingId = new WeddingId(weddingIdStr);

        return new AddTaskCommand(weddingId, description);
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

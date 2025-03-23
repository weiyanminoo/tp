package seedu.address.logic.parser;

import seedu.address.logic.commands.ListTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

import java.util.stream.Stream;

public class ListTaskCommandParser implements Parser<ListTaskCommand> {

    private static final Prefix PREFIX_WEDDING_ID = new Prefix("w/");

    @Override
    public ListTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_WEDDING_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ListTaskCommand.MESSAGE_USAGE);
        }

        String weddingIdStr = argMultimap.getValue(PREFIX_WEDDING_ID).get();
        WeddingId weddingId = new WeddingId(weddingIdStr);

        return new ListTaskCommand(weddingId);
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

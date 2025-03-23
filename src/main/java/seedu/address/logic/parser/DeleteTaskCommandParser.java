package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

import java.util.stream.Stream;

public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    private static final Prefix PREFIX_WEDDING_ID = new Prefix("w/");
    private static final Prefix PREFIX_INDEX = new Prefix("i/");

    @Override
    public DeleteTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING_ID, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_WEDDING_ID, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(DeleteTaskCommand.MESSAGE_USAGE);
        }

        String weddingIdStr = argMultimap.getValue(PREFIX_WEDDING_ID).get();
        String indexStr = argMultimap.getValue(PREFIX_INDEX).get();

        WeddingId weddingId = new WeddingId(weddingIdStr);
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            throw new ParseException("Task index must be an integer.");
        }

        return new DeleteTaskCommand(weddingId, taskIndex);
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

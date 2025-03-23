package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_ID;

import seedu.address.logic.commands.UnmarkTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

import java.util.stream.Stream;


public class UnmarkTaskCommandParser implements Parser<UnmarkTaskCommand> {

    @Override
    public UnmarkTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING_ID, PREFIX_TASK_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_WEDDING_ID, PREFIX_TASK_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(UnmarkTaskCommand.MESSAGE_USAGE);
        }

        String weddingIdStr = argMultimap.getValue(PREFIX_WEDDING_ID).get();
        String indexStr = argMultimap.getValue(PREFIX_TASK_INDEX).get();

        WeddingId weddingId = new WeddingId(weddingIdStr);

        int taskIndex;
        try {
            taskIndex = Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            throw new ParseException("Task index must be a positive integer.");
        }

        return new UnmarkTaskCommand(weddingId, taskIndex);
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

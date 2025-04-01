package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts the wedding list by wedding date.
 */
public class SortByWeddingDateCommand extends Command {
    public static final String COMMAND_WORD = "sortWDate";
    public static final String MESSAGE_SUCCESS = "Wedding list sorted by Date";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setSortWeddingsByDate(true);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof SortByWeddingDateCommand;
    }

    @Override
    public int hashCode() {
        return SortByWeddingDateCommand.class.hashCode();
    }
}

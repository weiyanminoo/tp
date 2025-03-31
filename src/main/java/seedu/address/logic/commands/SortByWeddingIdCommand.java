package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts the wedding list by wedding ID.
 */
public class SortByWeddingIdCommand extends Command {
    public static final String COMMAND_WORD = "sortWID";
    public static final String MESSAGE_SUCCESS = "Wedding list sorted by Wedding ID";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setSortWeddingsById();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof SortByWeddingIdCommand;
    }

    @Override
    public int hashCode() {
        return SortByWeddingIdCommand.class.hashCode();
    }
}


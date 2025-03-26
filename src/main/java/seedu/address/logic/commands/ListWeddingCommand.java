package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all wedding events in the address book to the user.
 */
public class ListWeddingCommand extends Command {
    public static final String COMMAND_WORD = "listWedding";
    public static final String MESSAGE_SUCCESS = "All wedding events: ";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setSortWeddingsByDate(false);
        model.updateFilteredWeddingList(Model.PREDICATE_SHOW_ALL_WEDDINGS);

        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof ListWeddingCommand);
    }
}

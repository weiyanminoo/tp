package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all wedding events in the address book to the user, sorted by date.
 */
public class ListWeddingByDateCommand extends Command {

    public static final String COMMAND_WORD = "listWeddingByDate";
    public static final String MESSAGE_SUCCESS = "All wedding events sorted by date: ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all weddings sorted by date.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredWeddingList(Model.PREDICATE_SHOW_ALL_WEDDINGS);
        model.setSortWeddingsByDate(true);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof ListWeddingByDateCommand);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;



/**
 * Lists all wedding events in the address book to the user.
 */
public class ListWeddingEventCommand extends Command {
    public static final String COMMAND_WORD = "listWedding";
    public static final String MESSAGE_SUCCESS = "Listed all wedding events";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String test = model.getFilteredWeddingList().toString();
        return new CommandResult(String.format(MESSAGE_SUCCESS + test));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof ListWeddingEventCommand);
    }
}

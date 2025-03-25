package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;


/**
 * Lists all wedding events in the address book to the user.
 */
public class ListWeddingCommand extends Command {
    public static final String COMMAND_WORD = "listWedding";
    public static final String MESSAGE_SUCCESS = "All wedding events: ";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredWeddingList(Model.PREDICATE_SHOW_ALL_WEDDINGS);

        StringBuilder output = new StringBuilder();
        for (Wedding wedding : model.getFilteredWeddingList()) {
            output.append("\nWedding: ").append(wedding.getWeddingName().toString())
                    .append(" (ID: ").append(wedding.getWeddingId().toString()).append(")")
                    .append("\nDate: ").append(wedding.getWeddingDate().toString())
                    .append("\nLocation: ").append(wedding.getWeddingLocation().toString())
                    .append("\n");
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getFilteredWeddingList().size()),
                false, false, true, false); // Set showWeddingList to true
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof ListWeddingCommand);
    }
}

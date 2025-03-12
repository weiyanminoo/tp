package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a Wedding event to the address book.
 */
public class AddWeddingEventCommand extends Command {

    public static final String COMMAND_WORD = "addWedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wedding to the address book. "
            + "Parameters: "
            + "id/WEDDING_ID n/NAME d/DATE l/LOCATION\n"
            + "Example: " + COMMAND_WORD + " id/W-001 n/John & Jane's Wedding d/20-Feb-2025 l/Grand Ballroom";

    public static final String MESSAGE_SUCCESS = "New wedding added: %1$s";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book";

    private final Wedding toAdd;

    /**
     * Creates an AddWeddingEventCommand to add the specified {@code Wedding}.
     */
    public AddWeddingEventCommand(Wedding wedding) {
        requireNonNull(wedding);
        toAdd = wedding;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasWedding(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        model.addWedding(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddWeddingEventCommand
                && toAdd.equals(((AddWeddingEventCommand) other).toAdd));
    }
}

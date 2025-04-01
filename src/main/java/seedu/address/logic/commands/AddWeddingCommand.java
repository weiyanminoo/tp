package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a Wedding event to the contact book.
 */
public class AddWeddingCommand extends Command {

    public static final String COMMAND_WORD = "addWedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wedding to the contact book. "
            + "Parameters: "
            + "n/NAME d/DATE l/LOCATION\n"
            + "Example: " + COMMAND_WORD + " n/John & Jane's Wedding d/20-Feb-2025 l/Grand Ballroom";

    public static final String MESSAGE_SUCCESS = "New wedding added: %1$s";

    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the contact book";

    private final Wedding toAdd;

    /**
     * Creates an AddWeddingCommand to add the specified {@code Wedding}.
     */
    public AddWeddingCommand(Wedding wedding) {
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
                || (other instanceof AddWeddingCommand
                && toAdd.equals(((AddWeddingCommand) other).toAdd));
    }
}

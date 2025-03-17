package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.TagMatchesPredicate;
import seedu.address.model.wedding.WeddingId;

/**
 * Filters the list of persons by the specified wedding id.
 * For example, "filter W12345" will display only persons whose tag matches wedding id "W12345".
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the list of persons by the specified wedding id.\n"
            + "Parameters: WEDDING_ID\n"
            + "Example: " + COMMAND_WORD + " W12345";

    public static final String MESSAGE_SUCCESS = "Filtered persons by wedding id: %1$s";

    private final WeddingId weddingIdToFilter;

    /**
     * Creates a FilterCommand to filter persons by the given wedding id.
     *
     * @param weddingIdToFilter the wedding id to filter persons by.
     */
    public FilterCommand(WeddingId weddingIdToFilter) {
        requireNonNull(weddingIdToFilter);
        this.weddingIdToFilter = weddingIdToFilter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if wedding id exists in the system.
        boolean weddingExists = model.getFilteredWeddingList().stream()
                .anyMatch(wedding -> wedding.getWeddingId().equals(weddingIdToFilter));
        if (!weddingExists) {
            throw new CommandException("The specified wedding id " + weddingIdToFilter + " does not exist.");
        }

        // Update person list using the TagMatchesPredicate.
        model.updateFilteredPersonList(new TagMatchesPredicate(weddingIdToFilter));
        return new CommandResult(String.format(MESSAGE_SUCCESS, weddingIdToFilter));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterCommand
                && weddingIdToFilter.equals(((FilterCommand) other).weddingIdToFilter));
    }
}

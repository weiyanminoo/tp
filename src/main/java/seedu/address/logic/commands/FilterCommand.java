package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.TagMatchesPredicate;

/**
 * Filters the list of persons by a specified tag.
 * For example, "filter client" will display only persons whose tag is "Client".
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the list of persons by the specified tag.\n"
            + "Parameters: TAG (must be 'Client' or 'Vendor')\n"
            + "Example: " + COMMAND_WORD + " vendor";

    public static final String MESSAGE_SUCCESS = "Filtered persons by tag: %1$s";
    public static final String MESSAGE_NO_SUCH_TAG = "The specified tag is invalid: %1$s\n"
            + "Tag must be 'Client' or 'Vendor'.";

    private final String tagToFilter;

    /**
     * Creates a FilterCommand to filter persons by the given {@code tagToFilter}.
     */
    public FilterCommand(String tagToFilter) {
        this.tagToFilter = tagToFilter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!tagToFilter.equalsIgnoreCase("Client") && !tagToFilter.equalsIgnoreCase("Vendor")) {
            throw new CommandException(String.format(MESSAGE_NO_SUCH_TAG, tagToFilter));
        }

        model.updateFilteredPersonList(new TagMatchesPredicate(tagToFilter));
        return new CommandResult(String.format(MESSAGE_SUCCESS, tagToFilter));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterCommand
                && tagToFilter.equalsIgnoreCase(((FilterCommand) other).tagToFilter));
    }

}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.TagMatchesPredicate;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;

/**
 * Filters the list of persons by the specified wedding id.
 * For example, "filter W12345" will display only persons whose tag matches
 * wedding id "W12345".
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the list of persons by the specified wedding id.\n"
            + "Parameters: WEDDING_ID\n"
            + "Example: " + COMMAND_WORD + " W12345";

    public static final String MESSAGE_SUCCESS = "Filtered persons by wedding:\n"
            + "ID: %1$s\n"
            + "Name: %2$s\n";

    public static final String MESSAGE_NO_PERSONS_FOUND = "Filtered persons by wedding:\n"
            + "ID: %1$s\n"
            + "Name: %2$s\n"
            + "No clients/vendors found for this wedding!";

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

        // Check if wedding id exists and get the wedding object
        Wedding matchingWedding = model.getAddressBook().getWeddingList().stream()
                .filter(wedding -> wedding.getWeddingId().equals(weddingIdToFilter))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(Messages.MESSAGE_WEDDING_NOT_FOUND,
                        weddingIdToFilter.value)));

        // Update person list using the TagMatchesPredicate.
        model.updateFilteredPersonList(new TagMatchesPredicate(weddingIdToFilter));

        // Show only the matching wedding:
        model.updateFilteredWeddingList(wedding -> wedding.equals(matchingWedding));

        // Check how many persons matched
        int count = model.getFilteredPersonList().size();
        if (count == 0) {
            return new CommandResult(String.format(MESSAGE_NO_PERSONS_FOUND,
                    matchingWedding.getWeddingId().value,
                    matchingWedding.getWeddingName().fullWeddingName,
                    matchingWedding.getWeddingDate().value,
                    matchingWedding.getWeddingLocation().venue));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                matchingWedding.getWeddingId(),
                matchingWedding.getWeddingName(),
                matchingWedding.getWeddingDate(),
                matchingWedding.getWeddingLocation()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterCommand
                        && weddingIdToFilter.equals(((FilterCommand) other).weddingIdToFilter));
    }
}

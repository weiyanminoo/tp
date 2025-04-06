package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagMatchesPredicate;
import seedu.address.model.wedding.WeddingId;

/**
 * Untags a person identified by their index in the filtered person list by removing a WeddingId tag.
 */
public class UntagCommand extends Command {

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the wedding tag from the person identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer) WEDDING_ID\n"
            + "Example: " + COMMAND_WORD + " 1 W12345";

    public static final String MESSAGE_UNTAG_PERSON_SUCCESS = "Untagged Person: %1$s from WeddingId: %2$s";
    public static final String MESSAGE_PERSON_NOT_TAGGED = "This person is not tagged with the specified wedding id.";

    private final Index targetIndex;
    private final WeddingId weddingId;

    /**
     * Constructs an {@code UntagCommand}.
     *
     * @param targetIndex of the person in the filtered person list to untag.
     * @param weddingId   the wedding id tag to remove from the person.
     */
    public UntagCommand(Index targetIndex, WeddingId weddingId) {
        this.targetIndex = targetIndex;
        this.weddingId = weddingId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUntag = lastShownList.get(targetIndex.getZeroBased());
        Tag tag = new Tag(weddingId);

        // Check if person has the specified tag
        if (!personToUntag.getTags().contains(tag)) {
            throw new CommandException(MESSAGE_PERSON_NOT_TAGGED);
        }

        // Untag the person
        model.untagPerson(personToUntag, tag);

        boolean isFiltered = model.getFilteredWeddingList().size() < model.getAddressBook().getWeddingList().size();

        if (isFiltered) {
            model.updateFilteredPersonList(new TagMatchesPredicate(weddingId)); // reapply the filter.
        } else {
            model.updateFilteredPersonList(p -> true); // Reset the filtered lists to show all.
            model.updateFilteredWeddingList(w -> true);
        }

        return new CommandResult(String.format(MESSAGE_UNTAG_PERSON_SUCCESS,
                personToUntag.getName().fullName, weddingId));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UntagCommand)) {
            return false;
        }

        UntagCommand otherUntagCommand = (UntagCommand) other;
        return targetIndex.equals(otherUntagCommand.targetIndex)
                && weddingId.equals(otherUntagCommand.weddingId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("weddingId", weddingId)
                .toString();
    }
}

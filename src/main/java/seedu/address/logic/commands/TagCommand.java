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
import seedu.address.model.wedding.WeddingId;

/**
 * Tags a person identified by their index in the filtered person list with a WeddingId.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the person identified by the index number with a wedding id.\n"
            + "Parameters: INDEX (must be a positive integer) WEDDING_ID\n"
            + "Example: " + COMMAND_WORD + " 1 W12345";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Tagged Person: %1$s with WeddingId: %2$s";

    public static final String MESSAGE_TAG_PERSON_DUPLICATE_TAG = "This person is already tagged to %1$s";

    private final Index targetIndex;
    private final WeddingId weddingId;

    /**
     * Constructs a {@code TagCommand}.
     *
     * @param targetIndex of the person in the filtered person list to tag.
     * @param weddingId   the wedding id to tag the person with.
     */
    public TagCommand(Index targetIndex, WeddingId weddingId) {
        this.targetIndex = targetIndex;
        this.weddingId = weddingId;
    }

    /**
     * Executes the tag command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the command result.
     * @throws CommandException if the index is invalid or the wedding id does not exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Check if wedding id exists in the system.
        boolean weddingExists = model.getFilteredWeddingList().stream()
                .anyMatch(wedding -> wedding.getWeddingId().equals(weddingId));
        if (!weddingExists) {
            throw new CommandException(String.format(Messages.MESSAGE_WEDDING_NOT_FOUND, weddingId.value));
        }

        Person personToTag = lastShownList.get(targetIndex.getZeroBased());

        if (personToTag.getTags().stream().anyMatch(tag -> tag.getWeddingId().equals(weddingId))) {
            throw new CommandException(String.format(MESSAGE_TAG_PERSON_DUPLICATE_TAG, weddingId.value));
        }

        Tag tag = new Tag(weddingId);

        // Tag the person
        model.tagPerson(personToTag, tag);

        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, personToTag.getName().fullName, weddingId));
    }

    /**
     * Returns true if both TagCommands have the same target index and wedding id.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return targetIndex.equals(otherTagCommand.targetIndex)
                && weddingId.equals(otherTagCommand.weddingId);
    }

    /**
     * Returns the string representation of the TagCommand.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("weddingId", weddingId)
                .toString();
    }
}

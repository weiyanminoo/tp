package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;



/**
 * Deletes a Wedding event from the contact book.
 */
public class DeleteWeddingCommand extends Command {

    public static final String COMMAND_WORD = "deleteWedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a wedding from the contact book. "
            + "Parameters: WEDDING_ID\n"
            + "Example: " + COMMAND_WORD + " W1";

    public static final String MESSAGE_SUCCESS = "Deleted wedding: %1$s";

    private final WeddingId weddingId;

    /**
     * Creates a DeleteWeddingCommand to delete the specified {@code Wedding}.
     */
    public DeleteWeddingCommand(WeddingId weddingId) {
        requireNonNull(weddingId);
        this.weddingId = weddingId;
    }

    /**
     * Executes the command and deletes the specified {@code Wedding}.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} representing the result of the deletion.
     * @throws CommandException If the specified {@code Wedding} does not exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Wedding weddingToDelete = model.getWeddingById(weddingId);

        if (weddingToDelete == null) {
            throw new CommandException(String.format(Messages.MESSAGE_WEDDING_NOT_FOUND, weddingId.value));
        }

        model.deleteWedding(weddingToDelete);

        Tag tagToDelete = new Tag(weddingId);
        model.removeTagFromAllContacts(tagToDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, weddingToDelete));
    }

    /**
     * Returns true if both DeleteWeddingCommands have the same wedding id.
     * @param other The other object to compare to.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteWeddingCommand
                && weddingId.equals(((DeleteWeddingCommand) other).weddingId));
    }
}

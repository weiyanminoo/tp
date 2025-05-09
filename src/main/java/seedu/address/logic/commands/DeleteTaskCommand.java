package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingTask;

/**
 * Deletes a task to a specific wedding identified by a Wedding ID.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "deleteTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a task from a specified wedding.\n"
            + "Parameters: w/WEDDING_ID i/TASK_INDEX\n"
            + "Example: " + COMMAND_WORD + " w/W1 i/2";

    public static final String MESSAGE_SUCCESS = "Deleted task from wedding %1$s:\n%2$s";
    public static final String MESSAGE_INVALID_TASK_INDEX = "Invalid task index for wedding %1$s.";

    private final WeddingId weddingId;
    private final int taskIndex;

    /**
     * Constructs a DeleteTaskCommand to remove the specified task from the given wedding.
     *
     * @param weddingId The ID of the wedding from which the task will be deleted.
     * @param taskIndex The 1-based index of the task to delete.
     */
    public DeleteTaskCommand(WeddingId weddingId, int taskIndex) {
        this.weddingId = weddingId;
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Wedding wedding = model.getFilteredWeddingList().stream()
                .filter(w -> w.getWeddingId().equals(weddingId))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(Messages.MESSAGE_WEDDING_NOT_FOUND,
                        weddingId.value)));

        try {
            WeddingTask removed = wedding.removeTask(taskIndex - 1);
            return new CommandResult(String.format(MESSAGE_SUCCESS, weddingId.value, removed),
                    false, false, false, true);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_TASK_INDEX, weddingId.value));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteTaskCommand)) {
            return false;
        }
        DeleteTaskCommand c = (DeleteTaskCommand) other;
        return weddingId.equals(c.weddingId) && taskIndex == c.taskIndex;
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.WeddingTask;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;


public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to a specified wedding.\n"
            + "Parameters: w/WEDDING_ID d/TASK_DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " w/W001 d/Book photographer";

    public static final String MESSAGE_SUCCESS = "New task added to wedding %1$s:\n%2$s";
    public static final String MESSAGE_WEDDING_NOT_FOUND = "Wedding with ID %1$s not found.";
    public static final String MESSAGE_INVALID_FORMAT = "Invalid command format. " + MESSAGE_USAGE;

    private final WeddingId weddingId;
    private final String taskDescription;

    /**
     * Creates an AddTaskCommand to add a task with the specified description to the specified wedding ID.
     */
    public AddTaskCommand(WeddingId weddingId, String taskDescription) {
        requireNonNull(weddingId);
        requireNonNull(taskDescription);
        this.weddingId = weddingId;
        this.taskDescription = taskDescription;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // 1. Find the matching wedding
        Wedding wedding = model.getFilteredWeddingList().stream()
                .filter(w -> w.getWeddingId().equals(weddingId))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_WEDDING_NOT_FOUND, weddingId.value)));

        // 2. Create the task (from your IP’s Task model or a new simple one)
        WeddingTask newTask = new WeddingTask(taskDescription); // or new Todo(taskDescription), etc.

        // 3. Add the task to the wedding
        wedding.addTask(newTask);

        // 4. Return success message
        return new CommandResult(String.format(MESSAGE_SUCCESS, weddingId.value, newTask));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddTaskCommand)) {
            return false;
        }
        AddTaskCommand c = (AddTaskCommand) other;
        return weddingId.equals(c.weddingId) && taskDescription.equals(c.taskDescription);
    }
}

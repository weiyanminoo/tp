package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.WeddingTask;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ListTaskCommand extends Command {

    public static final String COMMAND_WORD = "listTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks for a specified wedding.\n"
            + "Parameters: w/WEDDING_ID\n"
            + "Example: " + COMMAND_WORD + " w/W001";

    public static final String MESSAGE_WEDDING_NOT_FOUND = "Wedding with ID %1$s not found.";

    private final WeddingId weddingId;

    public ListTaskCommand(WeddingId weddingId) {
        this.weddingId = weddingId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Wedding wedding = model.getFilteredWeddingList().stream()
                .filter(w -> w.getWeddingId().equals(weddingId))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_WEDDING_NOT_FOUND, weddingId.value)));

        List<WeddingTask> tasks = wedding.getTasks();

        if (tasks.isEmpty()) {
            return new CommandResult("No tasks found for wedding " + weddingId.value);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Tasks for Wedding ").append(weddingId.value).append(":\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return new CommandResult(sb.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListTaskCommand)) {
            return false;
        }
        ListTaskCommand c = (ListTaskCommand) other;
        return weddingId.equals(c.weddingId);
    }
}

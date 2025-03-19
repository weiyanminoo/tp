package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Re-executes the pending command in force mode after user confirmation.
 */
public class ConfirmCommand extends Command {

    public static final String COMMAND_WORD = "y";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Retrieve the pending command that required confirmation.
        Command pendingCommand = ConfirmationManager.getInstance().getPendingCommand();
        if (pendingCommand == null) {
            throw new CommandException("No pending command to confirm.");
        }
        if (!(pendingCommand instanceof ForceableCommand)) {
            throw new CommandException("Pending command cannot be forced.");
        }
        ForceableCommand forceable = (ForceableCommand) pendingCommand;
        // Clear the pending command so that it's not executed twice.
        ConfirmationManager.getInstance().clearPendingCommand();
        return ((Command) forceable.createForceCommand()).execute(model);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ConfirmCommand;
    }
}

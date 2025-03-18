package seedu.address.logic.commands;

/**
 * A singleton that manages the pending command requiring confirmation.
 */
public class ConfirmationManager {
    private static ConfirmationManager instance;
    private Command pendingCommand;

    private ConfirmationManager() {}

    /**
     * Returns the singleton instance of ConfirmationManager.
     *
     * @return the ConfirmationManager instance.
     */
    public static ConfirmationManager getInstance() {
        if (instance == null) {
            instance = new ConfirmationManager();
        }
        return instance;
    }

    /**
     * Sets the pending command that requires user confirmation.
     *
     * @param command The command to store.
     */
    public void setPendingCommand(Command command) {
        this.pendingCommand = command;
    }

    /**
     * Returns the currently pending command.
     *
     * @return the pending command, or null if none.
     */
    public Command getPendingCommand() {
        return pendingCommand;
    }

    /**
     * Clears the stored pending command.
     */
    public void clearPendingCommand() {
        this.pendingCommand = null;
    }
}

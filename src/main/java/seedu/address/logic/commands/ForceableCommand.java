package seedu.address.logic.commands;

/**
 * An interface for commands that can be executed in force mode.
 */
public interface ForceableCommand {
    /**
     * Returns a new instance of the command that bypasses duplicate checks or similar warnings.
     *
     * @return a forced version of the command.
     */
    ForceableCommand createForceCommand();
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private boolean refreshUI;

    /**
     * Indicates whether this command result requires further user confirmation.
     * For example, this flag can be set when a duplicate person is detected,
     * and the UI must prompt the user to confirm the operation.
     */
    private final boolean requiresConfirmation;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean requiresConfirmation) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.requiresConfirmation = requiresConfirmation;
    }

    /**
     *  Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean requiresConfirmation, boolean refreshUI) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.requiresConfirmation = requiresConfirmation;
        this.refreshUI = refreshUI;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    /**
     * Returns true if this CommandResult indicates that further user confirmation is required.
     *
     * @return {@code true} if user confirmation is required; {@code false} otherwise.
     */
    public boolean isRequiresConfirmation() {
        return requiresConfirmation;
    }

    /**
     * Returns if this CommandResult indicates that the UI should be refreshed.
     * @return {@code true} if UI should be refreshed; {@code false} otherwise.
     */
    public boolean isRefreshUI() {
        return refreshUI;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && requiresConfirmation == otherCommandResult.requiresConfirmation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, requiresConfirmation);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("requiresConfirmation", requiresConfirmation)
                .toString();
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 * <p>
 * In normal mode, if the address book is not empty, the command will signal that confirmation is required
 * to avoid accidental clearing. The command is stored in the ConfirmationManager so that it can be re-executed
 * in force mode if the user confirms.
 * </p>
 */
public class ClearCommand extends Command implements ForceableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Contact book has been cleared!";
    public static final String MESSAGE_CONFIRMATION_REQUIRED =
            "WARNING: You are about to clear the contact book.\n"
            + "If you wish to proceed, use 'Ctrl / Command + A' and press 'Delete / Backspace' to clear the input box\n"
            + "and input 'y' to confirm.\n"
            + "Else, edit your input directly and press 'Enter'. ";

    private final boolean isForced;

    /**
     * Creates a ClearCommand in normal mode.
     */
    public ClearCommand() {
        this(false);
    }

    /**
     * Creates a ClearCommand with the specified mode.
     *
     * @param isForced If true, the confirmation requirement is bypassed.
     */
    public ClearCommand(boolean isForced) {
        requireNonNull(isForced);
        this.isForced = isForced;
    }

    /**
     * Executes the clear command.
     * <p>
     * In normal mode, if the address book is not empty, this method stores this command in the ConfirmationManager
     * and returns a CommandResult indicating that confirmation is required.
     * In force mode (or if the address book is already empty), it clears the address book immediately.
     * </p>
     *
     * @param model {@code Model} which the command should operate on.
     * @return the result of command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Check if the address book is not empty
        if (!isForced && !model.getAddressBook().getPersonList().isEmpty()) {
            ConfirmationManager.getInstance().setPendingCommand(this);
            return new CommandResult(MESSAGE_CONFIRMATION_REQUIRED, false, false, false, true);
        }
        // If force mode or if the address book is empty, proceed to clear it.
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Creates and returns a forced version of this command.
     *
     * @return a new ClearCommand with the force flag set to true.
     */
    @Override
    public ForceableCommand createForceCommand() {
        return new ClearCommand(true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ClearCommand)) {
            return false;
        }
        ClearCommand otherClear = (ClearCommand) other;
        return isForced == otherClear.isForced;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "{isForced=" + isForced + "}";
    }
}

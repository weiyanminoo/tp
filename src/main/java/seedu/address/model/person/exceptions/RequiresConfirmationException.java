package seedu.address.model.person.exceptions;

/**
 * Signals that a confirmation is required for this operation to proceed.
 */
public class RequiresConfirmationException extends RuntimeException {
    public RequiresConfirmationException() {
        super("Possible duplicate person spotted!");
    }
}

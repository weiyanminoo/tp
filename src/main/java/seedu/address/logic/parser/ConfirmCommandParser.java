package seedu.address.logic.parser;

import seedu.address.logic.commands.ConfirmCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ConfirmCommand object.
 */
public class ConfirmCommandParser implements Parser<ConfirmCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ConfirmCommand
     * and returns a ConfirmCommand object for execution.
     *
     * <p>
     * The ConfirmCommand does not accept any arguments. If the user input is not empty,
     * a ParseException is thrown.
     * </p>
     *
     * @param args the user input arguments.
     * @return a ConfirmCommand instance.
     * @throws ParseException if any arguments are present.
     */
    @Override
    public ConfirmCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            throw new ParseException("Confirm command does not accept any arguments.");
        }
        return new ConfirmCommand();
    }
}

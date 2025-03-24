package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command implements ForceableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the contact book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_ADDRESS + "ADDRESS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ROLE + "Photographer "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "WARNING: This person may already exist in the contact book.\n"
            + "If you wish to proceed, use 'Ctrl / Command + A' and press 'Delete / Backspace' to clear the input box\n"
            + "and input 'y' to confirm.\n"
            + "Else, edit your input directly and press 'Enter'. ";

    private final Person toAdd;
    private final boolean isForce;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
        isForce = false;
    }

    /**
     * Creates an AddCommand to add the specified {@code Person} in force mode.
     * In force mode, duplicate warnings are bypassed.
     *
     * @param person The person to add.
     * @param isForce A flag indicating that duplicates should be accepted.
     */
    public AddCommand(Person person, boolean isForce) {
        requireNonNull(person);
        toAdd = person;
        this.isForce = isForce;
    }

    /**
     * Executes the add command.
     *
     * <p>This method first checks if the person already exists in the address book.
     * If a duplicate is detected and this is not a force add, it returns a {@code CommandResult}
     * containing a duplicate warning message along with a flag indicating that confirmation is required.
     * The UI should then prompt the user and, upon confirmation, re-execute this command in force mode.</p>
     *
     * @param model {@code Model} which the command should operate on.
     * @return the result of command execution.
     * @throws CommandException if an error occurs during execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!isForce && model.hasPerson(toAdd)) {
            // In normal mode, if a duplicate exists, store this command and signal confirmation required.
            ConfirmationManager.getInstance().setPendingCommand(this);
            return new CommandResult(MESSAGE_DUPLICATE_PERSON, false, false, true);
        }

        if (isForce) {
            // Force mode: bypass duplicate check.
            model.forceAddPerson(toAdd);
        } else {
            // Normal mode: add person normally.
            model.addPerson(toAdd);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public ForceableCommand createForceCommand() {
        return new AddCommand(toAdd, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd) && isForce == otherAddCommand.isForce;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .add("isForce", isForce)
                .toString();
    }
}

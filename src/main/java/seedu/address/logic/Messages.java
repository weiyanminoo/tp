package seedu.address.logic;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_WEDDING_NOT_FOUND = "The specified wedding ID %1$s does not exist.";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        return Stream.of(duplicatePrefixes)
                .map(prefix -> "Multiple \"" + prefix.toString() + "\" detected, "
                        + "if you would like to add a duplicated \"" + prefix.toString()
                        + "\" in any field, please edit it to be: \"\\" + prefix.toString() + "\"")
                .collect(Collectors.joining("\n"));

    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName()).append("\n")
               .append("Phone: ").append(person.getPhone()).append("\n")
               .append("Email: ").append(person.getEmail()).append("\n")
               .append("Role: ").append(person.getRole()).append("\n")
               .append("Address: ").append(person.getAddress()).append("\n");
        return builder.toString();
    }

    /**
     * Formats the {@code wedding} for display to the user.
     */
    public static String format(Wedding wedding) {
        final StringBuilder builder = new StringBuilder();
        builder.append(wedding.getWeddingName()).append("\n")
               .append("Date: ").append(wedding.getWeddingDate()).append("\n")
               .append("Location: ").append(wedding.getWeddingLocation()).append("\n");
        return builder.toString();
    }
}

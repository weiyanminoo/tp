package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Wedding's name (e.g., "Alice & Bob Wedding").
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}.
 */
public class WeddingName {

    public static final String MESSAGE_CONSTRAINTS =
            "Wedding name should not be blank and can contain alphanumeric characters, spaces, or symbols like '&'.";

    public static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String fullWeddingName;

    /**
     * Constructs a {@code WeddingName}.
     *
     * @param name A valid name.
     */
    public WeddingName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.fullWeddingName = name;
    }

    /**
     * Returns true if a given string is a valid wedding name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullWeddingName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof WeddingName
                && fullWeddingName.equals(((WeddingName) other).fullWeddingName));
    }

    @Override
    public int hashCode() {
        return fullWeddingName.hashCode();
    }
}

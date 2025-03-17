package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Wedding's location (e.g., "Beach", "Hotel Ballroom").
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}.
 */
public class WeddingLocation {

    public static final String MESSAGE_CONSTRAINTS =
            "Wedding location should not be blank.";

    public static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String venue;

    /**
     * Constructs a {@code WeddingLocation}.
     *
     * @param location A valid location string.
     */
    public WeddingLocation(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        this.venue = location;
    }

    /**
     * Returns true if a given string is a valid wedding location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return venue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof WeddingLocation
                && venue.equals(((WeddingLocation) other).venue));
    }

    @Override
    public int hashCode() {
        return venue.hashCode();
    }
}

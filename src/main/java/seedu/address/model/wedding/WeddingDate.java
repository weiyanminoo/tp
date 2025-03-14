package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Wedding's date (e.g., "2025-12-01").
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}.
 */
public class WeddingDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Wedding date should be in the format 'DD-MMM-YYYY', e.g. '20-Dec-2025'.\n";

    // You might prefer a LocalDate parse with more robust checks.
    public static final String VALIDATION_REGEX = "^\\d{1,2}-[A-Za-z]{3}-\\d{4}$";


    public final String value;

    /**
     * Constructs a {@code WeddingDate}.
     *
     * @param date A valid date string.
     */
    public WeddingDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid wedding date in YYYY-MM-DD format.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof WeddingDate
                && value.equals(((WeddingDate) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

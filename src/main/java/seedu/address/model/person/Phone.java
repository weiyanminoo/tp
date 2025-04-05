package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the contact book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS = "Phone numbers should only contain numbers, spaces, and can include a '+' prefix for country code.\n"
            + "The number should have at least 3 digits and maximum 32 characters total (including '+' and spaces).";
    public static final String VALIDATION_REGEX = "^(\\+\\d[\\d ]*|[\\d ]+)$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        if (test.length() > 32) {
            return false;
        }

        // Check if it has at least 3 digits
        int digitCount = 0;
        for (int i = 0; i < test.length(); i++) {
            if (Character.isDigit(test.charAt(i))) {
                digitCount++;
            }
        }

        if (digitCount < 3) {
            return false;
        }

        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

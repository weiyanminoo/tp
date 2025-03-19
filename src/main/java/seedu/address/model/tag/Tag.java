package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.model.wedding.WeddingId;

/**
 * Represents a Tag in the address book, now used to store a WeddingId.
 * Guarantees: immutable; weddingId is valid as per the WeddingId constraints.
 */
public class Tag {

    public final WeddingId weddingId;

    /**
     * Constructs a {@code Tag}.
     *
     * @param weddingId A valid WeddingId.
     */
    public Tag(WeddingId weddingId) {
        requireNonNull(weddingId);
        this.weddingId = weddingId;
    }

    /**
     * Checks if this {@code Tag} is equal to another object.
     * Two {@code Tag} objects are considered equal if they have the same {@code weddingId}.
     *
     * @param other The object to compare with this {@code Tag}.
     * @return {@code true} if the given object is a {@code Tag} with the same {@code weddingId}, otherwise
     *     {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Tag)) {
            return false;
        }
        Tag otherTag = (Tag) other;
        return weddingId.equals(otherTag.weddingId);
    }

    /**
     * Returns the hash code of this {@code Tag}.
     *
     * @return The hash code of this {@code Tag}.
     */
    @Override
    public int hashCode() {
        return weddingId.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return '[' + weddingId.toString() + ']';
    }
}

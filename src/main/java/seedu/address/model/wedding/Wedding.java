package seedu.address.model.wedding;

import java.util.Objects;

/**
 * Represents a Wedding event in EasyWeds.
 */
public class Wedding {
    private static int nextId = 1; // Static counter to keep track of Wedding ID
    private final WeddingId weddingId;
    private final WeddingName weddingName;
    private final WeddingDate weddingDate;
    private final WeddingLocation location;

    /**
     * Creates a Wedding object with the given fields.
     */
    public Wedding(WeddingName weddingName, WeddingDate weddingDate, WeddingLocation location) {
        this.weddingId = new WeddingId("W" + nextId);
        this.weddingName = weddingName;
        this.weddingDate = weddingDate;
        this.location = location;
    }

    /**
     * Creates a Wedding object with a specified ID (useful for restoration).
     */
    public Wedding(WeddingId weddingId, WeddingName weddingName, WeddingDate weddingDate, WeddingLocation location) {
        this.weddingId = weddingId;
        this.weddingName = weddingName;
        this.weddingDate = weddingDate;
        this.location = location;

        int numericPart = Integer.parseInt(weddingId.value.substring(1)); // skip 'W'
        if (numericPart >= nextId) {
            nextId = numericPart + 1;
        }
    }

    /**
     * For read-only access to the current counter value.
     */
    public static int getNextId() {
        return nextId;
    }

    /**
     * For forcibly resetting the counter, e.g. if a duplicate was detected.
     */
    public static void setNextId(int newValue) {
        nextId = newValue;
    }

    public WeddingId getWeddingId() {
        return weddingId;
    }

    public WeddingName getWeddingName() {
        return weddingName;
    }

    public WeddingDate getWeddingDate() {
        return weddingDate;
    }

    public WeddingLocation getLocation() {
        return location;
    }

    /**
     * Returns true if this wedding and the given {@code otherWedding} have the same wedding ID.
     *
     * @param otherWedding The other wedding to compare against.
     * @return True if both weddings share the same wedding ID, false otherwise.
     */
    public boolean isSameWedding(Wedding otherWedding) {
        if (otherWedding == this) {
            return true;
        }
        if (otherWedding == null) {
            return false;
        }

        return this.weddingName.equals(otherWedding.weddingName)
                && this.weddingDate.equals(otherWedding.weddingDate)
                && this.location.equals(otherWedding.location);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Wedding)) {
            return false;
        }
        Wedding otherWedding = (Wedding) other;
        return weddingId.equals(otherWedding.weddingId)
                && weddingName.equals(otherWedding.weddingName)
                && weddingDate.equals(otherWedding.weddingDate)
                && location.equals(otherWedding.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weddingId, weddingName, weddingDate, location);
    }

    @Override
    public String toString() {
        return String.format("Wedding: %s (ID: %s) Date: %s Location: %s",
                weddingName, weddingId, weddingDate, location);
    }
}

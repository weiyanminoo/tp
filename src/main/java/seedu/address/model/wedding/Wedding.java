package seedu.address.model.wedding;

import java.util.Objects;

/**
 * Represents a Wedding event in EasyWeds.
 */
public class Wedding {
    private final String weddingId;
    private final String weddingName;
    private final String weddingDate;
    private final String location;

    /**
     * Creates a Wedding object with the given fields.
     */
    public Wedding(String weddingId, String weddingName, String weddingDate, String location) {
        this.weddingId = weddingId;
        this.weddingName = weddingName;
        this.weddingDate = weddingDate;
        this.location = location;
    }

    public String getWeddingId() {
        return weddingId;
    }

    public String getWeddingName() {
        return weddingName;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public String getLocation() {
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

        return this.getWeddingId().equals(otherWedding.getWeddingId());
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

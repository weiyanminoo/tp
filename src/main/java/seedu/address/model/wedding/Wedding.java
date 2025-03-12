package seedu.address.model.wedding;

import java.util.Objects;

/**
 * Represents a Wedding event in EasyWeds.
 */
public class Wedding {
    private final String weddingId;    // Unique identifier
    private final String weddingName;  // e.g., "John & Jane's Wedding"
    private final String weddingDate;  // e.g., "20 Feb 2025"
    private final String location;     // e.g., "Grand Ballroom"

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

package seedu.address.testutil;

import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

/**
 * A utility class to help with building Wedding objects.
 */
public class WeddingBuilder {

    public static final String DEFAULT_WEDDING_ID = "W001";
    public static final String DEFAULT_WEDDING_NAME = "John Wedding";
    public static final String DEFAULT_WEDDING_DATE = "15-Jun-2025";
    public static final String DEFAULT_WEDDING_LOCATION = "Central Park";

    private WeddingId id;
    private WeddingName name;
    private WeddingDate date;
    private WeddingLocation location;

    /**
     * Creates a {@code WeddingBuilder} with the default details.
     */
    public WeddingBuilder() {
        id = new WeddingId(DEFAULT_WEDDING_ID);
        name = new WeddingName(DEFAULT_WEDDING_NAME);
        date = new WeddingDate(DEFAULT_WEDDING_DATE);
        location = new WeddingLocation(DEFAULT_WEDDING_LOCATION);
    }

    /**
     * Initializes the WeddingBuilder with the data of {@code weddingToCopy}.
     * @param weddingToCopy
     */
    public WeddingBuilder(Wedding weddingToCopy) {
        id = weddingToCopy.getWeddingId();
        name = weddingToCopy.getWeddingName();
        date = weddingToCopy.getWeddingDate();
        location = weddingToCopy.getWeddingLocation();
    }

    /**
     * Sets the {@code WeddingId} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withWeddingId(String id) {
        this.id = new WeddingId(id);
        return this;
    }

    /**
     * Sets the {@code WeddingName} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withWeddingName(String name) {
        this.name = new WeddingName(name);
        return this;
    }

    /**
     * Sets the {@code WeddingDate} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withWeddingDate(String date) {
        this.date = new WeddingDate(date);
        return this;
    }

    /**
     * Sets the {@code WeddingLocation} of the {@code Wedding} that we are building.
     */
    public WeddingBuilder withWeddingLocation(String location) {
        this.location = new WeddingLocation(location);
        return this;
    }

    /**
     * Builds the wedding.
     * @return the wedding
     */
    public Wedding build() {
        return new Wedding(id, name, date, location);
    }
}

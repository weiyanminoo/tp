package seedu.address.storage;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

/**
 * Jackson-friendly version of {@link Wedding}.
 */
class JsonAdaptedWedding {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wedding's %s field is missing!";

    private final String weddingId;
    private final String weddingName;
    private final String weddingDate;
    private final String location;

    /**
     * Constructs a {@code JsonAdaptedWedding} with the given wedding details.
     */
    @JsonCreator
    public JsonAdaptedWedding(@JsonProperty("weddingId") String weddingId,
                              @JsonProperty("weddingName") String weddingName,
                              @JsonProperty("weddingDate") String weddingDate,
                              @JsonProperty("location") String location) {
        this.weddingId = weddingId;
        this.weddingName = weddingName;
        this.weddingDate = weddingDate;
        this.location = location;
    }

    /**
     * Converts a given {@code Wedding} into this class for Jackson use.
     */
    public JsonAdaptedWedding(Wedding source) {
        this.weddingId = source.getWeddingId().value;
        this.weddingName = source.getWeddingName().fullWeddingName;
        this.weddingDate = source.getWeddingDate().value;
        this.location = source.getLocation().venue;
    }

    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wedding.
     */
    public Wedding toModelType() throws IllegalValueException {
        if (weddingId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "weddingId"));
        }
        final WeddingId modelWeddingId = new WeddingId(weddingId);

        if (weddingName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "weddingName"));
        }
        final WeddingName modelWeddingName = new WeddingName(weddingName);

        if (weddingDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "weddingDate"));
        }
        final WeddingDate modelWeddingDate = new WeddingDate(weddingDate);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "location"));
        }
        final WeddingLocation modelLocation = new WeddingLocation(location);

        return new Wedding(modelWeddingId, modelWeddingName, modelWeddingDate, modelLocation, true);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JsonAdaptedWedding)) {
            return false;
        }
        JsonAdaptedWedding o = (JsonAdaptedWedding) other;
        return Objects.equals(weddingId, o.weddingId)
                && Objects.equals(weddingName, o.weddingName)
                && Objects.equals(weddingDate, o.weddingDate)
                && Objects.equals(location, o.location);
    }
}

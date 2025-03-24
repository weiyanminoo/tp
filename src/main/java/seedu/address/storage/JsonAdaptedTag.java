package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.WeddingId;

/**
 * Jackson-friendly version of {@link Tag}.
 */
public class JsonAdaptedTag {

    private final String weddingId;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given wedding id.
     */
    @JsonCreator
    public JsonAdaptedTag(String weddingId) {
        requireNonNull(weddingId);
        this.weddingId = weddingId;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        this.weddingId = source.weddingId.toString();
    }

    @JsonValue
    public String getWeddingId() {
        return weddingId;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if the wedding id is invalid.
     */
    public Tag toModelType() throws IllegalValueException {
        try {
            WeddingId weddingIdObject = new WeddingId(weddingId);
            return new Tag(weddingIdObject);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }
}

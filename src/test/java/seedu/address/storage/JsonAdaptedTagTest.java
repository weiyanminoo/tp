package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.WeddingId;

/**
 * Contains unit tests for {@code JsonAdaptedTag}.
 */
public class JsonAdaptedTagTest {

    private static final String VALID_WEDDING_ID = "W1";
    private static final String INVALID_WEDDING_ID = "InvalidId";
    private static final String EMPTY_WEDDING_ID = "";

    @Test
    public void constructor_nullWeddingId_throwsNullPointerException() {
        // Passing null to the JsonAdaptedTag constructor
        assertThrows(NullPointerException.class, () -> new JsonAdaptedTag((String) null));
    }

    @Test
    public void constructor_fromTag_success() {
        Tag sourceTag = new Tag(new WeddingId(VALID_WEDDING_ID));
        JsonAdaptedTag adaptedTag = new JsonAdaptedTag(sourceTag);

        // The weddingId string should match the source's wedding ID
        assertEquals(VALID_WEDDING_ID, adaptedTag.getWeddingId());
    }

    @Test
    public void toModelType_validWeddingId_success() throws Exception {
        JsonAdaptedTag adaptedTag = new JsonAdaptedTag(VALID_WEDDING_ID);
        Tag modelTag = adaptedTag.toModelType();
        assertEquals(new Tag(new WeddingId(VALID_WEDDING_ID)), modelTag);
    }

    @Test
    public void toModelType_invalidWeddingId_throwsIllegalValueException() {
        // Example of an invalid ID that fails WeddingId validation
        JsonAdaptedTag adaptedTag = new JsonAdaptedTag(INVALID_WEDDING_ID);
        assertThrows(IllegalValueException.class, adaptedTag::toModelType);
    }

    @Test
    public void toModelType_emptyWeddingId_throwsIllegalValueException() {
        // Empty string is also invalid if your WeddingId requires non-empty
        JsonAdaptedTag adaptedTag = new JsonAdaptedTag(EMPTY_WEDDING_ID);
        assertThrows(IllegalValueException.class, adaptedTag::toModelType);
    }
}

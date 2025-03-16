package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import seedu.address.model.wedding.WeddingId;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidWeddingId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag(new WeddingId("")));
        assertThrows(IllegalArgumentException.class, () -> new Tag(new WeddingId("friends")));
        assertThrows(IllegalArgumentException.class, () -> new Tag(new WeddingId("client")));
        assertThrows(IllegalArgumentException.class, () -> new Tag(new WeddingId("VENDOR")));
    }

    @Test
    public void constructor_validWeddingId_success() {
        // Should not throw any exception for valid wedding ids.
        assertDoesNotThrow(() -> new Tag(new WeddingId("W1")));
        assertDoesNotThrow(() -> new Tag(new WeddingId("W2")));
    }
}

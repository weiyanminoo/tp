package seedu.address.model.tag;


import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        // Empty string
        assertThrows(IllegalArgumentException.class, () -> new Tag(""));

        // Random invalid string
        assertThrows(IllegalArgumentException.class, () -> new Tag("friends"));

        // Mixed case not exactly "Client" or "Vendor"
        assertThrows(IllegalArgumentException.class, () -> new Tag("client"));
        assertThrows(IllegalArgumentException.class, () -> new Tag("VENDOR"));
    }

    @Test
    public void constructor_validTagName_success() {
        // Should not throw any exception
        new Tag("Client");
        new Tag("Vendor");
    }

    @Test
    public void isValidTagName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }
}

package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;
import seedu.address.model.wedding.WeddingTask;

public class WeddingCardTest {

    @Test
    public void equals() {
        Wedding wedding = createTestWedding();
        Wedding sameWedding = createTestWedding();
        Wedding differentWedding = createTestWeddingWithExtraTask();

        assertEquals(wedding.getWeddingId(), sameWedding.getWeddingId());
        assertEquals(wedding.getWeddingName(), sameWedding.getWeddingName());
        assertEquals(wedding.getWeddingDate(), sameWedding.getWeddingDate());
        assertEquals(wedding.getWeddingLocation(), sameWedding.getWeddingLocation());

        assertNotEquals(wedding.getTasks().size(), differentWedding.getTasks().size());
    }

    /**
     * Creates a test wedding for the tests
     */
    private Wedding createTestWedding() {
        Wedding wedding = new Wedding(
                new WeddingId("W123"),
                new WeddingName("Test Wedding"),
                new WeddingDate("01-Dec-2025"),
                new WeddingLocation("Test Location"),
                true
        );
        wedding.addTask(new WeddingTask("Book venue"));
        return wedding;
    }

    /**
     * Creates a test wedding with an extra task
     */
    private Wedding createTestWeddingWithExtraTask() {
        Wedding wedding = createTestWedding();
        wedding.addTask(new WeddingTask("Different task"));
        return wedding;
    }
}
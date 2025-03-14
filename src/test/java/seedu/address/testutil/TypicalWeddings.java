package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TypicalWeddings {
    public static final Wedding WEDDING_ONE = new Wedding("W001", "John & Jane Wedding",
            "2025-06-15", "Central Park");
    public static final Wedding WEDDING_TWO = new Wedding("W002", "Alice & Bob Wedding",
            "2025-09-10", "Beachside Resort");

    private TypicalWeddings() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical weddings.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        ab.addWedding(WEDDING_ONE);
        ab.addWedding(WEDDING_TWO);
        return ab;
    }
}

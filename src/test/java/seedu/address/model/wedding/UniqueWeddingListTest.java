package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;

public class UniqueWeddingListTest {

    // By default, these two use the constructor that auto-generates IDs.
    // For example, WEDDING_A might end up with "W1", WEDDING_B with "W2".
    private static final Wedding WEDDING_A = new Wedding(
            new WeddingName("Alice & Bob Wedding"),
            new WeddingDate("01-Jan-2025"),
            new WeddingLocation("Beach")
    );
    private static final Wedding WEDDING_B = new Wedding(
            new WeddingName("Carol & David Wedding"),
            new WeddingDate("02-Feb-2025"),
            new WeddingLocation("Garden")
    );

    private UniqueWeddingList uniqueWeddingList = new UniqueWeddingList();

    @BeforeEach
    public void resetWeddingNextId() {
        // Reset the static counter so that tests don't interfere with each other.
        Wedding.setNextId(1);
        uniqueWeddingList = new UniqueWeddingList();
    }

    // ========================== CONTAINS TESTS ==========================
    @Test
    public void contains_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.contains(null));
    }

    @Test
    public void contains_weddingNotInList_returnsFalse() {
        assertFalse(uniqueWeddingList.contains(WEDDING_A));
    }

    @Test
    public void contains_weddingInList_returnsTrue() {
        uniqueWeddingList.add(WEDDING_A);
        assertTrue(uniqueWeddingList.contains(WEDDING_A));
    }

    // ========================== ADD TESTS ==========================
    @Test
    public void add_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.add(null));
    }

    @Test
    public void add_newWedding_incrementsNextId() {
        int originalNextId = Wedding.getNextId();
        Wedding newWedding = new Wedding(
                new WeddingName("New Wedding"),
                new WeddingDate("04-Apr-2025"),
                new WeddingLocation("Hall")
        );
        uniqueWeddingList.add(newWedding);
        // For new weddings, the add method increments nextId.
        assertEquals(originalNextId + 1, Wedding.getNextId());
    }

    @Test
    public void add_restoredWedding_doesNotIncrementNextId() {
        int originalNextId = Wedding.getNextId();
        Wedding restoredWedding = new Wedding(
                new WeddingId("W" + originalNextId),
                new WeddingName("Restored Wedding"),
                new WeddingDate("05-May-2025"),
                new WeddingLocation("Garden"),
                true
        );
        uniqueWeddingList.add(restoredWedding);
        // For restored weddings, the add method should not increment nextId.
        assertEquals(originalNextId + 1, Wedding.getNextId());
    }

    // ========================== SETWEDDING TESTS ==========================
    @Test
    public void setWedding_nullTargetWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWedding(null, WEDDING_A));
    }

    @Test
    public void setWedding_nullEditedWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWedding(WEDDING_A, null));
    }

    @Test
    public void setWedding_targetNotInList_throwsWeddingNotFoundException() {
        // Attempt to edit a Wedding that isn't in the list
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.setWedding(WEDDING_A, WEDDING_A));
    }

    @Test
    public void setWedding_editedWeddingHasDifferentIdentity_success() {
        // WEDDING_A and WEDDING_B have different IDs or name/date/location
        uniqueWeddingList.add(WEDDING_A);
        uniqueWeddingList.setWedding(WEDDING_A, WEDDING_B);

        UniqueWeddingList expectedList = new UniqueWeddingList();
        expectedList.add(WEDDING_B);
        assertEquals(expectedList, uniqueWeddingList);
    }

    @Test
    public void setWedding_editedWeddingHasNonUniqueIdentity_throwsDuplicateWeddingException() {
        // We have two different weddings in the list
        uniqueWeddingList.add(WEDDING_A);
        uniqueWeddingList.add(WEDDING_B);

        // Attempt to set WEDDING_B to match WEDDING_A's identity => duplicates
        Wedding editedB = new Wedding(
                WEDDING_A.getWeddingId(), // or WEDDING_A's name/date/location
                WEDDING_A.getWeddingName(),
                WEDDING_A.getWeddingDate(),
                WEDDING_A.getLocation()
        );
        assertThrows(DuplicateWeddingException.class, () ->
                uniqueWeddingList.setWedding(WEDDING_B, editedB));
    }

    // ========================== REMOVE TESTS ==========================
    @Test
    public void remove_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.remove(null));
    }

    @Test
    public void remove_weddingDoesNotExist_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.remove(WEDDING_A));
    }

    @Test
    public void remove_existingWedding_removesWedding() {
        uniqueWeddingList.add(WEDDING_A);
        uniqueWeddingList.remove(WEDDING_A);
        UniqueWeddingList expectedList = new UniqueWeddingList();
        assertEquals(expectedList, uniqueWeddingList);
    }

    // ========================== SETWEDDINGS(UniqueWeddingList) TESTS ==========================
    @Test
    public void setWeddings_nullUniqueWeddingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((UniqueWeddingList) null));
    }

    @Test
    public void setWeddings_uniqueWeddingList_replacesData() {
        uniqueWeddingList.add(WEDDING_A);
        UniqueWeddingList newList = new UniqueWeddingList();
        newList.add(WEDDING_B);
        uniqueWeddingList.setWeddings(newList);
        assertEquals(newList, uniqueWeddingList);
    }

    // ========================== SETWEDDINGS(List<Wedding>) TESTS ==========================
    @Test
    public void setWeddings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((List<Wedding>) null));
    }

    @Test
    public void setWeddings_list_replacesData() {
        uniqueWeddingList.add(WEDDING_A);
        List<Wedding> weddingList = Collections.singletonList(WEDDING_B);
        uniqueWeddingList.setWeddings(weddingList);

        UniqueWeddingList expectedList = new UniqueWeddingList();
        expectedList.add(WEDDING_B);
        assertEquals(expectedList, uniqueWeddingList);
    }

    // ========================== ASUNMODIFIABLEOBSERVABLELIST TEST ==========================
    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueWeddingList.add(WEDDING_A);
        ObservableList<Wedding> unmodifiableList = uniqueWeddingList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.remove(0));
    }
}

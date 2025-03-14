package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;

public class UniqueWeddingListTest {

    // By default, these two use the constructor that auto-generates IDs.
    // For example, WEDDING_A might end up with "W1", WEDDING_B with "W2".
    private static final Wedding WEDDING_A = new Wedding("Alice & Bob Wedding", "2025-01-01", "Beach");
    private static final Wedding WEDDING_B = new Wedding("Carol & David Wedding", "2025-02-02", "Garden");

    private final UniqueWeddingList uniqueWeddingList = new UniqueWeddingList();

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
    public void add_duplicateWedding_throwsDuplicateWeddingException() {
        uniqueWeddingList.add(WEDDING_A);

        // Create another Wedding with the same ID as WEDDING_A
        // but different date/location to confirm duplication is determined by ID.
        Wedding sameIdWedding = new Wedding(
                WEDDING_A.getWeddingId(), // reuse the same auto-generated ID
                "Alice & Bob Wedding",    // name can match or differ; ID is the key
                "2025-03-03",
                "Hotel"
        );
        // Now isSameWedding(...) returns true because IDs match
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.add(sameIdWedding));
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
    public void setWedding_editedWeddingIsSameWedding_success() {
        uniqueWeddingList.add(WEDDING_A);

        // Create an edited version of WEDDING_A with the same ID but different location
        Wedding editedWeddingA = new Wedding(
                WEDDING_A.getWeddingId(),
                WEDDING_A.getWeddingName(),
                WEDDING_A.getWeddingDate(),
                "New Location"
        );
        uniqueWeddingList.setWedding(WEDDING_A, editedWeddingA);

        UniqueWeddingList expectedList = new UniqueWeddingList();
        expectedList.add(editedWeddingA);
        assertEquals(expectedList, uniqueWeddingList);
    }

    @Test
    public void setWedding_editedWeddingHasDifferentIdentity_success() {
        // WEDDING_A and WEDDING_B have different IDs
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

        // Attempt to set WEDDING_B to have WEDDING_A's ID => duplicates
        Wedding editedB = new Wedding(
                WEDDING_A.getWeddingId(),
                WEDDING_B.getWeddingName(),
                WEDDING_B.getWeddingDate(),
                WEDDING_B.getLocation()
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

    @Test
    public void setWeddings_listWithDuplicateWeddings_throwsDuplicateWeddingException() {
        // Two Weddings with the same ID => Duplicate
        Wedding weddingDuplicateA = new Wedding(
                WEDDING_A.getWeddingId(),
                WEDDING_A.getWeddingName(),
                WEDDING_A.getWeddingDate(),
                "Different Place"
        );
        List<Wedding> listWithDuplicateWeddings = Arrays.asList(WEDDING_A, weddingDuplicateA);
        assertThrows(DuplicateWeddingException.class, () ->
                uniqueWeddingList.setWeddings(listWithDuplicateWeddings));
    }

    // ========================== ASUNMODIFIABLEOBSERVABLELIST TEST ==========================
    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueWeddingList.add(WEDDING_A);
        ObservableList<Wedding> unmodifiableList = uniqueWeddingList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.remove(0));
    }
}

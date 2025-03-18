package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;

/**
 * A list of weddings that enforces uniqueness between its elements and does not allow nulls.
 * A wedding is considered unique by comparing using {@code Wedding#isSameWedding(Wedding)}.
 * Thus, adding and updating of weddings uses Wedding#isSameWedding(Wedding) for equality so as to ensure
 * that the wedding being added or updated is unique in terms of identity in the UniqueWeddingList.
 * However, the removal of a wedding uses Wedding#equals(Object) so as to ensure that the wedding
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Wedding#isSameWedding(Wedding)
 */
public class UniqueWeddingList implements Iterable<Wedding> {

    private final ObservableList<Wedding> internalList = FXCollections.observableArrayList();
    private final ObservableList<Wedding> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent wedding as the given argument.
     */
    public boolean contains(Wedding toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWedding);
    }

    /**
     * Adds a wedding to the list.
     * The wedding must not already exist in the list.
     */
    public void add(Wedding toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateWeddingException();
        }
        internalList.add(toAdd);
        // Only increment nextId if this is a new wedding.
        if (!toAdd.isRestored()) {
            Wedding.setNextId(Wedding.getNextId() + 1);
        }
    }

    /**
     * Replaces the wedding {@code target} in the list with {@code editedWedding}.
     * {@code target} must exist in the list.
     * The wedding identity of {@code editedWedding} must not be the same as another existing wedding in the list.
     */
    public void setWedding(Wedding target, Wedding editedWedding) {
        requireNonNull(target);
        requireNonNull(editedWedding);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new WeddingNotFoundException();
        }

        // If target and editedWedding are different but editedWedding already exists, it's a duplicate.
        if (!target.isSameWedding(editedWedding) && contains(editedWedding)) {
            throw new DuplicateWeddingException();
        }

        internalList.set(index, editedWedding);
    }

    /**
     * Removes the equivalent wedding from the list.
     * The wedding must exist in the list.
     */
    public void remove(Wedding toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new WeddingNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     */
    public void setWeddings(UniqueWeddingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code weddings}.
     * {@code weddings} must not contain duplicate weddings.
     */
    public void setWeddings(List<Wedding> weddings) {
        requireNonNull(weddings);
        if (!weddingsAreUnique(weddings)) {
            throw new DuplicateWeddingException();
        }

        internalList.setAll(weddings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Wedding> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Wedding> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueWeddingList
                && internalList.equals(((UniqueWeddingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code weddings} contains only unique weddings.
     */
    private boolean weddingsAreUnique(List<Wedding> weddings) {
        for (int i = 0; i < weddings.size() - 1; i++) {
            for (int j = i + 1; j < weddings.size(); j++) {
                if (weddings.get(i).isSameWedding(weddings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

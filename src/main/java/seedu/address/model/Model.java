package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Wedding> PREDICATE_SHOW_ALL_WEDDINGS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given person, bypassing duplicate checks.
     * This method should be used only when the user confirms that a duplicate is acceptable.
     */
    void forceAddPerson(Person person);

    /**
     * Forcefully updates the details of the specified person in the address book.
     */
    void forceSetPerson(Person target, Person editedPerson);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    boolean hasWedding(Wedding wedding);

    void addWedding(Wedding wedding);

    Wedding getWeddingById(WeddingId weddingId);

    void deleteWedding(Wedding wedding);

    /**
     * Replaces the given wedding {@code target} with {@code editedWedding}.
     * {@code target} must exist in the address book.
     * The wedding identity of {@code editedWedding} must not be the same as another existing wedding
     * in the address book.
     */
    void setWedding(Wedding target, Wedding editedWedding);

    /**
     * Returns an unmodifiable view of the list of {@code Wedding} backed by the internal list.
     */
    ObservableList<Wedding> getFilteredWeddingList();

    /**
     * Updates the filter of the filtered wedding list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWeddingList(Predicate<Wedding> predicate);


    // Add to Model.java
    void setSortWeddingsByDate(boolean sortByDate);

    boolean isSortingWeddingsByDate();

    /**
     * Returns an unmodifiable view of the sorted list of {@code Wedding} backed by the
     * internal sorted list using the provided comparator
     */
    ObservableList<Wedding> getSortedWeddingList(Comparator<Wedding> comparator);

    /**
     * Tags a person with the given tag.
     */
    void tagPerson(Person person, Tag tag);

    /**
     * Removes the specified tag from the specified person.
     */
    void untagPerson(Person person, Tag tag);

    /**
     * Removes a tag from all contacts.
     * @param tag The tag to be removed.
     */
    void removeTagFromAllContacts(Tag tag);
}

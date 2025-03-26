package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Wedding> filteredWeddings;
    private final SortedList<Wedding> sortedWeddings;

    private boolean sortWeddingsByDate = false;
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredWeddings = new FilteredList<>(this.addressBook.getWeddingList());
        sortedWeddings = new SortedList<>(filteredWeddings);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void forceAddPerson(Person person) {
        // Call the overloaded method in your addressBook with force = true.
        addressBook.addPerson(person, true);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredWeddings.equals(otherModelManager.filteredWeddings);
    }

    //=========== Wedding ================================================================================
    @Override
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return addressBook.hasWedding(wedding);
    }

    @Override
    public void addWedding(Wedding wedding) {
        addressBook.addWedding(wedding);
        updateFilteredWeddingList(w -> true); // Show all after adding
    }

    @Override
    public void setWedding(Wedding target, Wedding editedWedding) {
        requireAllNonNull(target, editedWedding);

        addressBook.setWedding(target, editedWedding);
    }

    @Override
    public Wedding getWeddingById(WeddingId weddingId) {
        requireNonNull(weddingId);
        return addressBook.getWeddingList().stream()
                .filter(wedding -> wedding.getWeddingId().equals(weddingId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteWedding(Wedding wedding) {
        addressBook.removeWedding(wedding);
    }

    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        if (sortWeddingsByDate) {
            return sortedWeddings;
        }
        return filteredWeddings;
    }

    @Override
    public void setSortWeddingsByDate(boolean sortByDate) {
        this.sortWeddingsByDate = sortByDate;
        if (sortByDate) {
            Comparator<Wedding> dateComparator = createWeddingDateComparator();
            sortedWeddings.setComparator(dateComparator);
        } else {
            sortedWeddings.setComparator(null);
        }
    }

    @Override
    public boolean isSortingWeddingsByDate() {
        return sortWeddingsByDate;
    }

    private Comparator<Wedding> createWeddingDateComparator() {
        return Comparator.comparing(wedding ->
                java.time.LocalDate.parse(
                        wedding.getWeddingDate().toString(),
                        java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yyyy")
                )
        );
    }

    public ObservableList<Wedding> getSortedWeddingList(Comparator<Wedding> comparator) {
        sortedWeddings.setComparator(comparator);
        return sortedWeddings;
    }

    @Override
    public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
        requireNonNull(predicate);
        filteredWeddings.setPredicate(predicate);
    }

    /**
     * Tags a person with the given tag.
     *
     * @param person the person to be tagged.
     * @param tag the tag to add.
     * @throws AssertionError if the person is not found in the address book.
     */
    @Override
    public void tagPerson(Person person, Tag tag) {
        requireNonNull(person);
        requireNonNull(tag);

        // Create an updated person instance with the new tag added.
        Person updatedPerson = person.addTag(tag);

        // Update the person in the address book.
        try {
            addressBook.setPerson(person, updatedPerson);
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person cannot be missing", e);
        }

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void removeTagFromAllContacts(Tag tag) {
        requireNonNull(tag);
        for (Person person : addressBook.getPersonList()) {
            if (person.getTags().contains(tag)) {
                Person updatedPerson = person.removeTag(tag);
                try {
                    addressBook.setPerson(person, updatedPerson);
                } catch (PersonNotFoundException e) {
                    throw new AssertionError("The target person cannot be missing", e);
                }
            }
        }

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Removes the given tag from the given person.
     *
     * @param personToUntag the person to be untagged.
     * @param tag the tag to remove.
     */
    @Override
    public void untagPerson(Person personToUntag, Tag tag) {
        requireNonNull(personToUntag);
        requireNonNull(tag);

        Person updatedPerson = personToUntag.removeTag(tag);
        addressBook.setPerson(personToUntag, updatedPerson);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
}

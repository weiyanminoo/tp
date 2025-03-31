package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.wedding.UniqueWeddingList;
import seedu.address.model.wedding.Wedding;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueWeddingList weddings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        weddings = new UniqueWeddingList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    public void setWeddings(List<Wedding> weddings) {
        this.weddings.setWeddings(weddings);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setWeddings(newData.getWeddingList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book in normal mode.
     * Delegates to addPerson with force set to false.
     */
    public void addPerson(Person p) {
        addPerson(p, false);
    }

    /**
     * Adds a person to the address book.
     *
     * @param p The person to add.
     * @param force If true, bypass duplicate checks; if false, enforce uniqueness.
     */
    public void addPerson(Person p, boolean force) {
        persons.add(p, force);
    }

    /**
     * Forcefully updates the details of the specified person in the address book.
     * <p>
     * This method bypasses duplicate checks. It locates the person in the internal list and
     * replaces the record with {@code editedPerson}. If the target is not found, a
     * {@code PersonNotFoundException} is thrown.
     * </p>
     */
    public void forceSetPerson(Person target, Person editedPerson) {
        // Delegate the force update to the UniquePersonList.
        persons.forceSetPerson(target, editedPerson);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// wedding-level operations

    /**
     * Returns true if a wedding with the same identity as {@code wedding} exists in the address book.
     */
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddings.contains(wedding);
    }

    /**
     * Adds a wedding to the address book.
     * The wedding must not already exist in the address book.
     */
    public void addWedding(Wedding wedding) {
        weddings.add(wedding);
    }

    /**
     * Replaces the given wedding {@code target} in the list with {@code editedWedding}.
     * {@code target} must exist in the address book.
     * The wedding identity of {@code editedWedding} must not be the same
     * as another existing wedding in the address book.
     */
    public void setWedding(Wedding target, Wedding editedWedding) {
        weddings.setWedding(target, editedWedding);
    }

    /**
     * Removes {@code wedding} from this {@code AddressBook}.
     * {@code wedding} must exist in the address book.
     */
    public void removeWedding(Wedding wedding) {
        weddings.remove(wedding);
    }


    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Wedding> getWeddingList() {
        return weddings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}

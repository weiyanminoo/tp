package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Role role;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Role role, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, role, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        if (otherPerson == null) {
            return false;
        }
        String normalizedThisName = normalize(name.toString());
        String normalizedOtherName = normalize(otherPerson.getName().toString());
        return normalizedThisName.equals(normalizedOtherName);
    }

    /**
     * Helper method to normalize a name by trimming leading/trailing spaces,
     * converting to lower case and replacing multiple spaces with a single space
     */
    private String normalize(String s) {
        return s.trim().toLowerCase().replaceAll("\\s+", " ");
    }

    /**
     * Returns a new Person with the WeddingId tag added.
     *
     * @param tag The tag to be added.
     * @return A new Person instance with the tag added.
     */
    public Person addTag(Tag tag) {
        requireAllNonNull(tag);
        Set<Tag> newTags = new HashSet<>(this.tags);
        newTags.add(tag);
        return new Person(name, phone, email, role, address, newTags);
    }

    /**
     * Returns a new Person with the WeddingId tag removed.
     *
     * @param tag The tag to be removed.
     * @return A new Person instance with the tag removed.
     */
    public Person removeTag(Tag tag) {
        requireAllNonNull(tag);
        Set<Tag> newTags = new HashSet<>(this.tags);
        newTags.remove(tag);
        return new Person(name, phone, email, role, address, newTags);
    }


    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && role.equals(otherPerson.role)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, role, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("role", role)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }
}

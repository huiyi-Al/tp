package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.log.LogHistory;
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

    // Data fields
    private final Address address;
    private final Notes notes;
    private final LogHistory logHistory;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Notes notes, LogHistory logHistory,
            Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, notes, logHistory, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.notes = notes;
        this.logHistory = logHistory;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     * Initializes person with an empty {@code LogHistory}.
     */
    public Person(Name name, Phone phone, Email email, Address address, Notes notes, Set<Tag> tags) {
        this(name, phone, email, address, notes, new LogHistory(), tags);
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

    public Address getAddress() {
        return address;
    }

    public Notes getNotes() {
        return notes;
    }

    public LogHistory getLogHistory() {
        return logHistory;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same phone or email.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        // Extract only digits from phone numbers for comparison
        String thisPhoneDigits = extractDigits(this.phone.value);
        String otherPhoneDigits = extractDigits(otherPerson.phone.value);

        // Compare phone digits
        boolean samePhone = thisPhoneDigits.equals(otherPhoneDigits);

        // Compare emails case-insensitively
        boolean sameEmail = this.email.value.equalsIgnoreCase(otherPerson.email.value);

        // Check if phone digits match OR email matches
        return samePhone || sameEmail;
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
                && address.equals(otherPerson.address)
                && notes.equals(otherPerson.notes)
                && logHistory.equals(otherPerson.logHistory)
                && tags.equals(otherPerson.tags);
    }

    /**
     * Extracts only numeric digits from a phone string.
     * Removes -, spaces, and any other non-digit characters.
     */
    private String extractDigits(String phoneString) {
        return phoneString.replaceAll("[^0-9]", "");
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, notes, logHistory, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("notes", notes)
                .add("logCount", logHistory.size())
                .add("tags", tags)
                .toString();
    }

}

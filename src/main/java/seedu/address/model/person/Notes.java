package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a  Person's notes in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNotes(String)}
 */
public class Notes {

    public static final String DEFAULT_NOTE = "";
    public static final String MESSAGE_CONSTRAINTS = "Notes can be empty, but they should be a single line";
    public static final String VALIDATION_REGEX = "[^\\n\\r]*";

    public final String value;

    /**
     * Constructs a {@code Notes}.
     *
     * @param notes A valid notes.
     */
    public Notes(String notes) {
        requireNonNull(notes);
        checkArgument(isValidNotes(notes), MESSAGE_CONSTRAINTS);
        value = notes;
    }

    /**
     * Returns true if a given string is a valid notes.
     */
    public static boolean isValidNotes(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Notes)) {
            return false;
        }

        Notes otherNotes = (Notes) other;
        return value.equals(otherNotes.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

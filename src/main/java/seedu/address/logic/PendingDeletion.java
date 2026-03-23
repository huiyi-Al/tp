package seedu.address.logic;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;

/**
 * Represents a pending deletion waiting for confirmation.
 */
public class PendingDeletion {
    private final Index index;
    private final Person person;

    /**
     * Constructs a {@code PendingDeletion} with the specified index and person.
     *
     * @param index The 1-based index of the person in the displayed person list.
     * @param person The person to be deleted upon confirmation.
     */
    public PendingDeletion(Index index, Person person) {
        this.index = index;
        this.person = person;
    }

    public Index getIndex() {
        return index;
    }

    public Person getPerson() {
        return person;
    }
}

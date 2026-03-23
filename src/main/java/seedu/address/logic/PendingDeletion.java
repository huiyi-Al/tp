package seedu.address.logic;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;

/**
 * Represents a pending deletion waiting for confirmation.
 */
public class PendingDeletion {
    private final Index index;
    private final Person person;

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

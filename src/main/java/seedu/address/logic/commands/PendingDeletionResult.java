package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;

/**
 * Represents a pending deletion waiting for confirmation.
 * This is a specialized result type that signals LogicManager to wait for confirmation.
 */
public class PendingDeletionResult extends CommandResult {
    private final Person personToDelete;
    private final Index targetIndex;

    public PendingDeletionResult(String feedbackToUser, Person personToDelete, Index targetIndex) {
        super(feedbackToUser);
        this.personToDelete = personToDelete;
        this.targetIndex = targetIndex;
    }

    public Person getPersonToDelete() {
        return personToDelete;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    public boolean isPendingDeletion() {
        return true;
    }
}

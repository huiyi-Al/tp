package seedu.address.logic.pending;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Pending action for delete confirmation.
 * This class is returned by {@link DeleteCommand} on its first execution
 * and handles the actual deletion when confirmed.
 */
public class DeletePendingAction implements PendingAction {

    private final Person personToDelete;
    private final Index targetIndex;
    private final String confirmationMessage;

    /**
     * Constructs a {@code DeletePendingAction} for the specified person and index.
     *
     * @param personToDelete The person to be deleted upon confirmation.
     * @param targetIndex The 1-based index of the person in the displayed list.
     */
    public DeletePendingAction(Person personToDelete, Index targetIndex) {
        this.personToDelete = personToDelete;
        this.targetIndex = targetIndex;
        this.confirmationMessage = String.format(DeleteCommand.MESSAGE_DELETE_CONFIRM,
                personToDelete.getName().fullName,
                personToDelete.getPhone().value,
                personToDelete.getEmail().value,
                DeleteCommand.COMMAND_WORD,
                targetIndex.getOneBased());
    }

    /**
     * Returns true if the given command matches this pending action.
     * A match occurs when the command is a {@link DeleteCommand} with the same target index.
     *
     * @param nextCommand The command to check for a match.
     * @return True if the command matches, false otherwise.
     */
    @Override
    public boolean matches(Command nextCommand) {
        if (!(nextCommand instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand deleteCommand = (DeleteCommand) nextCommand;
        return deleteCommand.getTargetIndex().equals(targetIndex);
    }

    /**
     * Completes the pending action by deleting the person from the model.
     *
     * @param model The model containing the address book data.
     * @return A {@link CommandResult} indicating successful deletion.
     * @throws CommandException If an error occurs during deletion.
     */
    @Override
    public CommandResult complete(Model model) throws CommandException {
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                formatPersonBasic(personToDelete)));
    }

    @Override
    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    /**
     * Formats a person's basic information for display.
     *
     * @param person The person to format.
     * @return A formatted string containing name, phone, and email.
     */
    private String formatPersonBasic(Person person) {
        return person.getName().fullName + "; Phone: " + person.getPhone().value
                + "; Email: " + person.getEmail().value;
    }
}

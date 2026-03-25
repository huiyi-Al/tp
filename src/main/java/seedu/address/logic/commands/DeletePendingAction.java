package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Pending action for delete confirmation.
 */
public class DeletePendingAction implements PendingAction {

    private final Person personToDelete;
    private final Index targetIndex;
    private final String confirmationMessage;

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

    @Override
    public boolean matches(Command nextCommand) {
        if (!(nextCommand instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand deleteCommand = (DeleteCommand) nextCommand;
        return deleteCommand.getTargetIndex().equals(targetIndex);
    }

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

    private String formatPersonBasic(Person person) {
        return person.getName().fullName + "; Phone: " + person.getPhone().value
                + "; Email: " + person.getEmail().value;
    }
}
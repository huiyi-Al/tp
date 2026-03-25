package seedu.address.logic.pending;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Pending action for clear confirmation.
 */
public class ClearPendingAction implements PendingAction {

    private final String confirmationMessage;

    public ClearPendingAction() {
        this.confirmationMessage = String.format(ClearCommand.MESSAGE_CLEAR_CONFIRM);
    }

    @Override
    public boolean matches(Command nextCommand) {
        return nextCommand instanceof ClearCommand;
    }

    @Override
    public CommandResult complete(Model model) throws CommandException {
        model.setAddressBook(new AddressBook());
        return new CommandResult(ClearCommand.MESSAGE_SUCCESS);
    }

    @Override
    public String getConfirmationMessage() {
        return confirmationMessage;
    }
}

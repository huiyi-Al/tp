package seedu.address.logic.pending;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Pending action for clear confirmation.
 * This class is returned by {@link ClearCommand} on its first execution
 * and handles the actual clearing of the address book when confirmed.
 */
public class ClearPendingAction implements PendingAction {

    private final String confirmationMessage;

    /**
     * Constructs a {@code ClearPendingAction} with the default confirmation message.
     */
    public ClearPendingAction() {
        this.confirmationMessage = String.format(ClearCommand.MESSAGE_CLEAR_CONFIRM);
    }

    @Override
    public boolean matches(Command nextCommand) {
        return nextCommand instanceof ClearCommand;
    }

    /**
     * Completes the pending action by clearing all entries from the address book.
     *
     * @param model The model containing the address book data.
     * @return A {@link CommandResult} indicating successful clearing.
     * @throws CommandException If an error occurs during clearing.
     */
    @Override
    public CommandResult complete(Model model) throws CommandException {
        model.setAddressBook(new AddressBook());
        return new CommandResult(ClearCommand.MESSAGE_SUCCESS).withSaveRequired();
    }

    @Override
    public String getConfirmationMessage() {
        return confirmationMessage;
    }
}

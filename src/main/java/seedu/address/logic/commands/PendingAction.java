package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an action pending confirmation.
 */
public class PendingAction {

    private final Command command;
    private final String confirmationMessage;

    public PendingAction(Command command, String confirmationMessage) {
        this.command = command;
        this.confirmationMessage = confirmationMessage;
    }

    public boolean matches(Command other) {
        return command.equals(other);
    }

    public CommandResult executeConfirmed(Model model) throws CommandException {
        // The command knows how to confirm itself
        return command.executeConfirmed(model);
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }
}

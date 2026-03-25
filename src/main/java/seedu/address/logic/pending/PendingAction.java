package seedu.address.logic.pending;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an action pending confirmation.
 * Returned by commands that require confirmation before execution.
 */
public interface PendingAction {

    /**
     * Returns true if the given command matches this pending action.
     */
    boolean matches(Command nextCommand);

    /**
     * Executes the confirmed action.
     */
    CommandResult complete(Model model) throws CommandException;

    /**
     * Returns the confirmation message to show to the user.
     */
    String getConfirmationMessage();
}

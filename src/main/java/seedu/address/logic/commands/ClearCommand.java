package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.pending.ClearPendingAction;
import seedu.address.logic.pending.PendingAction;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all entries from the address book.\n"
            + "Note: this command does not accept any arguments.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_CLEAR_CONFIRM =
            "Are you sure you want to clear all entries from the address book?\n"
                    + "Type 'clear' again to confirm.\n"
                    + "Any other command will cancel this pending action.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        PendingAction pendingAction = new ClearPendingAction();
        return new CommandResult(pendingAction.getConfirmationMessage(), pendingAction);
    }
}

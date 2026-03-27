package seedu.address.logic.pending;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Pending action for deletetag confirmation.
 * This class is returned by {@link DeleteTagCommand} on its first execution
 * and handles the actual deletion when confirmed.
 */
public class DeleteTagPendingAction implements PendingAction {

    private final Tag tagToDelete;
    private final String confirmationMessage;

    /**
     * Constructs a {@code DeleteTagPendingAction} for the specified tag.
     *
     * @param tagToDelete The tag to be deleted upon confirmation.
     */
    public DeleteTagPendingAction(Tag tagToDelete) {
        this.tagToDelete = tagToDelete;
        this.confirmationMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_CONFIRM,
                tagToDelete.tagName,
                DeleteTagCommand.COMMAND_WORD);
    }

    /**
     * Returns true if the given command matches this pending action.
     * A match occurs when the command is a {@link DeleteTagCommand} with the same target tag.
     *
     * @param nextCommand The command to check for a match.
     * @return True if the command matches, false otherwise.
     */
    @Override
    public boolean matches(Command nextCommand) {
        if (!(nextCommand instanceof DeleteTagCommand)) {
            return false;
        }
        DeleteTagCommand deleteTagCommand = (DeleteTagCommand) nextCommand;
        return deleteTagCommand.getTargetTag().equals(tagToDelete);
    }

    /**
     * Completes the pending action by deleting the tag from the model.
     *
     * @param model The model containing the address book data.
     * @return A {@link CommandResult} indicating successful global deletion.
     * @throws CommandException If an error occurs during deletion.
     */
    @Override
    public CommandResult complete(Model model) throws CommandException {
        model.deleteTag(tagToDelete);
        return new CommandResult(String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete.tagName));
    }

    @Override
    public String getConfirmationMessage() {
        return confirmationMessage;
    }
}

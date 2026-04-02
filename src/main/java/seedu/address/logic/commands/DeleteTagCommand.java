package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.pending.DeleteTagPendingAction;
import seedu.address.logic.pending.PendingAction;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Deletes an existing tag identified with its name.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a tag globally from the address book.\n"
            + "Parameters: TAG_NAME (must be between 1-50 characters long)\n"
            + "Example: " + COMMAND_WORD + " " + "Plumbing\n"
            + "Note: You will be prompted to confirm the deletion by typing the command again.";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_DELETE_CONFIRM =
            "Are you sure you want to delete tag %1$s?\n"
                    + "Type '%2$s %1$s' again to confirm. "
                    + "(Leading/trailing spaces and spaces between the command word and tag are ignored)\n"
                    + "Any other command will cancel this pending deletion.";

    public static final String MESSAGE_TAG_NOT_FOUND = "The tag '%1$s' does not exist in the address book.";

    private static final Logger logger = LogsCenter.getLogger(DeleteTagCommand.class);

    private final Tag targetTag;


    /**
     * Constructs a {@code DeleteTagCommand} to delete the specified tag.
     *
     * @param targetTag The tag to be deleted.
     */
    public DeleteTagCommand(Tag targetTag) {
        requireNonNull(targetTag);
        this.targetTag = targetTag;
    }

    public Tag getTargetTag() {
        return targetTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTag(targetTag)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, targetTag.tagName));
        }

        logger.info("Creating pending deletion for tag: " + targetTag.tagName);

        PendingAction pendingAction = new DeleteTagPendingAction(targetTag);
        assert pendingAction != null : "PendingAction should not be null";
        return new CommandResult(pendingAction.getConfirmationMessage(), pendingAction);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }
        DeleteTagCommand otherCommand = (DeleteTagCommand) other;
        return targetTag.equals(otherCommand.targetTag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetTag", targetTag)
                .toString();
    }
}

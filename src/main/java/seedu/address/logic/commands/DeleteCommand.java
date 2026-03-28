package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.pending.DeletePendingAction;
import seedu.address.logic.pending.PendingAction;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using its displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Note: You will be prompted to confirm the deletion by typing the command again.";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_CONFIRM =
            "Are you sure you want to delete %1$s (%2$s, %3$s)?\n"
                    + "Type '%4$s %5$d' again to confirm. "
                    + "(Any whitespace will be trimmed, and numbers with leading zeros (e.g., '0%5$d', '00%5$d') "
                    + "will also confirm the deletion)\n"
                    + "Any other command will cancel this pending deletion.";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Index targetIndex;

    /**
     * Constructs a {@code DeleteCommand} to delete the person at the specified index.
     *
     * <p>Note: This command requires two-step confirmation. The first execution will
     * return a {@link PendingAction} with a confirmation message. The second execution
     * with the same index will complete the deletion.</p>
     *
     * @param targetIndex The 1-based index of the person in the displayed person list to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        logger.info("Creating DeleteCommand for index: " + targetIndex.getOneBased());
        this.targetIndex = targetIndex;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing delete command for index: " + targetIndex.getOneBased());

        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        assert lastShownList != null : "Filtered person list should not be null";

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.info("Invalid index: " + targetIndex.getOneBased()
                    + " (list size: " + lastShownList.size() + ")");
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        assert personToDelete != null : "Person at valid index should not be null";

        logger.info("Creating pending deletion for: " + personToDelete.getName().fullName);

        PendingAction pendingAction = new DeletePendingAction(personToDelete, targetIndex);

        assert pendingAction != null : "PendingAction should not be null";

        return new CommandResult(pendingAction.getConfirmationMessage(), pendingAction);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

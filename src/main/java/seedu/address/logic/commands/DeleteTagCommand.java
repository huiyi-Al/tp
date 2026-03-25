package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Deletes an existing tag identified with its name.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a tag globally from the address book.\n"
            + "Parameters: TAG_NAME (must not be blank)\n"
            + "Example: " + COMMAND_WORD + " " + "Plumbing";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "The tag '%1$s' does not exist in the address book.";

    private final Tag targetTag;

    public DeleteTagCommand(Tag targetTag) {
        requireNonNull(targetTag);
        this.targetTag = targetTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTag(targetTag)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, targetTag.tagName));
        }

        model.deleteTag(targetTag);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, targetTag));
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

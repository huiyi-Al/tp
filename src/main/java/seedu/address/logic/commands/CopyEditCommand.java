package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Copies the edit command format for the specified client to clipboard.
 */
public class CopyEditCommand extends Command {

    public static final String COMMAND_WORD = "copyedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies the edit command format for the client identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COPY_SUCCESS = "Edit command format copied to clipboard for: %1$s (Index: %2$s)"
            + "\n(Press Ctrl+V or Cmd+V to paste)";
    public static final String MESSAGE_CLIPBOARD_UNAVAILABLE = "Could not access clipboard. Please copy manually.";

    private final Index targetIndex;

    public CopyEditCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToCopy = lastShownList.get(targetIndex.getZeroBased());
        int personIndex = targetIndex.getOneBased();

        String editCommand = generateEditCommand(personToCopy, personIndex);

        try {
            StringSelection stringSelection = new StringSelection(editCommand);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            return new CommandResult(String.format(MESSAGE_COPY_SUCCESS, personToCopy.getName().fullName, personIndex));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_CLIPBOARD_UNAVAILABLE);
        }
    }

    /**
     * Generates the edit command format with current client details.
     */
    private String generateEditCommand(Person person, int personIndex) {
        StringBuilder sb = new StringBuilder();
        sb.append("edit ").append(personIndex).append(" ");
        sb.append("--name=").append(person.getName().fullName).append(" ");
        sb.append("--phone=").append(person.getPhone().value).append(" ");
        sb.append("--email=").append(person.getEmail().value).append(" ");
        sb.append("--address=").append(person.getAddress().value).append(" ");
        sb.append("--notes=").append(person.getNotes().value).append(" ");

        for (Tag tag : person.getTags()) {
            sb.append("--tag=").append(tag.tagName).append(" ");
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CopyEditCommand)) {
            return false;
        }

        CopyEditCommand otherCopyEditCommand = (CopyEditCommand) other;
        return targetIndex.equals(otherCopyEditCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

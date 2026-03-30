package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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
            + ": Copies the edit command format for the client identified by the "
            + "index number in the current displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COPY_SUCCESS = "Edit command format copied to clipboard for: %1$s (Index: %2$s)"
            + "\n(Press Ctrl+V or Cmd+V to paste)";
    public static final String MESSAGE_CLIPBOARD_UNAVAILABLE = "Could not access clipboard. Please copy manually.";

    private static boolean isTestMode = false;
    private static boolean simulateClipboardFailure = false;
    private final Index targetIndex;

    /**
     * Constructor for production use.
     */
    public CopyEditCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * For testing only - sets test mode.
     */
    public static void setTestMode(boolean testMode) {
        isTestMode = testMode;
    }

    /**
     * For testing only - sets whether to simulate clipboard failure.
     */
    public static void setSimulateClipboardFailure(boolean simulateFailure) {
        simulateClipboardFailure = simulateFailure;
    }

    /**
     * For testing only - resets test flags.
     */
    public static void resetTestFlags() {
        isTestMode = false;
        simulateClipboardFailure = false;
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

        // In test mode, skip real clipboard
        if (isTestMode) {
            if (simulateClipboardFailure) {
                throw new CommandException(MESSAGE_CLIPBOARD_UNAVAILABLE);
            }
            return new CommandResult(String.format(MESSAGE_COPY_SUCCESS, personToCopy.getName().fullName, personIndex));
        }

        // Production code - real clipboard
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
        sb.append(EditCommand.COMMAND_WORD).append(" ").append(personIndex).append(" ");
        sb.append(PREFIX_NAME).append(person.getName().fullName).append(" ");
        sb.append(PREFIX_PHONE).append(person.getPhone().value).append(" ");
        sb.append(PREFIX_EMAIL).append(person.getEmail().value).append(" ");
        sb.append(PREFIX_ADDRESS).append(person.getAddress().value).append(" ");

        if (!person.getNotes().value.isEmpty()) {
            sb.append(PREFIX_NOTES).append(person.getNotes().value).append(" ");
        }

        for (Tag tag : person.getTags()) {
            sb.append(PREFIX_TAG).append(tag.tagName).append(" ");
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

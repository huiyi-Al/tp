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

/**
 * Copies the address of a person identified using its displayed index from the address book to the clipboard.
 */
public class CopyAddrCommand extends Command {

    public static final String COMMAND_WORD = "copyaddr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies the address of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COPY_ADDRESS_SUCCESS = "Address copied to clipboard for: %1$s (Index: %2$s)\n(Press Ctrl+V or Cmd+V to paste)";
    public static final String MESSAGE_CLIPBOARD_UNAVAILABLE = "Could not access clipboard. Please copy manually.";

    private final Index targetIndex;

    public CopyAddrCommand(Index targetIndex) {
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
        String address = personToCopy.getAddress().value;
        String personName = personToCopy.getName().fullName;
        int personIndex = targetIndex.getOneBased();

        try {
            // Copy to system clipboard
            StringSelection stringSelection = new StringSelection(address);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            return new CommandResult(String.format(MESSAGE_COPY_ADDRESS_SUCCESS, personName, personIndex));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_CLIPBOARD_UNAVAILABLE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CopyAddrCommand)) {
            return false;
        }

        CopyAddrCommand otherCopyAddrCommand = (CopyAddrCommand) other;
        return targetIndex.equals(otherCopyAddrCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
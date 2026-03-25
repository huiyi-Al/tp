package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using its displayed index from the address book.
 */
public class DeleteCommand extends ConfirmableCommand {

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Note: You will be prompted to confirm the deletion by typing the command again.";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_CONFIRM =
            "Are you sure you want to delete %1$s (%2$s, %3$s)?\n"
                    + "Type '%4$s %5$d' again to confirm.\n"
                    + "Any other command will cancel this pending deletion.";

    private final Index targetIndex;
    private Person personToDelete;

    public DeleteCommand(Index targetIndex) {
        logger.log(Level.INFO, "Creating DeleteCommand for index: " + targetIndex.getOneBased());
        this.targetIndex = targetIndex;
    }

    @Override
    public void prepare(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.INFO, "Invalid index: " + targetIndex.getOneBased()
                    + " (list size: " + lastShownList.size() + ")");
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        this.personToDelete = lastShownList.get(targetIndex.getZeroBased());
        logger.log(Level.INFO, "Prepared deletion for: " + personToDelete.getName().fullName);
    }

    @Override
    public String getConfirmationMessage() {
        return String.format(MESSAGE_DELETE_CONFIRM,
                personToDelete.getName().fullName,
                personToDelete.getPhone().value,
                personToDelete.getEmail().value,
                COMMAND_WORD,
                targetIndex.getOneBased());
    }

    @Override
    public CommandResult executeConfirmed(Model model) throws CommandException {
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                formatPersonBasic(personToDelete)));
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public int getConfirmationIndex() {
        return targetIndex.getOneBased();
    }

    private String formatPersonBasic(Person person) {
        return person.getName().fullName + "; Phone: " + person.getPhone().value
                + "; Email: " + person.getEmail().value;
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
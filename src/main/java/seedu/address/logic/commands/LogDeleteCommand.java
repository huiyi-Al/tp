package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogHistory;

/**
 * Deletes a log entry from a person identified by the displayed person index.
 */
public class LogDeleteCommand extends Command {

    public static final String COMMAND_WORD = "log-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a log entry from the client identified by the index number used "
            + "in the displayed person list.\n"
            + "Parameters: PERSON_INDEX LOG_INDEX\n"
            + "Example: " + COMMAND_WORD + " 2 1";

    public static final String MESSAGE_SUCCESS = "Deleted log %1$d from client: %2$s";
    public static final String MESSAGE_NO_LOGS = "This client has no logs.";
    public static final String MESSAGE_INVALID_LOG_DISPLAYED_INDEX =
            "The log index provided is invalid for the selected client.";

    private final Index personIndex;
    private final Index logIndex;

    /**
     * Creates a {@code LogDeleteCommand} for the given person index and log index.
     */
    public LogDeleteCommand(Index personIndex, Index logIndex) {
        requireNonNull(personIndex);
        requireNonNull(logIndex);
        this.personIndex = personIndex;
        this.logIndex = logIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(personIndex.getZeroBased());
        LogHistory logHistory = targetPerson.getLogHistory();
        if (logHistory.isEmpty()) {
            throw new CommandException(MESSAGE_NO_LOGS);
        }
        if (logIndex.getZeroBased() >= logHistory.size()) {
            throw new CommandException(MESSAGE_INVALID_LOG_DISPLAYED_INDEX);
        }

        LogHistory updatedLogHistory = logHistory.delete(logIndex);
        Person editedPerson = createPersonWithUpdatedLogHistory(targetPerson, updatedLogHistory);
        model.setPerson(targetPerson, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, logIndex.getOneBased(), editedPerson.getName()));
    }

    /**
     * Creates a copy of {@code original} with only log history changed to {@code updatedLogHistory}.
     */
    private static Person createPersonWithUpdatedLogHistory(Person original, LogHistory updatedLogHistory) {
        return new Person(
                original.getName(),
                original.getPhone(),
                original.getEmail(),
                original.getAddress(),
                original.getNotes(),
                updatedLogHistory,
                original.getTags());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LogDeleteCommand)) {
            return false;
        }

        LogDeleteCommand otherCommand = (LogDeleteCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && logIndex.equals(otherCommand.logIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("logIndex", logIndex)
                .toString();
    }
}

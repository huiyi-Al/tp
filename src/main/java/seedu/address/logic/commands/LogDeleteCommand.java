package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.pending.LogDeletePendingAction;
import seedu.address.logic.pending.PendingAction;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogHistory;

/**
 * Deletes a log entry from a person identified by the displayed person index.
 */
public class LogDeleteCommand extends Command {
    public static final String COMMAND_WORD = "logdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a log entry from the client identified by the index number used "
            + "in the displayed person list.\n"
            + "Parameters: PERSON_INDEX LOG_INDEX\n"
            + "Example: " + COMMAND_WORD + " 2 1\n"
            + "The LOG_INDEX refers to the log number shown in the UI.\n"
            + "Note: You will be prompted to confirm the deletion by typing the command again.";

    public static final String MESSAGE_SUCCESS = "Deleted log %1$d from client: %2$s";
    public static final String MESSAGE_DELETE_CONFIRM =
            "Are you sure you want to delete log #%2$d from client %1$s?\n"
                    + "Log: %3$s\n"
                    + "Type '%4$s %5$d %6$d' again to confirm. "
                    + "(Leading/trailing spaces and spaces between the command word and indexes are ignored, "
                    + "numbers with leading zeros (e.g., '0%5$d') also confirm the deletion)\n"
                    + "Any other command will cancel this pending action.";

    public static final String MESSAGE_NO_LOGS = "This client has no logs.";
    public static final String MESSAGE_INVALID_LOG_DISPLAYED_INDEX =
            "The log index provided is invalid for the selected client.";
    private static final Logger logger = LogsCenter.getLogger(LogDeleteCommand.class);

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

    public Index getPersonIndex() {
        return personIndex;
    }

    public Index getLogIndex() {
        return logIndex;
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
        if (logIndex.getOneBased() > logHistory.size()) {
            throw new CommandException(MESSAGE_INVALID_LOG_DISPLAYED_INDEX);
        }

        Index storageLogIndex = Index.fromZeroBased(logHistory.size() - logIndex.getOneBased());
        logPendingDeleteTransition(personIndex.getOneBased(), logIndex.getOneBased(),
                storageLogIndex.getOneBased(), logHistory.size());
        PendingAction pendingAction =
                new LogDeletePendingAction(targetPerson, personIndex, logIndex, storageLogIndex, logHistory);
        return new CommandResult(pendingAction.getConfirmationMessage(), pendingAction);
    }

    private static void logPendingDeleteTransition(int personIndexOneBased, int displayIndex,
                                                   int storageIndex, int currentLogCount) {
        if (!logger.isLoggable(Level.FINE)) {
            return;
        }
        logger.fine(() -> String.format("Executing logdelete transition for person index %d: "
                        + "display index %d maps to storage index %d, current log count=%d, awaiting confirmation",
                personIndexOneBased, displayIndex, storageIndex, currentLogCount));
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

package seedu.address.logic.pending;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LogDeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogEntry;
import seedu.address.model.person.log.LogHistory;

/**
 * Pending action for log-delete confirmation.
 */
public class LogDeletePendingAction implements PendingAction {

    private final Person person;
    private final Index personIndex;
    private final Index displayLogIndex;
    private final Index storageLogIndex;
    private final LogEntry logEntry;
    private final LogHistory originalLogHistory;
    private final String confirmationMessage;

    /**
     * Constructs a {@code LogDeletePendingAction} for the specified person and log entry.
     *
     * <p>This constructor prepares the pending action by:
     * <ul>
     *   <li>Storing the person and indices for later matching</li>
     *   <li>Retrieving the target log entry from the log history</li>
     *   <li>Generating a confirmation message with the log details</li>
     * </ul>
     *
     * @param person The person whose log entry is to be deleted.
     * @param personIndex The 1-based index of the person in the displayed list.
     * @param displayLogIndex The 1-based log number shown in the UI.
     * @param storageLogIndex The 1-based index of the log entry in the newest-first log history.
     * @param logHistory The current log history of the person (used to retrieve the log entry).
     */
    public LogDeletePendingAction(Person person, Index personIndex, Index displayLogIndex,
                                  Index storageLogIndex, LogHistory logHistory) {
        this.person = person;
        this.personIndex = personIndex;
        this.displayLogIndex = displayLogIndex;
        this.storageLogIndex = storageLogIndex;
        this.originalLogHistory = logHistory;

        int zeroBasedIndex = storageLogIndex.getZeroBased();
        this.logEntry = logHistory.asUnmodifiableList().get(zeroBasedIndex);

        this.confirmationMessage = String.format(LogDeleteCommand.MESSAGE_DELETE_CONFIRM,
                person.getName().fullName,
                displayLogIndex.getOneBased(),
                logEntry.getDescription(),
                LogDeleteCommand.COMMAND_WORD,
                personIndex.getOneBased(),
                displayLogIndex.getOneBased());
    }

    @Override
    public boolean matches(Command nextCommand) {
        if (!(nextCommand instanceof LogDeleteCommand)) {
            return false;
        }
        LogDeleteCommand logDeleteCommand = (LogDeleteCommand) nextCommand;
        return logDeleteCommand.getPersonIndex().equals(personIndex)
                && logDeleteCommand.getLogIndex().equals(displayLogIndex);
    }

    @Override
    public CommandResult complete(Model model) throws CommandException {
        LogHistory updatedLogHistory = originalLogHistory.delete(storageLogIndex);
        Person editedPerson = createPersonWithUpdatedLogHistory(person, updatedLogHistory);
        model.setPerson(person, editedPerson);
        return new CommandResult(String.format(LogDeleteCommand.MESSAGE_SUCCESS,
                displayLogIndex.getOneBased(), person.getName().fullName));
    }

    @Override
    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    private Person createPersonWithUpdatedLogHistory(Person original, LogHistory updatedLogHistory) {
        return new Person(
                original.getName(),
                original.getPhone(),
                original.getEmail(),
                original.getAddress(),
                original.getNotes(),
                updatedLogHistory,
                original.getTags());
    }
}

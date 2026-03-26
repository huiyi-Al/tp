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
    private final Index logIndex;
    private final LogEntry logEntry;
    private final LogHistory originalLogHistory;
    private final String confirmationMessage;

    public LogDeletePendingAction(Person person, Index personIndex, Index logIndex, LogHistory logHistory) {
        this.person = person;
        this.personIndex = personIndex;
        this.logIndex = logIndex;
        this.originalLogHistory = logHistory;

        int zeroBasedIndex = logIndex.getZeroBased();
        this.logEntry = logHistory.asUnmodifiableList().get(zeroBasedIndex);

        this.confirmationMessage = String.format(LogDeleteCommand.MESSAGE_DELETE_CONFIRM,
                person.getName().fullName,
                logIndex.getOneBased(),
                logEntry.getDescription(),
                LogDeleteCommand.COMMAND_WORD,
                personIndex.getOneBased(),
                logIndex.getOneBased());
    }

    @Override
    public boolean matches(Command nextCommand) {
        if (!(nextCommand instanceof LogDeleteCommand)) {
            return false;
        }
        LogDeleteCommand logDeleteCommand = (LogDeleteCommand) nextCommand;
        return logDeleteCommand.getPersonIndex().equals(personIndex)
                && logDeleteCommand.getLogIndex().equals(logIndex);
    }

    @Override
    public CommandResult complete(Model model) throws CommandException {
        LogHistory updatedLogHistory = originalLogHistory.delete(logIndex);
        Person editedPerson = createPersonWithUpdatedLogHistory(person, updatedLogHistory);
        model.setPerson(person, editedPerson);
        return new CommandResult(String.format(LogDeleteCommand.MESSAGE_SUCCESS,
                logIndex.getOneBased(), person.getName().fullName));
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

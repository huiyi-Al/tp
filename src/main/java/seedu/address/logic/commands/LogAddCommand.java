package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogEntry;
import seedu.address.model.person.log.LogHistory;
import seedu.address.model.person.log.LogMessage;

/**
 * Adds a timestamped log entry to a person identified by the displayed index.
 */
public class LogAddCommand extends Command {

    public static final String COMMAND_WORD = "logadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a log entry to the client identified by the index number used in the displayed person list.\n"
            + "Parameters: "
            + "INDEX LOG_MESSAGE\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 Observed leakage beneath sink during site visit.";

    public static final String MESSAGE_SUCCESS = "Added log to client: %1$s";

    private final Index personIndex;
    private final LogMessage logMessage;
    private final Supplier<LocalDateTime> timestampSupplier;

    /**
     * Creates a {@code LogAddCommand} for the given person index and message.
     */
    public LogAddCommand(Index personIndex, LogMessage logMessage) {
        this(personIndex, logMessage, LocalDateTime::now);
    }

    /**
     * Creates a {@code LogAddCommand} with a timestamp supplier.
     */
    LogAddCommand(Index personIndex, LogMessage logMessage, Supplier<LocalDateTime> timestampSupplier) {
        requireNonNull(personIndex);
        requireNonNull(logMessage);
        requireNonNull(timestampSupplier);
        this.personIndex = personIndex;
        this.logMessage = logMessage;
        this.timestampSupplier = timestampSupplier;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(personIndex.getZeroBased());
        LogEntry newLogEntry = new LogEntry(timestampSupplier.get(), logMessage);
        LogHistory updatedLogHistory = targetPerson.getLogHistory().add(newLogEntry);
        Person editedPerson = createPersonWithUpdatedLogHistory(targetPerson, updatedLogHistory);

        model.setPerson(targetPerson, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson.getName()));
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

        if (!(other instanceof LogAddCommand)) {
            return false;
        }

        LogAddCommand otherCommand = (LogAddCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && logMessage.equals(otherCommand.logMessage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("logMessage", logMessage)
                .toString();
    }
}

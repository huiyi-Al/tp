package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogEntry;
import seedu.address.model.person.log.LogHistory;
import seedu.address.model.person.log.LogMessage;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@code LogAddCommand}.
 */
public class LogAddCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLogToPersonWithNoLogs_success() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String messageText = "Observed leakage beneath sink during site visit.";
        LocalDateTime timestamp = LocalDateTime.of(2026, 3, 23, 10, 0);
        LogAddCommand logAddCommand =
                new LogAddCommand(INDEX_FIRST_PERSON, new LogMessage(messageText), () -> timestamp);

        LogEntry expectedLogEntry = new LogEntry(timestamp, new LogMessage(messageText));
        Person editedPerson = new PersonBuilder(targetPerson)
                .withLogHistory(targetPerson.getLogHistory().add(expectedLogEntry))
                .build();
        String expectedMessage = String.format(LogAddCommand.MESSAGE_SUCCESS, editedPerson.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, editedPerson);

        assertCommandSuccess(logAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addLogToPersonWithExistingLogs_newLogBecomesLatest() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LogEntry existingLog = new LogEntry(LocalDateTime.of(2026, 3, 20, 9, 0),
                new LogMessage("Older log entry"));
        Person personWithLogs = new PersonBuilder(firstPerson)
                .withLogHistory(new LogHistory().add(existingLog))
                .build();
        model.setPerson(firstPerson, personWithLogs);

        String newMessageText = "Client requested follow-up call next Wednesday.";
        LocalDateTime timestamp = LocalDateTime.of(2026, 3, 23, 10, 30);
        LogAddCommand logAddCommand =
                new LogAddCommand(INDEX_FIRST_PERSON, new LogMessage(newMessageText), () -> timestamp);
        LogEntry expectedLogEntry = new LogEntry(timestamp, new LogMessage(newMessageText));
        Person editedPerson = new PersonBuilder(personWithLogs)
                .withLogHistory(personWithLogs.getLogHistory().add(expectedLogEntry))
                .build();
        String expectedMessage = String.format(LogAddCommand.MESSAGE_SUCCESS, editedPerson.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithLogs, editedPerson);

        assertCommandSuccess(logAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LogAddCommand logAddCommand = new LogAddCommand(outOfBoundIndex, new LogMessage("Valid log message"));

        assertCommandFailure(logAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertTrue(INDEX_SECOND_PERSON.getZeroBased() < model.getAddressBook().getPersonList().size());

        LogAddCommand logAddCommand = new LogAddCommand(INDEX_SECOND_PERSON, new LogMessage("Valid log message"));
        assertCommandFailure(logAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LogAddCommand firstCommand = new LogAddCommand(INDEX_FIRST_PERSON, new LogMessage("First message"));
        LogAddCommand firstCommandCopy = new LogAddCommand(INDEX_FIRST_PERSON, new LogMessage("First message"));
        LogAddCommand secondCommand = new LogAddCommand(INDEX_FIRST_PERSON, new LogMessage("Second message"));

        assertTrue(firstCommand.equals(firstCommandCopy));
        assertTrue(firstCommand.equals(firstCommand));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(secondCommand));
    }
}

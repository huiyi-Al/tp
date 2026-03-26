package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.pending.PendingAction;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogEntry;
import seedu.address.model.person.log.LogHistory;
import seedu.address.model.person.log.LogMessage;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@code LogDeleteCommand}.
 */
public class LogDeleteCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteMiddleLogFromPerson_showsConfirmation() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LogEntry oldestLog = new LogEntry(LocalDateTime.of(2026, 3, 20, 9, 0), new LogMessage("Oldest"));
        LogEntry middleLog = new LogEntry(LocalDateTime.of(2026, 3, 21, 9, 0), new LogMessage("Middle"));
        LogEntry newestLog = new LogEntry(LocalDateTime.of(2026, 3, 22, 9, 0), new LogMessage("Newest"));
        Person personWithLogs = new PersonBuilder(firstPerson)
                .withLogHistory(new LogHistory().add(oldestLog).add(middleLog).add(newestLog))
                .build();
        model.setPerson(firstPerson, personWithLogs);

        Index logIndex = Index.fromOneBased(2);
        LogDeleteCommand logDeleteCommand = new LogDeleteCommand(INDEX_FIRST_PERSON, logIndex);

        String expectedMessage = String.format(LogDeleteCommand.MESSAGE_DELETE_CONFIRM,
                personWithLogs.getName().fullName,
                logIndex.getOneBased(),
                middleLog.getDescription(),
                LogDeleteCommand.COMMAND_WORD,
                INDEX_FIRST_PERSON.getOneBased(),
                logIndex.getOneBased());

        try {
            CommandResult result = logDeleteCommand.execute(model);
            assertTrue(result.hasPendingAction());
            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertTrue(result.getPendingAction().isPresent());
        } catch (CommandException e) {
            fail("Should not throw CommandException, should return CommandResult with PendingAction");
        }
    }

    @Test
    public void execute_deleteLatestLog_showsConfirmation() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Index latestLogIndex = Index.fromOneBased(1);
        LogDeleteCommand logDeleteCommand = new LogDeleteCommand(INDEX_SECOND_PERSON, latestLogIndex);

        LogEntry logToDelete = targetPerson.getLogHistory().asUnmodifiableList().get(0);

        String expectedMessage = String.format(LogDeleteCommand.MESSAGE_DELETE_CONFIRM,
                targetPerson.getName().fullName,
                latestLogIndex.getOneBased(),
                logToDelete.getDescription(),
                LogDeleteCommand.COMMAND_WORD,
                INDEX_SECOND_PERSON.getOneBased(),
                latestLogIndex.getOneBased());

        try {
            CommandResult result = logDeleteCommand.execute(model);
            assertTrue(result.hasPendingAction());
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException e) {
            fail("Should not throw CommandException, should return CommandResult with PendingAction");
        }
    }

    @Test
    public void execute_confirmed_deletesMiddleLogSuccess() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LogEntry oldestLog = new LogEntry(LocalDateTime.of(2026, 3, 20, 9, 0), new LogMessage("Oldest"));
        LogEntry middleLog = new LogEntry(LocalDateTime.of(2026, 3, 21, 9, 0), new LogMessage("Middle"));
        LogEntry newestLog = new LogEntry(LocalDateTime.of(2026, 3, 22, 9, 0), new LogMessage("Newest"));
        Person personWithLogs = new PersonBuilder(firstPerson)
                .withLogHistory(new LogHistory().add(oldestLog).add(middleLog).add(newestLog))
                .build();
        model.setPerson(firstPerson, personWithLogs);

        Index logIndex = Index.fromOneBased(2);
        LogDeleteCommand logDeleteCommand = new LogDeleteCommand(INDEX_FIRST_PERSON, logIndex);

        CommandResult firstResult = logDeleteCommand.execute(model);
        assertTrue(firstResult.hasPendingAction());
        PendingAction pendingAction = firstResult.getPendingAction().get();

        assertTrue(pendingAction.matches(logDeleteCommand));

        CommandResult result = pendingAction.complete(model);

        Person editedPerson = new PersonBuilder(personWithLogs)
                .withLogHistory(personWithLogs.getLogHistory().delete(logIndex))
                .build();
        String expectedMessage = String.format(LogDeleteCommand.MESSAGE_SUCCESS,
                logIndex.getOneBased(), editedPerson.getName());
        assertEquals(expectedMessage, result.getFeedbackToUser());

        assertEquals(2, model.getFilteredPersonList().get(0).getLogHistory().size());
    }

    @Test
    public void execute_confirmed_deletesLatestLogSuccess() throws Exception {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Index latestLogIndex = Index.fromOneBased(1);
        LogDeleteCommand logDeleteCommand = new LogDeleteCommand(INDEX_SECOND_PERSON, latestLogIndex);

        CommandResult firstResult = logDeleteCommand.execute(model);
        assertTrue(firstResult.hasPendingAction());
        PendingAction pendingAction = firstResult.getPendingAction().get();

        CommandResult result = pendingAction.complete(model);

        Person editedPerson = new PersonBuilder(targetPerson)
                .withLogHistory(targetPerson.getLogHistory().delete(latestLogIndex))
                .build();
        String expectedMessage = String.format(LogDeleteCommand.MESSAGE_SUCCESS,
                latestLogIndex.getOneBased(), editedPerson.getName());
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LogDeleteCommand logDeleteCommand = new LogDeleteCommand(outOfBoundIndex, Index.fromOneBased(1));

        assertCommandFailure(logDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertTrue(INDEX_SECOND_PERSON.getZeroBased() < model.getAddressBook().getPersonList().size());

        LogDeleteCommand logDeleteCommand = new LogDeleteCommand(INDEX_SECOND_PERSON, Index.fromOneBased(1));
        assertCommandFailure(logDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noLogs_throwsCommandException() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LogDeleteCommand logDeleteCommand = new LogDeleteCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));

        assertTrue(targetPerson.getLogHistory().isEmpty());
        assertCommandFailure(logDeleteCommand, model, LogDeleteCommand.MESSAGE_NO_LOGS);
    }

    @Test
    public void execute_invalidLogIndex_throwsCommandException() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Index outOfBoundsLogIndex = Index.fromOneBased(targetPerson.getLogHistory().size() + 1);
        LogDeleteCommand logDeleteCommand = new LogDeleteCommand(INDEX_SECOND_PERSON, outOfBoundsLogIndex);

        assertCommandFailure(logDeleteCommand, model, LogDeleteCommand.MESSAGE_INVALID_LOG_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LogDeleteCommand firstCommand = new LogDeleteCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        LogDeleteCommand firstCommandCopy = new LogDeleteCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        LogDeleteCommand secondCommand = new LogDeleteCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));
        LogDeleteCommand thirdCommand = new LogDeleteCommand(INDEX_SECOND_PERSON, Index.fromOneBased(1));

        assertTrue(firstCommand.equals(firstCommandCopy));
        assertTrue(firstCommand.equals(firstCommand));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(thirdCommand));
    }

    @Test
    public void toStringMethod() {
        Index personIndex = Index.fromOneBased(2);
        Index logIndex = Index.fromOneBased(1);
        LogDeleteCommand command = new LogDeleteCommand(personIndex, logIndex);
        String expected = LogDeleteCommand.class.getCanonicalName()
                + "{personIndex=" + personIndex + ", logIndex=" + logIndex + "}";
        assertEquals(expected, command.toString());
    }
}

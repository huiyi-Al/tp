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
 * Contains integration tests for {@code LogDeleteCommand}.
 */
public class LogDeleteCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteMiddleLogFromPerson_success() {
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

        Person editedPerson = new PersonBuilder(personWithLogs)
                .withLogHistory(personWithLogs.getLogHistory().delete(logIndex))
                .build();
        String expectedMessage = String.format(LogDeleteCommand.MESSAGE_SUCCESS, logIndex.getOneBased(),
                editedPerson.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithLogs, editedPerson);

        assertCommandSuccess(logDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteLatestLog_success() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Index latestLogIndex = Index.fromOneBased(1);
        LogDeleteCommand logDeleteCommand = new LogDeleteCommand(INDEX_SECOND_PERSON, latestLogIndex);

        Person editedPerson = new PersonBuilder(targetPerson)
                .withLogHistory(targetPerson.getLogHistory().delete(latestLogIndex))
                .build();
        String expectedMessage = String.format(LogDeleteCommand.MESSAGE_SUCCESS, latestLogIndex.getOneBased(),
                editedPerson.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, editedPerson);

        assertCommandSuccess(logDeleteCommand, model, expectedMessage, expectedModel);
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

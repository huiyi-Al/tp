package seedu.address.logic.pending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LOG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LOG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LogDeleteCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogEntry;
import seedu.address.model.person.log.LogHistory;
import seedu.address.model.person.log.LogMessage;

public class LogDeletePendingActionTest {

    private Person person;
    private LogHistory logHistory;
    private LogDeletePendingAction pendingAction;

    @BeforeEach
    public void setUp() {
        LogEntry log1 = new LogEntry(LocalDateTime.now(), new LogMessage("First log"));
        LogEntry log2 = new LogEntry(LocalDateTime.now().minusDays(1), new LogMessage("Second log"));
        logHistory = new LogHistory().add(log1).add(log2);

        person = ALICE;
        pendingAction = new LogDeletePendingAction(person, INDEX_FIRST_PERSON,
                INDEX_FIRST_LOG, INDEX_SECOND_LOG, logHistory);
    }

    @Test
    public void matches_sameCommand_returnsTrue() {
        LogDeleteCommand sameCommand = new LogDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LOG);
        assertTrue(pendingAction.matches(sameCommand));
    }

    @Test
    public void matches_differentPersonIndex_returnsFalse() {
        LogDeleteCommand differentPerson = new LogDeleteCommand(INDEX_SECOND_PERSON, INDEX_FIRST_LOG);
        assertFalse(pendingAction.matches(differentPerson));
    }

    @Test
    public void matches_differentLogIndex_returnsFalse() {
        LogDeleteCommand differentLog = new LogDeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_LOG);
        assertFalse(pendingAction.matches(differentLog));
    }

    @Test
    public void matches_differentBothIndices_returnsFalse() {
        LogDeleteCommand differentBoth = new LogDeleteCommand(INDEX_SECOND_PERSON, INDEX_SECOND_LOG);
        assertFalse(pendingAction.matches(differentBoth));
    }

    @Test
    public void matches_nonLogDeleteCommand_returnsFalse() {
        assertFalse(pendingAction.matches(new AddCommand(person)));
        assertFalse(pendingAction.matches(new DeleteCommand(INDEX_FIRST_PERSON)));
        assertFalse(pendingAction.matches(new ClearCommand()));
        assertFalse(pendingAction.matches(new ListCommand()));
    }

    @Test
    public void matches_nullCommand_returnsFalse() {
        assertFalse(pendingAction.matches(null));
    }
}

package seedu.address.logic.pending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeletePendingActionTest {

    @Test
    public void matches_deleteCommandSameIndex_returnsTrue() {
        Person person = new PersonBuilder().withName("Test User").build();
        DeletePendingAction pendingAction = new DeletePendingAction(person, INDEX_FIRST_PERSON);

        DeleteCommand matchingCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(pendingAction.matches(matchingCommand));
    }

    @Test
    public void matches_deleteCommandDifferentIndex_returnsFalse() {
        Person person = new PersonBuilder().withName("Test User").build();
        DeletePendingAction pendingAction = new DeletePendingAction(person, INDEX_FIRST_PERSON);

        DeleteCommand differentCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        assertFalse(pendingAction.matches(differentCommand));
    }

    @Test
    public void matches_nonDeleteCommand_returnsFalse() {
        Person person = new PersonBuilder().withName("Test User").build();
        DeletePendingAction pendingAction = new DeletePendingAction(person, INDEX_FIRST_PERSON);

        // Test with various non-DeleteCommand types
        assertFalse(pendingAction.matches(new AddCommand(person)));
        assertFalse(pendingAction.matches(new ListCommand()));
        assertFalse(pendingAction.matches(new ClearCommand()));
        assertFalse(pendingAction.matches(new ExitCommand()));
        assertFalse(pendingAction.matches(new HelpCommand()));
    }

    @Test
    public void matches_nullCommand_returnsFalse() {
        Person person = new PersonBuilder().withName("Test User").build();
        DeletePendingAction pendingAction = new DeletePendingAction(person, INDEX_FIRST_PERSON);

        assertFalse(pendingAction.matches(null));
    }
}

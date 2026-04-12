package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.pending.DeletePendingAction;
import seedu.address.logic.pending.PendingAction;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same values including save flag -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // different shouldSaveAddressBook value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback").withSaveRequired()));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        // different shouldSaveAddressBook value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback").withSaveRequired().hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", shouldSaveAddressBook="
                + commandResult.shouldSaveAddressBook() + ", pendingAction=" + commandResult.getPendingAction() + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void withSaveRequired() {
        CommandResult commandResult = new CommandResult("feedback").withSaveRequired();

        assertTrue(commandResult.shouldSaveAddressBook());
        assertEquals(commandResult, commandResult.withSaveRequired());
    }

    @Test
    public void toStringMethod_withPendingAction() {
        Person person = new PersonBuilder().withName("Test User").build();
        PendingAction pendingAction = new DeletePendingAction(person, Index.fromOneBased(1));
        CommandResult commandResult = new CommandResult("confirmation message", pendingAction);

        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", shouldSaveAddressBook="
                + commandResult.shouldSaveAddressBook() + ", pendingAction=" + commandResult.getPendingAction() + "}";
        assertEquals(expected, commandResult.toString());
    }
}

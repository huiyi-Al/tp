package seedu.address.logic.pending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ClearPendingActionTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void matches_clearCommand_returnsTrue() {
        ClearPendingAction pendingAction = new ClearPendingAction();
        ClearCommand clearCommand = new ClearCommand();
        assertTrue(pendingAction.matches(clearCommand));
    }

    @Test
    public void matches_nonClearCommand_returnsFalse() {
        ClearPendingAction pendingAction = new ClearPendingAction();

        Person person = new PersonBuilder().build();
        assertFalse(pendingAction.matches(new AddCommand(person)));
        assertFalse(pendingAction.matches(new DeleteCommand(INDEX_FIRST_PERSON)));
        assertFalse(pendingAction.matches(new ListCommand()));
    }

    @Test
    public void matches_nullCommand_returnsFalse() {
        ClearPendingAction pendingAction = new ClearPendingAction();
        assertFalse(pendingAction.matches(null));
    }

    @Test
    public void complete_clearsAddressBook() throws Exception {
        ClearPendingAction pendingAction = new ClearPendingAction();

        // Verify address book has entries
        assertFalse(model.getAddressBook().getPersonList().isEmpty());

        // Execute complete
        pendingAction.complete(model);

        // Verify address book is empty
        assertTrue(model.getAddressBook().getPersonList().isEmpty());
    }
}

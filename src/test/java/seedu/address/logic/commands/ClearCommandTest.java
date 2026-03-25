package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.pending.ClearPendingAction;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_showsConfirmation() {
        ClearCommand clearCommand = new ClearCommand();
        CommandResult result = clearCommand.execute(model);

        assertTrue(result.hasPendingAction());
        assertEquals(ClearCommand.MESSAGE_CLEAR_CONFIRM, result.getFeedbackToUser());
        assertTrue(result.getPendingAction().isPresent());
    }

    @Test
    public void execute_confirmed_clearsAddressBook() throws Exception {
        ClearPendingAction pendingAction = new ClearPendingAction();

        // Verify address book has entries
        assertFalse(model.getAddressBook().getPersonList().isEmpty());

        // Execute the pending action
        CommandResult result = pendingAction.complete(model);

        // Verify address book is empty
        assertTrue(model.getAddressBook().getPersonList().isEmpty());
        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }
}

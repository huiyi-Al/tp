package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CopyAddrCommand}.
 */
public class CopyAddrCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // Enable test mode before each test
        CopyAddrCommand.setTestMode(true);
        CopyAddrCommand.setSimulateClipboardFailure(false);
    }

    @AfterEach
    public void tearDown() {
        // Reset test flags after each test
        CopyAddrCommand.resetTestFlags();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToCopy = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(INDEX_FIRST_PERSON);

        // These lines are now ALWAYS executed in test mode
        String expectedMessage = String.format(CopyAddrCommand.MESSAGE_COPY_ADDRESS_SUCCESS,
                personToCopy.getName().fullName, INDEX_FIRST_PERSON.getOneBased());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(copyAddrCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToCopy = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CopyAddrCommand.MESSAGE_COPY_ADDRESS_SUCCESS,
                personToCopy.getName().fullName, INDEX_FIRST_PERSON.getOneBased());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(copyAddrCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clipboardUnavailable_throwsCommandException() {
        // Simulate clipboard failure
        CopyAddrCommand.setSimulateClipboardFailure(true);

        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(copyAddrCommand, model, CopyAddrCommand.MESSAGE_CLIPBOARD_UNAVAILABLE);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(outOfBoundIndex);

        assertCommandFailure(copyAddrCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(outOfBoundIndex);

        assertCommandFailure(copyAddrCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        CopyAddrCommand copyCommand = new CopyAddrCommand(INDEX_FIRST_PERSON);

        // Test different type
        assertNotEquals(copyCommand, new Object());

        // Test null
        assertNotEquals(null, copyCommand);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        CopyAddrCommand copyCommand1 = new CopyAddrCommand(INDEX_FIRST_PERSON);
        CopyAddrCommand copyCommand2 = new CopyAddrCommand(INDEX_FIRST_PERSON);

        assertEquals(copyCommand1, copyCommand2);
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        CopyAddrCommand copyCommand1 = new CopyAddrCommand(INDEX_FIRST_PERSON);
        CopyAddrCommand copyCommand2 = new CopyAddrCommand(INDEX_SECOND_PERSON);

        assertNotEquals(copyCommand1, copyCommand2);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(targetIndex);
        String expected = CopyAddrCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, copyAddrCommand.toString());
    }
}

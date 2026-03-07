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

import java.awt.GraphicsEnvironment;

import org.junit.jupiter.api.Assumptions;
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

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Skip test if running in headless environment (CI)
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), "Skipping clipboard test in headless environment");
        Person personToCopy = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CopyAddrCommand.MESSAGE_COPY_ADDRESS_SUCCESS,
                personToCopy.getName().fullName, INDEX_FIRST_PERSON.getOneBased());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(copyAddrCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(outOfBoundIndex);

        assertCommandFailure(copyAddrCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Skip test if running in headless environment (CI)
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), "Skipping clipboard test in headless environment");
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
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(outOfBoundIndex);

        assertCommandFailure(copyAddrCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CopyAddrCommand copyFirstCommand = new CopyAddrCommand(INDEX_FIRST_PERSON);
        CopyAddrCommand copySecondCommand = new CopyAddrCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(copyFirstCommand, copyFirstCommand);

        // same values -> returns true
        CopyAddrCommand copyFirstCommandCopy = new CopyAddrCommand(INDEX_FIRST_PERSON);
        assertEquals(copyFirstCommand, copyFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, copyFirstCommand);

        // null -> returns false
        assertNotEquals(null, copyFirstCommand);

        // different person -> returns false
        assertNotEquals(copyFirstCommand, copySecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CopyAddrCommand copyAddrCommand = new CopyAddrCommand(targetIndex);
        String expected = CopyAddrCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, copyAddrCommand.toString());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteTagCommand}.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_tagExists_success() {
        Tag tagToDelete = new Tag("Plumbing");
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(tagToDelete);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete.tagName);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(tagToDelete);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagDoesNotExist_throwsCommandException() {
        Tag nonExistentTag = new Tag("NonExistentTag");
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(nonExistentTag);

        assertCommandFailure(deleteTagCommand, model,
                String.format(DeleteTagCommand.MESSAGE_TAG_NOT_FOUND, nonExistentTag.tagName));
    }

    @Test
    public void equals() {
        Tag firstTag = new Tag("first");
        Tag secondTag = new Tag("second");
        DeleteTagCommand deleteFirstCommand = new DeleteTagCommand(firstTag);
        DeleteTagCommand deleteSecondCommand = new DeleteTagCommand(secondTag);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTagCommand deleteFirstCommandCopy = new DeleteTagCommand(firstTag);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Tag targetTag = new Tag("plumbing");
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(targetTag);
        String expected = DeleteTagCommand.class.getCanonicalName() + "{targetTag=" + targetTag + "}";
        assertEquals(expected, deleteTagCommand.toString());
    }
}

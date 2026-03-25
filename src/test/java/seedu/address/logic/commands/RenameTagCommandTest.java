package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RenameTagCommand.
 */
public class RenameTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTag_success() {
        Tag oldTag = new Tag("AC-Service");
        Tag newTag = new Tag("Aircon-Repair");
        RenameTagCommand renameTagCommand = new RenameTagCommand(oldTag, newTag);

        String expectedMessage = String.format(RenameTagCommand.MESSAGE_SUCCESS, oldTag.tagName, newTag.tagName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.setTag(oldTag, newTag);

        assertCommandSuccess(renameTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentOldTag_failure() {
        Tag nonExistentTag = new Tag("NonExistent");
        Tag newTag = new Tag("NewName");
        RenameTagCommand renameTagCommand = new RenameTagCommand(nonExistentTag, newTag);

        assertCommandFailure(renameTagCommand, model,
                String.format(RenameTagCommand.MESSAGE_TAG_NOT_FOUND, "NonExistent"));
    }

    @Test
    public void execute_duplicateNewTag_failure() {
        Tag oldTag = new Tag("AC-Service");
        Tag existingTag = new Tag("Plumbing");
        RenameTagCommand renameTagCommand = new RenameTagCommand(oldTag, existingTag);

        assertCommandFailure(renameTagCommand, model,
                String.format(RenameTagCommand.MESSAGE_DUPLICATE_TAG, "Plumbing"));
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
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
import seedu.address.model.tag.Tag;

public class CopyEditCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        CopyEditCommand.setTestMode(true);
        CopyEditCommand.setSimulateClipboardFailure(false);
    }

    @AfterEach
    public void tearDown() {
        CopyEditCommand.resetTestFlags();
    }

    @Test
    public void generateEditCommand_alice_returnsCorrectFormat() {
        CopyEditCommand command = new CopyEditCommand(INDEX_FIRST_PERSON);
        String result = command.generateEditCommand(ALICE, INDEX_FIRST_PERSON.getOneBased());

        StringBuilder expected = new StringBuilder();
        expected.append(EditCommand.COMMAND_WORD).append(" ").append(INDEX_FIRST_PERSON.getOneBased()).append(" ");
        expected.append(PREFIX_NAME).append(ALICE.getName().fullName).append(" ");
        expected.append(PREFIX_PHONE).append(ALICE.getPhone().value).append(" ");
        expected.append(PREFIX_EMAIL).append(ALICE.getEmail().value).append(" ");
        expected.append(PREFIX_ADDRESS).append(ALICE.getAddress().value).append(" ");
        expected.append(PREFIX_NOTES).append(ALICE.getNotes().value).append(" ");

        for (Tag tag : ALICE.getTags()) {
            expected.append(PREFIX_TAG).append(tag.tagName).append(" ");
        }

        assertEquals(expected.toString().trim(), result);
    }

    @Test
    public void generateEditCommand_benson_returnsCorrectFormat() {
        Index index = Index.fromOneBased(2);
        CopyEditCommand command = new CopyEditCommand(index);
        String result = command.generateEditCommand(BENSON, index.getOneBased());

        StringBuilder expected = new StringBuilder();
        expected.append(EditCommand.COMMAND_WORD).append(" ").append(index.getOneBased()).append(" ");
        expected.append(PREFIX_NAME).append(BENSON.getName().fullName).append(" ");
        expected.append(PREFIX_PHONE).append(BENSON.getPhone().value).append(" ");
        expected.append(PREFIX_EMAIL).append(BENSON.getEmail().value).append(" ");
        expected.append(PREFIX_ADDRESS).append(BENSON.getAddress().value).append(" ");

        for (Tag tag : BENSON.getTags()) {
            expected.append(PREFIX_TAG).append(tag.tagName).append(" ");
        }

        assertEquals(expected.toString().trim(), result);
        assertFalse(result.contains(PREFIX_NOTES.getPrefix()));
    }

    @Test
    public void generateEditCommand_carl_returnsCorrectFormat() {
        Index index = Index.fromOneBased(3);
        CopyEditCommand command = new CopyEditCommand(index);
        String result = command.generateEditCommand(CARL, index.getOneBased());

        StringBuilder expected = new StringBuilder();
        expected.append(EditCommand.COMMAND_WORD).append(" ").append(index.getOneBased()).append(" ");
        expected.append(PREFIX_NAME).append(CARL.getName().fullName).append(" ");
        expected.append(PREFIX_PHONE).append(CARL.getPhone().value).append(" ");
        expected.append(PREFIX_EMAIL).append(CARL.getEmail().value).append(" ");
        expected.append(PREFIX_ADDRESS).append(CARL.getAddress().value).append(" ");

        if (!CARL.getNotes().value.isEmpty()) {
            expected.append(PREFIX_NOTES).append(CARL.getNotes().value).append(" ");
        }

        for (Tag tag : CARL.getTags()) {
            expected.append(PREFIX_TAG).append(tag.tagName).append(" ");
        }

        assertEquals(expected.toString().trim(), result);
    }

    @Test
    public void generateEditCommand_elle_returnsCorrectFormat() {
        int elleIndex = model.getFilteredPersonList().indexOf(ELLE) + 1;
        Index index = Index.fromOneBased(elleIndex);
        CopyEditCommand command = new CopyEditCommand(index);
        String result = command.generateEditCommand(ELLE, index.getOneBased());

        StringBuilder expected = new StringBuilder();
        expected.append(EditCommand.COMMAND_WORD).append(" ").append(index.getOneBased()).append(" ");
        expected.append(PREFIX_NAME).append(ELLE.getName().fullName).append(" ");
        expected.append(PREFIX_PHONE).append(ELLE.getPhone().value).append(" ");
        expected.append(PREFIX_EMAIL).append(ELLE.getEmail().value).append(" ");
        expected.append(PREFIX_ADDRESS).append(ELLE.getAddress().value).append(" ");

        if (!ELLE.getNotes().value.isEmpty()) {
            expected.append(PREFIX_NOTES).append(ELLE.getNotes().value).append(" ");
        }

        for (Tag tag : ELLE.getTags()) {
            expected.append(PREFIX_TAG).append(tag.tagName).append(" ");
        }

        assertEquals(expected.toString().trim(), result);
    }

    @Test
    public void generateEditCommand_personWithEmptyNotes_omitsNotesField() {
        Person personWithEmptyNotes = model.getFilteredPersonList().stream()
                .filter(p -> p.getNotes().value.isEmpty())
                .findFirst()
                .orElseThrow();

        int index = model.getFilteredPersonList().indexOf(personWithEmptyNotes) + 1;
        CopyEditCommand command = new CopyEditCommand(Index.fromOneBased(index));
        String result = command.generateEditCommand(personWithEmptyNotes, index);

        assertFalse(result.contains(PREFIX_NOTES.getPrefix()));
    }

    @Test
    public void generateEditCommand_personWithMultipleTags_returnsAllTags() {
        Person personWithMultipleTags = model.getFilteredPersonList().stream()
                .filter(p -> p.getTags().size() > 1)
                .findFirst()
                .orElseThrow();

        int index = model.getFilteredPersonList().indexOf(personWithMultipleTags) + 1;
        CopyEditCommand command = new CopyEditCommand(Index.fromOneBased(index));
        String result = command.generateEditCommand(personWithMultipleTags, index);

        for (Tag tag : personWithMultipleTags.getTags()) {
            assertTrue(result.contains(PREFIX_TAG + tag.tagName));
        }
    }

    @Test
    public void execute_clipboardUnavailable_throwsCommandException() {
        CopyEditCommand.setSimulateClipboardFailure(true);
        CopyEditCommand copyEditCommand = new CopyEditCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(copyEditCommand, model, CopyEditCommand.MESSAGE_CLIPBOARD_UNAVAILABLE);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CopyEditCommand copyEditCommand = new CopyEditCommand(outOfBoundIndex);
        assertCommandFailure(copyEditCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        CopyEditCommand copyEditCommand = new CopyEditCommand(outOfBoundIndex);
        assertCommandFailure(copyEditCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_testMode_success() {
        Person personToCopy = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CopyEditCommand copyEditCommand = new CopyEditCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CopyEditCommand.MESSAGE_COPY_SUCCESS,
                personToCopy.getName().fullName, INDEX_FIRST_PERSON.getOneBased());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(copyEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_testModeSimulateClipboardFailure_throwsCommandException() {
        CopyEditCommand.setTestMode(true);
        CopyEditCommand.setSimulateClipboardFailure(true);

        CopyEditCommand copyEditCommand = new CopyEditCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(copyEditCommand, model, CopyEditCommand.MESSAGE_CLIPBOARD_UNAVAILABLE);
    }

    @Test
    public void execute_productionMode_success() {
        CopyEditCommand.setTestMode(false);
        CopyEditCommand.setSimulateClipboardFailure(false);

        Person personToCopy = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CopyEditCommand copyEditCommand = new CopyEditCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CopyEditCommand.MESSAGE_COPY_SUCCESS,
                personToCopy.getName().fullName, INDEX_FIRST_PERSON.getOneBased());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(copyEditCommand, model, expectedMessage, expectedModel);

        // Re-enable test mode for other tests
        CopyEditCommand.setTestMode(true);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        CopyEditCommand copyCommand = new CopyEditCommand(INDEX_FIRST_PERSON);
        assertFalse(copyCommand.equals(new Object()));
        assertFalse(copyCommand.equals(null));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        CopyEditCommand copyCommand1 = new CopyEditCommand(INDEX_FIRST_PERSON);
        CopyEditCommand copyCommand2 = new CopyEditCommand(INDEX_FIRST_PERSON);
        assertTrue(copyCommand1.equals(copyCommand2));
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        CopyEditCommand copyCommand1 = new CopyEditCommand(INDEX_FIRST_PERSON);
        CopyEditCommand copyCommand2 = new CopyEditCommand(INDEX_SECOND_PERSON);
        assertFalse(copyCommand1.equals(copyCommand2));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CopyEditCommand copyEditCommand = new CopyEditCommand(targetIndex);
        String expected = CopyEditCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, copyEditCommand.toString());
    }
}

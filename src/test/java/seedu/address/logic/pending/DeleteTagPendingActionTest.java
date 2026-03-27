package seedu.address.logic.pending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AC_SERVICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PLUMBING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.tag.Tag;

public class DeleteTagPendingActionTest {

    private Tag tag;
    private DeleteTagPendingAction pendingAction;

    @BeforeEach
    public void setUp() {
        tag = new Tag(VALID_TAG_PLUMBING);
        pendingAction = new DeleteTagPendingAction(tag);
    }

    @Test
    public void matches_sameCommand_returnsTrue() {
        DeleteTagCommand sameCommand = new DeleteTagCommand(tag);
        assertTrue(pendingAction.matches(sameCommand));
    }

    @Test
    public void matches_differentTag_returnsFalse() {
        DeleteTagCommand differentCommand = new DeleteTagCommand(new Tag(VALID_TAG_AC_SERVICE));
        assertFalse(pendingAction.matches(differentCommand));
    }

    @Test
    public void matches_nonDeleteTagCommand_returnsFalse() {
        assertFalse(pendingAction.matches(new AddCommand(ALICE)));
        assertFalse(pendingAction.matches(new DeleteCommand(INDEX_FIRST_PERSON)));
        assertFalse(pendingAction.matches(new ClearCommand()));
        assertFalse(pendingAction.matches(new ListCommand()));
    }

    @Test
    public void matches_nullCommand_returnsFalse() {
        assertFalse(pendingAction.matches(null));
    }
}

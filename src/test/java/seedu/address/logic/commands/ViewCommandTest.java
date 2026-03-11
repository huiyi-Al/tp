package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewCommandTest {

   private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

   @Test
   public void execute_invalidIndex_throwsCommandException() {
       Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
       ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

       assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
   }

   @Test
    public void equal() {
       ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_PERSON);
       ViewCommand viewSecondCommand = new ViewCommand(INDEX_SECOND_PERSON);

       // same object -> returns true
       assertTrue(viewFirstCommand.equals(viewFirstCommand));

       // same values -> returns true
       ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_PERSON);
       assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

       // different types -> returns false
       assertFalse(viewFirstCommand.equals(1));

       // null -> returns false
       assertFalse(viewFirstCommand.equals(null));

       // different person -> returns false
       assertFalse(viewFirstCommand.equals(viewSecondCommand));

   }

   @Test
    public void toStringMethod() {
       Index targetIndex = Index.fromOneBased(1);
       ViewCommand viewCommand = new ViewCommand(targetIndex);
       String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
       assertEquals(expected, viewCommand.toString());
   }


}

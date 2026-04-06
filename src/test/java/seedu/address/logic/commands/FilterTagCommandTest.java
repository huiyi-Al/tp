package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.TagsMatchAllKeywordsPredicate;

public class FilterTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagsMatchAllKeywordsPredicate firstPredicate =
                new TagsMatchAllKeywordsPredicate(Collections.singletonList("AC-Service"));
        TagsMatchAllKeywordsPredicate secondPredicate =
                new TagsMatchAllKeywordsPredicate(Collections.singletonList("Plumbing"));

        FilterTagCommand filterFirstCommand = new FilterTagCommand(firstPredicate);
        FilterTagCommand filterSecondCommand = new FilterTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterTagCommand filterFirstCommandCopy = new FilterTagCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_multipleTags_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        TagsMatchAllKeywordsPredicate predicate = new TagsMatchAllKeywordsPredicate(Arrays
                .asList("AC-Service", "Plumbing"));
        FilterTagCommand command = new FilterTagCommand(predicate);
        expectedModel.singlePredicateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_tagDoesNotExist_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        // Filtering for a tag that no one has
        TagsMatchAllKeywordsPredicate predicate =
                new TagsMatchAllKeywordsPredicate(Collections.singletonList("NonExistentTag"));
        FilterTagCommand command = new FilterTagCommand(predicate);

        expectedModel.singlePredicateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_selectedInitiallyNull_selectedNoChange() {
        model.resetPredicatesFilteredPersonList();
        model.setSelectedPerson(null);

        TagsMatchAllKeywordsPredicate predicate =
                new TagsMatchAllKeywordsPredicate(Collections.singletonList("AC-Service"));
        FilterTagCommand command = new FilterTagCommand(predicate);

        command.execute(model);

        assertNull(model.getSelectedPerson().getValue());
    }

    @Test
    public void execute_selectedInitiallyPersonNotRemoved_selectedNotChanged() {
        model.resetPredicatesFilteredPersonList();
        model.setSelectedPerson(BENSON);

        TagsMatchAllKeywordsPredicate predicate =
                new TagsMatchAllKeywordsPredicate(Collections.singletonList("AC-Service"));
        FilterTagCommand command = new FilterTagCommand(predicate);

        command.execute(model);

        assertEquals(BENSON, model.getSelectedPerson().getValue());
    }

    @Test
    public void execute_selectedInitiallyPersonThenRemoved_selectedChangeToNull() {
        model.resetPredicatesFilteredPersonList();
        model.setSelectedPerson(BENSON);

        TagsMatchAllKeywordsPredicate predicate =
                new TagsMatchAllKeywordsPredicate(Collections.singletonList("NonExistentTag"));
        FilterTagCommand command = new FilterTagCommand(predicate);

        command.execute(model);

        assertNull(model.getSelectedPerson().getValue());
    }

    @Test
    public void toStringMethod() {
        TagsMatchAllKeywordsPredicate predicate = new TagsMatchAllKeywordsPredicate(Arrays.asList("AC-Service"));
        FilterTagCommand filterCommand = new FilterTagCommand(predicate);
        String expected = FilterTagCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }
}

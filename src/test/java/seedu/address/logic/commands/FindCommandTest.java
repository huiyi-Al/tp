package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.SearchPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final ArgumentMultimap argMapEmpty;
    private final SearchPredicate searchPredicateEmpty;

    private final ArgumentMultimap argMapNameOnly;
    private final SearchPredicate searchPredicateNameOnly;

    private final ArgumentMultimap argMapNameOnlyUnevenCases;
    private final SearchPredicate searchPredicateNameOnlyUnevenCases;

    private final ArgumentMultimap argMapPhoneOnly;
    private final SearchPredicate searchPredicatePhoneOnly;

    private final ArgumentMultimap argMapAllPresent;
    private final SearchPredicate searchPredicateAllPresent;

    public FindCommandTest() {
        argMapEmpty = new ArgumentMultimap();
        searchPredicateEmpty = new SearchPredicate(argMapEmpty);

        argMapNameOnly = new ArgumentMultimap();
        argMapNameOnly.put(PREFIX_NAME, String.join(" ", "Kurz", "Elle", "Kunz"));
        searchPredicateNameOnly = new SearchPredicate(argMapNameOnly);

        argMapNameOnlyUnevenCases = new ArgumentMultimap();
        argMapNameOnlyUnevenCases.put(PREFIX_NAME, String.join(" ", "KURZ", "ElLe", "kunz"));
        searchPredicateNameOnlyUnevenCases = new SearchPredicate(argMapNameOnlyUnevenCases);

        argMapPhoneOnly = new ArgumentMultimap();
        argMapPhoneOnly.put(PREFIX_PHONE, String.join(" ", "535", "442"));
        searchPredicatePhoneOnly = new SearchPredicate(argMapPhoneOnly);

        argMapAllPresent = new ArgumentMultimap();
        argMapAllPresent.put(PREFIX_NAME, String.join(" ", "Kurz", "Elle", "Kunz"));
        argMapAllPresent.put(PREFIX_PHONE, String.join(" ", "535", "442"));
        searchPredicateAllPresent = new SearchPredicate(argMapAllPresent);
    }

    @Test
    public void equals() {
        FindCommand findCommandEmpty = new FindCommand(searchPredicateEmpty);
        FindCommand findCommandNameOnly = new FindCommand(searchPredicateNameOnly);

        // same object -> returns true
        assertTrue(findCommandEmpty.equals(findCommandEmpty));

        // same values -> returns true
        FindCommand findCommandEmptyCopy = new FindCommand(searchPredicateEmpty);
        assertTrue(findCommandEmpty.equals(findCommandEmptyCopy));

        // different types -> returns false
        assertFalse(findCommandEmpty.equals(1));

        // null -> returns false
        assertFalse(findCommandEmpty.equals(null));

        // different search predicate -> returns false
        assertFalse(findCommandEmpty.equals(findCommandNameOnly));
    }

    @Test
    public void execute_zeroInformation_noPersonFound() {
        FindCommand command = new FindCommand(searchPredicateEmpty);
        expectedModel.updateFilteredPersonList(searchPredicateEmpty);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleSubNames_multiplePersonsFound() {
        FindCommand command = new FindCommand(searchPredicateNameOnly);
        expectedModel.updateFilteredPersonList(searchPredicateNameOnly);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleSubNamesDifferentCases_multiplePersonsFound() {
        FindCommand command = new FindCommand(searchPredicateNameOnlyUnevenCases);
        expectedModel.updateFilteredPersonList(searchPredicateNameOnlyUnevenCases);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleSubNumbers_multiplePersonsFound() {
        FindCommand command = new FindCommand(searchPredicatePhoneOnly);
        expectedModel.updateFilteredPersonList(searchPredicatePhoneOnly);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleSubNamesAndSubNumbers_multiplePersonsFound() {
        FindCommand command = new FindCommand(searchPredicateAllPresent);
        expectedModel.updateFilteredPersonList(searchPredicateAllPresent);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        FindCommand findCommand = new FindCommand(searchPredicateAllPresent);
        String expected = FindCommand.class.getCanonicalName() + "{searchPredicate=" + searchPredicateAllPresent + "}";
        assertEquals(expected, findCommand.toString());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.SearchPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 *
 * <ul>
 *   <li>Equality between same objects, returns true.</li>
 *   <li>Equality between objects of same values, returns true.</li>
 *   <li>Equality between objects of different predicates, returns false.</li>
 *   <li>Equality between object and a different type, returns false.</li>
 *   <li>Equality between object and null, returns false.</li>
 *   <li>Executing with an empty predicate, finds no persons.</li>
 *   <li>Executing with multiple name tokens (same case), finds all matching persons.</li>
 *   <li>Executing with multiple name tokens (mixed case), finds all matching persons.</li>
 *   <li>Executing with multiple phone tokens, finds all matching persons.</li>
 *   <li>Executing with multiple email address tokens, finds all matching persons.</li>
 *   <li>Executing with multiple physical address tokens, finds all matching persons.</li>
 *   <li>Executing with all types of tokens, finds union of all matching persons.</li>
 *   <li>String representation matches expected format.</li>
 * </ul>
 */
public class FindCommandTest {

    private static final String TEST_NAME_QUERY = String.join(" ", "KUrZ", "ElLe", "kuNz");
    private static final String TEST_PHONE_QUERY = String.join(" ", "535", "442");
    private static final String TEST_EMAIL_QUERY = String.join(" ", "r@e", "a@e");
    private static final String TEST_ADDRESS_QUERY = String.join(" ", "Jurong", "clEmenti");

    private static final Model MODEL = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private static final Model EXPECTED_MODEL = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private static final ArgumentMultimap TEST_ARGMAP_EMPTY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_EMPTY;
    private static final FindCommand TEST_FIND_COMMAND_EMPTY;

    private static final ArgumentMultimap TEST_ARGMAP_NAME_ONLY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_NAME_ONLY;
    private static final FindCommand TEST_FIND_COMMAND_NAME_ONLY;

    private static final ArgumentMultimap TEST_ARGMAP_PHONE_ONLY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_PHONE_ONLY;
    private static final FindCommand TEST_FIND_COMMAND_PHONE_ONLY;

    private static final ArgumentMultimap TEST_ARGMAP_EMAIL_ONLY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_EMAIL_ONLY;
    private static final FindCommand TEST_FIND_COMMAND_EMAIL_ONLY;

    private static final ArgumentMultimap TEST_ARGMAP_ADDRESS_ONLY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_ADDRESS_ONLY;
    private static final FindCommand TEST_FIND_COMMAND_ADDRESS_ONLY;

    private static final ArgumentMultimap TEST_ARGMAP_ALL_PRESENT;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_ALL_PRESENT;
    private static final FindCommand TEST_FIND_COMMAND_ALL_PRESENT;

    static {
        TEST_ARGMAP_EMPTY = new ArgumentMultimap();
        TEST_SEARCH_PREDICATE_EMPTY = new SearchPredicate(TEST_ARGMAP_EMPTY);
        TEST_FIND_COMMAND_EMPTY = new FindCommand(TEST_SEARCH_PREDICATE_EMPTY);

        TEST_ARGMAP_NAME_ONLY = new ArgumentMultimap();
        TEST_ARGMAP_NAME_ONLY.put(PREFIX_NAME, TEST_NAME_QUERY);
        TEST_SEARCH_PREDICATE_NAME_ONLY = new SearchPredicate(TEST_ARGMAP_NAME_ONLY);
        TEST_FIND_COMMAND_NAME_ONLY = new FindCommand(TEST_SEARCH_PREDICATE_NAME_ONLY);

        TEST_ARGMAP_PHONE_ONLY = new ArgumentMultimap();
        TEST_ARGMAP_PHONE_ONLY.put(PREFIX_PHONE, TEST_PHONE_QUERY);
        TEST_SEARCH_PREDICATE_PHONE_ONLY = new SearchPredicate(TEST_ARGMAP_PHONE_ONLY);
        TEST_FIND_COMMAND_PHONE_ONLY = new FindCommand(TEST_SEARCH_PREDICATE_PHONE_ONLY);

        TEST_ARGMAP_EMAIL_ONLY = new ArgumentMultimap();
        TEST_ARGMAP_EMAIL_ONLY.put(PREFIX_EMAIL, TEST_EMAIL_QUERY);
        TEST_SEARCH_PREDICATE_EMAIL_ONLY = new SearchPredicate(TEST_ARGMAP_EMAIL_ONLY);
        TEST_FIND_COMMAND_EMAIL_ONLY = new FindCommand(TEST_SEARCH_PREDICATE_EMAIL_ONLY);

        TEST_ARGMAP_ADDRESS_ONLY = new ArgumentMultimap();
        TEST_ARGMAP_ADDRESS_ONLY.put(PREFIX_ADDRESS, TEST_ADDRESS_QUERY);
        TEST_SEARCH_PREDICATE_ADDRESS_ONLY = new SearchPredicate(TEST_ARGMAP_ADDRESS_ONLY);
        TEST_FIND_COMMAND_ADDRESS_ONLY = new FindCommand(TEST_SEARCH_PREDICATE_ADDRESS_ONLY);

        TEST_ARGMAP_ALL_PRESENT = new ArgumentMultimap();
        TEST_ARGMAP_ALL_PRESENT.put(PREFIX_NAME, TEST_NAME_QUERY);
        TEST_ARGMAP_ALL_PRESENT.put(PREFIX_PHONE, TEST_PHONE_QUERY);
        TEST_ARGMAP_ALL_PRESENT.put(PREFIX_EMAIL, TEST_EMAIL_QUERY);
        TEST_ARGMAP_ALL_PRESENT.put(PREFIX_ADDRESS, TEST_ADDRESS_QUERY);
        TEST_SEARCH_PREDICATE_ALL_PRESENT = new SearchPredicate(TEST_ARGMAP_ALL_PRESENT);
        TEST_FIND_COMMAND_ALL_PRESENT = new FindCommand(TEST_SEARCH_PREDICATE_ALL_PRESENT);
    }

    public FindCommandTest() {
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(TEST_FIND_COMMAND_ALL_PRESENT.equals(TEST_FIND_COMMAND_ALL_PRESENT));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        FindCommand findCommandAllPresentCopy = new FindCommand(TEST_SEARCH_PREDICATE_ALL_PRESENT);
        assertTrue(TEST_FIND_COMMAND_ALL_PRESENT.equals(findCommandAllPresentCopy));
    }

    @Test
    public void equals_differentPredicate_returnsFalse() {
        assertFalse(TEST_FIND_COMMAND_ALL_PRESENT.equals(TEST_FIND_COMMAND_NAME_ONLY));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(TEST_FIND_COMMAND_ALL_PRESENT.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(TEST_FIND_COMMAND_ALL_PRESENT.equals(null));
    }

    @Test
    public void execute_selectedInitiallyNull_selectedNoChange() {
        MODEL.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        MODEL.setSelectedPerson(null);
        TEST_FIND_COMMAND_NAME_ONLY.execute(MODEL);

        assertNull(MODEL.getSelectedPerson().getValue());
    }

    @Test
    public void execute_selectedInitiallyPersonNotRemoved_selectedNotChanged() {
        MODEL.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        MODEL.setSelectedPerson(CARL);
        TEST_FIND_COMMAND_NAME_ONLY.execute(MODEL);

        assertEquals(CARL, MODEL.getSelectedPerson().getValue());
    }

    @Test
    public void execute_selectedInitiallyPersonThenRemoved_selectedChangeToNull() {
        MODEL.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        MODEL.setSelectedPerson(DANIEL);
        TEST_FIND_COMMAND_NAME_ONLY.execute(MODEL);

        assertNull(MODEL.getSelectedPerson().getValue());
    }

    @Test
    public void execute_emptyPredicate_noPersonFound() {
        EXPECTED_MODEL.updateFilteredPersonList(TEST_SEARCH_PREDICATE_EMPTY);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        assertCommandSuccess(TEST_FIND_COMMAND_EMPTY, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(Collections.emptyList(), MODEL.getFilteredPersonList());
    }

    @Test
    public void execute_multipleSubNames_multiplePersonsFound() {
        EXPECTED_MODEL.updateFilteredPersonList(TEST_SEARCH_PREDICATE_NAME_ONLY);
        Set<Person> expectedFound = new HashSet<>(Arrays.asList(CARL, ELLE, FIONA));

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedFound.size());
        assertCommandSuccess(TEST_FIND_COMMAND_NAME_ONLY, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(expectedFound, new HashSet<>(MODEL.getFilteredPersonList()));
    }

    @Test
    public void execute_multipleSubPhones_multiplePersonsFound() {
        EXPECTED_MODEL.updateFilteredPersonList(TEST_SEARCH_PREDICATE_PHONE_ONLY);
        Set<Person> expectedFound = new HashSet<>(Arrays.asList(CARL, GEORGE));

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedFound.size());
        assertCommandSuccess(TEST_FIND_COMMAND_PHONE_ONLY, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(expectedFound, new HashSet<>(MODEL.getFilteredPersonList()));
    }

    @Test
    public void execute_multipleSubEmailAddresses_multiplePersonsFound() {
        EXPECTED_MODEL.updateFilteredPersonList(TEST_SEARCH_PREDICATE_EMAIL_ONLY);
        Set<Person> expectedFound = new HashSet<>(Arrays.asList(DANIEL, ELLE, FIONA, GEORGE));

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedFound.size());
        assertCommandSuccess(TEST_FIND_COMMAND_EMAIL_ONLY, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(expectedFound, new HashSet<>(MODEL.getFilteredPersonList()));
    }

    @Test
    public void execute_multipleSubPhysicalAddresses_multiplePersonsFound() {
        EXPECTED_MODEL.updateFilteredPersonList(TEST_SEARCH_PREDICATE_ADDRESS_ONLY);
        Set<Person> expectedFound = new HashSet<>(Arrays.asList(ALICE, BENSON));

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedFound.size());
        assertCommandSuccess(TEST_FIND_COMMAND_ADDRESS_ONLY, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(expectedFound, new HashSet<>(MODEL.getFilteredPersonList()));
    }

    @Test
    public void execute_allPresent_multiplePersonsUnion() {
        EXPECTED_MODEL.updateFilteredPersonList(TEST_SEARCH_PREDICATE_ALL_PRESENT);
        Set<Person> expectedFound = new HashSet<>(Arrays.asList(ALICE, BENSON, CARL, GEORGE, ELLE, FIONA, DANIEL));

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedFound.size());
        assertCommandSuccess(TEST_FIND_COMMAND_ALL_PRESENT, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(expectedFound, new HashSet<>(MODEL.getFilteredPersonList()));
    }

    @Test
    public void toString_allPresentPredicate_returnsExpectedString() {
        String expected = MessageFormat.format("{0}'{searchPredicate='{1}'}'",
                FindCommand.class.getCanonicalName(), TEST_SEARCH_PREDICATE_ALL_PRESENT);
        assertEquals(expected, TEST_FIND_COMMAND_ALL_PRESENT.toString());
    }
}

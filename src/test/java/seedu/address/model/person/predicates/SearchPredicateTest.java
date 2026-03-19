package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SearchPredicateTest {
    private final ArgumentMultimap argMapEmpty;
    private final SearchPredicate searchPredicateEmpty;

    private final ArgumentMultimap argMapNameOnly;
    private final SearchPredicate searchPredicateNameOnly;

    private final ArgumentMultimap argMapPhoneOnly;
    private final SearchPredicate searchPredicatePhoneOnly;

    private final ArgumentMultimap argMapAllPresent;
    private final SearchPredicate searchPredicateAllPresent;

    private final Person person1;
    private final Person person2;
    private final Person person3;
    private final Person person4;

    public SearchPredicateTest() {
        argMapEmpty = new ArgumentMultimap();
        searchPredicateEmpty = new SearchPredicate(argMapEmpty);

        argMapNameOnly = new ArgumentMultimap();
        argMapNameOnly.put(PREFIX_NAME, "al er");
        searchPredicateNameOnly = new SearchPredicate(argMapNameOnly);

        argMapPhoneOnly = new ArgumentMultimap();
        argMapPhoneOnly.put(PREFIX_PHONE, "123 456");
        searchPredicatePhoneOnly = new SearchPredicate(argMapPhoneOnly);

        argMapAllPresent = new ArgumentMultimap();
        argMapAllPresent.put(PREFIX_NAME, "al er");
        argMapAllPresent.put(PREFIX_PHONE, "123 456");
        searchPredicateAllPresent = new SearchPredicate(argMapAllPresent);

        person1 = new PersonBuilder().withName("Alan").withPhone("92355671").build();
        person2 = new PersonBuilder().withName("Cassie").withPhone("91237452").build();
        person3 = new PersonBuilder().withName("Bertha").withPhone("84567342").build();
        person4 = new PersonBuilder().withName("Daniel").withPhone("98765432").build();
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(searchPredicateEmpty.equals(searchPredicateEmpty));
        assertTrue(searchPredicateNameOnly.equals(searchPredicateNameOnly));
        assertTrue(searchPredicatePhoneOnly.equals(searchPredicatePhoneOnly));
        assertTrue(searchPredicateAllPresent.equals(searchPredicateAllPresent));

        // same values -> returns true
        ArgumentMultimap argMapAllPresentCopy = new ArgumentMultimap();
        argMapAllPresentCopy.put(PREFIX_NAME, "al er");
        argMapAllPresentCopy.put(PREFIX_PHONE, "123 456");
        SearchPredicate searchPredicateAllPresentCopy = new SearchPredicate(argMapAllPresentCopy);
        assertTrue(searchPredicateAllPresent.equals(searchPredicateAllPresentCopy));

        // different person -> returns false
        assertFalse(searchPredicateEmpty.equals(searchPredicatePhoneOnly));

        // different types -> returns false
        assertFalse(searchPredicateEmpty.equals(123));

        // null -> returns false
        assertFalse(searchPredicateEmpty.equals(null));

    }

    @Test
    public void test_searchingWithConditions_returnsTrue() {
        // Phone does not match, name does
        assertTrue(searchPredicateNameOnly.test(person1));
        assertTrue(searchPredicateAllPresent.test(person1));

        // Name does not match, phone does
        assertTrue(searchPredicatePhoneOnly.test(person2));
        assertTrue(searchPredicateAllPresent.test(person2));

        // All match
        assertTrue(searchPredicateNameOnly.test(person3));
        assertTrue(searchPredicatePhoneOnly.test(person3));
        assertTrue(searchPredicateAllPresent.test(person3));
    }

    @Test
    public void test_searchingWithConditions_returnsFalse() {
        // None match on name only
        assertFalse(searchPredicateNameOnly.test(person2));

        // None match on phone only
        assertFalse(searchPredicatePhoneOnly.test(person1));

        // None match on any field
        assertFalse(searchPredicateAllPresent.test(person4));
    }

    @Test
    public void toStringMethod() {
        assertTrue(argMapEmpty.getValue(PREFIX_NAME).isEmpty());
        assertTrue(argMapEmpty.getValue(PREFIX_PHONE).isEmpty());
        assertTrue(argMapAllPresent.getValue(PREFIX_NAME).isPresent());
        assertTrue(argMapAllPresent.getValue(PREFIX_PHONE).isPresent());

        String expectedEmpty = MessageFormat.format(
                "{0}, {1}",
                FullNamePredicate.class.getCanonicalName() + "{subNames="
                        + new ArrayList<>() + "}",
                PhoneNumberPredicate.class.getCanonicalName() + "{subNumbers="
                        + new ArrayList<>() + "}"
        );
        String expectedAllPresent = MessageFormat.format(
                "{0}, {1}",
                FullNamePredicate.class.getCanonicalName() + "{subNames="
                        + Arrays.asList(argMapAllPresent.getValue(PREFIX_NAME).get().split("\\s+")) + "}",
                PhoneNumberPredicate.class.getCanonicalName() + "{subNumbers="
                        + Arrays.asList(argMapAllPresent.getValue(PREFIX_PHONE).get().split("\\s+")) + "}"
        );

        assertEquals(expectedEmpty, searchPredicateEmpty.toString());
        assertEquals(expectedAllPresent, searchPredicateAllPresent.toString());
    }
}

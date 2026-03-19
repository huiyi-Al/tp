package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SearchPredicateTest {
    private final Map<Prefix, List<String>> argMapEmpty;
    private final SearchPredicate searchPredicateEmpty;

    private final Map<Prefix, List<String>> argMapNameOnly;
    private final SearchPredicate searchPredicateNameOnly;

    private final Map<Prefix, List<String>> argMapPhoneOnly;
    private final SearchPredicate searchPredicatePhoneOnly;

    private final Map<Prefix, List<String>> argMapAllPresent;
    private final SearchPredicate searchPredicateAllPresent;

    private final Person person1;
    private final Person person2;
    private final Person person3;
    private final Person person4;

    public SearchPredicateTest() {
        argMapEmpty = new HashMap<>();
        searchPredicateEmpty = new SearchPredicate(argMapEmpty);

        argMapNameOnly = new HashMap<>();
        argMapNameOnly.put(PREFIX_NAME, Arrays.asList("al", "er"));
        searchPredicateNameOnly = new SearchPredicate(argMapNameOnly);

        argMapPhoneOnly = new HashMap<>();
        argMapPhoneOnly.put(PREFIX_PHONE, Arrays.asList("123", "456"));
        searchPredicatePhoneOnly = new SearchPredicate(argMapPhoneOnly);

        argMapAllPresent = new HashMap<>();
        argMapAllPresent.put(PREFIX_NAME, Arrays.asList("al", "er"));
        argMapAllPresent.put(PREFIX_PHONE, Arrays.asList("123", "456"));
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
        Map<Prefix, List<String>> argMapAllPresentCopy = new HashMap<>();
        argMapAllPresentCopy.put(PREFIX_NAME, Arrays.asList("al", "er"));
        argMapAllPresentCopy.put(PREFIX_PHONE, Arrays.asList("123", "456"));
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
        String expectedEmpty = MessageFormat.format(
                "{0}, {1}",
                "NA",
                "NA"
        );
        String expectedNameOnly = MessageFormat.format(
                "{0}, {1}",
                FullNamePredicate.class.getCanonicalName() + "{subNames="
                        + argMapNameOnly.get(PREFIX_NAME) + "}",
                "NA"
        );
        String expectedPhoneOnly = MessageFormat.format(
                "{0}, {1}",
                "NA",
                PhoneNumberPredicate.class.getCanonicalName() + "{subNumbers="
                        + argMapPhoneOnly.get(PREFIX_PHONE) + "}"
        );
        String expectedAllPresent = MessageFormat.format(
                "{0}, {1}",
                FullNamePredicate.class.getCanonicalName() + "{subNames="
                        + argMapNameOnly.get(PREFIX_NAME) + "}",
                PhoneNumberPredicate.class.getCanonicalName() + "{subNumbers="
                        + argMapPhoneOnly.get(PREFIX_PHONE) + "}"
        );

        assertEquals(expectedEmpty, searchPredicateEmpty.toString());
        assertEquals(expectedNameOnly, searchPredicateNameOnly.toString());
        assertEquals(expectedPhoneOnly, searchPredicatePhoneOnly.toString());
        assertEquals(expectedAllPresent, searchPredicateAllPresent.toString());
    }
}

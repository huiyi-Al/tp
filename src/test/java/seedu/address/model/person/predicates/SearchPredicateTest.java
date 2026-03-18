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
import seedu.address.model.person.predicate.NameContainsSubstringsPredicate;
import seedu.address.model.person.predicate.PhoneNumberPredicate;
import seedu.address.model.person.predicate.SearchPredicate;
import seedu.address.testutil.PersonBuilder;

public class SearchPredicateTest {
    Map<Prefix, List<String>> argMapEmpty;
    SearchPredicate searchPredicateEmpty;

    Map<Prefix, List<String>> argMapNameOnly;
    SearchPredicate searchPredicateNameOnly;

    Map<Prefix, List<String>> argMapPhoneOnly;
    SearchPredicate searchPredicatePhoneOnly;

    Map<Prefix, List<String>> argMapAllPresent;
    SearchPredicate searchPredicateAllPresent;

    Person person1;
    Person person2;
    Person person3;

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
        person3 = new PersonBuilder().withName("Bertha").withPhone("81567342").build();
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
        argMapAllPresent.put(PREFIX_NAME, Arrays.asList("al", "er"));
        argMapAllPresent.put(PREFIX_PHONE, Arrays.asList("123", "456"));
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
        assertFalse(searchPredicatePhoneOnly.test(person3));

        // None match on any field
        assertFalse(searchPredicateAllPresent.test(person1));
    }

    @Test
    public void toStringMethod() {
        String expectedEmpty = MessageFormat.format(
                "{0}, {1}\n",
                "NA",
                "NA"
        );
        String expectedNameOnly = MessageFormat.format(
                "{0}, {1}\n",
                NameContainsSubstringsPredicate.class.getCanonicalName() + "{substrings=" +
                        argMapNameOnly.get(PREFIX_NAME) + "}",
                "NA"
        );
        String expectedPhoneOnly = MessageFormat.format(
                "{0}, {1}\n",
                "NA",
                PhoneNumberPredicate.class.getCanonicalName() + "{subnumbers=" +
                        argMapPhoneOnly.get(PREFIX_PHONE) + "}"
        );
        String expectedAllPresent = MessageFormat.format(
                "{0}, {1}\n",
                NameContainsSubstringsPredicate.class.getCanonicalName() + "{substrings=" +
                        argMapNameOnly.get(PREFIX_NAME) + "}",
                PhoneNumberPredicate.class.getCanonicalName() + "{subnumbers=" +
                        argMapPhoneOnly.get(PREFIX_PHONE) + "}"
        );

        assertEquals(expectedEmpty, searchPredicateEmpty.toString());
        assertEquals(expectedNameOnly, searchPredicateNameOnly.toString());
        assertEquals(expectedPhoneOnly, searchPredicatePhoneOnly.toString());
        assertEquals(expectedAllPresent, searchPredicateAllPresent.toString());
    }
}

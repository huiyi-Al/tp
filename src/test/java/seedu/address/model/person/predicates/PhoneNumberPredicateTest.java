package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicate.PhoneNumberPredicate;
import seedu.address.testutil.PersonBuilder;

public class PhoneNumberPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("123");
        List<String> secondPredicateKeywordList = Arrays.asList("123", "456");

        PhoneNumberPredicate firstPredicate = new PhoneNumberPredicate(firstPredicateKeywordList);
        PhoneNumberPredicate secondPredicate = new PhoneNumberPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneNumberPredicate firstPredicateCopy =
                new PhoneNumberPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(123));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_numberContainsSubnumber_returnsTrue() {
        // One subnumber
        PhoneNumberPredicate predicate =
                new PhoneNumberPredicate(Collections.singletonList("123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Multiple subnumber
        predicate = new PhoneNumberPredicate(Arrays.asList("123", "456"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Only one matching subnumber
        predicate = new PhoneNumberPredicate(Arrays.asList("123", "156"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Equal subnumbers
        predicate = new PhoneNumberPredicate(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_numberContainsSubnumber_returnsFalse() {
        // Zero substrings
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Non-matching substrings
        predicate = new PhoneNumberPredicate(Collections.singletonList("91234568"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PhoneNumberPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("1234")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("123", "456");
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(keywords);

        String expected = PhoneNumberPredicate.class.getCanonicalName() + "{subnumbers=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

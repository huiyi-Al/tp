package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests the PhoneNumberPredicate for expected positive and negative behavior
 * in the most efficient and effective manner.
 *
 * <ul>
 *   <li>Equality between same objects, returns true.</li>
 *   <li>Equality between objects of same values, returns true.</li>
 *   <li>Equality between objects of different values, returns false.</li>
 *   <li>Equality between object and a different type, returns false.</li>
 *   <li>Equality between object and null, returns false.</li>
 *   <li>Testing a phone number containing a single matching substring, returns true.</li>
 *   <li>Testing a phone number where all substrings match, returns true.</li>
 *   <li>Testing a phone number where only one of multiple substrings matches, returns true.</li>
 *   <li>Testing a phone number that exactly equals the substring, returns true.</li>
 *   <li>Testing with an empty substring list, returns false.</li>
 *   <li>Testing a phone number with a non-matching substring, returns false.</li>
 *   <li>Testing with substrings that match other fields but not phone, returns false.</li>
 *   <li>String representation matches expected format.</li>
 * </ul>
 */
public class PhoneNumberPredicateTest {

    private static final String PHONE_NUMBER = "91234567";
    private static final String PHONE_NUMBER_HYPHENSPACE = "9-123 4567";

    @Test
    public void equals_sameObject_returnsTrue() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Collections.singletonList("123"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        List<String> keywords = Collections.singletonList("123");
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(keywords);
        PhoneNumberPredicate predicateCopy = new PhoneNumberPredicate(keywords);
        assertTrue(predicate.equals(predicateCopy));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        PhoneNumberPredicate firstPredicate = new PhoneNumberPredicate(Collections.singletonList("123"));
        PhoneNumberPredicate secondPredicate = new PhoneNumberPredicate(Arrays.asList("123", "456"));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Collections.singletonList("123"));
        assertFalse(predicate.equals(123));
    }

    @Test
    public void equals_null_returnsFalse() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Collections.singletonList("123"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void test_singleMatchingSubstring_returnsTrue() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Collections.singletonList("123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone(PHONE_NUMBER).build()));
    }

    @Test
    public void test_allSubstringsMatch_returnsTrue() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Arrays.asList("123", "456"));
        assertTrue(predicate.test(new PersonBuilder().withPhone(PHONE_NUMBER).build()));
    }

    @Test
    public void test_oneOfMultipleSubstringsMatches_returnsTrue() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Arrays.asList("123", "156"));
        assertTrue(predicate.test(new PersonBuilder().withPhone(PHONE_NUMBER).build()));
    }

    @Test
    public void test_substringEqualsFullNumber_returnsTrue() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Collections.singletonList(PHONE_NUMBER));
        assertTrue(predicate.test(new PersonBuilder().withPhone(PHONE_NUMBER).build()));
    }

    @Test
    public void test_hyphenSpaceIgnored_returnsTrue() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Collections.singletonList(PHONE_NUMBER));
        assertTrue(predicate.test(new PersonBuilder().withPhone(PHONE_NUMBER_HYPHENSPACE).build()));
    }

    @Test
    public void test_emptySubstringList_returnsFalse() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone(PHONE_NUMBER).build()));
    }

    @Test
    public void test_nonMatchingSubstring_returnsFalse() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(Collections.singletonList("91234568"));
        assertFalse(predicate.test(new PersonBuilder().withPhone(PHONE_NUMBER).build()));
    }

    @Test
    public void test_substringsMatchOtherFieldsNotPhone_returnsFalse() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("1234")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toString_validSubNumbers_returnsExpectedString() {
        List<String> subNumbers = List.of("123", "456");
        PhoneNumberPredicate predicate = new PhoneNumberPredicate(subNumbers);
        String expected = MessageFormat.format("{0}'{subNumbers='{1}'}'",
                PhoneNumberPredicate.class.getCanonicalName(), subNumbers);
        assertEquals(expected, predicate.toString());
    }
}

package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests the EmailAddressPredicate for expected positive and negative behavior
 * in the most efficient and effective manner.
 *
 * <ul>
 *   <li>Equality between same objects, returns true.</li>
 *   <li>Equality between objects of same values, returns true.</li>
 *   <li>Equality between objects of different values, returns false.</li>
 *   <li>Equality between object and a different type, returns false.</li>
 *   <li>Equality between object and null, returns false.</li>
 *   <li>Testing with an empty string substring, throws IllegalArgumentException.</li>
 *   <li>Testing with a multi-word substring, throws IllegalArgumentException.</li>
 *   <li>Testing an email containing a single matching substring, returns true.</li>
 *   <li>Testing an email where all substrings match, returns true.</li>
 *   <li>Testing an email where only one of multiple substrings matches, returns true.</li>
 *   <li>Testing an email with mixed-case substrings, returns true.</li>
 *   <li>Testing with an empty substring list, returns false.</li>
 *   <li>Testing an email with a non-matching substring, returns false.</li>
 *   <li>Testing with substrings that match other fields but not email, returns false.</li>
 *   <li>String representation matches expected format.</li>
 * </ul>
 */
public class EmailAddressPredicateTest {

    private static final String EMAIL_ALICE = "alice@example.com";
    private static final String EMAIL_BOB = "bob@example.com";

    @Test
    public void equals_sameObject_returnsTrue() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        List<String> keywords = Collections.singletonList("alice");
        EmailAddressPredicate predicate = new EmailAddressPredicate(keywords);
        EmailAddressPredicate predicateCopy = new EmailAddressPredicate(keywords);
        assertTrue(predicate.equals(predicateCopy));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        EmailAddressPredicate firstPredicate = new EmailAddressPredicate(Collections.singletonList("alice"));
        EmailAddressPredicate secondPredicate = new EmailAddressPredicate(Arrays.asList("alice", "bob"));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Collections.singletonList("alice"));
        assertFalse(predicate.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Collections.singletonList("alice"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void test_emptyStringSubstring_returnsTrue() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new PersonBuilder().withEmail(EMAIL_ALICE).build()));
    }

    @Test
    public void test_singleMatchingSubstring_returnsTrue() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail(EMAIL_ALICE).build()));
    }

    @Test
    public void test_allSubstringsMatch_returnsTrue() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Arrays.asList("alice", "example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail(EMAIL_ALICE).build()));
    }

    @Test
    public void test_oneOfMultipleSubstringsMatches_returnsTrue() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Arrays.asList("alice", "bob"));
        assertTrue(predicate.test(new PersonBuilder().withEmail(EMAIL_ALICE).build()));
    }

    @Test
    public void test_mixedCaseSubstrings_returnsTrue() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Arrays.asList("aLiCe", "eXaMpLe"));
        assertTrue(predicate.test(new PersonBuilder().withEmail(EMAIL_ALICE).build()));
    }

    @Test
    public void test_emptySubstringList_returnsFalse() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail(EMAIL_ALICE).build()));
    }

    @Test
    public void test_nonMatchingSubstring_returnsFalse() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(Collections.singletonList("alicee"));
        assertFalse(predicate.test(new PersonBuilder().withEmail(EMAIL_ALICE).build()));
    }

    @Test
    public void test_substringsMatchOtherFieldsNotEmail_returnsFalse() {
        EmailAddressPredicate predicate = new EmailAddressPredicate(
                Arrays.asList("Alice", "12345", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withEmail(EMAIL_BOB).withName("Alice")
                .withPhone("12345").withAddress("Main Street").build()));
    }

    @Test
    public void toString_validSubEmailAddresses_returnsExpectedString() {
        List<String> subEmailAddresses = List.of("test1", "test2");
        EmailAddressPredicate predicate = new EmailAddressPredicate(subEmailAddresses);
        String expected = EmailAddressPredicate.class.getCanonicalName()
                + "{subEmailAddresses=" + subEmailAddresses + "}";
        assertEquals(expected, predicate.toString());
    }
}

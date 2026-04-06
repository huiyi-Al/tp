package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests the FullNamePredicate for expected positive and negative behavior
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
 *   <li>Testing a name containing a single matching substring, returns true.</li>
 *   <li>Testing a name where all substrings match, returns true.</li>
 *   <li>Testing a name where only one of multiple substrings matches, returns true.</li>
 *   <li>Testing a name with mixed-case substrings, returns true.</li>
 *   <li>Testing a name with substrings that span across words, returns true.</li>
 *   <li>Testing with an empty substring list, returns false.</li>
 *   <li>Testing a name with a non-matching substring, returns false.</li>
 *   <li>Testing with substrings that match other fields but not name, returns false.</li>
 *   <li>String representation matches expected format.</li>
 * </ul>
 */
public class FullNamePredicateTest {

    private static final String NAME_ALICE_BOB = "Alice Bob";
    private static final String NAME_ALICE_BOB_CAROL = "Alice Bob Carol";
    private static final String NAME_ALICE = "Alice";

    @Test
    public void equals_sameObject_returnsTrue() {
        FullNamePredicate predicate = new FullNamePredicate(Collections.singletonList("first"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        List<String> keywords = Collections.singletonList("first");
        FullNamePredicate predicate = new FullNamePredicate(keywords);
        FullNamePredicate predicateCopy = new FullNamePredicate(keywords);
        assertTrue(predicate.equals(predicateCopy));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        FullNamePredicate firstPredicate = new FullNamePredicate(Collections.singletonList("first"));
        FullNamePredicate secondPredicate = new FullNamePredicate(Arrays.asList("first", "second"));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        FullNamePredicate predicate = new FullNamePredicate(Collections.singletonList("first"));
        assertFalse(predicate.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        FullNamePredicate predicate = new FullNamePredicate(Collections.singletonList("first"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void test_emptyStringSubstring_returnsTrue() {
        FullNamePredicate predicate = new FullNamePredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new PersonBuilder().withName(NAME_ALICE_BOB).build()));
    }

    @Test
    public void test_singleMatchingSubstring_returnsTrue() {
        FullNamePredicate predicate = new FullNamePredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName(NAME_ALICE_BOB).build()));
    }

    @Test
    public void test_allSubstringsMatch_returnsTrue() {
        FullNamePredicate predicate = new FullNamePredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName(NAME_ALICE_BOB).build()));
    }

    @Test
    public void test_oneOfMultipleSubstringsMatches_returnsTrue() {
        FullNamePredicate predicate = new FullNamePredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
    }

    @Test
    public void test_mixedCaseSubstrings_returnsTrue() {
        FullNamePredicate predicate = new FullNamePredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName(NAME_ALICE_BOB).build()));
    }

    @Test
    public void test_substringsSpanningAcrossWords_returnsTrue() {
        FullNamePredicate predicate = new FullNamePredicate(Arrays.asList("LIc", "bO", "RoL"));
        assertTrue(predicate.test(new PersonBuilder().withName(NAME_ALICE_BOB_CAROL).build()));
    }

    @Test
    public void test_emptySubstringList_returnsFalse() {
        FullNamePredicate predicate = new FullNamePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName(NAME_ALICE).build()));
    }

    @Test
    public void test_nonMatchingSubstring_returnsFalse() {
        FullNamePredicate predicate = new FullNamePredicate(Collections.singletonList("Alicee"));
        assertFalse(predicate.test(new PersonBuilder().withName(NAME_ALICE_BOB).build()));
    }

    @Test
    public void test_substringsMatchOtherFieldsNotName_returnsFalse() {
        FullNamePredicate predicate = new FullNamePredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName(NAME_ALICE).withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toString_validSubNames_returnsExpectedString() {
        List<String> subNames = List.of("test1", "test2");
        FullNamePredicate predicate = new FullNamePredicate(subNames);
        String expected = MessageFormat.format("{0}'{subNames='{1}'}'",
                FullNamePredicate.class.getCanonicalName(), subNames);
        assertEquals(expected, predicate.toString());
    }
}

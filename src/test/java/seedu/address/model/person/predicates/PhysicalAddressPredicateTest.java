package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests the PhysicalAddressPredicate for expected positive and negative behavior
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
 *   <li>Testing a physical address containing a single matching substring, returns true.</li>
 *   <li>Testing a physical address where all substrings match, returns true.</li>
 *   <li>Testing a physical address where only one of multiple substrings matches, returns true.</li>
 *   <li>Testing a physical address with mixed-case substrings, returns true.</li>
 *   <li>Testing with an empty substring list, returns false.</li>
 *   <li>Testing a physical address with a non-matching substring, returns false.</li>
 *   <li>Testing with substrings that match other fields but not physical address, returns false.</li>
 *   <li>String representation matches expected format.</li>
 * </ul>
 */
public class PhysicalAddressPredicateTest {

    private static final String ADDRESS_ALICE = "Alice Street";
    private static final String ADDRESS_BOB = "Bob Avenue";

    @Test
    public void equals_sameObject_returnsTrue() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        List<String> keywords = Collections.singletonList("alice");
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(keywords);
        PhysicalAddressPredicate predicateCopy = new PhysicalAddressPredicate(keywords);
        assertTrue(predicate.equals(predicateCopy));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        PhysicalAddressPredicate firstPredicate = new PhysicalAddressPredicate(Collections.singletonList("alice"));
        PhysicalAddressPredicate secondPredicate = new PhysicalAddressPredicate(Arrays.asList("alice", "bob"));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Collections.singletonList("alice"));
        assertFalse(predicate.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Collections.singletonList("alice"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void test_emptyStringSubstring_throwsIllegalArgumentException() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Collections.singletonList(""));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> predicate.test(new
                PersonBuilder().withAddress(ADDRESS_ALICE).build()));
        assertEquals("Substring parameter cannot be empty", e.getMessage());
    }

    @Test
    public void test_singleMatchingSubstring_returnsTrue() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withAddress(ADDRESS_ALICE).build()));
    }

    @Test
    public void test_allSubstringsMatch_returnsTrue() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Arrays.asList("alice", "example"));
        assertTrue(predicate.test(new PersonBuilder().withAddress(ADDRESS_ALICE).build()));
    }

    @Test
    public void test_oneOfMultipleSubstringsMatches_returnsTrue() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Arrays.asList("alice", "bob"));
        assertTrue(predicate.test(new PersonBuilder().withAddress(ADDRESS_ALICE).build()));
    }

    @Test
    public void test_mixedCaseSubstrings_returnsTrue() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Arrays.asList("aLiCe", "eXaMpLe"));
        assertTrue(predicate.test(new PersonBuilder().withAddress(ADDRESS_ALICE).build()));
    }

    @Test
    public void test_emptySubstringList_returnsFalse() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress(ADDRESS_ALICE).build()));
    }

    @Test
    public void test_nonMatchingSubstring_returnsFalse() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(Collections.singletonList("alicee"));
        assertFalse(predicate.test(new PersonBuilder().withAddress(ADDRESS_ALICE).build()));
    }

    @Test
    public void test_substringsMatchOtherFieldsNotAddress_returnsFalse() {
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(
                Arrays.asList("Carol", "12345", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Carol").withPhone("12345").withAddress(ADDRESS_BOB)
                .build()));
    }

    @Test
    public void toString_validSubPhysicalAddresses_returnsExpectedString() {
        List<String> subPhysicalAddresses = List.of("test1", "test2");
        PhysicalAddressPredicate predicate = new PhysicalAddressPredicate(subPhysicalAddresses);
        String expected = PhysicalAddressPredicate.class.getCanonicalName()
                + "{subPhysicalAddresses=" + subPhysicalAddresses + "}";
        assertEquals(expected, predicate.toString());
    }
}

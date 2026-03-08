package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicate.NameContainsSubstringsPredicate;
import seedu.address.testutil.PersonBuilder;

public class NameContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsSubstringsPredicate firstPredicate = new NameContainsSubstringsPredicate(firstPredicateKeywordList);
        NameContainsSubstringsPredicate secondPredicate =
                new NameContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsSubstringsPredicate firstPredicateCopy =
                new NameContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsSubstrings_returnsTrue() {
        // One substring
        NameContainsSubstringsPredicate predicate =
                new NameContainsSubstringsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple substrings
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching substring
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case substrings
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Exclusive substrings
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("LIc", "bO", "RoL"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob Carol").build()));
    }

    @Test
    public void test_nameDoesNotContainSubstrings_returnsFalse() {
        // Zero substrings
        NameContainsSubstringsPredicate predicate = new NameContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching substrings
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("Alicee"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("test1", "test2");
        NameContainsSubstringsPredicate predicate = new NameContainsSubstringsPredicate(keywords);

        String expected = NameContainsSubstringsPredicate.class.getCanonicalName() + "{substrings=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

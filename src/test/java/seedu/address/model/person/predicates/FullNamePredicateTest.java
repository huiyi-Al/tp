package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class FullNamePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FullNamePredicate firstPredicate = new FullNamePredicate(firstPredicateKeywordList);
        FullNamePredicate secondPredicate =
                new FullNamePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FullNamePredicate firstPredicateCopy =
                new FullNamePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_invalidSubstrings_throwIllegalArgumentException() {
        FullNamePredicate predicate =
                new FullNamePredicate(Collections.singletonList(""));
        try {
            predicate.test(new PersonBuilder().withName("Alice Bob").build());
            throw new AssertionError("The expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("Substring parameter cannot be empty", e.getMessage());
        }

        predicate =
                new FullNamePredicate(Collections.singletonList("word1 word2"));
        try {
            predicate.test(new PersonBuilder().withName("Alice Bob").build());
            throw new AssertionError("The expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("Substring parameter should be a single word", e.getMessage());
        }
    }

    @Test
    public void test_nameContainsSubstrings_returnsTrue() {
        // One substring
        FullNamePredicate predicate =
                new FullNamePredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple substrings
        predicate = new FullNamePredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching substring
        predicate = new FullNamePredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case substrings
        predicate = new FullNamePredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Exclusive substrings
        predicate = new FullNamePredicate(Arrays.asList("LIc", "bO", "RoL"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob Carol").build()));
    }

    @Test
    public void test_nameDoesNotContainSubstrings_returnsFalse() {
        // Zero substrings
        FullNamePredicate predicate = new FullNamePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching substrings
        predicate = new FullNamePredicate(Arrays.asList("Alicee"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new FullNamePredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("test1", "test2");
        FullNamePredicate predicate = new FullNamePredicate(keywords);

        String expected = FullNamePredicate.class.getCanonicalName() + "{substrings=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

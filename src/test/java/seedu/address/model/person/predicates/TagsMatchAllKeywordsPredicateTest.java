package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagsMatchAllKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("AC-Service");
        List<String> secondPredicateKeywordList = Arrays.asList("AC-Service", "Plumbing");

        TagsMatchAllKeywordsPredicate firstPredicate = new TagsMatchAllKeywordsPredicate(firstPredicateKeywordList);
        TagsMatchAllKeywordsPredicate secondPredicate = new TagsMatchAllKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsMatchAllKeywordsPredicate firstPredicateCopy = new TagsMatchAllKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsMatchKeywords_returnsTrue() {
        // One tag match
        TagsMatchAllKeywordsPredicate predicate =
                new TagsMatchAllKeywordsPredicate(Collections.singletonList("Plumbing"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Plumbing").build()));

        // Multiple tags match (AND logic)
        predicate = new TagsMatchAllKeywordsPredicate(Arrays.asList("AC-Service", "TV-Service"));
        assertTrue(predicate.test(new PersonBuilder().withTags("AC-Service", "TV-Service", "Electrical").build()));

        // Case-insensitive check
        predicate = new TagsMatchAllKeywordsPredicate(Arrays.asList("ac-SERVICE", "TV-SERVICE"));
        assertTrue(predicate.test(new PersonBuilder().withTags("AC-Service", "TV-Service").build()));

        // Empty tag match
        predicate = new TagsMatchAllKeywordsPredicate(List.of(""));
        assertTrue(predicate.test(new PersonBuilder().withTags().build()));
    }

    @Test
    public void test_tagsMatchKeywords_returnsFalse() {
        // Zero keywords
        TagsMatchAllKeywordsPredicate predicate = new TagsMatchAllKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Plumbing").build()));

        // Only partial match
        predicate = new TagsMatchAllKeywordsPredicate(Arrays.asList("AC-Service", "Plumbing"));
        assertFalse(predicate.test(new PersonBuilder().withTags("AC-Service").build()));

        // Keywords match name/phone but not tags
        predicate = new TagsMatchAllKeywordsPredicate(Collections.singletonList("Plumbing"));
        assertFalse(predicate.test(new PersonBuilder().withName("Plumbing").withPhone("91234567").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Plumbing", "Urgent");
        TagsMatchAllKeywordsPredicate predicate = new TagsMatchAllKeywordsPredicate(keywords);

        String expected = TagsMatchAllKeywordsPredicate.class.getCanonicalName() + "{tag keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

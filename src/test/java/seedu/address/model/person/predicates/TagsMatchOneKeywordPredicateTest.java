package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagsMatchOneKeywordPredicateTest {
    @Test
    public void equals_sameObject_returnsTrue() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Collections.singletonList("AC-Service"));

        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        List<String> keywords = Collections.singletonList("AC-Service");
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(keywords);
        TagsMatchOneKeywordPredicate copy =
                new TagsMatchOneKeywordPredicate(keywords);

        assertTrue(predicate.equals(copy));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Collections.singletonList("AC-Service"));

        assertFalse(predicate.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Collections.singletonList("AC-Service"));

        assertFalse(predicate.equals(null));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        TagsMatchOneKeywordPredicate first =
                new TagsMatchOneKeywordPredicate(Collections.singletonList("AC-Service"));
        TagsMatchOneKeywordPredicate second =
                new TagsMatchOneKeywordPredicate(Arrays.asList("AC-Service", "Plumbing"));

        assertFalse(first.equals(second));
    }

    @Test
    public void test_nullMatch_returnsFalse() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(null);

        assertFalse(predicate.test(new PersonBuilder().withTags("Plumbing").build()));
    }

    @Test
    public void test_singularEmptyStringMatch_returnsPersonEmptyTags() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Collections.singletonList(""));

        assertTrue(predicate.test(new PersonBuilder().build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("Plumbing").build()));
    }

    @Test
    public void test_singleKeywordMatch_returnsTrue() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Collections.singletonList("Plumbing"));

        assertTrue(predicate.test(new PersonBuilder().withTags("Plumbing").build()));
    }

    @Test
    public void test_multipleKeywordsMatch_returnsTrue() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Arrays.asList("AC-Service", "TV-Service"));

        assertTrue(predicate.test(new PersonBuilder()
                .withTags("AC-Service", "TV-Service", "Electrical")
                .build()));
    }

    @Test
    public void test_caseInsensitiveMatch_returnsTrue() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Arrays.asList("ac-SERVICE", "TV-SERVICE"));

        assertTrue(predicate.test(new PersonBuilder()
                .withTags("AC-Service", "TV-Service")
                .build()));
    }

    @Test
    public void test_partialMatch_returnsTrue() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Arrays.asList("AC-Service", "Plumbing"));

        assertTrue(predicate.test(new PersonBuilder()
                .withTags("AC-Service")
                .build()));
    }

    @Test
    public void test_noKeywords_returnsFalse() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Collections.emptyList());

        assertFalse(predicate.test(new PersonBuilder().withTags("Plumbing").build()));
    }

    @Test
    public void test_keywordsNotInTags_returnsFalse() {
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(Collections.singletonList("Plumbing"));

        assertFalse(predicate.test(new PersonBuilder()
                .withName("Plumbing")
                .withPhone("91234567")
                .build()));
    }

    @Test
    public void toString_validKeywords_returnsFormattedString() {
        List<String> subTags = List.of("Plumbing", "Urgent");
        TagsMatchOneKeywordPredicate predicate =
                new TagsMatchOneKeywordPredicate(subTags);

        String expected = TagsMatchOneKeywordPredicate.class.getCanonicalName() + "{subTags=" + subTags + "}";

        assertEquals(expected, predicate.toString());
    }

    @Test
    public void toString_nullAndEmptyKeywords_returnsEmptyString() {
        List<String> subTags1 = null;
        TagsMatchOneKeywordPredicate predicate1 =
                new TagsMatchOneKeywordPredicate(subTags1);
        String expected1 = TagsMatchOneKeywordPredicate.class.getCanonicalName() + "{subTags=[]}";
        assertEquals(expected1, predicate1.toString());

        List<String> subTags2 = Collections.singletonList("");
        TagsMatchOneKeywordPredicate predicate2 =
                new TagsMatchOneKeywordPredicate(subTags2);
        String expected2 = TagsMatchOneKeywordPredicate.class.getCanonicalName() + "{subTags=" + subTags2 + "}";
        assertEquals(expected2, predicate2.toString());
    }
}

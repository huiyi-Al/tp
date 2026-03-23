package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} matches all of the keywords given.
 */
public class TagsMatchKeywordsPredicate implements Predicate<Person> {
    private final List<String> tagKeywords;

    public TagsMatchKeywordsPredicate(List<String> tagKeywords) {
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Person person) {
        if (tagKeywords.isEmpty()) return false;
        return tagKeywords.stream()
                .allMatch(keyword -> person.getTags().stream()
                                .anyMatch(tag -> tag.tagName.equalsIgnoreCase(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsMatchKeywordsPredicate)) {
            return false;
        }

        TagsMatchKeywordsPredicate otherTagContainsKeywordsPredicate = (TagsMatchKeywordsPredicate) other;
        return tagKeywords.equals(otherTagContainsKeywordsPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tag keywords", tagKeywords).toString();
    }
}

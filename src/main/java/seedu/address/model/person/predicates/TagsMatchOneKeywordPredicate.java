package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} matches at least one of the keywords given.
 */
public class TagsMatchOneKeywordPredicate implements Predicate<Person> {
    private final List<String> tagKeywords;

    public TagsMatchOneKeywordPredicate(List<String> tagKeywords) {
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Person person) {
        if (tagKeywords.isEmpty()) {
            return false;
        }
        return tagKeywords.stream()
                .anyMatch(tagKeyword -> person.getTags().stream()
                        .anyMatch(tag -> tag.tagName.equalsIgnoreCase(tagKeyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsMatchOneKeywordPredicate)) {
            return false;
        }

        TagsMatchOneKeywordPredicate otherPredicate = (TagsMatchOneKeywordPredicate) other;
        return tagKeywords.equals(otherPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tagKeywords", tagKeywords)
                .toString();
    }
}

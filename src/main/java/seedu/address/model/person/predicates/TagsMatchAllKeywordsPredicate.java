package seedu.address.model.person.predicates;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} matches all of the keywords given.
 */
public class TagsMatchAllKeywordsPredicate implements Predicate<Person> {

    private static final Logger logger = Logger.getLogger(TagsMatchAllKeywordsPredicate.class.getName());

    private final List<String> tagKeywords;

    /**
     * Constructs a {@code TagsMatchAllKeywordsPredicate} with the given list of tag keywords.
     *
     * @param tagKeywords the list of keywords to match against a person's tags
     */
    public TagsMatchAllKeywordsPredicate(List<String> tagKeywords) {
        this.tagKeywords = tagKeywords;
        logger.fine(MessageFormat.format("Initialized with tagKeywords: {0}", tagKeywords));
    }

    @Override
    public boolean test(Person person) {
        logger.fine(MessageFormat.format("Testing person: {0}", person));

        if (tagKeywords.isEmpty()) {
            logger.fine("No tag keywords provided");
            return false;
        }

        boolean result = tagKeywords.stream()
                .allMatch(keyword -> {
                    boolean match = person.getTags().stream()
                            .anyMatch(tag -> tag.tagName.equalsIgnoreCase(keyword));
                    logger.fine(MessageFormat.format(
                            "Checking keyword: {0}, match: {1}", keyword, match));
                    return match;
                });

        logger.fine(MessageFormat.format("Final result: {0}", result));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        logger.fine(MessageFormat.format("Checking equality with object: {0}", other));

        if (other == this) {
            logger.fine("Objects are the same instance");
            return true;
        }

        if (!(other instanceof TagsMatchAllKeywordsPredicate)) {
            logger.fine("Object is not instance of TagsMatchAllKeywordsPredicate");
            return false;
        }

        TagsMatchAllKeywordsPredicate otherTagContainsKeywordsPredicate =
                (TagsMatchAllKeywordsPredicate) other;
        boolean result = tagKeywords.equals(otherTagContainsKeywordsPredicate.tagKeywords);

        logger.fine(MessageFormat.format("Equality result: {0}", result));
        return result;
    }

    @Override
    public String toString() {
        String result = new ToStringBuilder(this)
                .add("tag keywords", tagKeywords)
                .toString();

        logger.fine(MessageFormat.format("toString result: {0}", result));
        return result;
    }
}

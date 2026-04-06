package seedu.address.model.person.predicates;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} matches at least one of the keywords given.
 */
public class TagsMatchOneKeywordPredicate implements Predicate<Person> {

    private static final Logger logger = Logger.getLogger(TagsMatchOneKeywordPredicate.class.getName());

    private final List<String> subTags;

    /**
     * Constructs a {@code TagsMatchOneKeywordPredicate} with the given list of tag keywords.
     *
     * @param subTags the list of keywords to match against a person's tags
     */
    public TagsMatchOneKeywordPredicate(List<String> subTags) {
        this.subTags = subTags;
        logger.fine(MessageFormat.format("Initialized with tagKeywords: {0}", subTags));
    }

    @Override
    public boolean test(Person person) {
        logger.fine(MessageFormat.format("Testing client: {0}", person));

        // Tag prefix not specified, so every person does not match
        if (subTags == null) {
            logger.fine("No tag prefix or keywords specified.");
            return false;
        }

        // Tag prefix specified but empty, match only those with no tags
        if (subTags.size() == 1 && subTags.get(0).isEmpty()) {
            logger.info("Tag prefix specified without keywords.");
            return person.getTags().isEmpty();
        }

        logger.fine("Tag prefix specified with keywords.");
        boolean result = subTags.stream()
                .anyMatch(subTag -> {
                    boolean match = person.getTags().stream()
                            .anyMatch(tag -> {
                                String tagString = tag.toString();
                                if (tagString.length() <= 2) { // Account for empty tag
                                    return subTag.isEmpty();
                                }
                                String trimmed = tagString.substring(1, tagString.length() - 1);
                                return StringUtil.containsSubstringIgnoreCase(trimmed, subTag);
                            });
                    logger.fine(MessageFormat.format(
                            "Checking keyword: {0}, match: {1}", subTag, match));
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

        if (!(other instanceof TagsMatchOneKeywordPredicate)) {
            logger.fine("Object is not instance of TagsMatchOneKeywordPredicate");
            return false;
        }

        TagsMatchOneKeywordPredicate otherPredicate = (TagsMatchOneKeywordPredicate) other;
        boolean result = subTags.equals(otherPredicate.subTags);

        logger.fine(MessageFormat.format("Equality result: {0}", result));
        return result;
    }

    @Override
    public String toString() {
        String result = new ToStringBuilder(this)
                .add("subTags", subTags)
                .toString();

        logger.fine(MessageFormat.format("toString result: {0}", result));
        return result;
    }
}

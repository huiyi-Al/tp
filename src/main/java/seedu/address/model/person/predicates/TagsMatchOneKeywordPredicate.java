package seedu.address.model.person.predicates;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        logger.fine("Tag prefix specified");
        boolean result = subTags.stream()
                .anyMatch(subTag -> {
                    logger.fine("Processing blank tag prefix");
                    if (subTag.isBlank()) {
                        return person.getTags().isEmpty();
                    }

                    logger.fine("Processing normal tag prefix");
                    boolean match = person.getTags().stream()
                            .anyMatch(tag -> {
                                String tagString = tag.toString();
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
        boolean result = Objects.equals(subTags, otherPredicate.subTags);

        logger.fine(MessageFormat.format("Equality result: {0}", result));
        return result;
    }

    @Override
    public String toString() {
        String result = new ToStringBuilder(this)
                .add("subTags", subTags != null ? subTags : new ArrayList<>())
                .toString();

        logger.fine(MessageFormat.format("toString result: {0}", result));
        return result;
    }
}

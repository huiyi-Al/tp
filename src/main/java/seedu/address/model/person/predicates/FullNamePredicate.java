package seedu.address.model.person.predicates;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class FullNamePredicate implements Predicate<Person> {

    private static final Logger logger = Logger.getLogger(FullNamePredicate.class.getName());

    private final List<String> subNames;

    /**
     * Constructs a {@code FullNamePredicate} with the given list of name substrings.
     *
     * @param subNames the list of substrings to match against a person's full name
     */
    public FullNamePredicate(List<String> subNames) {
        this.subNames = subNames;
        logger.fine(MessageFormat.format("Initialized with subNames: {0}", subNames));
    }

    @Override
    public boolean test(Person person) {
        logger.fine(MessageFormat.format("Testing person: {0}", person));

        boolean result = subNames.stream()
                .anyMatch(substring -> {
                    boolean match = StringUtil.containsSubstringIgnoreCase(
                            person.getName().fullName, substring);
                    logger.fine(MessageFormat.format(
                            "Checking substring: {0}, match: {1}", substring, match));
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

        if (!(other instanceof FullNamePredicate)) {
            logger.fine("Object is not instance of FullNamePredicate");
            return false;
        }

        FullNamePredicate otherFullNamePredicate = (FullNamePredicate) other;
        boolean result = subNames.equals(otherFullNamePredicate.subNames);

        logger.fine(MessageFormat.format("Equality result: {0}", result));
        return result;
    }

    @Override
    public String toString() {
        String result = new ToStringBuilder(this)
                .add("subNames", subNames)
                .toString();

        logger.fine(MessageFormat.format("toString result: {0}", result));
        return result;
    }
}

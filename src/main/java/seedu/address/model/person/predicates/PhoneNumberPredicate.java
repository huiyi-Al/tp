package seedu.address.model.person.predicates;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code phone number} is contained within the query.
 */
public class PhoneNumberPredicate implements Predicate<Person> {

    private static final Logger logger = Logger.getLogger(PhoneNumberPredicate.class.getName());

    private final List<String> subNumbers;

    /**
     * Constructs a {@code PhoneNumberPredicate} with the given list of phone number substrings.
     *
     * @param subNumbers the list of substrings to match against a person's phone number
     */
    public PhoneNumberPredicate(List<String> subNumbers) {
        this.subNumbers = subNumbers;
        logger.fine(MessageFormat.format("Initialized with subNumbers: {0}", subNumbers));
    }

    @Override
    public boolean test(Person person) {
        logger.fine(MessageFormat.format("Testing person: {0}", person));

        boolean result = subNumbers.stream()
                .anyMatch(subNumber -> {
                    boolean match = StringUtil.containsSubstringIgnoreCase(
                            person.getPhone().value, subNumber);
                    logger.fine(MessageFormat.format(
                            "Checking subNumber: {0}, match: {1}", subNumber, match));
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

        if (!(other instanceof PhoneNumberPredicate)) {
            logger.fine("Object is not instance of PhoneNumberPredicate");
            return false;
        }

        PhoneNumberPredicate otherPhoneNumberPredicate = (PhoneNumberPredicate) other;
        boolean result = subNumbers.equals(otherPhoneNumberPredicate.subNumbers);

        logger.fine(MessageFormat.format("Equality result: {0}", result));
        return result;
    }

    @Override
    public String toString() {
        String result = new ToStringBuilder(this)
                .add("subNumbers", subNumbers)
                .toString();

        logger.fine(MessageFormat.format("toString result: {0}", result));
        return result;
    }
}

package seedu.address.model.person.predicates;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code email address} is contained within the query.
 */
public class EmailAddressPredicate implements Predicate<Person> {

    private static final Logger logger = Logger.getLogger(EmailAddressPredicate.class.getName());

    private final List<String> subEmailAddresses;

    /**
     * Constructs a {@code EmailAddressPredicate} with the given list of email address substrings.
     *
     * @param subEmailAddresses the list of substrings to match against a person's email address
     */
    public EmailAddressPredicate(List<String> subEmailAddresses) {
        this.subEmailAddresses = subEmailAddresses;
        logger.fine(MessageFormat.format("Initialized with subEmailAddresses: {0}", subEmailAddresses));
    }

    @Override
    public boolean test(Person person) {
        logger.fine(MessageFormat.format("Testing person: {0}", person));

        boolean result = subEmailAddresses.stream()
                .anyMatch(subEmailAddress -> {
                    boolean match = StringUtil.containsSubstringIgnoreCase(
                            person.getEmail().value, subEmailAddress);
                    logger.fine(MessageFormat.format(
                            "Checking subEmailAddress: {0}, match: {1}", subEmailAddress, match));
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

        if (!(other instanceof EmailAddressPredicate)) {
            logger.fine("Object is not instance of EmailAddressPredicate");
            return false;
        }

        EmailAddressPredicate otherEmailAddressPredicate = (EmailAddressPredicate) other;
        boolean result = subEmailAddresses.equals(otherEmailAddressPredicate.subEmailAddresses);

        logger.fine(MessageFormat.format("Equality result: {0}", result));
        return result;
    }

    @Override
    public String toString() {
        String result = new ToStringBuilder(this)
                .add("subEmailAddresses", subEmailAddresses)
                .toString();

        logger.fine(MessageFormat.format("toString result: {0}", result));
        return result;
    }
}

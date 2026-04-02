package seedu.address.model.person.predicates;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code physical address} is contained within the query.
 */
public class PhysicalAddressPredicate implements Predicate<Person> {

    private static final Logger logger = Logger.getLogger(PhysicalAddressPredicate.class.getName());

    private final List<String> subPhysicalAddresses;

    /**
     * Constructs a {@code PhysicalAddressPredicate} with the given list of address substrings.
     *
     * @param subPhysicalAddresses the list of substrings to match against a person's physical address
     */
    public PhysicalAddressPredicate(List<String> subPhysicalAddresses) {
        this.subPhysicalAddresses = subPhysicalAddresses;
        logger.fine(MessageFormat.format("Initialized with subPhysicalAddresses: {0}", subPhysicalAddresses));
    }

    @Override
    public boolean test(Person person) {
        logger.fine(MessageFormat.format("Testing client: {0}", person));

        boolean result = subPhysicalAddresses.stream()
                .anyMatch(subPhysicalAddress -> {
                    boolean match = StringUtil.containsSubstringIgnoreCase(
                            person.getAddress().value, subPhysicalAddress);
                    logger.fine(MessageFormat.format(
                            "Checking subPhysicalAddress: {0}, match: {1}", subPhysicalAddress, match));
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

        if (!(other instanceof PhysicalAddressPredicate)) {
            logger.fine("Object is not instance of PhysicalAddressPredicate");
            return false;
        }

        PhysicalAddressPredicate otherPhysicalAddressPredicate = (PhysicalAddressPredicate) other;
        boolean result = subPhysicalAddresses.equals(otherPhysicalAddressPredicate.subPhysicalAddresses);

        logger.fine(MessageFormat.format("Equality result: {0}", result));
        return result;
    }

    @Override
    public String toString() {
        String result = new ToStringBuilder(this)
                .add("subPhysicalAddresses", subPhysicalAddresses)
                .toString();

        logger.fine(MessageFormat.format("toString result: {0}", result));
        return result;
    }
}

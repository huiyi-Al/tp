package seedu.address.model.person.predicates;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code name, phone number, email address, physical address, tags} can be matched by the
 * predicates provided.
 */
public class SearchPredicate implements Predicate<Person> {
    private static final Logger logger = Logger.getLogger(SearchPredicate.class.getName());

    private final FullNamePredicate fullNamePredicate;
    private final PhoneNumberPredicate phoneNumberPredicate;
    private final EmailAddressPredicate emailAddressPredicate;
    private final PhysicalAddressPredicate physicalAddressPredicate;
    private final TagsMatchOneKeywordPredicate tagsPredicate;

    /**
     * Creates a {@code SearchPredicate} that filters persons based on the provided argument map.
     *
     * @param argMultimap A map from {@code Prefix} to a list of search strings, where each prefix
     *                    corresponds to a field to filter on.
     *                    {@code PREFIX_NAME} for name substring matching.
     *                    {@code PREFIX_PHONE} for phone number matching.
     *                    {@code PREFIX_EMAIL} for email address matching.
     *                    {@code PREFIX_ADDRESS} for physical address matching.
     *                    {@code PREFIX_TAG} for tags matching.
     *                    Prefixes absent from the map are not filtered on.
     */
    public SearchPredicate(ArgumentMultimap argMultimap) {
        logger.info("Initializing SearchPredicate");

        this.fullNamePredicate = argMultimap.getValue(PREFIX_NAME).isPresent()
                ? new FullNamePredicate(argMultimap.getAllValues(PREFIX_NAME))
                : new FullNamePredicate(new ArrayList<>());
        logger.fine(MessageFormat.format("FullNamePredicate initialized: {0}", fullNamePredicate));

        this.phoneNumberPredicate = argMultimap.getValue(PREFIX_PHONE).isPresent()
                ? new PhoneNumberPredicate(argMultimap.getAllValues(PREFIX_PHONE))
                : new PhoneNumberPredicate(new ArrayList<>());
        logger.fine(MessageFormat.format("PhoneNumberPredicate initialized: {0}", phoneNumberPredicate));

        this.emailAddressPredicate = argMultimap.getValue(PREFIX_EMAIL).isPresent()
                ? new EmailAddressPredicate(argMultimap.getAllValues(PREFIX_EMAIL))
                : new EmailAddressPredicate(new ArrayList<>());
        logger.fine(MessageFormat.format("EmailAddressPredicate initialized: {0}", emailAddressPredicate));

        this.physicalAddressPredicate = argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                ? new PhysicalAddressPredicate(argMultimap.getAllValues(PREFIX_ADDRESS))
                : new PhysicalAddressPredicate(new ArrayList<>());
        logger.fine(MessageFormat.format("PhysicalAddressPredicate initialized: {0}", physicalAddressPredicate));

        this.tagsPredicate = argMultimap.getValue(PREFIX_TAG).isPresent()
                ? new TagsMatchOneKeywordPredicate(argMultimap.getAllValues(PREFIX_TAG))
                : new TagsMatchOneKeywordPredicate(new ArrayList<>());
        logger.fine(MessageFormat.format("TagsPredicate initialized: {0}", tagsPredicate));
    }

    @Override
    public boolean test(Person person) {
        logger.fine(MessageFormat.format("Testing person: {0}", person));

        boolean isNameMatch = fullNamePredicate.test(person);
        logger.fine(MessageFormat.format("Name condition: {0}", isNameMatch));

        boolean isPhoneMatch = phoneNumberPredicate.test(person);
        logger.fine(MessageFormat.format("Phone condition: {0}", isPhoneMatch));

        boolean isEmailAddressMatch = emailAddressPredicate.test(person);
        logger.fine(MessageFormat.format("Email condition: {0}", isEmailAddressMatch));

        boolean isPhysicalAddressMatch = physicalAddressPredicate.test(person);
        logger.fine(MessageFormat.format("Address condition: {0}", isPhysicalAddressMatch));

        boolean isTagMatch = tagsPredicate.test(person);
        logger.fine(MessageFormat.format("Tags condition: {0}", isTagMatch));

        boolean result = isNameMatch || isPhoneMatch || isEmailAddressMatch
                || isPhysicalAddressMatch || isTagMatch;

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

        if (!(other instanceof SearchPredicate)) {
            logger.fine("Object is not instance of SearchPredicate");
            return false;
        }

        SearchPredicate otherSearchPredicate = (SearchPredicate) other;
        boolean result = Objects.equals(fullNamePredicate, otherSearchPredicate.fullNamePredicate)
                && Objects.equals(phoneNumberPredicate, otherSearchPredicate.phoneNumberPredicate)
                && Objects.equals(emailAddressPredicate, otherSearchPredicate.emailAddressPredicate)
                && Objects.equals(physicalAddressPredicate, otherSearchPredicate.physicalAddressPredicate)
                && Objects.equals(tagsPredicate, otherSearchPredicate.tagsPredicate);

        logger.fine(MessageFormat.format("Equality result: {0}", result));
        return result;
    }

    @Override
    public String toString() {
        String result = MessageFormat.format(
                "{0}, {1}, {2}, {3}, {4}",
                fullNamePredicate.toString(),
                phoneNumberPredicate.toString(),
                emailAddressPredicate.toString(),
                physicalAddressPredicate.toString(),
                tagsPredicate.toString()
        );
        logger.fine(MessageFormat.format("toString result: {0}", result));
        return result;
    }
}

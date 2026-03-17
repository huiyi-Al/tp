package seedu.address.model.person.predicate;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code name} or {@code phone number} can be matched by the predicates provided.
 */
public class SearchPredicate implements Predicate<Person> {
    private final NameContainsSubstringsPredicate nameContainsSubstringsPredicate;
    private final PhoneNumberPredicate phoneNumberPredicate;

    /**
     * Creates a {@code SearchPredicate} that filters persons based on the provided argument map.
     *
     * @param argMap A map from {@code Prefix} to a list of search strings, where each prefix
     *               corresponds to a field to filter on.
     *               {@code PREFIX_NAME} for name substring matching.
     *               {@code PREFIX_PHONE} for phone number matching.
     *               Prefixes absent from the map are not filtered on.
     */
    public SearchPredicate(Map<Prefix, List<String>> argMap) {
        this.nameContainsSubstringsPredicate = argMap.containsKey(PREFIX_NAME)
                ? new NameContainsSubstringsPredicate(argMap.get(PREFIX_NAME))
                : null;

        this.phoneNumberPredicate = argMap.containsKey(PREFIX_PHONE)
                ? new PhoneNumberPredicate(argMap.get(PREFIX_PHONE))
                : null;
    }

    @Override
    public boolean test(Person person) {
        boolean nameCondition = false;
        boolean phoneCondition = false;

        if (nameContainsSubstringsPredicate != null) {
            nameCondition = nameContainsSubstringsPredicate.test(person);
        }
        if (phoneNumberPredicate != null) {
            phoneCondition = phoneNumberPredicate.test(person);
        }

        return nameCondition || phoneCondition;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchPredicate)) {
            return false;
        }

        SearchPredicate otherSearchPredicate = (SearchPredicate) other;
        return nameContainsSubstringsPredicate.equals(otherSearchPredicate.nameContainsSubstringsPredicate)
                && phoneNumberPredicate.equals(otherSearchPredicate.phoneNumberPredicate);
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "{0}, {1}\n",
                nameContainsSubstringsPredicate != null ? nameContainsSubstringsPredicate.toString() : "NA",
                phoneNumberPredicate != null ? phoneNumberPredicate.toString() : "NA"
        );
    }
}

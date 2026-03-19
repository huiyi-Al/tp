package seedu.address.model.person.predicates;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code name} or {@code phone number} can be matched by the predicates provided.
 */
public class SearchPredicate implements Predicate<Person> {
    private final FullNamePredicate fullNamePredicate;
    private final PhoneNumberPredicate phoneNumberPredicate;

    /**
     * Creates a {@code SearchPredicate} that filters persons based on the provided argument map.
     *
     * @param argMultimap A map from {@code Prefix} to a list of search strings, where each prefix
     *                    corresponds to a field to filter on.
     *                    {@code PREFIX_NAME} for name substring matching.
     *                    {@code PREFIX_PHONE} for phone number matching.
     *                    Prefixes absent from the map are not filtered on.
     */
    public SearchPredicate(ArgumentMultimap argMultimap) {
        this.fullNamePredicate = argMultimap.getValue(PREFIX_NAME).isPresent()
                ? new FullNamePredicate(Arrays.asList(argMultimap.getValue(PREFIX_NAME).get().split("\\s+")))
                : new FullNamePredicate(new ArrayList<>());
        this.phoneNumberPredicate = argMultimap.getValue(PREFIX_PHONE).isPresent()
                ? new PhoneNumberPredicate(Arrays.asList(argMultimap.getValue(PREFIX_PHONE).get().split("\\s+")))
                : new PhoneNumberPredicate(new ArrayList<>());
    }

    @Override
    public boolean test(Person person) {
        boolean nameCondition = false;
        boolean phoneCondition = false;

        nameCondition = fullNamePredicate.test(person);
        phoneCondition = phoneNumberPredicate.test(person);

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
        return Objects.equals(fullNamePredicate, otherSearchPredicate.fullNamePredicate)
                && Objects.equals(phoneNumberPredicate, otherSearchPredicate.phoneNumberPredicate);
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "{0}, {1}",
                fullNamePredicate.toString(),
                phoneNumberPredicate.toString()
        );
    }
}

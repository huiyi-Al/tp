package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code email address} is contained within the query.
 */
public class EmailAddressPredicate implements Predicate<Person> {
    private final List<String> subEmailAddresses;

    public EmailAddressPredicate(List<String> subEmailAddresses) {
        this.subEmailAddresses = subEmailAddresses;
    }

    @Override
    public boolean test(Person person) {
        return subEmailAddresses.stream()
                .anyMatch(subEmailAddress -> StringUtil.containsSubstringIgnoreCase(person.getPhone().value,
                        subEmailAddress));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailAddressPredicate)) {
            return false;
        }

        EmailAddressPredicate otherEmailAddressPredicate = (EmailAddressPredicate) other;
        return subEmailAddresses.equals(otherEmailAddressPredicate.subEmailAddresses);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("subEmailAddresses", subEmailAddresses).toString();
    }
}

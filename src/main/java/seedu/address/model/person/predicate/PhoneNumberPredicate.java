package seedu.address.model.person.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code phone number} is contained within the query.
 */
public class PhoneNumberPredicate implements Predicate<Person> {
    private final List<String> subNumbers;

    public PhoneNumberPredicate(List<String> subNumbers) {
        this.subNumbers = subNumbers;
    }

    @Override
    public boolean test(Person person) {
        return subNumbers.stream()
                .anyMatch(subNumber -> StringUtil.containsSubstringIgnoreCase(person.getPhone().value, subNumber));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneNumberPredicate)) {
            return false;
        }

        PhoneNumberPredicate otherPhoneNumberPredicate = (PhoneNumberPredicate) other;
        return subNumbers.equals(otherPhoneNumberPredicate.subNumbers);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("subNumbers", subNumbers).toString();
    }
}
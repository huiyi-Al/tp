package seedu.address.model.person.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substrings;
    private final List<String> subNumbers;

    public NameContainsSubstringsPredicate(List<String> substrings, List<String> subNumbers) {
        this.substrings = substrings;
        this.subNumbers = subNumbers;
    }

    @Override
    public boolean test(Person person) {
        return substrings.stream()
                .anyMatch(substring -> StringUtil.containsSubstringIgnoreCase(person.getName().fullName,
                        substring))
                || subNumbers.stream()
                .anyMatch(subNumber -> StringUtil.containsSubstringIgnoreCase(person.getPhone().value,
                        subNumber));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsSubstringsPredicate)) {
            return false;
        }

        NameContainsSubstringsPredicate otherNameContainsSubstringsPredicate = (NameContainsSubstringsPredicate) other;
        return substrings.equals(otherNameContainsSubstringsPredicate.substrings);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substrings", substrings).toString();
    }
}

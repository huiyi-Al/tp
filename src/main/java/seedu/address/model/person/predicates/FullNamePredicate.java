package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class FullNamePredicate implements Predicate<Person> {
    private final List<String> substrings;

    public FullNamePredicate(List<String> substrings) {
        this.substrings = substrings;
    }

    @Override
    public boolean test(Person person) {
        return substrings.stream()
                .anyMatch(substring -> StringUtil.containsSubstringIgnoreCase(person.getName().fullName,
                        substring));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FullNamePredicate)) {
            return false;
        }

        FullNamePredicate otherFullNamePredicate = (FullNamePredicate) other;
        return substrings.equals(otherFullNamePredicate.substrings);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substrings", substrings).toString();
    }
}

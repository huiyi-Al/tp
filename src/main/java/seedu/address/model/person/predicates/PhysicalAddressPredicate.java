package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code physical address} is contained within the query.
 */
public class PhysicalAddressPredicate implements Predicate<Person> {
    private final List<String> subPhysicalAddresses;

    public PhysicalAddressPredicate(List<String> subPhysicalAddresses) {
        this.subPhysicalAddresses = subPhysicalAddresses;
    }

    @Override
    public boolean test(Person person) {
        return subPhysicalAddresses.stream()
                .anyMatch(subPhysicalAddress -> StringUtil.containsSubstringIgnoreCase(person.getAddress().value,
                        subPhysicalAddress));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhysicalAddressPredicate)) {
            return false;
        }

        PhysicalAddressPredicate otherPhysicalAddressPredicate = (PhysicalAddressPredicate) other;
        return subPhysicalAddresses.equals(otherPhysicalAddressPredicate.subPhysicalAddresses);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("subPhysicalAddresses", subPhysicalAddresses).toString();
    }
}

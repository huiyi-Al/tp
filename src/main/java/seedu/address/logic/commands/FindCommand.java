package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.predicate.NameContainsSubstringsPredicate;
import seedu.address.model.person.predicate.PhoneNumberPredicate;

/**
 * Finds and lists all persons in address book based on following criteria:
 * 1. Full name contains any of the argument keywords.
 * 2. Phone number contains any of the numbers given.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified substrings (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_NAME + "substring [OPTIONAL_SUBSTRINGS]" + " " + PREFIX_PHONE
            + "number [OPTIONAL_NUMBERS]" + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "david" + " " + PREFIX_PHONE + "123";

    private final NameContainsSubstringsPredicate namePredicate;

    public FindCommand(NameContainsSubstringsPredicate namePredicate) {
        this.namePredicate = namePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(namePredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return namePredicate.equals(otherFindCommand.namePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .toString();
    }
}

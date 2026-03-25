package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.text.MessageFormat;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.predicates.SearchPredicate;

/**
 * Finds and lists all persons in address book based on following criteria:
 * 1. Full name contains any of the space-separated queries as substrings.
 * 2. Phone number contains any of the space-separated numbers given.
 * 3. Email address contains any of the space-separated queries as substrings.
 * 4. Physical address contains any of the space-separated queries as substrings.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = MessageFormat.format(
            """
                    {0}: Finds all persons whose details contain any of the given queries
                    and displays them as a list with index numbers.
                    Parameters: {1}subName [OPTIONAL_SUBNAMES] {2}subNumber [OPTIONAL_SUBNUMBERS] {3}subEmail
                    [OPTIONAL_SUBEMAILS] {4}subAddresses [OPTIONAL_SUBADDRESSES] {5}tags [OPTIONAL_TAGS]
                    Example: {0} {1}david {2}123 {3}d@gmail {4}Woodlands {5}Friend""",
            COMMAND_WORD, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG
    );

    private final SearchPredicate searchPredicate;

    public FindCommand(SearchPredicate searchPredicate) {
        this.searchPredicate = searchPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(searchPredicate);
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
        return searchPredicate.equals(otherFindCommand.searchPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("searchPredicate", searchPredicate)
                .toString();
    }
}

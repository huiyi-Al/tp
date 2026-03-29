package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.text.MessageFormat;
import java.util.logging.Logger;

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
                    {0}: Finds all persons whose details contain any of the given queries \
                    and displays them as a list with index numbers.
                    Preamble given will result in an error.
                    Parameters: {1}subName [OPTIONAL_SUBNAMES] {2}subNumber [OPTIONAL_SUBNUMBERS] {3}subEmail \
                    [OPTIONAL_SUBEMAILS] {4}subAddresses [OPTIONAL_SUBADDRESSES] {5}tags [OPTIONAL_TAGS]
                    Example: {0} {1}david {2}123 {3}d@gmail {4}Woodlands {5}Friend""",
            COMMAND_WORD, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG
    );

    private static final Logger logger = Logger.getLogger(FindCommand.class.getName());

    private final SearchPredicate searchPredicate;

    /**
     * Constructs a {@code FindCommand} with the specified {@code SearchPredicate}.
     *
     * @param searchPredicate the predicate used to search for people
     */
    public FindCommand(SearchPredicate searchPredicate) {
        logger.info("Initializing FindCommand");
        this.searchPredicate = searchPredicate;
        logger.fine(MessageFormat.format("SearchPredicate set: {0}", searchPredicate));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.fine(MessageFormat.format("Executing FindCommand with predicate: {0}", searchPredicate));

        model.updateFilteredPersonList(searchPredicate);
        int resultSize = model.getFilteredPersonList().size();

        logger.fine(MessageFormat.format("Filtered person list size: {0}", resultSize));

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, resultSize));
    }

    @Override
    public boolean equals(Object other) {
        logger.fine(MessageFormat.format("Checking equality with object: {0}", other));

        if (other == this) {
            logger.fine("Objects are the same instance");
            return true;
        }

        if (!(other instanceof FindCommand)) {
            logger.fine("Object is not instance of FindCommand");
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        boolean result = searchPredicate.equals(otherFindCommand.searchPredicate);

        logger.fine(MessageFormat.format("Equality result: {0}", result));
        return result;
    }

    @Override
    public String toString() {
        String result = new ToStringBuilder(this)
                .add("searchPredicate", searchPredicate)
                .toString();

        logger.fine(MessageFormat.format("toString result: {0}", result));
        return result;
    }
}

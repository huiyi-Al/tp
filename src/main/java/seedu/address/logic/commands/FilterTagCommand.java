package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.TagsMatchAllKeywordsPredicate;

/**
 * Finds and lists all persons in address book with specified tags.
 * Keyword matching is case-insensitive.
 */
public class FilterTagCommand extends Command {

    public static final String COMMAND_WORD = "filtertag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters clients by tags. "
            + "Returns clients with all specified tags. Commands cannot have a mix of specific tags and blank tags. "
            + "If there is a blank tag, clients with no tags are filtered.\n"
            + "Parameters: " + PREFIX_TAG + "TAG [" + PREFIX_TAG + "MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + PREFIX_TAG + "AC-Service " + PREFIX_TAG + "Plumbing";

    public static final String MESSAGE_MIX_COMMAND =
            "A mix of specific tags and blank tags are not allowed in filtertag.";

    private final TagsMatchAllKeywordsPredicate predicate;

    public FilterTagCommand(TagsMatchAllKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.addPredicateFilteredPersonList(predicate);
        Person lastSelectedPerson = model.getSelectedPerson().getValue();
        if (lastSelectedPerson != null && !model.getFilteredPersonList().contains(lastSelectedPerson)) {
            model.setSelectedPerson(null);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterTagCommand)) {
            return false;
        }

        FilterTagCommand otherFilterCommand = (FilterTagCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

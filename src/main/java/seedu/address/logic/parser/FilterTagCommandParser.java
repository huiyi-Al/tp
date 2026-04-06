package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.TagsMatchAllKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterTagCommandParser implements Parser<FilterTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FilterTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);

        // Ensure at least one --tag= prefix was provided.
        boolean isTagListEmpty = tagKeywords.isEmpty();

        // Ensure the preamble is empty.
        boolean hasPreamble = !argMultimap.getPreamble().isEmpty();

        // Ensure no provided tag is empty or just whitespace
        boolean hasBlankTags = tagKeywords.stream().anyMatch(tag -> tag.trim().isEmpty());

        if (isTagListEmpty || hasPreamble || hasBlankTags) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));
        }

        return new FilterTagCommand(new TagsMatchAllKeywordsPredicate(tagKeywords));
    }
}

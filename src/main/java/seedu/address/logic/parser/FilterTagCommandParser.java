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
        boolean isPrefixMissing = argMultimap.getValue(PREFIX_TAG).isEmpty();

        // Ensure the preamble is empty.
        boolean hasPreamble = !argMultimap.getPreamble().isEmpty();

        if (isPrefixMissing || hasPreamble) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));
        }

        long blankCount = tagKeywords.stream().filter(String::isBlank).count();
        long nonBlankCount = tagKeywords.size() - blankCount;

        if (blankCount > 0 && nonBlankCount > 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_MIX_COMMAND));
        }

        if (blankCount > 0) {
            return new FilterTagCommand(new TagsMatchAllKeywordsPredicate(List.of("")));
        }

        return new FilterTagCommand(new TagsMatchAllKeywordsPredicate(tagKeywords));
    }
}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_NAME);

        List<String> nameKeywords = Arrays.asList(argMultimap.getPreamble().split("\\s+"));
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> extraNameKeywords = Arrays.asList(argMultimap.getValue(PREFIX_NAME).get().split("\\s+"));
            nameKeywords.addAll(extraNameKeywords);
        }

        return new FindCommand(new NameContainsKeywordsPredicate(nameKeywords));
    }

}

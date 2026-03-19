package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.FullNamePredicate;
import seedu.address.model.person.predicates.PhoneNumberPredicate;
import seedu.address.model.person.predicates.SearchPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<Prefix> expectedPrefixes = new ArrayList<>(Arrays.asList(PREFIX_NAME, PREFIX_PHONE));
        trimmedArgs = " " + trimmedArgs; // To allow prefix on first character to be detected
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(trimmedArgs, expectedPrefixes.toArray(new Prefix[0]));

        // If there isn't at least one prefix
        boolean nonePresent = expectedPrefixes.stream()
                .allMatch(p -> argMultimap.getValue(p).isEmpty());
        if (nonePresent) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // If any present prefixes are empty
        boolean anyPresentButEmpty = expectedPrefixes.stream()
                .anyMatch(p -> argMultimap.getValue(p).map(String::isEmpty).orElse(false));
        if (anyPresentButEmpty) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new SearchPredicate(argMultimap));
    }
}

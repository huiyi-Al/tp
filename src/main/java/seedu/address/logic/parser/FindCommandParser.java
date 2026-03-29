package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.SearchPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Logger logger = Logger.getLogger(FindCommandParser.class.getName());

    /**
     * Parses the given {@code args} to return a {@code FindCommand} object for execution.
     * <p>
     * Validates that at least one valid prefix is present, no provided prefix has an empty value,
     * and each prefix contains only one query. Constructs a {@code SearchPredicate} based on the
     * parsed arguments and returns a new {@code FindCommand} with that predicate.
     *
     * @param args the raw input string containing search keywords and prefixes
     * @return a {@code FindCommand} initialized with a {@code SearchPredicate} based on the input
     * @throws ParseException if the input is empty, no prefixes are present, or any prefix has an empty value
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.fine(MessageFormat.format("Parsing args: {0}", args));

        String trimmedArgs = args.trim();
        logger.fine(MessageFormat.format("Trimmed args: {0}", trimmedArgs));

        if (trimmedArgs.isEmpty()) {
            logger.warning("Arguments are empty after trimming");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<Prefix> expectedPrefixes = new ArrayList<>(Arrays.asList(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG));
        logger.fine(MessageFormat.format("Expected prefixes: {0}", expectedPrefixes));

        trimmedArgs = MessageFormat.format(" {0}", trimmedArgs);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                trimmedArgs, expectedPrefixes.toArray(new Prefix[0]));
        logger.fine(MessageFormat.format("ArgumentMultimap created: {0}", argMultimap));

        boolean nonePresent = expectedPrefixes.stream()
                .allMatch(p -> argMultimap.getValue(p).isEmpty());
        if (nonePresent) {
            logger.warning("Validation failed: no prefixes present");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        boolean anyPresentButEmpty = expectedPrefixes.stream()
                .anyMatch(p -> argMultimap.getValue(p).map(String::isEmpty).orElse(false));
        if (anyPresentButEmpty) {
            logger.warning("Validation failed: some prefixes have empty values");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        boolean anyPrefixMultipleQuery = expectedPrefixes.stream()
                .allMatch(p -> argMultimap.getValue(p).isEmpty() || argMultimap.getAllValues(p).stream()
                        .anyMatch(v -> v.matches(".*\\s.*")));
        if (anyPrefixMultipleQuery) {
            logger.warning("Validation failed: some prefixes have multiple queries");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        SearchPredicate predicate = new SearchPredicate(argMultimap);
        logger.fine(MessageFormat.format("SearchPredicate created: {0}", predicate));

        return new FindCommand(predicate);
    }
}

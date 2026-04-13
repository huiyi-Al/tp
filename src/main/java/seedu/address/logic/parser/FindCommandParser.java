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
    private static final List<Prefix> EXPECTED_PREFIXES = new ArrayList<>(Arrays.asList(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG));

    private void checkValidFormat(ArgumentMultimap argMultimap) throws ParseException {
        boolean preamblePresent = !argMultimap.getPreamble().isEmpty();
        boolean nonePresent = EXPECTED_PREFIXES.stream()
                .allMatch(p -> argMultimap.getValue(p).isEmpty());
        boolean anyPresentButInvalidEmpty = EXPECTED_PREFIXES.stream()
                .anyMatch(p -> {
                    if (p.equals(PREFIX_TAG)) {
                        return false;
                    }
                    return argMultimap.getAllValues(p).stream().anyMatch(String::isBlank);
                });

        if (preamblePresent || nonePresent || anyPresentButInvalidEmpty) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

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
        if (trimmedArgs.isEmpty()) {
            logger.warning("Arguments are empty after trimming");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        trimmedArgs = MessageFormat.format(" {0}", trimmedArgs);

        // Solution below adapted from https://stackoverflow.com/a/9863752/11906335
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                trimmedArgs, EXPECTED_PREFIXES.toArray(new Prefix[0]));
        logger.fine(MessageFormat.format("ArgumentMultimap created: {0}", argMultimap));

        checkValidFormat(argMultimap);

        SearchPredicate predicate = new SearchPredicate(argMultimap);
        logger.fine(MessageFormat.format("SearchPredicate created: {0}", predicate));

        return new FindCommand(predicate);
    }
}

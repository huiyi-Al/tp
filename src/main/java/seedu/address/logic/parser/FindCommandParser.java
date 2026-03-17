package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicate.NameContainsSubstringsPredicate;
import seedu.address.model.person.predicate.PhoneNumberPredicate;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_NAME, PREFIX_PHONE);

        // If there isn't at least one prefix
        int missing = 0;
        for (Prefix expectedPrefix: expectedPrefixes) {
            if (argMultimap.getValue(expectedPrefix).isEmpty()) {
                missing++;
            }
        }
        if (missing == expectedPrefixes.size()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // If any prefix is present without any values given
        for (Prefix expectedPrefix: expectedPrefixes) {
            if (argMultimap.getValue(expectedPrefix).isPresent()
                    && argMultimap.getValue(expectedPrefix).get().isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        // Keep list of all present prefixes
        List<Prefix> presentPrefixes = new ArrayList<>();
        for (Prefix expectedPrefix: expectedPrefixes) {
            if (argMultimap.getValue(expectedPrefix).isEmpty()) {
                continue;
            }
            presentPrefixes.add(expectedPrefix);
        }

        // Convert map to remove missing prefixes
        Map<Prefix, List<String>> presentArgMap = new HashMap<>();
        for (Prefix presentPrefix: presentPrefixes) {
            if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
                continue;
            }
            presentArgMap.put(presentPrefix, Arrays.asList(argMultimap.getValue(PREFIX_NAME).get()
                    .split("\\s+")));
        }

        return new FindCommand(new NameContainsSubstringsPredicate(new ArrayList<>()));
    }
}

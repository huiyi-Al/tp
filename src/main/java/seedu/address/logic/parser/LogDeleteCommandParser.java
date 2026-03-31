package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LogDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LogDeleteCommand object.
 */
public class LogDeleteCommandParser implements Parser<LogDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LogDeleteCommand
     * and returns a LogDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LogDeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String usageMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogDeleteCommand.MESSAGE_USAGE);

        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+");

        if (parts.length != 2) {
            throw new ParseException(usageMessage);
        }

        Index personIndex = parseIndexOrThrowUsage(parts[0], usageMessage);
        Index logIndex = parseIndexOrThrowUsage(parts[1], usageMessage);

        return new LogDeleteCommand(personIndex, logIndex);
    }

    private static Index parseIndexOrThrowUsage(String rawIndex, String usageMessage) throws ParseException {
        try {
            return ParserUtil.parseIndex(rawIndex);
        } catch (ParseException pe) {
            throw new ParseException(usageMessage, pe);
        }
    }
}

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

        try {
            String trimmedArgs = args.trim();
            String[] parts = trimmedArgs.split("\\s+");

            if (parts.length != 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogDeleteCommand.MESSAGE_USAGE));
            }

            Index personIndex = ParserUtil.parseIndex(parts[0]);
            Index logIndex = ParserUtil.parseIndex(parts[1]);

            return new LogDeleteCommand(personIndex, logIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}

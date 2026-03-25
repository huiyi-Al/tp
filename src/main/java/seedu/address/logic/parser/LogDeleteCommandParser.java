package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LogDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code LogDeleteCommand} object.
 */
public class LogDeleteCommandParser implements Parser<LogDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code LogDeleteCommand}
     * and returns a {@code LogDeleteCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LogDeleteCommand parse(String args) throws ParseException {
        final String trimmedArgs = args.trim();
        final String[] tokens = trimmedArgs.split("\\s+");

        if (tokens.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogDeleteCommand.MESSAGE_USAGE));
        }

        try {
            Index personIndex = ParserUtil.parseIndex(tokens[0]);
            Index logIndex = ParserUtil.parseIndex(tokens[1]);
            return new LogDeleteCommand(personIndex, logIndex);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}

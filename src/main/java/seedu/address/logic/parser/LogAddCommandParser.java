package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LogAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.log.LogMessage;

/**
 * Parses input arguments and creates a new {@code LogAddCommand} object.
 */
public class LogAddCommandParser implements Parser<LogAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code LogAddCommand}
     * and returns a {@code LogAddCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LogAddCommand parse(String args) throws ParseException {
        SplitIndexAndMessage indexAndMessageSplit;
        try {
            indexAndMessageSplit = splitIndexAndMessage(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogAddCommand.MESSAGE_USAGE), pe);
        }

        final Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(indexAndMessageSplit.indexToken());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogAddCommand.MESSAGE_USAGE), pe);
        }

        String message = indexAndMessageSplit.message();
        if (!LogMessage.isValidLogMessage(message)) {
            throw new ParseException(LogMessage.MESSAGE_CONSTRAINTS);
        }

        return new LogAddCommand(personIndex, new LogMessage(message));
    }

    private static SplitIndexAndMessage splitIndexAndMessage(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException("Arguments are empty.");
        }

        int firstWhitespaceIndex = findFirstWhitespaceIndex(trimmedArgs);
        if (firstWhitespaceIndex == -1) {
            throw new ParseException("Missing log message.");
        }

        String indexToken = trimmedArgs.substring(0, firstWhitespaceIndex);
        String message = trimmedArgs.substring(firstWhitespaceIndex + 1).trim();
        if (message.isEmpty()) {
            throw new ParseException("Missing log message.");
        }

        return new SplitIndexAndMessage(indexToken, message);
    }

    private static int findFirstWhitespaceIndex(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (Character.isWhitespace(input.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private record SplitIndexAndMessage(String indexToken, String message) {}
}

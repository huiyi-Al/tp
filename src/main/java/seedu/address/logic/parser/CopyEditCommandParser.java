package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CopyEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CopyEditCommand object
 */
public class CopyEditCommandParser implements Parser<CopyEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyEditCommand
     * and returns a CopyEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyEditCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CopyEditCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyEditCommand.MESSAGE_USAGE), pe);
        }
    }
}

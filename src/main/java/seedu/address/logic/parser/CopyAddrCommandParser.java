package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CopyAddrCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CopyAddrCommand object
 */
public class CopyAddrCommandParser implements Parser<CopyAddrCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyAddrCommand
     * and returns a CopyAddrCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyAddrCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CopyAddrCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyAddrCommand.MESSAGE_USAGE), pe);
        }
    }
}
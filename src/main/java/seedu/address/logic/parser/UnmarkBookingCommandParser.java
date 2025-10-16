package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkBookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class UnmarkBookingCommandParser implements Parser<UnmarkBookingCommand>{



    public UnmarkBookingCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnmarkBookingCommand(index);
        }catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkBookingCommand.MESSAGE_USAGE), pe);
        }
    }
}

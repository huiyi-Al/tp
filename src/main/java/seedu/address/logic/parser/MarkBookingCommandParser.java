package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkBookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class MarkBookingCommandParser implements Parser<MarkBookingCommand>{



    public MarkBookingCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkBookingCommand(index);
        }catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkBookingCommand.MESSAGE_USAGE), pe);
        }
    }
}

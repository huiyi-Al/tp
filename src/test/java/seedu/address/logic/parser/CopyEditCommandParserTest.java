package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CopyEditCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CopyEditCommand code.
 */
public class CopyEditCommandParserTest {

    private CopyEditCommandParser parser = new CopyEditCommandParser();

    @Test
    public void parse_validArgs_returnsCopyEditCommand() {
        assertParseSuccess(parser, "1", new CopyEditCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_returnsCopyEditCommand() {
        assertParseSuccess(parser, "  1  ", new CopyEditCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyEditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyEditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyEditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyEditCommand.MESSAGE_USAGE));
    }
}

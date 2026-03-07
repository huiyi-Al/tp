package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CopyAddrCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CopyAddrCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the CopyAddrCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CopyAddrCommandParserTest {

    private CopyAddrCommandParser parser = new CopyAddrCommandParser();

    @Test
    public void parse_validArgs_returnsCopyAddrCommand() {
        assertParseSuccess(parser, "1", new CopyAddrCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_returnsCopyAddrCommand() {
        assertParseSuccess(parser, "  1  ", new CopyAddrCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Non-numeric input
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyAddrCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyAddrCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        // Negative index
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyAddrCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        // Zero index (invalid since indices are 1-based)
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyAddrCommand.MESSAGE_USAGE));
    }
}

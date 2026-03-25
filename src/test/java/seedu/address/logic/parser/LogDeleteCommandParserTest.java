package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LogDeleteCommand;

public class LogDeleteCommandParserTest {

    private final LogDeleteCommandParser parser = new LogDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsLogDeleteCommand() {
        assertParseSuccess(parser, "1 2", new LogDeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_tooFewTokens_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyTokens_throwsParseException() {
        assertParseFailure(parser, "1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        assertParseFailure(parser, "a 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidLogIndex_throwsParseException() {
        assertParseFailure(parser, "1 a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogDeleteCommand.MESSAGE_USAGE));
    }
}

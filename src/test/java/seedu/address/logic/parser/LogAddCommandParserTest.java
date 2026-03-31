package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LogAddCommand;
import seedu.address.model.person.log.LogMessage;

public class LogAddCommandParserTest {

    private final LogAddCommandParser parser = new LogAddCommandParser();

    @Test
    public void parse_validArgs_returnsLogAddCommand() {
        String message = "Observed leakage beneath sink during site visit.";
        assertParseSuccess(parser, "1 " + message,
                new LogAddCommand(INDEX_FIRST_PERSON, new LogMessage(message)));
    }

    @Test
    public void parse_validArgsWithPunctuation_returnsLogAddCommand() {
        String message = "Client requested follow-up call next Wednesday at 2pm.";
        assertParseSuccess(parser, "1 " + message,
                new LogAddCommand(INDEX_FIRST_PERSON, new LogMessage(message)));
    }

    @Test
    public void parse_messageTailPreserved_success() {
        String message = "Client requested follow-up   call next Wednesday.";
        assertParseSuccess(parser, "1 " + message,
                new LogAddCommand(INDEX_FIRST_PERSON, new LogMessage(message)));
    }

    @Test
    public void parse_missingMessage_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a Valid message",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankInput_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooLongMessage_throwsParseException() {
        String tooLongMessage = "a".repeat(LogMessage.MAX_LENGTH + 1);
        assertParseFailure(parser, "1 " + tooLongMessage, LogMessage.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}

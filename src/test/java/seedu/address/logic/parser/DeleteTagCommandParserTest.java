package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;

public class DeleteTagCommandParserTest {

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTagCommand() {
        // simple tag
        assertParseSuccess(parser, "plumbing", new DeleteTagCommand(new Tag("plumbing")));

        // multi-word tag
        assertParseSuccess(parser, "AC Repair", new DeleteTagCommand(new Tag("AC Repair")));

        // with whitespace
        assertParseSuccess(parser, "  plumbing  ", new DeleteTagCommand(new Tag("plumbing")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty string
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));

        // tag > 50 characters
        String longTag = "a".repeat(51);
        assertParseFailure(parser, longTag, String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tag.MESSAGE_CONSTRAINTS));
    }
}

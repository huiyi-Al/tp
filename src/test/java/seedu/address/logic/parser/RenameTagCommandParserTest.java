package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.model.tag.Tag;

public class RenameTagCommandParserTest {

    private RenameTagCommandParser parser = new RenameTagCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Tag oldTag = new Tag("OldTag");
        Tag newTag = new Tag("NewTag");

        // Whitespace between arguments should not matter
        assertParseSuccess(parser, " --tag=OldTag --tag=NewTag", new RenameTagCommand(oldTag, newTag));
    }

    @Test
    public void parse_missingParts_failure() {
        // only one tag
        assertParseFailure(parser, " --tag=OldTag", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RenameTagCommand.MESSAGE_USAGE));

        // zero tags
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RenameTagCommand.MESSAGE_USAGE));

        // three tags
        assertParseFailure(parser, " --tag=1 --tag=2 --tag=3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RenameTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE);

        // preamble with a number
        assertParseFailure(parser, "1 --tag=Old --tag=New", message);

        // preamble with a string
        assertParseFailure(parser, "random --tag=Old --tag=New", message);

        // preamble with special character
        assertParseFailure(parser, "i/string --tag=Old --tag=New", message);
    }

    @Test
    public void parse_invalidTagValues_failure() {
        // Tag too long (> 50 chars)
        String longTagName = "a".repeat(51);
        assertParseFailure(parser, " --tag=Valid --tag=" + longTagName, Tag.MESSAGE_CONSTRAINTS);

        // Blank tag
        assertParseFailure(parser, " --tag=Valid --tag=", Tag.MESSAGE_CONSTRAINTS);
    }
}

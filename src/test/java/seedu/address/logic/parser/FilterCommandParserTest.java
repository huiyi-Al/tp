package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.predicates.TagsMatchAllKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // No arguments
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // No tag prefix
        assertParseFailure(parser, " Plumbing",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty tag value
        assertParseFailure(parser, " --tag=",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // Multiple tags with one empty
        assertParseFailure(parser, " --tag=Plumbing --tag= ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // Preamble present
        assertParseFailure(parser, " someText --tag=Urgent",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // Single tag
        FilterCommand expectedFilterCommand =
                new FilterCommand(new TagsMatchAllKeywordsPredicate(Arrays.asList("Plumbing")));
        assertParseSuccess(parser, " --tag=Plumbing", expectedFilterCommand);

        // Multiple tags with whitespaces
        expectedFilterCommand =
                new FilterCommand(new TagsMatchAllKeywordsPredicate(Arrays.asList("AC-Service", "Electrical")));
        assertParseSuccess(parser, " --tag=AC-Service --tag=Electrical", expectedFilterCommand);

        // Case with trailing/leading spaces around prefix
        assertParseSuccess(parser, "    --tag=AC-Service   --tag=Electrical  ", expectedFilterCommand);
    }
}

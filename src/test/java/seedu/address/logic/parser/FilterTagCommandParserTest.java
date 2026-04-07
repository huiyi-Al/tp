package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.model.person.predicates.TagsMatchAllKeywordsPredicate;

public class FilterTagCommandParserTest {

    private FilterTagCommandParser parser = new FilterTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // No arguments
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));

        // No tag prefix
        assertParseFailure(parser, " Plumbing",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Multiple tags with one empty
        assertParseFailure(parser, " --tag=Plumbing --tag= ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_MIX_COMMAND));

        // Preamble present
        assertParseFailure(parser, " someText --tag=Urgent",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // Single tag
        FilterTagCommand expectedFilterCommand =
                new FilterTagCommand(new TagsMatchAllKeywordsPredicate(Arrays.asList("Plumbing")));
        assertParseSuccess(parser, " --tag=Plumbing", expectedFilterCommand);

        // Multiple tags with whitespaces
        expectedFilterCommand =
                new FilterTagCommand(new TagsMatchAllKeywordsPredicate(Arrays.asList("AC-Service", "Electrical")));
        assertParseSuccess(parser, " --tag=AC-Service --tag=Electrical", expectedFilterCommand);

        // Case with trailing/leading spaces around prefix
        assertParseSuccess(parser, "    --tag=AC-Service   --tag=Electrical  ", expectedFilterCommand);

        // Empty tag with prefix
        assertParseSuccess(parser, " --tag=",
                new FilterTagCommand(new TagsMatchAllKeywordsPredicate(List.of(""))));

        // Multiple empty tags -> Parser collapses this to List.of("")
        assertParseSuccess(parser, " --tag= --tag=",
                new FilterTagCommand(new TagsMatchAllKeywordsPredicate(List.of(""))));
    }
}

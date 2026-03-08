package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseDifferent;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   " + PREFIX_NAME + "  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preambleOnly_throwsParseException() {
        assertParseFailure(parser, "alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsNamePrefixOnly_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_NAME + "\n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsNamePreambleAndPrefixSuccess_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Bob")));
        assertParseSuccess(parser, "Carol " + PREFIX_NAME + "Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n Carol \n " + PREFIX_NAME + "\tBob\t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsNamePreambleAndPrefixFailure_throwsParseException() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Bob")));
        assertParseDifferent(parser, "Bob " + PREFIX_NAME + "Carol", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseDifferent(parser, "\tBob\t " + PREFIX_NAME + "\nCarol\n", expectedFindCommand);
    }

}

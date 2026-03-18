package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseDifferent;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.predicate.SearchPredicate;

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
        Map<Prefix, List<String>> argMap = new HashMap<>();
        argMap.put(PREFIX_NAME, Arrays.asList("Alice", "Bob"));

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new SearchPredicate(argMap));
        assertParseSuccess(parser, PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREFIX_NAME + "\n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsNamePreambleAndPrefixSuccess_returnsFindCommand() {
        Map<Prefix, List<String>> argMap = new HashMap<>();
        argMap.put(PREFIX_NAME, Arrays.asList("Bob"));

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new SearchPredicate(argMap));
        assertParseSuccess(parser, "Carol " + PREFIX_NAME + "Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n Carol \n " + PREFIX_NAME + "\tBob\t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsNamePreambleAndPrefixFailure_throwsParseException() {
        Map<Prefix, List<String>> argMap = new HashMap<>();
        argMap.put(PREFIX_NAME, Arrays.asList("Bob"));

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new SearchPredicate(argMap));
        assertParseDifferent(parser, "Bob " + PREFIX_NAME + "Carol", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseDifferent(parser, "\tBob\t " + PREFIX_NAME + "\nCarol\n", expectedFindCommand);
    }

    @Test
    public void parse_differentCase_returnsDifferentFindCommand() {
        Map<Prefix, List<String>> argMap = new HashMap<>();
        argMap.put(PREFIX_NAME, Arrays.asList("Alice", "Bob"));

        FindCommand expectedFindCommand =
                new FindCommand(new SearchPredicate(argMap));
        assertParseDifferent(parser, PREFIX_NAME + "alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_validPhone_returnsFindCommand() {
        Map<Prefix, List<String>> argMap = new HashMap<>();
        argMap.put(PREFIX_PHONE, Arrays.asList("123"));

        FindCommand expectedFindCommand =
                new FindCommand(new SearchPredicate(argMap));
        assertParseDifferent(parser, PREFIX_PHONE + "123", expectedFindCommand);
    }

    @Test
    public void parse_validNameAndValidPhone_returnsFindCommand() {
        Map<Prefix, List<String>> argMap = new HashMap<>();
        argMap.put(PREFIX_NAME, Arrays.asList("Alice"));
        argMap.put(PREFIX_PHONE, Arrays.asList("123"));

        FindCommand expectedFindCommand =
                new FindCommand(new SearchPredicate(argMap));
        assertParseDifferent(parser, PREFIX_NAME + "Alice" + " " + PREFIX_PHONE + "123", expectedFindCommand);
    }

    @Test
    public void parse_validNumberAndValidName_returnsFindCommand() {
        Map<Prefix, List<String>> argMap = new HashMap<>();
        argMap.put(PREFIX_NAME, Arrays.asList("Alice"));
        argMap.put(PREFIX_PHONE, Arrays.asList("123"));

        FindCommand expectedFindCommand =
                new FindCommand(new SearchPredicate(argMap));
        assertParseDifferent(parser, PREFIX_PHONE + "123" + " " + PREFIX_NAME + "Alice", expectedFindCommand);
    }
}

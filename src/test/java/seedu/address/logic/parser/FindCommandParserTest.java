package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseDifferent;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.text.MessageFormat;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.predicates.SearchPredicate;

/**
 * Tests the FindCommandParser for expected positive and negative behavior
 * in most efficient and effective manner.
 *
 * <ul>
 *   <li>Empty command, parse exception thrown.</li>
 *   <li>Preamble only, parse exception thrown.</li>
 *   <li>Empty field, parse exception thrown.</li>
 *   <li>Multi-query (unprefixed extra token), parse exception thrown.</li>
 *   <li>Valid name with whitespace variants, same expected command.</li>
 *   <li>Valid name w/ same case, same expected command.</li>
 *   <li>Valid name w/ different case, different expected command.</li>
 *   <li>Valid phone, same expected command.</li>
 *   <li>Valid email address w/ special characters, same expected command.</li>
 *   <li>Valid email address w/ special characters different case, different expected command.</li>
 *   <li>Valid physical address w/ same case, different expected command.</li>
 *   <li>Valid physical address w/ different case, different expected command.</li>
 *   <li>Valid tag, same expected command.</li>
 *   <li>Valid all fields expected order, same expected command.</li>
 *   <li>Valid all fields scrambled order, same expected command.</li>
 * </ul>
 */
public class FindCommandParserTest {
    private static final String TEST_EMPTY = "    ";
    private static final String TEST_NAME1 = "Alice";
    private static final String TEST_NAME2 = "Bob";
    private static final String TEST_NAME3 = "Carol";
    private static final String TEST_NAME4 = "David";
    private static final String TEST_MULTINAME1 = MessageFormat.format(" {0}{1} {0}{2}", PREFIX_NAME, TEST_NAME1,
            TEST_NAME2);
    private static final String TEST_PHONE1 = "123";
    private static final String TEST_PHONE2 = "456";
    private static final String TEST_MULTIPHONE1 = MessageFormat.format(" {0}{1} {0}{2}", PREFIX_PHONE,
            TEST_PHONE1, TEST_PHONE2);
    private static final String TEST_EMAIL1 = MessageFormat.format("{0}{1}@test.com", TEST_NAME1, TEST_PHONE1);
    private static final String TEST_EMAIL2 = MessageFormat.format("{0}{1}@test.com", TEST_NAME2, TEST_PHONE2);
    private static final String TEST_MULTIEMAIL1 = MessageFormat.format(" {0}{1} {0}{2}", PREFIX_EMAIL,
            TEST_EMAIL1, TEST_EMAIL2);
    private static final String TEST_ADDRESS1 = "Geylang";
    private static final String TEST_ADDRESS2 = "Tampines";
    private static final String TEST_MULTIADDRESS1 = MessageFormat.format(" {0}{1} {0}{2}", PREFIX_ADDRESS,
            TEST_ADDRESS1, TEST_ADDRESS2);
    private static final String TEST_TAG1 = "Tag1";
    private static final String TEST_TAG2 = "Tag2";
    private static final String TEST_MULTITAG1 = MessageFormat.format(" {0}{1} {0}{2}", PREFIX_TAG,
            TEST_TAG1, TEST_TAG2);
    private static final String TEST_MULTIALL_SAME_ORDER = MessageFormat.format(" {0} {1} {2} {3} {4}",
            TEST_MULTINAME1, TEST_MULTIPHONE1, TEST_MULTIEMAIL1, TEST_MULTIADDRESS1, TEST_MULTITAG1);
    private static final String TEST_MULTIALL_DIFFERENT_ORDER = MessageFormat.format(" {0} {1} {2} {3} {4}",
            TEST_MULTINAME1, TEST_MULTIEMAIL1, TEST_MULTIPHONE1, TEST_MULTIADDRESS1, TEST_MULTITAG1);
    private static final String TEST_MULTIQUERY = MessageFormat.format(" {0}{1} {2}", PREFIX_NAME, TEST_NAME1,
            TEST_NAME2);

    private ArgumentMultimap argMultimap;
    private final FindCommandParser parser;
    private FindCommand expected;

    public FindCommandParserTest() {
        this.argMultimap = new ArgumentMultimap();
        this.parser = new FindCommandParser();
        this.expected = generateFindCommand();
    }

    private FindCommand generateFindCommand() {
        return new FindCommand(new SearchPredicate(argMultimap));
    }

    @Test
    public void parse_emptyCommand_throwsParseException() {
        assertParseFailure(parser, TEST_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preambleOnly_throwsParseException() {
        assertParseFailure(parser, TEST_NAME1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_preambleWithFields_throwParseException() {
        argMultimap.clear();
        argMultimap.put(PREFIX_NAME, TEST_NAME2);
        expected = generateFindCommand();

        assertParseFailure(parser, MessageFormat.format("{0} {1}{2}", TEST_NAME3, PREFIX_NAME, TEST_NAME2),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preambleAndValidFieldCorrectOrder_throwsParseException() {
        // No leading and trailing whitespaces
        assertParseFailure(parser, MessageFormat.format("{0} {1}{2}", TEST_NAME3, PREFIX_NAME, TEST_NAME2),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        // Multiple whitespaces between keywords
        assertParseFailure(parser, MessageFormat.format("\n {0} \n {1}\t{2}\t", TEST_NAME3, PREFIX_NAME,
                TEST_NAME2), String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preambleAndValidFieldWrongOrder_throwsParseException() {
        // No leading and trailing whitespaces
        assertParseFailure(parser, MessageFormat.format("{0} {1}{2}", TEST_NAME2, PREFIX_NAME, TEST_NAME3),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        // Multiple whitespaces between keywords
        assertParseFailure(parser, MessageFormat.format("\t{0}\t {1} \n{2}\n", TEST_NAME2, PREFIX_NAME,
                TEST_NAME3), String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyField_throwsParseException() {
        assertParseFailure(parser, MessageFormat.format("{0}{1}", PREFIX_NAME, TEST_EMPTY),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiQuery_returnDifferentFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTIQUERY, PREFIX_NAME);
        expected = generateFindCommand();

        ArgumentMultimap argMultimapIndivName = ArgumentTokenizer.tokenize(TEST_MULTINAME1, PREFIX_NAME);
        FindCommand expectedIndivName = new FindCommand(new SearchPredicate(argMultimapIndivName));

        assertParseSuccess(parser, TEST_MULTIQUERY, expected);
        assertParseDifferent(parser, TEST_MULTIQUERY, expectedIndivName);
    }

    @Test
    public void parse_withWhiteSpace_returnsSameFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTINAME1, PREFIX_NAME);
        expected = generateFindCommand();

        assertParseSuccess(parser, MessageFormat.format("{0} \t{1} \t {0}\n {2}\n", PREFIX_NAME, TEST_NAME1,
                TEST_NAME2), expected);
    }

    @Test
    public void parse_validNameSameCase_returnsSameFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTINAME1, PREFIX_NAME);
        expected = generateFindCommand();

        assertParseSuccess(parser, TEST_MULTINAME1, expected);
    }

    @Test
    public void parse_validNameDifferentCase_returnsDifferentFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTINAME1.toUpperCase(), PREFIX_NAME);
        expected = generateFindCommand();

        assertParseDifferent(parser, TEST_MULTINAME1, expected);
    }

    @Test
    public void parse_validPhone_returnsSameFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTIPHONE1, PREFIX_PHONE);
        expected = generateFindCommand();

        assertParseSuccess(parser, TEST_MULTIPHONE1, expected);
    }

    @Test
    public void parse_validEmailWithSpecialChars_returnSameFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTIEMAIL1, PREFIX_EMAIL);
        expected = generateFindCommand();

        assertParseSuccess(parser, TEST_MULTIEMAIL1, expected);
    }

    @Test
    public void parse_validEmailWithSpecialCharsDifferentCase_returnDifferentFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTIEMAIL1.toUpperCase(), PREFIX_EMAIL);
        expected = generateFindCommand();

        assertParseDifferent(parser, MessageFormat.format("{0}{1}", PREFIX_EMAIL, TEST_MULTIEMAIL1), expected);
    }

    @Test
    public void parse_validAddressSameCase_returnsSameFindCommand() {
        argMultimap.put(PREFIX_ADDRESS, TEST_MULTIADDRESS1);
        expected = generateFindCommand();

        assertParseDifferent(parser, TEST_MULTIADDRESS1, expected);
    }

    @Test
    public void parse_validAddressDifferentCase_returnsDifferentFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTIADDRESS1.toUpperCase(), PREFIX_ADDRESS);
        expected = generateFindCommand();

        assertParseDifferent(parser, TEST_MULTIADDRESS1, expected);
    }

    @Test
    public void parse_validTag_returnsSameFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTITAG1, PREFIX_TAG);
        expected = generateFindCommand();

        assertParseSuccess(parser, TEST_MULTITAG1, expected);
    }

    @Test
    public void parse_allFieldsExpectedOrder_returnsSameFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTIALL_SAME_ORDER, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        expected = generateFindCommand();

        assertParseSuccess(parser, TEST_MULTIALL_SAME_ORDER, expected);
    }

    @Test
    public void parse_allFieldsScrambledOrder_returnsSameFindCommand() {
        argMultimap = ArgumentTokenizer.tokenize(TEST_MULTIALL_DIFFERENT_ORDER, PREFIX_PHONE, PREFIX_NAME,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        expected = generateFindCommand();

        assertParseSuccess(parser, TEST_MULTIALL_DIFFERENT_ORDER, expected);
    }
}

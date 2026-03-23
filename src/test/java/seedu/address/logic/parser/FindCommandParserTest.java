package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
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
 *   <li>Preamble and valid argument in correct order, same expected command.</li>
 *   <li>Preamble and valid argument in wrong order, parse exception thrown.</li>
 *   <li>Empty field, parse exception thrown.</li>
 *   <li>Valid name w/ same case, same expected command.</li>
 *   <li>Valid name w/ different case, different expected command.</li>
 *   <li>Valid phone, same expected command.</li>
 *   <li>Valid email no @ or ., same expected command. (TODO)</li>
 *   <li>Valid email w/ @ and ., same expected command. (TODO)</li>
 *   <li>Valid email with different case, different expected command. (TODO)</li>
 *   <li>Valid all fields expected order, same expected command.</li>
 *   <li>Valid all fields scrambled order, same expected command.</li>
 * </ul>
 */
public class FindCommandParserTest {
    private final ArgumentMultimap argMultimap;
    private final FindCommandParser parser;
    private FindCommand expected;

    private static final String TEST_EMPTY = "    ";
    private static final String TEST_NAME1 = "Alice";
    private static final String TEST_NAME2 = "Bob";
    private static final String TEST_NAME3 = "Carol";
    private static final String TEST_NAME4 = "David";
    private static final String TEST_MULTINAME1 = MessageFormat.format("{0} {1}", TEST_NAME1, TEST_NAME2);
    private static final String TEST_PHONE1 = "123";
    private static final String TEST_PHONE2 = "456";
    private static final String TEST_MULTIPHONE1 = MessageFormat.format("{0} {1}", TEST_PHONE1, TEST_PHONE2);
    private static final String TEST_EMAIL1 = MessageFormat.format("{0}{1}@test.com", TEST_NAME1, TEST_PHONE1);
    private static final String TEST_EMAIL2 = MessageFormat.format("{0}{1}@test.com", TEST_NAME2, TEST_PHONE2);
    private static final String TEST_MULTIEMAIL1 = MessageFormat.format("{0} {1}", TEST_EMAIL1, TEST_EMAIL2);


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
    public void parse_preambleAndValidFieldCorrectOrder_returnsSameFindCommand() {
        argMultimap.clear();
        argMultimap.put(PREFIX_NAME, TEST_NAME2);
        expected = generateFindCommand();

        // No leading and trailing whitespaces
        assertParseSuccess(parser, MessageFormat.format("{0} {1}{2}", TEST_NAME3, PREFIX_NAME, TEST_NAME2),
                expected);
        // Multiple whitespaces between keywords
        assertParseSuccess(parser, MessageFormat.format("\n {0} \n {1}\t{2}\t", TEST_NAME3, PREFIX_NAME,
                TEST_NAME2), expected);
    }

    @Test
    public void parse_preambleAndValidFieldWrongOrder_throwsParseException() {
        argMultimap.clear();
        argMultimap.put(PREFIX_NAME, TEST_NAME2);
        expected = generateFindCommand();

        // No leading and trailing whitespaces
        assertParseDifferent(parser, MessageFormat.format("{0} {1}{2}", TEST_NAME2, PREFIX_NAME, TEST_NAME3),
                expected);
        // Multiple whitespaces between keywords
        assertParseDifferent(parser, MessageFormat.format("\t{0}\t {1} \n{2}\n", TEST_NAME2, PREFIX_NAME,
                TEST_NAME3), expected);
    }

    @Test
    public void parse_emptyField_throwsParseException() {
        assertParseFailure(parser, MessageFormat.format("{0}{1}", PREFIX_NAME, TEST_EMPTY),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameSameCase_returnsSameFindCommand() {
        argMultimap.clear();
        argMultimap.put(PREFIX_NAME, TEST_MULTINAME1);
        expected = generateFindCommand();

        // No leading and trailing whitespaces
        assertParseSuccess(parser, MessageFormat.format("{0}{1}", PREFIX_NAME, TEST_MULTINAME1), expected);
        // Multiple whitespaces between keywords
        assertParseSuccess(parser, MessageFormat.format("{0} \t{1} \t \n {2}\n", PREFIX_NAME, TEST_NAME1,
                TEST_NAME2), expected);
    }

    @Test
    public void parse_validNameDifferentCase_returnsDifferentFindCommand() {
        argMultimap.clear();
        argMultimap.put(PREFIX_NAME, TEST_MULTINAME1.toUpperCase());
        expected = generateFindCommand();

        assertParseDifferent(parser, MessageFormat.format("{0}{1}", PREFIX_NAME, TEST_MULTINAME1), expected);
    }

    @Test
    public void parse_validPhone_returnsSameFindCommand() {
        argMultimap.clear();
        argMultimap.put(PREFIX_PHONE, TEST_MULTIPHONE1);
        expected = generateFindCommand();

        assertParseSuccess(parser, MessageFormat.format("{0}{1}", PREFIX_PHONE, TEST_MULTIPHONE1), expected);
    }

    @Test
    public void parse_validEmailNoSpecialChars_returnSameFindCommand() {
        argMultimap.clear();
        argMultimap.put(PREFIX_EMAIL, TEST_MULTINAME1);
        expected = generateFindCommand();

        assertParseSuccess(parser, MessageFormat.format("{0}{1}", PREFIX_EMAIL, TEST_MULTINAME1), expected);
    }

    @Test
    public void parse_validEmailWithSpecialChars_returnSameFindCommand() {
        argMultimap.clear();
        argMultimap.put(PREFIX_EMAIL, TEST_MULTIEMAIL1);
        expected = generateFindCommand();

        assertParseSuccess(parser, MessageFormat.format("{0}{1}", PREFIX_EMAIL, TEST_MULTIEMAIL1), expected);
    }

    @Test
    public void parse_validEmailWithSpecialChars_returnDifferentFindCommand() {
        argMultimap.clear();
        argMultimap.put(PREFIX_EMAIL, TEST_MULTIEMAIL1.toUpperCase());
        expected = generateFindCommand();

        assertParseDifferent(parser, MessageFormat.format("{0}{1}", PREFIX_EMAIL, TEST_MULTIEMAIL1), expected);
    }

    @Test
    public void parse_allFieldsExpectedOrder_returnsSameFindCommand() {
        argMultimap.clear();
        argMultimap.put(PREFIX_NAME, TEST_MULTINAME1);
        argMultimap.put(PREFIX_PHONE, TEST_MULTIPHONE1);
        argMultimap.put(PREFIX_EMAIL, TEST_MULTIEMAIL1);
        expected = generateFindCommand();

        assertParseSuccess(parser, MessageFormat.format("{0}{1} {2}{3} {4}{5}", PREFIX_NAME, TEST_MULTINAME1,
                PREFIX_PHONE, TEST_MULTIPHONE1, PREFIX_EMAIL, TEST_MULTIEMAIL1), expected);
    }

    @Test
    public void parse_allFieldsScrambledOrder_returnsSameFindCommand() {
        argMultimap.clear();
        argMultimap.put(PREFIX_NAME, TEST_MULTINAME1);
        argMultimap.put(PREFIX_EMAIL, TEST_MULTIEMAIL1);
        argMultimap.put(PREFIX_PHONE, TEST_MULTIPHONE1);
        expected = generateFindCommand();

        assertParseSuccess(parser, MessageFormat.format("{0}{1} {2}{3} {4}{5}", PREFIX_EMAIL, TEST_MULTIEMAIL1,
                PREFIX_PHONE, TEST_MULTIPHONE1, PREFIX_NAME, TEST_MULTINAME1), expected);
    }
}

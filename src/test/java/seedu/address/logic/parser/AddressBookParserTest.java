package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CopyAddrCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LogAddCommand;
import seedu.address.logic.commands.LogDeleteCommand;
import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.log.LogMessage;
import seedu.address.model.person.predicates.SearchPredicate;
import seedu.address.model.person.predicates.TagsMatchAllKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);

        assertThrows(ParseException.class, () ->
                parser.parseCommand(ClearCommand.COMMAND_WORD + " 3"));

        assertThrows(ParseException.class, () ->
                parser.parseCommand(ClearCommand.COMMAND_WORD + " abc"));
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ExitCommand.COMMAND_WORD + " 3"));
        assertThrows(ParseException.class, () ->
                parser.parseCommand(ExitCommand.COMMAND_WORD + " abc"));
    }

    @Test
    public void parseCommand_find() throws Exception {
        String subNames = MessageFormat
                .format(" {0}{1} {0}{2} {0}{3}", PREFIX_NAME, "KUrZ", "ElLe", "kuNz");
        String subNumbers = MessageFormat
                .format(" {0}{1} {0}{2}", PREFIX_PHONE, "535", "442");
        String subEmailAddresses = MessageFormat
                .format(" {0}{1} {0}{2}", PREFIX_EMAIL, "r@e", "a@e");
        String subPhysicalAddresses = MessageFormat
                .format(" {0}{1} {0}{2}", PREFIX_ADDRESS, "Jurong", "clEmenti");
        String tags = MessageFormat
                .format(" {0}{1} {0}{2}", PREFIX_TAG, "plumbing", "ac-Service");
        String combined = MessageFormat
                .format("{0} {1} {2} {3} {4}", subNames, subNumbers, subEmailAddresses, subPhysicalAddresses,
                        tags);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(combined, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_TAG);

        FindCommand command = (FindCommand) parser.parseCommand(
                MessageFormat.format("{0} {1}", FindCommand.COMMAND_WORD, combined)
        );

        assertEquals(new FindCommand(new SearchPredicate(argMultimap)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);

        assertThrows(ParseException.class, () ->
                parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));

        assertThrows(ParseException.class, () ->
                parser.parseCommand(HelpCommand.COMMAND_WORD + " abc"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);

        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListCommand.COMMAND_WORD + " 3"));

        assertThrows(ParseException.class, () ->
                parser.parseCommand(ListCommand.COMMAND_WORD + " abc"));
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ViewCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_copyaddr() throws Exception {
        CopyAddrCommand command = (CopyAddrCommand) parser.parseCommand(
                CopyAddrCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new CopyAddrCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        List<String> keywords = Arrays.asList("AC-Servicing", "Plumbing", "Urgent");
        String userInput = FilterCommand.COMMAND_WORD + " "
                + keywords.stream()
                .map(tag -> PREFIX_TAG + tag)
                .collect(Collectors.joining(" "));

        FilterCommand command = (FilterCommand) parser.parseCommand(userInput);

        assertEquals(new FilterCommand(new TagsMatchAllKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_logAdd() throws Exception {
        String message = "Observed leakage beneath sink during site visit.";
        LogAddCommand command = (LogAddCommand) parser.parseCommand(
                LogAddCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " " + message);
        assertEquals(new LogAddCommand(INDEX_FIRST_PERSON, new LogMessage(message)), command);
    }

    @Test
    public void parseCommand_logDelete() throws Exception {
        LogDeleteCommand command = (LogDeleteCommand) parser.parseCommand(
                LogDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new LogDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_renametag() throws Exception {
        Tag oldTag = new Tag("Old");
        Tag newTag = new Tag("New");

        RenameTagCommand command = (RenameTagCommand) parser.parseCommand(
                RenameTagCommand.COMMAND_WORD + " --tag=" + oldTag.tagName + " --tag=" + newTag.tagName);

        assertEquals(new RenameTagCommand(oldTag, newTag), command);
    }

    @Test
    public void parseCommand_deletetag() throws Exception {
        Tag tag = new Tag("Plumbing");
        String userInput = DeleteTagCommand.COMMAND_WORD + " " + tag.tagName;
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(userInput);

        assertEquals(new DeleteTagCommand(tag), command);
    }
}

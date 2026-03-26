package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new RenameTagCommand object.
 */
public class RenameTagCommandParser implements Parser<RenameTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenameTagCommand.
     */
    public RenameTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        List<String> tagNames = argMultimap.getAllValues(PREFIX_TAG);

        // Validate that exactly two tags were provided and no preamble
        if (tagNames.size() != 2 || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE));
        }

        try {
            Tag oldTag = ParserUtil.parseTag(tagNames.get(0));
            Tag newTag = ParserUtil.parseTag(tagNames.get(1));
            return new RenameTagCommand(oldTag, newTag);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, pe.getMessage()));
        }
    }
}

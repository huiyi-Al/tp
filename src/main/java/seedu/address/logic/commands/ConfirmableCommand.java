package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command that requires confirmation before execution.
 */
public abstract class ConfirmableCommand extends Command {

    /**
     * Returns the confirmation message to show to the user.
     */
    public abstract String getConfirmationMessage();

    /**
     * Executes the confirmed command.
     */
    public abstract CommandResult executeConfirmed(Model model) throws CommandException;

    /**
     * Returns the command word for matching.
     */
    public abstract String getCommandWord();

    /**
     * Returns the index for matching (if applicable).
     */
    public abstract int getConfirmationIndex();

    /**
     * Returns true if the given command matches this pending command.
     */
    public boolean matches(Command command) {
        if (!(command instanceof ConfirmableCommand)) {
            return false;
        }
        ConfirmableCommand other = (ConfirmableCommand) command;
        return this.getCommandWord().equals(other.getCommandWord())
                && this.getConfirmationIndex() == other.getConfirmationIndex();
    }
}
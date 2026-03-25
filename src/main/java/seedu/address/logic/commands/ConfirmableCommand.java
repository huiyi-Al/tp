package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command that requires confirmation before execution.
 */
public abstract class ConfirmableCommand extends Command {

    /**
     * Prepares the command by validating and loading necessary data.
     * This is called before storing the pending command.
     *
     * @param model The model to access data.
     * @throws CommandException if preparation fails (e.g., invalid index).
     */
    public abstract void prepare(Model model) throws CommandException;

    /**
     * Returns the confirmation message to show to the user.
     */
    public abstract String getConfirmationMessage();

    /**
     * Executes the confirmed command (called on second execution).
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

    /**
     * This method is called on the FIRST execution of a confirmable command.
     * It prepares the command and returns a CommandResult with the confirmation message.
     * LogicManager will then store this command as pending.
     */
    @Override
    public final CommandResult execute(Model model) throws CommandException {
        prepare(model);
        return new CommandResult(getConfirmationMessage());
    }
}